package com.gocardless.http;

import com.gocardless.errors.InvalidApiUsageException;
import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;

import static org.assertj.core.api.Assertions.assertThat;

import static org.glassfish.grizzly.http.util.HttpStatus.BAD_REQUEST_400;
import static org.glassfish.grizzly.http.util.HttpStatus.OK_200;

public class GetRequestTest {
    @Rule
    public MockHttp http = new MockHttp();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private GetRequest<DummyItem> request;

    @Before
    public void setUp() throws Exception {
        request = new DummyGetRequest();
    }

    @Test
    public void shouldPerformGetRequest() {
        whenHttp(http.server()).match(get("/dummy/123")).then(status(OK_200),
                resourceContent("fixtures/single.json"));
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldThrowOnApiError() {
        whenHttp(http.server()).match(get("/dummy/123")).then(status(BAD_REQUEST_400),
                resourceContent("fixtures/invalid_api_usage.json"));
        exception.expect(InvalidApiUsageException.class);
        exception.expectMessage("Invalid document structure");
        request.execute();
    }

    private class DummyGetRequest extends GetRequest<DummyItem> {
        public DummyGetRequest() {
            super(http.client());
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
