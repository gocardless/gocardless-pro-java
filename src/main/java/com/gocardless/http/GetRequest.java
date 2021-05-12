package com.gocardless.http;

/**
 * Base class for GET requests that return a single item.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class GetRequest<T> extends ApiRequest<T> {
    protected GetRequest(HttpClient httpClient) {
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
     * Returns a {@link com.gocardless.http.ApiResponse} that wraps the response entity.
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
        return "GET";
    }

    @Override
    protected final boolean hasBody() {
        return false;
    }

    protected abstract Class<T> getResponseClass();
}
