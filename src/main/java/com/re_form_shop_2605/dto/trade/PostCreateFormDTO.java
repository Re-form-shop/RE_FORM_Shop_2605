package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: Swagger multipart/form-data 업로드용 판매글 작성 폼 DTO
 * ─────────────────────────────────────────────────────
 */
public class PostCreateFormDTO {

    // 제목
    @NotBlank
    @Size(max = 200)
    private String title;

    // 본문
    @NotBlank
    private String content;

    // 종목
    @NotNull
    private Sport sport;

    // 구단명
    @NotBlank
    @Size(max = 50)
    private String team;

    // 유니폼명
    @NotBlank
    @Size(max = 200)
    private String uniformName;

    // 유니폼 상태 등급
    @NotNull
    private Grade grade;

    // 유니폼 사이즈
    @Size(max = 10)
    private String size;

    // 마킹 여부
    private Boolean marking;

    // 판매 희망가
    @NotNull
    @Min(0)
    private Integer price;

    // 수령 방법
    @NotNull
    private DeliveryType deliveryType;

    // 업로드할 이미지 파일 목록
    private List<MultipartFile> images;

    // 서비스 계층에서 사용하는 작성 DTO로 변환한다.
    public PostRequestDTO toRequestDTO() {
        return new PostRequestDTO(
                title,
                content,
                sport,
                team,
                uniformName,
                grade,
                size,
                marking,
                price != null ? price : 0,
                deliveryType
        );
    }
}
