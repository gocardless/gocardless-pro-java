package com.gocardless.http;

import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.HttpUrl;

import java.io.Reader;
import java.util.Map;

/**
 * Base class for API requests.
 *
 * @param <T> the type of the item returned by this request.
 */
abstract class ApiRequest<T> {
    private transient final HttpClient httpClient;

    ApiRequest(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    HttpUrl getUrl(UrlFormatter urlFormatter) {
        return urlFormatter.formatUrl(getPathTemplate(), getPathParams(), getQueryParams());
    }

    protected Map<String, String> getPathParams() {
        return ImmutableMap.of();
    }

    Map<String, Object> getQueryParams() {
        return ImmutableMap.of();
    }

    Map<String, String> getHeaders() {
        return ImmutableMap.of();
    }

    HttpClient getHttpClient() {
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
