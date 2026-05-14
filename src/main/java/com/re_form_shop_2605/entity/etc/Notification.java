package com.re_form_shop_2605.entity.etc;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.Enum.NotificationType;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 알림 JPA 엔티티
 * ─────────────────────────────────────────────────────
 */
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long notiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 수신자 회원 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type; // 알림 분류

    @Column(name = "report_content", nullable = false, length = 300)
    private String reportContent; // 알림 내용

    @Column(name = "link_url", length = 300)
    private String linkUrl; // 클릭 시 경로

    @Column(name = "is_read", nullable = false)
    private boolean isRead; // 읽음 여부

    /* 알림 상태 변경 */
    // 1) 최초 생성 시
    @Builder
    public Notification(Member member, NotificationType type, String reportContent, String linkUrl) {
        this.member = member;
        this.type = type;
        this.reportContent = reportContent;
        this.linkUrl = linkUrl;
        this.isRead = false;
    }

    // 2) 읽음 처리
    public void read() {
        this.isRead = true;
    }
}
