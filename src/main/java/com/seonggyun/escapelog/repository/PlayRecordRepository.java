package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.PlayRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRecordRepository extends JpaRepository<PlayRecord, Long> {
}
