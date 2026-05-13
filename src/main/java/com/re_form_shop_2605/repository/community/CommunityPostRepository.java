package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.community.CommunityPost;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    /* 최근 24시간 이내 게시글 조회 (배치용) */
    @Query("SELECT c FROM CommunityPost c WHERE c.createdAt >= :since " +
            "AND c.status = :status")
    List<CommunityPost> findRecentPosts(
            @Param("since") LocalDateTime since,
            @Param("status") CommunityPostStatus status
    );
}
