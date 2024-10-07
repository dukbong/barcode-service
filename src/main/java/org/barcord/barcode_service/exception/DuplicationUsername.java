package org.barcord.barcode_service.exception;

public class DuplicationUsername extends RuntimeException {

    public DuplicationUsername(String message) {
        super(message);
    }

}