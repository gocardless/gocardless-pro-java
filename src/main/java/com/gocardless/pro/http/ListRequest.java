package com.gocardless.pro.http;

import java.io.Reader;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public abstract class ListRequest<T> extends HttpRequest<List<T>> {
    public ListRequest(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected List<T> parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseMultiple(stream, getEnvelope(), getTypeToken());
    }

    @Override
    protected final String getMethod() {
        return "GET";
    }

    @Override
    protected final boolean hasBody() {
        return false;
    }

    protected abstract TypeToken<List<T>> getTypeToken();
}
