package com.re_form_shop_2605.service.trade;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.delivery.DeliveryCourierListResponseDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceRequestDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import com.re_form_shop_2605.dto.etc.TradeNotificationTemplateDTO;
import com.re_form_shop_2605.dto.trade.DeliveryRequestDTO;
import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.dto.chat.PostBriefDTO;
import com.re_form_shop_2605.dto.trade.ReviewRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeShippingRequestDTO;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.MannerReview;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.entity.trade.PostImage;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.repository.trade.mannerReviewRepository;
import com.re_form_shop_2605.repository.trade.postImageRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import com.re_form_shop_2605.service.delivery.DeliveryTrackingService;
import com.re_form_shop_2605.service.chat.ChatService;
import com.re_form_shop_2605.service.etc.NotificationService;
import com.re_form_shop_2605.entity.Enum.PointHistoryType;
import com.re_form_shop_2605.entity.payment.PointHistory;
import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.repository.payment.PointHistoryRepository;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 거래 관련 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
// 거래 생성, 상태 변경, 리뷰 작성 같은 거래 흐름을 담당한다.
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

    private static final int REVIEW_WRITE_PERIOD_DAYS = 5;

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final postImageRepository postImageRepository;
    private final mannerReviewRepository mannerReviewRepository;
    private final DeliveryTrackingService deliveryTrackingService;
    private final NotificationService notificationService;
    private final ChatService chatService; // 거래 이벤트 채팅방 시스템 메시지 발송용
    private final PointWalletRepository pointWalletRepository; // 구매 확정 시 판매자 포인트 정산용
    private final PointHistoryRepository pointHistoryRepository; // 포인트 이력 기록용

    @Override
    // 판매글 상태와 전달 방식 조건을 검증한 뒤 거래를 생성한다.
    public Long addTrade(Long buyerId, TradeRequestDTO tradeRequestDTO) {
        Member buyer = memberRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Post post = postRepository.findById(tradeRequestDTO.postId())
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if (post.getSellerId().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("본인 판매글에는 거래를 생성할 수 없습니다.");
        }

        if (post.getStatus() != PostStatus.ON_SALE) {
            throw new IllegalArgumentException("판매 중인 게시글만 거래를 생성할 수 있습니다.");
        }

        if (tradeRepository.existsByPost_PostId(post.getPostId())) {
            throw new IllegalArgumentException("이미 거래가 생성된 게시글입니다.");
        }

        Trade trade = Trade.builder()
                .post(post)
                .buyer(buyer)
                .seller(post.getSellerId())
                .status(TradeStatus.REQUESTED)
                .deliveryType(resolveTradeDeliveryType(post.getDeliveryType(), tradeRequestDTO.deliveryType()))
                .tradePrice(post.getPrice())
                .build();

        Long tradeId = tradeRepository.save(trade).getTradeId();

        // 거래 생성 시 채팅방 자동 생성 및 안내 메시지 발송
        chatService.sendSystemMessage(
                post.getPostId(), buyer.getMemberId(),
                "[RE:FORM] 구매 요청이 시작되었습니다. 판매자의 수락을 기다리고 있습니다."
        );

        return tradeId;
    }

    @Override
    @Transactional(readOnly = true)
    // 거래 상세 화면에 필요한 정보를 조립해 반환한다.
    public TradeResponseDTO readTrade(Long tradeId) {
        return mapTradeResponse(getTrade(tradeId));
    }

    @Override
    @Transactional(readOnly = true)
    public TradeResponseDTO readTrade(Long requesterId, Long tradeId) {
        Trade trade = getTrade(tradeId);
        if (!isTradeParticipant(trade, requesterId)) {
            throw new IllegalArgumentException("거래 참여자만 거래 상세를 조회할 수 있습니다.");
        }
        return mapTradeResponse(trade);
    }

    @Override
    @Transactional(readOnly = true)
    // 구매자 기준 거래 목록을 상태 필터와 함께 페이지 단위로 반환한다.
    public PageResponse<TradeResponseDTO> readBuyerTrades(Long buyerId, TradeStatus status, int page, int size) {
        List<Trade> trades = tradeRepository.findAllByBuyer_MemberIdOrderByTradeIdDesc(buyerId);
        List<TradeResponseDTO> tradeResponseDTOList = new ArrayList<>();

        for (Trade trade : trades) {
            if (!matchesTradeStatus(trade, status)) {
                continue;
            }
            tradeResponseDTOList.add(mapTradeResponse(trade));
        }

        return ServicePageResponse.of(tradeResponseDTOList, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    // 판매자 기준 거래 목록을 상태 필터와 함께 페이지 단위로 반환한다.
    public PageResponse<TradeResponseDTO> readSellerTrades(Long sellerId, TradeStatus status, int page, int size) {
        List<Trade> trades = tradeRepository.findAllBySeller_MemberIdOrderByTradeIdDesc(sellerId);
        List<TradeResponseDTO> tradeResponseDTOList = new ArrayList<>();

        for (Trade trade : trades) {
            if (!matchesTradeStatus(trade, status)) {
                continue;
            }
            tradeResponseDTOList.add(mapTradeResponse(trade));
        }

        return ServicePageResponse.of(tradeResponseDTOList, page, size);
    }

    @Override
    // 판매자만 구매 요청 상태의 거래를 수락할 수 있게 처리한다.
    public void acceptTrade(Long sellerId, Long tradeId) {
        Trade trade = getTrade(tradeId);

        if (!trade.getSeller().getMemberId().equals(sellerId)) {
            throw new IllegalArgumentException("거래 수락 권한이 없습니다.");
        }

        if (trade.getStatus() != TradeStatus.REQUESTED) {
            throw new IllegalArgumentException("구매 요청 상태의 거래만 수락할 수 있습니다.");
        }

        trade.accept();
        trade.getPost().changeStatus(PostStatus.RESERVED);

        // 거래 수락 시 채팅방에 안내 메시지
        chatService.sendSystemMessage(
                trade.getPost().getPostId(), trade.getBuyer().getMemberId(),
                "[RE:FORM] 판매자가 거래를 수락했습니다. 결제를 진행해 주세요."
        );
    }

    @Override
    // 구매자만 결제 이후 거래를 구매 확정할 수 있게 처리한다.
    public void confirmTrade(Long buyerId, Long tradeId) {
        Trade trade = getTrade(tradeId);

        if (!trade.getBuyer().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("구매 확정 권한이 없습니다.");
        }

        if (!isConfirmableStatus(trade.getStatus())) {
            throw new IllegalArgumentException("구매 확정이 가능한 거래 상태가 아닙니다.");
        }

        trade.confirm();
        trade.getPost().changeStatus(PostStatus.SOLD);

        // ── 판매자 포인트 정산: pending → withdrawable ──────────────────────────
        // 결제 완료 시 earnPoint()로 pending에 적립된 거래 대금을
        // 구매 확정 시점에 withdrawable(출금 가능)로 전환한다.
        int tradePrice = trade.getTradePrice();
        PointWallet sellerWallet = pointWalletRepository.findByMemberMemberId(
                trade.getSeller().getMemberId())
                .orElseThrow(() -> new IllegalStateException(
                        "confirmTrade : 판매자 포인트 지갑이 존재하지 않습니다. (sellerId=" + trade.getSeller().getMemberId() + ")"));

        sellerWallet.confirm(tradePrice, tradePrice); // pending -= tradePrice, withdrawable += tradePrice
        pointWalletRepository.save(sellerWallet);

        // 포인트 이력 기록 (EARN 타입, trade와 연결하여 중복 지급 방지)
        pointHistoryRepository.save(PointHistory.builder()
                .pointWallet(sellerWallet)
                .type(PointHistoryType.EARN)
                .changeAmount(tradePrice)
                .balance(sellerWallet.getBalance())
                .trade(trade)
                .build());

        // 구매 확정 시 채팅방에 안내 메시지
        chatService.sendSystemMessage(
                trade.getPost().getPostId(), trade.getBuyer().getMemberId(),
                "[RE:FORM] 구매가 확정되었습니다. 거래가 완료되었습니다. 감사합니다!"
        );
    }

    @Override
    // 거래 수령 방식과 요청자 권한에 맞는 주소 정보를 수정한다.
    public void modifyDelivery(Long requesterId, Long tradeId, DeliveryRequestDTO deliveryRequestDTO) {
        Trade trade = getTrade(tradeId);

        if (!isDeliveryEditableStatus(trade.getStatus())) {
            throw new IllegalArgumentException("현재 거래 상태에서는 배송지 정보를 수정할 수 없습니다.");
        }

        if (trade.getDeliveryType() == TradeDeliveryType.DELIVERY) {
            if (!trade.getBuyer().getMemberId().equals(requesterId)) {
                throw new IllegalArgumentException("배송지 수정 권한이 없습니다.");
            }
        } else if (trade.getDeliveryType() == TradeDeliveryType.DIRECT) {
            if (!trade.getSeller().getMemberId().equals(requesterId)) {
                throw new IllegalArgumentException("직거래 주소 수정 권한이 없습니다.");
            }
        }

        trade.changeDeliveryAddress(deliveryRequestDTO.deliveryAddress());
    }

    @Override
    public void startShipping(Long sellerId, Long tradeId, TradeShippingRequestDTO requestDTO) {
        Trade trade = getTrade(tradeId);

        if (!trade.getSeller().getMemberId().equals(sellerId)) {
            throw new IllegalArgumentException("배송 정보 입력 권한이 없습니다.");
        }
        if (trade.getDeliveryType() != TradeDeliveryType.DELIVERY) {
            throw new IllegalArgumentException("택배 거래만 배송 정보를 입력할 수 있습니다.");
        }
        if (trade.getStatus() != TradeStatus.PAID) {
            throw new IllegalArgumentException("결제 완료된 거래만 배송 정보를 입력할 수 있습니다.");
        }
        if (trade.getDeliveryAddress() == null || trade.getDeliveryAddress().isBlank()) {
            throw new IllegalArgumentException("배송지 정보가 입력된 거래만 배송을 시작할 수 있습니다.");
        }

        String courierName = resolveCourierName(requestDTO.courierCode());
        trade.beginShippingProgress();
        trade.updateShippingInfo(requestDTO.courierCode(), courierName, requestDTO.trackingNumber());

        TradeNotificationTemplateDTO buyerTemplate = notificationService.buildBuyerShippingRegisteredTemplate(
                trade.getTradeId(),
                trade.getPost().getTitle()
        );
        TradeNotificationTemplateDTO sellerTemplate = notificationService.buildSellerShippingRegisteredTemplate(
                trade.getTradeId(),
                trade.getPost().getTitle()
        );

        notificationService.createTradeNotification(trade.getBuyer(), buyerTemplate.content(), buyerTemplate.linkUrl());
        notificationService.createTradeNotification(trade.getSeller(), sellerTemplate.content(), sellerTemplate.linkUrl());

        // 배송 시작 시 채팅방에 안내 메시지
        chatService.sendSystemMessage(
                trade.getPost().getPostId(), trade.getBuyer().getMemberId(),
                "[RE:FORM] 판매자가 배송을 시작했습니다. 택배사: " + courierName
                        + " / 송장번호: " + requestDTO.trackingNumber()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryTrackingTraceResponseDTO readTradeTracking(Long requesterId, Long tradeId) {
        Trade trade = getTrade(tradeId);

        if (!isTradeParticipant(trade, requesterId)) {
            throw new IllegalArgumentException("거래 배송 조회 권한이 없습니다.");
        }
        if (!trade.hasShippingInfo()) {
            throw new IllegalArgumentException("배송 정보가 아직 입력되지 않았습니다.");
        }

        DeliveryTrackingTraceRequestDTO requestDTO = new DeliveryTrackingTraceRequestDTO(
                List.of(new DeliveryTrackingTraceRequestDTO.TraceItemRequestDTO(
                        String.valueOf(trade.getTradeId()),
                        trade.getCourierCode(),
                        trade.getTrackingNumber()
                ))
        );
        return deliveryTrackingService.trace(requestDTO);
    }

    @Override
    // 거래 완료 후 구매자만 판매자에 대한 리뷰를 작성할 수 있게 처리한다.
    public Long addReview(Long buyerId, ReviewRequestDTO reviewRequestDTO) {
        Trade trade = getTrade(reviewRequestDTO.tradeId());

        if (!trade.getBuyer().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("리뷰 작성 권한이 없습니다.");
        }

        if (mannerReviewRepository.existsByTrade_TradeIdAndBuyer_MemberId(reviewRequestDTO.tradeId(), buyerId)) {
            throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
        }

        if (trade.getStatus() != TradeStatus.COMPLETED && trade.getStatus() != TradeStatus.CONFIRMED) {
            throw new IllegalArgumentException("거래 완료 후에만 리뷰를 작성할 수 있습니다.");
        }

        validateReviewWritePeriod(trade);

        MannerReview review = MannerReview.builder()
                .trade(trade)
                .buyer(trade.getBuyer())
                .seller(trade.getSeller())
                .score(reviewRequestDTO.score())
                .content(reviewRequestDTO.content())
                .build();

        MannerReview savedReview = mannerReviewRepository.save(review);
        updateSellerMannerScore(trade.getSeller());

        return savedReview.getMannerId();
    }

    @Override
    @Transactional(readOnly = true)
    // 단건 리뷰를 화면용 응답 DTO로 반환한다.
    public ReviewResponseDTO readReview(Long mannerId) {
        MannerReview review = mannerReviewRepository.findById(mannerId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        return toReviewResponseDTO(review);
    }

    @Override
    @Transactional(readOnly = true)
    // 판매자 기준으로 받은 리뷰 목록을 페이지 단위로 반환한다.
    public PageResponse<ReviewResponseDTO> readSellerReviews(Long sellerId, int page, int size) {
        List<MannerReview> reviews = mannerReviewRepository.findAllBySeller_MemberIdOrderByMannerIdDesc(sellerId);
        List<ReviewResponseDTO> reviewResponseDTOList = new ArrayList<>();

        for (MannerReview review : reviews) {
            reviewResponseDTOList.add(toReviewResponseDTO(review));
        }

        return ServicePageResponse.of(reviewResponseDTOList, page, size);
    }

    // 거래 ID로 거래 엔티티를 조회한다.
    private Trade getTrade(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("거래가 존재하지 않습니다."));
    }

    // 거래 엔티티를 화면용 응답 DTO로 조립한다.
    private TradeResponseDTO mapTradeResponse(Trade trade) {
        boolean hasReview = mannerReviewRepository.existsByTrade_TradeIdAndBuyer_MemberId(
                trade.getTradeId(),
                trade.getBuyer().getMemberId()
        );

        PostBriefDTO postBriefDTO = toPostBriefDTO(trade.getPost());

        MemberBriefDTO buyer = new MemberBriefDTO(
                trade.getBuyer().getMemberId(),
                trade.getBuyer().getNickname(),
                trade.getBuyer().getProfileImageUrl()
        );
        MemberBriefDTO seller = new MemberBriefDTO(
                trade.getSeller().getMemberId(),
                trade.getSeller().getNickname(),
                trade.getSeller().getProfileImageUrl()
        );

        return new TradeResponseDTO(
                trade.getTradeId(),
                postBriefDTO,
                buyer,
                seller,
                trade.getStatus(),
                trade.getDeliveryType(),
                trade.getDeliveryAddress(),
                trade.getCourierCode(),
                trade.getCourierName(),
                trade.getTrackingNumber(),
                trade.getTradePrice(),
                trade.getShippingStartedAt(),
                trade.getCompletedAt(),
                trade.getConfirmedAt(),
                trade.getCreatedAt(),
                hasReview
        );
    }

    // 거래/채팅 화면에서 공통으로 쓰는 판매글 요약 DTO를 생성한다.
    private PostBriefDTO toPostBriefDTO(Post post) {
        List<PostImage> postImages = postImageRepository.findAllByPost_PostIdOrderBySortOrderAsc(post.getPostId());
        String thumbnailUrl = postImages.isEmpty() ? null : postImages.get(0).getImageUrl();

        return new PostBriefDTO(
                post.getPostId(),
                post.getTitle(),
                thumbnailUrl,
                post.getPrice(),
                post.getStatus()
        );
    }

    // 리뷰 엔티티를 화면용 응답 DTO로 변환한다.
    private ReviewResponseDTO toReviewResponseDTO(MannerReview review) {
        MemberBriefDTO buyer = new MemberBriefDTO(
                review.getBuyer().getMemberId(),
                review.getBuyer().getNickname(),
                review.getBuyer().getProfileImageUrl()
        );
        MemberBriefDTO seller = new MemberBriefDTO(
                review.getSeller().getMemberId(),
                review.getSeller().getNickname(),
                review.getSeller().getProfileImageUrl()
        );

        return new ReviewResponseDTO(
                review.getMannerId(),
                review.getTrade().getTradeId(),
                buyer,
                seller,
                (int) Math.round(review.getScore()),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    // 판매글 수령 방식을 거래 도메인 수령 방식 값으로 변환한다.
    private TradeDeliveryType resolveTradeDeliveryType(DeliveryType deliveryType, TradeDeliveryType selectedDeliveryType) {
        return switch (deliveryType) {
            case DIRECT -> {
                if (selectedDeliveryType != null && selectedDeliveryType != TradeDeliveryType.DIRECT) {
                    throw new IllegalArgumentException("직거래만 가능한 판매글입니다.");
                }
                yield TradeDeliveryType.DIRECT;
            }
            case DELIVERY -> {
                if (selectedDeliveryType != null && selectedDeliveryType != TradeDeliveryType.DELIVERY) {
                    throw new IllegalArgumentException("배송만 가능한 판매글입니다.");
                }
                yield TradeDeliveryType.DELIVERY;
            }
            case BOTH -> {
                if (selectedDeliveryType == null) {
                    throw new IllegalArgumentException("수령 방식을 선택해야 합니다.");
                }
                yield selectedDeliveryType;
            }
        };
    }

    // 거래가 구매 확정 가능한 상태인지 확인한다.
    private boolean isConfirmableStatus(TradeStatus status) {
        return status == TradeStatus.PAID
                || status == TradeStatus.IN_PROGRESS
                || status == TradeStatus.RECEIVED;
    }

    // 거래 주소 정보를 수정할 수 있는 상태인지 확인한다.
    private boolean isDeliveryEditableStatus(TradeStatus status) {
        return status == TradeStatus.REQUESTED
                || status == TradeStatus.ACCEPTED
                || status == TradeStatus.PAID;
    }

    private boolean isTradeParticipant(Trade trade, Long memberId) {
        return trade.getBuyer().getMemberId().equals(memberId)
                || trade.getSeller().getMemberId().equals(memberId);
    }

    private void validateReviewWritePeriod(Trade trade) {
        LocalDateTime baseTime = resolveReviewBaseTime(trade);
        if (LocalDateTime.now().isAfter(baseTime.plusDays(REVIEW_WRITE_PERIOD_DAYS))) {
            throw new IllegalArgumentException("리뷰는 거래 완료 후 5일 이내에만 작성할 수 있습니다.");
        }
    }

    private LocalDateTime resolveReviewBaseTime(Trade trade) {
        if (trade.getCompletedAt() != null) {
            return trade.getCompletedAt();
        }
        if (trade.getConfirmedAt() != null) {
            return trade.getConfirmedAt();
        }
        throw new IllegalArgumentException("거래 완료 시각이 없어 리뷰를 작성할 수 없습니다.");
    }

    private void updateSellerMannerScore(Member seller) {
        Double averageScore = mannerReviewRepository.findAverageScoreBySellerId(seller.getMemberId());
        BigDecimal mannerScore = averageScore == null
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(averageScore).setScale(2, RoundingMode.HALF_UP);
        seller.updateMannerScore(mannerScore);
    }

    private String resolveCourierName(String courierCode) {
        DeliveryCourierListResponseDTO courierListResponseDTO = deliveryTrackingService.readCouriers();
        if (courierListResponseDTO == null
                || courierListResponseDTO.data() == null
                || courierListResponseDTO.data().couriers() == null) {
            throw new IllegalArgumentException("택배사 목록을 불러오지 못했습니다.");
        }

        return courierListResponseDTO.data().couriers().stream()
                .filter(courier -> courierCode.equals(courier.trackingApiCode()))
                .map(DeliveryCourierListResponseDTO.CourierDTO::displayName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 택배사 코드입니다."));
    }

    // 거래 상태가 조회 필터 조건과 일치하는지 확인한다.
    private boolean matchesTradeStatus(Trade trade, TradeStatus status) {
        return status == null || trade.getStatus() == status;
    }

}
