package com.gocardless.pro.http;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

public abstract class HttpRequest<T> {
    protected final HttpClient httpClient;
    protected final String pathTemplate;

    public HttpRequest(HttpClient httpClient, String pathTemplate) {
        this.httpClient = httpClient;
        this.pathTemplate = pathTemplate;
    }

    public T execute() throws IOException {
        return httpClient.get(this);
    }

    URL getUrl(UrlFormatter urlFormatter) {
        return urlFormatter.formatUrl(pathTemplate, getPathParams(), getQueryParams());
    }

    protected Map<String, String> getPathParams() {
        return ImmutableMap.of();
    }

    protected Map<String, Object> getQueryParams() {
        return ImmutableMap.of();
    }

    abstract T parseResponse(Reader stream, ResponseParser responseParser);
}
