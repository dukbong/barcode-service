package org.barcord.barcode_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KakaoFilterException extends RuntimeException {

    private final HttpStatus errorCode;

    public KakaoFilterException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
