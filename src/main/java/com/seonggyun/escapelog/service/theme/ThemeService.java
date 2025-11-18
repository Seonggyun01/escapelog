package com.seonggyun.escapelog.service.theme;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.repository.ThemeRepository;
import com.seonggyun.escapelog.repository.VenueRepository;
import com.seonggyun.escapelog.service.theme.exception.ThemeServiceErrorCode;
import com.seonggyun.escapelog.service.theme.exception.ThemeServiceException;
import com.seonggyun.escapelog.service.venue.VenueService;
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
}
