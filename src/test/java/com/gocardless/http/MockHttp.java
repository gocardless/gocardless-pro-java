package com.gocardless.http;

import com.gocardless.GoCardlessClient;
import com.gocardless.TestUtil;

import com.xebialabs.restito.server.StubServer;

import org.junit.rules.ExternalResource;

public class MockHttp extends ExternalResource {
    private StubServer server;

    @Override
    protected void before() throws Throwable {
        server = new StubServer().run();
    }

    @Override
    protected void after() {
        server.stop();
    }

    public StubServer server() {
        return server;
    }

    public HttpClient client() {
        String baseUrl = String.format("http://localhost:%d", server.getPort());
        GoCardlessClient client = GoCardlessClient.create("key", "secret", baseUrl);;
        return TestUtil.getHttpClient(client);
    }
}
