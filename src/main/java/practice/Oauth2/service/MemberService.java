package practice.Oauth2.service;


import practice.Oauth2.domain.member.Member;
import practice.Oauth2.domain.member.Role;
import practice.Oauth2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 자동 생성
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member updateMember(Long memberId) {
        Member member = memberRepository.getOne(memberId);
        member.setName("NEW_Name");
        member.setRole(Role.ADMIN); // 권한은 업데이트가 바로 적용이 안됨 다시 로그인 필요
        return member;
    }
}
