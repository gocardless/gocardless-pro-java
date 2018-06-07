package com.gocardless.errors;

import com.gocardless.GoCardlessException;

/**
 * Exception thrown when a response is returned from the API which isn't valid JSON (for
 * example, an HTML error page returned from a load balancer)
 */
public class MalformedResponseException extends GoCardlessException {
    private final String responseBody;

    public MalformedResponseException(String responseBody) {
        super("Malformed response received from server");
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
