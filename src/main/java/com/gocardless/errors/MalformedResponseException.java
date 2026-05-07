package com.gocardless.errors;

import com.gocardless.GoCardlessException;

/**
 * Exception thrown when a response is returned from the API which isn't valid JSON (for example, an
 * HTML error page returned from a load balancer)
 */
public class MalformedResponseException extends GoCardlessException {
    private static final int UNKNOWN_STATUS_CODE = -1;
    private static final int BODY_PREVIEW_MAX_LENGTH = 500;
    private final int statusCode;
    private final String responseBody;

    public MalformedResponseException(String responseBody) {
        this(UNKNOWN_STATUS_CODE, responseBody);
    }

    public MalformedResponseException(int statusCode, String responseBody) {
        super(buildMessage(statusCode, responseBody));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /**
     * @return the HTTP status code of the malformed response, or -1 if unknown.
     */
    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private static String buildMessage(int statusCode, String responseBody) {
        StringBuilder message = new StringBuilder("Malformed response received from server");
        if (statusCode != UNKNOWN_STATUS_CODE) {
            message.append(" (HTTP ").append(statusCode).append(")");
        }
        if (responseBody != null && !responseBody.isEmpty()) {
            String preview = responseBody.length() > BODY_PREVIEW_MAX_LENGTH
                    ? responseBody.substring(0, BODY_PREVIEW_MAX_LENGTH) + "..."
                    : responseBody;
            message.append(": ").append(preview);
        }
        return message.toString();
    }
}
