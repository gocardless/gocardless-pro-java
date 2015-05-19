package com.gocardless.http;

import java.io.InputStream;

/**
 * Base class for POST requests.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class PostRequest<T> extends HttpRequest<T> {
    protected PostRequest(HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Executes this request.
     *
     * Returns the API response.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public T execute() {
        return getHttpClient().execute(this);
    }

    @Override
    protected T parseResponse(InputStream stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "POST";
    }

    protected abstract Class<T> getResponseClass();
}
