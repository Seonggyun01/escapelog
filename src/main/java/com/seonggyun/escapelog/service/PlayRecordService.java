package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.domain.PlayRecord;
import com.seonggyun.escapelog.domain.Theme;
import com.seonggyun.escapelog.repository.MemberRepository;
import com.seonggyun.escapelog.repository.PlayRecordRepository;
import com.seonggyun.escapelog.repository.ThemeRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayRecordService {
    private final MemberRepository memberRepository;
    private final ThemeRepository themeRepository;
    private final PlayRecordRepository playRecordRepository;

    /**
     * 플레이기록 저장
     */
    @Transactional
    public Long savePlayRecord(
            Long memberId,
            Long themeId,
            LocalDate playDate,
            Boolean cleared,
            int clearTimeSec,
            int hintCount,
            int rating,
            String comment) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Theme theme = themeRepository.findById(themeId).orElseThrow();
        PlayRecord playRecord = new PlayRecord(
                member,
                theme,
                playDate,
                cleared,
                clearTimeSec,
                hintCount,
                rating,
                comment);
        playRecordRepository.save(playRecord);
        return playRecord.getId();
    }

    /**
     * 전체 조회
     */
    public List<PlayRecord> findAll() {
        return playRecordRepository.findAll();
    }

    /**
     * 단건 조회
     */
    public PlayRecord findById(Long id) {
        return playRecordRepository.findById(id).get();
    }

    /**
     * 맴버 아이디로 조회
     */
    public List<PlayRecord> findByMember(Long memberId) {
        return playRecordRepository.findByMemberId(memberId);
    }

    /**
     * 테마 아이디로 조회
     */
    public List<PlayRecord> findByTheme(Long themeId) {
        return playRecordRepository.findByThemeId(themeId);
    }

    /**
     * 총 플레이 횟수
     */
    public long countByMember(Member member) {
        return playRecordRepository.countByMember(member);
    }

    /**
     * 성공률
     */
    public int calculateSuccessRate(Member member) {
        long total = playRecordRepository.countByMember(member);
        if (total == 0) {
            return 0;
        }
        long success = playRecordRepository.countByMemberAndClearedTrue(member);
        return (int) Math.round((double) success / total * 100);
    }

    /**
     * 방문 매장 수 (중복 제거)
     */
    public long countDistinctVenueByMember(Member member) {
        return playRecordRepository.countDistinctVenueByMember(member);
    }

    /**
     * 평균 클리어 시간
     */
    public String getAverageClearTimeFormatted(Member member) {
        List<PlayRecord> records = playRecordRepository.findByMember(member);
        if (records.isEmpty()) {
            return "0분";
        }

        double avgSec = records.stream()
                .filter(r -> r.getClearTimeSec() > 0)
                .mapToInt(PlayRecord::getClearTimeSec)
                .average()
                .orElse(0);

        int minutes = (int) (avgSec / 60);
        return minutes + "분";
    }

    /**
     * 최근 플레이 기록 (최신순 3개)
     */
    public List<PlayRecord> getRecentPlays(Member member) {
        return playRecordRepository.findByMember(member).stream()
                .sorted(Comparator.comparing(PlayRecord::getPlayDate).reversed())
                .limit(3)
                .collect(
                        Collectors.toList());
    }
}
