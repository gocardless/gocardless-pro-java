package com.example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

final class UrlFormatter {
    private final URI baseUri;

    UrlFormatter(String baseUri) {
        this.baseUri = URI.create(baseUri);
    }

    URL formatUrl(String template, Map<String, String> params) {
        String path = template;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            path = path.replace(":" + entry.getKey(), entry.getValue());
        }

        try {
            return baseUri.resolve(path).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
