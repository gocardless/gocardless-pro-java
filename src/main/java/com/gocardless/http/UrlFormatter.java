package com.gocardless.http;

import java.util.Map;

import com.squareup.okhttp.HttpUrl;

final class UrlFormatter {
    private final HttpUrl baseUrl;

    UrlFormatter(String baseUrl) {
        this.baseUrl = HttpUrl.parse(baseUrl);
    }

    HttpUrl formatUrl(String template, Map<String, String> pathParams,
            Map<String, Object> queryParams) {
        String path = template;
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            path = path.replace(":" + entry.getKey(), entry.getValue());
        }
        HttpUrl.Builder builder = baseUrl.resolve(path).newBuilder();
        for (Map.Entry<String, Object> param : queryParams.entrySet()) {
            builder.addQueryParameter(param.getKey(), param.getValue().toString());
        }
        return builder.build();
    }
}
