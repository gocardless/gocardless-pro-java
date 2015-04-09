package com.gocardless.pro;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.Recorder;
import com.gocardless.pro.resources.Customer;
import com.gocardless.pro.resources.Mandate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoCardlessClientTest {
    private GoCardlessClient client;

    @Rule
    public Recorder recorder = new Recorder();

    @Before
    public void setUp() throws Exception {
        recorder.setSslSupport(true);

        String apiKey = System.getenv("GC_API_KEY");
        String apiSecret = System.getenv("GC_API_SECRET");

        client = GoCardlessClient.create(apiKey, apiSecret, "https://api-sandbox.gocardless.com");
        TestUtil.disableSslCertificateChecking(client);
    }

    @Test
    @Betamax(tape = "get a customer")
    public void shouldGetACustomer() throws IOException {
        Customer customer = client.customers().get("CU00003068FG73").execute();

        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
    }

    @Test
    @Betamax(tape = "list customers")
    public void shouldListCustomers() throws IOException {
        List<Customer> customers = client.customers().list().execute();

        assertThat(customers).hasSize(2);

        assertThat(customers.get(0).getId()).isEqualTo("CU00003068FG73");
        assertThat(customers.get(0).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(0).getGivenName()).isEqualTo("Frank");

        assertThat(customers.get(1).getId()).isEqualTo("CU0000302M1J1J");
        assertThat(customers.get(1).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(1).getGivenName()).isEqualTo("Sarah");
    }

    @Test
    @Betamax(tape = "list mandates for a customer")
    public void shouldListMandatesForACustomer() throws IOException {
        List<Mandate> mandates = client.mandates().list().withCustomer("CU00003068FG73").execute();

        assertThat(mandates).hasSize(2);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
    }
}
