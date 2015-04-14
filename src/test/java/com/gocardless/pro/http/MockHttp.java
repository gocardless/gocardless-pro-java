package com.gocardless.pro.http;

import com.gocardless.pro.TestUtil;

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
        return TestUtil.createHttpClient("key", "secret", baseUrl);
    }
}
