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
    private final VenueRepository venueRepository;

    /**
     * 매장 등록
     */
    @Transactional
    public Long saveVenue(String name, String region){
        Venue venue = new Venue(name, region);
        venueRepository.save(venue);
        return venue.getId();
    }

    /**
     * 전체 조회
     */
    public List<Venue> findAll(){
        return venueRepository.findAll();
    }

    /**
     * 단건 조회
     */
    public Venue findOne(Long id){
        return venueRepository.findById(id).get();
    }

    /**
     * 매장명 조회
     */
    public List<Venue> findByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }
}
