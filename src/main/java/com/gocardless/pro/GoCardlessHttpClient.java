

package com.gocardless.pro;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class GoCardlessHttpClient {
    private static final Map<String, String> HEADERS;

    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        
            builder.put("GoCardless-Version", "2014-11-03");
        

        HEADERS = builder.build();
    }

    private final OkHttpClient rawClient;
    private final UrlFormatter urlFormatter;
    private final ResponseParser responseParser;
    private final String credentials;

    GoCardlessHttpClient(String apiKeyId, String apiKey, String baseUri) {
        this.rawClient = new OkHttpClient();
        this.urlFormatter = new UrlFormatter(baseUri);
        this.responseParser = new ResponseParser();

        this.credentials = Credentials.basic(apiKeyId, apiKey);
    }

    public <T> T get(String path, Map<String, String> params, String envelope, Class<T> clazz) throws IOException {
        Request request = requestBuilder(path, params).get().build();
        Response response = execute(request);

        return responseParser.parseSingle(response.body().charStream(), envelope, clazz);
    }

    private Request.Builder requestBuilder(String path, Map<String, String> params) {
        URL url = urlFormatter.formatUrl(path, params);
        Request.Builder request = new Request.Builder()
                .url(url)
                .header("Authorization", credentials);

        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            request = request.header(entry.getKey(), entry.getValue());
        }

        return request;
    }

    private Response execute(Request request) throws IOException {
        return rawClient.newCall(request).execute();
    }

    @VisibleForTesting
    OkHttpClient getRawClient() {
        return rawClient;
    }
}
