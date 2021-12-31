package com.gocardless.http;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Base class for GET requests that return multiple items.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class ListRequest<S, T> extends ApiRequest<ListResponse<T>> {
    private final transient ListRequestExecutor<S, T> executor;
    private final transient String method;
    private String after;
    private String before;
    private Integer limit;

    protected ListRequest(HttpClient httpClient, ListRequestExecutor<S, T> executor) {
        this(httpClient, executor, "GET");
    }

    protected ListRequest(HttpClient httpClient, ListRequestExecutor<S, T> executor,
            String method) {
        super(httpClient);
        this.method = method;
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

    /**
     * Executes this request.
     *
     * Returns a {@link com.gocardless.http.ApiResponse} that wraps the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    public ApiResponse<S> executeWrapped() {
        return executor.executeWrapped(this, getHttpClient());
    }

    @Override
    protected ListResponse<T> parseResponse(String responseBody, ResponseParser responseParser) {
        return responseParser.parsePage(responseBody, getEnvelope(), getTypeToken());
    }

    @Override
    protected final String getMethod() {
        return this.method;
    }

    @Override
    protected boolean hasBody() {
        return false;
    }

    @Override
    protected Map<String, Object> getQueryParams() {
        ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
        if (after != null) {
            params.put("after", after);
        }
        if (before != null) {
            params.put("before", before);
        }
        if (limit != null) {
            params.put("limit", limit);
        }
        return params.build();
    }

    protected abstract TypeToken<List<T>> getTypeToken();

    protected void setAfter(String after) {
        this.after = after;
    }

    protected void setBefore(String before) {
        this.before = before;
    }

    protected void setLimit(Integer limit) {
        this.limit = limit;
    }

    public interface ListRequestExecutor<S, T> {
        S execute(ListRequest<S, T> request, HttpClient client);

        ApiResponse<S> executeWrapped(ListRequest<S, T> request, HttpClient client);
    }

    public static <T> ListRequestExecutor<ListResponse<T>, T> pagingExecutor() {
        return new ListRequestExecutor<ListResponse<T>, T>() {
            @Override
            public ListResponse<T> execute(ListRequest<ListResponse<T>, T> request,
                    HttpClient client) {
                return client.executeWithRetries(request);
            }

            @Override
            public ApiResponse<ListResponse<T>> executeWrapped(
                    ListRequest<ListResponse<T>, T> request, HttpClient client) {
                return client.executeWrapped(request);
            }
        };
    }

    public static <T> ListRequestExecutor<Iterable<T>, T> iteratingExecutor() {
        return new ListRequestExecutor<Iterable<T>, T>() {
            @Override
            public Iterable<T> execute(ListRequest<Iterable<T>, T> request, HttpClient client) {
                return new PaginatingIterable<>(request, client);
            }

            @Override
            public ApiResponse<Iterable<T>> executeWrapped(ListRequest<Iterable<T>, T> request,
                    HttpClient client) {
                throw new IllegalStateException(
                        "executeWrapped not available when iterating through list responses");
            }
        };
    }
}
