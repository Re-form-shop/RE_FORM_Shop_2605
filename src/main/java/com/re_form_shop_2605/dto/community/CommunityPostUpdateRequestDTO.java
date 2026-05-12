package com.re_form_shop_2605.dto.community;

import jakarta.validation.constraints.Size;

// PUT /api/community/{commId}
public record CommunityPostUpdateRequestDTO(
        @Size(max = 200)
        String commTitle,

        String commContent,

        @Size(max = 500)
        String commImageUrl
) {}
