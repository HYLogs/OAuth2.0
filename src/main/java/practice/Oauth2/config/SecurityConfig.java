package practice.Oauth2.config;

import practice.Oauth2.domain.member.Role;
import practice.Oauth2.service.OAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable() // httpBasic 방식 대신 Jwt를 사용하기 때문에 disable로 설정
                .csrf().disable() // 세션 방식 로그인이 아니기 때문에 불필요한 위조방지를 꺼야한다.
                .cors() // 다른 도메인 (도메인 간 요청)을 가진 리소스에 액세스 할 수 있게하는 보안 메커니즘
            .and()
                .authorizeRequests()
                .requestMatchers("/private/**").authenticated() //private로 시작하는 uri는 로그인 필수
                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name()) //admin으로 시작하는 uri는 관리자 계정만 접근 가능
                .anyRequest().permitAll() //나머지 uri는 모든 접근 허용
            .and()
                .logout()
                .logoutSuccessUrl("/") // logout 성공시 이동할 url 설정
                .invalidateHttpSession(true).deleteCookies("JSESSIONID") // logout 후 쿠키제거
            .and()
                .oauth2Login()
                .loginPage("/loginForm") //로그인이 필요한데 로그인을 하지 않았다면 이동할 uri 설정
                .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
                .userInfoEndpoint()//로그인 완료 후 회원 정보 받기
                .userService(oAuth2MemberService)
            .and().and().build(); //로그인 후 받아온 유저 정보 처리
    }
}
