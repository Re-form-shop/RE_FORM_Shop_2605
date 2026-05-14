package com.re_form_shop_2605.service.member;

import org.springframework.web.multipart.MultipartFile;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 회원 프로필 이미지 저장 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
// 회원 프로필 이미지 저장 서비스 인터페이스
public interface MemberImageService {

    // 회원 전용 폴더에 프로필 이미지 파일을 저장
    String saveProfileImage(Long memberId, MultipartFile profileImage);

    // 프로필 저장 전에 임시 업로드한 프로필 이미지를 저장
    String saveTemporaryProfileImage(Long memberId, MultipartFile profileImage);

    // 임시 업로드 이미지를 실제 프로필 이미지 경로로 이동
    String promoteTemporaryProfileImage(Long memberId, String profileImageUrl);

    // 회원 전용 폴더를 삭제
    void deleteProfileImageDirectory(Long memberId);
}
