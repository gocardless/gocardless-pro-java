package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequest;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with billing request resources.
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
public class BillingRequestService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#billingRequests() }.
     */
    public BillingRequestService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * <p class="notice">
     * <strong>Important</strong>: All properties associated with `subscription_request` and
     * `instalment_schedule_request` are only supported for ACH and PAD schemes.
     * </p>
     */
    public BillingRequestCreateRequest create() {
        return new BillingRequestCreateRequest(httpClient);
    }

    /**
     * If the billing request has a pending <code>collect_customer_details</code> action, this
     * endpoint can be used to collect the details in order to complete it.
     * 
     * The endpoint takes the same payload as Customers, but checks that the customer fields are
     * populated correctly for the billing request scheme.
     * 
     * Whatever is provided to this endpoint is used to update the referenced customer, and will
     * take effect immediately after the request is successful.
     */
    public BillingRequestCollectCustomerDetailsRequest collectCustomerDetails(String identity) {
        return new BillingRequestCollectCustomerDetailsRequest(httpClient, identity);
    }

    /**
     * If the billing request has a pending <code>collect_bank_account</code> action, this endpoint
     * can be used to collect the details in order to complete it.
     * 
     * The endpoint takes the same payload as Customer Bank Accounts, but check the bank account is
     * valid for the billing request scheme before creating and attaching it.
     * 
     * If the scheme is PayTo and the pay_id is available, this can be included in the payload along
     * with the country_code.
     * 
     * _ACH scheme_ For compliance reasons, an extra validation step is done using a third-party
     * provider to make sure the customer's bank account can accept Direct Debit. If a bank account
     * is discovered to be closed or invalid, the customer is requested to adjust the account
     * number/routing number and succeed in this check to continue with the flow.
     * 
     * _BACS scheme_ [Payer Name
     * Verification](https://hub.gocardless.com/s/article/Introduction-to-Payer-Name-Verification?language=en_GB)
     * is enabled by default for UK based bank accounts, meaning we verify the account holder name
     * and bank account number match the details held by the relevant bank.
     */
    public BillingRequestCollectBankAccountRequest collectBankAccount(String identity) {
        return new BillingRequestCollectBankAccountRequest(httpClient, identity);
    }

    /**
     * This is needed when you have a mandate request. As a scheme compliance rule we are required
     * to allow the payer to crosscheck the details entered by them and confirm it.
     */
    public BillingRequestConfirmPayerDetailsRequest confirmPayerDetails(String identity) {
        return new BillingRequestConfirmPayerDetailsRequest(httpClient, identity);
    }

    /**
     * If a billing request is ready to be fulfilled, call this endpoint to cause it to fulfil,
     * executing the payment.
     */
    public BillingRequestFulfilRequest fulfil(String identity) {
        return new BillingRequestFulfilRequest(httpClient, identity);
    }

    /**
     * Immediately cancels a billing request, causing all billing request flows to expire.
     */
    public BillingRequestCancelRequest cancel(String identity) {
        return new BillingRequestCancelRequest(httpClient, identity);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your billing requests.
     */
    public BillingRequestListRequest<ListResponse<BillingRequest>> list() {
        return new BillingRequestListRequest<>(httpClient,
                ListRequest.<BillingRequest>pagingExecutor());
    }

    public BillingRequestListRequest<Iterable<BillingRequest>> all() {
        return new BillingRequestListRequest<>(httpClient,
                ListRequest.<BillingRequest>iteratingExecutor());
    }

    /**
     * Fetches a billing request
     */
    public BillingRequestGetRequest get(String identity) {
        return new BillingRequestGetRequest(httpClient, identity);
    }

    /**
     * Notifies the customer linked to the billing request, asking them to authorise it. Currently,
     * the customer can only be notified by email.
     * 
     * This endpoint is currently supported only for Instant Bank Pay Billing Requests.
     */
    public BillingRequestNotifyRequest notify(String identity) {
        return new BillingRequestNotifyRequest(httpClient, identity);
    }

    /**
     * Triggers a fallback from the open-banking flow to direct debit. Note, the billing request
     * must have fallback enabled.
     */
    public BillingRequestFallbackRequest fallback(String identity) {
        return new BillingRequestFallbackRequest(httpClient, identity);
    }

    /**
     * This will allow for the updating of the currency and subsequently the scheme if needed for a
     * Billing Request. This will only be available for mandate only flows which do not have the
     * lock_currency flag set to true on the Billing Request Flow. It will also not support any
     * request which has a payments request.
     */
    public BillingRequestChooseCurrencyRequest chooseCurrency(String identity) {
        return new BillingRequestChooseCurrencyRequest(httpClient, identity);
    }

    /**
     * Creates an Institution object and attaches it to the Billing Request
     */
    public BillingRequestSelectInstitutionRequest selectInstitution(String identity) {
        return new BillingRequestSelectInstitutionRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BillingRequestService#create }.
     *
     * <p class="notice">
     * <strong>Important</strong>: All properties associated with `subscription_request` and
     * `instalment_schedule_request` are only supported for ACH and PAD schemes.
     * </p>
     */
    public static final class BillingRequestCreateRequest
            extends IdempotentPostRequest<BillingRequest> {
        private Boolean fallbackEnabled;
        private InstalmentScheduleRequest instalmentScheduleRequest;
        private Links links;
        private MandateRequest mandateRequest;
        private Map<String, String> metadata;
        private PaymentRequest paymentRequest;
        private PurposeCode purposeCode;
        private SubscriptionRequest subscriptionRequest;

        /**
         * (Optional) If true, this billing request can fallback from instant payment to direct
         * debit. Should not be set if GoCardless payment intelligence feature is used.
         * 
         * See [Billing Requests: Retain customers with
         * Fallbacks](https://developer.gocardless.com/billing-requests/retain-customers-with-fallbacks/)
         * for more information.
         */
        public BillingRequestCreateRequest withFallbackEnabled(Boolean fallbackEnabled) {
            this.fallbackEnabled = fallbackEnabled;
            return this;
        }

        public BillingRequestCreateRequest withInstalmentScheduleRequest(
                InstalmentScheduleRequest instalmentScheduleRequest) {
            this.instalmentScheduleRequest = instalmentScheduleRequest;
            return this;
        }

        /**
         * The amount to be deducted from each payment as an app fee, to be paid to the partner
         * integration which created the subscription, in the lowest denomination for the currency
         * (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestAppFee(Integer appFee) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withAppFee(appFee);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "USD" and "CAD" are supported.
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestCurrency(String currency) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withCurrency(currency);
            return this;
        }

        /**
         * An explicit array of instalment payments, each specifying at least an `amount` and
         * `charge_date`. See [create (with dates)](#instalment-schedules-create-with-dates)
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestInstalmentsWithDates(
                List<InstalmentsWithDates> instalmentsWithDates) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withInstalmentsWithDates(instalmentsWithDates);
            return this;
        }

        /**
         * Frequency of the payments you want to create, together with an array of payment amounts
         * to be collected, with a specified start date for the first payment. See [create (with
         * schedule)](#instalment-schedules-create-with-schedule)
         * 
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestInstalmentsWithSchedule(
                InstalmentsWithSchedule instalmentsWithSchedule) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withInstalmentsWithSchedule(instalmentsWithSchedule);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestMetadata(
                Map<String, String> metadata) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withMetadata(metadata);
            return this;
        }

        /**
         * Name of the instalment schedule, up to 100 chars. This name will also be copied to the
         * payments of the instalment schedule if you use schedule-based creation.
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestName(String name) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withName(name);
            return this;
        }

        /**
         * An optional payment reference. This will be set as the reference on each payment created
         * and will appear on your customer's bank statement. See the documentation for the [create
         * payment endpoint](#payments-create-a-payment) for more details. <br />
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestPaymentReference(
                String paymentReference) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withPaymentReference(paymentReference);
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
        public BillingRequestCreateRequest withInstalmentScheduleRequestRetryIfPossible(
                Boolean retryIfPossible) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withRetryIfPossible(retryIfPossible);
            return this;
        }

        /**
         * The total amount of the instalment schedule, defined as the sum of all individual
         * payments, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         * If the requested payment amounts do not sum up correctly, a validation error will be
         * returned.
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestTotalAmount(
                Integer totalAmount) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withTotalAmount(totalAmount);
            return this;
        }

        public BillingRequestCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors). Only required if your account
         * manages multiple creditors.
         */
        public BillingRequestCreateRequest withLinksCreditor(String creditor) {
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
        public BillingRequestCreateRequest withLinksCustomer(String customer) {
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
        public BillingRequestCreateRequest withLinksCustomerBankAccount(
                String customerBankAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomerBankAccount(customerBankAccount);
            return this;
        }

        public BillingRequestCreateRequest withMandateRequest(MandateRequest mandateRequest) {
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
        public BillingRequestCreateRequest withMandateRequestAuthorisationSource(
                MandateRequest.AuthorisationSource authorisationSource) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withAuthorisationSource(authorisationSource);
            return this;
        }

        /**
         * This attribute represents the authorisation type between the payer and merchant. It can
         * be set to `one_off`, `recurring` or `standing` for ACH scheme. And `single`, `recurring`
         * and `sporadic` for PAD scheme. _Note:_ This is only supported for ACH and PAD schemes.
         * 
         */
        public BillingRequestCreateRequest withMandateRequestConsentType(String consentType) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withConsentType(consentType);
            return this;
        }

        /**
         * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo and
         * VRP.
         */
        public BillingRequestCreateRequest withMandateRequestConstraints(Constraints constraints) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withConstraints(constraints);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public BillingRequestCreateRequest withMandateRequestCurrency(String currency) {
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
        public BillingRequestCreateRequest withMandateRequestDescription(String description) {
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
        public BillingRequestCreateRequest withMandateRequestMetadata(
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
        public BillingRequestCreateRequest withMandateRequestReference(String reference) {
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
        public BillingRequestCreateRequest withMandateRequestScheme(String scheme) {
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
        public BillingRequestCreateRequest withMandateRequestSweeping(Boolean sweeping) {
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
        public BillingRequestCreateRequest withMandateRequestVerify(MandateRequest.Verify verify) {
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
        public BillingRequestCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        public BillingRequestCreateRequest withPaymentRequest(PaymentRequest paymentRequest) {
            this.paymentRequest = paymentRequest;
            return this;
        }

        /**
         * Amount in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestCreateRequest withPaymentRequestAmount(Integer amount) {
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
        public BillingRequestCreateRequest withPaymentRequestAppFee(Integer appFee) {
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
        public BillingRequestCreateRequest withPaymentRequestCurrency(String currency) {
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
        public BillingRequestCreateRequest withPaymentRequestDescription(String description) {
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
        public BillingRequestCreateRequest withPaymentRequestFundsSettlement(
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
        public BillingRequestCreateRequest withPaymentRequestMetadata(
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
        public BillingRequestCreateRequest withPaymentRequestReference(String reference) {
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
        public BillingRequestCreateRequest withPaymentRequestRetryIfPossible(
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
        public BillingRequestCreateRequest withPaymentRequestScheme(String scheme) {
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
        public BillingRequestCreateRequest withPurposeCode(PurposeCode purposeCode) {
            this.purposeCode = purposeCode;
            return this;
        }

        public BillingRequestCreateRequest withSubscriptionRequest(
                SubscriptionRequest subscriptionRequest) {
            this.subscriptionRequest = subscriptionRequest;
            return this;
        }

        /**
         * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestCreateRequest withSubscriptionRequestAmount(Integer amount) {
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
        public BillingRequestCreateRequest withSubscriptionRequestAppFee(Integer appFee) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withAppFee(appFee);
            return this;
        }

        /**
         * The total number of payments that should be taken by this subscription.
         */
        public BillingRequestCreateRequest withSubscriptionRequestCount(Integer count) {
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
        public BillingRequestCreateRequest withSubscriptionRequestCurrency(String currency) {
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
        public BillingRequestCreateRequest withSubscriptionRequestDayOfMonth(Integer dayOfMonth) {
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
        public BillingRequestCreateRequest withSubscriptionRequestInterval(Integer interval) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withInterval(interval);
            return this;
        }

        /**
         * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
         */
        public BillingRequestCreateRequest withSubscriptionRequestIntervalUnit(
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
        public BillingRequestCreateRequest withSubscriptionRequestMetadata(
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
        public BillingRequestCreateRequest withSubscriptionRequestMonth(
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
        public BillingRequestCreateRequest withSubscriptionRequestName(String name) {
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
        public BillingRequestCreateRequest withSubscriptionRequestPaymentReference(
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
        public BillingRequestCreateRequest withSubscriptionRequestRetryIfPossible(
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
        public BillingRequestCreateRequest withSubscriptionRequestStartDate(String startDate) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withStartDate(startDate);
            return this;
        }

        public BillingRequestCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<BillingRequest> handleConflict(HttpClient httpClient, String id) {
            BillingRequestGetRequest request = new BillingRequestGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private BillingRequestCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BillingRequestCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
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

        public static class InstalmentsWithDates {
            private Integer amount;
            private String chargeDate;
            private String description;

            /**
             * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in
             * EUR).
             */
            public InstalmentsWithDates withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * A future date on which the payment should be collected. If the date is before the
             * next_possible_charge_date on the [mandate](#core-endpoints-mandates), it will be
             * automatically rolled forwards to that date.
             */
            public InstalmentsWithDates withChargeDate(String chargeDate) {
                this.chargeDate = chargeDate;
                return this;
            }

            /**
             * A human-readable description of the payment. This will be included in the
             * notification email GoCardless sends to your customer if your organisation does not
             * send its own notifications (see [compliance
             * requirements](#appendix-compliance-requirements)).
             */
            public InstalmentsWithDates withDescription(String description) {
                this.description = description;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (amount != null) {
                    params.put("instalments_with_dates[amount]", amount);
                }
                if (chargeDate != null) {
                    params.put("instalments_with_dates[charge_date]", chargeDate);
                }
                if (description != null) {
                    params.put("instalments_with_dates[description]", description);
                }
                return params.build();
            }
        }

        public static class InstalmentsWithSchedule {
            private List<Integer> amounts;
            private Integer interval;
            private IntervalUnit intervalUnit;
            private String startDate;

            /**
             * List of amounts of each instalment, in the lowest denomination for the currency (e.g.
             * cents in USD).
             * 
             */
            public InstalmentsWithSchedule withAmounts(List<Integer> amounts) {
                this.amounts = amounts;
                return this;
            }

            /**
             * Number of `interval_units` between charge dates. Must be greater than or equal to
             * `1`.
             * 
             */
            public InstalmentsWithSchedule withInterval(Integer interval) {
                this.interval = interval;
                return this;
            }

            /**
             * The unit of time between customer charge dates. One of `weekly`, `monthly` or
             * `yearly`.
             */
            public InstalmentsWithSchedule withIntervalUnit(IntervalUnit intervalUnit) {
                this.intervalUnit = intervalUnit;
                return this;
            }

            /**
             * The date on which the first payment should be charged. Must be on or after the
             * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When left blank
             * and `month` or `day_of_month` are provided, this will be set to the date of the first
             * payment. If created without `month` or `day_of_month` this will be set as the
             * mandate's `next_possible_charge_date`
             */
            public InstalmentsWithSchedule withStartDate(String startDate) {
                this.startDate = startDate;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (amounts != null) {
                    params.put("instalments_with_schedule[amounts]", amounts);
                }
                if (interval != null) {
                    params.put("instalments_with_schedule[interval]", interval);
                }
                if (intervalUnit != null) {
                    params.put("instalments_with_schedule[interval_unit]", intervalUnit);
                }
                if (startDate != null) {
                    params.put("instalments_with_schedule[start_date]", startDate);
                }
                return params.build();
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
        }

        public static class InstalmentScheduleRequest {
            private Integer appFee;
            private String currency;
            private List<InstalmentsWithDates> instalmentsWithDates;
            private InstalmentsWithSchedule instalmentsWithSchedule;
            private Map<String, String> metadata;
            private String name;
            private String paymentReference;
            private Boolean retryIfPossible;
            private Integer totalAmount;

            /**
             * The amount to be deducted from each payment as an app fee, to be paid to the partner
             * integration which created the subscription, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             */
            public InstalmentScheduleRequest withAppFee(Integer appFee) {
                this.appFee = appFee;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "USD" and "CAD" are supported.
             */
            public InstalmentScheduleRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * An explicit array of instalment payments, each specifying at least an `amount` and
             * `charge_date`. See [create (with dates)](#instalment-schedules-create-with-dates)
             */
            public InstalmentScheduleRequest withInstalmentsWithDates(
                    List<InstalmentsWithDates> instalmentsWithDates) {
                this.instalmentsWithDates = instalmentsWithDates;
                return this;
            }

            /**
             * Frequency of the payments you want to create, together with an array of payment
             * amounts to be collected, with a specified start date for the first payment. See
             * [create (with schedule)](#instalment-schedules-create-with-schedule)
             * 
             */
            public InstalmentScheduleRequest withInstalmentsWithSchedule(
                    InstalmentsWithSchedule instalmentsWithSchedule) {
                this.instalmentsWithSchedule = instalmentsWithSchedule;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public InstalmentScheduleRequest withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * Name of the instalment schedule, up to 100 chars. This name will also be copied to
             * the payments of the instalment schedule if you use schedule-based creation.
             */
            public InstalmentScheduleRequest withName(String name) {
                this.name = name;
                return this;
            }

            /**
             * An optional payment reference. This will be set as the reference on each payment
             * created and will appear on your customer's bank statement. See the documentation for
             * the [create payment endpoint](#payments-create-a-payment) for more details. <br />
             */
            public InstalmentScheduleRequest withPaymentReference(String paymentReference) {
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
            public InstalmentScheduleRequest withRetryIfPossible(Boolean retryIfPossible) {
                this.retryIfPossible = retryIfPossible;
                return this;
            }

            /**
             * The total amount of the instalment schedule, defined as the sum of all individual
             * payments, in the lowest denomination for the currency (e.g. pence in GBP, cents in
             * EUR). If the requested payment amounts do not sum up correctly, a validation error
             * will be returned.
             */
            public InstalmentScheduleRequest withTotalAmount(Integer totalAmount) {
                this.totalAmount = totalAmount;
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
            private String consentType;
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
             * This attribute represents the authorisation type between the payer and merchant. It
             * can be set to `one_off`, `recurring` or `standing` for ACH scheme. And `single`,
             * `recurring` and `sporadic` for PAD scheme. _Note:_ This is only supported for ACH and
             * PAD schemes.
             * 
             */
            public MandateRequest withConsentType(String consentType) {
                this.consentType = consentType;
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

    /**
     * Request class for {@link BillingRequestService#collectCustomerDetails }.
     *
     * If the billing request has a pending <code>collect_customer_details</code> action, this
     * endpoint can be used to collect the details in order to complete it.
     * 
     * The endpoint takes the same payload as Customers, but checks that the customer fields are
     * populated correctly for the billing request scheme.
     * 
     * Whatever is provided to this endpoint is used to update the referenced customer, and will
     * take effect immediately after the request is successful.
     */
    public static final class BillingRequestCollectCustomerDetailsRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private Customer customer;
        private CustomerBillingDetail customerBillingDetail;

        public BillingRequestCollectCustomerDetailsRequest withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided.
         * For Canadian customers, the use of a `company_name` value will mean that any mandate
         * created from this customer will be considered to be a "Business PAD" (otherwise, any
         * mandate will be considered to be a "Personal PAD").
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerCompanyName(
                String companyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCompanyName(companyName);
            return this;
        }

        /**
         * Customer's email address. Required in most cases, as this allows GoCardless to send
         * notifications to this customer.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerEmail(String email) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerFamilyName(
                String familyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerGivenName(String givenName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withGivenName(givenName);
            return this;
        }

        /**
         * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the
         * language for notification emails sent by GoCardless if your organisation does not send
         * its own (see [compliance requirements](#appendix-compliance-requirements)). Currently
         * only "en", "fr", "de", "pt", "es", "it", "nl", "da", "nb", "sl", "sv" are supported. If
         * this is not provided and a customer was linked during billing request creation, the
         * linked customer language will be used. Otherwise, the language is default to "en".
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerLanguage(String language) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withLanguage(language);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerMetadata(
                Map<String, String> metadata) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withMetadata(metadata);
            return this;
        }

        /**
         * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including
         * country code.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerPhoneNumber(
                String phoneNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withPhoneNumber(phoneNumber);
            return this;
        }

        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetail(
                CustomerBillingDetail customerBillingDetail) {
            this.customerBillingDetail = customerBillingDetail;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailAddressLine1(
                String addressLine1) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailAddressLine2(
                String addressLine2) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailAddressLine3(
                String addressLine3) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailCity(
                String city) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withCity(city);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailCountryCode(
                String countryCode) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be
         * supplied if the customer's bank account is denominated in Danish krone (DKK).
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailDanishIdentityNumber(
                String danishIdentityNumber) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withDanishIdentityNumber(danishIdentityNumber);
            return this;
        }

        /**
         * For ACH customers only. Required for ACH customers. A string containing the IP address of
         * the payer to whom the mandate belongs (i.e. as a result of their completion of a mandate
         * setup flow in their browser).
         * 
         * Not required for creating offline mandates where `authorisation_source` is set to
         * telephone or paper.
         * 
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailIpAddress(
                String ipAddress) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withIpAddress(ipAddress);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailPostalCode(
                String postalCode) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter
         * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g.
         * `CA` for California).
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailRegion(
                String region) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withRegion(region);
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
         * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerBillingDetailSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (customerBillingDetail == null) {
                customerBillingDetail = new CustomerBillingDetail();
            }
            customerBillingDetail.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        private BillingRequestCollectCustomerDetailsRequest(HttpClient httpClient,
                String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestCollectCustomerDetailsRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/collect_customer_details";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
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
        }
    }

    /**
     * Request class for {@link BillingRequestService#collectBankAccount }.
     *
     * If the billing request has a pending <code>collect_bank_account</code> action, this endpoint
     * can be used to collect the details in order to complete it.
     * 
     * The endpoint takes the same payload as Customer Bank Accounts, but check the bank account is
     * valid for the billing request scheme before creating and attaching it.
     * 
     * If the scheme is PayTo and the pay_id is available, this can be included in the payload along
     * with the country_code.
     * 
     * _ACH scheme_ For compliance reasons, an extra validation step is done using a third-party
     * provider to make sure the customer's bank account can accept Direct Debit. If a bank account
     * is discovered to be closed or invalid, the customer is requested to adjust the account
     * number/routing number and succeed in this check to continue with the flow.
     * 
     * _BACS scheme_ [Payer Name
     * Verification](https://hub.gocardless.com/s/article/Introduction-to-Payer-Name-Verification?language=en_GB)
     * is enabled by default for UK based bank accounts, meaning we verify the account holder name
     * and bank account number match the details held by the relevant bank.
     */
    public static final class BillingRequestCollectBankAccountRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
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
         * includes a [customer bank account token](#javascript-flow-customer-bank-account-tokens).
         */
        public BillingRequestCollectBankAccountRequest withAccountHolderName(
                String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public BillingRequestCollectBankAccountRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Account number suffix (only for bank accounts denominated in NZD) - see [local
         * details](#local-bank-details-new-zealand) for more information.
         */
        public BillingRequestCollectBankAccountRequest withAccountNumberSuffix(
                String accountNumberSuffix) {
            this.accountNumberSuffix = accountNumberSuffix;
            return this;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for
         * bank accounts in other currencies. See [local details](#local-bank-details-united-states)
         * for more information.
         */
        public BillingRequestCollectBankAccountRequest withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public BillingRequestCollectBankAccountRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public BillingRequestCollectBankAccountRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
         * Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public BillingRequestCollectBankAccountRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public BillingRequestCollectBankAccountRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
         * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
         */
        public BillingRequestCollectBankAccountRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCollectBankAccountRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCollectBankAccountRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * A unique record such as an email address, mobile number or company number, that can be
         * used to make and accept payments.
         */
        public BillingRequestCollectBankAccountRequest withPayId(String payId) {
            this.payId = payId;
            return this;
        }

        private BillingRequestCollectBankAccountRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestCollectBankAccountRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/collect_bank_account";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
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

    /**
     * Request class for {@link BillingRequestService#confirmPayerDetails }.
     *
     * This is needed when you have a mandate request. As a scheme compliance rule we are required
     * to allow the payer to crosscheck the details entered by them and confirm it.
     */
    public static final class BillingRequestConfirmPayerDetailsRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;
        private Boolean payerRequestedDualSignature;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestConfirmPayerDetailsRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestConfirmPayerDetailsRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * This attribute can be set to true if the payer has indicated that multiple signatures are
         * required for the mandate. As long as every other Billing Request actions have been
         * completed, the payer will receive an email notification containing instructions on how to
         * complete the additional signature. The dual signature flow can only be completed using
         * GoCardless branded pages.
         */
        public BillingRequestConfirmPayerDetailsRequest withPayerRequestedDualSignature(
                Boolean payerRequestedDualSignature) {
            this.payerRequestedDualSignature = payerRequestedDualSignature;
            return this;
        }

        private BillingRequestConfirmPayerDetailsRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestConfirmPayerDetailsRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/confirm_payer_details";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#fulfil }.
     *
     * If a billing request is ready to be fulfilled, call this endpoint to cause it to fulfil,
     * executing the payment.
     */
    public static final class BillingRequestFulfilRequest extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestFulfilRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestFulfilRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private BillingRequestFulfilRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestFulfilRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/fulfil";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#cancel }.
     *
     * Immediately cancels a billing request, causing all billing request flows to expire.
     */
    public static final class BillingRequestCancelRequest extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestCancelRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private BillingRequestCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestCancelRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your billing requests.
     */
    public static final class BillingRequestListRequest<S> extends ListRequest<S, BillingRequest> {
        private String createdAt;
        private String customer;
        private String status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public BillingRequestListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public BillingRequestListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
         */
        public BillingRequestListRequest<S> withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * ID of a [customer](#core-endpoints-customers). If specified, this endpoint will return
         * all requests for the given customer.
         */
        public BillingRequestListRequest<S> withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Number of records to return.
         */
        public BillingRequestListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * One of:
         * <ul>
         * <li>`pending`: the billing request is pending and can be used</li>
         * <li>`ready_to_fulfil`: the billing request is ready to fulfil</li>
         * <li>`fulfilling`: the billing request is currently undergoing fulfilment</li>
         * <li>`fulfilled`: the billing request has been fulfilled and a payment created</li>
         * <li>`cancelled`: the billing request has been cancelled and cannot be used</li>
         * </ul>
         */
        public BillingRequestListRequest<S> withStatus(String status) {
            this.status = status;
            return this;
        }

        private BillingRequestListRequest(HttpClient httpClient,
                ListRequestExecutor<S, BillingRequest> executor) {
            super(httpClient, executor);
        }

        public BillingRequestListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (createdAt != null) {
                params.put("created_at", createdAt);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected TypeToken<List<BillingRequest>> getTypeToken() {
            return new TypeToken<List<BillingRequest>>() {};
        }
    }

    /**
     * Request class for {@link BillingRequestService#get }.
     *
     * Fetches a billing request
     */
    public static final class BillingRequestGetRequest extends GetRequest<BillingRequest> {
        @PathParam
        private final String identity;

        private BillingRequestGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }
    }

    /**
     * Request class for {@link BillingRequestService#notify }.
     *
     * Notifies the customer linked to the billing request, asking them to authorise it. Currently,
     * the customer can only be notified by email.
     * 
     * This endpoint is currently supported only for Instant Bank Pay Billing Requests.
     */
    public static final class BillingRequestNotifyRequest extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private String notificationType;
        private String redirectUri;

        /**
         * Currently, can only be `email`.
         */
        public BillingRequestNotifyRequest withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        /**
         * URL that the payer can be redirected to after authorising the payment.
         */
        public BillingRequestNotifyRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        private BillingRequestNotifyRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestNotifyRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/notify";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#fallback }.
     *
     * Triggers a fallback from the open-banking flow to direct debit. Note, the billing request
     * must have fallback enabled.
     */
    public static final class BillingRequestFallbackRequest extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;

        private BillingRequestFallbackRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestFallbackRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/fallback";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#chooseCurrency }.
     *
     * This will allow for the updating of the currency and subsequently the scheme if needed for a
     * Billing Request. This will only be available for mandate only flows which do not have the
     * lock_currency flag set to true on the Billing Request Flow. It will also not support any
     * request which has a payments request.
     */
    public static final class BillingRequestChooseCurrencyRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private String currency;
        private Map<String, String> metadata;

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public BillingRequestChooseCurrencyRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestChooseCurrencyRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestChooseCurrencyRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private BillingRequestChooseCurrencyRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestChooseCurrencyRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/choose_currency";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link BillingRequestService#selectInstitution }.
     *
     * Creates an Institution object and attaches it to the Billing Request
     */
    public static final class BillingRequestSelectInstitutionRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private String countryCode;
        private String institution;

        /**
         * [ISO
         * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. The country code of the institution. If nothing is provided, institutions
         * with the country code 'GB' are returned by default.
         */
        public BillingRequestSelectInstitutionRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * The unique identifier for this institution
         */
        public BillingRequestSelectInstitutionRequest withInstitution(String institution) {
            this.institution = institution;
            return this;
        }

        private BillingRequestSelectInstitutionRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestSelectInstitutionRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/actions/select_institution";
        }

        @Override
        protected String getEnvelope() {
            return "billing_requests";
        }

        @Override
        protected Class<BillingRequest> getResponseClass() {
            return BillingRequest.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
