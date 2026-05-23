package com.re_form_shop_2605.repository.trade.projection;

import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

public interface AdminPostListProjection {
    Long getPostId();
    String getTitle();
    Sport getSport();
    String getTeam();
    int getPrice();
    PostStatus getStatus();
    Long getSellerId();
    String getSellerNickname();
    LocalDateTime getCreatedAt();
}
