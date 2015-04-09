package com.gocardless.pro.http;

import java.io.Reader;

public abstract class GetRequest<T> extends HttpRequest<T> {
    private final String envelope;
    private final Class<T> responseClass;

    public GetRequest(HttpClient httpClient, String pathTemplate, String envelope,
            Class<T> responseClass) {
        super(httpClient, pathTemplate);
        this.envelope = envelope;
        this.responseClass = responseClass;
    }

    @Override
    T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, envelope, responseClass);
    }
}
