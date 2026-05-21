package com.gocardless.code_samples;

// Code Sample Test
// This test verifies that a documentation code sample is syntactically valid
// and can execute against a mocked API without errors.
//
// IMPORTANT: This test does NOT verify business logic - it only verifies that
// the code sample compiles and executes without syntax errors.
import com.gocardless.GoCardlessClient;
import com.gocardless.resources.*;
import com.gocardless.services.InstalmentScheduleService.InstalmentScheduleCreateWithScheduleRequest.Instalments.IntervalUnit;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstalmentSchedulesCreateWithScheduleCodeSampleTest {
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
    public void testCreateWithScheduleCodeSample() throws Exception {
        // Mock response - enqueue multiple times to handle code samples with multiple API calls
        String responseBody = "{ \"instalment_schedules\": {} }";
        for (int i = 0; i < 5; i++) {
            server.enqueue(new MockResponse().setBody(responseBody).setResponseCode(200));
        }
        // Suppress stdout from code samples
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            InstalmentSchedule instalmentSchedule = client.instalmentSchedules()
                    .createWithSchedule().withTotalAmount(10000) // 100 GBP in Pence, collected from
                                                                 // the customer.
                    .withAppFee(10) // Your 10 pence fee, applied to each instalment, to be
                                    // paid out to you
                    .withCurrency(
                            com.gocardless.services.InstalmentScheduleService.InstalmentScheduleCreateWithScheduleRequest.Currency.GBP)
                    .withInstalmentsIntervalUnit(IntervalUnit.WEEKLY).withInstalmentsInterval(2)
                    .withInstalmentsAmounts(Arrays.asList(3400, 3400, 3200))
                    .withLinksMandate("MD0000YTKZKY4J").withMetadata("invoiceId", "001")
                    .withIdempotencyKey("random_instalment_schedule_specific_string").execute();
        } finally {
            System.setOut(originalOut);
        }
    }
}
