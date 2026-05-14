package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.RiskAnalysisResultDTO;
import com.re_form_shop_2605.entity.Enum.RiskLevel;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-14
 * 설명: 위험 탐지 목록 조회 Service
 * ─────────────────────────────────────────────────────
 */
public interface RiskAnalysisService {
    // 위험 게시글 목록 조회
    PageResponse<RiskAnalysisResultDTO> readPostRiskList(RiskLevel riskLevel, int page, int size);

    // 위험 채팅 목록 조회
    PageResponse<RiskAnalysisResultDTO> readChatRiskList(RiskLevel riskLevel, int page, int size);
}
