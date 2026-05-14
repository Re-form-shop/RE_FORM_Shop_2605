package com.re_form_shop_2605.repository.etc;

import com.re_form_shop_2605.entity.etc.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 알림 엔티티에 대한 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByMember_MemberIdOrderByNotiIdDesc(Long memberId);

    int countByMember_MemberIdAndIsReadFalse(Long memberId);

    boolean existsByMember_MemberIdAndTypeAndLinkUrlAndIsReadFalseAndCreatedAtGreaterThanEqual(
            Long memberId,
            com.re_form_shop_2605.entity.Enum.NotificationType type,
            String linkUrl,
            LocalDateTime createdAt
    );
}
