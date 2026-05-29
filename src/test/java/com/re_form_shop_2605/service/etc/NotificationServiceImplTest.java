package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.ReFormShop2605Application;
import com.re_form_shop_2605.dto.etc.NotificationResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.NotificationType;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.etc.Notification;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.etc.NotificationRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명:
 */
@Log4j2
@SpringBootTest(classes = ReFormShop2605Application.class)
//@Transactional
class NotificationServiceImplTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void readNotificationsTest() {
        Member member = createMember("read_notifications");
        for (int i = 0; i < 10; i++) {
            notificationRepository.save(Notification.builder()
                    .member(member)
                    .type(NotificationType.SYSTEM)
                    .reportContent("content_" + i)
                    .linkUrl("/test/" + i)
                    .build());
        }

        NotificationResponseDTO responseDTO = notificationService.readNotifications(member.getMemberId(), 0, 10);

        assertEquals(10, responseDTO.items().content().size());
        assertEquals(10, responseDTO.unreadCount());
    }

    @Test
    void modifyNotificationReadTest() {
        Member member = createMember("modify_notification");
        Notification notification = notificationRepository.save(Notification.builder()
                .member(member)
                .type(NotificationType.SYSTEM)
                .reportContent("content")
                .linkUrl("/test")
                .build());

        notificationService.modifyNotificationRead(member.getMemberId(), notification.getNotiId());

        assertFalse(!notificationRepository.findById(notification.getNotiId()).orElseThrow().isRead());
    }

    @Test
    void removeNotificationTest() {
        Member member = createMember("remove_notification");
        Notification notification = notificationRepository.save(Notification.builder()
                .member(member)
                .type(NotificationType.SYSTEM)
                .reportContent("content")
                .linkUrl("/test")
                .build());

        notificationService.removeNotification(notification.getNotiId());

        assertEquals(false, notificationRepository.findById(notification.getNotiId()).isPresent());
    }

    private Member createMember(String prefix) {
        long seed = System.nanoTime();
        Member member = Member.builder()
                .email(prefix + "_" + seed + "@test.com")
                .password("1234")
                .nickname(prefix + "_" + seed)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build();
        return memberRepository.save(member);
    }
}
