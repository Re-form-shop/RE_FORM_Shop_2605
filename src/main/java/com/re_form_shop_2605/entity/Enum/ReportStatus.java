package com.re_form_shop_2605.entity.Enum;

public enum ReportStatus {
    /*
    신고 처리 상태
    - PENDING : 신고 접수
    - NORMAL : 문제 없음
    - WARNING : 문제 발견
    - DELETED : 문제 심각, 게시글 삭제
     */
    PENDING, NORMAL, WARNING, DELETED
}