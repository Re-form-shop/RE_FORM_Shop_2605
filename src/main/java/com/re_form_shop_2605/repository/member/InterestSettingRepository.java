package com.re_form_shop_2605.repository.member;

import com.re_form_shop_2605.entity.member.InterestSetting;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 종목 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface InterestSettingRepository extends JpaRepository<InterestSetting,Long> {
}
