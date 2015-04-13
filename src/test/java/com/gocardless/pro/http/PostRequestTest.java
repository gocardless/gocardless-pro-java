package com.gocardless.pro.http;

import java.io.IOException;

import org.glassfish.grizzly.http.util.HttpStatus;

import org.junit.Test;

import static com.gocardless.pro.TestUtil.withJsonBody;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRequestTest extends HttpRequestTest {
    @Test
    public void shouldPerformPostRequest() throws IOException {
        whenHttp(server).match(post("/dummy"), not(withPostBody())).then(status(HttpStatus.OK_200),
                resourceContent("fixtures/single.json"));
        DummyPostRequest request = new DummyPostRequest();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldPerformPostRequestWithBody() throws IOException {
        whenHttp(server).match(post("/dummy"), withJsonBody("fixtures/single.json")).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/single.json"));
        DummyPostRequest request = new DummyPostRequestWithBody();
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    private class DummyPostRequest extends PostRequest<DummyItem> {
        public DummyPostRequest() {
            super(client);
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
