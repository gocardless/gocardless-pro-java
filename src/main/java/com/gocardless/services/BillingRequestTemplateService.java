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
        private String paymentRequestScheme;

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

        /**
         * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
         * supported in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer`
         * are supported in supported Eurozone countries (EUR). For Eurozone countries,
         * `sepa_credit_transfer` is used as the default. Please be aware that
         * `sepa_instant_credit_transfer` may incur an additional fee for your customer.
         */
        public BillingRequestTemplateListRequest<S> withPaymentRequestScheme(
                String paymentRequestScheme) {
            this.paymentRequestScheme = paymentRequestScheme;
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
            if (paymentRequestScheme != null) {
                params.put("payment_request_scheme", paymentRequestScheme);
            }
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
        private MandateRequestConstraints mandateRequestConstraints;
        private String mandateRequestCurrency;
        private String mandateRequestDescription;
        private Map<String, String> mandateRequestMetadata;
        private String mandateRequestScheme;
        private String mandateRequestVerify;
        private Map<String, String> metadata;
        private String name;
        private String paymentRequestAmount;
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
         * Constraints that will apply to the mandate_request. (Optional) Specifically required for
         * PayTo and VRP.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraints(
                MandateRequestConstraints mandateRequestConstraints) {
            this.mandateRequestConstraints = mandateRequestConstraints;
            return this;
        }

        /**
         * The latest date at which payments can be taken, must occur after start_date if present
         * 
         * This is an optional field and if it is not supplied the agreement will be considered open
         * and will not have an end date. Keep in mind the end date must take into account how long
         * it will take the user to set up this agreement via the Billing Request.
         * 
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraintsEndDate(
                String endDate) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withEndDate(endDate);
            return this;
        }

        /**
         * The maximum amount that can be charged for a single payment in the lowest denomination
         * for the currency (e.g. pence in GBP, cents in EUR). _Note:_ Required for PayTo and VRP.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraintsMaxAmountPerPayment(
                Integer maxAmountPerPayment) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withMaxAmountPerPayment(maxAmountPerPayment);
            return this;
        }

        /**
         * A constraint where you can specify info (free text string) about how payments are
         * calculated. For use when payments vary and cannot be expressed as a fixed amount and
         * frequency. _Note:_ This is only supported for ACH and PAD schemes.
         * 
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraintsPaymentMethod(
                String paymentMethod) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withPaymentMethod(paymentMethod);
            return this;
        }

        /**
         * Caps on the total amount and/or number of payments that can be collected within a
         * repeating period (e.g. no more than a set amount per month), as opposed to
         * `max_amount_per_payment` which caps a single payment.
         * 
         * _Note:_ Required for VRP, where exactly one periodic limit must be provided. Optional for
         * PayTo.
         * 
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraintsPeriodicLimits(
                List<PeriodicLimits> periodicLimits) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withPeriodicLimits(periodicLimits);
            return this;
        }

        /**
         * The date from which payments can be taken.
         * 
         * This is an optional field and if it is not supplied the start date will be set to the day
         * authorisation happens.
         * 
         */
        public BillingRequestTemplateCreateRequest withMandateRequestConstraintsStartDate(
                String startDate) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withStartDate(startDate);
            return this;
        }

        /**
         * [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
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
         * Verification preference for the mandate.
         */
        public BillingRequestTemplateCreateRequest withMandateRequestVerify(
                String mandateRequestVerify) {
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
         * Amount in full.
         */
        public BillingRequestTemplateCreateRequest withPaymentRequestAmount(
                String paymentRequestAmount) {
            this.paymentRequestAmount = paymentRequestAmount;
            return this;
        }

        /**
         * [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
         * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
         * supported Eurozone countries only.
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
         * are supported in supported Eurozone countries (EUR). For Eurozone countries,
         * `sepa_credit_transfer` is used as the default. Please be aware that
         * `sepa_instant_credit_transfer` may incur an additional fee for your customer.
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

        public static class PeriodicLimits {
            private Alignment alignment;
            private Integer maxPayments;
            private Integer maxTotalAmount;
            private Period period;

            /**
             * The alignment of the period. Defaults to `creation_date` if not specified.
             * 
             * `calendar` - the period follows fixed calendar boundaries, the same for every
             * mandate: `week` runs Monday to Sunday, `month` runs from the 1st to the last day of
             * the calendar month, and `year` runs from 1 January to 31 December. If the mandate
             * starts partway through a period, the limit for that first period is reduced
             * proportionally to the days remaining (e.g. a monthly limit starting on the 15th gives
             * roughly half the limit for that first month).
             * 
             * `creation_date` - the period follows the mandate's own start date rather than the
             * calendar. For example, if the mandate starts on the 15th, each monthly period runs
             * from the 15th to the 14th of the following month. The first period is a full period,
             * not reduced proportionally.
             * 
             * _Note:_ Has no effect when period is `flexible`.
             * 
             */
            public PeriodicLimits withAlignment(Alignment alignment) {
                this.alignment = alignment;
                return this;
            }

            /**
             * The maximum number of payments that can be collected in this periodic limit.
             * 
             * _Note:_ Only supported for the PayTo scheme, where it is optional.
             * 
             */
            public PeriodicLimits withMaxPayments(Integer maxPayments) {
                this.maxPayments = maxPayments;
                return this;
            }

            /**
             * The maximum total amount that can be charged for all payments in this periodic limit,
             * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             * 
             * _Note:_ Required for VRP. This is not permitted for the PayTo scheme.
             * 
             */
            public PeriodicLimits withMaxTotalAmount(Integer maxTotalAmount) {
                this.maxTotalAmount = maxTotalAmount;
                return this;
            }

            /**
             * The repeating period for this mandate. Required whenever a periodic limit is provided
             * (for both VRP and PayTo). If periodic_limits is omitted entirely for PayTo, this
             * defaults to flexible.
             * 
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

        public static class MandateRequestConstraints {
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
            public MandateRequestConstraints withEndDate(String endDate) {
                this.endDate = endDate;
                return this;
            }

            /**
             * The maximum amount that can be charged for a single payment in the lowest
             * denomination for the currency (e.g. pence in GBP, cents in EUR). _Note:_ Required for
             * PayTo and VRP.
             */
            public MandateRequestConstraints withMaxAmountPerPayment(Integer maxAmountPerPayment) {
                this.maxAmountPerPayment = maxAmountPerPayment;
                return this;
            }

            /**
             * A constraint where you can specify info (free text string) about how payments are
             * calculated. For use when payments vary and cannot be expressed as a fixed amount and
             * frequency. _Note:_ This is only supported for ACH and PAD schemes.
             * 
             */
            public MandateRequestConstraints withPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
                return this;
            }

            /**
             * Caps on the total amount and/or number of payments that can be collected within a
             * repeating period (e.g. no more than a set amount per month), as opposed to
             * `max_amount_per_payment` which caps a single payment.
             * 
             * _Note:_ Required for VRP, where exactly one periodic limit must be provided. Optional
             * for PayTo.
             * 
             */
            public MandateRequestConstraints withPeriodicLimits(
                    List<PeriodicLimits> periodicLimits) {
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
            public MandateRequestConstraints withStartDate(String startDate) {
                this.startDate = startDate;
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
        private MandateRequestConstraints mandateRequestConstraints;
        private String mandateRequestCurrency;
        private String mandateRequestDescription;
        private Map<String, String> mandateRequestMetadata;
        private String mandateRequestScheme;
        private String mandateRequestVerify;
        private Map<String, String> metadata;
        private String name;
        private String paymentRequestAmount;
        private String paymentRequestCurrency;
        private String paymentRequestDescription;
        private Map<String, String> paymentRequestMetadata;
        private String paymentRequestScheme;
        private String redirectUri;

        /**
         * Constraints that will apply to the mandate_request. (Optional) Specifically required for
         * PayTo and VRP.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraints(
                MandateRequestConstraints mandateRequestConstraints) {
            this.mandateRequestConstraints = mandateRequestConstraints;
            return this;
        }

        /**
         * The latest date at which payments can be taken, must occur after start_date if present
         * 
         * This is an optional field and if it is not supplied the agreement will be considered open
         * and will not have an end date. Keep in mind the end date must take into account how long
         * it will take the user to set up this agreement via the Billing Request.
         * 
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraintsEndDate(
                String endDate) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withEndDate(endDate);
            return this;
        }

        /**
         * The maximum amount that can be charged for a single payment in the lowest denomination
         * for the currency (e.g. pence in GBP, cents in EUR). _Note:_ Required for PayTo and VRP.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraintsMaxAmountPerPayment(
                Integer maxAmountPerPayment) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withMaxAmountPerPayment(maxAmountPerPayment);
            return this;
        }

        /**
         * A constraint where you can specify info (free text string) about how payments are
         * calculated. For use when payments vary and cannot be expressed as a fixed amount and
         * frequency. _Note:_ This is only supported for ACH and PAD schemes.
         * 
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraintsPaymentMethod(
                String paymentMethod) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withPaymentMethod(paymentMethod);
            return this;
        }

        /**
         * Caps on the total amount and/or number of payments that can be collected within a
         * repeating period (e.g. no more than a set amount per month), as opposed to
         * `max_amount_per_payment` which caps a single payment.
         * 
         * _Note:_ Required for VRP, where exactly one periodic limit must be provided. Optional for
         * PayTo.
         * 
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraintsPeriodicLimits(
                List<PeriodicLimits> periodicLimits) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withPeriodicLimits(periodicLimits);
            return this;
        }

        /**
         * The date from which payments can be taken.
         * 
         * This is an optional field and if it is not supplied the start date will be set to the day
         * authorisation happens.
         * 
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestConstraintsStartDate(
                String startDate) {
            if (mandateRequestConstraints == null) {
                mandateRequestConstraints = new MandateRequestConstraints();
            }
            mandateRequestConstraints.withStartDate(startDate);
            return this;
        }

        /**
         * [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
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
         * Verification preference for the mandate.
         */
        public BillingRequestTemplateUpdateRequest withMandateRequestVerify(
                String mandateRequestVerify) {
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
         * Amount in full.
         */
        public BillingRequestTemplateUpdateRequest withPaymentRequestAmount(
                String paymentRequestAmount) {
            this.paymentRequestAmount = paymentRequestAmount;
            return this;
        }

        /**
         * [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and
         * `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your customers in
         * supported Eurozone countries only.
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
         * are supported in supported Eurozone countries (EUR). For Eurozone countries,
         * `sepa_credit_transfer` is used as the default. Please be aware that
         * `sepa_instant_credit_transfer` may incur an additional fee for your customer.
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

        public static class PeriodicLimits {
            private Alignment alignment;
            private Integer maxPayments;
            private Integer maxTotalAmount;
            private Period period;

            /**
             * The alignment of the period. Defaults to `creation_date` if not specified.
             * 
             * `calendar` - the period follows fixed calendar boundaries, the same for every
             * mandate: `week` runs Monday to Sunday, `month` runs from the 1st to the last day of
             * the calendar month, and `year` runs from 1 January to 31 December. If the mandate
             * starts partway through a period, the limit for that first period is reduced
             * proportionally to the days remaining (e.g. a monthly limit starting on the 15th gives
             * roughly half the limit for that first month).
             * 
             * `creation_date` - the period follows the mandate's own start date rather than the
             * calendar. For example, if the mandate starts on the 15th, each monthly period runs
             * from the 15th to the 14th of the following month. The first period is a full period,
             * not reduced proportionally.
             * 
             * _Note:_ Has no effect when period is `flexible`.
             * 
             */
            public PeriodicLimits withAlignment(Alignment alignment) {
                this.alignment = alignment;
                return this;
            }

            /**
             * The maximum number of payments that can be collected in this periodic limit.
             * 
             * _Note:_ Only supported for the PayTo scheme, where it is optional.
             * 
             */
            public PeriodicLimits withMaxPayments(Integer maxPayments) {
                this.maxPayments = maxPayments;
                return this;
            }

            /**
             * The maximum total amount that can be charged for all payments in this periodic limit,
             * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             * 
             * _Note:_ Required for VRP. This is not permitted for the PayTo scheme.
             * 
             */
            public PeriodicLimits withMaxTotalAmount(Integer maxTotalAmount) {
                this.maxTotalAmount = maxTotalAmount;
                return this;
            }

            /**
             * The repeating period for this mandate. Required whenever a periodic limit is provided
             * (for both VRP and PayTo). If periodic_limits is omitted entirely for PayTo, this
             * defaults to flexible.
             * 
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

        public static class MandateRequestConstraints {
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
            public MandateRequestConstraints withEndDate(String endDate) {
                this.endDate = endDate;
                return this;
            }

            /**
             * The maximum amount that can be charged for a single payment in the lowest
             * denomination for the currency (e.g. pence in GBP, cents in EUR). _Note:_ Required for
             * PayTo and VRP.
             */
            public MandateRequestConstraints withMaxAmountPerPayment(Integer maxAmountPerPayment) {
                this.maxAmountPerPayment = maxAmountPerPayment;
                return this;
            }

            /**
             * A constraint where you can specify info (free text string) about how payments are
             * calculated. For use when payments vary and cannot be expressed as a fixed amount and
             * frequency. _Note:_ This is only supported for ACH and PAD schemes.
             * 
             */
            public MandateRequestConstraints withPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
                return this;
            }

            /**
             * Caps on the total amount and/or number of payments that can be collected within a
             * repeating period (e.g. no more than a set amount per month), as opposed to
             * `max_amount_per_payment` which caps a single payment.
             * 
             * _Note:_ Required for VRP, where exactly one periodic limit must be provided. Optional
             * for PayTo.
             * 
             */
            public MandateRequestConstraints withPeriodicLimits(
                    List<PeriodicLimits> periodicLimits) {
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
            public MandateRequestConstraints withStartDate(String startDate) {
                this.startDate = startDate;
                return this;
            }
        }
    }
}
