package com.gocardless.pro.http;

import java.io.Reader;

/**
 * Base class for POST requests.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class PostRequest<T> extends HttpRequest<T> {
    protected PostRequest(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "POST";
    }

    protected abstract Class<T> getResponseClass();
}
