package com.re_form_shop_2605.service.trade;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 판매글 이미지 저장 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface PostImageService {

    // 게시글 전용 폴더에 이미지 파일을 저장
    List<String> savePostImages(Long postId, List<MultipartFile> images);

    // 판매글 작성 전에 임시 업로드한 이미지 파일을 저장
    List<String> saveTemporaryPostImages(Long memberId, List<MultipartFile> images);

    // 게시글 전용 폴더를 삭제
    void deletePostImageDirectory(Long postId);
}
