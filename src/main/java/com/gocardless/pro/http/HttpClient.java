package com.gocardless.pro.http;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class HttpClient {
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

    public HttpClient(String apiKeyId, String apiKey, String baseUri) {
        this.rawClient = new OkHttpClient();
        this.urlFormatter = new UrlFormatter(baseUri);
        this.responseParser = new ResponseParser();

        this.credentials = Credentials.basic(apiKeyId, apiKey);
    }

    <T> T get(HttpRequest<T> request) throws IOException {
        URL url = request.getUrl(urlFormatter);
        Request.Builder httpRequest = new Request.Builder()
                .url(url)
                .header("Authorization", credentials);

        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            httpRequest = httpRequest.header(entry.getKey(), entry.getValue());
        }

        Response response = execute(httpRequest.build());

        return request.parseResponse(response.body().charStream(), responseParser);
    }

    private Response execute(Request request) throws IOException {
        return rawClient.newCall(request).execute();
    }

    @VisibleForTesting
    public OkHttpClient getRawClient() {
        return rawClient;
    }
}
