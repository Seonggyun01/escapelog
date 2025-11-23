package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.domain.playRecord.PlayRecord;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.venue.Venue;
import java.time.LocalDate;
import java.util.EnumSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayRecordTest {

    Member member;
    Theme theme;
    Venue venue;

    @BeforeEach
    void 멤버_테마_생성() {
        member = new Member("user01", "passw1!", "홍성균");
        venue = new Venue("방탈출", "서울");
        theme = new Theme(venue, "theme1", 3, 60, 1, 6,
                EnumSet.of(Genre.MYSTERY, Genre.THRILLER));
    }

    @Test
    void 정상적인_입력으로_PlayRecord를_생성할_수_있다() {
        PlayRecord record = new PlayRecord(
                member,
                theme,
                LocalDate.now().minusDays(1),
                true,
                300,
                2,
                5,
                "재밌게 플레이했어요!"
        );

        assertThat(record.getMember()).isEqualTo(member);
        assertThat(record.getTheme()).isEqualTo(theme);
        assertThat(record.getCleared()).isTrue();
        assertThat(record.getClearTimeSec()).isEqualTo(300);
        assertThat(record.getHintCount()).isEqualTo(2);
        assertThat(record.getRating()).isEqualTo(5);
        assertThat(record.getComment()).contains("재밌게");
    }

    @Test
    void memberId나_themeId가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                null, theme, LocalDate.now(), true, 100, 1, 4, "테스트"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("회원");

        assertThatThrownBy(() -> new PlayRecord(
                member, null, LocalDate.now(), true, 100, 1, 4, "테스트"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("테마");
    }

    @Test
    void playDate가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, null, true, 100, 0, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("플레이 날짜는 필수");
    }

    @Test
    void playDate가_미래이면_예외가_발생한다() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, tomorrow, true, 100, 0, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("미래일");
    }

    @Test
    void cleared가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), null, 100, 0, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("성공 여부");
    }

    @Test
    void cleared_true일_때_clearTimeSec이_0이면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), true, 0, 1, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 클리어");
    }

    @Test
    void cleared_false일_때_clearTimeSec이_0이_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), false, 200, 1, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("클리어 시간을 입력할 수 없습니다");
    }

    @Test
    void hintCount가_음수이면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), true, 100, -1, 3, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("힌트 사용 개수");
    }

    @Test
    void rating이_1보다_작거나_5보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), true, 100, 1, 0, ""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), true, 100, 1, 6, ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void comment가_501자를_초과하면_예외가_발생한다() {
        String longComment = "a".repeat(501);
        assertThatThrownBy(() -> new PlayRecord(
                member, theme, LocalDate.now(), true, 100, 1, 4, longComment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("후기는 500자 이하로 입력해주세요.");
    }
}
