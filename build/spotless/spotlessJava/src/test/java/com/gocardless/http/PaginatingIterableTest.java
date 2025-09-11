package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.HttpTestUtil.DummyItem;
import com.gocardless.http.ListRequestTest.DummyListRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;

public class PaginatingIterableTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldIterateThroughPages() throws Exception {
        http.enqueueResponse(200, "fixtures/first-page.json");
        http.enqueueResponse(200, "fixtures/last-page.json");
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        request.setLimit(2);
        Iterable<DummyItem> iterable = new PaginatingIterable<>(request, http.client());
        List<DummyItem> items = Lists.newArrayList(iterable);
        assertThat(items).hasSize(3);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
        assertThat(items.get(2).stringField).isEqualTo("baz");
        assertThat(items.get(2).intField).isEqualTo(333);
        http.assertRequestMade("GET", "/dummy?limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
        http.assertRequestMade("GET", "/dummy?after=ID123&limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldCopeWithEmptyLastPage() throws Exception {
        http.enqueueResponse(200, "fixtures/first-page.json");
        http.enqueueResponse(200, "fixtures/empty-page.json");
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        request.setLimit(2);
        Iterable<DummyItem> iterable = new PaginatingIterable<>(request, http.client());
        List<DummyItem> items = Lists.newArrayList(iterable);
        assertThat(items).hasSize(2);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
        http.assertRequestMade("GET", "/dummy?limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
        http.assertRequestMade("GET", "/dummy?after=ID123&limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnNetworkFailure() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/first-page.json");
        http.enqueueResponse(200, "fixtures/last-page.json");
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        request.setLimit(2);
        Iterable<DummyItem> iterable = new PaginatingIterable<>(request, http.client());
        List<DummyItem> items = Lists.newArrayList(iterable);
        assertThat(items).hasSize(3);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
        assertThat(items.get(2).stringField).isEqualTo("baz");
        assertThat(items.get(2).intField).isEqualTo(333);
        http.takeRequest();
        http.assertRequestMade("GET", "/dummy?limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
        http.assertRequestMade("GET", "/dummy?after=ID123&limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnInternalError() throws Exception {
        http.enqueueResponse(200, "fixtures/first-page.json");
        http.enqueueResponse(500, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/last-page.json");
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        request.setLimit(2);
        Iterable<DummyItem> iterable = new PaginatingIterable<>(request, http.client());
        List<DummyItem> items = Lists.newArrayList(iterable);
        assertThat(items).hasSize(3);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
        assertThat(items.get(2).stringField).isEqualTo("baz");
        assertThat(items.get(2).intField).isEqualTo(333);
        http.assertRequestMade("GET", "/dummy?limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
        http.assertRequestMade("GET", "/dummy?after=ID123&limit=2&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }
}
