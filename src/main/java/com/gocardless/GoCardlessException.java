package com.gocardless;

/**
 * Base class for client library exceptions.
 */
public abstract class GoCardlessException extends RuntimeException {
    protected GoCardlessException() {
        super();
    }

    protected GoCardlessException(String message) {
        super(message);
    }

    protected GoCardlessException(String message, Throwable cause) {
        super(message, cause);
    }
}
