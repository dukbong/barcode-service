package org.barcord.barcode_service.api.service.kakako;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.dto.CustomDefaultOAuth2User;
import org.barcord.barcode_service.api.service.kakako.role.KakaoRole;
import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        KakaoRole defaultRole = KakaoRole.MEMBER;

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(defaultRole.getRoleName());

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Object propertiesObject = oAuth2User.getAttribute("properties");
        String kakaoId = oAuth2User.getName();
        String nickname = null;
        if (propertiesObject instanceof Map<?, ?> propertiesMap) {
            nickname = propertiesMap.get("nickname").toString();
        } else {
            throw new IllegalStateException("Unexpected properties format");
        }

        saveOrUpdateUser(kakaoId, nickname, defaultRole);

        return new CustomDefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName, userRequest.getAccessToken().getTokenValue());
    }

    private void saveOrUpdateUser(String kakaoId, String nickName, KakaoRole role) {
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        if (user.isEmpty()) {
            User newUser = User.builder().kakaoId(kakaoId).nickName(nickName).role(role).build();
            userRepository.save(newUser);
        } else {
            User existingUser = user.get();
            if (!existingUser.getNickName().equals(nickName)) {
                existingUser.updateNickName(nickName);
            }
        }
    }

}
