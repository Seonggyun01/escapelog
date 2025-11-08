package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.PlayRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRecordRepository extends JpaRepository<PlayRecord, Long> {
    List<PlayRecord> findByMemberId(Long memberId);
    List<PlayRecord> findByThemeId(Long themeId);
}
