package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a webhook resource returned from the API.
 *
 * Basic description of a webhook
 */
public class Webhook {
    private Webhook() {
        // blank to prevent instantiation
    }

    private Map<String, Object> requestHeaders;
    private Integer responseCode;
    private Boolean responseHeadersCountTruncated;
    private Boolean isTest;
    private String createdAt;
    private String responseBody;
    private String id;
    private String requestBody;
    private Boolean successful;
    private Boolean responseBodyTruncated;
    private Map<String, Object> responseHeaders;
    private Boolean responseHeadersContentTruncated;
    private String url;

    /**
     * The request headers sent with the webhook
     */
    public Map<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * The response code from the webhook request
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * Boolean indicating the number of response headers was truncated
     */
    public Boolean getResponseHeadersCountTruncated() {
        return responseHeadersCountTruncated;
    }

    /**
     * Boolean value showing whether this was a demo webhook for testing
     */
    public Boolean getIsTest() {
        return isTest;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * The body of the response from the webhook URL
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Unique identifier, beginning with "WB".
     */
    public String getId() {
        return id;
    }

    /**
     * The body of the request sent to the webhook URL
     */
    public String getRequestBody() {
        return requestBody;
    }

    /**
     * Boolean indicating whether the request was successful or failed
     */
    public Boolean getSuccessful() {
        return successful;
    }

    /**
     * Boolean value indicating the webhook response body was truncated
     */
    public Boolean getResponseBodyTruncated() {
        return responseBodyTruncated;
    }

    /**
     * The headers sent with the response from the webhook URL
     */
    public Map<String, Object> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Boolean indicating the content of response headers was truncated
     */
    public Boolean getResponseHeadersContentTruncated() {
        return responseHeadersContentTruncated;
    }

    /**
     * URL the webhook was POST-ed to
     */
    public String getUrl() {
        return url;
    }
}
