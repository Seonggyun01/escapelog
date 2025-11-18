package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.domain.playRecord.PlayRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayRecordRepository extends JpaRepository<PlayRecord, Long> {
    List<PlayRecord> findByMemberId(Long memberId);
    List<PlayRecord> findByThemeId(Long themeId);

    /**
     * 맴버 기록 개수
     */
    long countByMember(Member member);

    /**
     * 특정 회원의 성공 플레이 수 (cleared == true)
     */
    long countByMemberAndClearedTrue(Member member);

    /**
     * 특정 회원이 플레이한 "서로 다른 매장 수" (PlayRecord → Theme → Venue 경로를 통해 DISTINCT COUNT)
     */
    @Query("SELECT COUNT(DISTINCT pr.theme.venue.id) " +
            "FROM PlayRecord pr " +
            "WHERE pr.member = :member")
    long countDistinctVenueByMember(@Param("member") Member member);

    /**
     * 특정 회원의 전체 플레이 기록 (정렬이나 평균 계산용)
     */
    List<PlayRecord> findByMember(Member member);

    /**
     * 특정 회원의 최근 플레이 기록 (최신순 상위 N개)
     */
    List<PlayRecord> findTop3ByMemberOrderByPlayDateDesc(Member member);

    /**
     *  특정 회원의 평균 클리어 시간 (초 단위) → PlayRecord에 clearTimeSec 필드가 존재하니까 사용 가능
     */
    @Query("SELECT AVG(pr.clearTimeSec) FROM PlayRecord pr WHERE pr.member = :member AND pr.clearTimeSec > 0")
    Double findAverageClearTimeByMember(@Param("member") Member member);
}
