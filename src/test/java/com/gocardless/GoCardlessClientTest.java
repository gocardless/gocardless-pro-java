package com.gocardless;

import java.util.List;

import com.gocardless.http.ApiResponse;
import com.gocardless.http.ListResponse;
import com.gocardless.http.MockHttp;
import com.gocardless.resources.*;
import com.gocardless.services.PaymentService.PaymentCreateRequest;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.gocardless.services.MandateService.MandateListRequest.Status.ACTIVE;
import static com.gocardless.services.MandateService.MandateListRequest.Status.FAILED;
import static com.gocardless.services.SubscriptionService.SubscriptionCreateRequest.IntervalUnit.MONTHLY;

import static org.assertj.core.api.Assertions.assertThat;

public class GoCardlessClientTest {
    private static final String ACCESS_TOKEN = "access-token";
    private GoCardlessClient client;
    @Rule
    public final MockHttp http = new MockHttp();

    @Before
    public void setUp() throws Exception {
        client = GoCardlessClient.create(ACCESS_TOKEN, http.getBaseUrl());
    }

    @Test
    public void shouldGetACustomer() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_a_customer.json");
        Customer customer = client.customers().get("CU00003068FG73").execute();
        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
        http.assertRequestMade("GET", "/customers/CU00003068FG73");
    }

    @Test
    public void shouldGetACustomerWrapped() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_a_customer.json",
                ImmutableMap.of("RateLimit-Limit", "1000"));
        ApiResponse<Customer> response = client.customers().get("CU00003068FG73").executeWrapped();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getHeaders().get("RateLimit-Limit")).containsExactly("1000");
        Customer customer = response.getResource();
        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
        http.assertRequestMade("GET", "/customers/CU00003068FG73");
    }

    @Test
    public void shouldListCustomers() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_customers.json");
        List<Customer> customers = client.customers().list().execute().getItems();
        assertThat(customers).hasSize(2);
        assertThat(customers.get(0).getId()).isEqualTo("CU00003068FG73");
        assertThat(customers.get(0).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(0).getGivenName()).isEqualTo("Frank");
        assertThat(customers.get(1).getId()).isEqualTo("CU0000302M1J1J");
        assertThat(customers.get(1).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(1).getGivenName()).isEqualTo("Sarah");
        http.assertRequestMade("GET", "/customers");
    }

    @Test
    public void shouldListMandatesForACustomer() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_for_a_customer.json");
        List<Mandate> mandates =
                client.mandates().list().withCustomer("CU00003068FG73").execute().getItems();
        assertThat(mandates).hasSize(2);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(0).getLinks().getCreditor()).isEqualTo("CR000035EME9H5");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(mandates.get(1).getLinks().getCreditor()).isEqualTo("CR000035EME9H5");
        http.assertRequestMade("GET", "/mandates?customer=CU00003068FG73");
    }

    @Test
    public void shouldListMandatesByCustomerAndStatus() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_by_customer_and_status.json");
        List<Mandate> mandates =
                client.mandates().list().withCustomer("CU00003068FG73").withStatus(ACTIVE)
                        .withStatus(FAILED).execute().getItems();
        assertThat(mandates).hasSize(1);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        http.assertRequestMade("GET", "/mandates?customer=CU00003068FG73&status=active,failed");
    }

    @Test
    public void shouldCreateACustomer() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_customer_response.json");
        Customer customer =
                client.customers().create().withFamilyName("Osborne").withGivenName("Sharon")
                        .withAddressLine1("27 Acer Road").withAddressLine2("Apt 2")
                        .withCity("London").withPostalCode("E8 3GX").withCountryCode("GB")
                        .execute();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Sharon");
        http.assertRequestMade("POST", "/customers",
                "fixtures/client/create_a_customer_request.json");
    }

    @Test
    public void shouldUpdateACustomer() throws Exception {
        http.enqueueResponse(200, "fixtures/client/update_a_customer_response.json");
        Customer customer =
                client.customers().update("CU000031FFQ5H3").withGivenName("Ozzy").execute();
        assertThat(customer.getId()).isEqualTo("CU000031FFQ5H3");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Ozzy");
        http.assertRequestMade("PUT", "/customers/CU000031FFQ5H3",
                "fixtures/client/update_a_customer_request.json");
    }

    @Test
    public void shouldGetCustomersCreatedAfter() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_customers_created_after.json");
        List<Customer> result =
                client.customers().list().withCreatedAtGte("2015-04-13T15:02:40Z").execute()
                        .getItems();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo("CU0000321DW2ZH");
        http.assertRequestMade("GET", "/customers?created_at[gte]=2015-04-13T15:02:40Z");
    }

    @Test
    public void shouldCreateAPayment() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_payment_response.json");
        Payment payment =
                client.payments().create().withAmount(2000)
                        .withCurrency(PaymentCreateRequest.Currency.GBP).withMetadata("foo", "bar")
                        .withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getAmount()).isEqualTo(2000);
        assertThat(payment.getCurrency()).isEqualTo(Payment.Currency.GBP);
        assertThat(payment.getMetadata()).hasSize(1).containsEntry("foo", "bar");
        assertThat(payment.getLinks().getMandate()).isEqualTo("MD00001PEYCSQF");
        http.assertRequestMade("POST", "/payments", "fixtures/client/create_a_payment_request.json");
    }

    @Test
    public void shouldPageThroughMandates() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_1.json");
        ListResponse<Mandate> page1 = client.mandates().list().withLimit(2).execute();
        assertThat(page1.getItems()).hasSize(2);
        assertThat(page1.getItems().get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(page1.getItems().get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(page1.getBefore()).isNull();
        assertThat(page1.getAfter()).isNotNull();
        assertThat(page1.getLimit()).isEqualTo(2);
        http.assertRequestMade("GET", "/mandates?limit=2");
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_2.json");
        ListResponse<Mandate> page2 =
                client.mandates().list().withLimit(2).withAfter(page1.getAfter()).execute();
        assertThat(page2.getItems()).hasSize(1);
        assertThat(page2.getItems().get(0).getId()).isEqualTo("MD00001P1KTRNY");
        assertThat(page2.getBefore()).isNotNull();
        assertThat(page2.getAfter()).isNull();
        assertThat(page2.getLimit()).isEqualTo(2);
        http.assertRequestMade("GET", "/mandates?after=MD00001P57AN84&limit=2");
    }

    @Test
    public void shouldIterateThroughMandates() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_1.json");
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_2.json");
        Iterable<Mandate> iterable = client.mandates().all().withLimit(2).execute();
        List<Mandate> mandates = Lists.newArrayList(iterable);
        assertThat(mandates).hasSize(3);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(mandates.get(2).getId()).isEqualTo("MD00001P1KTRNY");
        http.assertRequestMade("GET", "/mandates?limit=2");
        http.assertRequestMade("GET", "/mandates?after=MD00001P57AN84&limit=2");
    }

    @Test
    public void shouldCancelAMandate() throws Exception {
        http.enqueueResponse(200, "fixtures/client/cancel_a_mandate_response.json");
        Mandate mandate =
                client.mandates().cancel("MD00001P1KTRNY").withMetadata("foo", "bar").execute();
        assertThat(mandate.getNextPossibleChargeDate()).isNull();
        http.assertRequestMade("POST", "/mandates/MD00001P1KTRNY/actions/cancel",
                "fixtures/client/cancel_a_mandate_request.json");
    }

    @Test
    public void shouldCreateASubscription() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_subscription_response.json");
        Subscription subscription =
                client.subscriptions().create().withAmount(1000).withCurrency("GBP")
                        .withIntervalUnit(MONTHLY).withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(subscription.getId()).isNotNull();
        http.assertRequestMade("POST", "/subscriptions",
                "fixtures/client/create_a_subscription_request.json");
    }

    @Test
    public void shouldListEnabledCreditorBankAccounts() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_enabled_creditor_bank_accounts.json");
        Iterable<CreditorBankAccount> iterable =
                client.creditorBankAccounts().all().withEnabled(true).execute();
        List<CreditorBankAccount> bankAccounts = Lists.newArrayList(iterable);
        assertThat(bankAccounts).hasSize(1);
        assertThat(bankAccounts.get(0).getId()).isEqualTo("BA00001NN2B44F");
        http.assertRequestMade("GET", "/creditor_bank_accounts?enabled=true");
    }
}
