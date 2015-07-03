package com.gocardless.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Refund;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with refund resources.
 *
 * Refund objects represent (partial) refunds of a [payment](#core-endpoints-payment) back to the
 * [customer](#core-endpoints-customers).
 * 
 * GoCardless will notify you via a [webhook](#webhooks)
 * whenever a refund is created, and will update the `amount_refunded` property of the payment.
 * 
 *
 * _Note:_ A payment that has been (partially) refunded can still receive a late failure or
 * chargeback from the banks.
 */
public class RefundService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#refunds() }.
     */
    public RefundService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new refund object.
     * 
     * This fails with:<a name="refund_payment_invalid_state"></a><a
     * name="total_amount_confirmation_invalid"></a><a name="number_of_refunds_exceeded"></a>
     * 
     * -
     * `refund_payment_invalid_state` error if the linked [payment](#core-endpoints-payments) isn't
     * either `confirmed` or `paid_out`.
     * 
     * - `total_amount_confirmation_invalid` if the confirmation
     * amount doesn't match the total amount refunded for the payment. This safeguard is there to prevent
     * two processes from creating refunds without awareness of each other.
     * 
     * -
     * `number_of_refunds_exceeded` if five or more refunds have already been created against the
     * payment.
     * 
     */
    public RefundCreateRequest create() {
        return new RefundCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your refunds.
     */
    public RefundListRequest<ListResponse<Refund>> list() {
        return new RefundListRequest<>(httpClient, ListRequest.<Refund>pagingExecutor());
    }

    public RefundListRequest<Iterable<Refund>> all() {
        return new RefundListRequest<>(httpClient, ListRequest.<Refund>iteratingExecutor());
    }

    /**
     * Retrieves all details for a single refund
     */
    public RefundGetRequest get(String identity) {
        return new RefundGetRequest(httpClient, identity);
    }

    /**
     * Updates a refund object.
     */
    public RefundUpdateRequest update(String identity) {
        return new RefundUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RefundService#create }.
     *
     * Creates a new refund object.
     * 
     * This fails with:<a name="refund_payment_invalid_state"></a><a
     * name="total_amount_confirmation_invalid"></a><a name="number_of_refunds_exceeded"></a>
     * 
     * -
     * `refund_payment_invalid_state` error if the linked [payment](#core-endpoints-payments) isn't
     * either `confirmed` or `paid_out`.
     * 
     * - `total_amount_confirmation_invalid` if the confirmation
     * amount doesn't match the total amount refunded for the payment. This safeguard is there to prevent
     * two processes from creating refunds without awareness of each other.
     * 
     * -
     * `number_of_refunds_exceeded` if five or more refunds have already been created against the
     * payment.
     * 
     */
    public static final class RefundCreateRequest extends PostRequest<Refund> {
        private Integer amount;
        private Links links;
        private Map<String, String> metadata;
        private Integer totalAmountConfirmation;

        /**
         * Amount in pence or cents.
         */
        public RefundCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public RefundCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [payment](#core-endpoints-payments) against which the refund is being made.
         */
        public RefundCreateRequest withLinksPayment(String payment) {
            if (links == null) {
                links = new Links();
            }
            links.withPayment(payment);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public RefundCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public RefundCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Total expected refunded amount in pence or cents. If there are other partial refunds against this
         * payment, this value should be the sum of the existing refunds plus the amount of the refund being
         * created.
         */
        public RefundCreateRequest withTotalAmountConfirmation(Integer totalAmountConfirmation) {
            this.totalAmountConfirmation = totalAmountConfirmation;
            return this;
        }

        private RefundCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String payment;

            /**
             * ID of the [payment](#core-endpoints-payments) against which the refund is being made.
             */
            public Links withPayment(String payment) {
                this.payment = payment;
                return this;
            }
        }
    }

    /**
     * Request class for {@link RefundService#list }.
     *
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your refunds.
     */
    public static final class RefundListRequest<S> extends ListRequest<S, Refund> {
        private String payment;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public RefundListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public RefundListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public RefundListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "PM".
         */
        public RefundListRequest<S> withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        private RefundListRequest(HttpClient httpClient, ListRequestExecutor<S, Refund> executor) {
            super(httpClient, executor);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (payment != null) {
                params.put("payment", payment);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected TypeToken<List<Refund>> getTypeToken() {
            return new TypeToken<List<Refund>>() {};
        }
    }

    /**
     * Request class for {@link RefundService#get }.
     *
     * Retrieves all details for a single refund
     */
    public static final class RefundGetRequest extends GetRequest<Refund> {
        @PathParam
        private final String identity;

        private RefundGetRequest(HttpClient httpClient, String identity) {
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
            return "/refunds/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }
    }

    /**
     * Request class for {@link RefundService#update }.
     *
     * Updates a refund object.
     */
    public static final class RefundUpdateRequest extends PutRequest<Refund> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public RefundUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public RefundUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private RefundUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/refunds/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
