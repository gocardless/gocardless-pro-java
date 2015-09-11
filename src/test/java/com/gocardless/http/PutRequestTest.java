package com.gocardless.http;

import com.gocardless.http.HttpTestUtil.DummyItem;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PutRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldPerformPutRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPutRequest request = new DummyPutRequest();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("PUT", "/dummy");
    }

    @Test
    public void shouldPerformPutRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPutRequest request = new DummyPutRequestWithBody();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
        http.assertRequestMade("PUT", "/dummy", "fixtures/single.json");
    }

    public void shouldPerformWrappedPutRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/single.json");
        DummyPutRequest request = new DummyPutRequest();
        ApiResponse<DummyItem> result = request.executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
        http.assertRequestMade("PUT", "/dummy", "fixtures/single.json");
    }

    private class DummyPutRequest extends PutRequest<DummyItem> {
        public DummyPutRequest() {
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

    private class DummyPutRequestWithBody extends DummyPutRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
