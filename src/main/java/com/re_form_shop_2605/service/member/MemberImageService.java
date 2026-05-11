package com.re_form_shop_2605.service.member;

import org.springframework.web.multipart.MultipartFile;

// 회원 프로필 이미지 저장 서비스 인터페이스
public interface MemberImageService {

    // 회원 전용 폴더에 프로필 이미지 파일을 저장
    String saveProfileImage(Long memberId, MultipartFile profileImage);

    // 회원 전용 폴더를 삭제
    void deleteProfileImageDirectory(Long memberId);
}
