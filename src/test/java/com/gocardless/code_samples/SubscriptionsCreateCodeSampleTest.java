package com.gocardless.code_samples;

// Code Sample Test
// This test verifies that a documentation code sample is syntactically valid
// and can execute against a mocked API without errors.
//
// IMPORTANT: This test does NOT verify business logic - it only verifies that
// the code sample compiles and executes without syntax errors.
import com.gocardless.GoCardlessClient;
import com.gocardless.resources.*;
import com.gocardless.services.SubscriptionService.SubscriptionCreateRequest.IntervalUnit;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubscriptionsCreateCodeSampleTest {
    private static final String ACCESS_TOKEN = "SECRET_TOKEN";
    private GoCardlessClient client;
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        client = GoCardlessClient.newBuilder(ACCESS_TOKEN)
                .withBaseUrl(String.format("http://localhost:%d", server.getPort())).build();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testCreateCodeSample() throws Exception {
        // Mock response - enqueue multiple times to handle code samples with multiple API calls
        String responseBody = "{ \"subscriptions\": {} }";
        for (int i = 0; i < 5; i++) {
            server.enqueue(new MockResponse().setBody(responseBody).setResponseCode(200));
        }
        // Suppress stdout from code samples
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            Subscription subscription = client.subscriptions().create().withAmount(2500)
                    .withCurrency("GBP").withName("Monthly Magazine")
                    .withIntervalUnit(IntervalUnit.MONTHLY).withDayOfMonth(1)
                    .withMetadata("order_no", "ABCD1234").withLinksMandate("MD123").execute();
        } finally {
            System.setOut(originalOut);
        }
    }
}
