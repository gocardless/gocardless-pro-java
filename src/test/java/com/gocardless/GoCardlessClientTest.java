package com.gocardless;

import static com.gocardless.services.MandateService.MandateListRequest.Status.ACTIVE;
import static com.gocardless.services.MandateService.MandateListRequest.Status.FAILED;
import static com.gocardless.services.SubscriptionService.SubscriptionCreateRequest.IntervalUnit.MONTHLY;
import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.ApiResponse;
import com.gocardless.http.ListResponse;
import com.gocardless.http.MockHttp;
import com.gocardless.resources.*;
import com.gocardless.services.CustomerService.CustomerCreateRequest;
import com.gocardless.services.PaymentService.PaymentCreateRequest;
import com.gocardless.services.SubscriptionService.SubscriptionCreateRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GoCardlessClientTest {
    private static final String ACCESS_TOKEN = "access-token";
    private GoCardlessClient client;
    @Rule
    public final MockHttp http = new MockHttp();

    @Before
    public void setUp() throws Exception {
        client = GoCardlessClient.newBuilder(ACCESS_TOKEN).withBaseUrl(http.getBaseUrl()).build();
    }

    @Test
    public void shouldGetACustomer() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_a_customer.json");
        Customer customer = client.customers().get("CU00003068FG73").execute();
        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
        http.assertRequestMade("GET", "/customers/CU00003068FG73",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldGetACustomerWrapped() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_a_customer.json",
                ImmutableMap.of("RateLimit-Limit", "1000"));
        ApiResponse<Customer> response = client.customers().get("CU00003068FG73")
                .withHeader("Accept-Language", "fr-FR").executeWrapped();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getHeaders().get("RateLimit-Limit")).containsExactly("1000");
        Customer customer = response.getResource();
        assertThat(customer.getId()).isEqualTo("CU00003068FG73");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Frank");
        http.assertRequestMade("GET", "/customers/CU00003068FG73", ImmutableMap.of("Authorization",
                "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    public void shouldListCustomers() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_customers.json");
        List<Customer> customers = client.customers().list().withHeader("Accept-Language", "fr-FR")
                .execute().getItems();
        assertThat(customers).hasSize(2);
        assertThat(customers.get(0).getId()).isEqualTo("CU00003068FG73");
        assertThat(customers.get(0).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(0).getGivenName()).isEqualTo("Frank");
        assertThat(customers.get(1).getId()).isEqualTo("CU0000302M1J1J");
        assertThat(customers.get(1).getFamilyName()).isEqualTo("Osborne");
        assertThat(customers.get(1).getGivenName()).isEqualTo("Sarah");
        http.assertRequestMade("GET", "/customers", ImmutableMap.of("Authorization",
                "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
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
        http.assertRequestMade("GET", "/mandates?customer=CU00003068FG73",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldListMandatesByCustomerAndStatus() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_by_customer_and_status.json");
        List<Mandate> mandates = client.mandates().list().withCustomer("CU00003068FG73")
                .withStatus(ACTIVE).withStatus(FAILED).execute().getItems();
        assertThat(mandates).hasSize(1);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        http.assertRequestMade("GET", "/mandates?customer=CU00003068FG73&status=active,failed",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldCreateACustomer() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_customer_response.json");
        Customer customer = client.customers().create().withHeader("Accept-Language", "fr-FR")
                .withFamilyName("Osborne").withGivenName("Sharon").withAddressLine1("27 Acer Road")
                .withAddressLine2("Apt 2").withCity("London").withPostalCode("E8 3GX")
                .withCountryCode("GB").execute();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Sharon");
        http.assertRequestMade("POST", "/customers",
                "fixtures/client/create_a_customer_request.json", ImmutableMap.of("Authorization",
                        "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldCreateACustomerWithAGeneratedIdempotencyKey() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_customer_response.json");
        CustomerCreateRequest request = client.customers().create()
                .withHeader("Accept-Language", "fr-FR").withFamilyName("Osborne")
                .withGivenName("Sharon").withAddressLine1("27 Acer Road").withAddressLine2("Apt 2")
                .withCity("London").withPostalCode("E8 3GX").withCountryCode("GB");
        request.execute();
        http.assertRequestIncludedHeader("Idempotency-Key");
        http.enqueueResponse(201, "fixtures/client/create_a_customer_response.json");
        request.execute();
        http.assertRequestMade("POST", "/customers",
                "fixtures/client/create_a_customer_request.json", ImmutableMap.of("Authorization",
                        "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldHandleConflictWhenCreatingACustomer() throws Exception {
        http.enqueueResponse(409, "fixtures/conflict.json");
        http.enqueueResponse(200, "fixtures/client/create_a_customer_response.json");
        CustomerCreateRequest request = client.customers().create()
                .withHeader("Accept-Language", "fr-FR").withFamilyName("Osborne")
                .withGivenName("Sharon").withAddressLine1("27 Acer Road").withAddressLine2("Apt 2")
                .withCity("London").withPostalCode("E8 3GX").withCountryCode("GB");
        request.execute();
        http.assertRequestMade("POST", "/customers",
                "fixtures/client/create_a_customer_request.json", ImmutableMap.of("Authorization",
                        "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
        http.assertRequestMade("GET", "/customers/ID123", ImmutableMap.of("Authorization",
                "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldUpdateACustomer() throws Exception {
        http.enqueueResponse(200, "fixtures/client/update_a_customer_response.json");
        Customer customer = client.customers().update("CU000031FFQ5H3")
                .withHeader("Accept-Language", "fr-FR").withGivenName("Ozzy").execute();
        assertThat(customer.getId()).isEqualTo("CU000031FFQ5H3");
        assertThat(customer.getFamilyName()).isEqualTo("Osborne");
        assertThat(customer.getGivenName()).isEqualTo("Ozzy");
        http.assertRequestMade("PUT", "/customers/CU000031FFQ5H3",
                "fixtures/client/update_a_customer_request.json", ImmutableMap.of("Authorization",
                        "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldGetCustomersCreatedAfter() throws Exception {
        http.enqueueResponse(200, "fixtures/client/get_customers_created_after.json");
        List<Customer> result = client.customers().list().withCreatedAtGte("2015-04-13T15:02:40Z")
                .execute().getItems();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo("CU0000321DW2ZH");
        http.assertRequestMade("GET", "/customers?created_at[gte]=2015-04-13T15:02:40Z",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldCreateAPayment() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_payment_response.json");
        Payment payment = client.payments().create().withAmount(2000)
                .withCurrency(PaymentCreateRequest.Currency.GBP).withMetadata("foo", "bar")
                .withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getAmount()).isEqualTo(2000);
        assertThat(payment.getCurrency()).isEqualTo(Payment.Currency.GBP);
        assertThat(payment.getMetadata()).hasSize(1).containsEntry("foo", "bar");
        assertThat(payment.getLinks().getMandate()).isEqualTo("MD00001PEYCSQF");
        http.assertRequestMade("POST", "/payments", "fixtures/client/create_a_payment_request.json",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldPageThroughMandates() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_1.json");
        ListResponse<Mandate> page1 = client.mandates().list()
                .withHeader("Accept-Language", "fr-FR").withLimit(2).execute();
        assertThat(page1.getItems()).hasSize(2);
        assertThat(page1.getItems().get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(page1.getItems().get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(page1.getBefore()).isNull();
        assertThat(page1.getAfter()).isNotNull();
        assertThat(page1.getLimit()).isEqualTo(2);
        http.assertRequestMade("GET", "/mandates?limit=2", ImmutableMap.of("Authorization",
                "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_2.json");
        ListResponse<Mandate> page2 =
                client.mandates().list().withHeader("Accept-Language", "fr-FR").withLimit(2)
                        .withAfter(page1.getAfter()).execute();
        assertThat(page2.getItems()).hasSize(1);
        assertThat(page2.getItems().get(0).getId()).isEqualTo("MD00001P1KTRNY");
        assertThat(page2.getBefore()).isNotNull();
        assertThat(page2.getAfter()).isNull();
        assertThat(page2.getLimit()).isEqualTo(2);
        http.assertRequestMade("GET", "/mandates?after=MD00001P57AN84&limit=2", ImmutableMap
                .of("Authorization", "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldIterateThroughMandates() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_1.json");
        http.enqueueResponse(200, "fixtures/client/list_mandates_page_2.json");
        Iterable<Mandate> iterable = client.mandates().all().withHeader("Accept-Language", "fr-FR")
                .withLimit(2).execute();
        List<Mandate> mandates = Lists.newArrayList(iterable);
        assertThat(mandates).hasSize(3);
        assertThat(mandates.get(0).getId()).isEqualTo("MD00001PEYCSQF");
        assertThat(mandates.get(1).getId()).isEqualTo("MD00001P57AN84");
        assertThat(mandates.get(2).getId()).isEqualTo("MD00001P1KTRNY");
        http.assertRequestMade("GET", "/mandates?limit=2", ImmutableMap.of("Authorization",
                "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
        http.assertRequestMade("GET", "/mandates?after=MD00001P57AN84&limit=2", ImmutableMap
                .of("Authorization", "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldCancelAMandate() throws Exception {
        http.enqueueResponse(200, "fixtures/client/cancel_a_mandate_response.json");
        Mandate mandate = client.mandates().cancel("MD00001P1KTRNY")
                .withHeader("Accept-Language", "fr-FR").withMetadata("foo", "bar").execute();
        assertThat(mandate.getNextPossibleChargeDate()).isNull();
        http.assertRequestMade("POST", "/mandates/MD00001P1KTRNY/actions/cancel",
                "fixtures/client/cancel_a_mandate_request.json", ImmutableMap.of("Authorization",
                        "Bearer " + ACCESS_TOKEN, "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldCreateASubscription() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_subscription_response.json");
        Subscription subscription =
                client.subscriptions().create().withAmount(1000).withCurrency("GBP")
                        .withIntervalUnit(MONTHLY).withLinksMandate("MD00001PEYCSQF").execute();
        assertThat(subscription.getId()).isNotNull();
        http.assertRequestMade("POST", "/subscriptions",
                "fixtures/client/create_a_subscription_request.json",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }

    @Test
    public void shouldCreateASubscriptionWithAGeneratedIdempotencyKey() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_subscription_response.json");
        SubscriptionCreateRequest request = client.subscriptions().create()
                .withHeader("Accept-Language", "fr-FR").withAmount(1000).withCurrency("GBP")
                .withIntervalUnit(MONTHLY).withLinksMandate("MD00001PEYCSQF");
        Subscription subscription = request.execute();
        assertThat(subscription.getId()).isNotNull();
        http.assertRequestIncludedHeader("Idempotency-Key");
        http.enqueueResponse(201, "fixtures/client/create_a_subscription_response.json");
        request.execute();
        http.assertRequestIncludedHeader("Accept-Language");
    }

    @Test
    public void shouldCreateASubscriptionWithSpecifiedIdempotencyKey() throws Exception {
        http.enqueueResponse(201, "fixtures/client/create_a_subscription_response.json");
        Subscription subscription = client.subscriptions().create().withIdempotencyKey("lol")
                .withHeader("Accept-Language", "fr-FR").withAmount(1000).withCurrency("GBP")
                .withIntervalUnit(MONTHLY).withLinksMandate("MD00001PEYCSQF").execute();;
        assertThat(subscription.getId()).isNotNull();
        http.assertRequestMade("POST", "/subscriptions",
                "fixtures/client/create_a_subscription_request.json",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN, "Idempotency-Key", "lol",
                        "Accept-Language", "fr-FR"));
    }

    @Test
    public void shouldListEnabledCreditorBankAccounts() throws Exception {
        http.enqueueResponse(200, "fixtures/client/list_enabled_creditor_bank_accounts.json");
        Iterable<CreditorBankAccount> iterable =
                client.creditorBankAccounts().all().withEnabled(true).execute();
        List<CreditorBankAccount> bankAccounts = Lists.newArrayList(iterable);
        assertThat(bankAccounts).hasSize(1);
        assertThat(bankAccounts.get(0).getId()).isEqualTo("BA00001NN2B44F");
        http.assertRequestMade("GET", "/creditor_bank_accounts?enabled=true",
                ImmutableMap.of("Authorization", "Bearer " + ACCESS_TOKEN));
    }
}
