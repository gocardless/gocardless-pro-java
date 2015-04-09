package com.gocardless.pro;

import co.freeside.betamax.ssl.DummyX509TrustManager;
import com.gocardless.pro.http.HttpClient;
import com.squareup.okhttp.OkHttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class TestUtil {
    public static void disableSslCertificateChecking(GoCardlessClient client) throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {new DummyX509TrustManager()};
        SSLContext sc = SSLContext.getInstance("TLS");

        sc.init(null, trustAllCerts, new java.security.SecureRandom());

        OkHttpClient rawClient = client.getHttpClient().getRawClient();
        rawClient.setSslSocketFactory(sc.getSocketFactory());
        rawClient.setHostnameVerifier(new AllowAllHostnameVerifier());
    }

    public static HttpClient createHttpClient(String apiKey, String apiSecret, String baseUrl) {
        return new HttpClient(apiKey, apiSecret, baseUrl);
    }
}
