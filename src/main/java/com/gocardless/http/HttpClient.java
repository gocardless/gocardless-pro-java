package com.gocardless.http;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import com.gocardless.GoCardlessException;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import com.squareup.okhttp.*;

/**
 * An HTTP client that can execute {@link ApiRequest}s.
 *
 * Users of this library should not need to access this class directly.
 */
public class HttpClient {
    /**
     * See http://tools.ietf.org/html/rfc7230#section-3.2.6.
     */
    private static final String DISALLOWED_USER_AGENT_CHARACTERS =
            "[^\\w!#$%&'\\*\\+\\-\\.\\^`\\|~]";
    private static final String USER_AGENT = String.format(
            "gocardless-pro/1.9.0 java/%s %s/%s %s/%s",
            cleanUserAgentToken(System.getProperty("java.vm.specification.version")),
            cleanUserAgentToken(System.getProperty("java.vm.name")),
            cleanUserAgentToken(System.getProperty("java.version")),
            cleanUserAgentToken(System.getProperty("os.name")),
            cleanUserAgentToken(System.getProperty("os.version")));
    private static final RequestBody EMPTY_BODY = RequestBody.create(null, new byte[0]);
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private static final Map<String, String> HEADERS;
    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("GoCardless-Version", "2015-07-06");
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

    <T> T execute(ApiRequest<T> apiRequest) {
        Request request = buildRequest(apiRequest);
        Response response = execute(request);
        return parseResponseBody(apiRequest, response);
    }

    <T> ApiResponse<T> executeWrapped(ApiRequest<T> apiRequest) {
        Request request = buildRequest(apiRequest);
        Response response = execute(request);
        T resource = parseResponseBody(apiRequest, response);
        return new ApiResponse<>(resource, response.code(), response.headers().toMultimap());
    }

    private <T> Request buildRequest(ApiRequest<T> apiRequest) {
        HttpUrl url = apiRequest.getUrl(urlFormatter);
        Request.Builder request =
                new Request.Builder().url(url).header("Authorization", credentials)
                        .header("User-Agent", USER_AGENT)
                        .method(apiRequest.getMethod(), getBody(apiRequest));
        for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
            request = request.header(entry.getKey(), entry.getValue());
        }
        return request.build();
    }

    private <T> RequestBody getBody(ApiRequest<T> request) {
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

    private <T> T parseResponseBody(ApiRequest<T> request, Response response) {
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

    private static String cleanUserAgentToken(String s) {
        return s.replaceAll(DISALLOWED_USER_AGENT_CHARACTERS, "_");
    }

    @VisibleForTesting
    OkHttpClient getRawClient() {
        return rawClient;
    }
}
