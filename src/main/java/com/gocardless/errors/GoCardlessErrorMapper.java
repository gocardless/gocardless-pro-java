package com.gocardless.errors;

/**
 * Provides a mapping between API error responses and exceptions.  Users of this
 * library will not need to use this class.
 */
public class GoCardlessErrorMapper {
    /**
     * Maps an error response to an exception.
     *
     * @param error the error response to map
     */
    public static GoCardlessApiException toException(ApiErrorResponse error) {
        switch (error.getType()) {
            case GOCARDLESS:
                return new GoCardlessInternalException(error);
            case INVALID_API_USAGE:
                return new InvalidApiUsageException(error);
            case INVALID_STATE:
                return new InvalidStateException(error);
            case VALIDATION_FAILED:
                return new ValidationFailedException(error);
        }
        throw new IllegalStateException("Unknown error type: " + error.getType());
    }
}
