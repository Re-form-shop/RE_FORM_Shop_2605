package com.re_form_shop_2605.dto.AI;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.AI.RiskAnalysisResult;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-24
 * 설명: 위험 탐지 목록 반환 DTO
 * ─────────────────────────────────────────────────────
 */
public record RiskAnalysisResultDTO(
        // 위험 탐지 결과 번호
        Long riskId,
        // 대상 타입 (POST / CHAT)
        TargetType targetType,  // POST / CHAT
        // 대상 번호 (messageId 또는 postId)
        Long targetId,          // 대상 (messageId or postId)
        // 위험 등급
        RiskLevel riskLevel,    // 위험 등급 (null = 정상, 값 있음 = 유해)
        // 감지 사유
        String reason,          // 감지 사유 (정상이면 null)
        // 관리자 참고용 제안
        String suggestion,      // ChatGPT 개선 제안 (정상이면 null)
        // 감지 시각
        LocalDateTime createdAt, // 감지 일시
        // 채팅방 이동용 번호
        Long chatRoomId,
        // 기존 호환용 채팅방 번호
        Long chatId,
        // 위험 메시지 번호
        Long messageId,
        // 발신자 회원 번호
        Long senderMemberId,
        // 발신자 닉네임
        String senderNickname,
        // 메시지 미리보기
        String messagePreview,
        // 게시글 제목
        String postTitle,
        // 판매자 회원 번호
        Long sellerMemberId,
        // 판매자 닉네임
        String sellerNickname,
        // 게시글 상태
        PostStatus postStatus
) {
    /* 정상 메시지용 정적 팩토리 메서드 (riskLevel = null -> todo 프론트에서 정상 메시지로 처리) */
    public static RiskAnalysisResultDTO safe() {
        return new RiskAnalysisResultDTO(null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null);
    }

    /* RiskAnalysisResult 엔티티 -> DTO 변환 */
    public static RiskAnalysisResultDTO from(RiskAnalysisResult riskAnalysisResult) {
        return new RiskAnalysisResultDTO(
                riskAnalysisResult.getRiskId(),
                riskAnalysisResult.getTargetType(),
                riskAnalysisResult.getTargetId(),
                riskAnalysisResult.getRiskLevel(),
                riskAnalysisResult.getReason(),
                riskAnalysisResult.getSuggestion(),
                riskAnalysisResult.getCreatedAt(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
