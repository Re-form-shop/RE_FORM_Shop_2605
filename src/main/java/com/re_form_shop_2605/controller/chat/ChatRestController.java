package com.re_form_shop_2605.controller.chat;

import com.re_form_shop_2605.dto.chat.ChatMessageDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomCreateRequestDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomDetailDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomSummaryDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 채팅방관리 RestController
 * ─────────────────────────────────────────────────────
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "채팅 API", description = "채팅방 생성, 내 채팅방 목록 조회, 메시지 이력 조회 관련 API")
public class ChatRestController {

    private final ChatService chatService;

    // 채팅방 생성 또는 기존 방 반환
    @Operation(
            summary = "채팅방 생성 또는 조회",
            description = "판매글 ID를 기준으로 채팅방을 생성하고, 이미 존재하면 기존 채팅방 정보를 반환합니다."
    )
    @PostMapping
    public ResponseEntity<ChatRoomDetailDTO> createOrGetChatRoom(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestBody ChatRoomCreateRequestDTO chatRoomCreateRequestDTO
    ) {
        return ResponseEntity.ok(
                chatService.getOrCreateChatRoom(chatRoomCreateRequestDTO.postId(), principal.getMemberId())
        );
    }

    // 내 채팅방 목록 조회
    @Operation(
            summary = "내 채팅방 목록 조회",
            description = "현재 로그인한 회원이 참여 중인 채팅방 목록을 최신 순으로 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<ChatRoomSummaryDTO>> getMyChatRooms(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(chatService.getMyChatRooms(principal.getMemberId()));
    }

    // 채팅방 단건 조회
    @Operation(
            summary = "채팅방 상세 조회",
            description = "채팅방 ID로 채팅방 상세 정보(참여자·판매글·거래 상태)와 최근 메시지 이력을 조회합니다."
    )
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatRoomDetailDTO> getChatRoomDetail(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable Long chatId
    ) {
        return ResponseEntity.ok(chatService.getChatRoomDetail(chatId, principal.getMemberId()));
    }

    // 메시지 이력 조회 (페이징)
    @Operation(
            summary = "채팅 메시지 이력 조회",
            description = "채팅방 ID 기준으로 메시지 이력을 페이지 단위로 조회합니다."
    )
    @GetMapping("/{chatId}/message")
    public ResponseEntity<Page<ChatMessageDTO>> getMessages(
            @PathVariable Long chatId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(chatService.getMessages(chatId, pageable));
    }
}
