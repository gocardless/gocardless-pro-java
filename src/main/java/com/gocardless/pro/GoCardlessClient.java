package com.gocardless.pro;

import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.services.*;

import com.google.common.annotations.VisibleForTesting;

/**
 * Entry point into the client.
 */
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

    /**
     * A service class for working with api key resources.
     */
    public ApiKeyService apiKeys() {
        return apiKeys;
    }

    /**
     * A service class for working with creditor resources.
     */
    public CreditorService creditors() {
        return creditors;
    }

    /**
     * A service class for working with creditor bank account resources.
     */
    public CreditorBankAccountService creditorBankAccounts() {
        return creditorBankAccounts;
    }

    /**
     * A service class for working with customer resources.
     */
    public CustomerService customers() {
        return customers;
    }

    /**
     * A service class for working with customer bank account resources.
     */
    public CustomerBankAccountService customerBankAccounts() {
        return customerBankAccounts;
    }

    /**
     * A service class for working with event resources.
     */
    public EventService events() {
        return events;
    }

    /**
     * A service class for working with helper resources.
     */
    public HelperService helpers() {
        return helpers;
    }

    /**
     * A service class for working with mandate resources.
     */
    public MandateService mandates() {
        return mandates;
    }

    /**
     * A service class for working with payment resources.
     */
    public PaymentService payments() {
        return payments;
    }

    /**
     * A service class for working with payout resources.
     */
    public PayoutService payouts() {
        return payouts;
    }

    /**
     * A service class for working with publishable api key resources.
     */
    public PublishableApiKeyService publishableApiKeys() {
        return publishableApiKeys;
    }

    /**
     * A service class for working with redirect flow resources.
     */
    public RedirectFlowService redirectFlows() {
        return redirectFlows;
    }

    /**
     * A service class for working with refund resources.
     */
    public RefundService refunds() {
        return refunds;
    }

    /**
     * A service class for working with role resources.
     */
    public RoleService roles() {
        return roles;
    }

    /**
     * A service class for working with subscription resources.
     */
    public SubscriptionService subscriptions() {
        return subscriptions;
    }

    /**
     * A service class for working with user resources.
     */
    public UserService users() {
        return users;
    }

    /**
     * Available environments for this client.
     */
    public enum Environment {
        /**
         * Live environment (base URL https://api.gocardless.com).
         */
        LIVE,
        /**
         * Sandbox environment (base URL https://api-sandbox.gocardless.com).
         */
        SANDBOX;
        private String getBaseUrl() {
            switch (this) {
                case LIVE:
                    return "https://api.gocardless.com";
                case SANDBOX:
                    return "https://api-sandbox.gocardless.com";
            }
            throw new IllegalArgumentException("Unknown environment:" + this);
        }
    }

    /**
     * Creates an instance of the client in the live environment.
     *
     * @param apiKey the API key
     * @param apiSecret the API secret
     */
    public static GoCardlessClient create(String apiKey, String apiSecret) {
        return create(apiKey, apiSecret, Environment.LIVE);
    }

    /**
     * Creates an instance of the client in a specified environment.
     *
     * @param apiKey the API key
     * @param apiSecret the API secret
     * @param environment the environment
     */
    public static GoCardlessClient create(String apiKey, String apiSecret, Environment environment) {
        return create(apiKey, apiSecret, environment.getBaseUrl());
    }

    /**
     * Creates an instance of the client running against a custom URL.
     *
     * @param apiKey the API key
     * @param apiSecret the API secret
     * @param baseUrl the base URL of the API
     */
    public static GoCardlessClient create(String apiKey, String apiSecret, String baseUrl) {
        return new GoCardlessClient(new HttpClient(apiKey, apiSecret, baseUrl));
    }

    @VisibleForTesting
    HttpClient getHttpClient() {
        return httpClient;
    }
}
