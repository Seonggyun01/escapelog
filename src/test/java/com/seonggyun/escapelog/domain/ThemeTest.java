package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.EnumSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThemeTest {
    Venue venue;

    @BeforeEach
    void 매장_생성(){
        venue = new Venue("방탈출", "서울");
    }

    @Test
    @DisplayName("정상적인 입력으로 Theme를 생성 가능")
    void 정상_입력_Theme생성() {
        Theme theme = new Theme(
                venue, "지하철 미스터리", 3, 90, 2, 6,
                EnumSet.of(Genre.MYSTERY, Genre.THRILLER)
        );

        assertThat(theme.getVenue()).isEqualTo(venue);
        assertThat(theme.getTitle()).isEqualTo("지하철 미스터리");
        assertThat(theme.getDifficulty()).isEqualTo(3);
        assertThat(theme.getDurationMin()).isEqualTo(90);
        assertThat(theme.getMinPlayer()).isEqualTo(2);
        assertThat(theme.getMaxPlayer()).isEqualTo(6);
        assertThat(theme.getGenres()).containsExactlyInAnyOrder(Genre.MYSTERY, Genre.THRILLER);
    }

    @Test
    @DisplayName("venueId가 null이면 예외가 발생한다")
    void venueId_null_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(null, "미스터리", 3, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("매장에 속해야");
    }

    @Test
    @DisplayName("제목이 null이거나 공백이면 예외가 발생한다")
    void 제목_null_공백_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, null, 3, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new Theme(venue, "   ", 3, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("제목이 120자를 초과하면 예외가 발생한다")
    void 제목_120자_초과_예외_발생() {
        String longTitle = "a".repeat(121);
        assertThatThrownBy(() ->
                new Theme(venue, longTitle, 3, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("120자 이하");
    }

    @Test
    @DisplayName("난이도가 1보다 작거나 5보다 크면 예외가 발생한다")
    void 난이도_범위_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, "난이도", 0, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new Theme(venue, "난이도", 6, 90, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("플레이시간이 0보다 작거나 300보다 크면 예외가_발생한다")
    void 플레이시간_범위_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, "시간", 3, -1, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new Theme(venue, "시간", 3, 301, 2, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최소 인원이 1명 미만이면 예외가 발생한다")
    void 최소_인원_1미만_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, "인원", 3, 90, 0, 6, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 인원은 1명 이상");
    }

    @Test
    @DisplayName("최대 인원이 최소 인원보다 작으면 예외가 발생한다")
    void 최대_인원이_최소_인원보다_작으면_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, "인원", 3, 90, 4, 2, EnumSet.of(Genre.MYSTERY))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 인원은 최소 인원보다 작을 수 없습니다");
    }

    @Test
    @DisplayName("장르가_null이거나_비어있으면_예외가_발생한다")
    void 장르_null_예외_발생() {
        assertThatThrownBy(() ->
                new Theme(venue, "장르", 3, 90, 2, 6, null)
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new Theme(venue, "장르", 3, 90, 2, 6, EnumSet.noneOf(Genre.class))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}