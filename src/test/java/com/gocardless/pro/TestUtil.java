package com.gocardless.pro;

import com.gocardless.pro.http.HttpClient;

public class TestUtil {
    public static HttpClient getHttpClient(GoCardlessClient client) {
        return client.getHttpClient();
    }
}
