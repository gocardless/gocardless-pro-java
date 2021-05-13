package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.errors.InvalidApiUsageException;
import com.gocardless.http.HttpTestUtil.DummyItem;
import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GetRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldPerformGetRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyGetRequest().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("GET", "/dummy/123",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformWrappedGetRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json", ImmutableMap.of("foo", "bar"));
        ApiResponse<DummyItem> result =
                new DummyGetRequest().withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("GET", "/dummy/123",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldThrowOnApiError() throws Exception {
        http.enqueueResponse(400, "fixtures/invalid_api_usage.json");
        exception.expect(InvalidApiUsageException.class);
        exception.expectMessage("Invalid document structure");
        new DummyGetRequest().execute();
    }

    @Test
    public void shouldRetryOnNetworkFailure() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyGetRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        // The first request isn't "made" at all as the socket doesn't accept the
        // connection. This tests that we send our headers on the retry.
        http.assertRequestMade("GET", "/dummy/123",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldRetryOnInternalError() throws Exception {
        http.enqueueResponse(500, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyGetRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("GET", "/dummy/123",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
        http.assertRequestMade("GET", "/dummy/123",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    private class DummyGetRequest extends GetRequest<DummyItem> {
        public DummyGetRequest() {
            super(http.client());
        }

        public DummyGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected ImmutableMap<String, String> getPathParams() {
            return ImmutableMap.of("id", "123");
        }

        @Override
        protected String getPathTemplate() {
            return "/dummy/:id";
        }

        @Override
        protected String getEnvelope() {
            return "items";
        }

        @Override
        protected Class<DummyItem> getResponseClass() {
            return DummyItem.class;
        }
    }
}
