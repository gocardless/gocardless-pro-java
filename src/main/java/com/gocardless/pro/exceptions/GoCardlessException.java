package com.gocardless.pro.exceptions;

public class GoCardlessException extends RuntimeException {
    public GoCardlessException(String message) {
        super(message);
    }

    public GoCardlessException(String message, Throwable cause) {
        super(message, cause);
    }
}
