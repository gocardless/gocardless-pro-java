package com.gocardless.pro.http;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

final class UrlFormatter {
    private static final Joiner JOINER = Joiner.on('&');
    private static final Function<Map.Entry<String, Object>, String> FORMAT_QUERY_PART = new Function<Map.Entry<String, Object>, String>() {
        @Override
        public String apply(Map.Entry<String, Object> input) {
            try {
                return String.format("%s=%s", input.getKey(), URLEncoder.encode(input.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException(e);
            }
        }
    };

    private final URI baseUri;

    UrlFormatter(String baseUri) {
        this.baseUri = URI.create(baseUri);
    }

    URL formatUrl(String template, Map<String, String> pathParams, Map<String, Object> queryParams) {
        String path = template;

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            path = path.replace(":" + entry.getKey(), entry.getValue());
        }

        if (!queryParams.isEmpty()) {
            Iterable<String> queryParts = Iterables.transform(queryParams.entrySet(), FORMAT_QUERY_PART);
            String queryString = JOINER.join(queryParts);
            path = String.format("%s?%s", path, queryString);
        }

        try {
            return baseUri.resolve(path).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
