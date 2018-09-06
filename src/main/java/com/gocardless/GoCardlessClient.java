package com.gocardless;

import java.net.Proxy;

import javax.net.ssl.SSLSocketFactory;

import com.gocardless.http.HttpClient;
import com.gocardless.services.*;

import com.google.common.annotations.VisibleForTesting;

import com.squareup.okhttp.OkHttpClient;

/**
 * Entry point into the client.
 */
public class GoCardlessClient {
    private final HttpClient httpClient;
    private final BankDetailsLookupService bankDetailsLookups;
    private final CreditorService creditors;
    private final CreditorBankAccountService creditorBankAccounts;
    private final CustomerService customers;
    private final CustomerBankAccountService customerBankAccounts;
    private final CustomerNotificationService customerNotifications;
    private final EventService events;
    private final MandateService mandates;
    private final MandateImportService mandateImports;
    private final MandateImportEntryService mandateImportEntries;
    private final MandatePdfService mandatePdfs;
    private final PaymentService payments;
    private final PayoutService payouts;
    private final PayoutItemService payoutItems;
    private final RedirectFlowService redirectFlows;
    private final RefundService refunds;
    private final SubscriptionService subscriptions;

    public static final class Builder {
        private final String accessToken;
        private String baseUrl;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;

        /**
         * Constructor.  Users of this library will not need to access this constructor directly -
         * they should use GoCardlessClient.newBuilder() to get an instance. The client
         * will automatically be configured to use GoCardless' live environment
         * and the supplied access token.
         *
         * @param accessToken the access token to use to access the GoCardless API
         */
        private Builder(String accessToken) {
            this.accessToken = accessToken;
            this.withEnvironment(Environment.LIVE);
        }

        /**
         * Configures the base URL for the client
         *
         * @param baseUrl the base URL for the GoCardless API
         */
        public Builder withBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Configures the GoCardless environment for the client
         *
         * @param environment the GoCardless environment to connect to
         */
        public Builder withEnvironment(Environment environment) {
            return this.withBaseUrl(environment.getBaseUrl());
        }

        /**
         * Configures the proxy to use for HTTP requests made by the client
         *
         * @param proxy the proxy to use for HTTP requests made by the client
         */
        public Builder withProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        /**
         * Configures the SSL socket factory to be used when making HTTP requests
         *
         * @param sslSocketFactory the SSL socket factory to be used when making HTTP requests
         */
        public Builder withSslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * Builds a configured instance of the GoCardlessClient
         */
        public GoCardlessClient build() {
            OkHttpClient rawClient = new OkHttpClient();
            rawClient.setProxy(proxy);
            rawClient.setSslSocketFactory(sslSocketFactory);
            HttpClient client = new HttpClient(accessToken, baseUrl, rawClient);
            return new GoCardlessClient(client);
        }
    }

    private GoCardlessClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.bankDetailsLookups = new BankDetailsLookupService(httpClient);
        this.creditors = new CreditorService(httpClient);
        this.creditorBankAccounts = new CreditorBankAccountService(httpClient);
        this.customers = new CustomerService(httpClient);
        this.customerBankAccounts = new CustomerBankAccountService(httpClient);
        this.customerNotifications = new CustomerNotificationService(httpClient);
        this.events = new EventService(httpClient);
        this.mandates = new MandateService(httpClient);
        this.mandateImports = new MandateImportService(httpClient);
        this.mandateImportEntries = new MandateImportEntryService(httpClient);
        this.mandatePdfs = new MandatePdfService(httpClient);
        this.payments = new PaymentService(httpClient);
        this.payouts = new PayoutService(httpClient);
        this.payoutItems = new PayoutItemService(httpClient);
        this.redirectFlows = new RedirectFlowService(httpClient);
        this.refunds = new RefundService(httpClient);
        this.subscriptions = new SubscriptionService(httpClient);
    }

    /**
     * A service class for working with bank details lookup resources.
     */
    public BankDetailsLookupService bankDetailsLookups() {
        return bankDetailsLookups;
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
     * A service class for working with customer notification resources.
     */
    public CustomerNotificationService customerNotifications() {
        return customerNotifications;
    }

    /**
     * A service class for working with event resources.
     */
    public EventService events() {
        return events;
    }

    /**
     * A service class for working with mandate resources.
     */
    public MandateService mandates() {
        return mandates;
    }

    /**
     * A service class for working with mandate import resources.
     */
    public MandateImportService mandateImports() {
        return mandateImports;
    }

    /**
     * A service class for working with mandate import entry resources.
     */
    public MandateImportEntryService mandateImportEntries() {
        return mandateImportEntries;
    }

    /**
     * A service class for working with mandate pdf resources.
     */
    public MandatePdfService mandatePdfs() {
        return mandatePdfs;
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
     * A service class for working with payout item resources.
     */
    public PayoutItemService payoutItems() {
        return payoutItems;
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
     * Returns a builder which can be used to configure and instantiate a GoCardlessClient instance.
     * The client will automatically be configured to use GoCardless' live environment
     * and the supplied access token.
     *
     * @param accessToken the access token to use to access the GoCardless API
     */
    public static Builder newBuilder(String accessToken) {
        return new Builder(accessToken);
    }

    @VisibleForTesting
    HttpClient getHttpClient() {
        return httpClient;
    }
}
