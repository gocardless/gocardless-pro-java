package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequestTemplate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with billing request template resources.
 *
 * Billing Request Templates are reusable templates that result in numerous Billing Requests with
 * similar attributes. They provide a no-code solution for generating various types of multi-user
 * payment links.
 * 
 * Each template includes a reusable URL that can be embedded in a website or shared with customers
 * via email. Every time the URL is opened, it generates a new Billing Request.
 * 
 * Billing Request Templates overcome the key limitation of the Billing Request: a Billing Request
 * cannot be shared among multiple users because it is intended for single-use and is designed to
 * cater to the unique needs of individual customers.
 */
public class BillingRequestTemplateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#billingRequestTemplates() }.
     */
    public BillingRequestTemplateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your Billing Request
     * Templates.
     */
    public BillingRequestTemplateListRequest<ListResponse<BillingRequestTemplate>> list() {
        return new BillingRequestTemplateListRequest<>(httpClient,
                ListRequest.<BillingRequestTemplate>pagingExecutor());
    }

    public BillingRequestTemplateListRequest<Iterable<BillingRequestTemplate>> all() {
        return new BillingRequestTemplateListRequest<>(httpClient,
                ListRequest.<BillingRequestTemplate>iteratingExecutor());
    }

    /**
     * Fetches a Billing Request Template
     */
    public BillingRequestTemplateGetRequest get(String identity) {
        return new BillingRequestTemplateGetRequest(httpClient, identity);
    }

    /**
      * 
     */
    public BillingRequestTemplateCreateRequest create() {
        return new BillingRequestTemplateCreateRequest(httpClient);
    }

    /**
     * Updates a Billing Request Template, which will affect all future Billing Requests created by
     * this template.
     */
    public BillingRequestTemplateUpdateRequest update(String identity) {
        return new BillingRequestTemplateUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BillingRequestTemplateService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your Billing Request
     * Templates.
     */
    public static final class BillingRequestTemplateListRequest<S>
            extends ListRequest<S, BillingRequestTemplate> {
        /**
         * Cursor pointing to the start of the desired set.
         */
        public BillingRequestTemplateListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public BillingRequestTemplateListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public BillingRequestTemplateListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private BillingRequestTemplateListRequest(HttpClient httpClient,
                ListRequestExecutor<S, BillingRequestTemplate> executor) {
            super(httpClient, executor);
        }

        public BillingRequestTemplateListRequest<S> withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_request_templates";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_templates";
        }

        @Override
        protected TypeToken<List<BillingRequestTemplate>> getTypeToken() {
            return new TypeToken<List<BillingRequestTemplate>>() {};
        }
    }

    /**
     * Request class for {@link BillingRequestTemplateService#get }.
     *
     * Fetches a Billing Request Template
     */
    public static final class BillingRequestTemplateGetRequest
            extends GetRequest<BillingRequestTemplate> {
        @PathParam
        private final String identity;

        private BillingRequestTemplateGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestTemplateGetRequest withHeader(String headerName, String headerValue) {
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
            return "billing_request_templates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_templates";
        }

        @Override
        protected Class<BillingRequestTemplate> getResponseClass() {
            return BillingRequestTemplate.class;
        }
    }

    /**
     * Request class for {@link BillingRequestTemplateService#create }.
     *
     * 
     */
    public static final class BillingRequestTemplateCreateRequest
            extends IdempotentPostRequest<BillingRequestTemplate> {
        private Links links;
        private String mandateRequestCurrency;
        private String mandateRequestDescription;
        private Map<String, String> mandateRequestMetadata;
        private String mandateRequestScheme;
        private MandateRequestVerify mandateRequestVerify;
        private Map<String, String> metadata;
        private String name;
        private Integer paymentRequestAmount;
        private String paymentRequestCurrency;
        private String paymentRequestDescription;
        private Map<String, String> paymentRequestMetadata;
        private String paymentRequestScheme;
        private String redirectUri;

        public BillingRequestTemplateCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors). Only required if your account
         * manages multiple creditors.
         */
        public BillingRequestTemplateCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestCurrency(
                String mandateRequestCurrency) {
            this.mandateRequestCurrency = mandateRequestCurrency;
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestTemplateCreateRequest withMandateRequestDescription(
                String mandateRequestDescription) {
            this.mandateRequestDescription = mandateRequestDescription;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the mandate created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestMetadata(
                Map<String, String> mandateRequestMetadata) {
            this.mandateRequestMetadata = mandateRequestMetadata;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the mandate created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestMetadata(String key,
                String value) {
            if (mandateRequestMetadata == null) {
                mandateRequestMetadata = new HashMap<>();
            }
            mandateRequestMetadata.put(key, value);
            return this;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         * Optional for mandate only requests - if left blank, the payer will be able to select the
         * currency/scheme to pay with from a list of your available schemes.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestScheme(
                String mandateRequestScheme) {
            this.mandateRequestScheme = mandateRequestScheme;
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
        public BillingRequestTemplateCreateRequest withMandateRequestVerify(
                MandateRequestVerify mandateRequestVerify) {
            this.mandateRequestVerify = mandateRequestVerify;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name for the template. Provides a friendly human name for the template, as it is shown in
         * the dashboard. Must not exceed 255 characters.
         */
        public BillingRequestTemplateCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Amount in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestAmount(
                Integer paymentRequestAmount) {
            this.paymentRequestAmount = paymentRequestAmount;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
         * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
         * Germany only.
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestCurrency(
                String paymentRequestCurrency) {
            this.paymentRequestCurrency = paymentRequestCurrency;
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestDescription(
                String paymentRequestDescription) {
            this.paymentRequestDescription = paymentRequestDescription;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the payment created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestMetadata(
                Map<String, String> paymentRequestMetadata) {
            this.paymentRequestMetadata = paymentRequestMetadata;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the payment created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestMetadata(String key,
                String value) {
            if (paymentRequestMetadata == null) {
                paymentRequestMetadata = new HashMap<>();
            }
            paymentRequestMetadata.put(key, value);
            return this;
        }

        /**
         * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
         * supported in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer`
         * are supported in Germany (EUR). In Germany, `sepa_credit_transfer` is used as the
         * default. Please be aware that `sepa_instant_credit_transfer` may incur an additional fee
         * for your customer.
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestScheme(
                String paymentRequestScheme) {
            this.paymentRequestScheme = paymentRequestScheme;
            return this;
        }

        /**
         * URL that the payer can be redirected to after completing the request flow.
         */
        public BillingRequestTemplateCreateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public BillingRequestTemplateCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<BillingRequestTemplate> handleConflict(HttpClient httpClient,
                String id) {
            BillingRequestTemplateGetRequest request =
                    new BillingRequestTemplateGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private BillingRequestTemplateCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BillingRequestTemplateCreateRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "billing_request_templates";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_templates";
        }

        @Override
        protected Class<BillingRequestTemplate> getResponseClass() {
            return BillingRequestTemplate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum MandateRequestVerify {
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

        public static class Links {
            private String creditor;

            /**
             * ID of the associated [creditor](#core-endpoints-creditors). Only required if your
             * account manages multiple creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link BillingRequestTemplateService#update }.
     *
     * Updates a Billing Request Template, which will affect all future Billing Requests created by
     * this template.
     */
    public static final class BillingRequestTemplateUpdateRequest
            extends PutRequest<BillingRequestTemplate> {
        @PathParam
        private final String identity;
        private String mandateRequestCurrency;
        private String mandateRequestDescription;
        private Map<String, String> mandateRequestMetadata;
        private String mandateRequestScheme;
        private MandateRequestVerify mandateRequestVerify;
        private Map<String, String> metadata;
        private String name;
        private Integer paymentRequestAmount;
        private String paymentRequestCurrency;
        private String paymentRequestDescription;
        private Map<String, String> paymentRequestMetadata;
        private String paymentRequestScheme;
        private String redirectUri;

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestCurrency(
                String mandateRequestCurrency) {
            this.mandateRequestCurrency = mandateRequestCurrency;
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestDescription(
                String mandateRequestDescription) {
            this.mandateRequestDescription = mandateRequestDescription;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the mandate created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestMetadata(
                Map<String, String> mandateRequestMetadata) {
            this.mandateRequestMetadata = mandateRequestMetadata;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the mandate created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestMetadata(String key,
                String value) {
            if (mandateRequestMetadata == null) {
                mandateRequestMetadata = new HashMap<>();
            }
            mandateRequestMetadata.put(key, value);
            return this;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         * Optional for mandate only requests - if left blank, the payer will be able to select the
         * currency/scheme to pay with from a list of your available schemes.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestScheme(
                String mandateRequestScheme) {
            this.mandateRequestScheme = mandateRequestScheme;
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
        public BillingRequestTemplateUpdateRequest withMandateRequestVerify(
                MandateRequestVerify mandateRequestVerify) {
            this.mandateRequestVerify = mandateRequestVerify;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name for the template. Provides a friendly human name for the template, as it is shown in
         * the dashboard. Must not exceed 255 characters.
         */
        public BillingRequestTemplateUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Amount in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestAmount(
                Integer paymentRequestAmount) {
            this.paymentRequestAmount = paymentRequestAmount;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
         * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
         * Germany only.
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestCurrency(
                String paymentRequestCurrency) {
            this.paymentRequestCurrency = paymentRequestCurrency;
            return this;
        }

        /**
         * A human-readable description of the payment and/or mandate. This will be displayed to the
         * payer when authorising the billing request.
         * 
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestDescription(
                String paymentRequestDescription) {
            this.paymentRequestDescription = paymentRequestDescription;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the payment created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestMetadata(
                Map<String, String> paymentRequestMetadata) {
            this.paymentRequestMetadata = paymentRequestMetadata;
            return this;
        }

        /**
         * Key-value store of custom data that will be applied to the payment created when this
         * request is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestMetadata(String key,
                String value) {
            if (paymentRequestMetadata == null) {
                paymentRequestMetadata = new HashMap<>();
            }
            paymentRequestMetadata.put(key, value);
            return this;
        }

        /**
         * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
         * supported in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer`
         * are supported in Germany (EUR). In Germany, `sepa_credit_transfer` is used as the
         * default. Please be aware that `sepa_instant_credit_transfer` may incur an additional fee
         * for your customer.
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestScheme(
                String paymentRequestScheme) {
            this.paymentRequestScheme = paymentRequestScheme;
            return this;
        }

        /**
         * URL that the payer can be redirected to after completing the request flow.
         */
        public BillingRequestTemplateUpdateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        private BillingRequestTemplateUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestTemplateUpdateRequest withHeader(String headerName,
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
            return "billing_request_templates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_templates";
        }

        @Override
        protected Class<BillingRequestTemplate> getResponseClass() {
            return BillingRequestTemplate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum MandateRequestVerify {
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
}
