package com.gocardless.http;

import java.io.Reader;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Base class for HTTP requests.
 *
 * @param <T> the type of the item returned by this request.
 */
abstract class HttpRequest<T> {
    private transient final HttpClient httpClient;

    HttpRequest(HttpClient httpClient) {
        this.httpClient = httpClient;
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

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    protected String getRequestEnvelope() {
        return getEnvelope();
    }

    protected abstract String getPathTemplate();

    protected abstract String getMethod();

    protected abstract String getEnvelope();

    protected abstract boolean hasBody();

    protected abstract T parseResponse(Reader stream, ResponseParser responseParser);
}
