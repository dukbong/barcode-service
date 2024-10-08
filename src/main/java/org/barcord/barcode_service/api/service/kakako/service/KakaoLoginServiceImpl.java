package org.barcord.barcode_service.api.service.kakako.service;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.dto.KakaoInfoResponse;
import org.barcord.barcode_service.api.service.kakako.dto.KakaoResponse;
import org.barcord.barcode_service.api.service.kakako.dto.KakaoUserInfoResponse;
import org.barcord.barcode_service.api.service.kakako.response.KakaoLoginResponse;
import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.barcord.barcode_service.exception.KakaoLoginException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${kakao.client_id}")
    private String clientId;
    @Value("${kakao.kauth_token_url}")
    private String kauthTokenUrl;
    @Value("${kakao.kauth_user_url}")
    private String kauthUserUrl;
    @Value("${kakao.request_location}")
    private String requestLocation;

    @Override
    public String getRequestLocation() {
        return requestLocation;
    }

    @Override
    public KakaoLoginResponse getAccessToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(kauthTokenUrl)
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("code", code)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<KakaoResponse> response = restTemplate.exchange(url,
                    HttpMethod.POST,
                    new HttpEntity<>(null, headers),
                    KakaoResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                KakaoResponse kakaoResponse = response.getBody();
                if(kakaoResponse == null) throw new KakaoLoginException("로그인에 성공하였으나 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
                ResponseEntity<KakaoUserInfoResponse> kakaoUserInfoResponse = kakaoUserInfo(kakaoResponse.getAccessToken());

                userRepository.save(User.builder()
                        .kakaoId(String.valueOf(Objects.requireNonNull(kakaoUserInfoResponse.getBody()).getId()))
                        .nickName(kakaoUserInfoResponse.getBody().getProperties().getNickname())
                        .build());

                return KakaoLoginResponse.builder()
                        .status(HttpStatus.OK)
                        .message("로그인에 성공했습니다.")
                        .accessToken(kakaoResponse.getAccessToken())
                        .refreshToken(kakaoResponse.getRefreshToken())
                        .build();
            }
            if (response.getStatusCode().is4xxClientError()) {
                throw new KakaoLoginException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
            }
            if (response.getStatusCode().is5xxServerError()) {
                throw new KakaoLoginException("카카오 로그인 중 서버 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY);
            }
        } catch (RestClientException e) {
            throw new KakaoLoginException("카카오 서버와의 연결에 문제가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            throw new KakaoLoginException("로그인 중 오류가 발생헀습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

//    회원 정보
    private ResponseEntity<KakaoUserInfoResponse> kakaoUserInfo(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(kauthUserUrl)
                .path("/v2/user/me")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

       return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(null, headers),
                KakaoUserInfoResponse.class);

    }
}
