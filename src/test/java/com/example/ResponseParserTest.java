package com.example;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.asCharSource;
import static com.google.common.io.Resources.getResource;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponseParserTest {
    private ResponseParser parser;

    public ResponseParserTest() {
        parser = new ResponseParser();
    }

    @Test
    public void shouldParseSingle() throws IOException {
        URL resource = getResource("fixtures/single.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();

        DummyItem result = parser.parseSingle(reader, "items", DummyItem.class);

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    private class DummyItem {
        String stringField;
        int intField;
    }
}
