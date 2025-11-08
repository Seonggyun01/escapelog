package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.domain.PlayRecord;
import com.seonggyun.escapelog.domain.Theme;
import com.seonggyun.escapelog.repository.MemberRepository;
import com.seonggyun.escapelog.repository.PlayRecordRepository;
import com.seonggyun.escapelog.repository.ThemeRepository;
import java.time.LocalDate;
import java.util.List;
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
        Member member = memberRepository.findById(memberId).get();
        Theme theme = themeRepository.findById(themeId).get();
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
    public List<PlayRecord> findAll(){
        return playRecordRepository.findAll();
    }

    /**
     * 단건 조회
     */
    public PlayRecord findById(Long id){
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
}
