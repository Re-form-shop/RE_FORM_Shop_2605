package com.re_form_shop_2605.controller.chat;

import com.re_form_shop_2605.dto.chat.ChatMessageDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomCreateRequestDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomDetailDTO;
import com.re_form_shop_2605.dto.chat.ChatRoomSummaryDTO;
import com.re_form_shop_2605.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
public class ChatRestController {

    private final ChatService chatService;

    // 채팅방 생성 또는 기존 방 반환
    @PostMapping
    public ResponseEntity<ChatRoomDetailDTO> createOrGetChatRoom(
            @RequestBody ChatRoomCreateRequestDTO chatRoomCreateRequestDTO
    ) {
        Long buyerId = 1L; // 임시 -> todo Security 연동 후 @AuthenticationPrincipal로 교체
        return ResponseEntity.ok(chatService.getOrCreateChatRoom(chatRoomCreateRequestDTO.postId(), buyerId));
    }

    // 내 채팅방 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomSummaryDTO>> getMyChatRooms() {
        Long memberId = 1L; // 임시 -> todo Security 연동 후 진짜 memberId 교체
        return ResponseEntity.ok(chatService.getMyChatRooms(memberId));
    }

    // 메시지 이력 조회 (페이징)
    @GetMapping("/{chatId}/message")
    public ResponseEntity<Page<ChatMessageDTO>> getMessages(
            @PathVariable Long chatId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(chatService.getMessages(chatId, pageable));
    }
}
