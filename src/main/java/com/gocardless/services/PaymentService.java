package com.gocardless.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Payment;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with payment resources.
 *
 * Payment objects represent payments from a [customer](#core-endpoints-customers) to a
 * [creditor](#whitelabel-partner-endpoints-creditors), taken against a Direct Debit
 * [mandate](#core-endpoints-mandates).
 * 
 * GoCardless will notify you via a [webhook](#webhooks)
 * whenever the state of a payment changes.
 */
public class PaymentService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#payments() }.
     */
    public PaymentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * <a name="mandate_is_inactive"></a>Creates a new payment object.
     * 
     * This fails with a
     * `mandate_is_inactive` error if the linked [mandate](#core-endpoints-mandates) is cancelled or has
     * failed. Payments can be created against `pending_submission` and `submitted` as well as `active`
     * mandates.
     */
    public PaymentCreateRequest create() {
        return new PaymentCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your payments.
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
     * Cancels the payment if it has not already been submitted to the banks. Any metadata supplied to
     * this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This will fail with
     * a `cancellation_failed` error unless the payment's status is `pending_submission`.
     */
    public PaymentCancelRequest cancel(String identity) {
        return new PaymentCancelRequest(httpClient, identity);
    }

    /**
     * <a name="retry_failed"></a>Retries a failed payment if the underlying mandate is active. You will
     * receive a `resubmission_requested` webhook, but after that retrying the payment follows the same
     * process as its initial creation, so you will receive a `submitted` webhook, followed by a
     * `confirmed` or `failed` event. Any metadata supplied to this endpoint will be stored against the
     * payment submission event it causes.
     * 
     * This will return a `retry_failed` error if the payment
     * has not failed.
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
     * This fails with a
     * `mandate_is_inactive` error if the linked [mandate](#core-endpoints-mandates) is cancelled or has
     * failed. Payments can be created against `pending_submission` and `submitted` as well as `active`
     * mandates.
     */
    public static final class PaymentCreateRequest extends IdempotentPostRequest<Payment> {
        private Integer amount;
        private Integer appFee;
        private String chargeDate;
        private String currency;
        private String description;
        private Links links;
        private Map<String, String> metadata;
        private String reference;

        /**
         * Amount in pence (GBP), cents (EUR), or öre (SEK).
         */
        public PaymentCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * The amount to be deducted from the payment as the OAuth app's fee, in pence or cents. <p
         * class="beta-notice"><strong>Beta</strong>: This field is part of the <a href='#guides-oauth'>OAuth
         * API</a>, which is currently in beta.</p>
         */
        public PaymentCreateRequest withAppFee(Integer appFee) {
            this.appFee = appFee;
            return this;
        }

        /**
         * A future date on which the payment should be collected. If not specified, the payment will be
         * collected as soon as possible. This must be on or after the [mandate](#core-endpoints-mandates)'s
         * `next_possible_charge_date`, and will be rolled-forwards by GoCardless if it is not a working day.
         */
        public PaymentCreateRequest withChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code, currently only
         * "GBP", "EUR", and "SEK" are supported.
         */
        public PaymentCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * A human readable description of the payment.
         */
        public PaymentCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        public PaymentCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [mandate](#core-endpoints-mandates) against which this payment should be collected.
         */
        public PaymentCreateRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * An optional payment reference that will appear on your customer's bank statement. For Bacs
         * payments this can be up to 10 characters, for SEPA payments the limit is 140 characters, and for
         * Autogiro payments the limit is 11 characters.
         */
        public PaymentCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public PaymentCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Payment> handleConflict(HttpClient httpClient, String id) {
            return new PaymentGetRequest(httpClient, id);
        }

        private PaymentCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/payments";
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

        public static class Links {
            private String mandate;

            /**
             * ID of the [mandate](#core-endpoints-mandates) against which this payment should be collected.
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
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your payments.
     */
    public static final class PaymentListRequest<S> extends ListRequest<S, Payment> {
        private CreatedAt createdAt;
        private String creditor;
        private String customer;
        private String mandate;
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
         * Unique identifier, beginning with "MD".
         */
        public PaymentListRequest<S> withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * One of:
         * <ul>
         * <li>`pending_submission`: the payment has been created, but not yet submitted to
         * the banks</li>
         * <li>`submitted`: the payment has been submitted to the banks</li>
         *
         * <li>`confirmed`: the payment has been confirmed as collected</li>
         * <li>`failed`: the payment
         * failed to be processed. Note that payments can fail after being confirmed, if the failure message
         * is sent late by the banks.</li>
         * <li>`charged_back`: the payment has been charged back</li>
         *
         * <li>`paid_out`:  the payment has been included in a [payout](#core-endpoints-payouts)</li>
         *
         * <li>`cancelled`: the payment has been cancelled</li>
         * <li>`pending_customer_approval`: we're
         * waiting for the customer to approve this payment</li>
         * <li>`customer_approval_denied`: the
         * customer has denied approval for the payment. You should contact the customer directly</li></ul>
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

        private PaymentListRequest(HttpClient httpClient, ListRequestExecutor<S, Payment> executor) {
            super(httpClient, executor);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
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
            return "/payments";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected TypeToken<List<Payment>> getTypeToken() {
            return new TypeToken<List<Payment>>() {};
        }

        public enum Status {
            @SerializedName("pending_customer_approval")
            PENDING_CUSTOMER_APPROVAL, @SerializedName("pending_submission")
            PENDING_SUBMISSION, @SerializedName("submitted")
            SUBMITTED, @SerializedName("confirmed")
            CONFIRMED, @SerializedName("failed")
            FAILED, @SerializedName("charged_back")
            CHARGED_BACK, @SerializedName("paid_out")
            PAID_OUT, @SerializedName("cancelled")
            CANCELLED, @SerializedName("customer_approval_denied")
            CUSTOMER_APPROVAL_DENIED;
            @Override
            public String toString() {
                return name().toLowerCase();
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

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payments/:identity";
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

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private PaymentUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payments/:identity";
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
     * Cancels the payment if it has not already been submitted to the banks. Any metadata supplied to
     * this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This will fail with
     * a `cancellation_failed` error unless the payment's status is `pending_submission`.
     */
    public static final class PaymentCancelRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
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

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payments/:identity/actions/cancel";
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
     * <a name="retry_failed"></a>Retries a failed payment if the underlying mandate is active. You will
     * receive a `resubmission_requested` webhook, but after that retrying the payment follows the same
     * process as its initial creation, so you will receive a `submitted` webhook, followed by a
     * `confirmed` or `failed` event. Any metadata supplied to this endpoint will be stored against the
     * payment submission event it causes.
     * 
     * This will return a `retry_failed` error if the payment
     * has not failed.
     * 
     * Payments can be retried up to 3 times.
     */
    public static final class PaymentRetryRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public PaymentRetryRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
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

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payments/:identity/actions/retry";
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
