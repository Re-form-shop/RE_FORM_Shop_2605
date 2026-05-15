package com.re_form_shop_2605.domain.community;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyVO {
    private Long replyId;
    private Long postId;
    private Long memberId;
    private Long parentId; // 대댓글의 부모 댓글
    private String replyContent; // 댓글 내용
    private Boolean isDeleted;
    private int likeCount;
    private LocalDateTime createdAt;
}
