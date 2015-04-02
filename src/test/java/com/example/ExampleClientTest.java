

package com.example;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.Recorder;
import com.example.resources.Customer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleClientTest {
    private ExampleClient client;

    @Rule
    public Recorder recorder = new Recorder();

    @Before
    public void setUp() throws Exception {
        recorder.setSslSupport(true);

        String apiKey = System.getenv("GC_API_KEY");
        String apiSecret = System.getenv("GC_API_SECRET");

        client = ExampleClient.create(
                apiKey, apiSecret, "https://api-sandbox.gocardless.com");
        TestUtil.disableSslCertificateChecking(client);
    }

    @Test
    @Betamax(tape = "get a customer")
    public void shouldGetACustomer() throws IOException {
        Customer customer = client.customers().get("CU00003068FG73");

        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
    }
}
