package com.gocardless.http;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;

/**
 * Base class for API responses.
 *
 * @param <T> the type of the resource within this response.
 */
public class ApiResponse<T> {
    private final T resource;
    private final int statusCode;
    private final Multimap<String, String> headers;

    public ApiResponse(T resource, int statusCode, Map<String, List<String>> headers) {
        this.resource = resource;
        this.statusCode = statusCode;
        this.headers = buildHeaderMap(headers);
    }

    public T getResource() {
        return resource;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Multimap<String, String> getHeaders() {
        return headers;
    }

    private static Multimap<String, String> buildHeaderMap(Map<String, List<String>> headers) {
        ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            builder.putAll(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
