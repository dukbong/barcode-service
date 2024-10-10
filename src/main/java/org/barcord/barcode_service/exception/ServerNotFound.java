package org.barcord.barcode_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerNotFound extends RuntimeException {

    private final HttpStatus errorCode;

    public ServerNotFound(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
