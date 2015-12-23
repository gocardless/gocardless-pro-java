package com.gocardless.http;

import com.google.common.collect.ImmutableMap;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for POST requests.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class PostRequest<T> extends ApiRequest<T> {
    private final transient Map<String, String> headers = new HashMap<>();

    protected PostRequest(HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Executes this request.
     *
     * Returns the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public T execute() {
        return getHttpClient().execute(this);
    }

    /**
     * Executes this request.
     *
     * Returns a {@link com.gocardless.http.ApiResponse} that wraps the
     * response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public ApiResponse<T> executeWrapped() {
        return getHttpClient().executeWrapped(this);
    }

    public PostRequest<T> withIdempotentKey(final String key)
    {
        headers.put("Idempotency-Key", key);
        return this;
    }

    @Override
    protected T parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "POST";
    }

    @Override
    Map<String, String> getHeaders()
    {
        return ImmutableMap.copyOf(headers);
    }

    protected abstract Class<T> getResponseClass();
}
