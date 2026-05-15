package com.re_form_shop_2605.service.chat;

import com.re_form_shop_2605.domain.chat.ChatMessageVO;
import com.re_form_shop_2605.domain.etc.RiskAnalysisResultVO;
import com.re_form_shop_2605.dto.chat.*;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.AI.RiskAnalysisResultDTO;
import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.entity.Enum.MessageType;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.chat.ChatMessage;
import com.re_form_shop_2605.entity.chat.ChatRoom;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.mapper.chat.ChatMessageMapper;
import com.re_form_shop_2605.repository.chat.ChatMessageRepository;
import com.re_form_shop_2605.repository.chat.ChatRoomRepository;
import com.re_form_shop_2605.repository.AI.RiskAnalysisResultRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-11
 * 설명: 채팅을 구현하는 서비스
 * ─────────────────────────────────────────────────────
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;

    /* 채팅방 생성 */
    public ChatRoomDetailDTO getOrCreateChatRoom(Long postId, Long buyerId){
        // 1. 기존 채팅방 조회 (같은 판매글, 같은 구매자 채팅방 중복 확인 -> DB에서 복합유니크로 보장 되지만 코드에서도 확인)
        Optional<ChatRoom> existing = chatRoomRepository.findByPost_PostIdAndBuyer_MemberId(postId, buyerId);
        if (existing.isPresent()){
            return toChatRoomDetailDTO(existing.get());
        }

        // 2. 없으면 새로 생성
        Post post = postRepository.findById(postId).orElseThrow();
        Member buyer = memberRepository.findById(buyerId).orElseThrow();

        // 판매자는 post에서 가져옴
        // buyer가 본인 판매글에 채팅 시도하는 경우 방지
        if (post.getSellerId().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("본인 판매글에 채팅할 수 없습니다."); // todo(어떻게 처리할지 프론트와 상의 !!)
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .post(post)
                .buyer(buyer)
                // trade는 아직 null (채팅 먼저 -> 거래 나중)
                .build();

        // DB에 저장
        chatRoomRepository.save(chatRoom);

        // DTO로 변환해서 반환 (DTO 변환 메서드)
        return toChatRoomDetailDTO(chatRoom);
    }

    /* 메시지 저장 (WebSocket 컨트롤러에서 호출) */
    public ChatMessageDTO saveMessage(
            ChatSendMessageDTO chatSendMessageDTO,
            RiskAnalysisResultDTO riskAnalysisResultDTO
            ){
        ChatRoom chatRoom = chatRoomRepository.findById(chatSendMessageDTO.chatId()).orElseThrow();
        validateParticipant(chatRoom, chatSendMessageDTO.senderId());
        Member sender = memberRepository.findById(chatSendMessageDTO.senderId()).orElseThrow();

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .member(sender)
                .content(chatSendMessageDTO.content())
                .type(MessageType.valueOf(chatSendMessageDTO.type())) // todo .toUpperCase() -> 대문자로 변환 후 처리?
                .isRead(false)
                .build();

        return toChatMessageDTO(chatMessageRepository.save(chatMessage), riskAnalysisResultDTO);
    }

    /* 채팅방 입장 시 읽음 처리 */
    public void markAsRead(Long chatId, Long myId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow();
        validateParticipant(chatRoom, myId);
        chatMessageRepository.markAllAsRead(chatId, myId);
    }

    /** todo 메시지 이력 조회 (페이징) */
    public Page<ChatMessageDTO> getMessages(Long chatId, Pageable pageable){
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        List<ChatMessageVO> chatMessageVOList = chatMessageMapper.findMessagesWithModeration(chatId, offset, size);
        int total = chatMessageMapper.countMessagesByChatId(chatId);

        List<ChatMessageDTO> content = chatMessageVOList.stream()
                .map(vo -> {
                    // riskLevel != null -> 유해
                    // riskLevel == null -> 정상
                    RiskAnalysisResultDTO moderation;

                    if (vo.getRiskAnalysisResultVO() == null
                            || vo.getRiskAnalysisResultVO().getRiskLevel() == null) {
                        // 정상 메시지 — safe() 반환
                        moderation = RiskAnalysisResultDTO.safe();
                    } else {
                        // 유해 메시지 — RiskAnalysisResultVO 값으로 DTO 구성
                        RiskAnalysisResultVO risk = vo.getRiskAnalysisResultVO();
                        moderation = new RiskAnalysisResultDTO(
                                risk.getRiskId(),
                                TargetType.CHAT,
                                vo.getMessageId(),
                                risk.getRiskLevel(),
                                risk.getReason(),
                                risk.getSuggestion(),
                                null  // todo createdAt — 필요 시 RiskAnalysisResultVO에 추가
                        );
                    }
                    return new ChatMessageDTO(
                            vo.getMessageId(),
                            vo.getSenderId(),
                            vo.getContent(),
                            vo.getType(),
                            vo.getCreatedAt(),
                            vo.getIsRead(),
                            moderation
                    );
                })
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    /* 내 채팅방 목록 -> 구매자, 판매자 통합해서 보여주기 */
    public List<ChatRoomSummaryDTO> getMyChatRooms(Long memberId) {
        List<ChatRoom> asBuyer = chatRoomRepository.findByBuyer_MemberIdOrderByCreatedAtDesc(memberId);
        List<ChatRoom> asSeller = chatRoomRepository.findByPost_PostIdOrderByCreatedAtDesc(memberId);

        return Stream.concat(asBuyer.stream(), asSeller.stream())
                .sorted(Comparator.comparing(ChatRoom::getCreatedAt).reversed())
                .map(this::toChatRoomSummaryDTO)
                .toList();
    }

    /* ---------------------------------------DTO 변환 메서드------------------------------------------- */
    private ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage){
        return new ChatMessageDTO(
                chatMessage.getMessageId(),
                chatMessage.getMember().getMemberId(),
                chatMessage.getContent(),
                chatMessage.getType().name(),
                chatMessage.getCreatedAt(),
                chatMessage.isRead(),
                RiskAnalysisResultDTO.safe()
        );
    }

    private ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage, RiskAnalysisResultDTO riskAnalysisResultDTO){
        return new ChatMessageDTO(
                chatMessage.getMessageId(),
                chatMessage.getMember().getMemberId(),
                chatMessage.getContent(),
                chatMessage.getType().name(),
                chatMessage.getCreatedAt(),
                chatMessage.isRead(),
                riskAnalysisResultDTO
        );
    }

    private ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoom chatRoom){
        // 구매자 정보
        MemberBriefDTO buyer = new MemberBriefDTO(
                chatRoom.getBuyer().getMemberId(),
                chatRoom.getBuyer().getNickname(),
                chatRoom.getBuyer().getProfileImageUrl()
        );

        // 판매자 정보
        MemberBriefDTO seller = new MemberBriefDTO(
                chatRoom.getPost().getSellerId().getMemberId(),
                chatRoom.getPost().getSellerId().getNickname(),
                chatRoom.getPost().getSellerId().getProfileImageUrl()
        );

        // 판매글 요약
        PostBriefDTO post = new PostBriefDTO(
                chatRoom.getPost().getPostId(),
                chatRoom.getPost().getTitle(),
                chatRoom.getPost().getImages().isEmpty() ? null : chatRoom.getPost().getImages().getFirst().getImageUrl(),
                chatRoom.getPost().getPrice(),
                chatRoom.getPost().getStatus()
        );

        // 거래 연결 여부
        Long tradeId = chatRoom.getTrade() != null ? chatRoom.getTrade().getTradeId() : null;
        TradeStatus tradeStatus = chatRoom.getTrade() != null ? chatRoom.getTrade().getStatus() : null;

        // 메시지 이력 첫 페이지 조회
        Page<ChatMessage> messagePage = chatMessageRepository.findByChatRoom_ChatIdOrderByCreatedAtDesc(chatRoom.getChatId(), PageRequest.of(0, 20));

        // PageResponse 필드 직접 주입
        PageResponse<ChatMessageDTO> messages = new PageResponse<>(
                messagePage.getContent().stream().map(this::toChatMessageDTO).toList(), // content
                messagePage.getTotalElements(),
                messagePage.getTotalPages(),
                messagePage.getSize(),
                messagePage.getNumber(),
                messagePage.isFirst(),
                messagePage.isLast()
        );

        return new ChatRoomDetailDTO(
                chatRoom.getChatId(),
                buyer,
                seller,
                post,
                tradeId,
                tradeStatus,
                messages
        );
    }

    private ChatRoomSummaryDTO toChatRoomSummaryDTO(ChatRoom chatRoom){
        // 상대방 구하기 - 현재 로그인 유저 기준으로 상대방이 달라짐
        // 내가 구매자면 -> seller가 상대방
        // 내가 판매자면 -> buyer가 상대방
        // todo (지금은 임시로 buyer 기준, Security 연동 후 수정 필요)
        Member seller = chatRoom.getPost().getSellerId();
        MemberBriefDTO partner = new MemberBriefDTO(
                seller.getMemberId(),
                seller.getNickname(),
                seller.getProfileImageUrl()
        );

        // 마지막 페이지
        Page<ChatMessage> lastPage = chatMessageRepository.findByChatRoom_ChatIdOrderByCreatedAtDesc(chatRoom.getChatId(), PageRequest.of(0, 1)); // 1개만 조회

        String lastMessage = lastPage.isEmpty() ? null : lastPage.getContent().getFirst().getContent(); // 어차피 한개 밖에 없다.

        LocalDateTime lastMessageAt = lastPage.isEmpty() ? null : lastPage.getContent().getLast().getCreatedAt(); // 어차피 한개 밖에 없다.

        // todo 읽지 않은 메시지 수 (Security 연동 후 myId 실제 값으로 교체)
        Long myId = chatRoom.getBuyer().getMemberId(); // todo 임시 (내 아이디)
        // 해당 하는 채팅룸의 읽지 않은 메세지 수 (내 아이디가 아니면서)
        int unreadCount = (int) chatMessageRepository.countByChatRoom_ChatIdAndIsReadFalseAndMember_MemberIdNot(chatRoom.getChatId(), myId);

        // 판매글 요약
        PostBriefDTO post = new PostBriefDTO(
                chatRoom.getPost().getPostId(),
                chatRoom.getPost().getTitle(),
                chatRoom.getPost().getImages().isEmpty() ? null : chatRoom.getPost().getImages().getFirst().getImageUrl(),
                chatRoom.getPost().getPrice(),
                chatRoom.getPost().getStatus()
        );

        return new ChatRoomSummaryDTO(
                chatRoom.getChatId(),
                partner,
                lastMessage,
                lastMessageAt,
                unreadCount,
                post
        );
    }

    private void validateParticipant(ChatRoom chatRoom, Long memberId) {
        boolean isBuyer = chatRoom.getBuyer().getMemberId().equals(memberId);
        boolean isSeller = chatRoom.getPost().getSellerId().getMemberId().equals(memberId);
        if (!isBuyer && !isSeller) {
            throw new IllegalArgumentException("채팅방 참여자만 메시지를 처리할 수 있습니다.");
        }
    }
}
