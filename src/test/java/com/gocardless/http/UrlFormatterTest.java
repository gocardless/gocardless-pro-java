package com.gocardless.http;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.squareup.okhttp.HttpUrl;

import org.junit.Before;
import org.junit.Test;

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
        Map<String, String> pathParams = ImmutableMap.of();
        Map<String, Object> queryParams = ImmutableMap.of();
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com" + template);
    }

    @Test
    public void shouldSubstituteOnePart() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> pathParams = ImmutableMap.of("bar", "123");
        Map<String, Object> queryParams = ImmutableMap.of();
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/:foo/123/:foo/other");
    }

    @Test
    public void shouldSubstituteMultiple() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> pathParams = ImmutableMap.of("bar", "123", "foo", "456");
        Map<String, Object> queryParams = ImmutableMap.of();
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/456/123/456/other");
    }

    @Test
    public void shouldNotSubstituteWithoutColon() {
        String template = "/:foo/:bar/:foo/other";
        Map<String, String> pathParams = ImmutableMap.of("other", "123");
        Map<String, Object> queryParams = ImmutableMap.of();
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com" + template);
    }

    @Test
    public void shouldAddQueryParam() {
        String template = "/foo";
        Map<String, String> pathParams = ImmutableMap.of();
        Map<String, Object> queryParams = ImmutableMap.<String, Object>of("bar", 123);
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/foo?bar=123");
    }

    @Test
    public void shouldAddQueryParams() {
        String template = "/foo";
        Map<String, String> pathParams = ImmutableMap.of();
        Map<String, Object> queryParams =
                ImmutableMap.<String, Object>of("bar", 123, "baz", "quux");
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/foo?bar=123&baz=quux");
    }

    @Test
    public void shouldEncodeQueryParam() {
        String template = "/foo";
        Map<String, String> pathParams = ImmutableMap.of();
        Map<String, Object> queryParams = ImmutableMap.<String, Object>of("bar", "1 2 3");
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/foo?bar=1%202%203");
    }

    @Test
    public void shouldSupportBaseUrlWithPath() {
        urlFormatter = new UrlFormatter("http://example.com/direct/");
        String template = "debit/:id";
        Map<String, String> pathParams = ImmutableMap.of("id", "ID123");
        Map<String, Object> queryParams = ImmutableMap.<String, Object>of();
        HttpUrl result = urlFormatter.formatUrl(template, pathParams, queryParams);
        assertThat(result.toString()).isEqualTo("http://example.com/direct/debit/ID123");
    }
}
