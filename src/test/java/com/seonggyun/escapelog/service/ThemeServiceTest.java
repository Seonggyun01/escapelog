package com.seonggyun.escapelog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.Theme;
import com.seonggyun.escapelog.domain.Venue;
import com.seonggyun.escapelog.repository.ThemeRepository;
import com.seonggyun.escapelog.repository.VenueRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ThemeServiceTest {

    @Autowired ThemeService themeService;
    @Autowired ThemeRepository themeRepository;
    @Autowired VenueRepository venueRepository;

    Venue gangnam;
    Venue hongdae;

    @BeforeEach
    void setup() {
        themeRepository.deleteAll();
        venueRepository.deleteAll();

        gangnam = new Venue("강남점", "서울");
        hongdae = new Venue("홍대점", "서울");
        venueRepository.saveAll(List.of(gangnam, hongdae));
    }

    @Test
    @DisplayName("테마를 정상적으로 저장할 수 있다")
    void saveTheme() {
        // given
        Set<Genre> genres = Set.of(Genre.MYSTERY, Genre.HORROR);

        // when
        Long themeId = themeService.saveTheme(
                gangnam.getId(),
                "시간의 방",
                3, 60, 2, 6, genres
        );

        // then
        Theme saved = themeRepository.findById(themeId).orElseThrow();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("시간의 방");
        assertThat(saved.getVenue().getId()).isEqualTo(gangnam.getId());
        assertThat(saved.getGenres())
                .containsExactlyInAnyOrder(Genre.MYSTERY, Genre.HORROR);
    }

    @Test
    @DisplayName("같은 제목의 테마라도 매장이 다르면 각각 저장된다")
    void saveTheme_sameTitleDifferentVenue() {
        // given
        String title = "시간의 방";

        // when
        Long gangnamThemeId = themeService.saveTheme(
                gangnam.getId(), title, 3, 60, 2, 6, Set.of(Genre.MYSTERY));
        Long hongdaeThemeId = themeService.saveTheme(
                hongdae.getId(), title, 2, 50, 2, 4, Set.of(Genre.FANTASY));

        // then
        Theme gangnamTheme = themeRepository.findById(gangnamThemeId).orElseThrow();
        Theme hongdaeTheme = themeRepository.findById(hongdaeThemeId).orElseThrow();

        assertThat(gangnamTheme.getVenue().getId()).isEqualTo(gangnam.getId());
        assertThat(hongdaeTheme.getVenue().getId()).isEqualTo(hongdae.getId());
        assertThat(gangnamTheme.getTitle()).isEqualTo(title);
        assertThat(hongdaeTheme.getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("같은 매장에 같은 제목의 테마를 여러 번 저장할 수 있다 (현재 구현 기준)")
    void saveTheme_duplicateInSameVenue_allowed() {
        // given
        String title = "시간의 방";

        // when
        Long id1 = themeService.saveTheme(
                gangnam.getId(), title, 3, 60, 2, 6, Set.of(Genre.MYSTERY));
        Long id2 = themeService.saveTheme(
                gangnam.getId(), title, 4, 70, 2, 6, Set.of(Genre.HORROR));

        // then
        List<Theme> themes = themeRepository.findAll();
        assertThat(themes).hasSize(2);
        assertThat(id1).isNotEqualTo(id2);
        assertThat(themes)
                .extracting(Theme::getTitle)
                .allMatch(t -> t.equals(title));
    }

    @Test
    @DisplayName("존재하지 않는 매장 ID로 테마 저장 시 예외가 발생한다")
    void saveTheme_invalidVenue_throwsException() {
        // when & then
        assertThatThrownBy(() -> themeService.saveTheme(
                999L,
                "유령 테마",
                3, 60, 2, 6, Set.of(Genre.HORROR)
        )).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("전체 테마를 조회할 수 있다")
    void findAll() {
        // given
        themeService.saveTheme(gangnam.getId(), "시간의 방", 3, 60, 2, 6, Set.of(Genre.MYSTERY));
        themeService.saveTheme(hongdae.getId(), "기억의 조각", 2, 50, 2, 5, Set.of(Genre.FANTASY));

        // when
        List<Theme> themes = themeService.findAll();

        // then
        assertThat(themes).hasSize(2);
    }

    @Test
    @DisplayName("등록된 테마가 없으면 findAll은 빈 리스트를 반환한다")
    void findAll_empty() {
        // given
        themeRepository.deleteAll();

        // when
        List<Theme> themes = themeService.findAll();

        // then
        assertThat(themes).isEmpty();
    }

    @Test
    @DisplayName("ID로 단일 테마를 조회할 수 있다")
    void findOne() {
        // given
        Long id = themeService.saveTheme(
                gangnam.getId(),
                "시간의 방",
                3, 60, 2, 6, Set.of(Genre.MYSTERY)
        );

        // when
        Theme found = themeService.findOne(id);

        // then
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getVenue().getId()).isEqualTo(gangnam.getId());
        assertThat(found.getTitle()).isEqualTo("시간의 방");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 단일 테마 조회 시 예외가 발생한다")
    void findOne_invalidId_throwsException() {
        // when & then
        assertThatThrownBy(() -> themeService.findOne(999L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("특정 매장 ID의 테마만 조회된다")
    void findByVenueId_onlyThatVenueThemes() {
        // given
        themeService.saveTheme(gangnam.getId(), "강남의 비밀", 3, 60, 2, 6, Set.of(Genre.MYSTERY));
        themeService.saveTheme(gangnam.getId(), "강남 좀비", 4, 70, 2, 6, Set.of(Genre.HORROR));
        themeService.saveTheme(hongdae.getId(), "홍대 유령의 집", 2, 50, 2, 5, Set.of(Genre.HORROR));

        // when
        List<Theme> gangnamThemes = themeService.findByVenueId(gangnam.getId());
        List<Theme> hongdaeThemes = themeService.findByVenueId(hongdae.getId());

        // then
        assertThat(gangnamThemes).hasSize(2);
        assertThat(gangnamThemes)
                .allMatch(theme -> theme.getVenue().getId().equals(gangnam.getId()));

        assertThat(hongdaeThemes).hasSize(1);
        assertThat(hongdaeThemes)
                .allMatch(theme -> theme.getVenue().getId().equals(hongdae.getId()));
    }

    @Test
    @DisplayName("테마가 없는 매장 ID로 조회하면 빈 리스트를 반환한다")
    void findByVenueId_emptyWhenNoThemes() {
        // given
        Venue busan = new Venue("부산점", "부산");
        venueRepository.save(busan);

        // when
        List<Theme> result = themeService.findByVenueId(busan.getId());

        // then
        assertThat(result).isEmpty();
    }
}
