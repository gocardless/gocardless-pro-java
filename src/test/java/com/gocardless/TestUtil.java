package com.gocardless;

import com.gocardless.http.HttpClient;

public class TestUtil {
    public static HttpClient getHttpClient(GoCardlessClient client) {
        return client.getHttpClient();
    }
}
