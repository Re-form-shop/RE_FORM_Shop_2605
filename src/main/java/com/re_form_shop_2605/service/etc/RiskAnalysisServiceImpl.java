package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.AI.RiskAnalysisResultDTO;
import com.re_form_shop_2605.entity.chat.ChatMessage;
import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.AI.RiskAnalysisResult;
import com.re_form_shop_2605.repository.AI.RiskAnalysisResultRepository;
import com.re_form_shop_2605.repository.chat.ChatMessageRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-24
 * 설명: 관리자 위험 게시글/채팅 목록 조회 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    // 위험 탐지 결과 저장소
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;
    // 판매글 저장소
    private final PostRepository postRepository;
    // 채팅 메시지 저장소
    private final ChatMessageRepository chatMessageRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RiskAnalysisResultDTO> readPostRiskList(RiskLevel riskLevel, int page, int size) {
        log.info("[RiskAnalysisService] 위험 게시글 목록 조회 riskLevel={}", riskLevel);

        List<RiskAnalysisResult> list = riskAnalysisResultRepository.findByTargetTypeOrderByCreatedAtDesc(TargetType.POST);

        List<RiskAnalysisResultDTO> result = list.stream()
                .filter(r -> riskLevel == null || r.getRiskLevel() == riskLevel)
                .map(this::toPostRiskDTO)
                .toList();

        return ServicePageResponse.of(result,page,size);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RiskAnalysisResultDTO> readChatRiskList(RiskLevel riskLevel, int page, int size) {
        log.info("[RiskAnalysisService] 위험 채팅 목록 조회 riskLevel={}", riskLevel);

        List<RiskAnalysisResult> list = riskAnalysisResultRepository
                .findByTargetTypeOrderByCreatedAtDesc(TargetType.CHAT);

        List<RiskAnalysisResultDTO> result = list.stream()
                .filter(r -> riskLevel == null || r.getRiskLevel() == riskLevel)
                .map(this::toChatRiskDTO)
                .toList();

        return ServicePageResponse.of(result, page, size);
    }

    private RiskAnalysisResultDTO toPostRiskDTO(RiskAnalysisResult riskAnalysisResult) {
        var post = postRepository.findById(riskAnalysisResult.getTargetId()).orElse(null);

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
                post == null ? null : post.getTitle(),
                post == null ? null : post.getSellerId().getMemberId(),
                post == null ? null : post.getSellerId().getNickname(),
                post == null ? null : post.getStatus()
        );
    }

    private RiskAnalysisResultDTO toChatRiskDTO(RiskAnalysisResult riskAnalysisResult) {
        ChatMessage chatMessage = chatMessageRepository.findById(riskAnalysisResult.getTargetId()).orElse(null);

        return new RiskAnalysisResultDTO(
                riskAnalysisResult.getRiskId(),
                riskAnalysisResult.getTargetType(),
                riskAnalysisResult.getTargetId(),
                riskAnalysisResult.getRiskLevel(),
                riskAnalysisResult.getReason(),
                riskAnalysisResult.getSuggestion(),
                riskAnalysisResult.getCreatedAt(),
                chatMessage == null ? null : chatMessage.getChatRoom().getChatId(),
                chatMessage == null ? null : chatMessage.getChatRoom().getChatId(),
                chatMessage == null ? null : chatMessage.getMessageId(),
                chatMessage == null ? null : chatMessage.getMember().getMemberId(),
                chatMessage == null ? null : chatMessage.getMember().getNickname(),
                chatMessage == null ? null : buildMessagePreview(chatMessage.getContent()),
                null,
                null,
                null,
                null
        );
    }

    private String buildMessagePreview(String content) {
        if (content == null || content.isBlank()) {
            return content;
        }
        return content.length() <= 80 ? content : content.substring(0, 80) + "...";
    }
}
