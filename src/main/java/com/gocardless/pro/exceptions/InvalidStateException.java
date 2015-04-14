package com.gocardless.pro.exceptions;

public class InvalidStateException extends GoCardlessApiException {
    public InvalidStateException(ApiErrorResponse error) {
        super(error);
    }
}
