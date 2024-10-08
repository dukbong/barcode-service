package org.barcord.barcode_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KakaoLoginException extends RuntimeException {

    private final HttpStatus errorCode;

    public KakaoLoginException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
