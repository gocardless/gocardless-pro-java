package com.gocardless.errors;

/**
 * Exception thrown when number of api requests are exceeded configured rate limit.
 */
public class RateLimitException extends InvalidApiUsageException {
    RateLimitException(ApiErrorResponse error) {
        super(error);
    }
}
