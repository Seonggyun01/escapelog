package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.repository.ThemeRepository;
import com.seonggyun.escapelog.repository.VenueRepository;
import java.util.List;
import java.util.Set;
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
    private final VenueRepository venueRepository;

    @Transactional
    public Long saveTheme(Long venueId, String title, Integer difficulty, Integer durationMin,
                          Integer minPlayer, Integer maxPlayer, Set<Genre> genres) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_VENUE_NOT_FOUND)); //get()->orElseThrow()

        Theme theme = new Theme(venue, title, difficulty, durationMin, minPlayer, maxPlayer, genres);

        themeRepository.save(theme);
        return theme.getId();
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findOne(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_THEME_NOT_FOUND)); //get()->orElseThrow()
    }

    public List<Theme> findByVenueId(Long id) {
        return themeRepository.findByVenueId(id);
    }
}
