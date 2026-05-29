package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.PaymentInitResponseDTO;
import com.re_form_shop_2605.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private static final String TEST_PRINCIPAL_ATTR = "testPrincipal";

    @Mock
    private PaymentService paymentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PaymentController(paymentService))
                .setCustomArgumentResolvers(new TestAuthenticationPrincipalResolver())
                .build();
    }

    @Test
    void testPaymentInit() throws Exception {
        given(paymentService.createPayment(eq(1L), any()))
                .willReturn(new PaymentInitResponseDTO("order-123", "테스트 유니폼", 15000));

        mockMvc.perform(post("/api/payments/init")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .requestAttr(TEST_PRINCIPAL_ATTR, principal(1L))
                        .content("""
                        {
                            "tradeId": 1,
                            "payMethod": "Card"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tossOrderId").value("order-123"))
                .andExpect(jsonPath("$.amount").value(15000));
    }

    private MemberSecurityDTO principal(Long memberId) {
        return new MemberSecurityDTO(
                memberId,
                "user" + memberId,
                "password",
                "user" + memberId + "@test.com",
                "tester",
                null,
                false,
                "LOCAL",
                List.of()
        );
    }

    static class TestAuthenticationPrincipalResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(AuthenticationPrincipal.class)
                    && MemberSecurityDTO.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public Object resolveArgument(MethodParameter parameter,
                                      ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest,
                                      WebDataBinderFactory binderFactory) {
            return webRequest.getAttribute(TEST_PRINCIPAL_ATTR, NativeWebRequest.SCOPE_REQUEST);
        }
    }
}
