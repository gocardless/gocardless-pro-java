package com.gocardless.pro.exceptions;

import java.util.List;

import com.gocardless.pro.exceptions.ApiErrorResponse.ApiError;
import com.gocardless.pro.exceptions.ApiErrorResponse.ErrorType;

public class GoCardlessApiException extends GoCardlessException {
    private final ApiErrorResponse error;

    public GoCardlessApiException(ApiErrorResponse error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorType getType() {
        return error.getType();
    }

    public String getDocumentationUrl() {
        return error.getDocumentationUrl();
    }

    public String getRequestId() {
        return error.getRequestId();
    }

    public int getCode() {
        return error.getCode();
    }

    public List<ApiError> getErrors() {
        return error.getErrors();
    }
}
