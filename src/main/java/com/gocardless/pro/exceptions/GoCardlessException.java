package com.gocardless.pro.exceptions;

/**
 * Base class for API exceptions.
 */
public abstract class GoCardlessException extends RuntimeException {
    protected GoCardlessException(String message) {
        super(message);
    }

    protected GoCardlessException(String message, Throwable cause) {
        super(message, cause);
    }
}
