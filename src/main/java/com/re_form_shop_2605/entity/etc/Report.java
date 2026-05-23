package com.re_form_shop_2605.entity.etc;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 신고 접수 및 관리자 처리 기록 JPA 엔티티
 * ─────────────────────────────────────────────────────
 */
public class Report extends BaseEntity {
    // 신고 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    // 신고자 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member member;

    // 신고 대상 타입
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ReportTargetType targetType;

    // 신고 대상 번호
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // 신고 사유
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private ReportReason reason;

    // 신고 상세 내용
    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    // 신고 처리 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReportStatus status;

    // 관리자 처리 시각
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    // 처리 관리자 닉네임
    @Column(name = "processed_by", length = 100)
    private String processedBy;

    // 관리자 처리 메모
    @Column(name = "admin_memo", columnDefinition = "TEXT")
    private String adminMemo;

    // 판매자 소명
    @Column(name = "seller_claim", columnDefinition = "TEXT")
    private String sellerClaim;

    // 마지막 분쟁 처리 결과
    @Column(name = "dispute_resolution_type", length = 50)
    private String disputeResolutionType;

    // 분쟁 연장 종료 시각
    @Column(name = "extended_until")
    private LocalDateTime extendedUntil;

    // 신고 최초 생성
    public Report(Member member, ReportTargetType targetType, Long targetId,
                  ReportReason reason, String detail) {
        this.member = member;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.detail = detail;
        this.status = ReportStatus.PENDING;
    }

    // 신고 대상 문제 없음 처리
    public void normal(String adminMemo, String processedBy) {
        this.status = ReportStatus.NORMAL;
        recordAdminReview(adminMemo, processedBy);
    }

    // 신고 대상 경고 처리
    public void warn(String adminMemo, String processedBy) {
        this.status = ReportStatus.WARNING;
        recordAdminReview(adminMemo, processedBy);
    }

    // 신고 대상 삭제 처리
    public void delete(String adminMemo, String processedBy) {
        this.status = ReportStatus.DELETED;
        recordAdminReview(adminMemo, processedBy);
    }

    // 관리자 처리 공통 메타데이터 기록
    public void recordAdminReview(String adminMemo, String processedBy) {
        this.processedAt = LocalDateTime.now();
        this.processedBy = processedBy;
        this.adminMemo = adminMemo;
    }

    // 분쟁 처리 결과와 연장 기한을 함께 기록
    public void recordDisputeReview(String adminMemo, String processedBy, String resolutionType, LocalDateTime extendedUntil) {
        recordAdminReview(adminMemo, processedBy);
        this.disputeResolutionType = resolutionType;
        this.extendedUntil = extendedUntil;
    }
}
