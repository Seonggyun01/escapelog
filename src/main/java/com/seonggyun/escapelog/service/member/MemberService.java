package com.seonggyun.escapelog.service.member;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.repository.MemberRepository;
import com.seonggyun.escapelog.service.member.exception.MemberServiceErrorCode;
import com.seonggyun.escapelog.service.member.exception.MemberServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
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
                    throw new MemberServiceException(MemberServiceErrorCode.DUPLICATE_LOGIN_ID);
                });
    }

    private void validateDuplicateName(String name) {
        memberRepository.findByName(name)
                .ifPresent(m -> {
                    throw new MemberServiceException(MemberServiceErrorCode.DUPLICATE_NAME);
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberServiceException(MemberServiceErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(() -> new MemberServiceException(MemberServiceErrorCode.MEMBER_NOT_FOUND_BY_NAME));
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberServiceException(MemberServiceErrorCode.MEMBER_NOT_FOUND_BY_LOGIN_ID));
    }

    public Member login(String loginId, String password) {
        Member loginMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberServiceException(MemberServiceErrorCode.MEMBER_NOT_FOUND_BY_LOGIN_ID));

        if (loginMember.getPassword().equals(password)) {
            return loginMember;
        }
        return null;
    }
}
