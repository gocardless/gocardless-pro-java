package com.gocardless.http;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import java.util.List;

class PaginatingIterator<T> extends AbstractIterator<T> {
    private final ListRequest<?, T> request;
    private final HttpClient client;
    private List<T> items;
    private String nextCursor;

    PaginatingIterator(ListRequest<?, T> request, HttpClient client) {
        this.request = request;
        this.client = client;
        loadPage();
    }

    @Override
    protected T computeNext() {
        if (items.isEmpty() && nextCursor != null) {
            loadPage();
        }
        if (items.isEmpty()) {
            return endOfData();
        }
        T item = items.get(0);
        items.remove(0);
        return item;
    }

    private void loadPage() {
        request.setAfter(nextCursor);
        ListResponse<T> response = client.executeWithRetries(request);
        items = Lists.newArrayList(response.getItems());
        nextCursor = response.getAfter();
    }
}
