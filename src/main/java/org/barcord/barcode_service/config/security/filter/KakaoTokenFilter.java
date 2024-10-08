package org.barcord.barcode_service.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.dto.KakaoInfoResponse;
import org.barcord.barcode_service.api.service.kakako.dto.KakaoRefreshTokenResponse;
import org.barcord.barcode_service.exception.KakaoFilterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KakaoTokenFilter extends OncePerRequestFilter {

    @Value("${kakao.kauth_user_url}")
    private String kauthUserUrl;
    @Value("${kakao.kauth_token_url}")
    private String kauthTokenUrl;
    @Value("${kakao.client_id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
            ResponseEntity<KakaoInfoResponse> kakaoInfoResponse = isAccessTokenValid(accessToken);
            if (!kakaoInfoResponse.getStatusCode().is2xxSuccessful()) {
                int statusCode = kakaoInfoResponse.getStatusCode().value();
                if (statusCode == 400) {
                    throw new KakaoFilterException("카카오 서비스의 일시적 장애 또는 요청 정보가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
                }
                if (statusCode == 401) {
                    String refreshToken = request.getHeader("Kakao-Refresh-Token");
                    if (refreshToken != null) {
                        ResponseEntity<KakaoRefreshTokenResponse> refreshTokenResponse = refreshAccessToken(refreshToken);
                        if (refreshTokenResponse.getStatusCode().is2xxSuccessful()) {
                            if (refreshTokenResponse.getBody() != null) {
                                String newAccessToken = refreshTokenResponse.getBody().getAccessToken();
                                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
                                if (refreshTokenResponse.getBody().getRefreshToken() == null) {
                                    response.setHeader("Kakao-Refresh-Token", refreshToken);
                                } else {
                                    response.setHeader("Kakao-Refresh-Token", refreshTokenResponse.getBody().getRefreshToken());
                                }
                            } else {
                                throw new KakaoFilterException("서버에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        } else {
                            throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
                        }
                    } else {
                        throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
                    }
                }
            } else {
                // 원인 권한이 없어서 그렇다. 403에러 10-08일 여기까지
                Authentication authentication = new UsernamePasswordAuthenticationToken(kakaoInfoResponse.getBody().getId(), null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
        }
        return accessToken.substring(7);
    }

    private void validateAccessToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    private void handleKakaoInfoResponse(HttpServletRequest request,
                                         HttpServletResponse response,
                                         ResponseEntity<KakaoInfoResponse> kakaoInfoResponse) {
        if (kakaoInfoResponse.getStatusCode().is2xxSuccessful()) {
            return;
        }

        int statusCode = kakaoInfoResponse.getStatusCode().value();
        switch (statusCode) {
            case 400:
                throw new KakaoFilterException("카카오 서비스의 일시적 장애 또는 요청 정보가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
            case 401:
                handleRefreshToken(request, response);
                break;
            default:
                throw new KakaoFilterException("서버에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Kakao-Refresh-Token");
        if (refreshToken == null) {
            throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
        }

        ResponseEntity<KakaoRefreshTokenResponse> refreshTokenResponse = refreshAccessToken(refreshToken);
        if (!refreshTokenResponse.getStatusCode().is2xxSuccessful()) {
            throw new KakaoFilterException("유효하지 않은 요청입니다.", HttpStatus.UNAUTHORIZED);
        }

        if (refreshTokenResponse.getBody() != null) {
            String newAccessToken = refreshTokenResponse.getBody().getAccessToken();
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
            String newRefreshToken = refreshTokenResponse.getBody().getRefreshToken();
            response.setHeader("Kakao-Refresh-Token", newRefreshToken != null ? newRefreshToken : refreshToken);
        } else {
            throw new KakaoFilterException("서버에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<KakaoRefreshTokenResponse> refreshAccessToken(String refreshToken) {
        String url = UriComponentsBuilder.fromHttpUrl(kauthTokenUrl)
                .path("oauth/token")
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", refreshToken)
                .queryParam("client_id", clientId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                KakaoRefreshTokenResponse.class);
    }

    private ResponseEntity<KakaoInfoResponse> isAccessTokenValid(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(kauthUserUrl)
                .path("v1/user/access_token_info")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        return restTemplate.exchange(url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoInfoResponse.class);
    }
}
