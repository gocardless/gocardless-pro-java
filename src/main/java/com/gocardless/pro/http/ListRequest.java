package com.gocardless.pro.http;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Base class for GET requests that return multiple items.
 *
 * @param <T> the type of the item returned by this request.
 */
public abstract class ListRequest<T> extends HttpRequest<ListResponse<T>> implements Iterable<T> {
    private String after;
    private String before;
    private Integer limit;

    protected ListRequest(HttpClient httpClient) {
        super(httpClient);
    }

    public Iterator<T> iterator() {
        return new PaginatingIterator<>(this);
    }

    @Override
    protected ListResponse<T> parseResponse(Reader stream, ResponseParser responseParser) {
        return responseParser.parsePage(stream, getEnvelope(), getTypeToken());
    }

    @Override
    protected final String getMethod() {
        return "GET";
    }

    @Override
    protected final boolean hasBody() {
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
}
