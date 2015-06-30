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
    private static final String USER_AGENT = String.format("gocardless-pro/0.2.4 %s/%s %s/%s",
            replaceSpaces(System.getProperty("os.name")),
            replaceSpaces(System.getProperty("os.version")),
            replaceSpaces(System.getProperty("java.vm.name")),
            replaceSpaces(System.getProperty("java.version")));
    private static final RequestBody EMPTY_BODY = RequestBody.create(null, new byte[0]);
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private static final Map<String, String> HEADERS;
    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("GoCardless-Version", "2015-04-29");
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
     * @param accessToken the access token.
     * @param baseUrl base URI to make requests against.
     */
    public HttpClient(String accessToken, String baseUrl) {
        this.rawClient = new OkHttpClient();
        rawClient.interceptors().add(new LoggingInterceptor());
        this.urlFormatter = new UrlFormatter(baseUrl);
        Gson gson = GsonFactory.build();
        this.responseParser = new ResponseParser(gson);
        this.requestWriter = new RequestWriter(gson);
        this.credentials = String.format("Bearer %s", accessToken);
    }

    <T> T execute(HttpRequest<T> httpRequest) {
        Request request = buildRequest(httpRequest);
        Response response = execute(request);
        return parseResponseBody(httpRequest, response);
    }

    <T> HttpResponse<T> executeWrapped(HttpRequest<T> httpRequest) {
        Request request = buildRequest(httpRequest);
        Response response = execute(request);
        T resource = parseResponseBody(httpRequest, response);
        return new HttpResponse<T>(resource, response.code(), response.headers().toMultimap());
    }

    <T> Request buildRequest(HttpRequest<T> httpRequest) {
        URL url = httpRequest.getUrl(urlFormatter);
        Request.Builder request =
                new Request.Builder().url(url).header("Authorization", credentials)
                        .header("User-Agent", USER_AGENT)
                        .method(httpRequest.getMethod(), getBody(httpRequest));
        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            request = request.header(entry.getKey(), entry.getValue());
        }
        return request.build();
    }

    private <T> RequestBody getBody(HttpRequest<T> request) {
        if (!request.hasBody()) {
            if (request.getMethod().equals("GET")) {
                return null;
            } else {
                return EMPTY_BODY;
            }
        }
        String json = requestWriter.write(request, request.getRequestEnvelope());
        return RequestBody.create(MEDIA_TYPE, json);
    }

    private Response execute(Request request) {
        Response response;
        try {
            response = rawClient.newCall(request).execute();
        } catch (IOException e) {
            throw new GoCardlessNetworkException("Failed to execute request", e);
        }
        if (!response.isSuccessful()) {
            throw handleErrorResponse(response);
        }
        return response;
    }

    private <T> T parseResponseBody(HttpRequest<T> request, Response response) {
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
