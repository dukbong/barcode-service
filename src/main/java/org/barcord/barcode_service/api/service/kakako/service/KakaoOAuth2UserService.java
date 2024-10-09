package org.barcord.barcode_service.api.service.kakako.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
//    private final RestTemplate restTemplate;
//    
//  @Value("${kakao.client_id}")
//  private String clientId;
//  @Value("${kakao.kauth_token_url}")
//  private String kauthTokenUrl;
//  @Value("${kakao.kauth_user_url}")
//  private String kauthUserUrl;
//  @Value("${kakao.request_location}")
//  private String requestLocation;

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        String kakaoId = oAuth2User.getAttribute("id").toString(); 
//
//        Map<String, Object> properties = oAuth2User.getAttribute("properties");
//
//        String nickName = (String) properties.get("nickname");
//
////        saveOrUpdateUser(kakaoId, nickName);
//
//        return oAuth2User;
//    }
	
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
 
        // Role generate
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
 
        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        
        // DB 저장로직이 필요하면 추가
        String nickname = (String) ((Map<String, Object>) oAuth2User.getAttributes().get("properties")).get("nickname");
 
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }

	private void saveOrUpdateUser(String kakaoId, String nickName) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		if (user.isEmpty()) {
			User newUser = User.builder().kakaoId(kakaoId).nickName(nickName).build();
			userRepository.save(newUser);
		}
	}

//	public KakaoLoginResponse getAccessToken(String code) {
//		String url = UriComponentsBuilder.fromHttpUrl(kauthTokenUrl).path("/oauth/token")
//				.queryParam("grant_type", "authorization_code").queryParam("client_id", clientId)
//				.queryParam("code", code).toUriString();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
//
//		try {
//			ResponseEntity<KakaoResponse> response = restTemplate.exchange(url, HttpMethod.POST,
//					new HttpEntity<>(null, headers), KakaoResponse.class);
//
//			if (response.getStatusCode().is2xxSuccessful()) {
//				KakaoResponse kakaoResponse = response.getBody();
//				if (kakaoResponse == null)
//					throw new KakaoLoginException("로그인에 성공하였으나 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//
//				return KakaoLoginResponse.builder().status(HttpStatus.OK).message("로그인에 성공했습니다.")
//						.accessToken(kakaoResponse.getAccessToken()).refreshToken(kakaoResponse.getRefreshToken())
//						.build();
//			}
//			if (response.getStatusCode().is4xxClientError()) {
//				throw new KakaoLoginException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
//			}
//			if (response.getStatusCode().is5xxServerError()) {
//				throw new KakaoLoginException("카카오 로그인 중 서버 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY);
//			}
//		} catch (RestClientException e) {
//			throw new KakaoLoginException("카카오 서버와의 연결에 문제가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
//		} catch (Exception e) {
//			throw new KakaoLoginException("로그인 중 오류가 발생헀습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return null;
//	}
}
