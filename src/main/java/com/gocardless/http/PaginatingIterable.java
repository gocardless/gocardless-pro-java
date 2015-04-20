package com.gocardless.http;

import java.util.Iterator;

class PaginatingIterable<T> implements Iterable<T> {
    private final ListRequest<?, T> request;
    private final HttpClient client;

    PaginatingIterable(ListRequest<?, T> request, HttpClient client) {
        this.request = request;
        this.client = client;
    }

    @Override
    public Iterator<T> iterator() {
        return new PaginatingIterator<>(request, client);
    }
}
