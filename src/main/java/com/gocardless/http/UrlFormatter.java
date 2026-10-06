package com.gocardless.http;

import static com.google.common.net.UrlEscapers.urlPathSegmentEscaper;

import java.util.Map;
import okhttp3.HttpUrl;

final class UrlFormatter {
    private final HttpUrl baseUrl;

    UrlFormatter(String baseUrl) {
        this.baseUrl = HttpUrl.parse(baseUrl);
    }

    HttpUrl formatUrl(String template, Map<String, String> pathParams,
            Map<String, Object> queryParams) {
        String path = template;
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            path = path.replace(":" + entry.getKey(),
                    escapePathParam(entry.getKey(), entry.getValue()));
        }
        HttpUrl.Builder builder = resolveAgainstBaseUrl(path).newBuilder();
        for (Map.Entry<String, Object> param : queryParams.entrySet()) {
            builder.addQueryParameter(param.getKey(), param.getValue().toString());
        }
        return builder.build();
    }

    /**
     * Resolves a path against the base URL, rejecting one that would leave its origin - an absolute
     * or scheme-relative path, which {@link okhttp3.HttpUrl#resolve} otherwise follows like a
     * browser follows a link, replacing the origin while the auth header stays attached.
     *
     * <p>
     * Checked on the resolved URL rather than the raw string, since {@code resolve} accepts
     * authority syntax (e.g. backslashes) that {@link okhttp3.HttpUrl#parse} would reject. Dot
     * segments are left alone: they resolve against the base URL and can't leave its origin.
     */
    private HttpUrl resolveAgainstBaseUrl(String path) {
        HttpUrl resolved = baseUrl.resolve(path);
        if (resolved == null) {
            throw new IllegalArgumentException(
                    "Invalid request path '" + path + "': not a valid URL path");
        }
        if (!resolved.scheme().equals(baseUrl.scheme()) || !resolved.host().equals(baseUrl.host())
                || resolved.port() != baseUrl.port()) {
            throw new IllegalArgumentException("Invalid request path '" + path
                    + "': a path may not specify a scheme or a host, only a location relative "
                    + "to the configured base URL");
        }
        return resolved;
    }

    /**
     * Escapes a value before it is interpolated into a request path. A path parameter is a single
     * path segment, so values that could move the request to a different endpoint - path
     * separators, control characters, '.', '..' (escaping can't make these safe - resolve() strips
     * them regardless), and empty values - are rejected instead.
     */
    private static String escapePathParam(String key, String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("No value provided for URL parameter '" + key + "'");
        }
        if (value.equals(".") || value.equals("..")) {
            throw new IllegalArgumentException("Invalid value for URL parameter '" + key + "': '"
                    + value + "' would change which endpoint the request is sent to");
        }
        for (int i = 0; i < value.length(); i++) {
            if (isForbiddenPathParamChar(value.charAt(i))) {
                throw new IllegalArgumentException("Invalid value for URL parameter '" + key
                        + "': '" + value + "' contains a character that is not allowed in a path "
                        + "segment");
            }
        }
        return urlPathSegmentEscaper().escape(value);
    }

    private static boolean isForbiddenPathParamChar(char c) {
        return c == '/' || c == '?' || c == '#' || c < 0x20 || c == 0x7f;
    }
}
