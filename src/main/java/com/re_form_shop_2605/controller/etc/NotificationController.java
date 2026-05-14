package com.re_form_shop_2605.controller.etc;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.NotificationDTO;
import com.re_form_shop_2605.dto.etc.NotificationResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.etc.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 사용자 알림 조회와 읽음 처리를 담당하는 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/notifications")
@Tag(name = "알림 API", description = "사용자 알림 조회와 읽음 처리를 위한 API")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /api/notifications
    // 알림 목록과 읽지 않은 개수를 함께 조회
    @Operation(
            summary = "알림 목록 조회",
            description = "현재 회원의 알림 목록과 읽지 않은 알림 개수를 함께 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<NotificationPageResponse>> readNotifications(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        NotificationResponseDTO responseDTO = notificationService.readNotifications(principal.getMemberId(), page, size);
        NotificationPageResponse response = new NotificationPageResponse(responseDTO.items(), responseDTO.unreadCount());
        return ResponseEntity.ok(ApiResponse.ok(response, "알림 목록 조회 완료"));
    }

    // PATCH /api/notifications/{id}/read
    // 특정 알림을 읽음 상태로 변경
    @Operation(
            summary = "알림 읽음 처리",
            description = "알림 ID에 해당하는 알림을 읽음 상태로 변경합니다."
    )
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<ReadNotificationResponse>> readNotification(
            @PathVariable("id") Long notiId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        notificationService.modifyNotificationRead(principal.getMemberId(), notiId);
        return ResponseEntity.ok(ApiResponse.ok(new ReadNotificationResponse(notiId, true), "알림 읽음 처리 완료"));
    }

    // PATCH /api/notifications/read-all
    // 현재 사용자의 모든 미확인 알림을 읽음 상태로 변경
    @Operation(
            summary = "전체 알림 읽음 처리",
            description = "현재 회원의 읽지 않은 알림 전체를 읽음 상태로 변경합니다."
    )
    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<UpdatedCountResponse>> readAllNotifications(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        NotificationResponseDTO responseDTO = notificationService.readNotifications(principal.getMemberId(), 0, Integer.MAX_VALUE);
        int updatedCount = 0;

        for (NotificationDTO item : responseDTO.items().content()) {
            if (!item.isRead()) {
                notificationService.modifyNotificationRead(principal.getMemberId(), item.notiId());
                updatedCount++;
            }
        }

        return ResponseEntity.ok(ApiResponse.ok(new UpdatedCountResponse(updatedCount), "전체 알림 읽음 처리 완료"));
    }

    // 페이징 처리 응답
    public record NotificationPageResponse(PageResponse<NotificationDTO> content, int unreadCount) {
    }

    // 읽음 처리 응답
    public record ReadNotificationResponse(Long id, boolean isRead) {
    }

    // 업데이트 된 응답
    public record UpdatedCountResponse(int updatedCount) {
    }
}
