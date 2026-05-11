package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.service.payment.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testViewAllPendingWithdrawList() throws Exception {
        mockMvc.perform(get("/api/admin/withdraws"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void test() throws Exception {
        mockMvc.perform(patch("/api/admin/withdraws/{withdrawId}/action", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                         "action": "APPROVED",
                         "rejectReason": null
                        }
                        """)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

//        mockMvc.perform(patch("/api/admin/withdraws/{withdrawId}/action", 2L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                        {
//                         "action": "REJECTED",
//                         "rejectReason": "거래 완료 후 이의 제기되어 확인 필요함"
//                        }
//                        """)
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk());
    }
}