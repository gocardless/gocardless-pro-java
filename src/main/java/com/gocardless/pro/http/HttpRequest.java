package com.gocardless.pro.http;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

public abstract class HttpRequest<T> {
    protected final HttpClient httpClient;
    protected final String pathTemplate;

    public HttpRequest(HttpClient httpClient, String pathTemplate) {
        this.httpClient = httpClient;
        this.pathTemplate = pathTemplate;
    }

    public T execute() throws IOException {
        return httpClient.get(this);
    }

    URL getUrl(UrlFormatter urlFormatter) {
        return urlFormatter.formatUrl(pathTemplate, getParams());
    }

    protected abstract Map<String, String> getParams();

    abstract T parseResponse(Reader stream, ResponseParser responseParser);
}
