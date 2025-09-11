package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Payment;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with payment resources.
 *
 * Payment objects represent payments from a [customer](#core-endpoints-customers) to a
 * [creditor](#core-endpoints-creditors), taken against a Direct Debit
 * [mandate](#core-endpoints-mandates).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) whenever the state of a payment
 * changes.
 */
public class PaymentService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#payments() }.
     */
    public PaymentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * <a name="mandate_is_inactive"></a>Creates a new payment object.
     * 
     * This fails with a `mandate_is_inactive` error if the linked
     * [mandate](#core-endpoints-mandates) is cancelled or has failed. Payments can be created
     * against mandates with status of: `pending_customer_approval`, `pending_submission`,
     * `submitted`, and `active`.
     */
    public PaymentCreateRequest create() {
        return new PaymentCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payments.
     */
    public PaymentListRequest<ListResponse<Payment>> list() {
        return new PaymentListRequest<>(httpClient, ListRequest.<Payment>pagingExecutor());
    }

    public PaymentListRequest<Iterable<Payment>> all() {
        return new PaymentListRequest<>(httpClient, ListRequest.<Payment>iteratingExecutor());
    }

    /**
     * Retrieves the details of a single existing payment.
     */
    public PaymentGetRequest get(String identity) {
        return new PaymentGetRequest(httpClient, identity);
    }

    /**
     * Updates a payment object. This accepts only the metadata parameter.
     */
    public PaymentUpdateRequest update(String identity) {
        return new PaymentUpdateRequest(httpClient, identity);
    }

    /**
     * Cancels the payment if it has not already been submitted to the banks. Any metadata supplied
     * to this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This will fail with a `cancellation_failed` error unless the payment's status is
     * `pending_submission`.
     */
    public PaymentCancelRequest cancel(String identity) {
        return new PaymentCancelRequest(httpClient, identity);
    }

    /**
     * <a name="retry_failed"></a>Retries a failed payment if the underlying mandate is active. You
     * will receive a `resubmission_requested` webhook, but after that retrying the payment follows
     * the same process as its initial creation, so you will receive a `submitted` webhook, followed
     * by a `confirmed` or `failed` event. Any metadata supplied to this endpoint will be stored
     * against the payment submission event it causes.
     * 
     * This will return a `retry_failed` error if the payment has not failed.
     * 
     * Payments can be retried up to 3 times.
     */
    public PaymentRetryRequest retry(String identity) {
        return new PaymentRetryRequest(httpClient, identity);
    }

    /**
     * Request class for {@link PaymentService#create }.
     *
     * <a name="mandate_is_inactive"></a>Creates a new payment object.
     * 
     * This fails with a `mandate_is_inactive` error if the linked
     * [mandate](#core-endpoints-mandates) is cancelled or has failed. Payments can be created
     * against mandates with status of: `pending_customer_approval`, `pending_submission`,
     * `submitted`, and `active`.
     */
    public static final class PaymentCreateRequest extends IdempotentPostRequest<Payment> {
        private Integer amount;
        private Integer appFee;
        private String chargeDate;
        private Currency currency;
        private String description;
        private Boolean fasterAch;
        private Links links;
        private Map<String, String> metadata;
        private String reference;
        private Boolean retryIfPossible;

        /**
         * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public PaymentCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * The amount to be deducted from the payment as the OAuth app's fee, in the lowest
         * denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public PaymentCreateRequest withAppFee(Integer appFee) {
            this.appFee = appFee;
            return this;
        }

        /**
         * A future date on which the payment should be collected. If not specified, the payment
         * will be collected as soon as possible. If the value is before the
         * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date` creation will fail. If
         * the value is not a working day it will be rolled forwards to the next available one.
         */
        public PaymentCreateRequest withChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public PaymentCreateRequest withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * A human-readable description of the payment. This will be included in the notification
         * email GoCardless sends to your customer if your organisation does not send its own
         * notifications (see [compliance requirements](#appendix-compliance-requirements)).
         */
        public PaymentCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set this to true or false in the request to create an ACH payment to explicitly choose
         * whether the payment should be processed through Faster ACH or standard ACH, rather than
         * relying on the presence or absence of the charge date to indicate that.
         */
        public PaymentCreateRequest withFasterAch(Boolean fasterAch) {
            this.fasterAch = fasterAch;
            return this;
        }

        public PaymentCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [mandate](#core-endpoints-mandates) against which this payment should be
         * collected.
         */
        public PaymentCreateRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * An optional reference that will appear on your customer's bank statement. The character
         * limit for this reference is dependent on the scheme.<br />
         * <strong>ACH</strong> - 10 characters<br />
         * <strong>Autogiro</strong> - 11 characters<br />
         * <strong>Bacs</strong> - 10 characters<br />
         * <strong>BECS</strong> - 30 characters<br />
         * <strong>BECS NZ</strong> - 12 characters<br />
         * <strong>Betalingsservice</strong> - 30 characters<br />
         * <strong>Faster Payments</strong> - 18 characters<br />
         * <strong>PAD</strong> - scheme doesn't offer references<br />
         * <strong>PayTo</strong> - 18 characters<br />
         * <strong>SEPA</strong> - 140 characters<br />
         * Note that this reference must be unique (for each merchant) for the BECS scheme as it is
         * a scheme requirement.
         * <p class='restricted-notice'>
         * <strong>Restricted</strong>: You can only specify a payment reference for Bacs payments
         * (that is, when collecting from the UK) if you're on the
         * <a href='https://gocardless.com/pricing'>GoCardless Plus, Pro or Enterprise packages</a>.
         * </p>
         * <p class='restricted-notice'>
         * <strong>Restricted</strong>: You can not specify a payment reference for Faster Payments.
         * </p>
         */
        public PaymentCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        /**
         * On failure, automatically retry the payment using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
         * </p>
         */
        public PaymentCreateRequest withRetryIfPossible(Boolean retryIfPossible) {
            this.retryIfPossible = retryIfPossible;
            return this;
        }

        public PaymentCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Payment> handleConflict(HttpClient httpClient, String id) {
            PaymentGetRequest request = new PaymentGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private PaymentCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public PaymentCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "payments";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Currency {
            @SerializedName("AUD")
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name();
            }
        }

        public static class Links {
            private String mandate;

            /**
             * ID of the [mandate](#core-endpoints-mandates) against which this payment should be
             * collected.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }

    /**
     * Request class for {@link PaymentService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payments.
     */
    public static final class PaymentListRequest<S> extends ListRequest<S, Payment> {
        private ChargeDate chargeDate;
        private CreatedAt createdAt;
        private String creditor;
        private Currency currency;
        private String customer;
        private String mandate;
        private SortDirection sortDirection;
        private SortField sortField;
        private Status status;
        private String subscription;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PaymentListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PaymentListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public PaymentListRequest<S> withChargeDate(ChargeDate chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        /**
         * Limit to records where the payment was or will be collected from the customer's bank
         * account after the specified date.
         */
        public PaymentListRequest<S> withChargeDateGt(String gt) {
            if (chargeDate == null) {
                chargeDate = new ChargeDate();
            }
            chargeDate.withGt(gt);
            return this;
        }

        /**
         * Limit to records where the payment was or will be collected from the customer's bank
         * account on or after the specified date.
         */
        public PaymentListRequest<S> withChargeDateGte(String gte) {
            if (chargeDate == null) {
                chargeDate = new ChargeDate();
            }
            chargeDate.withGte(gte);
            return this;
        }

        /**
         * Limit to records where the payment was or will be collected from the customer's bank
         * account before the specified date.
         */
        public PaymentListRequest<S> withChargeDateLt(String lt) {
            if (chargeDate == null) {
                chargeDate = new ChargeDate();
            }
            chargeDate.withLt(lt);
            return this;
        }

        /**
         * Limit to records where the payment was or will be collected from the customer's bank
         * account on or before the specified date.
         */
        public PaymentListRequest<S> withChargeDateLte(String lte) {
            if (chargeDate == null) {
                chargeDate = new ChargeDate();
            }
            chargeDate.withLte(lte);
            return this;
        }

        public PaymentListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public PaymentListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public PaymentListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public PaymentListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public PaymentListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * ID of a creditor to filter payments by. If you pass this parameter, you cannot also pass
         * `customer`.
         */
        public PaymentListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public PaymentListRequest<S> withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * ID of a customer to filter payments by. If you pass this parameter, you cannot also pass
         * `creditor`.
         */
        public PaymentListRequest<S> withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PaymentListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates
         * created before 2016.
         */
        public PaymentListRequest<S> withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * The direction to sort in. One of:
         * <ul>
         * <li>`asc`</li>
         * <li>`desc`</li>
         * </ul>
         */
        public PaymentListRequest<S> withSortDirection(SortDirection sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        /**
         * Field by which to sort records. One of:
         * <ul>
         * <li>`charge_date`</li>
         * <li>`amount`</li>
         * </ul>
         */
        public PaymentListRequest<S> withSortField(SortField sortField) {
            this.sortField = sortField;
            return this;
        }

        /**
         * One of:
         * <ul>
         * <li>`pending_customer_approval`: we're waiting for the customer to approve this
         * payment</li>
         * <li>`pending_submission`: the payment has been created, but not yet submitted to the
         * banks</li>
         * <li>`submitted`: the payment has been submitted to the banks</li>
         * <li>`confirmed`: the payment has been confirmed as collected</li>
         * <li>`paid_out`: the payment has been included in a [payout](#core-endpoints-payouts)</li>
         * <li>`cancelled`: the payment has been cancelled</li>
         * <li>`customer_approval_denied`: the customer has denied approval for the payment. You
         * should contact the customer directly</li>
         * <li>`failed`: the payment failed to be processed. Note that payments can fail after being
         * confirmed if the failure message is sent late by the banks.</li>
         * <li>`charged_back`: the payment has been charged back</li>
         * </ul>
         */
        public PaymentListRequest<S> withStatus(Status status) {
            this.status = status;
            return this;
        }

        /**
         * Unique identifier, beginning with "SB".
         */
        public PaymentListRequest<S> withSubscription(String subscription) {
            this.subscription = subscription;
            return this;
        }

        private PaymentListRequest(HttpClient httpClient,
                ListRequestExecutor<S, Payment> executor) {
            super(httpClient, executor);
        }

        public PaymentListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (chargeDate != null) {
                params.putAll(chargeDate.getQueryParams());
            }
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (currency != null) {
                params.put("currency", currency);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            if (sortDirection != null) {
                params.put("sort_direction", sortDirection);
            }
            if (sortField != null) {
                params.put("sort_field", sortField);
            }
            if (status != null) {
                params.put("status", status);
            }
            if (subscription != null) {
                params.put("subscription", subscription);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "payments";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected TypeToken<List<Payment>> getTypeToken() {
            return new TypeToken<List<Payment>>() {};
        }

        public enum Currency {
            @SerializedName("AUD")
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name();
            }
        }

        public enum SortDirection {
            @SerializedName("asc")
            ASC, @SerializedName("desc")
            DESC, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum SortField {
            @SerializedName("charge_date")
            CHARGE_DATE, @SerializedName("amount")
            AMOUNT, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum Status {
            @SerializedName("pending_customer_approval")
            PENDING_CUSTOMER_APPROVAL, @SerializedName("pending_submission")
            PENDING_SUBMISSION, @SerializedName("submitted")
            SUBMITTED, @SerializedName("confirmed")
            CONFIRMED, @SerializedName("paid_out")
            PAID_OUT, @SerializedName("cancelled")
            CANCELLED, @SerializedName("customer_approval_denied")
            CUSTOMER_APPROVAL_DENIED, @SerializedName("failed")
            FAILED, @SerializedName("charged_back")
            CHARGED_BACK, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class ChargeDate {
            private String gt;
            private String gte;
            private String lt;
            private String lte;

            /**
             * Limit to records where the payment was or will be collected from the customer's bank
             * account after the specified date.
             */
            public ChargeDate withGt(String gt) {
                this.gt = gt;
                return this;
            }

            /**
             * Limit to records where the payment was or will be collected from the customer's bank
             * account on or after the specified date.
             */
            public ChargeDate withGte(String gte) {
                this.gte = gte;
                return this;
            }

            /**
             * Limit to records where the payment was or will be collected from the customer's bank
             * account before the specified date.
             */
            public ChargeDate withLt(String lt) {
                this.lt = lt;
                return this;
            }

            /**
             * Limit to records where the payment was or will be collected from the customer's bank
             * account on or before the specified date.
             */
            public ChargeDate withLte(String lte) {
                this.lte = lte;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (gt != null) {
                    params.put("charge_date[gt]", gt);
                }
                if (gte != null) {
                    params.put("charge_date[gte]", gte);
                }
                if (lt != null) {
                    params.put("charge_date[lt]", lt);
                }
                if (lte != null) {
                    params.put("charge_date[lte]", lte);
                }
                return params.build();
            }
        }

        public static class CreatedAt {
            private String gt;
            private String gte;
            private String lt;
            private String lte;

            /**
             * Limit to records created after the specified date-time.
             */
            public CreatedAt withGt(String gt) {
                this.gt = gt;
                return this;
            }

            /**
             * Limit to records created on or after the specified date-time.
             */
            public CreatedAt withGte(String gte) {
                this.gte = gte;
                return this;
            }

            /**
             * Limit to records created before the specified date-time.
             */
            public CreatedAt withLt(String lt) {
                this.lt = lt;
                return this;
            }

            /**
             * Limit to records created on or before the specified date-time.
             */
            public CreatedAt withLte(String lte) {
                this.lte = lte;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (gt != null) {
                    params.put("created_at[gt]", gt);
                }
                if (gte != null) {
                    params.put("created_at[gte]", gte);
                }
                if (lt != null) {
                    params.put("created_at[lt]", lt);
                }
                if (lte != null) {
                    params.put("created_at[lte]", lte);
                }
                return params.build();
            }
        }
    }

    /**
     * Request class for {@link PaymentService#get }.
     *
     * Retrieves the details of a single existing payment.
     */
    public static final class PaymentGetRequest extends GetRequest<Payment> {
        @PathParam
        private final String identity;

        private PaymentGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PaymentGetRequest withHeader(String headerName, String headerValue) {
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
            return "payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }
    }

    /**
     * Request class for {@link PaymentService#update }.
     *
     * Updates a payment object. This accepts only the metadata parameter.
     */
    public static final class PaymentUpdateRequest extends PutRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;
        private Boolean retryIfPossible;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * On failure, automatically retry the payment using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         * <p class="notice">
         * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
         * enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
         * </p>
         */
        public PaymentUpdateRequest withRetryIfPossible(Boolean retryIfPossible) {
            this.retryIfPossible = retryIfPossible;
            return this;
        }

        private PaymentUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PaymentUpdateRequest withHeader(String headerName, String headerValue) {
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
            return "payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link PaymentService#cancel }.
     *
     * Cancels the payment if it has not already been submitted to the banks. Any metadata supplied
     * to this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This will fail with a `cancellation_failed` error unless the payment's status is
     * `pending_submission`.
     */
    public static final class PaymentCancelRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentCancelRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private PaymentCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PaymentCancelRequest withHeader(String headerName, String headerValue) {
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
            return "payments/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
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
     * Request class for {@link PaymentService#retry }.
     *
     * <a name="retry_failed"></a>Retries a failed payment if the underlying mandate is active. You
     * will receive a `resubmission_requested` webhook, but after that retrying the payment follows
     * the same process as its initial creation, so you will receive a `submitted` webhook, followed
     * by a `confirmed` or `failed` event. Any metadata supplied to this endpoint will be stored
     * against the payment submission event it causes.
     * 
     * This will return a `retry_failed` error if the payment has not failed.
     * 
     * Payments can be retried up to 3 times.
     */
    public static final class PaymentRetryRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private String chargeDate;
        private Map<String, String> metadata;

        /**
         * A future date on which the payment should be collected. If not specified, the payment
         * will be collected as soon as possible. If the value is before the
         * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date` creation will fail. If
         * the value is not a working day it will be rolled forwards to the next available one.
         */
        public PaymentRetryRequest withChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentRetryRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PaymentRetryRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private PaymentRetryRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PaymentRetryRequest withHeader(String headerName, String headerValue) {
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
            return "payments/:identity/actions/retry";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
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
