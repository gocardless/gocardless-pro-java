package com.gocardless.http;

import com.gocardless.http.HttpTestUtil.DummyItem;

import org.glassfish.grizzly.http.util.HttpStatus;

import org.junit.Rule;
import org.junit.Test;

import static com.gocardless.http.HttpTestUtil.withJsonBody;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.header;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRequestTest {
    @Rule
    public MockHttp http = new MockHttp();

    @Test
    public void shouldPerformPostRequest() {
        whenHttp(http.server()).match(post("/dummy"), not(withPostBody())).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/single.json"));
        DummyPostRequest request = new DummyPostRequest();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldPerformPostRequestWithBody() {
        whenHttp(http.server()).match(post("/dummy"), withJsonBody("fixtures/single.json")).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/single.json"));
        DummyPostRequest request = new DummyPostRequestWithBody();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldPerformWrappedPostRequest() {
        whenHttp(http.server()).match(post("/dummy"), not(withPostBody())).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/single.json"),
                header("foo", "bar"));
        DummyPostRequest request = new DummyPostRequest();
        HttpResponse<DummyItem> result = request.executeWrapped();
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getHeaders().get("foo")).containsExactly("bar");
        assertThat(result.getResource().stringField).isEqualTo("foo");
        assertThat(result.getResource().intField).isEqualTo(123);
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
