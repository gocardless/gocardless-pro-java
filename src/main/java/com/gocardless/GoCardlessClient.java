package com.gocardless;

import com.gocardless.http.HttpClient;
import com.gocardless.http.LoggingInterceptor;
import com.gocardless.services.*;
import com.google.common.annotations.VisibleForTesting;
import java.net.Proxy;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/**
 * Entry point into the client.
 */
public class GoCardlessClient {
    private final HttpClient httpClient;
    private final BankAuthorisationService bankAuthorisations;
    private final BankDetailsLookupService bankDetailsLookups;
    private final BillingRequestService billingRequests;
    private final BillingRequestFlowService billingRequestFlows;
    private final BillingRequestTemplateService billingRequestTemplates;
    private final BlockService blocks;
    private final CreditorService creditors;
    private final CreditorBankAccountService creditorBankAccounts;
    private final CurrencyExchangeRateService currencyExchangeRates;
    private final CustomerService customers;
    private final CustomerBankAccountService customerBankAccounts;
    private final CustomerNotificationService customerNotifications;
    private final EventService events;
    private final InstalmentScheduleService instalmentSchedules;
    private final InstitutionService institutions;
    private final MandateService mandates;
    private final MandateImportService mandateImports;
    private final MandateImportEntryService mandateImportEntries;
    private final MandatePdfService mandatePdfs;
    private final PayerAuthorisationService payerAuthorisations;
    private final PaymentService payments;
    private final PayoutService payouts;
    private final PayoutItemService payoutItems;
    private final RedirectFlowService redirectFlows;
    private final RefundService refunds;
    private final ScenarioSimulatorService scenarioSimulators;
    private final SubscriptionService subscriptions;
    private final TaxRateService taxRates;
    private final WebhookService webhooks;

    public static final class Builder {
        private final String accessToken;
        private String baseUrl;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;
        private X509TrustManager trustManager;
        private boolean errorOnIdempotencyConflict;

        /**
         * Constructor. Users of this library will not need to access this constructor directly -
         * they should use GoCardlessClient.newBuilder() to get an instance. The client will
         * automatically be configured to use GoCardless' live environment and the supplied access
         * token.
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
        public Builder withSslSocketFactoryAndTrustManager(SSLSocketFactory sslSocketFactory,
                X509TrustManager trustManager) {
            this.sslSocketFactory = sslSocketFactory;
            this.trustManager = trustManager;
            return this;
        }

        /**
         * Configures the behaviour on an Idempotency Conflict error
         *
         * @param errorOnIdempotencyConflict true to raise an error upon conflict, false to fetch
         *        the conflicting resource instead.
         */
        public Builder withErrorOnIdempotencyConflict(boolean errorOnIdempotencyConflict) {
            this.errorOnIdempotencyConflict = errorOnIdempotencyConflict;
            return this;
        }

        /**
         * Builds a configured instance of the GoCardlessClient
         */
        public GoCardlessClient build() {
            OkHttpClient.Builder rawClientBuilder = new OkHttpClient.Builder().proxy(proxy);
            if (sslSocketFactory != null && trustManager != null) {
                rawClientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
            }
            OkHttpClient rawClient =
                    rawClientBuilder.addInterceptor(new LoggingInterceptor()).build();
            HttpClient client =
                    new HttpClient(accessToken, baseUrl, rawClient, errorOnIdempotencyConflict);
            return new GoCardlessClient(client);
        }
    }

    private GoCardlessClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.bankAuthorisations = new BankAuthorisationService(httpClient);
        this.bankDetailsLookups = new BankDetailsLookupService(httpClient);
        this.billingRequests = new BillingRequestService(httpClient);
        this.billingRequestFlows = new BillingRequestFlowService(httpClient);
        this.billingRequestTemplates = new BillingRequestTemplateService(httpClient);
        this.blocks = new BlockService(httpClient);
        this.creditors = new CreditorService(httpClient);
        this.creditorBankAccounts = new CreditorBankAccountService(httpClient);
        this.currencyExchangeRates = new CurrencyExchangeRateService(httpClient);
        this.customers = new CustomerService(httpClient);
        this.customerBankAccounts = new CustomerBankAccountService(httpClient);
        this.customerNotifications = new CustomerNotificationService(httpClient);
        this.events = new EventService(httpClient);
        this.instalmentSchedules = new InstalmentScheduleService(httpClient);
        this.institutions = new InstitutionService(httpClient);
        this.mandates = new MandateService(httpClient);
        this.mandateImports = new MandateImportService(httpClient);
        this.mandateImportEntries = new MandateImportEntryService(httpClient);
        this.mandatePdfs = new MandatePdfService(httpClient);
        this.payerAuthorisations = new PayerAuthorisationService(httpClient);
        this.payments = new PaymentService(httpClient);
        this.payouts = new PayoutService(httpClient);
        this.payoutItems = new PayoutItemService(httpClient);
        this.redirectFlows = new RedirectFlowService(httpClient);
        this.refunds = new RefundService(httpClient);
        this.scenarioSimulators = new ScenarioSimulatorService(httpClient);
        this.subscriptions = new SubscriptionService(httpClient);
        this.taxRates = new TaxRateService(httpClient);
        this.webhooks = new WebhookService(httpClient);
    }

    /**
     * A service class for working with bank authorisation resources.
     */
    public BankAuthorisationService bankAuthorisations() {
        return bankAuthorisations;
    }

    /**
     * A service class for working with bank details lookup resources.
     */
    public BankDetailsLookupService bankDetailsLookups() {
        return bankDetailsLookups;
    }

    /**
     * A service class for working with billing request resources.
     */
    public BillingRequestService billingRequests() {
        return billingRequests;
    }

    /**
     * A service class for working with billing request flow resources.
     */
    public BillingRequestFlowService billingRequestFlows() {
        return billingRequestFlows;
    }

    /**
     * A service class for working with billing request template resources.
     */
    public BillingRequestTemplateService billingRequestTemplates() {
        return billingRequestTemplates;
    }

    /**
     * A service class for working with block resources.
     */
    public BlockService blocks() {
        return blocks;
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
     * A service class for working with currency exchange rate resources.
     */
    public CurrencyExchangeRateService currencyExchangeRates() {
        return currencyExchangeRates;
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
     * A service class for working with instalment schedule resources.
     */
    public InstalmentScheduleService instalmentSchedules() {
        return instalmentSchedules;
    }

    /**
     * A service class for working with institution resources.
     */
    public InstitutionService institutions() {
        return institutions;
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
     * A service class for working with payer authorisation resources.
     */
    public PayerAuthorisationService payerAuthorisations() {
        return payerAuthorisations;
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
     * A service class for working with scenario simulator resources.
     */
    public ScenarioSimulatorService scenarioSimulators() {
        return scenarioSimulators;
    }

    /**
     * A service class for working with subscription resources.
     */
    public SubscriptionService subscriptions() {
        return subscriptions;
    }

    /**
     * A service class for working with tax rate resources.
     */
    public TaxRateService taxRates() {
        return taxRates;
    }

    /**
     * A service class for working with webhook resources.
     */
    public WebhookService webhooks() {
        return webhooks;
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
     * The client will automatically be configured to use GoCardless' live environment and the
     * supplied access token.
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
