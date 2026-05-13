package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.etc.RiskAnalysisResultDTO;
import com.re_form_shop_2605.entity.Enum.ModerationCategory;
import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.etc.RiskAnalysisResult;
import com.re_form_shop_2605.repository.etc.RiskAnalysisResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.moderation.*;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-13
 * 설명: 위험 탐지 AI 구현 Service
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class ModerationService {

    private final ModerationModel moderationModel;
    private final ChatClient.Builder chatClientBuilder; // 개선 제안용 ChatGPT
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;

    /**
     * 채팅 메세지 유해성 검사 후 결과를 DB에 저장하고 RiskAnalysisResultDTO 반환.
     *
     * @param content : 검사할 메시지 내용
     * @param targetType: POST / CHAT 항목 분류
     * @param targetId : 검사 대상자
     * @return RiskAnalysisResultDTO
     * - riskLevel == null -> 정상
     * - riskLevel != null -> 유해 (reason, suggestion 포함)
     */
    public RiskAnalysisResultDTO checkAndSave(String content, TargetType targetType, Long targetId) {
        try {
            // Step 1. Moderation API 호출
            ModerationPrompt prompt = new ModerationPrompt(content); // "이 텍스트에 문제가 있어?"라고 검사할 내용을 담은 질문지
            ModerationResponse response = moderationModel.call(prompt); // 응답
            Moderation moderation = response.getResult().getOutput(); // 응답에서 실제 모더레이션 결과 객체를 꺼냄.

            boolean flagged = moderation.getResults().getFirst().isFlagged(); // 유해 콘텐츠로 판정되었는지 여부 (false: 정상 콘텐츠)
            log.info("[Moderation] flagged={}, targetType={}, targetId={}", flagged, targetType, targetId);

            // Step 2. 정상적인 메시지 -> 즉시 반환 (DB 저장 불필요)
            if (!flagged) {
                return RiskAnalysisResultDTO.safe();
            }

            // Step 3. 위반 카테고리 분석
            // Categories 객체를 Map<String, Object>로 변환
            Categories categories = moderation.getResults().getFirst().getCategories();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(categories, new TypeReference<>() {});

            // true인 카테고리만 필터링 -> ModerationCategory 매핑
            List<ModerationCategory> matched = new ArrayList<>();
            map.forEach((key, value) -> {
                if ((boolean) value) {
                    ModerationCategory.fromCode(key).ifPresent(category -> {
                        matched.add(category);
                        log.info("[Moderation] 위반 카테고리={}", key);
                    });
                }
            });

            // Step 4. 최고 위험 등급 결정
            // RiskLevel.ordinal() : LOW=0, MID=1, HIGH=2
            // 가장 높은 ordinal = 가장 심각한 등급
            RiskLevel maxRiskLevel = matched.stream()
                    .map(ModerationCategory::getRiskLevel)
                    .max((a, b) -> a.ordinal() - b.ordinal())
                    .orElse(RiskLevel.LOW); // ModerationCategory enum에 없어도 flagged=true면 todo LOW 상의 (MID로 할지!)

            // Step 5. reason 문자열 생성
            // 예) "폭력 표현, 위협적인 표현, "
            String reason = matched.stream()
                    .map(ModerationCategory::getMessage)
                    .reduce("", (acc, msg) -> acc + msg + ", ");

            // Step 6. ChatGPT 개선 제안 요청
            String suggestion = generateSuggestion(content, reason, targetType);

            // Step 7. risk_analysis_result 테이블 저장
            RiskAnalysisResult saved = riskAnalysisResultRepository.save(
                    RiskAnalysisResult.builder()
                            .targetType(targetType)
                            .targetId(targetId)
                            .riskLevel(maxRiskLevel)
                            .reason(reason)
                            .suggestion(suggestion)
                            .build()
            );
            log.info("[Moderation] DB 저장 완료 riskLevel={}, targetType={}, targetId={}", maxRiskLevel, targetType, targetId);

            // Step 8. RiskAnalysisResultDTO 반환
            // from()으로 저장된 엔티티를 DTO로 변환
            // riskId, createdAt 등 DB 생성 값까지 포함됨
            return RiskAnalysisResultDTO.from(saved);

        } catch (Exception e) {
            // fallback API 장애 시 -> 정상 처리
            log.error("[Moderation] API 호출 실패 -> fallback: {}", e.getMessage());
            return RiskAnalysisResultDTO.safe();
        }
    }

    /**
     * ChatGPT에게 유해 메시지 개선 제안 요청.
     *
     * @param originalContent : 원본 유해 메시지
     * @param reason : 감지된 카테고리 사유
     * @return 개선 제안 문자열 (API 실패 시 null)
     */
    private String generateSuggestion(String originalContent, String reason, TargetType targetType) {
        try {
            // targetType에 따라 "채팅 메시지" / "게시글" 표현을 다르게 사용
            String targetLabel = targetType == TargetType.CHAT ? "채팅 메시지" : "게시글";

            String userPrompt = String.format(
                            "다음 %s는 '%s' 이유로 유해하다고 판단되었습니다.\n" +
                            "원본 내용: \"%s\"\n" +
                            "이 내용을 작성한 사람에게 어떻게 표현을 바꾸면 좋을지 " +
                            "한국어로 1~2문장으로 간결하게 개선 제안을 해주세요.",
                            targetLabel,
                            reason.stripTrailing().replaceAll(", $", ""),
                            originalContent
            );

            String suggestion = chatClientBuilder.build()
                    .prompt()
                    .user(userPrompt)
                    .call()
                    .content();
            log.info("[Moderation] 개선 제안 생성 완료: {}", suggestion);
            return suggestion;
        } catch (Exception e) {
            // 개선 제안 실패 시 null 반환 todo 프론트와 상의!
            log.error("[Moderation] 개선 제안 생성 실패: {}", e.getMessage());
            return null;
        }
    }
}
