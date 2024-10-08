package org.barcord.barcode_service.advice;

import org.barcord.barcode_service.api.service.kakako.response.KakaoLoginResponse;
import org.barcord.barcode_service.exception.KakaoFilterException;
import org.barcord.barcode_service.exception.KakaoLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({KakaoLoginException.class, KakaoFilterException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<KakaoLoginResponse> kakaoLoginException(KakaoLoginException ex) {
        return ResponseEntity.status(ex.getErrorCode())
                .body(KakaoLoginResponse.builder()
                        .message(ex.getMessage())
                        .status(ex.getErrorCode())
                        .build());
    }

}
