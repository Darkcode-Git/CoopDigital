package com.cooperativa.exceptions;

public class CooperativaException extends RuntimeException {
    public CooperativaException(String message) {
        super(message);
    }

    public CooperativaException(String message, Throwable cause) {
        super(message, cause);
    }
}
