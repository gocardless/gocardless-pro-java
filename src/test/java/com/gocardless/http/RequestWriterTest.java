package com.gocardless.http;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.gocardless.http.HttpTestUtil.jsonMatchesFixture;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestWriterTest {
    private RequestWriter writer;

    @Before
    public void setUp() {
        writer = new RequestWriter(GsonFactory.build());
    }

    @Test
    public void shouldWriteRequestAsJson() throws IOException {
        String result = writer.write(new DummyRequest(), "items");
        assertThat(jsonMatchesFixture(result, "fixtures/single.json")).isTrue();
    }

    @Test
    public void shouldIgnorePathParams() throws IOException {
        String result = writer.write(new DummyRequestWithPathParam(), "items");
        assertThat(jsonMatchesFixture(result, "fixtures/single.json")).isTrue();
    }

    private class DummyRequest {
        private int intField = 123;
        private String stringField = "foo";
    }

    private class DummyRequestWithPathParam extends DummyRequest {
        @PathParam
        private int id = 456;
    }
}
