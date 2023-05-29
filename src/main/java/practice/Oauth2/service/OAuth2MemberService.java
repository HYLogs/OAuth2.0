package practice.Oauth2.service;

import org.springframework.stereotype.Service;
import practice.Oauth2.domain.member.Member;
import practice.Oauth2.domain.member.Role;
import practice.Oauth2.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import practice.Oauth2.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // name 생성
        String provider = userRequest.getClientRegistration().getRegistrationId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        Role role = Role.USER; //일반 유저

        List<Member> members = memberRepository.findByEmail(email);
        Member member;
        if (members.isEmpty()) { //최초 로그인
            member = Member.builder()
                    .name(name)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId).build();
            memberRepository.save(member);
        } else{ // 기존 고객
            member = members.get(0);
        }

        return new PrincipalDetails(member,  oAuth2User.getAttributes());
    }
}
