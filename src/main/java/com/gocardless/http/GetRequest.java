package com.gocardless.http;

import java.io.InputStream;

/**
 * Base class for GET requests that return a single item.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class GetRequest<S, T> extends HttpRequest<T> {
    private final GetRequestExecutor<S, T> executor;

    protected GetRequest(HttpClient httpClient, GetRequestExecutor<S, T> executor) {
        super(httpClient);
        this.executor = executor;
    }

    /**
     * Executes this request.
     *
     * Returns the API response.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public S execute() {
        return executor.execute(this, getHttpClient());
    }

    @Override
    protected T parseResponse(InputStream stream, ResponseParser responseParser) {
        return responseParser.parseSingle(stream, getEnvelope(), getResponseClass());
    }

    @Override
    protected final String getMethod() {
        return "GET";
    }

    @Override
    protected final boolean hasBody() {
        return false;
    }

    protected abstract Class<T> getResponseClass();

    public interface GetRequestExecutor<S, T> {
        S execute(GetRequest<S, T> request, HttpClient client);
    }

    public static <T> GetRequestExecutor<T, T> jsonExecutor() {
        return new GetRequestExecutor<T, T>() {
            @Override
            public T execute(GetRequest<T, T> request, HttpClient client) {
                return client.execute(request);
            }
        };
    }

    public static <T> GetRequestExecutor<InputStream, T> downloadExecutor(final String contentType) {
        return new GetRequestExecutor<InputStream, T>() {
            @Override
            public InputStream execute(GetRequest<InputStream, T> request, HttpClient client) {
                return client.rawExecute(request, contentType);
            }
        };
    }
}
