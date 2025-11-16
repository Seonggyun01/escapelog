package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Venue;
import com.seonggyun.escapelog.repository.VenueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueService {

    private static final String ERROR_VENUE_NOT_FOUND = "해당 매장을 찾을 수 없습니다.";

    private final VenueRepository venueRepository;

    @Transactional
    public Long saveVenue(String name, String region) {
        Venue venue = new Venue(name, region);
        venueRepository.save(venue);
        return venue.getId();
    }

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Venue findOne(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_VENUE_NOT_FOUND)); // get()->orElseThrow()
    }

    public List<Venue> findByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }
}
