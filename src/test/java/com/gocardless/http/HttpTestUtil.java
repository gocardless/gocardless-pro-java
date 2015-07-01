package com.gocardless.http;

import java.io.IOException;

import javax.net.ssl.SSLContext;

import co.freeside.betamax.ssl.DummyX509TrustManager;

import com.gocardless.GoCardlessClient;
import com.gocardless.TestUtil;

import com.google.common.io.Resources;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.squareup.okhttp.OkHttpClient;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;

public class HttpTestUtil {
    public static void disableSslCertificateChecking(GoCardlessClient client) throws Exception {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new DummyX509TrustManager[] {new DummyX509TrustManager()},
                new java.security.SecureRandom());
        OkHttpClient rawClient = TestUtil.getHttpClient(client).getRawClient();
        rawClient.setSslSocketFactory(sc.getSocketFactory());
        rawClient.setHostnameVerifier(new AllowAllHostnameVerifier());
    }

    public static boolean jsonMatchesFixture(String actual, String fixturePath) throws IOException {
        JsonParser parser = new JsonParser();
        String expectedJson = Resources.toString(getResource(fixturePath), UTF_8);
        JsonElement result = parser.parse(actual);
        JsonElement expected = parser.parse(expectedJson);
        return result.equals(expected);
    }

    public static class DummyItem {
        public String stringField;
        public int intField;
    }
}
