package com.gocardless.http;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import com.gocardless.GoCardlessException;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import com.squareup.okhttp.*;

/**
 * An HTTP client that can execute {@link HttpRequest}s.
 *
 * Users of this library should not need to access this class directly.
 */
public class HttpClient {
    private static final String USER_AGENT = String.format("gocardless-pro/0.0.4 %s/%s %s/%s",
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

    /**
     * Constructor.  Users of this library should not need to access this class directly.
     *
     * @param apiKey the API key.
     * @param apiSecret the API secret.
     * @param baseUrl base URI to make requests against.
     */
    public HttpClient(String apiKey, String apiSecret, String baseUrl) {
        this.rawClient = new OkHttpClient();
        this.urlFormatter = new UrlFormatter(baseUrl);
        Gson gson = GsonFactory.build();
        this.responseParser = new ResponseParser(gson);
        this.requestWriter = new RequestWriter(gson);
        this.credentials = Credentials.basic(apiKey, apiSecret);
    }

    <T> T execute(HttpRequest<T> request) {
        URL url = request.getUrl(urlFormatter);
        Request.Builder httpRequest =
                new Request.Builder().url(url).header("Authorization", credentials)
                        .header("User-Agent", USER_AGENT)
                        .method(request.getMethod(), getBody(request));
        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            httpRequest = httpRequest.header(entry.getKey(), entry.getValue());
        }
        Response response = execute(httpRequest.build());
        if (response.isSuccessful()) {
            return handleSuccessfulResponse(request, response);
        } else {
            throw handleErrorResponse(response);
        }
    }

    private <T> RequestBody getBody(HttpRequest<T> request) {
        if (!request.hasBody()) {
            return null;
        }
        String json = requestWriter.write(request, request.getRequestEnvelope());
        return RequestBody.create(MEDIA_TYPE, json);
    }

    private Response execute(Request request) {
        try {
            return rawClient.newCall(request).execute();
        } catch (IOException e) {
            throw new GoCardlessNetworkException("Failed to execute request", e);
        }
    }

    private <T> T handleSuccessfulResponse(HttpRequest<T> request, Response response) {
        try (Reader stream = response.body().charStream()) {
            return request.parseResponse(stream, responseParser);
        } catch (IOException e) {
            throw new GoCardlessNetworkException("Failed to read response body", e);
        }
    }

    private GoCardlessException handleErrorResponse(Response response) {
        try (Reader stream = response.body().charStream()) {
            return responseParser.parseError(stream);
        } catch (IOException e) {
            throw new GoCardlessNetworkException("Failed to read response body", e);
        }
    }

    private static String replaceSpaces(String s) {
        return s.replaceAll(" ", "_");
    }

    @VisibleForTesting
    OkHttpClient getRawClient() {
        return rawClient;
    }
}
