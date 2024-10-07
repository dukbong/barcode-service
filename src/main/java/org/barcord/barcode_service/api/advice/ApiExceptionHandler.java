package org.barcord.barcode_service.api.advice;

import org.barcord.barcode_service.api.ApiResponse;
import org.barcord.barcode_service.exception.DuplicationUsername;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DuplicationUsername.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> duplicationUsername(DuplicationUsername ex) {
        return ApiResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                null
        );
    }
}
