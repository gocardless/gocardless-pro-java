package com.gocardless.http;

import java.io.Reader;

/**
 * Base class for GET requests that return a single item.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class GetRequest<T> extends HttpRequest<T> {
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
        return getHttpClient().execute(this);
    }

    /**
     * Executes this request.
     *
     * Returns a {@link com.gocardless.http.HttpResponse} that wraps the
     * response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public HttpResponse<T> executeWrapped() {
        return getHttpClient().executeWrapped(this);
    }

    @Override
    protected T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
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
