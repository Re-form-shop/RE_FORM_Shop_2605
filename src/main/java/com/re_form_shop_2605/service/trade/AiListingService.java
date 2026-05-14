package com.re_form_shop_2605.service.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_form_shop_2605.dto.trade.AiListingSuggestResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-14
 * 설명: 판매글 작성 시 이미지를 AI로 분석해 제목/설명을 제안하는 서비스
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
public class AiListingService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AiListingService(ChatClient.Builder clientBuilder) {
        this.chatClient = clientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 이미지를 분석하여 판매글 제목과 설명을 제안
     *
     * @param contentType: 이미지 MIME 타입 (예: "image/jpeg")
     * @param bytes: 이미지 바이트 배열
     * @return AiListingSuggestResponseDTO (title, description)
     */
    public AiListingSuggestResponseDTO suggestFromImage(String contentType, byte[] bytes) {
        // 1. 시스템 메시지 생성
        SystemMessage systemMessage = SystemMessage.builder().text("""
                당신은 중고 스포츠 유니폼 판매 전문가입니다.
                사용자가 업로드한 상품 이미지를 보고,
                중고 거래 플랫폼에 올릴 판매글 제목과 설명을 한국어로 작성해주세요.
                반드시 아래 JSON 형식만 출력하고, 다른 설명은 절대 하지 마세요.
                {
                  "title": "판매글 제목 (20자 이내, 종목·브랜드·사이즈 포함)",
                  "description": "판매글 설명 (150자 이내, 상태·특징·사이즈 포함)"
                }
                """).build();

        // 2. 미디어 생성
        Media media = Media.builder().mimeType(MimeType.valueOf(contentType)).data(bytes).build();

        // 3. 유저 메시지 생성
        UserMessage userMessage = UserMessage.builder().text("이 이미지를 분석해서 판매글 제목과 설명을 JSON으로 작성해주세요.").media(media).build();

        // 4. 프롬프트 생성
        Prompt prompt = Prompt.builder().messages(systemMessage, userMessage).build();

        // 5. LLM 호출
        // 제목/설명은 완성된 JSON을 파싱해야 하므로 .call() 사용
        String rawJson = chatClient.prompt(prompt)
                .call()
                .content();
        log.info("[AiListingService] AI 원본 응답: {}", rawJson);

        // 6. JSON 파싱 후 DTO 반환
        return parseToDto(rawJson);
    }

    /**
     * AI 응답 문자열에서 JSON을 추출해 DTO로 변환
     * AI가 마크다운 코드블록(```json ... ```)으로 감쌀 수 있으므로 제거 처리 포함
     */
    private AiListingSuggestResponseDTO parseToDto(String raw) {
        try {
            // 마크다운 코드블록 제거 후 파싱
            String cleaned = raw
                    .replaceAll("(?s)```json\\s*", "")
                    .replaceAll("(?s)```\\s*", "")
                    .trim();

            JsonNode node = objectMapper.readTree(cleaned);
            String title = node.get("title").asText(""); // 자바형태로 변경
            String description = node.get("description").asText(""); // 자바형태로 변경
            return new AiListingSuggestResponseDTO(title, description);

        } catch (Exception e) {
            log.error("[AiListingService] JSON 파싱 실패: {}", e.getMessage());
            return AiListingSuggestResponseDTO.fallback(); // fallback
        }
    }


}
