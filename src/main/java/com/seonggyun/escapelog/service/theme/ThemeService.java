package com.seonggyun.escapelog.service.theme;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.repository.ThemeRepository;
import com.seonggyun.escapelog.service.theme.exception.ThemeServiceErrorCode;
import com.seonggyun.escapelog.service.theme.exception.ThemeServiceException;
import com.seonggyun.escapelog.service.venue.VenueService;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThemeService {

    private static final String ERROR_VENUE_NOT_FOUND = "해당 매장을 찾을 수 없습니다.";
    private static final String ERROR_THEME_NOT_FOUND = "해당 테마를 찾을 수 없습니다.";

    private final ThemeRepository themeRepository;
    private final VenueService venueService;

    @Transactional
    public Long saveTheme(Long venueId, String title, Integer difficulty, Integer durationMin,
                          Integer minPlayer, Integer maxPlayer, Set<Genre> genres) {

        Venue venue = venueService.findOne(venueId);
        Theme theme = new Theme(venue, title, difficulty, durationMin, minPlayer, maxPlayer, genres);

        themeRepository.save(theme);
        return theme.getId();
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findOne(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ThemeServiceException(ThemeServiceErrorCode.THEME_NOT_FOUND));
    }

    public List<Theme> findByVenueId(Long id) {
        return themeRepository.findByVenueId(id);
    }

    public List<Theme> searchTheme(String keyword, String sort, List<Genre> genres) {
        List<Theme> themes = List.of();
        if (keyword == null || keyword.isBlank()) {
            themes = themeRepository.findAll();
        }
        if (keyword != null && !keyword.isBlank()) {
            String trimmed = keyword.trim();
            themes = themeRepository.findByTitleContainingIgnoreCaseOrVenue_NameContainingIgnoreCase(trimmed, trimmed);
        }
        themes = filterGenres(themes, genres);
        applySort(themes, sort);

        return themes;
    }

    private List<Theme> filterGenres(List<Theme> themes, List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return themes;
        }
        return themes.stream()
                .filter(theme -> theme.getGenres().stream().anyMatch(genres::contains))
                .collect(Collectors.toList());
    }

    private void applySort(List<Theme> themes, String sort) {
        if (sort == null || sort.isBlank()) {
            return;
        }
        Comparator<Theme> comparator = Comparator.comparing(Theme::getDifficulty,
                Comparator.nullsLast(Integer::compareTo));
        if ("difficultyAsc".equals(sort)) {
            themes.sort(comparator);
        }
        if ("difficultyDesc".equals(sort)) {
            themes.sort(comparator.reversed());
        }
    }

    public Set<Genre> extractGenres(List<Theme> themes) {
        if (themes == null || themes.isEmpty()) {
            return Set.of();
        }

        return themes.stream()
                .flatMap(theme -> theme.getGenres().stream()) // 각 테마의 장르들을 하나의 스트림으로
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(Enum::name)) // 이름 기준 정렬된 Set
                ));
    }
}
