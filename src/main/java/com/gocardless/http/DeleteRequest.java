package com.gocardless.http;

/**
 * Base class for DELETE requests.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class DeleteRequest<T> extends ApiRequest<T> {
    protected DeleteRequest(HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Executes this request.
     *
     * Returns the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public T execute() {
        return getHttpClient().executeWithRetries(this);
    }

    /**
     * Executes this request.
     *
     * Returns a {@link com.gocardless.http.ApiResponse} that wraps the
     * response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public ApiResponse<T> executeWrapped() {
        return getHttpClient().executeWrapped(this);
    }

    @Override
    protected T parseResponse(String responseBody, ResponseParser responseParser) {
        return responseParser.parseSingle(responseBody, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "DELETE";
    }

    protected abstract Class<T> getResponseClass();
}
