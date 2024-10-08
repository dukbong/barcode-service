package org.barcord.barcode_service.api.controller.login;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.response.KakaoLoginResponse;
import org.barcord.barcode_service.api.service.kakako.service.KakaoLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {

    private final KakaoLoginService kakaoLoginServiceImpl;

    @GetMapping
    public ResponseEntity<KakaoLoginResponse> login(@RequestParam("code") String code) {
        KakaoLoginResponse kakaoLoginResponse = kakaoLoginServiceImpl.getAccessToken(code);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + kakaoLoginResponse.getAccessToken())
                .header("Kakao-Refresh-Token", kakaoLoginResponse.getRefreshToken())
                .body(kakaoLoginResponse);
    }

    @GetMapping("/kakao/request")
    public String kakaoRequest() {
        return kakaoLoginServiceImpl.getRequestLocation();
    }

}
