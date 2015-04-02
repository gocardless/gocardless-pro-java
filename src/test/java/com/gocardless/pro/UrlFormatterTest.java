package com.gocardless.pro;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlFormatterTest {
    private UrlFormatter urlFormatter;

    @Before
    public void setUp() {
        urlFormatter = new UrlFormatter("http://example.com");
    }

    @Test
    public void shouldDoNothingWhenNoParametersSupplied() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> params = ImmutableMap.of();

        URL result = urlFormatter.formatUrl(template, params);
        assertThat(result.toString()).isEqualTo("http://example.com" + template);
    }

    @Test
    public void shouldSubstituteOnePart() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> params = ImmutableMap.of(
                "bar", "123"
        );

        URL result = urlFormatter.formatUrl(template, params);
        assertThat(result.toString()).isEqualTo("http://example.com/:foo/123/:foo/other");
    }

    @Test
    public void shouldSubstituteMultiple() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> params = ImmutableMap.of(
                "bar", "123",
                "foo", "456"
        );

        URL result = urlFormatter.formatUrl(template, params);
        assertThat(result.toString()).isEqualTo("http://example.com/456/123/456/other");
    }

    @Test
    public void shouldNotSubstituteWithoutColon() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> params = ImmutableMap.of(
                "other", "123"
        );

        URL result = urlFormatter.formatUrl(template, params);
        assertThat(result.toString()).isEqualTo("http://example.com" + template);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnInvalidUrl() {
        String template = "/[";
        Map<String, String> params = ImmutableMap.of(
        );

        urlFormatter.formatUrl(template, params);
    }
}
