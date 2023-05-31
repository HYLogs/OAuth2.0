package practice.Oauth2.dto;

import lombok.Builder;
import practice.Oauth2.domain.member.Member;
import practice.Oauth2.domain.member.Role;

import java.util.Map;

public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String name;
    private String providerAndId;
    private String provider;
    private String providerId;
    private Role role;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String name, String providerAndId, String provider, String providerId, Role role) {
        this.attributes = attributes;
        this.name = name;
        this.providerAndId = providerAndId;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    // Google, Github 로그인 구별
    public  static OAuthAttributes of(String provider, Map<String, Object> attributes, Role role) throws Exception {
        switch (provider) {
            case "google":
                return ofGoogle(provider, attributes, role);
            case "github":
                return ofGithub(provider, attributes, role);
            default:
                throw new Exception("지원하지 않는 로그인입니다.");
        }


    }

    // Google 로그인 처리
    private static OAuthAttributes ofGoogle(String provider, Map<String, Object> attributes, Role role) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .name((String) attributes.get("name"))
                .providerAndId(provider + "_" + attributes.get("sub"))
                .provider(provider)
                .providerId((String) attributes.get("sub"))
                .role(role)
                .build();
    }

    // Github 로그인 처리
    private static OAuthAttributes ofGithub(String registrationId, Map<String, Object> attributes, Role role) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .name((String) attributes.get("name"))
                .providerAndId(registrationId + "_" + attributes.get("id"))
                .provider(registrationId)
                .providerId(String.valueOf(attributes.get("id")))
                .role(role)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .memberProviderId(providerAndId)
                .role(Role.USER)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
