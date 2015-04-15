package com.gocardless.pro.exceptions;

/**
 * Exception that is internal to the client.
 */
public class GoCardlessClientException extends GoCardlessException {
    public GoCardlessClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
