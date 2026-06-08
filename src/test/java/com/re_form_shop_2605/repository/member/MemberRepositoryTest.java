package com.re_form_shop_2605.repository.member;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.member.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Test
    void insert() {
        String seed = String.valueOf(System.nanoTime());
        Member  member = Member.builder()
                .email("member-" + seed + "@gmail.com")
                .password("1234")
                .nickname("tom-" + seed)
                .profileImageUrl("https://avatars.githubusercontent.com/u/2605?v=4")
                .bio("Hi")
                .mannerScore(BigDecimal.valueOf(2))
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build();
        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember.getMemberId());
        assertEquals(member.getEmail(), savedMember.getEmail());
    }

    @Test
    void findById() {
        String seed = String.valueOf(System.nanoTime());
        Member savedMember = memberRepository.save(Member.builder()
                .email("find-" + seed + "@gmail.com")
                .password("1234")
                .nickname("member-" + seed)
                .profileImageUrl("https://avatars.githubusercontent.com/u/2605?v=4")
                .bio("Hi")
                .mannerScore(BigDecimal.valueOf(2))
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build());

        Member member = memberRepository.findById(savedMember.getMemberId()).orElseThrow();

        assertEquals(savedMember.getMemberId(), member.getMemberId());
        assertEquals(savedMember.getEmail(), member.getEmail());
    }

}
