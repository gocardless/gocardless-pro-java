package com.gocardless.pro.http;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    private static final String USER_AGENT = String.format(
            "gocardless-pro-client/0.0.1-SNAPSHOT %s/%s %s/%s",
            replaceSpaces(System.getProperty("os.name")),
            replaceSpaces(System.getProperty("os.version")),
            replaceSpaces(System.getProperty("java.vm.name")),
            replaceSpaces(System.getProperty("java.version")));
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private static final Map<String, String> HEADERS;
    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("GoCardless-Version", "2014-11-03");
        HEADERS = builder.build();
    }
    private final OkHttpClient rawClient;
    private final UrlFormatter urlFormatter;
    private final ResponseParser responseParser;
    private final RequestWriter requestWriter;
    private final String credentials;

    public HttpClient(String apiKeyId, String apiKey, String baseUri) {
        this.rawClient = new OkHttpClient();
        this.urlFormatter = new UrlFormatter(baseUri);
        Gson gson = GsonFactory.build();
        this.responseParser = new ResponseParser(gson);
        this.requestWriter = new RequestWriter(gson);
        this.credentials = Credentials.basic(apiKeyId, apiKey);
    }

    <T> T execute(HttpRequest<T> request) throws IOException {
        URL url = request.getUrl(urlFormatter);
        Request.Builder httpRequest =
                new Request.Builder().url(url).header("Authorization", credentials)
                        .header("User-Agent", USER_AGENT)
                        .method(request.getMethod(), getBody(request));
        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            httpRequest = httpRequest.header(entry.getKey(), entry.getValue());
        }
        Response response = execute(httpRequest.build());
        return request.parseResponse(response.body().charStream(), responseParser);
    }

    private <T> RequestBody getBody(HttpRequest<T> request) {
        if (!request.hasBody()) {
            return null;
        }
        String json = requestWriter.write(request, request.getEnvelope());
        return RequestBody.create(MEDIA_TYPE, json);
    }

    private Response execute(Request request) throws IOException {
        return rawClient.newCall(request).execute();
    }

    private static String replaceSpaces(String s) {
        return s.replaceAll(" ", "_");
    }

    @VisibleForTesting
    public OkHttpClient getRawClient() {
        return rawClient;
    }
}
