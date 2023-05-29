package practice.Oauth2.controller;

import practice.Oauth2.domain.member.Member;
import practice.Oauth2.dto.PrincipalDetails;
import practice.Oauth2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    private final MemberService memberService;
    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "privatePage";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/update")
    public String update(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null){
            Long id = principal.getMember().getId();
            Member member = memberService.updateMember(id);
            principal.setMember(member); // 권한은 업데이트가 바로 적용이 안됨 다시 로그인 필요
        }
        return "redirect:/";
    }
}
