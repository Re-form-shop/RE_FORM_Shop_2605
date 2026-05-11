package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import com.re_form_shop_2605.entity.payment.PointRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRequestRepository extends JpaRepository<PointRequest, Long> {
    /* 출금 요청 저장/조회 */
    // 1. Pending 상태인 출금 요청 조회 (중복 요청 방지)
    boolean existsByMemberMemberIdAndStatus(Long memberId, PointRequestStatus status);

    // 2. memberId로 출금 요청 목록 최신순 조회
    List<PointRequest> findByMemberMemberIdOrderByCreatedAtDesc(Long memberId);
}