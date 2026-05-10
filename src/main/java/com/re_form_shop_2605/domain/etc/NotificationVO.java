package com.re_form_shop_2605.domain.etc;

import com.re_form_shop_2605.entity.Enum.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVO {
    private Long notiId;
    private Long memberId; // 수신자 회원 ID
    private NotificationType type; // 알림 분류
    private String reportContent; // 알림 내용
    private String linkUrl; // 클릭 시 경로
    private boolean isRead; // 읽음 여부
    private LocalDateTime createdAt;
}
