package com.gocardless.errors;

/**
 * Exception thrown when the action you are trying to perform is invalid due to
 * the state of the resource you are requesting it on.
 */
public class InvalidStateException extends GoCardlessApiException {
    InvalidStateException(ApiErrorResponse error) {
        super(error);
    }
}
