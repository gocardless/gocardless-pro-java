package com.gocardless.pro.http;

import java.io.Reader;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public abstract class ListRequest<T> extends HttpRequest<ListResponse<T>> {
    public ListRequest(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected ListResponse<T> parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parsePage(stream, getEnvelope(), getTypeToken());
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
