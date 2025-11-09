package com.seonggyun.escapelog.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void join() {
        // given
        String name = "홍성균";

        // when
        Long savedId = memberService.join(name);
        Member found = memberRepository.findById(savedId).orElseThrow();

        // then
        assertThat(found.getName()).isEqualTo(name);
        assertThat(found.getId()).isNotNull();
    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    void findMembers() {
        // given
        memberService.join("철수");
        memberService.join("영희");

        // when
        List<Member> members = memberService.findMembers();

        // then
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("단일 회원 조회 테스트")
    void findOne() {
        // given
        Long id = memberService.join("성균");

        // when
        Member found = memberService.findOne(id);

        // then
        assertThat(found.getName()).isEqualTo("성균");
    }

    @Test
    @DisplayName("이름으로 회원 조회 테스트")
    void findByName() {
        // given
        memberService.join("재윤");
        memberService.join("재윤"); // 같은 이름 두 명

        // when
        List<Member> result = memberService.findByName("재윤");

        // then
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(m -> m.getName().equals("재윤"));
    }
}
