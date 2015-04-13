package com.gocardless.pro.http;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

public abstract class HttpRequest<T> {
    private transient final HttpClient httpClient;

    public HttpRequest(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public T execute() throws IOException {
        return httpClient.execute(this);
    }

    URL getUrl(UrlFormatter urlFormatter) {
        return urlFormatter.formatUrl(getPathTemplate(), getPathParams(), getQueryParams());
    }

    protected Map<String, String> getPathParams() {
        return ImmutableMap.of();
    }

    protected Map<String, Object> getQueryParams() {
        return ImmutableMap.of();
    }

    protected abstract String getPathTemplate();

    protected abstract String getMethod();

    protected abstract String getEnvelope();

    protected abstract boolean hasBody();

    protected abstract T parseResponse(Reader stream, ResponseParser responseParser);
}
