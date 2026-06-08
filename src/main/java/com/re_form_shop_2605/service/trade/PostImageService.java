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

    // 임시 업로드 URL을 게시글 전용 경로로 승격하고, 이미 최종 경로인 URL은 그대로 유지한다.
    List<String> finalizePostImageUrls(Long postId, Long memberId, List<String> imageUrls);

    // 원본 이미지 URL에 대응하는 썸네일 URL을 반환한다.
    String getThumbnailUrl(String imageUrl);

    // 게시글 전용 폴더를 삭제
    void deletePostImageDirectory(Long postId);
}
