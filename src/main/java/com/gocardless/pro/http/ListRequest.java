package com.gocardless.pro.http;

import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.List;

public abstract class ListRequest<T> extends HttpRequest<List<T>> {
    private final String envelope;
    private final TypeToken<List<T>> typeToken;

    public ListRequest(HttpClient httpClient, String pathTemplate, String envelope,
            TypeToken<List<T>> typeToken) {
        super(httpClient, pathTemplate);

        this.envelope = envelope;
        this.typeToken = typeToken;
    }

    @Override
    List<T> parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseMultiple(stream, envelope, typeToken);
    }
}
