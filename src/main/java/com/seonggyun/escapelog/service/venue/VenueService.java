package com.seonggyun.escapelog.service.venue;

import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.repository.VenueRepository;
import com.seonggyun.escapelog.service.venue.exception.VenueServiceErrorCode;
import com.seonggyun.escapelog.service.venue.exception.VenueServiceException;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
                .orElseThrow(
                        () -> new VenueServiceException(VenueServiceErrorCode.VENUE_NOT_FOUND)); // get()->orElseThrow()
    }

    public List<Venue> findByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Venue> searchVenues(String keyword, String sort) {
        List<Venue> venues = List.of();
        if (keyword == null || keyword.isBlank()) {
            venues = venueRepository.findAll();
        }
        if (keyword != null && !keyword.isBlank()) {
            String trimmed = keyword.trim();
            venues = venueRepository.findByNameContainingIgnoreCaseOrRegionContainingIgnoreCase(trimmed, trimmed);
        }

        applySort(venues, sort);

        return venues;
    }

    private void applySort(List<Venue> venues, String sort) {
        if (sort == null || sort.isBlank()) {
            return;
        }
        Collator collator = Collator.getInstance(Locale.KOREA);
        Comparator<Venue> byName = Comparator.comparing(Venue::getName, collator);

        if ("nameAsc".equals(sort)) {
            venues.sort(byName);
        }
        if ("nameDesc".equals(sort)) {
            venues.sort(byName.reversed());
        }
    }
}
