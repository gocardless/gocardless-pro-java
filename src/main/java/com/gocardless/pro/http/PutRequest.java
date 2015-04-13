package com.gocardless.pro.http;

import java.io.Reader;

public abstract class PutRequest<T> extends HttpRequest<T> {
    public PutRequest(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "PUT";
    }

    protected abstract Class<T> getResponseClass();
}
