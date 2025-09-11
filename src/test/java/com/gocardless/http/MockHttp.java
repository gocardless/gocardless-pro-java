package com.gocardless.http;

import java.io.IOException;
import java.util.Map;

import com.gocardless.GoCardlessClient;
import com.gocardless.TestUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
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

    public void enqueueResponse(int statusCode, String fixturePath, Map<String, String> headers) throws Exception {
        String body = Resources.toString(Resources.getResource(fixturePath), UTF_8);
        MockResponse response = new MockResponse().setBody(body).setResponseCode(statusCode);

        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.setHeader(header.getKey(), header.getValue());
        }

        server.enqueue(response);
    }

    public void enqueueNetworkFailure() {
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START));
    }

    public void assertRequestMade(String method, String path) throws Exception {
        assertRequestMade(method, path, null, ImmutableMap.<String, String>of());
    }

    public void assertRequestMade(String method, String path, String fixturePath) throws Exception {
        assertRequestMade(method, path, fixturePath, ImmutableMap.<String, String>of());
    }

    public void assertRequestMade(String method, String path, Map<String, String> headers) throws Exception {
        assertRequestMade(method, path, null, headers);
    }

    public void assertRequestMade(String method, String path, String fixturePath, Map<String, String> headers) throws Exception {
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo(method);
        assertThat(recordedRequest.getPath()).isEqualTo(path);

        for (Map.Entry<String, String> header : headers.entrySet()) {
            assertThat(recordedRequest.getHeader(header.getKey())).isEqualTo(header.getValue());
        }

        if (fixturePath == null) {
            assertThat(recordedRequest.getBodySize()).isEqualTo(0);
        } else {
            assertThat(jsonMatchesFixture(recordedRequest.getBody().readUtf8(), fixturePath)).isTrue();
        }
    }

    public void assertRequestIncludedHeader(String headerName) throws Exception {
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getHeader(headerName)).isNotNull();
    }

    public void takeRequest() throws Exception {
        server.takeRequest();
    }

    public String getBaseUrl() {
        return String.format("http://localhost:%d", server.getPort());
    }

    public HttpClient client() {
        GoCardlessClient client = GoCardlessClient.newBuilder("token").withBaseUrl(getBaseUrl()).build();

        return TestUtil.getHttpClient(client);
    }
}
