package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.HttpTestUtil.DummyItem;
import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;

public class DeleteRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldPerformDeleteRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyDeleteRequest().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("DELETE", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldRetryOnNetworkFailure() throws Exception {
        http.enqueueNetworkFailure();
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result =
                new DummyDeleteRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        // The first request isn't "made" at all as the socket doesn't accept the
        // connection. This tests that we send our headers on the retry.
        http.assertRequestMade("DELETE", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldRetryOnInternalError() throws Exception {
        http.enqueueResponse(500, "fixtures/internal_error.json");
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result =
                new DummyDeleteRequest().withHeader("Accept-Language", "fr-FR").execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("DELETE", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
        http.assertRequestMade("DELETE", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldPerformDeleteRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyDeleteRequestWithBody().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("DELETE", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    public void shouldPerformWrappedDeleteRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        ApiResponse<DummyItem> result =
                new DummyDeleteRequest().withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("DELETE", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    public void shouldPerformWrappedDeleteRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        ApiResponse<DummyItem> result = new DummyDeleteRequestWithBody()
                .withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("DELETE", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    private class DummyDeleteRequest extends DeleteRequest<DummyItem> {
        public DummyDeleteRequest() {
            super(http.client());
        }

        public DummyDeleteRequest withHeader(String headerName, String headerValue) {
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

    private class DummyDeleteRequestWithBody extends DummyDeleteRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
