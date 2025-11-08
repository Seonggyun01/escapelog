package com.seonggyun.escapelog.repository;

import com.seonggyun.escapelog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
