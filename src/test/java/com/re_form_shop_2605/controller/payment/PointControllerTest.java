package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import com.re_form_shop_2605.service.payment.PointService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    private static final String TEST_PRINCIPAL_ATTR = "testPrincipal";

    @Mock
    private PointService pointService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PointController(pointService))
                .setCustomArgumentResolvers(new TestAuthenticationPrincipalResolver())
                .build();
    }

    @Test
    void testViewPointWallet() throws Exception {
        given(pointService.getPointWallet(1L))
                .willReturn(new PointWalletResponseDTO(50000, 30000, 20000));

        mockMvc.perform(get("/api/users/me/points")
                        .requestAttr(TEST_PRINCIPAL_ATTR, principal(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(50000));
    }

    @Test
    void testWithdraw() throws Exception {
        given(pointService.requestWithdraw(eq(3L), any()))
                .willReturn(new WithdrawResponseDTO(
                        10L,
                        10000,
                        "은행3",
                        "333-33-333333",
                        PointRequestStatus.PENDING,
                        LocalDateTime.of(2026, 5, 27, 12, 0)
                ));

        mockMvc.perform(post("/api/users/me/points/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .requestAttr(TEST_PRINCIPAL_ATTR, principal(3L))
                        .content("""
                        {
                            "requestAmount": 10000,
                            "bankName": "은행3",
                            "accountNumber": "333-33-333333",
                            "bankCode": "003",
                            "holderInfo": "900101"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.withdrawId").value(10));
    }

    @Test
    void testGetWithdrawList() throws Exception {
        given(pointService.getMemberRequestWithdrawList(2L))
                .willReturn(List.of(
                        new WithdrawResponseDTO(
                                20L,
                                15000,
                                "은행2",
                                "222-22-222222",
                                PointRequestStatus.PENDING,
                                LocalDateTime.of(2026, 5, 27, 13, 0)
                        )
                ));

        mockMvc.perform(get("/api/users/me/points/withdraw")
                        .requestAttr(TEST_PRINCIPAL_ATTR, principal(2L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].withdrawId").value(20));
    }

    @Test
    void testCancelWithdraw() throws Exception {
        willDoNothing().given(pointService).cancelWithdraw(3L, 6L);

        mockMvc.perform(delete("/api/users/me/points/withdraw/{withdrawId}", 6L)
                        .with(csrf())
                        .requestAttr(TEST_PRINCIPAL_ATTR, principal(3L)))
                .andDo(print())
                .andExpect(status().isNoContent());
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
