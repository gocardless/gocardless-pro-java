package com.gocardless.errors;

/**
 * Exception thrown when user is not permitted to do desired action.
 */
public class PermissionException extends InvalidApiUsageException {
    PermissionException(ApiErrorResponse error) {
        super(error);
    }
}
