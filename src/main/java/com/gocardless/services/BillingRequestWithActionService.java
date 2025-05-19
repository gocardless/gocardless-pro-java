package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequestWithAction;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with billing request with action resources.
 *
 * Billing Requests help create resources that require input or action from a customer. An example
 * of required input might be additional customer billing details, while an action would be asking a
 * customer to authorise a payment using their mobile banking app.
 * 
 * See [Billing Requests:
 * Overview](https://developer.gocardless.com/getting-started/billing-requests/overview/) for
 * how-to's, explanations and tutorials.
 * <p class="notice">
 * <strong>Important</strong>: All properties associated with `subscription_request` and
 * `instalment_schedule_request` are only supported for ACH and PAD schemes.
 * </p>
 */
public class BillingRequestWithActionService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#billingRequestWithActions() }.
     */
    public BillingRequestWithActionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a billing request and completes any specified actions in a single request. This
     * endpoint allows you to create a billing request and immediately complete actions such as
     * collecting customer details, bank account details, or other required actions.
     */
    public BillingRequestWithActionCreateWithActionsRequest createWithActions() {
        return new BillingRequestWithActionCreateWithActionsRequest(httpClient);
    }

    /**
     * Request class for {@link BillingRequestWithActionService#createWithActions }.
     *
     * Creates a billing request and completes any specified actions in a single request. This
     * endpoint allows you to create a billing request and immediately complete actions such as
     * collecting customer details, bank account details, or other required actions.
     */
    public static final class BillingRequestWithActionCreateWithActionsRequest
            extends PostRequest<BillingRequestWithAction> {
        private Actions actions;
        private Boolean fallbackEnabled;
        private Links links;
        private MandateRequest mandateRequest;
        private Map<String, String> metadata;
        private PaymentRequest paymentRequest;
        private PurposeCode purposeCode;
        private SubscriptionRequest subscriptionRequest;

        /**
         * Action payloads
         */
        public BillingRequestWithActionCreateWithActionsRequest withActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        /**
         * URL for an oauth flow that will allow the user to authorise the payment
         */
        public BillingRequestWithActionCreateWithActionsRequest withActionsBankAuthorisationRedirectUri(
                String bankAuthorisationRedirectUri) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withBankAuthorisationRedirectUri(bankAuthorisationRedirectUri);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withActionsCollectBankAccount(
                CollectBankAccount collectBankAccount) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withCollectBankAccount(collectBankAccount);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withActionsCollectCustomerDetails(
                CollectCustomerDetails collectCustomerDetails) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withCollectCustomerDetails(collectCustomerDetails);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withActionsConfirmPayerDetails(
                ConfirmPayerDetails confirmPayerDetails) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withConfirmPayerDetails(confirmPayerDetails);
            return this;
        }

        /**
         * Create a bank authorisation object as part of this request
         */
        public BillingRequestWithActionCreateWithActionsRequest withActionsCreateBankAuthorisation(
                Boolean createBankAuthorisation) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withCreateBankAuthorisation(createBankAuthorisation);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withActionsSelectInstitution(
                SelectInstitution selectInstitution) {
            if (actions == null) {
                actions = new Actions();
            }
            actions.withSelectInstitution(selectInstitution);
            return this;
        }

        /**
         * (Optional) If true, this billing request can fallback from instant payment to direct
         * debit. Should not be set if GoCardless payment intelligence feature is used.
         * 
         * See [Billing Requests: Retain customers with
         * Fallbacks](https://developer.gocardless.com/billing-requests/retain-customers-with-fallbacks/)
         * for more information.
         */
        public BillingRequestWithActionCreateWithActionsRequest withFallbackEnabled(
                Boolean fallbackEnabled) {
            this.fallbackEnabled = fallbackEnabled;
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors). Only required if your account
         * manages multiple creditors.
         */
        public BillingRequestWithActionCreateWithActionsRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * ID of the [customer](#core-endpoints-customers) against which this request should be
         * made.
         */
        public BillingRequestWithActionCreateWithActionsRequest withLinksCustomer(String customer) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomer(customer);
            return this;
        }

        /**
         * (Optional) ID of the [customer_bank_account](#core-endpoints-customer-bank-accounts)
         * against which this request should be made.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withLinksCustomerBankAccount(
                String customerBankAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomerBankAccount(customerBankAccount);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withMandateRequest(
                MandateRequest mandateRequest) {
            this.mandateRequest = mandateRequest;
            return this;
        }

        /**
         * This field is ACH specific, sometimes referred to as [SEC
         * code](https://www.moderntreasury.com/learn/sec-codes).
         * 
         * This is the way that the payer gives authorisation to the merchant. web: Authorisation is
         * Internet Initiated or via Mobile Entry (maps to SEC code: WEB) telephone: Authorisation
         * is provided orally over telephone (maps to SEC code: TEL) paper: Authorisation is
         * provided in writing and signed, or similarly authenticated (maps to SEC code: PPD)
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestAuthorisationSource(
                MandateRequest.AuthorisationSource authorisationSource) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withAuthorisationSource(authorisationSource);
            return this;
        }

        /**
         * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo and
         * VRP.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestConstraints(
                Constraints constraints) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withConstraints(constraints);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestCurrency(
                String currency) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withCurrency(currency);
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestDescription(
                String description) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withDescription(description);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestMetadata(
                Map<String, String> metadata) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withMetadata(metadata);
            return this;
        }

        /**
         * Unique reference. Different schemes have different length and [character
         * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
         * satisfying the different scheme requirements if this field is left blank.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestReference(
                String reference) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withReference(reference);
            return this;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         * Optional for mandate only requests - if left blank, the payer will be able to select the
         * currency/scheme to pay with from a list of your available schemes.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestScheme(
                String scheme) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withScheme(scheme);
            return this;
        }

        /**
         * If true, this billing request would be used to set up a mandate solely for moving (or
         * sweeping) money from one account owned by the payer to another account that the payer
         * also owns. This is required for Faster Payments
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestSweeping(
                Boolean sweeping) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withSweeping(sweeping);
            return this;
        }

        /**
         * Verification preference for the mandate. One of:
         * <ul>
         * <li>`minimum`: only verify if absolutely required, such as when part of scheme rules</li>
         * <li>`recommended`: in addition to `minimum`, use the GoCardless payment intelligence
         * solution to decide if a payer should be verified</li>
         * <li>`when_available`: if verification mechanisms are available, use them</li>
         * <li>`always`: as `when_available`, but fail to create the Billing Request if a mechanism
         * isn't available</li>
         * </ul>
         * 
         * By default, all Billing Requests use the `recommended` verification preference. It uses
         * GoCardless payment intelligence solution to determine if a payer is fraudulent or not.
         * The verification mechanism is based on the response and the payer may be asked to verify
         * themselves. If the feature is not available, `recommended` behaves like `minimum`.
         * 
         * If you never wish to take advantage of our reduced risk products and Verified Mandates as
         * they are released in new schemes, please use the `minimum` verification preference.
         * 
         * See [Billing Requests: Creating Verified
         * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
         * for more information.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMandateRequestVerify(
                MandateRequest.Verify verify) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withVerify(verify);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMetadata(
                Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withMetadata(String key,
                String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequest(
                PaymentRequest paymentRequest) {
            this.paymentRequest = paymentRequest;
            return this;
        }

        /**
         * Amount in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestAmount(
                Integer amount) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withAmount(amount);
            return this;
        }

        /**
         * The amount to be deducted from the payment as an app fee, to be paid to the partner
         * integration which created the billing request, in the lowest denomination for the
         * currency (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestAppFee(
                Integer appFee) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withAppFee(appFee);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
         * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
         * supported Eurozone countries only.
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestCurrency(
                String currency) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withCurrency(currency);
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestDescription(
                String description) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withDescription(description);
            return this;
        }

        /**
         * This field will decide how GoCardless handles settlement of funds from the customer.
         * 
         * - `managed` will be moved through GoCardless' account, batched, and payed out. - `direct`
         * will be a direct transfer from the payer's account to the merchant where invoicing will
         * be handled separately.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestFundsSettlement(
                PaymentRequest.FundsSettlement fundsSettlement) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withFundsSettlement(fundsSettlement);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestMetadata(
                Map<String, String> metadata) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withMetadata(metadata);
            return this;
        }

        /**
         * A custom payment reference defined by the merchant. It is only available for payments
         * using the Direct Funds settlement model on the Faster Payments scheme.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestReference(
                String reference) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withReference(reference);
            return this;
        }

        /**
         * On failure, automatically retry payments using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
         * </p>
         * <p class="notice">
         * <strong>Important</strong>: This is not applicable to IBP and VRP payments.
         * </p>
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestRetryIfPossible(
                Boolean retryIfPossible) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withRetryIfPossible(retryIfPossible);
            return this;
        }

        /**
         * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
         * supported in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer`
         * are supported in supported Eurozone countries (EUR). For Eurozone countries,
         * `sepa_credit_transfer` is used as the default. Please be aware that
         * `sepa_instant_credit_transfer` may incur an additional fee for your customer.
         */
        public BillingRequestWithActionCreateWithActionsRequest withPaymentRequestScheme(
                String scheme) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withScheme(scheme);
            return this;
        }

        /**
         * Specifies the high-level purpose of a mandate and/or payment using a set of pre-defined
         * categories. Required for the PayTo scheme, optional for all others. Currently `mortgage`,
         * `utility`, `loan`, `dependant_support`, `gambling`, `retail`, `salary`, `personal`,
         * `government`, `pension`, `tax` and `other` are supported.
         */
        public BillingRequestWithActionCreateWithActionsRequest withPurposeCode(
                PurposeCode purposeCode) {
            this.purposeCode = purposeCode;
            return this;
        }

        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequest(
                SubscriptionRequest subscriptionRequest) {
            this.subscriptionRequest = subscriptionRequest;
            return this;
        }

        /**
         * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestAmount(
                Integer amount) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withAmount(amount);
            return this;
        }

        /**
         * The amount to be deducted from each payment as an app fee, to be paid to the partner
         * integration which created the subscription, in the lowest denomination for the currency
         * (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestAppFee(
                Integer appFee) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withAppFee(appFee);
            return this;
        }

        /**
         * The total number of payments that should be taken by this subscription.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestCount(
                Integer count) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withCount(count);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestCurrency(
                String currency) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withCurrency(currency);
            return this;
        }

        /**
         * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to
         * indicate the last day of the month.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestDayOfMonth(
                Integer dayOfMonth) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withDayOfMonth(dayOfMonth);
            return this;
        }

        /**
         * Number of `interval_units` between customer charge dates. Must be greater than or equal
         * to `1`. Must result in at least one charge date per year. Defaults to `1`.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestInterval(
                Integer interval) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withInterval(interval);
            return this;
        }

        /**
         * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestIntervalUnit(
                SubscriptionRequest.IntervalUnit intervalUnit) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withIntervalUnit(intervalUnit);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestMetadata(
                Map<String, String> metadata) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withMetadata(metadata);
            return this;
        }

        /**
         * Name of the month on which to charge a customer. Must be lowercase. Only applies when the
         * interval_unit is `yearly`.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestMonth(
                SubscriptionRequest.Month month) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withMonth(month);
            return this;
        }

        /**
         * Optional name for the subscription. This will be set as the description on each payment
         * created. Must not exceed 255 characters.
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestName(
                String name) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withName(name);
            return this;
        }

        /**
         * An optional payment reference. This will be set as the reference on each payment created
         * and will appear on your customer's bank statement. See the documentation for the [create
         * payment endpoint](#payments-create-a-payment) for more details. <br />
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestPaymentReference(
                String paymentReference) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withPaymentReference(paymentReference);
            return this;
        }

        /**
         * On failure, automatically retry payments using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
         * </p>
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestRetryIfPossible(
                Boolean retryIfPossible) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withRetryIfPossible(retryIfPossible);
            return this;
        }

        /**
         * The date on which the first payment should be charged. If fulfilled after this date, this
         * will be set as the mandate's `next_possible_charge_date`. When left blank and `month` or
         * `day_of_month` are provided, this will be set to the date of the first payment. If
         * created without `month` or `day_of_month` this will be set as the mandate's
         * `next_possible_charge_date`.
         * 
         */
        public BillingRequestWithActionCreateWithActionsRequest withSubscriptionRequestStartDate(
                String startDate) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withStartDate(startDate);
            return this;
        }

        private BillingRequestWithActionCreateWithActionsRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BillingRequestWithActionCreateWithActionsRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/create_with_actions";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequestWithAction> getResponseClass() {
            return BillingRequestWithAction.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum PurposeCode {
            @SerializedName("mortgage")
            MORTGAGE, @SerializedName("utility")
            UTILITY, @SerializedName("loan")
            LOAN, @SerializedName("dependant_support")
            DEPENDANT_SUPPORT, @SerializedName("gambling")
            GAMBLING, @SerializedName("retail")
            RETAIL, @SerializedName("salary")
            SALARY, @SerializedName("personal")
            PERSONAL, @SerializedName("government")
            GOVERNMENT, @SerializedName("pension")
            PENSION, @SerializedName("tax")
            TAX, @SerializedName("other")
            OTHER, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class CollectBankAccount {
            private String accountHolderName;
            private String accountNumber;
            private String accountNumberSuffix;
            private AccountType accountType;
            private String bankCode;
            private String branchCode;
            private String countryCode;
            private String currency;
            private String iban;
            private Map<String, String> metadata;
            private String payId;

            /**
             * Name of the account holder, as known by the bank. This field will be transliterated,
             * upcased and truncated to 18 characters. This field is required unless the request
             * includes a [customer bank account
             * token](#javascript-flow-customer-bank-account-tokens).
             */
            public CollectBankAccount withAccountHolderName(String accountHolderName) {
                this.accountHolderName = accountHolderName;
                return this;
            }

            /**
             * Bank account number - see [local details](#appendix-local-bank-details) for more
             * information. Alternatively you can provide an `iban`.
             */
            public CollectBankAccount withAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
                return this;
            }

            /**
             * Account number suffix (only for bank accounts denominated in NZD) - see [local
             * details](#local-bank-details-new-zealand) for more information.
             */
            public CollectBankAccount withAccountNumberSuffix(String accountNumberSuffix) {
                this.accountNumberSuffix = accountNumberSuffix;
                return this;
            }

            /**
             * Bank account type. Required for USD-denominated bank accounts. Must not be provided
             * for bank accounts in other currencies. See [local
             * details](#local-bank-details-united-states) for more information.
             */
            public CollectBankAccount withAccountType(AccountType accountType) {
                this.accountType = accountType;
                return this;
            }

            /**
             * Bank code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
             */
            public CollectBankAccount withBankCode(String bankCode) {
                this.bankCode = bankCode;
                return this;
            }

            /**
             * Branch code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
             */
            public CollectBankAccount withBranchCode(String branchCode) {
                this.branchCode = branchCode;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
             * Defaults to the country code of the `iban` if supplied, otherwise is required.
             */
            public CollectBankAccount withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
             */
            public CollectBankAccount withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * International Bank Account Number. Alternatively you can provide [local
             * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank
             * accounts denominated in SEK - you must supply [local
             * details](#local-bank-details-sweden).
             */
            public CollectBankAccount withIban(String iban) {
                this.iban = iban;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public CollectBankAccount withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * A unique record such as an email address, mobile number or company number, that can
             * be used to make and accept payments.
             */
            public CollectBankAccount withPayId(String payId) {
                this.payId = payId;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (accountHolderName != null) {
                    params.put("collect_bank_account[account_holder_name]", accountHolderName);
                }
                if (accountNumber != null) {
                    params.put("collect_bank_account[account_number]", accountNumber);
                }
                if (accountNumberSuffix != null) {
                    params.put("collect_bank_account[account_number_suffix]", accountNumberSuffix);
                }
                if (accountType != null) {
                    params.put("collect_bank_account[account_type]", accountType);
                }
                if (bankCode != null) {
                    params.put("collect_bank_account[bank_code]", bankCode);
                }
                if (branchCode != null) {
                    params.put("collect_bank_account[branch_code]", branchCode);
                }
                if (countryCode != null) {
                    params.put("collect_bank_account[country_code]", countryCode);
                }
                if (currency != null) {
                    params.put("collect_bank_account[currency]", currency);
                }
                if (iban != null) {
                    params.put("collect_bank_account[iban]", iban);
                }
                if (metadata != null) {
                    params.put("collect_bank_account[metadata]", metadata);
                }
                if (payId != null) {
                    params.put("collect_bank_account[pay_id]", payId);
                }
                return params.build();
            }

            public enum AccountType {
                @SerializedName("savings")
                SAVINGS, @SerializedName("checking")
                CHECKING, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }

        public static class Customer {
            private String companyName;
            private String email;
            private String familyName;
            private String givenName;
            private String language;
            private Map<String, String> metadata;
            private String phoneNumber;

            /**
             * Customer's company name. Required unless a `given_name` and `family_name` are
             * provided. For Canadian customers, the use of a `company_name` value will mean that
             * any mandate created from this customer will be considered to be a "Business PAD"
             * (otherwise, any mandate will be considered to be a "Personal PAD").
             */
            public Customer withCompanyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            /**
             * Customer's email address. Required in most cases, as this allows GoCardless to send
             * notifications to this customer.
             */
            public Customer withEmail(String email) {
                this.email = email;
                return this;
            }

            /**
             * Customer's surname. Required unless a `company_name` is provided.
             */
            public Customer withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * Customer's first name. Required unless a `company_name` is provided.
             */
            public Customer withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the
             * language for notification emails sent by GoCardless if your organisation does not
             * send its own (see [compliance requirements](#appendix-compliance-requirements)).
             * Currently only "en", "fr", "de", "pt", "es", "it", "nl", "da", "nb", "sl", "sv" are
             * supported. If this is not provided and a customer was linked during billing request
             * creation, the linked customer language will be used. Otherwise, the language is
             * default to "en".
             */
            public Customer withLanguage(String language) {
                this.language = language;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Customer withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including
             * country code.
             */
            public Customer withPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (companyName != null) {
                    params.put("customer[company_name]", companyName);
                }
                if (email != null) {
                    params.put("customer[email]", email);
                }
                if (familyName != null) {
                    params.put("customer[family_name]", familyName);
                }
                if (givenName != null) {
                    params.put("customer[given_name]", givenName);
                }
                if (language != null) {
                    params.put("customer[language]", language);
                }
                if (metadata != null) {
                    params.put("customer[metadata]", metadata);
                }
                if (phoneNumber != null) {
                    params.put("customer[phone_number]", phoneNumber);
                }
                return params.build();
            }
        }

        public static class CustomerBillingDetail {
            private String addressLine1;
            private String addressLine2;
            private String addressLine3;
            private String city;
            private String countryCode;
            private String danishIdentityNumber;
            private String ipAddress;
            private String postalCode;
            private String region;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address.
             */
            public CustomerBillingDetail withAddressLine1(String addressLine1) {
                this.addressLine1 = addressLine1;
                return this;
            }

            /**
             * The second line of the customer's address.
             */
            public CustomerBillingDetail withAddressLine2(String addressLine2) {
                this.addressLine2 = addressLine2;
                return this;
            }

            /**
             * The third line of the customer's address.
             */
            public CustomerBillingDetail withAddressLine3(String addressLine3) {
                this.addressLine3 = addressLine3;
                return this;
            }

            /**
             * The city of the customer's address.
             */
            public CustomerBillingDetail withCity(String city) {
                this.city = city;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             */
            public CustomerBillingDetail withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
             * Must be supplied if the customer's bank account is denominated in Danish krone (DKK).
             */
            public CustomerBillingDetail withDanishIdentityNumber(String danishIdentityNumber) {
                this.danishIdentityNumber = danishIdentityNumber;
                return this;
            }

            /**
             * For ACH customers only. Required for ACH customers. A string containing the IP
             * address of the payer to whom the mandate belongs (i.e. as a result of their
             * completion of a mandate setup flow in their browser).
             * 
             * Not required for creating offline mandates where `authorisation_source` is set to
             * telephone or paper.
             * 
             */
            public CustomerBillingDetail withIpAddress(String ipAddress) {
                this.ipAddress = ipAddress;
                return this;
            }

            /**
             * The customer's postal code.
             */
            public CustomerBillingDetail withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The customer's address region, county or department. For US customers a 2 letter
             * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required
             * (e.g. `CA` for California).
             */
            public CustomerBillingDetail withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer,
             * samordningsnummer, or organisationsnummer) of the customer. Must be supplied if the
             * customer's bank account is denominated in Swedish krona (SEK). This field cannot be
             * changed once it has been set.
             */
            public CustomerBillingDetail withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (addressLine1 != null) {
                    params.put("customer_billing_detail[address_line1]", addressLine1);
                }
                if (addressLine2 != null) {
                    params.put("customer_billing_detail[address_line2]", addressLine2);
                }
                if (addressLine3 != null) {
                    params.put("customer_billing_detail[address_line3]", addressLine3);
                }
                if (city != null) {
                    params.put("customer_billing_detail[city]", city);
                }
                if (countryCode != null) {
                    params.put("customer_billing_detail[country_code]", countryCode);
                }
                if (danishIdentityNumber != null) {
                    params.put("customer_billing_detail[danish_identity_number]",
                            danishIdentityNumber);
                }
                if (ipAddress != null) {
                    params.put("customer_billing_detail[ip_address]", ipAddress);
                }
                if (postalCode != null) {
                    params.put("customer_billing_detail[postal_code]", postalCode);
                }
                if (region != null) {
                    params.put("customer_billing_detail[region]", region);
                }
                if (swedishIdentityNumber != null) {
                    params.put("customer_billing_detail[swedish_identity_number]",
                            swedishIdentityNumber);
                }
                return params.build();
            }
        }

        public static class CollectCustomerDetails {
            private Customer customer;
            private CustomerBillingDetail customerBillingDetail;

            public CollectCustomerDetails withCustomer(Customer customer) {
                this.customer = customer;
                return this;
            }

            public CollectCustomerDetails withCustomerBillingDetail(
                    CustomerBillingDetail customerBillingDetail) {
                this.customerBillingDetail = customerBillingDetail;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (customer != null) {
                    params.put("collect_customer_details[customer]", customer);
                }
                if (customerBillingDetail != null) {
                    params.put("collect_customer_details[customer_billing_detail]",
                            customerBillingDetail);
                }
                return params.build();
            }
        }

        public static class ConfirmPayerDetails {
            private Map<String, String> metadata;
            private Boolean payerRequestedDualSignature;

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public ConfirmPayerDetails withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * This attribute can be set to true if the payer has indicated that multiple signatures
             * are required for the mandate. As long as every other Billing Request actions have
             * been completed, the payer will receive an email notification containing instructions
             * on how to complete the additional signature. The dual signature flow can only be
             * completed using GoCardless branded pages.
             */
            public ConfirmPayerDetails withPayerRequestedDualSignature(
                    Boolean payerRequestedDualSignature) {
                this.payerRequestedDualSignature = payerRequestedDualSignature;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (metadata != null) {
                    params.put("confirm_payer_details[metadata]", metadata);
                }
                if (payerRequestedDualSignature != null) {
                    params.put("confirm_payer_details[payer_requested_dual_signature]",
                            payerRequestedDualSignature);
                }
                return params.build();
            }
        }

        public static class SelectInstitution {
            private String countryCode;
            private String institution;

            /**
             * [ISO
             * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             * alpha-2 code. The country code of the institution. If nothing is provided,
             * institutions with the country code 'GB' are returned by default.
             */
            public SelectInstitution withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * The unique identifier for this institution
             */
            public SelectInstitution withInstitution(String institution) {
                this.institution = institution;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (countryCode != null) {
                    params.put("select_institution[country_code]", countryCode);
                }
                if (institution != null) {
                    params.put("select_institution[institution]", institution);
                }
                return params.build();
            }
        }

        public static class Actions {
            private String bankAuthorisationRedirectUri;
            private CollectBankAccount collectBankAccount;
            private CollectCustomerDetails collectCustomerDetails;
            private ConfirmPayerDetails confirmPayerDetails;
            private Boolean createBankAuthorisation;
            private SelectInstitution selectInstitution;

            /**
             * URL for an oauth flow that will allow the user to authorise the payment
             */
            public Actions withBankAuthorisationRedirectUri(String bankAuthorisationRedirectUri) {
                this.bankAuthorisationRedirectUri = bankAuthorisationRedirectUri;
                return this;
            }

            public Actions withCollectBankAccount(CollectBankAccount collectBankAccount) {
                this.collectBankAccount = collectBankAccount;
                return this;
            }

            public Actions withCollectCustomerDetails(
                    CollectCustomerDetails collectCustomerDetails) {
                this.collectCustomerDetails = collectCustomerDetails;
                return this;
            }

            public Actions withConfirmPayerDetails(ConfirmPayerDetails confirmPayerDetails) {
                this.confirmPayerDetails = confirmPayerDetails;
                return this;
            }

            /**
             * Create a bank authorisation object as part of this request
             */
            public Actions withCreateBankAuthorisation(Boolean createBankAuthorisation) {
                this.createBankAuthorisation = createBankAuthorisation;
                return this;
            }

            public Actions withSelectInstitution(SelectInstitution selectInstitution) {
                this.selectInstitution = selectInstitution;
                return this;
            }
        }

        public static class Links {
            private String creditor;
            private String customer;
            private String customerBankAccount;

            /**
             * ID of the associated [creditor](#core-endpoints-creditors). Only required if your
             * account manages multiple creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }

            /**
             * ID of the [customer](#core-endpoints-customers) against which this request should be
             * made.
             */
            public Links withCustomer(String customer) {
                this.customer = customer;
                return this;
            }

            /**
             * (Optional) ID of the [customer_bank_account](#core-endpoints-customer-bank-accounts)
             * against which this request should be made.
             * 
             */
            public Links withCustomerBankAccount(String customerBankAccount) {
                this.customerBankAccount = customerBankAccount;
                return this;
            }
        }

        public static class PeriodicLimits {
            private Alignment alignment;
            private Integer maxPayments;
            private Integer maxTotalAmount;
            private Period period;

            /**
             * The alignment of the period.
             * 
             * `calendar` - this will finish on the end of the current period. For example this will
             * expire on the Monday for the current week or the January for the next year.
             * 
             * `creation_date` - this will finish on the next instance of the current period. For
             * example Monthly it will expire on the same day of the next month, or yearly the same
             * day of the next year.
             * 
             */
            public PeriodicLimits withAlignment(Alignment alignment) {
                this.alignment = alignment;
                return this;
            }

            /**
             * (Optional) The maximum number of payments that can be collected in this periodic
             * limit.
             */
            public PeriodicLimits withMaxPayments(Integer maxPayments) {
                this.maxPayments = maxPayments;
                return this;
            }

            /**
             * The maximum total amount that can be charged for all payments in this periodic limit.
             * Required for VRP.
             * 
             */
            public PeriodicLimits withMaxTotalAmount(Integer maxTotalAmount) {
                this.maxTotalAmount = maxTotalAmount;
                return this;
            }

            /**
             * The repeating period for this mandate
             */
            public PeriodicLimits withPeriod(Period period) {
                this.period = period;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (alignment != null) {
                    params.put("periodic_limits[alignment]", alignment);
                }
                if (maxPayments != null) {
                    params.put("periodic_limits[max_payments]", maxPayments);
                }
                if (maxTotalAmount != null) {
                    params.put("periodic_limits[max_total_amount]", maxTotalAmount);
                }
                if (period != null) {
                    params.put("periodic_limits[period]", period);
                }
                return params.build();
            }

            public enum Alignment {
                @SerializedName("calendar")
                CALENDAR, @SerializedName("creation_date")
                CREATION_DATE, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }

            public enum Period {
                @SerializedName("day")
                DAY, @SerializedName("week")
                WEEK, @SerializedName("month")
                MONTH, @SerializedName("year")
                YEAR, @SerializedName("flexible")
                FLEXIBLE, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }

        public static class Constraints {
            private String endDate;
            private Integer maxAmountPerPayment;
            private String paymentMethod;
            private List<PeriodicLimits> periodicLimits;
            private String startDate;

            /**
             * The latest date at which payments can be taken, must occur after start_date if
             * present
             * 
             * This is an optional field and if it is not supplied the agreement will be considered
             * open and will not have an end date. Keep in mind the end date must take into account
             * how long it will take the user to set up this agreement via the Billing Request.
             * 
             */
            public Constraints withEndDate(String endDate) {
                this.endDate = endDate;
                return this;
            }

            /**
             * The maximum amount that can be charged for a single payment. Required for VRP.
             */
            public Constraints withMaxAmountPerPayment(Integer maxAmountPerPayment) {
                this.maxAmountPerPayment = maxAmountPerPayment;
                return this;
            }

            /**
             * A constraint where you can specify info (free text string) about how payments are
             * calculated. _Note:_ This is only supported for ACH and PAD schemes.
             * 
             */
            public Constraints withPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
                return this;
            }

            /**
             * List of periodic limits and constraints which apply to them
             */
            public Constraints withPeriodicLimits(List<PeriodicLimits> periodicLimits) {
                this.periodicLimits = periodicLimits;
                return this;
            }

            /**
             * The date from which payments can be taken.
             * 
             * This is an optional field and if it is not supplied the start date will be set to the
             * day authorisation happens.
             * 
             */
            public Constraints withStartDate(String startDate) {
                this.startDate = startDate;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (endDate != null) {
                    params.put("constraints[end_date]", endDate);
                }
                if (maxAmountPerPayment != null) {
                    params.put("constraints[max_amount_per_payment]", maxAmountPerPayment);
                }
                if (paymentMethod != null) {
                    params.put("constraints[payment_method]", paymentMethod);
                }
                if (periodicLimits != null) {
                    params.put("constraints[periodic_limits]", periodicLimits);
                }
                if (startDate != null) {
                    params.put("constraints[start_date]", startDate);
                }
                return params.build();
            }
        }

        public static class MandateRequest {
            private AuthorisationSource authorisationSource;
            private Constraints constraints;
            private String currency;
            private String description;
            private Map<String, String> metadata;
            private String reference;
            private String scheme;
            private Boolean sweeping;
            private Verify verify;

            /**
             * This field is ACH specific, sometimes referred to as [SEC
             * code](https://www.moderntreasury.com/learn/sec-codes).
             * 
             * This is the way that the payer gives authorisation to the merchant. web:
             * Authorisation is Internet Initiated or via Mobile Entry (maps to SEC code: WEB)
             * telephone: Authorisation is provided orally over telephone (maps to SEC code: TEL)
             * paper: Authorisation is provided in writing and signed, or similarly authenticated
             * (maps to SEC code: PPD)
             * 
             */
            public MandateRequest withAuthorisationSource(AuthorisationSource authorisationSource) {
                this.authorisationSource = authorisationSource;
                return this;
            }

            /**
             * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo
             * and VRP.
             */
            public MandateRequest withConstraints(Constraints constraints) {
                this.constraints = constraints;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             */
            public MandateRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             * 
             */
            public MandateRequest withDescription(String description) {
                this.description = description;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public MandateRequest withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * Unique reference. Different schemes have different length and [character
             * set](#appendix-character-sets) requirements. GoCardless will generate a unique
             * reference satisfying the different scheme requirements if this field is left blank.
             */
            public MandateRequest withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
             * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
             * Optional for mandate only requests - if left blank, the payer will be able to select
             * the currency/scheme to pay with from a list of your available schemes.
             */
            public MandateRequest withScheme(String scheme) {
                this.scheme = scheme;
                return this;
            }

            /**
             * If true, this billing request would be used to set up a mandate solely for moving (or
             * sweeping) money from one account owned by the payer to another account that the payer
             * also owns. This is required for Faster Payments
             */
            public MandateRequest withSweeping(Boolean sweeping) {
                this.sweeping = sweeping;
                return this;
            }

            /**
             * Verification preference for the mandate. One of:
             * <ul>
             * <li>`minimum`: only verify if absolutely required, such as when part of scheme
             * rules</li>
             * <li>`recommended`: in addition to `minimum`, use the GoCardless payment intelligence
             * solution to decide if a payer should be verified</li>
             * <li>`when_available`: if verification mechanisms are available, use them</li>
             * <li>`always`: as `when_available`, but fail to create the Billing Request if a
             * mechanism isn't available</li>
             * </ul>
             * 
             * By default, all Billing Requests use the `recommended` verification preference. It
             * uses GoCardless payment intelligence solution to determine if a payer is fraudulent
             * or not. The verification mechanism is based on the response and the payer may be
             * asked to verify themselves. If the feature is not available, `recommended` behaves
             * like `minimum`.
             * 
             * If you never wish to take advantage of our reduced risk products and Verified
             * Mandates as they are released in new schemes, please use the `minimum` verification
             * preference.
             * 
             * See [Billing Requests: Creating Verified
             * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
             * for more information.
             */
            public MandateRequest withVerify(Verify verify) {
                this.verify = verify;
                return this;
            }

            public enum AuthorisationSource {
                @SerializedName("web")
                WEB, @SerializedName("telephone")
                TELEPHONE, @SerializedName("paper")
                PAPER, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }

            public enum Verify {
                @SerializedName("minimum")
                MINIMUM, @SerializedName("recommended")
                RECOMMENDED, @SerializedName("when_available")
                WHEN_AVAILABLE, @SerializedName("always")
                ALWAYS, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }

        public static class PaymentRequest {
            private Integer amount;
            private Integer appFee;
            private String currency;
            private String description;
            private FundsSettlement fundsSettlement;
            private Map<String, String> metadata;
            private String reference;
            private Boolean retryIfPossible;
            private String scheme;

            /**
             * Amount in minor unit (e.g. pence in GBP, cents in EUR).
             */
            public PaymentRequest withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * The amount to be deducted from the payment as an app fee, to be paid to the partner
             * integration which created the billing request, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             */
            public PaymentRequest withAppFee(Integer appFee) {
                this.appFee = appFee;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP`
             * and `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your
             * customers in supported Eurozone countries only.
             */
            public PaymentRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             * 
             */
            public PaymentRequest withDescription(String description) {
                this.description = description;
                return this;
            }

            /**
             * This field will decide how GoCardless handles settlement of funds from the customer.
             * 
             * - `managed` will be moved through GoCardless' account, batched, and payed out. -
             * `direct` will be a direct transfer from the payer's account to the merchant where
             * invoicing will be handled separately.
             * 
             */
            public PaymentRequest withFundsSettlement(FundsSettlement fundsSettlement) {
                this.fundsSettlement = fundsSettlement;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public PaymentRequest withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * A custom payment reference defined by the merchant. It is only available for payments
             * using the Direct Funds settlement model on the Faster Payments scheme.
             * 
             */
            public PaymentRequest withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * On failure, automatically retry payments using [intelligent
             * retries](#success-intelligent-retries). Default is `false`.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
             * </p>
             * <p class="notice">
             * <strong>Important</strong>: This is not applicable to IBP and VRP payments.
             * </p>
             */
            public PaymentRequest withRetryIfPossible(Boolean retryIfPossible) {
                this.retryIfPossible = retryIfPossible;
                return this;
            }

            /**
             * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
             * supported in the UK (GBP) and `sepa_credit_transfer` and
             * `sepa_instant_credit_transfer` are supported in supported Eurozone countries (EUR).
             * For Eurozone countries, `sepa_credit_transfer` is used as the default. Please be
             * aware that `sepa_instant_credit_transfer` may incur an additional fee for your
             * customer.
             */
            public PaymentRequest withScheme(String scheme) {
                this.scheme = scheme;
                return this;
            }

            public enum FundsSettlement {
                @SerializedName("managed")
                MANAGED, @SerializedName("direct")
                DIRECT, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }

        public static class SubscriptionRequest {
            private Integer amount;
            private Integer appFee;
            private Integer count;
            private String currency;
            private Integer dayOfMonth;
            private Integer interval;
            private IntervalUnit intervalUnit;
            private Map<String, String> metadata;
            private Month month;
            private String name;
            private String paymentReference;
            private Boolean retryIfPossible;
            private String startDate;

            /**
             * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             */
            public SubscriptionRequest withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * The amount to be deducted from each payment as an app fee, to be paid to the partner
             * integration which created the subscription, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             */
            public SubscriptionRequest withAppFee(Integer appFee) {
                this.appFee = appFee;
                return this;
            }

            /**
             * The total number of payments that should be taken by this subscription.
             */
            public SubscriptionRequest withCount(Integer count) {
                this.count = count;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
             */
            public SubscriptionRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to
             * indicate the last day of the month.
             */
            public SubscriptionRequest withDayOfMonth(Integer dayOfMonth) {
                this.dayOfMonth = dayOfMonth;
                return this;
            }

            /**
             * Number of `interval_units` between customer charge dates. Must be greater than or
             * equal to `1`. Must result in at least one charge date per year. Defaults to `1`.
             */
            public SubscriptionRequest withInterval(Integer interval) {
                this.interval = interval;
                return this;
            }

            /**
             * The unit of time between customer charge dates. One of `weekly`, `monthly` or
             * `yearly`.
             */
            public SubscriptionRequest withIntervalUnit(IntervalUnit intervalUnit) {
                this.intervalUnit = intervalUnit;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public SubscriptionRequest withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * Name of the month on which to charge a customer. Must be lowercase. Only applies when
             * the interval_unit is `yearly`.
             * 
             */
            public SubscriptionRequest withMonth(Month month) {
                this.month = month;
                return this;
            }

            /**
             * Optional name for the subscription. This will be set as the description on each
             * payment created. Must not exceed 255 characters.
             */
            public SubscriptionRequest withName(String name) {
                this.name = name;
                return this;
            }

            /**
             * An optional payment reference. This will be set as the reference on each payment
             * created and will appear on your customer's bank statement. See the documentation for
             * the [create payment endpoint](#payments-create-a-payment) for more details. <br />
             */
            public SubscriptionRequest withPaymentReference(String paymentReference) {
                this.paymentReference = paymentReference;
                return this;
            }

            /**
             * On failure, automatically retry payments using [intelligent
             * retries](#success-intelligent-retries). Default is `false`.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
             * </p>
             */
            public SubscriptionRequest withRetryIfPossible(Boolean retryIfPossible) {
                this.retryIfPossible = retryIfPossible;
                return this;
            }

            /**
             * The date on which the first payment should be charged. If fulfilled after this date,
             * this will be set as the mandate's `next_possible_charge_date`. When left blank and
             * `month` or `day_of_month` are provided, this will be set to the date of the first
             * payment. If created without `month` or `day_of_month` this will be set as the
             * mandate's `next_possible_charge_date`.
             * 
             */
            public SubscriptionRequest withStartDate(String startDate) {
                this.startDate = startDate;
                return this;
            }

            public enum IntervalUnit {
                @SerializedName("weekly")
                WEEKLY, @SerializedName("monthly")
                MONTHLY, @SerializedName("yearly")
                YEARLY, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }

            public enum Month {
                @SerializedName("january")
                JANUARY, @SerializedName("february")
                FEBRUARY, @SerializedName("march")
                MARCH, @SerializedName("april")
                APRIL, @SerializedName("may")
                MAY, @SerializedName("june")
                JUNE, @SerializedName("july")
                JULY, @SerializedName("august")
                AUGUST, @SerializedName("september")
                SEPTEMBER, @SerializedName("october")
                OCTOBER, @SerializedName("november")
                NOVEMBER, @SerializedName("december")
                DECEMBER, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }
    }
}
