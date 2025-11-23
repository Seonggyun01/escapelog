package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seonggyun.escapelog.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("유효한 loginId, password, name으로 Member 생성에 성공한다")
    void createMember_success() {
        Member member = new Member("user01", "passw1!", "홍성균");

        assertThat(member.getLoginId()).isEqualTo("user01");
        assertThat(member.getPassword()).isEqualTo("passw1!");
        assertThat(member.getName()).isEqualTo("홍성균");
    }

    @Test
    @DisplayName("loginId가 null이면 예외가 발생한다")
    void loginId_null_throwsException() {
        assertThatThrownBy(() -> new Member(null, "passw1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로그인 ID는 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("loginId가 공백이면 예외가 발생한다")
    void loginId_blank_throwsException() {
        assertThatThrownBy(() -> new Member("   ", "passw1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로그인 ID는 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("loginId가 4자 미만이면 예외가 발생한다")
    void loginId_tooShort_throwsException() {
        assertThatThrownBy(() -> new Member("abc", "passw1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4~20자");
    }

    @Test
    @DisplayName("loginId가 20자를 초과하면 예외가 발생한다")
    void loginId_tooLong_throwsException() {
        String longId = "a".repeat(21);
        assertThatThrownBy(() -> new Member(longId, "passw1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4~20자");
    }

    @Test
    @DisplayName("loginId에 허용되지 않은 문자가 포함되면 예외가 발생한다")
    void loginId_invalidCharacters_throwsException() {
        assertThatThrownBy(() -> new Member("user @", "passw1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("로그인 ID는 영문, 숫자, 특수문자");
    }

    @Test
    @DisplayName("허용된 특수문자를 포함한 loginId는 생성에 성공한다")
    void loginId_withAllowedSpecialChars_success() {
        Member member = new Member("user!@#-", "passw1!", "홍성균");
        assertThat(member.getLoginId()).isEqualTo("user!@#-");
    }

    @Test
    @DisplayName("password가 null이면 예외가 발생한다")
    void password_null_throwsException() {
        assertThatThrownBy(() -> new Member("user01", null, "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("password가 공백이면 예외가 발생한다")
    void password_blank_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "   ", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("password가 6자 미만이면 예외가 발생한다")
    void password_tooShort_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "abc1!", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("6~20자");
    }

    @Test
    @DisplayName("password가 20자를 초과하면 예외가 발생한다")
    void password_tooLong_throwsException() {
        String longPw = "a".repeat(21);
        assertThatThrownBy(() -> new Member("user01", longPw, "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("6~20자");
    }

    @Test
    @DisplayName("password에 허용되지 않은 문자가 포함되면 예외가 발생한다")
    void password_invalidCharacters_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "pass word", "홍성균"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 영문, 숫자, 특수문자");
    }

    @Test
    @DisplayName("허용된 특수문자를 포함한 password는 생성에 성공한다")
    void password_withAllowedSpecialChars_success() {
        Member member = new Member("user01", "P@ssw0rd!", "홍성균");
        assertThat(member.getPassword()).isEqualTo("P@ssw0rd!");
    }

    @Test
    @DisplayName("name이 null이면 예외가 발생한다")
    void name_null_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "passw1!", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("name이 공백이면 예외가 발생한다")
    void name_blank_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "passw1!", "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("name이 50자를 초과하면 예외가 발생한다")
    void name_tooLong_throwsException() {
        String longName = "a".repeat(51);
        assertThatThrownBy(() -> new Member("user01", "passw1!", longName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("50자 이하");
    }

    @Test
    @DisplayName("name에 특수문자가 포함되면 예외가 발생한다")
    void name_withSpecialChars_throwsException() {
        assertThatThrownBy(() -> new Member("user01", "passw1!", "홍성균!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에는 한글, 영어, 숫자만 사용할 수 있습니다.");
    }

    @Test
    @DisplayName("한글, 영어, 숫자로만 구성된 name은 생성에 성공한다")
    void name_valid_success() {
        Member member1 = new Member("user01", "passw1!", "홍성균");
        Member member2 = new Member("user02", "passw1!", "Seonggyun123");

        assertThat(member1.getName()).isEqualTo("홍성균");
        assertThat(member2.getName()).isEqualTo("Seonggyun123");
    }

    @Test
    @DisplayName("updateName으로 이름을 변경할 때에도 검증이 적용된다")
    void updateName_validation() {
        Member member = new Member("user01", "passw1!", "홍성균");

        member.updateName("새이름123");
        assertThat(member.getName()).isEqualTo("새이름123");

        assertThatThrownBy(() -> member.updateName("!잘못된이름"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에는 한글, 영어, 숫자만 사용할 수 있습니다.");
    }
}
