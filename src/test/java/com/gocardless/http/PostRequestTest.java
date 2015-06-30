package com.gocardless.http;

import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRequestTest {
    @Rule
    public MockHttp http = new MockHttp();

    @Test
    public void shouldPerformPostRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPostRequest request = new DummyPostRequest();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy");
    }

    @Test
    public void shouldPerformPostRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPostRequest request = new DummyPostRequestWithBody();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json");
    }

    @Test
    public void shouldPerformWrappedPostRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json", ImmutableMap.of("foo", "bar"));
        DummyPostRequest request = new DummyPostRequest();
        HttpResponse<DummyItem> result = request.executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("POST", "/dummy");
    }

    private class DummyPostRequest extends PostRequest<DummyItem> {
        public DummyPostRequest() {
            super(http.client());
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
