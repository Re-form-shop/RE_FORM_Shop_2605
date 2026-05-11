package com.re_form_shop_2605.service.chat;

import com.re_form_shop_2605.dto.chat.ChatMessageDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomDetailDTO;
import com.re_form_shop_2605.dto.chat.MemberBriefDTO;
import com.re_form_shop_2605.entity.chat.ChatMessage;
import com.re_form_shop_2605.entity.chat.ChatRoom;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.chat.ChatMessageRepository;
import com.re_form_shop_2605.repository.chat.ChatRoomRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 채팅방 생성
    public ChatRoomDetailDTO getOrCreateChatRoom(Long postId, Long buyerId){
        // 1. 기존 채팅방 조회 (같은 판매글, 같은 구매자 채팅방 중복 확인 -> DB에서 복합유니크로 보장 되지만 코드에서도 확인)
        Optional<ChatRoom> existing = chatRoomRepository.findByPost_PostIdAndBuyer_MemberId(postId, buyerId);
        if (existing.isPresent()){
            return toChatRoomDetailDTO(existing.get());
        }

        // 2. 없으면 새로 생성
        Post post = postRepository.findById(postId).orElseThrow();
    }


    // ---DTO 변환 메서드 ---
    private ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage){
        return new ChatMessageDTO(
                chatMessage.getMessageId(),
                chatMessage.getMember().getMemberId(),
                chatMessage.getContent(),
                chatMessage.getType().name(),
                chatMessage.getCreatedAt(),
                chatMessage.isRead()
        );
    }

    private ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoom chatRoom){
        // 구매자 정보
        MemberBriefDTO 
    }
}
