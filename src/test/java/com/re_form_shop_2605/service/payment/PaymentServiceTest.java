package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.PaymentInitRequestDTO;
import com.re_form_shop_2605.dto.payment.PaymentInitResponseDTO;
import com.re_form_shop_2605.entity.Enum.PayMethod;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.payment.Payment;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.payment.PaymentRepository;
import com.re_form_shop_2605.repository.payment.TossLogRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 작성자: 손민정
 * 작성일: 2026-05-12
 * 설명: 결제 전 수령 정보 검증 테스트
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TossLogRepository tossLogRepository;
    @Mock
    private WebClient tossWebClient;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    /**
     * 작성자: 손민정
     * 작성일: 2026-05-12
     * 설명: 배송 거래에 주소가 없으면 결제 생성이 실패하는지 검증한다.
     */
    void createPaymentFailsWhenDeliveryTradeHasNoAddress() {
        Trade trade = createTrade(TradeDeliveryType.DELIVERY, null);
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.createPayment(2L, new PaymentInitRequestDTO(1L, PayMethod.Card))
        );

        assertEquals("createPayment : 배송 거래는 결제 전에 배송지 정보가 필요합니다.", exception.getMessage());
    }

    @Test
    /**
     * 작성자: 손민정
     * 작성일: 2026-05-12
     * 설명: 직거래는 결제를 진행할 수 없도록 막히는지 검증한다.
     */
    void createPaymentSucceedsWhenTradeIsDirect() {
        Trade trade = createTrade(TradeDeliveryType.DIRECT, "서울 잠실종합운동장");
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        PaymentInitResponseDTO response = paymentService.createPayment(2L, new PaymentInitRequestDTO(1L, PayMethod.Card));

        assertNotNull(response.tossOrderId());
        assertEquals("테스트 상품", response.orderName());
        assertEquals(10000, response.amount());
    }

    @Test
    /**
     * 작성자: 손민정
     * 작성일: 2026-05-12
     * 설명: 택배 거래에 필요한 정보가 있으면 결제 생성이 성공하는지 검증한다.
     */
    void createPaymentSucceedsWhenRequiredTradeMethodInfoExists() {
        Trade trade = createTrade(TradeDeliveryType.DELIVERY, "서울시 강남구");
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));

        PaymentInitResponseDTO response = paymentService.createPayment(2L, new PaymentInitRequestDTO(1L, PayMethod.Card));

        assertNotNull(response.tossOrderId());
        assertEquals("테스트 상품", response.orderName());
        assertEquals(10000, response.amount());

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        assertEquals(trade, paymentCaptor.getValue().getTrade());
    }

    /**
     * 작성자: 손민정
     * 작성일: 2026-05-12
     * 설명: 결제 테스트에 사용할 거래 엔티티를 생성한다.
     */
    private Trade createTrade(TradeDeliveryType tradeDeliveryType, String deliveryAddress) {
        Member seller = Member.builder()
                .memberId(1L)
                .email("seller@test.com")
                .password("1234")
                .nickname("seller")
                .mannerScore(BigDecimal.ZERO)
                .build();

        Member buyer = Member.builder()
                .memberId(2L)
                .email("buyer@test.com")
                .password("1234")
                .nickname("buyer")
                .mannerScore(BigDecimal.ZERO)
                .build();

        Post post = Post.builder()
                .postId(1L)
                .sellerId(seller)
                .title("테스트 상품")
                .content("content")
                .price(10000)
                .status(PostStatus.RESERVED)
                .build();

        return Trade.builder()
                .tradeId(1L)
                .post(post)
                .buyer(buyer)
                .seller(seller)
                .status(TradeStatus.ACCEPTED)
                .deliveryType(tradeDeliveryType)
                .deliveryAddress(deliveryAddress)
                .tradePrice(10000)
                .build();
    }
}
