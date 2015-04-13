package com.gocardless.pro.http;

import java.io.Reader;

public abstract class PostRequest<T> extends HttpRequest<T> {
    public PostRequest(HttpClient httpClient) {
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
