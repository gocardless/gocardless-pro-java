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
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your billing_requests.
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
      * 
     */
    public BillingRequestCreateRequest create() {
        return new BillingRequestCreateRequest(httpClient);
    }

    /**
     * Fetches a billing request
     */
    public BillingRequestGetRequest get(String identity) {
        return new BillingRequestGetRequest(httpClient, identity);
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
     */
    public BillingRequestCollectBankAccountRequest collectBankAccount(String identity) {
        return new BillingRequestCollectBankAccountRequest(httpClient, identity);
    }

    /**
     * If a billing request is ready to be fulfilled, call this endpoint to cause it to fulfil,
     * executing the payment.
     */
    public BillingRequestFulfilRequest fulfil(String identity) {
        return new BillingRequestFulfilRequest(httpClient, identity);
    }

    /**
     * This is needed when you have mandate_request. As a scheme compliance rule we are required to
     * allow the payer to crosscheck the details entered by them and confirm it.
     */
    public BillingRequestConfirmPayerDetailsRequest confirmPayerDetails(String identity) {
        return new BillingRequestConfirmPayerDetailsRequest(httpClient, identity);
    }

    /**
     * Immediately cancels a billing request, causing all billing request flows to expire.
     */
    public BillingRequestCancelRequest cancel(String identity) {
        return new BillingRequestCancelRequest(httpClient, identity);
    }

    /**
     * Notifies the customer linked to the billing request, asking them to authorise it. Currently,
     * the customer can only be notified by email.
     */
    public BillingRequestNotifyRequest notify(String identity) {
        return new BillingRequestNotifyRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BillingRequestService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your billing_requests.
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
         * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
         * created.
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
         * <li>`pending`: the billing_request is pending and can be used</li>
         * <li>`ready_to_fulfil`: the billing_request is ready to fulfil</li>
         * <li>`fulfilled`: the billing_request has been fulfilled and a payment created</li>
         * <li>`cancelled`: the billing_request has been cancelled and cannot be used</li>
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
     * Request class for {@link BillingRequestService#create }.
     *
     * 
     */
    public static final class BillingRequestCreateRequest
            extends IdempotentPostRequest<BillingRequest> {
        private Links links;
        private MandateRequest mandateRequest;
        private Map<String, String> metadata;
        private PaymentRequest paymentRequest;

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
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * only "GBP" is supported as we only have one scheme that is per_payment_authorised.
         */
        public BillingRequestCreateRequest withMandateRequestCurrency(String currency) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withCurrency(currency);
            return this;
        }

        /**
         * A Direct Debit scheme. Currently "ach", "bacs", "becs", "becs_nz", "betalingsservice",
         * "pad" and "sepa_core" are supported.
         */
        public BillingRequestCreateRequest withMandateRequestScheme(String scheme) {
            if (mandateRequest == null) {
                mandateRequest = new MandateRequest();
            }
            mandateRequest.withScheme(scheme);
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
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * only "GBP" is supported as we only have one scheme that is per_payment_authorised.
         */
        public BillingRequestCreateRequest withPaymentRequestCurrency(String currency) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withCurrency(currency);
            return this;
        }

        /**
         * A human-readable description of the payment. This will be displayed to the payer when
         * authorising the billing request.
         * 
         */
        public BillingRequestCreateRequest withPaymentRequestDescription(String description) {
            if (paymentRequest == null) {
                paymentRequest = new PaymentRequest();
            }
            paymentRequest.withDescription(description);
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

        public static class MandateRequest {
            private String currency;
            private String scheme;

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently only "GBP" is supported as we only have one scheme that is
             * per_payment_authorised.
             */
            public MandateRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A Direct Debit scheme. Currently "ach", "bacs", "becs", "becs_nz",
             * "betalingsservice", "pad" and "sepa_core" are supported.
             */
            public MandateRequest withScheme(String scheme) {
                this.scheme = scheme;
                return this;
            }
        }

        public static class PaymentRequest {
            private Integer amount;
            private Integer appFee;
            private String currency;
            private String description;

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
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently only "GBP" is supported as we only have one scheme that is
             * per_payment_authorised.
             */
            public PaymentRequest withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * A human-readable description of the payment. This will be displayed to the payer when
             * authorising the billing request.
             * 
             */
            public PaymentRequest withDescription(String description) {
                this.description = description;
                return this;
            }
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
         * this is not provided, the language will be chosen based on the `country_code` (if
         * supplied) or default to "en".
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
             * supported. If this is not provided, the language will be chosen based on the
             * `country_code` (if supplied) or default to "en".
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

        /**
         * Name of the account holder, as known by the bank. Usually this is the same as the name
         * stored with the linked [creditor](#core-endpoints-creditors). This field will be
         * transliterated, upcased and truncated to 18 characters. This field is required unless the
         * request includes a [customer bank account
         * token](#javascript-flow-customer-bank-account-tokens).
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
     * Request class for {@link BillingRequestService#confirmPayerDetails }.
     *
     * This is needed when you have mandate_request. As a scheme compliance rule we are required to
     * allow the payer to crosscheck the details entered by them and confirm it.
     */
    public static final class BillingRequestConfirmPayerDetailsRequest
            extends PostRequest<BillingRequest> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

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
     * Request class for {@link BillingRequestService#notify }.
     *
     * Notifies the customer linked to the billing request, asking them to authorise it. Currently,
     * the customer can only be notified by email.
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
}
