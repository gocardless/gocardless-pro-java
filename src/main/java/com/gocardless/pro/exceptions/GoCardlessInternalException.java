package com.gocardless.pro.exceptions;

public class GoCardlessInternalException extends GoCardlessApiException {
    public GoCardlessInternalException(ApiErrorResponse error) {
        super(error);
    }
}
