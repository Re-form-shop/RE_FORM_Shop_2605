package com.re_form_shop_2605.domain.etc;

import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ReportVO {
    private Long reportId;
    private Long reporterId; // 신고자 member id
    private ReportTargetType targetType; // 신고 대상글
    private Long targetId; // 신고 대상 ID
    private ReportReason reason; // 신고 사유
    private String detail; // 상세 내용
    private ReportStatus status; // 신고 처리 상태
    private LocalDateTime createdAt;
}