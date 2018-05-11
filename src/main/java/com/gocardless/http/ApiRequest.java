package com.gocardless.http;

import java.io.Reader;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import com.squareup.okhttp.HttpUrl;

/**
 * Base class for API requests.
 *
 * @param <T> the type of the item returned by this request.
 */
abstract class ApiRequest<T> {
    // We use automatic serialization to build the request body from an `ApiRequest`'s
    // instance variables. Declaring these as transient means they are not included in
    // this serialization.
    private transient final HttpClient httpClient;
    private transient final Map<String, String> customHeaders;

    ApiRequest(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.customHeaders = Maps.newHashMap();
    }

    HttpUrl getUrl(UrlFormatter urlFormatter) {
        return urlFormatter.formatUrl(getPathTemplate(), getPathParams(), getQueryParams());
    }

    protected Map<String, String> getPathParams() {
        return ImmutableMap.of();
    }

    protected Map<String, Object> getQueryParams() {
        return ImmutableMap.of();
    }

    HttpClient getHttpClient() {
        return httpClient;
    }

    protected String getRequestEnvelope() {
        return getEnvelope();
    }

    // In subclasses, we define a `withHeader` method which returns the mutated request,
    // providing a nicer API which is more consistent with the rest of the library. We
    // can't do that here, as we don't know the concrete return type.
    protected final void addHeader(String headerName, String headerValue) {
        this.customHeaders.put(headerName, headerValue);
    }

    protected final Map<String, String> getCustomHeaders() {
        return ImmutableMap.copyOf(this.customHeaders);
    }

    protected Map<String, String> getHeaders() {
        return this.getCustomHeaders();
    };

    protected abstract String getPathTemplate();

    protected abstract String getMethod();

    protected abstract String getEnvelope();

    protected abstract boolean hasBody();

    protected abstract T parseResponse(Reader stream, ResponseParser responseParser);
}
