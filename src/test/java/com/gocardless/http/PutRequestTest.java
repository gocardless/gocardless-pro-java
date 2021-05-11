package com.gocardless.http;

import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PutRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldPerformPutRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");

        DummyItem result = new DummyPutRequest().execute();

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);

        http.assertRequestMade("PUT", "/dummy", ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnNetworkFailure() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/single.json");

        DummyItem result = new DummyPutRequest()
            .withHeader("Accept-Language", "fr-FR")
            .execute();

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);

        // The first request isn't "made" at all as the socket doesn't accept the
        // connection. This tests that we send our headers on the retry.
        http.assertRequestMade("PUT", "/dummy", ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldRetryOnInternalError() throws Exception {
        http.enqueueResponse(500, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");

        DummyItem result = new DummyPutRequest()
            .withHeader("Accept-Language", "fr-FR")
            .execute();

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);

        http.assertRequestMade("PUT", "/dummy", ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
        http.assertRequestMade("PUT", "/dummy", ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldPerformPutRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");

        DummyItem result = new DummyPutRequestWithBody().execute();

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);

        http.assertRequestMade("PUT", "/dummy", "fixtures/single.json", ImmutableMap.of("Authorization", "Bearer token"));
    }

    public void shouldPerformWrappedPutRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");

        ApiResponse<DummyItem> result = new DummyPutRequest()
            .withHeader("Accept-Language", "fr-FR")
            .executeWrapped();

        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);

        http.assertRequestMade("PUT", "/dummy", "fixtures/single.json", ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    public void shouldPerformWrappedPutRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");

        ApiResponse<DummyItem> result = new DummyPutRequestWithBody()
            .withHeader("Accept-Language", "fr-FR")
            .executeWrapped();

        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);

        http.assertRequestMade("PUT", "/dummy", "fixtures/single.json", ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    private class DummyPutRequest extends PutRequest<DummyItem> {
        public DummyPutRequest() {
            super(http.client());
        }

        public DummyPutRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
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
        protected Class<DummyItem> getResponseClass() {
            return DummyItem.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    private class DummyPutRequestWithBody extends DummyPutRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
