package com.seonggyun.escapelog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import org.junit.jupiter.api.Test;

class GenreTest {
    @Test
    void 모든_장르가_정상적으로_정의되어있다() {
        Genre[] genres = Genre.values();

        assertThat(genres).isNotEmpty();
        assertThat(genres).contains(
                Genre.MYSTERY, Genre.HORROR, Genre.THRILLER, Genre.CRIME,
                Genre.FANTASY, Genre.SCIFI, Genre.ADVENTURE, Genre.HISTORICAL,
                Genre.DETECTIVE, Genre.STEALTH, Genre.SURVIVAL, Genre.ESCAPE,
                Genre.LOGIC, Genre.PUZZLE, Genre.DRAMA, Genre.COMEDY, Genre.MELO
        );
    }

    @Test
    void 한글_이름이_올바르게_매핑되어있다() {
        assertThat(Genre.HORROR.getKoreanName()).isEqualTo("공포");
        assertThat(Genre.MYSTERY.getKoreanName()).isEqualTo("추리");
        assertThat(Genre.COMEDY.getKoreanName()).isEqualTo("코믹");
        assertThat(Genre.HISTORICAL.getKoreanName()).isEqualTo("시대극");
    }

    @Test
    void EnumSet은_중복을_허용하지_않는다() {
        EnumSet<Genre> genres = EnumSet.of(Genre.HORROR, Genre.MYSTERY, Genre.HORROR);

        assertThat(genres).hasSize(2);
        assertThat(genres).containsExactlyInAnyOrder(Genre.HORROR, Genre.MYSTERY);
    }

    @Test
    void valueOf를_통해_문자열에서_열거형으로_변환할_수_있다() {
        Genre genre = Genre.valueOf("FANTASY");
        assertThat(genre).isEqualTo(Genre.FANTASY);
    }

    @Test
    void 존재하지_않는_장르명을_입력하면_예외가_발생한다() {
        assertThatThrownBy(() -> Genre.valueOf("UNKNOWN"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}