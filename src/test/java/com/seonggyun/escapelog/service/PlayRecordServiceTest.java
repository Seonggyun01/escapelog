package com.seonggyun.escapelog.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.domain.PlayRecord;
import com.seonggyun.escapelog.domain.Theme;
import com.seonggyun.escapelog.domain.Venue;
import com.seonggyun.escapelog.repository.MemberRepository;
import com.seonggyun.escapelog.repository.PlayRecordRepository;
import com.seonggyun.escapelog.repository.ThemeRepository;
import com.seonggyun.escapelog.repository.VenueRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PlayRecordServiceTest {

    @Autowired PlayRecordService playRecordService;
    @Autowired ThemeService themeService;
    @Autowired MemberRepository memberRepository;
    @Autowired ThemeRepository themeRepository;
    @Autowired VenueRepository venueRepository;
    @Autowired PlayRecordRepository playRecordRepository;

    Member member1;
    Member member2;
    Theme theme1;
    Theme theme2;

    @BeforeEach
    void setUp() {
        Venue venue = saveVenue("강남점", "서울");
        member1 = saveMember("성균");
        member2 = saveMember("재윤");

        Long themeId1 = themeService.saveTheme(
                venue.getId(),
                "시간의 방",
                3,
                60,
                2,
                6,
                Set.of(Genre.MYSTERY)
        );

        Long themeId2 = themeService.saveTheme(
                venue.getId(),
                "좀비의 밤",
                4,
                70,
                2,
                6,
                Set.of(Genre.HORROR)
        );

        theme1 = findTheme(themeId1);
        theme2 = findTheme(themeId2);
    }

    private Venue saveVenue(String name, String region) {
        return venueRepository.save(new Venue(name, region));
    }

    private Member saveMember(String name) {
        return memberRepository.save(new Member(name));
    }

    private Theme findTheme(Long id) {
        return themeRepository.findById(id).orElseThrow();
    }

    private Long savePlayRecord(Member member, Theme theme, LocalDate date,
                                boolean cleared, int time, int hint, int rating, String comment) {
        return playRecordService.savePlayRecord(
                member.getId(),
                theme.getId(),
                date,
                cleared,
                time,
                hint,
                rating,
                comment
        );
    }

    @Test
    @DisplayName("플레이 기록을 정상적으로 저장할 수 있다")
    void savePlayRecord_success() {
        LocalDate playDate = LocalDate.of(2025, 11, 1);

        Long recordId = savePlayRecord(
                member1,
                theme1,
                playDate,
                true,
                3200,
                1,
                5,
                "난이도 적당하고 재밌었음"
        );

        PlayRecord saved = playRecordRepository.findById(recordId).orElseThrow();

        assertThat(saved.getMember().getId()).isEqualTo(member1.getId());
        assertThat(saved.getTheme().getId()).isEqualTo(theme1.getId());
        assertThat(saved.getPlayDate()).isEqualTo(playDate);
        assertThat(saved.getCleared()).isTrue();
        assertThat(saved.getClearTimeSec()).isEqualTo(3200);
        assertThat(saved.getHintCount()).isEqualTo(1);
        assertThat(saved.getRating()).isEqualTo(5);
        assertThat(saved.getComment()).isEqualTo("난이도 적당하고 재밌었음");
    }

    @Test
    @DisplayName("전체 플레이 기록을 조회할 수 있다")
    void findAll() {
        savePlayRecord(member1, theme1, LocalDate.now(), true, 3000, 1, 5, "굿");
        savePlayRecord(member2, theme2, LocalDate.now(), false, 0, 3, 3, "시간 부족");

        List<PlayRecord> records = playRecordService.findAll();

        assertThat(records).hasSize(2);
    }

    @Test
    @DisplayName("ID로 단일 플레이 기록을 조회할 수 있다")
    void findById() {
        Long id = savePlayRecord(
                member1,
                theme1,
                LocalDate.now(),
                true,
                2500,
                0,
                4,
                "재방문 의사 있음"
        );

        PlayRecord found = playRecordService.findById(id);

        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getMember().getId()).isEqualTo(member1.getId());
        assertThat(found.getTheme().getId()).isEqualTo(theme1.getId());
    }

    @Test
    @DisplayName("특정 회원의 플레이 기록만 조회된다")
    void findByMember_onlyThatMember() {
        savePlayRecord(member1, theme1,
                LocalDate.of(2025, 10, 1),
                true, 3200, 1, 5,
                "테마1 첫 플레이");

        savePlayRecord(member1, theme2,
                LocalDate.of(2025, 10, 10),
                false, 0, 2, 3,
                "클리어 실패");

        savePlayRecord(member2, theme1,
                LocalDate.of(2025, 10, 5),
                true, 2800, 0, 4,
                "괜찮았음");

        List<PlayRecord> member1Records = playRecordService.findByMember(member1.getId());
        List<PlayRecord> member2Records = playRecordService.findByMember(member2.getId());

        assertThat(member1Records).hasSize(2);
        assertThat(member1Records)
                .allMatch(r -> r.getMember().getId().equals(member1.getId()));

        assertThat(member2Records).hasSize(1);
        assertThat(member2Records)
                .allMatch(r -> r.getMember().getId().equals(member2.getId()));
    }

    @Test
    @DisplayName("플레이 기록이 없는 회원은 빈 리스트가 반환된다")
    void findByMember_empty() {
        Member newMember = saveMember("새로운유저");

        List<PlayRecord> result = playRecordService.findByMember(newMember.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("특정 테마의 플레이 기록만 조회된다")
    void findByTheme_onlyThatTheme() {
        savePlayRecord(member1, theme1,
                LocalDate.of(2025, 9, 1),
                true, 3100, 1, 5,
                "테마1 클리어");

        savePlayRecord(member2, theme1,
                LocalDate.of(2025, 9, 2),
                false, 0, 2, 2,
                "실패");

        savePlayRecord(member1, theme2,
                LocalDate.of(2025, 9, 3),
                true, 2900, 0, 4,
                "테마2 클리어");

        List<PlayRecord> theme1Records = playRecordService.findByTheme(theme1.getId());
        List<PlayRecord> theme2Records = playRecordService.findByTheme(theme2.getId());

        assertThat(theme1Records).hasSize(2);
        assertThat(theme1Records)
                .allMatch(r -> r.getTheme().getId().equals(theme1.getId()));

        assertThat(theme2Records).hasSize(1);
        assertThat(theme2Records)
                .allMatch(r -> r.getTheme().getId().equals(theme2.getId()));
    }

    @Test
    @DisplayName("플레이 기록이 없는 테마는 빈 리스트가 반환된다")
    void findByTheme_empty() {
        Venue venue = saveVenue("부산점", "부산");
        Long newThemeId = themeService.saveTheme(
                venue.getId(),
                "신규 테마",
                3,
                60,
                2,
                6,
                Set.of(Genre.MYSTERY)
        );
        Theme newTheme = findTheme(newThemeId);

        List<PlayRecord> result = playRecordService.findByTheme(newTheme.getId());

        assertThat(result).isEmpty();
    }
}
