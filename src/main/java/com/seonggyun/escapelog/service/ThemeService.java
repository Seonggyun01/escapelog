package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.Theme;
import com.seonggyun.escapelog.domain.Venue;
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
    private final ThemeRepository themeRepository;
    private final VenueRepository venueRepository;

    /**
     * 테마 저장
     */
    @Transactional
    public Long saveTheme(Long venueId, String title, Integer difficulty, Integer durationMin, Integer minPlayer,
                          Integer maxPlayer, Set<Genre> genres) {
        Venue venue = venueRepository.findById((venueId)).get();
        Theme theme = new Theme(venue, title, difficulty, durationMin, minPlayer, maxPlayer, genres);
        themeRepository.save(theme);
        return theme.getId();
    }

    /**
     * 전체 조회
     */
    public List<Theme> findAll(){
        return themeRepository.findAll();
    }

    /**
     * 단건 조회
     */
    public Theme findOne(Long id){
        return themeRepository.findById(id).get();
    }

    /**
     * 매장명으로 테마 조회
     */
    public List<Theme> findByVenueId(Long id){
        return themeRepository.findByVenueId(id);
    }
}
