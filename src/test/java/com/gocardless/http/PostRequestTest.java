package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.HttpTestUtil.DummyItem;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PostRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldPerformPostRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyPostRequest().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformPostRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyItem result = new DummyPostRequestWithBody().execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformWrappedPostRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json", ImmutableMap.of("foo", "bar"));
        ApiResponse<DummyItem> result =
                new DummyPostRequest().withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldPerformWrappedPostRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json", ImmutableMap.of("foo", "bar"));
        ApiResponse<DummyItem> result = new DummyPostRequestWithBody()
                .withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    private class DummyPostRequest extends PostRequest<DummyItem> {
        public DummyPostRequest() {
            super(http.client());
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
        protected Class<DummyItem> getResponseClass() {
            return DummyItem.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    private class DummyPostRequestWithBody extends DummyPostRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
