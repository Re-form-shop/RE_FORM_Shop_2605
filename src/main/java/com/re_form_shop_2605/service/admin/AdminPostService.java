package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminPostDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminPostListDTO;
import com.re_form_shop_2605.dto.admin.AdminPostActionRequestDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.PostStatus;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 게시글 목록 조회, 상세 조회, 상태 처리 기능을 담당하는 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface AdminPostService {

    // 키워드와 상태 조건으로 관리자용 게시글 목록을 조회
    PageResponse<AdminPostListDTO> readPosts(String keyword, PostStatus status, int page, int size);

    // 관리자용 게시글 상세 정보를 조회한다.
    AdminPostDetailDTO readPost(Long postId);

    // 관리자 액션에 따라 게시글을 숨김 또는 삭제 상태로 변경한다.
    AdminPostDetailDTO processPost(Long postId, AdminPostActionRequestDTO requestDTO);
}
