package org.barcord.barcode_service.advice;

import org.barcord.barcode_service.exception.ServerNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ServerNotFound.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> ServerNotFoundUser(ServerNotFound ex) {
        return ResponseEntity.status(ex.getErrorCode())
                .body(ex.getMessage());
    }

}
