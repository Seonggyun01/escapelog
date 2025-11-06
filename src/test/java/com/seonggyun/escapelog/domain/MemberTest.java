package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
    @Test
    @DisplayName("정상 이름으로 Member 생성 성공")
    void 정상_이름_맴버_생성_테스트(){
        Member member = new Member("홍성균");
        assertThat(member.getName()).isEqualTo("홍성균");
    }

    @Test
    @DisplayName("영어와_숫자_조합으로_멤버를_생성할_수_있다")
    void 영어_숫자_조합_멤버_생성_테스트() {
        Member member = new Member("Seonggyun123");
        assertThat(member.getName()).isEqualTo("Seonggyun123");
    }

    @Test
    @DisplayName("이름이 null이면 예외가 발생한다.")
    void 이름이_null이면_예외_발생(){
        assertThatThrownBy(() -> new Member(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 공백이면 예외가 발생한다.")
    void 이름이_공백이면_예외_발생(){
        assertThatThrownBy(() -> new Member("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("이름에_특수문자가_포함되면_예외가_발생한다")
    void 특수문자_포함_예외_발생() {
        assertThatThrownBy(() -> new Member("홍성균!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에는 한글, 영어, 숫자만 사용할 수 있습니다.");
    }

    @Test
    @DisplayName("이름이_50자를_초과하면_예외가_발생한다")
    void 이름_50자_초과_예외_발생() {
        String longName = "a".repeat(51); // 51자
        assertThatThrownBy(() -> new Member(longName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("50자 이하");
    }

    @Test
    @DisplayName("이름이_정확히_50자이면_정상적으로_생성된다")
    void 이름_50자_정상_생성() {
        String maxName = "a".repeat(50);
        Member member = new Member(maxName);
        assertThat(member.getName()).hasSize(50);
    }

    @Test
    @DisplayName("이름이_숫자만으로_정상_생성된다")
    void 숫자만으로_정상_생성() {
        Member member = new Member("12345");
        assertThat(member.getName()).isEqualTo("12345");
    }
}