package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.RiskAnalysisResultDTO;
import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.etc.RiskAnalysisResult;
import com.re_form_shop_2605.repository.etc.RiskAnalysisResultRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RiskAnalysisResultDTO> readPostRiskList(RiskLevel riskLevel, int page, int size) {
        log.info("[RiskAnalysisService] 위험 게시글 목록 조회 riskLevel={}", riskLevel);

        List<RiskAnalysisResult> list = riskAnalysisResultRepository.findByTargetTypeOrderByCreatedAtDesc(TargetType.POST);

        List<RiskAnalysisResultDTO> result = list.stream()
                .filter(r -> riskLevel == null || r.getRiskLevel() == riskLevel)
                .map(RiskAnalysisResultDTO::from)
                .toList();

        return ServicePageResponse.of(result,page,size);
    }

    @Override
    public PageResponse<RiskAnalysisResultDTO> readChatRiskList(RiskLevel riskLevel, int page, int size) {
        log.info("[RiskAnalysisService] 위험 채팅 목록 조회 riskLevel={}", riskLevel);

        List<RiskAnalysisResult> list = riskAnalysisResultRepository
                .findByTargetTypeOrderByCreatedAtDesc(TargetType.CHAT);

        List<RiskAnalysisResultDTO> result = list.stream()
                .filter(r -> riskLevel == null || r.getRiskLevel() == riskLevel)
                .map(RiskAnalysisResultDTO::from)
                .toList();

        return ServicePageResponse.of(result, page, size);
    }
}
