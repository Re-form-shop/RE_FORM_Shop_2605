package com.re_form_shop_2605.controller.statistics;

import com.re_form_shop_2605.dto.statistics.StatisticsResponseDTO;
import com.re_form_shop_2605.service.statistics.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-27
 * 설명: 메인 화면 통계 조회 컨트롤러
 * ─────────────────────────────────────────────────────
 */

@RestController
@Tag(name = "통계 API", description = "메인 화면 통계값 조회")
@RequestMapping("/api/statistics")
@RequiredArgsConstructor

public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}