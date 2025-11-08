package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.Venue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
