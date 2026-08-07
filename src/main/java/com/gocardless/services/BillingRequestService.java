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
 * See <a href="https://developer.gocardless.com/getting-started/billing-requests/overview/">Billing
 * Requests: Overview</a> for how-to's, explanations and tutorials.
 * <p class="notice">
 * <strong>Important</strong>: All properties associated with <code>subscription_request</code> and
 * <code>instalment_schedule_request</code> are only supported for ACH and PAD schemes.
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
     * <strong>Important</strong>: All properties associated with <code>subscription_request</code>
     * and <code>instalment_schedule_request</code> are only supported for ACH and PAD schemes.
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
     * <em>ACH scheme</em> For compliance reasons, an extra validation step is done using a
     * third-party provider to make sure the customer's bank account can accept Direct Debit. If a
     * bank account is discovered to be closed or invalid, the customer is requested to adjust the
     * account number/routing number and succeed in this check to continue with the flow.
     * 
     * <em>BACS and SEPA schemes</em> <a href=
     * "https://hub.gocardless.com/s/article/Introduction-to-Payer-Name-Verification?language=en_GB">Payer
     * Name Verification</a> is enabled by default for UK and Eurozone based bank accounts, meaning
     * we verify the account holder name and bank account number/IBAN match the details held by the
     * relevant bank. If there is no match, the endpoint will return a 422 - validation error on
     * account_holder_name: "Account holder name does not match bank account details provided".
     * Testing instructions are <a href=
     * "https://developer.gocardless.com/developer-tools/scenario-simulators/#payer_name_verification">here</a>
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
     * Returns a <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-cursor-pagination">cursor-paginated</a>
     * list of your billing requests.
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
     * This endpoint is currently supported only for Pay by Bank Billing Requests.
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
     * <strong>Important</strong>: All properties associated with <code>subscription_request</code>
     * and <code>instalment_schedule_request</code> are only supported for ACH and PAD schemes.
     * </p>
     */
    public static final class BillingRequestCreateRequest
            extends IdempotentPostRequest<BillingRequest> {
        private Boolean fallbackEnabled;
        private InstalmentScheduleRequest instalmentScheduleRequest;
        private Links links;
        private MandateRequest mandateRequest;
        private Map<String, String> metadata;
        private PaymentContextCode paymentContextCode;
        private String paymentPurposeCode;
        private PaymentRequest paymentRequest;
        private PurposeCode purposeCode;
        private SubscriptionRequest subscriptionRequest;

        /**
         * (Optional) If true, this billing request can fallback from instant payment to direct
         * debit. Should not be set if GoCardless payment intelligence feature is used.
         * 
         * See <a href=
         * "https://developer.gocardless.com/billing-requests/retain-customers-with-fallbacks/">Billing
         * Requests: Retain customers with Fallbacks</a> for more information.
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
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "USD" and "CAD" are supported.
         */
        public BillingRequestCreateRequest withInstalmentScheduleRequestCurrency(String currency) {
            if (instalmentScheduleRequest == null) {
                instalmentScheduleRequest = new InstalmentScheduleRequest();
            }
            instalmentScheduleRequest.withCurrency(currency);
            return this;
        }

        /**
         * An explicit array of instalment payments, each specifying at least an <code>amount</code>
         * and <code>charge_date</code>. See <a href=
         * "https://developer.gocardless.com/api-reference/#instalment-schedules-create-with-dates">create
         * (with dates)</a>
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
         * to be collected, with a specified start date for the first payment. See <a href=
         * "https://developer.gocardless.com/api-reference/#instalment-schedules-create-with-schedule">create
         * (with schedule)</a>
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
         * and will appear on your customer's bank statement. See the documentation for the <a href=
         * "https://developer.gocardless.com/api-reference/#payments-create-a-payment">create
         * payment endpoint</a> for more details. <br>
         * </br>
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
         * On failure, automatically retry payments using
         * <a href="https://developer.gocardless.com/success-plus/overview">intelligent retries</a>.
         * Default is <code>false</code>.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless dashboard</a>.
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
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>.
         * Only required if your account manages multiple creditors.
         */
        public BillingRequestCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>
         * against which this request should be made.
         */
        public BillingRequestCreateRequest withLinksCustomer(String customer) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomer(customer);
            return this;
        }

        /**
         * (Optional) ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer_bank_account</a>
         * against which this request should be made.
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
         * This field is ACH specific, sometimes referred to as
         * <a href="https://www.moderntreasury.com/learn/sec-codes">SEC code</a>.
         * 
         * This is the way that the payer gives authorisation to the merchant. web: Authorisation is
         * Internet Initiated or via Mobile Entry (maps to SEC code: WEB) telephone: Authorisation
         * is provided orally over telephone (maps to SEC code: TEL) paper: Authorisation is
         * provided in writing and signed, or similarly authenticated (maps to SEC code: PPD)
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
         * be set to <code>one_off</code>, <code>recurring</code> or <code>standing</code> for ACH
         * scheme. And <code>single</code>, <code>recurring</code> and <code>sporadic</code> for PAD
         * scheme. <em>Note:</em> This is only supported for ACH and PAD schemes.
         */
        public BillingRequestCreateRequest withMandateRequestConsentType(String consentType) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withConsentType(consentType);
            return this;
        }

        /**
         * Constraints that will apply to the mandate_request. (Optional) Specifically required for
         * PayTo and VRP.
         */
        public BillingRequestCreateRequest withMandateRequestConstraints(Constraints constraints) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withConstraints(constraints);
            return this;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
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
         */
        public BillingRequestCreateRequest withMandateRequestDescription(String description) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withDescription(description);
            return this;
        }

        /**
         * This field will decide how GoCardless handles settlement of funds from the customer.
         * 
         * <ul>
         * <li><code>managed</code> will be moved through GoCardless' account, batched, and payed
         * out.</li>
         * <li><code>direct</code> will be a direct transfer from the payer's account to the
         * merchant where invoicing will be handled separately.</li>
         * </ul>
         */
        public BillingRequestCreateRequest withMandateRequestFundsSettlement(
                MandateRequest.FundsSettlement fundsSettlement) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withFundsSettlement(fundsSettlement);
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
         * Unique reference. Different schemes have different length and <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-character-sets">character
         * set</a> requirements. GoCardless will generate a unique reference satisfying the
         * different scheme requirements if this field is left blank.
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
         * 
         * <ul>
         * <li><code>minimum</code>: only verify if absolutely required, such as when part of scheme
         * rules</li>
         * <li><code>recommended</code>: in addition to <code>minimum</code>, use the GoCardless
         * payment intelligence solution to decide if a payer should be verified</li>
         * <li><code>when_available</code>: if verification mechanisms are available, use them</li>
         * <li><code>always</code>: as <code>when_available</code>, but fail to create the Billing
         * Request if a mechanism isn't available</li>
         * </ul>
         * By default, all Billing Requests use the <code>recommended</code> verification
         * preference. It uses GoCardless payment intelligence solution to determine if a payer is
         * fraudulent or not. The verification mechanism is based on the response and the payer may
         * be asked to verify themselves. If the feature is not available, <code>recommended</code>
         * behaves like <code>minimum</code>.
         * 
         * If you never wish to take advantage of our reduced risk products and Verified Mandates as
         * they are released in new schemes, please use the <code>minimum</code> verification
         * preference.
         * 
         * See <a href=
         * "https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/">Billing
         * Requests: Creating Verified Mandates</a> for more information.
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

        /**
         * Specifies the context or scenario in which the payment is being made. Defines whether the
         * payment is for advance/arrears billing, point of sale transactions, ecommerce, or account
         * transfers. This helps banks and payment processors understand the payment scenario and
         * apply appropriate processing rules and risk controls.
         */
        public BillingRequestCreateRequest withPaymentContextCode(
                PaymentContextCode paymentContextCode) {
            this.paymentContextCode = paymentContextCode;
            return this;
        }

        /**
         * Specifies the underlying purpose of the payment. Defines the specific reason or type of
         * service/goods the payment relates to, improving straight-through processing and
         * compliance. See
         * <a href="https://developer.gocardless.com/vrp-commercial-payment-purpose-codes/">VRP
         * Commercial Payment Purpose Codes</a> for the complete list of valid codes.
         */
        public BillingRequestCreateRequest withPaymentPurposeCode(String paymentPurposeCode) {
            this.paymentPurposeCode = paymentPurposeCode;
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
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * <code>GBP</code> and <code>EUR</code> supported; <code>GBP</code> with your customers in
         * the UK and for <code>EUR</code> with your customers in supported Eurozone countries only.
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
         * <ul>
         * <li><code>managed</code> will be moved through GoCardless' account, batched, and payed
         * out.</li>
         * <li><code>direct</code> will be a direct transfer from the payer's account to the
         * merchant where invoicing will be handled separately.</li>
         * </ul>
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
         * A custom payment reference defined by the merchant. It is only available for payments on
         * the PayTo scheme or payments using the Direct Funds settlement model on the Faster
         * Payments scheme.
         */
        public BillingRequestCreateRequest withPaymentRequestReference(String reference) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withReference(reference);
            return this;
        }

        /**
         * On failure, automatically retry payments using
         * <a href="https://developer.gocardless.com/success-plus/overview">intelligent retries</a>.
         * Default is <code>false</code>.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless dashboard</a>.
         * </p>
         * <p class="notice">
         * <strong>Important</strong>: This is not applicable to Pay by Bank and VRP payments.
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
         * (Optional) A scheme used for Open Banking payments. Currently
         * <code>faster_payments</code> is supported in the UK (GBP) and
         * <code>sepa_credit_transfer</code> and <code>sepa_instant_credit_transfer</code> are
         * supported in supported Eurozone countries (EUR). For Eurozone countries,
         * <code>sepa_credit_transfer</code> is used as the default. Please be aware that
         * <code>sepa_instant_credit_transfer</code> may incur an additional fee for your customer.
         */
        public BillingRequestCreateRequest withPaymentRequestScheme(String scheme) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withScheme(scheme);
            return this;
        }

        /**
         * Specifies the high-level purpose/category of a mandate and/or payment using a set of
         * pre-defined categories. Provides context on the nature and reason for the payment to
         * facilitate processing and compliance. See
         * <a href="https://developer.gocardless.com/billing-request-purpose-codes/">Billing Request
         * Purpose Codes</a> for the complete list of valid codes.
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
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public BillingRequestCreateRequest withSubscriptionRequestCurrency(String currency) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withCurrency(currency);
            return this;
        }

        /**
         * As per RFC 2445. The day of the month to charge customers on. <code>1</code>
         * <ul>
         * <li></li>
         * </ul>
         * <code>28</code> or <code>-1</code> to indicate the last day of the month.
         */
        public BillingRequestCreateRequest withSubscriptionRequestDayOfMonth(Integer dayOfMonth) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withDayOfMonth(dayOfMonth);
            return this;
        }

        /**
         * Number of <code>interval_units</code> between customer charge dates. Must be greater than
         * or equal to <code>1</code>. Must result in at least one charge date per year. Defaults to
         * <code>1</code>.
         */
        public BillingRequestCreateRequest withSubscriptionRequestInterval(Integer interval) {
            if (subscriptionRequest == null) {
                subscriptionRequest = new SubscriptionRequest();
            }
            subscriptionRequest.withInterval(interval);
            return this;
        }

        /**
         * The unit of time between customer charge dates. One of <code>weekly</code>,
         * <code>monthly</code> or <code>yearly</code>.
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
         * interval_unit is <code>yearly</code>.
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
         * and will appear on your customer's bank statement. See the documentation for the <a href=
         * "https://developer.gocardless.com/api-reference/#payments-create-a-payment">create
         * payment endpoint</a> for more details. <br>
         * </br>
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
         * On failure, automatically retry payments using
         * <a href="https://developer.gocardless.com/success-plus/overview">intelligent retries</a>.
         * Default is <code>false</code>.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless dashboard</a>.
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
         * will be set as the mandate's <code>next_possible_charge_date</code>. When left blank and
         * <code>month</code> or <code>day_of_month</code> are provided, this will be set to the
         * date of the first payment. If created without <code>month</code> or
         * <code>day_of_month</code> this will be set as the mandate's
         * <code>next_possible_charge_date</code>.
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

        public enum PaymentContextCode {
            @SerializedName("billing_goods_and_services_in_advance")
            BILLING_GOODS_AND_SERVICES_IN_ADVANCE, @SerializedName("billing_goods_and_services_in_arrears")
            BILLING_GOODS_AND_SERVICES_IN_ARREARS, @SerializedName("face_to_face_point_of_sale")
            FACE_TO_FACE_POINT_OF_SALE, @SerializedName("ecommerce_merchant_initiated_payment")
            ECOMMERCE_MERCHANT_INITIATED_PAYMENT, @SerializedName("transfer_to_self")
            TRANSFER_TO_SELF, @SerializedName("transfer_to_third_party")
            TRANSFER_TO_THIRD_PARTY, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
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
            OTHER, @SerializedName("bonus_payment")
            BONUS_PAYMENT, @SerializedName("cash_management_transfer")
            CASH_MANAGEMENT_TRANSFER, @SerializedName("card_bulk_clearing")
            CARD_BULK_CLEARING, @SerializedName("credit_card_payment")
            CREDIT_CARD_PAYMENT, @SerializedName("trade_settlement_payment")
            TRADE_SETTLEMENT_PAYMENT, @SerializedName("debit_card_payment")
            DEBIT_CARD_PAYMENT, @SerializedName("dividend")
            DIVIDEND, @SerializedName("deliver_against_payment")
            DELIVER_AGAINST_PAYMENT, @SerializedName("epayment")
            EPAYMENT, @SerializedName("fee_collection_and_interest")
            FEE_COLLECTION_AND_INTEREST, @SerializedName("fee_collection")
            FEE_COLLECTION, @SerializedName("person_to_person_payment")
            PERSON_TO_PERSON_PAYMENT, @SerializedName("government_payment")
            GOVERNMENT_PAYMENT, @SerializedName("hedging_transaction")
            HEDGING_TRANSACTION, @SerializedName("irrevocable_credit_card_payment")
            IRREVOCABLE_CREDIT_CARD_PAYMENT, @SerializedName("irrevocable_debit_card_payment")
            IRREVOCABLE_DEBIT_CARD_PAYMENT, @SerializedName("intra_company_payment")
            INTRA_COMPANY_PAYMENT, @SerializedName("interest")
            INTEREST, @SerializedName("lockbox_transactions")
            LOCKBOX_TRANSACTIONS, @SerializedName("commercial")
            COMMERCIAL, @SerializedName("consumer")
            CONSUMER, @SerializedName("other_payment")
            OTHER_PAYMENT, @SerializedName("pension_payment")
            PENSION_PAYMENT, @SerializedName("represented")
            REPRESENTED, @SerializedName("reimbursement_received_credit_transfer")
            REIMBURSEMENT_RECEIVED_CREDIT_TRANSFER, @SerializedName("receive_against_payment")
            RECEIVE_AGAINST_PAYMENT, @SerializedName("salary_payment")
            SALARY_PAYMENT, @SerializedName("securities")
            SECURITIES, @SerializedName("social_security_benefit")
            SOCIAL_SECURITY_BENEFIT, @SerializedName("supplier_payment")
            SUPPLIER_PAYMENT, @SerializedName("tax_payment")
            TAX_PAYMENT, @SerializedName("trade")
            TRADE, @SerializedName("treasury_payment")
            TREASURY_PAYMENT, @SerializedName("value_added_tax_payment")
            VALUE_ADDED_TAX_PAYMENT, @SerializedName("with_holding")
            WITH_HOLDING, @SerializedName("cash_management_sweep_account")
            CASH_MANAGEMENT_SWEEP_ACCOUNT, @SerializedName("cash_management_top_account")
            CASH_MANAGEMENT_TOP_ACCOUNT, @SerializedName("cash_management_zero_balance_account")
            CASH_MANAGEMENT_ZERO_BALANCE_ACCOUNT, @SerializedName("crossborder_mi_payments")
            CROSSBORDER_MI_PAYMENTS, @SerializedName("foreign_currency_domestic_transfer")
            FOREIGN_CURRENCY_DOMESTIC_TRANSFER, @SerializedName("cash_in_pre_credit")
            CASH_IN_PRE_CREDIT, @SerializedName("cash_out_notes_coins")
            CASH_OUT_NOTES_COINS, @SerializedName("carrier_guarded_wholesale_valuables")
            CARRIER_GUARDED_WHOLESALE_VALUABLES, @SerializedName("unknown")
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
             * 
             * Minimum and maximum amounts vary by payment scheme. For more information, see
             * <a href=
             * "https://support.gocardless.com/hc/en-gb/articles/115000309245-Transaction-limits">Transaction
             * limits</a>
             * 
             * For Variable Recurring Payments (VRP), this must not exceed the mandate's
             * <code>max_amount_per_payment</code> constraint.
             */
            public InstalmentsWithDates withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * A future date on which the payment should be collected. If the date is before the
             * next_possible_charge_date on the <a href=
             * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>,
             * it will be automatically rolled forwards to that date.
             */
            public InstalmentsWithDates withChargeDate(String chargeDate) {
                this.chargeDate = chargeDate;
                return this;
            }

            /**
             * A human-readable description of the payment. This will be included in the
             * notification email GoCardless sends to your customer if your organisation does not
             * send its own notifications (see <a href=
             * "https://developer.gocardless.com/api-reference/#appendix-compliance-requirements">compliance
             * requirements</a>).
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
             */
            public InstalmentsWithSchedule withAmounts(List<Integer> amounts) {
                this.amounts = amounts;
                return this;
            }

            /**
             * Number of <code>interval_units</code> between charge dates. Must be greater than or
             * equal to <code>1</code>.
             */
            public InstalmentsWithSchedule withInterval(Integer interval) {
                this.interval = interval;
                return this;
            }

            /**
             * The unit of time between customer charge dates. One of <code>weekly</code>,
             * <code>monthly</code> or <code>yearly</code>.
             */
            public InstalmentsWithSchedule withIntervalUnit(IntervalUnit intervalUnit) {
                this.intervalUnit = intervalUnit;
                return this;
            }

            /**
             * The date on which the first payment should be charged. Must be on or after the
             * <a href=
             * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>'s
             * <code>next_possible_charge_date</code>. When left blank and <code>month</code> or
             * <code>day_of_month</code> are provided, this will be set to the date of the first
             * payment. If created without <code>month</code> or <code>day_of_month</code> this will
             * be set as the mandate's <code>next_possible_charge_date</code>
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
             * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency
             * code. Currently "USD" and "CAD" are supported.
             */
            public InstalmentScheduleRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * An explicit array of instalment payments, each specifying at least an
             * <code>amount</code> and <code>charge_date</code>. See <a href=
             * "https://developer.gocardless.com/api-reference/#instalment-schedules-create-with-dates">create
             * (with dates)</a>
             */
            public InstalmentScheduleRequest withInstalmentsWithDates(
                    List<InstalmentsWithDates> instalmentsWithDates) {
                this.instalmentsWithDates = instalmentsWithDates;
                return this;
            }

            /**
             * Frequency of the payments you want to create, together with an array of payment
             * amounts to be collected, with a specified start date for the first payment. See
             * <a href=
             * "https://developer.gocardless.com/api-reference/#instalment-schedules-create-with-schedule">create
             * (with schedule)</a>
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
             * the <a href=
             * "https://developer.gocardless.com/api-reference/#payments-create-a-payment">create
             * payment endpoint</a> for more details. <br>
             * </br>
             */
            public InstalmentScheduleRequest withPaymentReference(String paymentReference) {
                this.paymentReference = paymentReference;
                return this;
            }

            /**
             * On failure, automatically retry payments using
             * <a href="https://developer.gocardless.com/success-plus/overview">intelligent
             * retries</a>. Default is <code>false</code>.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless
             * dashboard</a>.
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
             * ID of the associated <a href=
             * "https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>.
             * Only required if your account manages multiple creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }

            /**
             * ID of the <a href=
             * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>
             * against which this request should be made.
             */
            public Links withCustomer(String customer) {
                this.customer = customer;
                return this;
            }

            /**
             * (Optional) ID of the <a href=
             * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer_bank_account</a>
             * against which this request should be made.
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
             * The alignment of the period. Defaults to <code>creation_date</code> if not specified.
             * 
             * <code>calendar</code>
             * <ul>
             * <li>the period follows fixed calendar boundaries, the same for every mandate:</li>
             * </ul>
             * <code>week</code> runs Monday to Sunday, <code>month</code> runs from the 1st to the
             * last day of the calendar month, and <code>year</code> runs from 1 January to 31
             * December. If the mandate starts partway through a period, the limit for that first
             * period is reduced proportionally to the days remaining (e.g. a monthly limit starting
             * on the 15th gives roughly half the limit for that first month).
             * 
             * <code>creation_date</code>
             * <ul>
             * <li>the period follows the mandate's own start date rather than the calendar. For
             * example, if the mandate starts on the 15th, each monthly period runs from the 15th to
             * the 14th of the following month. The first period is a full period, not reduced
             * proportionally.</li>
             * </ul>
             * 
             * <em>Note:</em> Has no effect when period is <code>flexible</code>.
             */
            public PeriodicLimits withAlignment(Alignment alignment) {
                this.alignment = alignment;
                return this;
            }

            /**
             * The maximum number of payments that can be collected in this periodic limit.
             * 
             * <em>Note:</em> Only supported for the PayTo scheme, where it is optional.
             */
            public PeriodicLimits withMaxPayments(Integer maxPayments) {
                this.maxPayments = maxPayments;
                return this;
            }

            /**
             * The maximum total amount that can be charged for all payments in this periodic limit,
             * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             * 
             * <em>Note:</em> Required for VRP. This is not permitted for the PayTo scheme.
             */
            public PeriodicLimits withMaxTotalAmount(Integer maxTotalAmount) {
                this.maxTotalAmount = maxTotalAmount;
                return this;
            }

            /**
             * The repeating period for this mandate. Required whenever a periodic limit is provided
             * (for both VRP and PayTo). If periodic_limits is omitted entirely for PayTo, this
             * defaults to flexible.
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
             */
            public Constraints withEndDate(String endDate) {
                this.endDate = endDate;
                return this;
            }

            /**
             * The maximum amount that can be charged for a single payment in the lowest
             * denomination for the currency (e.g. pence in GBP, cents in EUR). <em>Note:</em>
             * Required for PayTo and VRP.
             */
            public Constraints withMaxAmountPerPayment(Integer maxAmountPerPayment) {
                this.maxAmountPerPayment = maxAmountPerPayment;
                return this;
            }

            /**
             * A constraint where you can specify info (free text string) about how payments are
             * calculated. For use when payments vary and cannot be expressed as a fixed amount and
             * frequency. <em>Note:</em> This is only supported for ACH and PAD schemes.
             */
            public Constraints withPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
                return this;
            }

            /**
             * Caps on the total amount and/or number of payments that can be collected within a
             * repeating period (e.g. no more than a set amount per month), as opposed to
             * <code>max_amount_per_payment</code> which caps a single payment.
             * 
             * <em>Note:</em> Required for VRP, where exactly one periodic limit must be provided.
             * Optional for PayTo.
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
            private FundsSettlement fundsSettlement;
            private Map<String, String> metadata;
            private String reference;
            private String scheme;
            private Boolean sweeping;
            private Verify verify;

            /**
             * This field is ACH specific, sometimes referred to as
             * <a href="https://www.moderntreasury.com/learn/sec-codes">SEC code</a>.
             * 
             * This is the way that the payer gives authorisation to the merchant. web:
             * Authorisation is Internet Initiated or via Mobile Entry (maps to SEC code: WEB)
             * telephone: Authorisation is provided orally over telephone (maps to SEC code: TEL)
             * paper: Authorisation is provided in writing and signed, or similarly authenticated
             * (maps to SEC code: PPD)
             */
            public MandateRequest withAuthorisationSource(AuthorisationSource authorisationSource) {
                this.authorisationSource = authorisationSource;
                return this;
            }

            /**
             * This attribute represents the authorisation type between the payer and merchant. It
             * can be set to <code>one_off</code>, <code>recurring</code> or <code>standing</code>
             * for ACH scheme. And <code>single</code>, <code>recurring</code> and
             * <code>sporadic</code> for PAD scheme. <em>Note:</em> This is only supported for ACH
             * and PAD schemes.
             */
            public MandateRequest withConsentType(String consentType) {
                this.consentType = consentType;
                return this;
            }

            /**
             * Constraints that will apply to the mandate_request. (Optional) Specifically required
             * for PayTo and VRP.
             */
            public MandateRequest withConstraints(Constraints constraints) {
                this.constraints = constraints;
                return this;
            }

            /**
             * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency
             * code.
             */
            public MandateRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             */
            public MandateRequest withDescription(String description) {
                this.description = description;
                return this;
            }

            /**
             * This field will decide how GoCardless handles settlement of funds from the customer.
             * 
             * <ul>
             * <li><code>managed</code> will be moved through GoCardless' account, batched, and
             * payed out.</li>
             * <li><code>direct</code> will be a direct transfer from the payer's account to the
             * merchant where invoicing will be handled separately.</li>
             * </ul>
             */
            public MandateRequest withFundsSettlement(FundsSettlement fundsSettlement) {
                this.fundsSettlement = fundsSettlement;
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
             * Unique reference. Different schemes have different length and <a href=
             * "https://developer.gocardless.com/api-reference/#appendix-character-sets">character
             * set</a> requirements. GoCardless will generate a unique reference satisfying the
             * different scheme requirements if this field is left blank.
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
             * 
             * <ul>
             * <li><code>minimum</code>: only verify if absolutely required, such as when part of
             * scheme rules</li>
             * <li><code>recommended</code>: in addition to <code>minimum</code>, use the GoCardless
             * payment intelligence solution to decide if a payer should be verified</li>
             * <li><code>when_available</code>: if verification mechanisms are available, use
             * them</li>
             * <li><code>always</code>: as <code>when_available</code>, but fail to create the
             * Billing Request if a mechanism isn't available</li>
             * </ul>
             * By default, all Billing Requests use the <code>recommended</code> verification
             * preference. It uses GoCardless payment intelligence solution to determine if a payer
             * is fraudulent or not. The verification mechanism is based on the response and the
             * payer may be asked to verify themselves. If the feature is not available,
             * <code>recommended</code> behaves like <code>minimum</code>.
             * 
             * If you never wish to take advantage of our reduced risk products and Verified
             * Mandates as they are released in new schemes, please use the <code>minimum</code>
             * verification preference.
             * 
             * See <a href=
             * "https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/">Billing
             * Requests: Creating Verified Mandates</a> for more information.
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
             * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency
             * code. <code>GBP</code> and <code>EUR</code> supported; <code>GBP</code> with your
             * customers in the UK and for <code>EUR</code> with your customers in supported
             * Eurozone countries only.
             */
            public PaymentRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             */
            public PaymentRequest withDescription(String description) {
                this.description = description;
                return this;
            }

            /**
             * This field will decide how GoCardless handles settlement of funds from the customer.
             * 
             * <ul>
             * <li><code>managed</code> will be moved through GoCardless' account, batched, and
             * payed out.</li>
             * <li><code>direct</code> will be a direct transfer from the payer's account to the
             * merchant where invoicing will be handled separately.</li>
             * </ul>
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
             * on the PayTo scheme or payments using the Direct Funds settlement model on the Faster
             * Payments scheme.
             */
            public PaymentRequest withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * On failure, automatically retry payments using
             * <a href="https://developer.gocardless.com/success-plus/overview">intelligent
             * retries</a>. Default is <code>false</code>.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless
             * dashboard</a>.
             * </p>
             * <p class="notice">
             * <strong>Important</strong>: This is not applicable to Pay by Bank and VRP payments.
             * </p>
             */
            public PaymentRequest withRetryIfPossible(Boolean retryIfPossible) {
                this.retryIfPossible = retryIfPossible;
                return this;
            }

            /**
             * (Optional) A scheme used for Open Banking payments. Currently
             * <code>faster_payments</code> is supported in the UK (GBP) and
             * <code>sepa_credit_transfer</code> and <code>sepa_instant_credit_transfer</code> are
             * supported in supported Eurozone countries (EUR). For Eurozone countries,
             * <code>sepa_credit_transfer</code> is used as the default. Please be aware that
             * <code>sepa_instant_credit_transfer</code> may incur an additional fee for your
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
             * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency
             * code. Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are
             * supported.
             */
            public SubscriptionRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * As per RFC 2445. The day of the month to charge customers on. <code>1</code>
             * <ul>
             * <li></li>
             * </ul>
             * <code>28</code> or <code>-1</code> to indicate the last day of the month.
             */
            public SubscriptionRequest withDayOfMonth(Integer dayOfMonth) {
                this.dayOfMonth = dayOfMonth;
                return this;
            }

            /**
             * Number of <code>interval_units</code> between customer charge dates. Must be greater
             * than or equal to <code>1</code>. Must result in at least one charge date per year.
             * Defaults to <code>1</code>.
             */
            public SubscriptionRequest withInterval(Integer interval) {
                this.interval = interval;
                return this;
            }

            /**
             * The unit of time between customer charge dates. One of <code>weekly</code>,
             * <code>monthly</code> or <code>yearly</code>.
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
             * the interval_unit is <code>yearly</code>.
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
             * the <a href=
             * "https://developer.gocardless.com/api-reference/#payments-create-a-payment">create
             * payment endpoint</a> for more details. <br>
             * </br>
             */
            public SubscriptionRequest withPaymentReference(String paymentReference) {
                this.paymentReference = paymentReference;
                return this;
            }

            /**
             * On failure, automatically retry payments using
             * <a href="https://developer.gocardless.com/success-plus/overview">intelligent
             * retries</a>. Default is <code>false</code>.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless
             * dashboard</a>.
             * </p>
             */
            public SubscriptionRequest withRetryIfPossible(Boolean retryIfPossible) {
                this.retryIfPossible = retryIfPossible;
                return this;
            }

            /**
             * The date on which the first payment should be charged. If fulfilled after this date,
             * this will be set as the mandate's <code>next_possible_charge_date</code>. When left
             * blank and <code>month</code> or <code>day_of_month</code> are provided, this will be
             * set to the date of the first payment. If created without <code>month</code> or
             * <code>day_of_month</code> this will be set as the mandate's
             * <code>next_possible_charge_date</code>.
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
         * Customer's company name. Required unless a <code>given_name</code> and
         * <code>family_name</code> are provided. For Canadian customers, the use of a
         * <code>company_name</code> value will mean that any mandate created from this customer
         * will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
         * be a "Personal PAD").
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
         * Customer's surname. Required unless a <code>company_name</code> is provided.
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
         * Customer's first name. Required unless a <code>company_name</code> is provided.
         */
        public BillingRequestCollectCustomerDetailsRequest withCustomerGivenName(String givenName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withGivenName(givenName);
            return this;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">ISO 639-1</a> code. Used
         * as the language for notification emails sent by GoCardless if your organisation does not
         * send its own (see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-compliance-requirements">compliance
         * requirements</a>). Currently only "en", "fr", "de", "pt", "es", "it", "nl", "da", "nb",
         * "sl", "sv" are supported. If this is not provided and a customer was linked during
         * billing request creation, the linked customer language will be used. Otherwise, the
         * language is default to "en".
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
         * <a href="https://en.wikipedia.org/wiki/E.123">ITU E.123</a> formatted phone number,
         * including country code.
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
         * <a href=
         * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
         * 3166-1 alpha-2 code.</a>
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
         * Not required for creating offline mandates where <code>authorisation_source</code> is set
         * to telephone or paper.
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
         * <a href="https://en.wikipedia.org/wiki/ISO_3166-2:US">ISO3166-2:US</a> state code is
         * required (e.g. <code>CA</code> for California).
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
             * Customer's company name. Required unless a <code>given_name</code> and
             * <code>family_name</code> are provided. For Canadian customers, the use of a
             * <code>company_name</code> value will mean that any mandate created from this customer
             * will be considered to be a "Business PAD" (otherwise, any mandate will be considered
             * to be a "Personal PAD").
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
             * Customer's surname. Required unless a <code>company_name</code> is provided.
             */
            public Customer withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * Customer's first name. Required unless a <code>company_name</code> is provided.
             */
            public Customer withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">ISO 639-1</a> code.
             * Used as the language for notification emails sent by GoCardless if your organisation
             * does not send its own (see <a href=
             * "https://developer.gocardless.com/api-reference/#appendix-compliance-requirements">compliance
             * requirements</a>). Currently only "en", "fr", "de", "pt", "es", "it", "nl", "da",
             * "nb", "sl", "sv" are supported. If this is not provided and a customer was linked
             * during billing request creation, the linked customer language will be used.
             * Otherwise, the language is default to "en".
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
             * <a href="https://en.wikipedia.org/wiki/E.123">ITU E.123</a> formatted phone number,
             * including country code.
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
             * <a href=
             * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
             * 3166-1 alpha-2 code.</a>
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
             * Not required for creating offline mandates where <code>authorisation_source</code> is
             * set to telephone or paper.
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
             * <a href="https://en.wikipedia.org/wiki/ISO_3166-2:US">ISO3166-2:US</a> state code is
             * required (e.g. <code>CA</code> for California).
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
     * <em>ACH scheme</em> For compliance reasons, an extra validation step is done using a
     * third-party provider to make sure the customer's bank account can accept Direct Debit. If a
     * bank account is discovered to be closed or invalid, the customer is requested to adjust the
     * account number/routing number and succeed in this check to continue with the flow.
     * 
     * <em>BACS and SEPA schemes</em> <a href=
     * "https://hub.gocardless.com/s/article/Introduction-to-Payer-Name-Verification?language=en_GB">Payer
     * Name Verification</a> is enabled by default for UK and Eurozone based bank accounts, meaning
     * we verify the account holder name and bank account number/IBAN match the details held by the
     * relevant bank. If there is no match, the endpoint will return a 422 - validation error on
     * account_holder_name: "Account holder name does not match bank account details provided".
     * Testing instructions are <a href=
     * "https://developer.gocardless.com/developer-tools/scenario-simulators/#payer_name_verification">here</a>
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
         * Name of the account holder, as known by the bank. The full name provided when the
         * customer is created is stored and is available via the API, but is transliterated,
         * upcased, and truncated to 18 characters in bank submissions. This field is required
         * unless the request includes a <a href=
         * "https://developer.gocardless.com/api-reference/#javascript-flow-customer-bank-account-tokens">customer
         * bank account token</a>.
         */
        public BillingRequestCollectBankAccountRequest withAccountHolderName(
                String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public BillingRequestCollectBankAccountRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Account number suffix (only for bank accounts denominated in NZD) - see <a href=
         * "https://developer.gocardless.com/api-reference/#local-bank-details-new-zealand">local
         * details</a> for more information.
         */
        public BillingRequestCollectBankAccountRequest withAccountNumberSuffix(
                String accountNumberSuffix) {
            this.accountNumberSuffix = accountNumberSuffix;
            return this;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for
         * bank accounts in other currencies. See <a href=
         * "https://developer.gocardless.com/api-reference/#local-bank-details-united-states">local
         * details</a> for more information.
         */
        public BillingRequestCollectBankAccountRequest withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        /**
         * Bank code - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public BillingRequestCollectBankAccountRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public BillingRequestCollectBankAccountRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * <a href=
         * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
         * 3166-1 alpha-2 code</a>. Defaults to the country code of the <code>iban</code> if
         * supplied, otherwise is required.
         */
        public BillingRequestCollectBankAccountRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public BillingRequestCollectBankAccountRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a>. IBANs are not accepted for Swedish bank accounts denominated in SEK - you
         * must supply
         * <a href="https://developer.gocardless.com/api-reference/#local-bank-details-sweden">local
         * details</a>.
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
     * Returns a <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-cursor-pagination">cursor-paginated</a>
     * list of your billing requests.
     */
    public static final class BillingRequestListRequest<S> extends ListRequest<S, BillingRequest> {
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
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
         * If specified, this endpoint will return all requests for the given customer.
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
         * 
         * <ul>
         * <li><code>pending</code>: the billing request is pending and can be used</li>
         * <li><code>ready_to_fulfil</code>: the billing request is ready to fulfil</li>
         * <li><code>fulfilling</code>: the billing request is currently undergoing fulfilment</li>
         * <li><code>fulfilled</code>: the billing request has been fulfilled and a payment
         * created</li>
         * <li><code>cancelled</code>: the billing request has been cancelled and cannot be
         * used</li>
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
     * This endpoint is currently supported only for Pay by Bank Billing Requests.
     */
    public static final class BillingRequestNotifyRequest extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private String notificationType;
        private String redirectUri;

        /**
         * Currently, can only be <code>email</code>.
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
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
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
         * <a href=
         * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
         * 3166-1</a> alpha-2 code. The country code of the institution. If nothing is provided,
         * institutions with the country code 'GB' are returned by default.
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
