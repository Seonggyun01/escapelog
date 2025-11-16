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
    public Long join(String loginId, String password, String name){
        validateDuplicateLoginId(loginId);
        validateDuplicateName(name);
        Member member = new Member(loginId, password, name);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 사용 중인 로그인 ID입니다.");
                });
    }

    private void validateDuplicateName(String name) {
        memberRepository.findByName(name)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
                });
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
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    /**
     * 닉네임으로 단일 회원 조회 (name이 unique라는 전제)
     */
    public Member findByName(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임의 회원이 없습니다."));
    }

    /**
     * loginId로 단일 회원 조회
     */
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 없습니다."));
    }

    /**
     * login 검증
     */
    public Member login(String loginId, String password) {

        Member loginMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("이미 가입안된 사람입니다."));

        if(loginMember.getPassword().equals(password)){
            return loginMember;
        }
        return null;
    }
}
