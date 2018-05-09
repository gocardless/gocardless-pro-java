package com.gocardless.http;

import java.util.List;

import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class ListRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldPerformListRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/page.json");
        DummyListRequest<ListResponse<DummyItem>> request =
                DummyListRequest.pageRequest(http.client());
        ListResponse<DummyItem> result = request.execute();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
        http.assertRequestMade("GET", "/dummy?id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnNetworkFailure() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/page.json");
        DummyListRequest<ListResponse<DummyItem>> request =
                DummyListRequest.pageRequest(http.client());
        ListResponse<DummyItem> result = request.execute();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
        http.assertRequestMade("GET", "/dummy?id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnInternalError() throws Exception {
        http.enqueueResponse(500, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/page.json");
        DummyListRequest<ListResponse<DummyItem>> request =
                DummyListRequest.pageRequest(http.client());
        ListResponse<DummyItem> result = request.execute();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
        http.assertRequestMade("GET", "/dummy?id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformWrappedListRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/page.json", ImmutableMap.of("foo", "bar"));
        DummyListRequest<ListResponse<DummyItem>> request =
                DummyListRequest.pageRequest(http.client());
        ApiResponse<ListResponse<DummyItem>> result = request.executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().getItems()).hasSize(2);
        http.assertRequestMade("GET", "/dummy?id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldBeAbleToIterateThroughList() throws Exception {
        http.enqueueResponse(200, "fixtures/first-page.json");
        http.enqueueResponse(200, "fixtures/last-page.json");
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        Iterable<DummyItem> iterable = request.execute();
        List<DummyItem> result = Lists.newArrayList(iterable);
        assertThat(result).hasSize(3);
        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(111);
        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(222);
        assertThat(result.get(2).stringField).isEqualTo("baz");
        assertThat(result.get(2).intField).isEqualTo(333);
        http.assertRequestMade("GET", "/dummy?id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
        http.assertRequestMade("GET", "/dummy?after=ID123&id=123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldNotAllowExecuteWrappedWhenIterating() {
        DummyListRequest<Iterable<DummyItem>> request =
                DummyListRequest.iterableRequest(http.client());
        exception.expect(IllegalStateException.class);
        request.executeWrapped();
    }

    static class DummyListRequest<S> extends ListRequest<S, DummyItem> {
        private DummyListRequest(HttpClient httpClient, ListRequestExecutor<S, DummyItem> executor) {
            super(httpClient, executor);
        }

        @Override
        protected ImmutableMap<String, Object> getQueryParams() {
            return ImmutableMap.<String, Object>builder().putAll(super.getQueryParams())
                    .put("id", "123").build();
        }

        @Override
        protected String getPathTemplate() {
            return "/dummy";
        }

        @Override
        protected String getEnvelope() {
            return "items";
        }

        @Override
        protected TypeToken<List<DummyItem>> getTypeToken() {
            return new TypeToken<List<DummyItem>>() {};
        }

        static DummyListRequest<ListResponse<DummyItem>> pageRequest(HttpClient httpClient) {
            return new DummyListRequest<>(httpClient, ListRequest.<DummyItem>pagingExecutor());
        }

        static DummyListRequest<Iterable<DummyItem>> iterableRequest(HttpClient httpClient) {
            return new DummyListRequest<>(httpClient, ListRequest.<DummyItem>iteratingExecutor());
        }
    }
}
