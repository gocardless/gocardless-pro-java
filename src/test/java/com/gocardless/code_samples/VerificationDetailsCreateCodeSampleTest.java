package com.gocardless.code_samples;

// Code Sample Test
// This test verifies that a documentation code sample is syntactically valid
// and can execute against a mocked API without errors.
//
// IMPORTANT: This test does NOT verify business logic - it only verifies that
// the code sample compiles and executes without syntax errors.
import com.gocardless.GoCardlessClient;
import com.gocardless.resources.*;
import com.gocardless.services.VerificationDetailService.VerificationDetailCreateRequest.Directors;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VerificationDetailsCreateCodeSampleTest {
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
        String responseBody = "{ \"verification_details\": {} }";
        for (int i = 0; i < 5; i++) {
            server.enqueue(new MockResponse().setBody(responseBody).setResponseCode(200));
        }
        // Suppress stdout from code samples
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            Directors director = new Directors();
            director.withCity("London");
            director.withCountryCode("GB");
            director.withDateOfBirth("1986-02-19");
            director.withGivenName("Gandalf");
            director.withFamilyName("Grey");
            director.withPostalCode("B4 7NJ");
            director.withStreet("Drury Lane");
            VerificationDetail verificationDetail = client.verificationDetails().create()
                    .withName("Acme").withDescription("wine and cheese seller")
                    .withCompanyNumber("03768189").withAddressLine1("12 Drury Lane")
                    .withCity("London").withPostalCode("B4 7NJ").withDirectors(director)
                    .withLinksCreditor("CR123").execute();
        } finally {
            System.setOut(originalOut);
        }
    }
}
