package com.re_form_shop_2605.dto.member;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: Swagger multipart/form-data 업로드용 프로필 수정 폼 DTO
 * ─────────────────────────────────────────────────────
 */
public class ProfileUpdateFormDTO {

    // 수정할 닉네임
    @Size(min = 2, max = 20)
    private String nickname;

    // 수정할 자기소개
    @Size(max = 200)
    private String bio;

    // 교체 업로드할 프로필 이미지 파일
    private MultipartFile profileImage;

    // 서비스 계층에서 사용하는 수정 DTO로 변환한다.
    public ProfileUpdateRequestDTO toUpdateRequestDTO() {
        return new ProfileUpdateRequestDTO(
                nickname,
                null,
                bio
        );
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }
}
