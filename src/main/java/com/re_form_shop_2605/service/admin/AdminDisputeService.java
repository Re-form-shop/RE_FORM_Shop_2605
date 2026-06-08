package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminDisputeDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminDisputeListDTO;
import com.re_form_shop_2605.dto.admin.AdminDisputeAction;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 분쟁 관리 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface AdminDisputeService {

    PageResponse<AdminDisputeListDTO> readDisputes(TradeStatus status, int page, int size);

    AdminDisputeDetailDTO readDispute(Long tradeId);

    AdminDisputeDetailDTO processDispute(Long tradeId, AdminDisputeAction action, String adminMemo, Integer extensionDays, String processedBy);
}
