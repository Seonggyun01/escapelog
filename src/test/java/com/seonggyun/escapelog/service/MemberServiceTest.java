package com.seonggyun.escapelog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.repository.MemberRepository;
import com.seonggyun.escapelog.service.member.MemberService;
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
    @DisplayName("회원 가입에 성공하면 ID가 발급되고 저장된다")
    void join_success() {
        Long id = memberService.join("user01", "passw1!", "닉네임1");

        Member saved = memberRepository.findById(id).orElseThrow();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getLoginId()).isEqualTo("user01");
        assertThat(saved.getPassword()).isEqualTo("passw1!");
        assertThat(saved.getName()).isEqualTo("닉네임1");
    }

    @Test
    @DisplayName("중복 로그인 ID로 가입하면 예외가 발생한다")
    void join_duplicateLoginId() {
        memberService.join("user01", "passw1!", "닉네임1");

        assertThatThrownBy(() -> memberService.join("user01", "passw2!", "닉네임2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 로그인 ID입니다.");
    }

    @Test
    @DisplayName("중복 닉네임으로 가입하면 예외가 발생한다")
    void join_duplicateName() {
        memberService.join("user01", "passw1!", "닉네임1");

        assertThatThrownBy(() -> memberService.join("user02", "passw2!", "닉네임1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 닉네임입니다.");
    }

    @Test
    @DisplayName("전체 회원을 조회할 수 있다")
    void findMembers() {
        memberService.join("user01", "passw1!", "닉네임1");
        memberService.join("user02", "passw2!", "닉네임2");

        List<Member> members = memberService.findMembers();

        assertThat(members).hasSize(2);
        assertThat(members)
                .extracting(Member::getName)
                .containsExactlyInAnyOrder("닉네임1", "닉네임2");
    }

    @Test
    @DisplayName("ID로 단일 회원을 조회할 수 있다")
    void findOne() {
        Long id = memberService.join("user01", "passw1!", "닉네임1");

        Member found = memberService.findOne(id);

        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getName()).isEqualTo("닉네임1");
    }

    @Test
    @DisplayName("닉네임으로 단일 회원을 조회할 수 있다")
    void findByName() {
        memberService.join("user01", "passw1!", "닉네임1");

        Member found = memberService.findByName("닉네임1");

        assertThat(found.getLoginId()).isEqualTo("user01");
        assertThat(found.getName()).isEqualTo("닉네임1");
    }

    @Test
    @DisplayName("loginId로 단일 회원을 조회할 수 있다")
    void findByLoginId() {
        memberService.join("user01", "passw1!", "닉네임1");

        Member found = memberService.findByLoginId("user01");

        assertThat(found.getLoginId()).isEqualTo("user01");
        assertThat(found.getName()).isEqualTo("닉네임1");
    }

    @Test
    @DisplayName("올바른 정보로 로그인하면 Member를 반환한다")
    void login_success() {
        memberService.join("user01", "passw1!", "닉네임1");

        Member loginMember = memberService.login("user01", "passw1!");

        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getLoginId()).isEqualTo("user01");
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인하면 null을 반환한다")
    void login_wrongPassword() {
        memberService.join("user01", "passw1!", "닉네임1");

        Member loginMember = memberService.login("user01", "wrong!");

        assertThat(loginMember).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 로그인 ID로 로그인하면 null을 반환한다")
    void login_notFound() {
        Member loginMember = memberService.login("noUser", "pass");

        assertThat(loginMember).isNull();
    }
}
