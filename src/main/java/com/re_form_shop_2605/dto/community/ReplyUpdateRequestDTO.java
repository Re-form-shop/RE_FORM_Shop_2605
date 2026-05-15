package com.re_form_shop_2605.dto.community;

import jakarta.validation.constraints.NotBlank;

// PUT /api/community/replies/{replyId}
public class ReplyUpdateRequestDTO {
    @NotBlank
    String replyContent;
}
