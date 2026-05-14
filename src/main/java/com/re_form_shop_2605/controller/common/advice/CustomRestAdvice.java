package com.re_form_shop_2605.controller.common.advice;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.security.AuthException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 유효성 검사 및 예외처리 관련 공통 로직을 담당하는 @RestControllerAdvice 클래스
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@RestControllerAdvice
public class CustomRestAdvice {

    // @ModelAttribute 바인딩 중 발생한 검증 오류를 공통 응답 형태로 반환
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBindException(BindException ex) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        if(ex.hasErrors()) {
            BindingResult bindingResult = ex.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorsMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        return ResponseEntity.badRequest().body(ApiResponse.fail("유효성 검사에 실패했습니다.", errorsMap));
    }

    // @RequestBody 검증 실패 시 필드별 오류를 공통 응답 형태로 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorsMap.put(fieldError.getField(), fieldError.getCode());
        });

        return ResponseEntity.badRequest().body(ApiResponse.fail("유효성 검사에 실패했습니다.", errorsMap));
    }

    // 로그인/토큰/세션 같은 인증 도메인 예외는 보안 정책에 맞춰 403으로 통일한다.
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleAuthException(AuthException ex) {
        log.warn(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(ex.getMessage(), errorsMap));
    }

    // Spring Security 인증 실패 예외도 브라우저/Swagger에서 동일한 403 응답으로 보이게 맞춘다.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleAuthenticationException(AuthenticationException ex) {
        log.warn(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail("인증 처리에 실패했습니다.", errorsMap));
    }

    // 서비스나 컨트롤러에서 발생한 일반 잘못된 요청 예외를 400으로 반환
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(ApiResponse.fail(ex.getMessage(), errorsMap));
    }

    // FK, unique 제약 등 데이터 무결성 예외를 409로 반환
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleFKException(DataIntegrityViolationException ex) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", "foreign key constraint fails");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail("데이터 무결성 제약에 위배되었습니다.", errorsMap));

    }

    // 조회 결과가 없을 때 404 응답으로 변환
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleNoRnoException(NoSuchElementException ex) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", "No value present");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("요청한 데이터를 찾을 수 없습니다.", errorsMap));
    }

    // 위에서 처리하지 못한 예외를 500 공통 응답으로 반환
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleException(Exception ex) {
        log.error(ex);

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("서버 내부 오류가 발생했습니다.", errorsMap));
    }
}
