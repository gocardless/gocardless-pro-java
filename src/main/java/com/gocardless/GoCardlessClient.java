package com.gocardless;

import com.gocardless.http.HttpClient;
import com.gocardless.services.*;

import com.google.common.annotations.VisibleForTesting;

/**
 * Entry point into the client.
 */
public class GoCardlessClient {
    private final HttpClient httpClient;
    private final CreditorService creditors;
    private final CreditorBankAccountService creditorBankAccounts;
    private final CustomerService customers;
    private final CustomerBankAccountService customerBankAccounts;
    private final EventService events;
    private final HelperService helpers;
    private final MandateService mandates;
    private final PaymentService payments;
    private final PayoutService payouts;
    private final RedirectFlowService redirectFlows;
    private final RefundService refunds;
    private final SubscriptionService subscriptions;

    private GoCardlessClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.creditors = new CreditorService(httpClient);
        this.creditorBankAccounts = new CreditorBankAccountService(httpClient);
        this.customers = new CustomerService(httpClient);
        this.customerBankAccounts = new CustomerBankAccountService(httpClient);
        this.events = new EventService(httpClient);
        this.helpers = new HelperService(httpClient);
        this.mandates = new MandateService(httpClient);
        this.payments = new PaymentService(httpClient);
        this.payouts = new PayoutService(httpClient);
        this.redirectFlows = new RedirectFlowService(httpClient);
        this.refunds = new RefundService(httpClient);
        this.subscriptions = new SubscriptionService(httpClient);
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
     * A service class for working with subscription resources.
     */
    public SubscriptionService subscriptions() {
        return subscriptions;
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
     * @param accessToken the access token
     */
    public static GoCardlessClient create(String accessToken) {
        return create(accessToken, Environment.LIVE);
    }

    /**
     * Creates an instance of the client in a specified environment.
     *
     * @param accessToken the access token
     * @param environment the environment
     */
    public static GoCardlessClient create(String accessToken, Environment environment) {
        return create(accessToken, environment.getBaseUrl());
    }

    /**
     * Creates an instance of the client running against a custom URL.
     *
     * @param accessToken the access token
     * @param baseUrl the base URL of the API
     */
    public static GoCardlessClient create(String accessToken, String baseUrl) {
        return new GoCardlessClient(new HttpClient(accessToken, baseUrl));
    }

    @VisibleForTesting
    HttpClient getHttpClient() {
        return httpClient;
    }
}
