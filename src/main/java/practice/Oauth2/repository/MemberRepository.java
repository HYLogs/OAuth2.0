package practice.Oauth2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.Oauth2.domain.member.Member;

import java.util.List;



public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member getOne(Long id);
    public List<Member> findByMemberProviderId(String MemberProviderId);
}
