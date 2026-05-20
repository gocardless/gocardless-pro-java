package com.gocardless.code_samples;

// Code Sample Test
// This test verifies that a documentation code sample is syntactically valid
// and can execute against a mocked API without errors.
//
// IMPORTANT: This test does NOT verify business logic - it only verifies that
// the code sample compiles and executes without syntax errors.
import com.gocardless.GoCardlessClient;
import com.gocardless.resources.*;
import com.gocardless.services.OutboundPaymentImportService.OutboundPaymentImportCreateRequest.EntryItems;
import com.gocardless.services.OutboundPaymentImportService.OutboundPaymentImportCreateRequest.EntryItems.Scheme;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OutboundPaymentImportsCreateCodeSampleTest {
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
        String responseBody = "{ \"outbound_payment_imports\": {} }";
        for (int i = 0; i < 5; i++) {
            server.enqueue(new MockResponse().setBody(responseBody).setResponseCode(200));
        }
        // Suppress stdout from code samples
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            EntryItems entry1 = new EntryItems().withAmount(1000).withScheme(Scheme.FASTER_PAYMENTS)
                    .withReference("Invoice 123").withRecipientBankAccountId("BA123");
            Map<String, String> metadata = new HashMap<>();
            metadata.put("order_id", "ORD-789");
            EntryItems entry2 = new EntryItems().withAmount(2000).withScheme(Scheme.FASTER_PAYMENTS)
                    .withReference("Invoice 124").withRecipientBankAccountId("BA456")
                    .withMetadata(metadata);
            OutboundPaymentImport outboundPaymentImport = client.outboundPaymentImports().create()
                    .withEntryItems(Arrays.asList(entry1, entry2)).withLinksCreditor("CR123")
                    .execute();
        } finally {
            System.setOut(originalOut);
        }
    }
}
