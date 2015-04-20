package com.gocardless;

import java.util.List;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.Recorder;

import com.gocardless.http.HttpTestUtil;
import com.gocardless.http.ListResponse;
import com.gocardless.resources.*;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.gocardless.GoCardlessClient.Environment.SANDBOX;
import static com.gocardless.services.MandateService.MandateListRequest.Status.ACTIVE;
import static com.gocardless.services.MandateService.MandateListRequest.Status.FAILED;
import static com.gocardless.services.SubscriptionService.SubscriptionCreateRequest.IntervalUnit.MONTHLY;

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
        client = GoCardlessClient.create(apiKey, apiSecret, SANDBOX);
        HttpTestUtil.disableSslCertificateChecking(client);
    }

    @Test
    @Betamax(tape = "get a customer")
    public void shouldGetACustomer() {
        Customer customer = client.customers().get("CU00003068FG73").execute();
        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
    }

    @Test
    @Betamax(tape = "list customers")
    public void shouldListCustomers() {
        List<Customer> customers = client.customers().list().execute().getItems();
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
    public void shouldListMandatesForACustomer() {
        List<Mandate> mandates =
                client.mandates().list().withCustomer("CU00003068FG73").execute().getItems();
        assertThat(mandates).hasSize(2);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(0).getLinks().getCreditor()).isEqualTo("CR000035EME9H5");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(mandates.get(1).getLinks().getCreditor()).isEqualTo("CR000035EME9H5");
    }

    @Test
    @Betamax(tape = "list mandates by customer and status")
    public void shouldListMandatesByCustomerAndStatus() {
        List<Mandate> mandates =
                client.mandates().list().withCustomer("CU00003068FG73").withStatus(ACTIVE)
                        .withStatus(FAILED).execute().getItems();
        assertThat(mandates).hasSize(1);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
    }

    @Test
    @Betamax(tape = "disable an api key")
    public void shouldDisableAnApiKey() {
        ApiKey key = client.apiKeys().disable("AK00001335JR69").execute();
        assertThat(key.getEnabled()).isFalse();
    }

    @Test
    @Betamax(tape = "create and update a customer")
    public void shouldCreateAndUpdateCustomer() {
        Customer customer =
                client.customers().create().withFamilyName("Osborne").withGivenName("Sharon")
                        .withAddressLine1("27 Acer Road").withAddressLine2("Apt 2")
                        .withCity("London").withPostalCode("E8 3GX").withCountryCode("GB")
                        .execute();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Sharon");
        Customer updatedCustomer =
                client.customers().update(customer.getId()).withGivenName("Ozzy").execute();
        assertThat(updatedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(updatedCustomer.getFamilyName()).isEqualTo("Osborne");
        assertThat(updatedCustomer.getGivenName()).isEqualTo("Ozzy");
    }

    @Test
    @Betamax(tape = "get customers created after")
    public void shouldGetCustomersCreatedAfter() {
        List<Customer> result =
                client.customers().list().withCreatedAtGte("2015-04-13T15:02:40Z").execute()
                        .getItems();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo("CU0000321DW2ZH");
    }

    @Test
    @Betamax(tape = "create a payment")
    public void shouldCreateAPayment() {
        Payment payment =
                client.payments().create().withAmount(2000).withCurrency("GBP")
                        .withMetadata("foo", "bar").withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getAmount()).isEqualTo(2000);
        assertThat(payment.getCurrency()).isEqualTo("GBP");
        assertThat(payment.getMetadata()).hasSize(1).containsEntry("foo", "bar");
        assertThat(payment.getLinks().getMandate()).isEqualTo("MD00001PEYCSQF");
    }

    @Test
    @Betamax(tape = "page through mandates")
    public void shouldPageThroughMandates() {
        ListResponse<Mandate> page1 = client.mandates().list().withLimit(2).execute();
        assertThat(page1.getItems()).hasSize(2);
        assertThat(page1.getItems().get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(page1.getItems().get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(page1.getBefore()).isNull();
        assertThat(page1.getAfter()).isNotNull();
        assertThat(page1.getLimit()).isEqualTo(2);
        ListResponse<Mandate> page2 =
                client.mandates().list().withLimit(2).withAfter(page1.getAfter()).execute();
        assertThat(page2.getItems()).hasSize(1);
        assertThat(page2.getItems().get(0).getId()).isEqualTo("MD00001P1KTRNY");
        assertThat(page2.getBefore()).isNotNull();
        assertThat(page2.getAfter()).isNull();
        assertThat(page2.getLimit()).isEqualTo(2);
    }

    @Test
    @Betamax(tape = "iterate through mandates")
    public void shouldIterateThroughMandates() {
        Iterable<Mandate> iterable = client.mandates().all().withLimit(2).execute();
        List<Mandate> mandates = Lists.newArrayList(iterable);
        assertThat(mandates).hasSize(3);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(mandates.get(2).getId()).isEqualTo("MD00001P1KTRNY");
    }

    @Test
    @Betamax(tape = "cancel a mandate")
    public void shouldCancelAMandate() {
        Mandate mandate =
                client.mandates().cancel("MD00001P1KTRNY").withMetadata("foo", "bar").execute();
        assertThat(mandate.getNextPossibleChargeDate()).isNull();
    }

    @Test
    @Betamax(tape = "create a subscription")
    public void shouldCreateASubscription() {
        Subscription subscription =
                client.subscriptions().create().withAmount(1000).withCurrency("GBP")
                        .withIntervalUnit(MONTHLY).withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(subscription.getId()).isNotNull();
    }
}
