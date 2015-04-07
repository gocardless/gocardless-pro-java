package com.gocardless.pro.http;

import com.gocardless.pro.TestUtil;
import com.xebialabs.restito.server.StubServer;
import org.junit.After;
import org.junit.Before;

public abstract class HttpRequestTest {
    protected HttpClient client;
    protected StubServer server;

    @Before
    public void start() {
        server = new StubServer().run();

        String baseUrl = String.format("http://localhost:%d", server.getPort());
        client = TestUtil.createHttpClient("key", "secret", baseUrl);
    }

    @After
    public void stop() {
        server.stop();
    }

    protected class DummyItem {
        String stringField;
        int intField;
    }
}
