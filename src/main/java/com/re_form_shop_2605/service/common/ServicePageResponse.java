package com.re_form_shop_2605.service.common;

import com.re_form_shop_2605.dto.common.PageResponse;

import java.util.ArrayList;
import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 페이징 처리 비지니스 로직
 * ─────────────────────────────────────────────────────
 */
public final class ServicePageResponse {

    private ServicePageResponse() {
    }

    public static <T> PageResponse<T> of(List<T> items, int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = size <= 0 ? 10 : size;
        int totalElements = items.size();
        int fromIndex = Math.min((safePage - 1) * safeSize, totalElements);
        int toIndex = Math.min(fromIndex + safeSize, totalElements);
        int responsePage = safePage;

        List<T> content = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            content.add(items.get(i));
        }

        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / safeSize);

        return new PageResponse<>(
                content,
                totalElements,
                totalPages,
                safeSize,
                responsePage,
                safePage == 1,
                totalPages == 0 || safePage >= totalPages
        );
    }
}
