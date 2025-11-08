package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VenueTest {

    @Test
    @DisplayName("매장명과 지역을 입력해서 정상적으로 생성한다.")
    void 매장명_지역_입력_정상_테스트() {
        Venue venue = new Venue("방탈출", "서울 홍대");
        assertThat(venue.getName()).isEqualTo("방탈출");
        assertThat(venue.getRegion()).isEqualTo("서울 홍대");
        assertThat(venue.getThemes()).isEmpty();
    }

    @Test
    @DisplayName("매장이름이 비어있으면 예외가 발생한다.")
    void 매장명_공백_예외_테스트() {
        assertThatThrownBy(() -> new Venue("  ", "서울"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("매장 이름은 비워둘 수 없습니다.");
    }

    @Test
    @DisplayName("매장이름이 null이면 예외가 발생한다.")
    void 매장명_null_예외_테스트() {
        assertThatThrownBy(() -> new Venue(null, "서울"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("매장 이름은 비워둘 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 120자를 초과하면 예외가 발생한다")
    void 이름_120자_초과_예외가_발생() {
        String longName = "a".repeat(121);
        assertThatThrownBy(() -> new Venue(longName, "서울"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("매장 이름은 120자 이하로 입력해야 합니다.");
    }

    @Test
    @DisplayName("지역은 null이어도 된다.")
    void 지역_null_정상_테스트() {
        Venue venue = new Venue("방탈출", null);
        assertThat(venue.getName()).isEqualTo("방탈출");
        assertThat(venue.getRegion()).isNull();
    }

    @Test
    @DisplayName("지역은 공백이어도 된다.")
    void 지역_공백_정상_테스트() {
        Venue venue = new Venue("방탈출", "  ");
        assertThat(venue.getName()).isEqualTo("방탈출");
        assertThat(venue.getRegion()).isBlank();
    }

    @Test
    @DisplayName("지역이 60자를 초과하면 예외가 발생한다")
    void 지역_60자_초과_예외_발생() {
        String longRegion = "r".repeat(61);
        assertThatThrownBy(() -> new Venue("마스터키", longRegion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지역은 60자 이하로 입력해야 합니다.");
    }

    @Test
    @DisplayName("매장에 테마 추가할 수 있다")
    void 테마_추가_테스트() {
        Venue venue = new Venue("방탈출", "서울");
        Theme theme = new Theme(venue, "지하철 미스터리", 3, 60, 2, 6, EnumSet.of(Genre.MYSTERY, Genre.THRILLER));

        venue.addTheme(theme);

        assertThat(venue.getThemes()).containsExactly(theme);
    }

    @Test
    @DisplayName("동일 테마를 중복 추가하면 예외가 발생한다.")
    void 동일_테마_중복_추가_예외_발생() {
        Venue venue = new Venue("방탈출", "서울");
        Theme theme = new Theme(venue, "지하철 미스터리", 3, 60, 2, 6, EnumSet.of(Genre.MYSTERY, Genre.THRILLER));
        venue.addTheme(theme);
        assertThatThrownBy(() -> venue.addTheme(theme))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 등록된 테마입니다.");
    }
}