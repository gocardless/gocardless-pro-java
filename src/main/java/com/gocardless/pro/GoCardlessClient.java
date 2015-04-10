package com.gocardless.pro;

import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.services.*;
import com.google.common.annotations.VisibleForTesting;

public class GoCardlessClient {
    private final HttpClient httpClient;
    private final ApiKeyService apiKeys;
    private final CreditorService creditors;
    private final CreditorBankAccountService creditorBankAccounts;
    private final CustomerService customers;
    private final CustomerBankAccountService customerBankAccounts;
    private final EventService events;
    private final HelperService helpers;
    private final MandateService mandates;
    private final PaymentService payments;
    private final PayoutService payouts;
    private final PublishableApiKeyService publishableApiKeys;
    private final RedirectFlowService redirectFlows;
    private final RefundService refunds;
    private final RoleService roles;
    private final SubscriptionService subscriptions;
    private final UserService users;

    private GoCardlessClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.apiKeys = new ApiKeyService(httpClient);
        this.creditors = new CreditorService(httpClient);
        this.creditorBankAccounts = new CreditorBankAccountService(httpClient);
        this.customers = new CustomerService(httpClient);
        this.customerBankAccounts = new CustomerBankAccountService(httpClient);
        this.events = new EventService(httpClient);
        this.helpers = new HelperService(httpClient);
        this.mandates = new MandateService(httpClient);
        this.payments = new PaymentService(httpClient);
        this.payouts = new PayoutService(httpClient);
        this.publishableApiKeys = new PublishableApiKeyService(httpClient);
        this.redirectFlows = new RedirectFlowService(httpClient);
        this.refunds = new RefundService(httpClient);
        this.roles = new RoleService(httpClient);
        this.subscriptions = new SubscriptionService(httpClient);
        this.users = new UserService(httpClient);
    }

    public ApiKeyService apiKeys() {
        return apiKeys;
    }

    public CreditorService creditors() {
        return creditors;
    }

    public CreditorBankAccountService creditorBankAccounts() {
        return creditorBankAccounts;
    }

    public CustomerService customers() {
        return customers;
    }

    public CustomerBankAccountService customerBankAccounts() {
        return customerBankAccounts;
    }

    public EventService events() {
        return events;
    }

    public HelperService helpers() {
        return helpers;
    }

    public MandateService mandates() {
        return mandates;
    }

    public PaymentService payments() {
        return payments;
    }

    public PayoutService payouts() {
        return payouts;
    }

    public PublishableApiKeyService publishableApiKeys() {
        return publishableApiKeys;
    }

    public RedirectFlowService redirectFlows() {
        return redirectFlows;
    }

    public RefundService refunds() {
        return refunds;
    }

    public RoleService roles() {
        return roles;
    }

    public SubscriptionService subscriptions() {
        return subscriptions;
    }

    public UserService users() {
        return users;
    }

    @VisibleForTesting
    HttpClient getHttpClient() {
        return httpClient;
    }

    public static GoCardlessClient create(String apiKey, String apiSecret, String baseUrl) {
        return new GoCardlessClient(new HttpClient(apiKey, apiSecret, baseUrl));
    }
}
