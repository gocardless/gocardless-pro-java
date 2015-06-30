package com.gocardless.http;

import java.io.IOException;
import java.util.Map;

import com.gocardless.GoCardlessClient;
import com.gocardless.TestUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.rules.ExternalResource;

import static com.gocardless.http.HttpTestUtil.jsonMatchesFixture;

import static com.google.common.base.Charsets.UTF_8;

import static org.assertj.core.api.Assertions.assertThat;

public class MockHttp extends ExternalResource {
    private MockWebServer server;

    @Override
    protected void before() throws Throwable {
        server = new MockWebServer();
        server.start();
    }

    @Override
    protected void after() {
        try {
            server.shutdown();
        } catch (IOException e) {
            // do nothing
        }
    }

    public void enqueueResponse(int statusCode, String fixturePath) throws Exception {
        enqueueResponse(statusCode, fixturePath, ImmutableMap.<String, String>of());
    }

    public void enqueueResponse(int statusCode, String fixturePath, Map<String, String> headers)
            throws Exception {
        String body = Resources.toString(Resources.getResource(fixturePath), UTF_8);
        MockResponse response = new MockResponse().setBody(body);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.setHeader(header.getKey(), header.getValue());
        }
        server.enqueue(response);
    }

    public void assertRequestMade(String method, String path) throws Exception {
        assertRequestMade(method, path, null);
    }

    public void assertRequestMade(String method, String path, String fixturePath) throws Exception {
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo(method);
        assertThat(recordedRequest.getPath()).isEqualTo(path);
        if (fixturePath == null) {
            assertThat(recordedRequest.getBodySize()).isEqualTo(0);
        } else {
            assertThat(jsonMatchesFixture(recordedRequest.getBody().readUtf8(), fixturePath))
                    .isTrue();
        }
    }

    public HttpClient client() {
        String baseUrl = String.format("http://localhost:%d", server.getPort());
        GoCardlessClient client = GoCardlessClient.create("token", baseUrl);
        return TestUtil.getHttpClient(client);
    }
}
