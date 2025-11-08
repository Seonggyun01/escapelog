package com.seonggyun.escapelog.service;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입(회원 추가)
     */
    @Transactional
    public Long join(String name){
        Member member = new Member(name);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단건 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    /**
     * 이름으로 회원 조회
     */
    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }
}
