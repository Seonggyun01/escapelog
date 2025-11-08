package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.Theme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findByVenueId(Long venueId);
}
