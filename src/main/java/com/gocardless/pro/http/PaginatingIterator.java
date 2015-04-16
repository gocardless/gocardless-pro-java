package com.gocardless.pro.http;

import java.util.List;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;

class PaginatingIterator<T> extends AbstractIterator<T> {
    private final ListRequest<T> request;
    private List<T> items;
    private String nextCursor;

    PaginatingIterator(ListRequest<T> request) {
        this.request = request;
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
        ListResponse<T> response = request.execute();
        items = Lists.newArrayList(response.getItems());
        nextCursor = response.getAfter();
    }
}
