package org.barcord.barcode_service.api.controller.login;

import org.barcord.barcode_service.api.service.kakako.service.KakaoOAuth2UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2/code/kakao")
public class LoginController {
	
//    private final KakaoOAuth2UserService kakaoOAuth2UserService;

//    @GetMapping
//    public ResponseEntity<KakaoLoginResponse> login(@RequestParam("code") String code) {
//    	log.info(">>>>>>>>>>>>>>>");
//        KakaoLoginResponse kakaoLoginResponse = kakaoOAuth2UserService.getAccessToken(code);
//        return ResponseEntity.ok()
//                .header("Authorization", "Bearer " + kakaoLoginResponse.getAccessToken())
//                .header("Kakao-Refresh-Token", kakaoLoginResponse.getRefreshToken())
//                .body(kakaoLoginResponse);
//    }

//    @GetMapping("/kakao/request")
//    public String kakaoRequest() {
//        return kakaoLoginServiceImpl.getRequestLocation();
//    }
	
	
    @GetMapping("/success")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
//        String kakaoId = oAuth2User.getAttributes().get("id").toString();
//        String accessToken = oAuth2User.getAttribute("access_token"); // Kakao에서 반환된 accessToken
//        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok()
//                .header("Authorization", "Bearer " + accessToken) // Kakao accessToken 헤더로 설정
                .body("로그인 성공: "); // 필요시 추가 정보를 반환
    }

    @GetMapping("/failure")
    public ResponseEntity<String> loginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }
}
