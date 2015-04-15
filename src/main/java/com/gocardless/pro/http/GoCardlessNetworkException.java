package com.gocardless.pro.http;

import com.gocardless.pro.GoCardlessException;

/**
 * Exception thrown due to a network error.
 */
public class GoCardlessNetworkException extends GoCardlessException {
    GoCardlessNetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
