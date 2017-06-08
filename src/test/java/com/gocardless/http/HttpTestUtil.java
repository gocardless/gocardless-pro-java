package com.gocardless.http;

import java.io.IOException;

import com.google.common.io.Resources;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;

public class HttpTestUtil {
    public static boolean jsonMatchesFixture(String actual, String fixturePath) throws IOException {
        JsonParser parser = new JsonParser();
        String expectedJson = Resources.toString(getResource(fixturePath), UTF_8);
        JsonElement result = parser.parse(actual);
        JsonElement expected = parser.parse(expectedJson);
        return result.equals(expected);
    }

    public static class DummyItem {
        public String stringField;
        public int intField;
    }
}
