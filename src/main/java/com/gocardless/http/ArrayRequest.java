package com.gocardless.http;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Base class for ANY request that return multiple items.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class ArrayRequest<T> extends ApiRequest<ImmutableList<T>> {
    private transient String method;

    protected ArrayRequest(HttpClient httpClient, String method) {
        super(httpClient);
        this.method = method;
    }

    /**
     * Executes this request.
     *
     * Returns the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public ImmutableList<T> execute() {
        return getHttpClient().execute(this);
    }

    /**
     * Executes this request.
     *
     * Returns a {@link com.gocardless.http.ApiResponse} that wraps the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public ApiResponse<ImmutableList<T>> executeWrapped() {
        return getHttpClient().executeWrapped(this);
    }

    @Override
    protected ImmutableList<T> parseResponse(String responseBody, ResponseParser responseParser) {
        return responseParser.parseMultiple(responseBody, getEnvelope(), getTypeToken());
    }

    @Override
    protected final String getMethod() {
        return this.method;
    }

    @Override
    protected boolean hasBody() {
        if (this.method.equals("GET")) {
            return false;
        }
        return true;
    }

    protected abstract TypeToken<List<T>> getTypeToken();
}
