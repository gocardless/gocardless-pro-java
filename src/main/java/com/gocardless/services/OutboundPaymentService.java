package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.OutboundPayment;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with outbound payment resources.
 *
 * Outbound Payments represent payments sent from [creditors](#core-endpoints-creditors).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) when the status of the outbound
 * payment [changes](#event-actions-outbound-payments).
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: Outbound Payments are currently in Beta and available only to a
 * limited list of organisations. If you are interested in using this feature, please stay tuned for
 * our public launch announcement. We are actively testing and refining our API to ensure it meets
 * your needs and provides the best experience.
 * </p>
 */
public class OutboundPaymentService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#outboundPayments() }.
     */
    public OutboundPaymentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
      * 
     */
    public OutboundPaymentCreateRequest create() {
        return new OutboundPaymentCreateRequest(httpClient);
    }

    /**
     * Creates an outbound payment to your verified business bank account as the recipient.
     */
    public OutboundPaymentWithdrawRequest withdraw() {
        return new OutboundPaymentWithdrawRequest(httpClient);
    }

    /**
     * Cancels an outbound payment. Only outbound payments with either `verifying`,
     * `pending_approval`, or `scheduled` status can be cancelled. Once an outbound payment is
     * `executing`, the money moving process has begun and cannot be reversed.
     */
    public OutboundPaymentCancelRequest cancel(String identity) {
        return new OutboundPaymentCancelRequest(httpClient, identity);
    }

    /**
     * Approves an outbound payment. Only outbound payments in the “pending_approval” state can be
     * approved.
     */
    public OutboundPaymentApproveRequest approve(String identity) {
        return new OutboundPaymentApproveRequest(httpClient, identity);
    }

    /**
     * Fetches an outbound_payment by ID
     */
    public OutboundPaymentGetRequest get(String identity) {
        return new OutboundPaymentGetRequest(httpClient, identity);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of outbound payments.
     */
    public OutboundPaymentListRequest<ListResponse<OutboundPayment>> list() {
        return new OutboundPaymentListRequest<>(httpClient,
                ListRequest.<OutboundPayment>pagingExecutor());
    }

    public OutboundPaymentListRequest<Iterable<OutboundPayment>> all() {
        return new OutboundPaymentListRequest<>(httpClient,
                ListRequest.<OutboundPayment>iteratingExecutor());
    }

    /**
     * Updates an outbound payment object. This accepts only the metadata parameter.
     */
    public OutboundPaymentUpdateRequest update(String identity) {
        return new OutboundPaymentUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link OutboundPaymentService#create }.
     *
     * 
     */
    public static final class OutboundPaymentCreateRequest
            extends IdempotentPostRequest<OutboundPayment> {
        private Integer amount;
        private String description;
        private String executionDate;
        private Links links;
        private Map<String, String> metadata;
        private String scheme;

        /**
         * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public OutboundPaymentCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * A human-readable description of the outbound payment
         */
        public OutboundPaymentCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * A future date on which the outbound payment should be sent. If not specified, the payment
         * will be sent as soon as possible.
         */
        public OutboundPaymentCreateRequest withExecutionDate(String executionDate) {
            this.executionDate = executionDate;
            return this;
        }

        public OutboundPaymentCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the creditor who sends the outbound payment.
         */
        public OutboundPaymentCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * ID of the customer bank account which receives the outbound payment.
         */
        public OutboundPaymentCreateRequest withLinksRecipientBankAccount(
                String recipientBankAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withRecipientBankAccount(recipientBankAccount);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Bank payment scheme to process the outbound payment. Currently only "faster_payments"
         * (GBP) is supported.
         */
        public OutboundPaymentCreateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public OutboundPaymentCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<OutboundPayment> handleConflict(HttpClient httpClient, String id) {
            OutboundPaymentGetRequest request = new OutboundPaymentGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private OutboundPaymentCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public OutboundPaymentCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payments";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String creditor;
            private String recipientBankAccount;

            /**
             * ID of the creditor who sends the outbound payment.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }

            /**
             * ID of the customer bank account which receives the outbound payment.
             */
            public Links withRecipientBankAccount(String recipientBankAccount) {
                this.recipientBankAccount = recipientBankAccount;
                return this;
            }
        }
    }

    /**
     * Request class for {@link OutboundPaymentService#withdraw }.
     *
     * Creates an outbound payment to your verified business bank account as the recipient.
     */
    public static final class OutboundPaymentWithdrawRequest extends PostRequest<OutboundPayment> {
        private Integer amount;
        private String description;
        private String executionDate;
        private Links links;
        private Map<String, String> metadata;
        private String scheme;

        /**
         * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
         */
        public OutboundPaymentWithdrawRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * A human-readable description of the outbound payment
         */
        public OutboundPaymentWithdrawRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * A future date on which the outbound payment should be sent. If not specified, the payment
         * will be sent as soon as possible.
         */
        public OutboundPaymentWithdrawRequest withExecutionDate(String executionDate) {
            this.executionDate = executionDate;
            return this;
        }

        public OutboundPaymentWithdrawRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the creditor who sends the outbound payment.
         */
        public OutboundPaymentWithdrawRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentWithdrawRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentWithdrawRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Bank payment scheme to process the outbound payment. Currently only "faster_payments"
         * (GBP) is supported.
         */
        public OutboundPaymentWithdrawRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        private OutboundPaymentWithdrawRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public OutboundPaymentWithdrawRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payments/withdrawal";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }

        public static class Links {
            private String creditor;

            /**
             * ID of the creditor who sends the outbound payment.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link OutboundPaymentService#cancel }.
     *
     * Cancels an outbound payment. Only outbound payments with either `verifying`,
     * `pending_approval`, or `scheduled` status can be cancelled. Once an outbound payment is
     * `executing`, the money moving process has begun and cannot be reversed.
     */
    public static final class OutboundPaymentCancelRequest extends PostRequest<OutboundPayment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentCancelRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private OutboundPaymentCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public OutboundPaymentCancelRequest withHeader(String headerName, String headerValue) {
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
            return "outbound_payments/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
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
     * Request class for {@link OutboundPaymentService#approve }.
     *
     * Approves an outbound payment. Only outbound payments in the “pending_approval” state can be
     * approved.
     */
    public static final class OutboundPaymentApproveRequest extends PostRequest<OutboundPayment> {
        @PathParam
        private final String identity;

        private OutboundPaymentApproveRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public OutboundPaymentApproveRequest withHeader(String headerName, String headerValue) {
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
            return "outbound_payments/:identity/actions/approve";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
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
     * Request class for {@link OutboundPaymentService#get }.
     *
     * Fetches an outbound_payment by ID
     */
    public static final class OutboundPaymentGetRequest extends GetRequest<OutboundPayment> {
        @PathParam
        private final String identity;

        private OutboundPaymentGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public OutboundPaymentGetRequest withHeader(String headerName, String headerValue) {
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
            return "outbound_payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
        }
    }

    /**
     * Request class for {@link OutboundPaymentService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of outbound payments.
     */
    public static final class OutboundPaymentListRequest<S>
            extends ListRequest<S, OutboundPayment> {
        private String createdFrom;
        private String createdTo;
        private Status status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public OutboundPaymentListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public OutboundPaymentListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * The beginning of query period
         */
        public OutboundPaymentListRequest<S> withCreatedFrom(String createdFrom) {
            this.createdFrom = createdFrom;
            return this;
        }

        /**
         * The end of query period
         */
        public OutboundPaymentListRequest<S> withCreatedTo(String createdTo) {
            this.createdTo = createdTo;
            return this;
        }

        /**
         * Number of records to return.
         */
        public OutboundPaymentListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * One of:
         * <ul>
         * <li>`verifying`: The payment has been
         * [created](outbound-payments-create-an-outbound-payment) and the verification process has
         * begun.</li>
         * <li>`pending_approval`: The payment is awaiting
         * [approval](#outbound-payments-approve-an-outbound-payment).</li>
         * <li>`scheduled`: The payment has passed verification &
         * [approval](#outbound-payments-approve-an-outbound-payment), but processing has not yet
         * begun.</li>
         * <li>`executing`: The execution date has arrived and the payment has been placed in queue
         * for processing.</li>
         * <li>`executed`: The payment has been accepted by the scheme and is now on its way to the
         * recipient.</li>
         * <li>`cancelled`: The payment has been
         * [cancelled](#outbound-payments-cancel-an-outbound-payment) or was not
         * [approved](#outbound-payments-approve-an-outbound-payment) on time.</li>
         * <li>`failed`: The payment was not sent, usually due to an error while or after
         * executing.</li>
         * </ul>
         */
        public OutboundPaymentListRequest<S> withStatus(Status status) {
            this.status = status;
            return this;
        }

        private OutboundPaymentListRequest(HttpClient httpClient,
                ListRequestExecutor<S, OutboundPayment> executor) {
            super(httpClient, executor);
        }

        public OutboundPaymentListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (createdFrom != null) {
                params.put("created_from", createdFrom);
            }
            if (createdTo != null) {
                params.put("created_to", createdTo);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payments";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected TypeToken<List<OutboundPayment>> getTypeToken() {
            return new TypeToken<List<OutboundPayment>>() {};
        }

        public enum Status {
            @SerializedName("verifying")
            VERIFYING, @SerializedName("pending_approval")
            PENDING_APPROVAL, @SerializedName("scheduled")
            SCHEDULED, @SerializedName("executing")
            EXECUTING, @SerializedName("executed")
            EXECUTED, @SerializedName("cancelled")
            CANCELLED, @SerializedName("failed")
            FAILED, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link OutboundPaymentService#update }.
     *
     * Updates an outbound payment object. This accepts only the metadata parameter.
     */
    public static final class OutboundPaymentUpdateRequest extends PutRequest<OutboundPayment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public OutboundPaymentUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private OutboundPaymentUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public OutboundPaymentUpdateRequest withHeader(String headerName, String headerValue) {
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
            return "outbound_payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payments";
        }

        @Override
        protected Class<OutboundPayment> getResponseClass() {
            return OutboundPayment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
