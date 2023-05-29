package practice.Oauth2.domain.member;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; //기본키
    private String name; //유저 이름
    private String email; //유저 구글 이메일
    private String provider; //공급자 (google, facebook ...)
    private String providerId; //공급 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; //유저 권한 (관리자, 고객)

    @Builder
    public Member(String name, String email, Role role, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
