//package com.re_form_shop_2605.entity.etc;
//
//import com.re_form_shop_2605.entity.BaseEntity;
//import com.re_form_shop_2605.entity.Enum.ReportReason;
//import com.re_form_shop_2605.entity.Enum.ReportStatus;
//import com.re_form_shop_2605.entity.Enum.ReportTargetType;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "report")
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Report extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "report_id")
//    private Long reportId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reporter_id", nullable = false)
//    private Member member; // 신고자 member id
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "target_type", nullable = false)
//    private ReportTargetType targetType; // 신고 대상글
//
//    @Column(name = "target_id", nullable = false)
//    private Long targetId; // 신고 대상 ID
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "reason", nullable = false)
//    private ReportReason reason; // 신고 사유
//
//    @Column(name = "detail", columnDefinition = "TEXT")
//    private String detail; // 상세 내용
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private ReportStatus status; // 신고 처리 상태
//
//    /* 신고 확인 결과 처리 */
//    // 1) 최초 생성 시
//    public Report(Member member, ReportTargetType targetType, Long targetId,
//                  ReportReason reason, String detail) {
//        this.member = member;
//        this.targetType = targetType;
//        this.targetId = targetId;
//        this.reason = reason;
//        this.detail = detail;
//        this.status = ReportStatus.PENDING;
//    }
//
//    // 2) 신고 대상 문제 없음
//    public void normal() {
//        this.status = ReportStatus.NORMAL;
//    }
//
//    // 3) 신고 대상 경고
//    public void warn() {
//        this.status = ReportStatus.WARNING;
//    }
//
//    // 4) 신고 대상 삭제
//    public void delete() {
//        this.status = ReportStatus.DELETED;
//    }
//}
