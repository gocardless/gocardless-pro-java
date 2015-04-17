package com.gocardless.errors;

import com.google.gson.annotations.SerializedName;

/**
 * Types of error that can be returned by the API.
 */
public enum ErrorType {
    /**
     * An internal error occurred while processing your request. This should be
     * reported to our support team with the id, so we can resolve the issue.
     */
    @SerializedName("gocardless")
    GOCARDLESS,
    /**
     * This is an error with the request you made. It could be an invalid URL, the
     * authentication header could be missing, invalid, or grant insufficient
     * permissions, you may have reached your rate limit, or the syntax of your
     * request could be incorrect. The errors will give more detail of the specific
     * issue.
     */
    @SerializedName("invalid_api_usage")
    INVALID_API_USAGE,
    /**
     * The action you are trying to perform is invalid due to the state of the
     * resource you are requesting it on. For example, a payment you are trying to
     * cancel might already have been submitted. The errors will give more details.
     */
    @SerializedName("invalid_state")
    INVALID_STATE,
    /**
     * The parameters submitted with your request were invalid. Details of which
     * fields were invalid and why are included in the response.
     */
    @SerializedName("validation_failed")
    VALIDATION_FAILED
}
