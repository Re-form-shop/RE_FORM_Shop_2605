package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import com.re_form_shop_2605.entity.payment.PointRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 출금 요청 저장 및 조회 Repository
 * ─────────────────────────────────────────────────────
 */

@Repository
public interface PointRequestRepository extends JpaRepository<PointRequest, Long> {
    /* 출금 요청 저장/조회 */
    // 1. 특정 회원의 Pending 상태인 출금 요청 조회 (중복 요청 방지 / 사용자 뷰)
    boolean existsByMemberMemberIdAndStatus(Long memberId, PointRequestStatus status);

    // 2. memberId로 출금 요청 목록 최신순 조회
    List<PointRequest> findByMemberMemberIdOrderByCreatedAtDesc(Long memberId);

    // 3. 전체 회원의 Pending 상태 목록 조회 (관리자 뷰)
    List<PointRequest> findByStatusOrderByCreatedAtAsc(PointRequestStatus status);

    long countByStatus(PointRequestStatus status);
}
