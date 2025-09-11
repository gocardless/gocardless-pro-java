package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.errors.ValidationFailedException;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IdempotentPostRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldPerformPostRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result = new DummyPostRequest().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformPostRequestWithGeneratedIdempotencyKey() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result = new DummyPostRequest().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestIncludedHeader("Idempotency-Key");
    }

    @Test
    public void shouldPerformPostRequestWithGeneratedIdempotencyKeyAndCustomHeader()
            throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPostRequest request = new DummyPostRequest().withHeader("Accept-Language", "fr-FR");
        HttpTestUtil.DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestIncludedHeader("Idempotency-Key");
        http.enqueueResponse(200, "fixtures/single.json");
        request.execute();
        http.assertRequestIncludedHeader("Accept-Language");
    }

    @Test
    public void shouldPerformPostRequestWithSpecifiedIdempotencyKeyAndCustomHeader()
            throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result =
                new DummyPostRequest().withIdempotencyKey("i-am-the-one-and-only")
                        .withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Idempotency-Key",
                        "i-am-the-one-and-only", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldRetryRequestWhenServerRespondsWithInternalError() throws Exception {
        http.enqueueResponse(503, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPostRequest request = new DummyPostRequest();
        HttpTestUtil.DummyItem result = request.withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldRetryRequestWhenServerDoesNotRespond() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result =
                new DummyPostRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.takeRequest();
        // This tests that we send our headers on the retry.
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldHandleConflictByPerformingGet() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(409, "fixtures/conflict.json");
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result =
                new DummyPostRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.takeRequest();
        // This tests that we send our headers on the retry.
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
        http.assertRequestMade("GET", "/dummy/ID123",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldPropagateExceptionForNonConflictError() throws Exception {
        http.enqueueResponse(422, "fixtures/validation_failed.json");
        DummyPostRequest request = new DummyPostRequest();
        exception.expect(ValidationFailedException.class);
        request.execute();
    }

    @Test
    public void shouldPropagateExceptionIfTooManyFailures() {
        for (int i = 0; i < HttpClient.MAX_RETRIES; i++) {
            http.enqueueNetworkFailure();
        }
        DummyPostRequest request = new DummyPostRequest();
        exception.expect(GoCardlessNetworkException.class);
        request.execute();
    }

    @Test
    public void shouldUseSameIdempotencyKeyForFurtherRequestsIfSet() throws Exception {
        http.enqueueResponse(503, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");
        HttpTestUtil.DummyItem result =
                new DummyPostRequest().withHeader("Accept-Language", "fr-FR")
                        .withIdempotencyKey("i-am-the-one-and-only").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        // This tests that we send our headers on the retry.
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Idempotency-Key",
                        "i-am-the-one-and-only", "Accept-Language", "fr-FR"));
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Idempotency-Key",
                        "i-am-the-one-and-only", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldUseSameIdempotencyKeyForFurtherRequestsIfNotSet() throws Exception {
        http.enqueueResponse(503, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPostRequest request = new DummyPostRequest().withHeader("Accept-Language", "fr-FR");
        HttpTestUtil.DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        // This tests that we send our headers on the retry.
        Map<String, String> headers = request.getHeaders();
        String idempotencyKey = headers.get("Idempotency-Key");
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Idempotency-Key", idempotencyKey,
                        "Accept-Language", "fr-FR"));
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Idempotency-Key", idempotencyKey,
                        "Accept-Language", "fr-FR"));
    }

    private class DummyPostRequest extends IdempotentPostRequest<HttpTestUtil.DummyItem> {
        private int intField = 123;
        private String stringField = "foo";

        public DummyPostRequest() {
            super(http.client());
        }

        public DummyPostRequest withIdempotencyKey(String idempotencyKey) {
            setIdempotencyKey(idempotencyKey);
            return this;
        }

        public DummyPostRequest withHeader(String headerName, String headerValue) {
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
        protected Class<HttpTestUtil.DummyItem> getResponseClass() {
            return HttpTestUtil.DummyItem.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected GetRequest<HttpTestUtil.DummyItem> handleConflict(HttpClient httpClient,
                String id) {
            DummyGetRequest request = new DummyGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
            return request;
        }
    }

    private class DummyGetRequest extends GetRequest<HttpTestUtil.DummyItem> {
        private final String id;

        public DummyGetRequest(HttpClient httpClient, String id) {
            super(httpClient);
            this.id = id;
        }

        public DummyGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected ImmutableMap<String, String> getPathParams() {
            return ImmutableMap.of("id", id);
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
        protected Class<HttpTestUtil.DummyItem> getResponseClass() {
            return HttpTestUtil.DummyItem.class;
        }
    }
}
