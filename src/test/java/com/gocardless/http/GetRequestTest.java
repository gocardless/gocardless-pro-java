package com.gocardless.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.gocardless.errors.InvalidApiUsageException;
import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.withHeader;

import static org.assertj.core.api.Assertions.assertThat;

import static org.glassfish.grizzly.http.util.HttpStatus.BAD_REQUEST_400;
import static org.glassfish.grizzly.http.util.HttpStatus.OK_200;

public class GetRequestTest {
    @Rule
    public MockHttp http = new MockHttp();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldPerformGetRequest() {
        whenHttp(http.server()).match(get("/dummy/123")).then(status(OK_200),
                resourceContent("fixtures/single.json"));
        DummyGetRequest<DummyItem> request = DummyGetRequest.jsonRequest(http.client());
        DummyItem result = request.execute();
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldPerformDownloadRequest() throws IOException {
        whenHttp(http.server()).match(get("/dummy/123"), withHeader("Accept", "text/plain")).then(
                status(OK_200), resourceContent("fixtures/hello.txt"));
        DummyGetRequest<InputStream> request = DummyGetRequest.downloadRequest(http.client());
        String result = CharStreams.toString(new InputStreamReader(request.execute()));
        assertThat(result.trim()).isEqualTo("hello");
    }

    @Test
    public void shouldThrowOnApiError() {
        whenHttp(http.server()).match(get("/dummy/123")).then(status(BAD_REQUEST_400),
                resourceContent("fixtures/invalid_api_usage.json"));
        exception.expect(InvalidApiUsageException.class);
        exception.expectMessage("Invalid document structure");
        DummyGetRequest<DummyItem> request = DummyGetRequest.jsonRequest(http.client());
        request.execute();
    }

    private static class DummyGetRequest<S> extends GetRequest<S, DummyItem> {
        private DummyGetRequest(HttpClient httpClient, GetRequestExecutor<S, DummyItem> executor) {
            super(httpClient, executor);
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

        static DummyGetRequest<DummyItem> jsonRequest(HttpClient httpClient) {
            return new DummyGetRequest<>(httpClient, GetRequest.<DummyItem>jsonExecutor());
        }

        static DummyGetRequest<InputStream> downloadRequest(HttpClient httpClient) {
            return new DummyGetRequest<>(httpClient,
                    GetRequest.<DummyItem>downloadExecutor("text/plain"));
        }
    }
}
