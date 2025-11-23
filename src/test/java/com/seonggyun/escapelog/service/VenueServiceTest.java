package com.seonggyun.escapelog.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.repository.VenueRepository;
import com.seonggyun.escapelog.service.venue.VenueService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class VenueServiceTest {

    @Autowired
    VenueService venueService;
    @Autowired
    VenueRepository venueRepository;

    @BeforeEach
    void setUp() {
        venueRepository.deleteAll();
    }

    @Test
    @DisplayName("매장을 등록하면 ID가 발급되고 정상적으로 저장된다")
    void saveVenue() {
        // given
        String name = "강남점";
        String region = "서울";

        // when
        Long savedId = venueService.saveVenue(name, region);
        Venue found = venueRepository.findById(savedId).orElseThrow();

        // then
        assertThat(found.getId()).isNotNull();
        assertThat(found.getName()).isEqualTo(name);
        assertThat(found.getRegion()).isEqualTo(region);
    }

    @Test
    @DisplayName("전체 매장을 조회할 수 있다")
    void findAll() {
        // given
        venueService.saveVenue("강남점", "서울");
        venueService.saveVenue("부산점", "부산");

        // when
        List<Venue> venues = venueService.findAll();

        // then
        assertThat(venues).hasSize(2);
        assertThat(venues)
                .extracting(Venue::getName)
                .containsExactlyInAnyOrder("강남점", "부산점");
    }

    @Test
    @DisplayName("등록된 매장이 없으면 빈 리스트를 반환한다")
    void findAll_empty() {
        // when
        List<Venue> venues = venueService.findAll();

        // then
        assertThat(venues).isEmpty();
    }

    @Test
    @DisplayName("ID로 매장을 단건 조회할 수 있다")
    void findOne() {
        // given
        Long id = venueService.saveVenue("광주점", "광주");

        // when
        Venue found = venueService.findOne(id);

        // then
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getName()).isEqualTo("광주점");
        assertThat(found.getRegion()).isEqualTo("광주");
    }

    @Test
    @DisplayName("특정 키워드를 포함하는 매장명만 조회된다")
    void findByName_partialMatch() {
        // given
        venueService.saveVenue("홍대 방탈출", "서울");
        venueService.saveVenue("홍대 미스터리룸", "서울");
        venueService.saveVenue("강남 방탈출", "서울");

        // when
        List<Venue> result = venueService.findByName("홍대");

        // then
        assertThat(result).hasSize(2);
        assertThat(result)
                .allMatch(v -> v.getName().contains("홍대"));
    }

    @Test
    @DisplayName("대소문자 구분 없이 검색된다 (IgnoreCase 동작 확인)")
    void findByName_ignoreCase() {
        // given
        venueService.saveVenue("Hongdae Escape", "서울");
        venueService.saveVenue("hongdae Mystery", "서울");
        venueService.saveVenue("Busan Escape", "부산");

        // when
        List<Venue> upper = venueService.findByName("HONGDAE");
        List<Venue> lower = venueService.findByName("hongdae");

        // then
        assertThat(upper).hasSize(2);
        assertThat(lower).hasSize(2);
    }

    @Test
    @DisplayName("검색어에 해당하는 매장이 없으면 빈 리스트를 반환한다")
    void findByName_noResult() {
        // given
        venueService.saveVenue("강남 방탈출", "서울");
        venueService.saveVenue("부산 방탈출", "부산");

        // when
        List<Venue> result = venueService.findByName("홍대");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("같은 키워드지만 지역이 다른 매장들도 함께 조회된다")
    void findByName_multipleRegions() {
        // given
        venueService.saveVenue("홍대 방탈출", "서울");
        venueService.saveVenue("홍대 방탈출", "부산");
        venueService.saveVenue("홍대 방탈출", "광주");

        // when
        List<Venue> result = venueService.findByName("홍대");

        // then
        assertThat(result).hasSize(3);
        assertThat(result)
                .extracting(Venue::getRegion)
                .containsExactlyInAnyOrder("서울", "부산", "광주");
    }
}
