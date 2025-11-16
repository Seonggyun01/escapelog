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
    private static final String ERROR_LOGIN_ID_DUPLICATED = "이미 사용 중인 로그인 ID입니다.";
    private static final String ERROR_NAME_DUPLICATED = "이미 사용 중인 닉네임입니다.";
    private static final String ERROR_MEMBER_NOT_FOUND = "존재하지 않는 회원입니다.";
    private static final String ERROR_MEMBER_NOT_FOUND_BY_NAME = "해당 닉네임의 회원이 없습니다.";
    private static final String ERROR_MEMBER_NOT_FOUND_BY_LOGIN_ID = "해당 ID의 회원이 없습니다.";
    private static final String ERROR_LOGIN_MEMBER_NOT_FOUND = "가입되지 않은 회원입니다.";

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(String loginId, String password, String name) {
        validateDuplicateLoginId(loginId);
        validateDuplicateName(name);
        Member member = new Member(loginId, password, name);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(m -> {
                    throw new IllegalArgumentException(ERROR_LOGIN_ID_DUPLICATED);
                });
    }

    private void validateDuplicateName(String name) {
        memberRepository.findByName(name)
                .ifPresent(m -> {
                    throw new IllegalArgumentException(ERROR_NAME_DUPLICATED);
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MEMBER_NOT_FOUND));
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MEMBER_NOT_FOUND_BY_NAME));
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MEMBER_NOT_FOUND_BY_LOGIN_ID));
    }

    public Member login(String loginId, String password) {
        Member loginMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_LOGIN_MEMBER_NOT_FOUND));

        if (loginMember.getPassword().equals(password)) {
            return loginMember;
        }
        return null;
    }
}
