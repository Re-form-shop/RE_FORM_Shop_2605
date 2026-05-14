package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: Swagger multipart/form-data 업로드용 판매글 수정 폼 DTO
 * ─────────────────────────────────────────────────────
 */
public class PostUpdateFormDTO {

    // 수정할 제목
    @Size(max = 200)
    private String title;

    // 수정할 본문
    private String content;

    // 수정할 유니폼 등급
    private Grade grade;

    // 수정할 유니폼 사이즈
    @Size(max = 10)
    private String size;

    // 수정할 마킹 여부
    private Boolean marking;

    // 수정할 가격
    @Min(0)
    private Integer price;

    // 수정할 수령 방법
    private DeliveryType deliveryType;

    // 교체 업로드할 이미지 파일 목록
    private List<MultipartFile> images;

    // 서비스 계층에서 사용하는 수정 DTO로 변환한다.
    public PostUpdateRequestDTO toUpdateRequestDTO() {
        return new PostUpdateRequestDTO(
                title,
                content,
                grade,
                size,
                marking,
                price,
                deliveryType
        );
    }
}
