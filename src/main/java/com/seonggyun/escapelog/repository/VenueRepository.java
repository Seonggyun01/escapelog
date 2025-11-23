package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.venue.Venue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByNameContainingIgnoreCase(String name);

    List<Venue> findByNameContainingIgnoreCaseOrRegionContainingIgnoreCase(String trimed, String trimed1);
}
