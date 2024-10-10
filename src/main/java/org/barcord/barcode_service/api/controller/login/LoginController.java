package org.barcord.barcode_service.api.controller.login;

import org.barcord.barcode_service.api.service.kakako.dto.CustomDefaultOAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/success")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + ((CustomDefaultOAuth2User) oAuth2User).getAccessToken()) // Kakao accessToken 헤더로 설정
                .body("로그인 성공"); // 필요시 추가 정보를 반환
    }

    @GetMapping("/failure")
    public ResponseEntity<String> loginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }
}
