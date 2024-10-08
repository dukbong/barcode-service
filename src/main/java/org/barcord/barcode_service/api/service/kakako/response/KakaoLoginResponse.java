package org.barcord.barcode_service.api.service.kakako.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class KakaoLoginResponse {
    private HttpStatus status;
    private String message;
    private String accessToken;
    private String refreshToken;
}
