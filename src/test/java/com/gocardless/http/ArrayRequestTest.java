package com.gocardless.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.HttpTestUtil.DummyItem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;

public class ArrayRequestTest {
    @Rule
    public final MockHttp http = new MockHttp();

    @Test
    public void shouldPerformArrayRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/array.json");
        ImmutableList<DummyItem> result = new DummyArrayRequest().execute();
        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(123);
        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(456);
        http.assertRequestMade("POST", "/dummy", ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformArrayRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/array.json");
        ImmutableList<DummyItem> result = new DummyArrayRequestWithBody().execute();
        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(123);
        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(456);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token"));
    }

    @Test
    public void shouldPerformWrappedArrayRequest() throws Exception {
        http.enqueueResponse(200, "fixtures/array.json", ImmutableMap.of("foo", "bar"));
        ApiResponse<ImmutableList<DummyItem>> result =
                new DummyArrayRequest().withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        ImmutableList<DummyItem> wrappedResult = result.getResource();
        assertThat(wrappedResult.get(0).stringField).isEqualTo("foo");
        assertThat(wrappedResult.get(0).intField).isEqualTo(123);
        assertThat(wrappedResult.get(1).stringField).isEqualTo("bar");
        assertThat(wrappedResult.get(1).intField).isEqualTo(456);
        http.assertRequestMade("POST", "/dummy",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldPerformWrappedArrayRequestWithBody() throws Exception {
        http.enqueueResponse(200, "fixtures/array.json", ImmutableMap.of("foo", "bar"));
        ApiResponse<ImmutableList<DummyItem>> result = new DummyArrayRequestWithBody()
                .withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        ImmutableList<DummyItem> wrappedResult = result.getResource();
        assertThat(wrappedResult.get(0).stringField).isEqualTo("foo");
        assertThat(wrappedResult.get(0).intField).isEqualTo(123);
        assertThat(wrappedResult.get(1).stringField).isEqualTo("bar");
        assertThat(wrappedResult.get(1).intField).isEqualTo(456);
        http.assertRequestMade("POST", "/dummy", "fixtures/single.json",
                ImmutableMap.of("Authorization", "Bearer token", "Accept-Language", "fr-FR"));
    }

    private class DummyArrayRequest extends ArrayRequest<DummyItem> {
        public DummyArrayRequest() {
            super(http.client(), "POST");
        }

        public DummyArrayRequest withHeader(String headerName, String headerValue) {
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
        protected TypeToken<List<DummyItem>> getTypeToken() {
            return new TypeToken<List<DummyItem>>() {};
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    private class DummyArrayRequestWithBody extends DummyArrayRequest {
        private int intField = 123;
        private String stringField = "foo";

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
