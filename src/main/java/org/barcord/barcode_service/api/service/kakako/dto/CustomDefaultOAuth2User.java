package org.barcord.barcode_service.api.service.kakako.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomDefaultOAuth2User extends DefaultOAuth2User {

    private final String accessToken;

    public CustomDefaultOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String accessToken) {
        super(authorities, attributes, nameAttributeKey);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}