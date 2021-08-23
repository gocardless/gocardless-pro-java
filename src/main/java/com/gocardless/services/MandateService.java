package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Mandate;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with mandate resources.
 *
 * Mandates represent the Direct Debit mandate with a [customer](#core-endpoints-customers).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) whenever the status of a mandate
 * changes.
 */
public class MandateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#mandates() }.
     */
    public MandateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new mandate object.
     */
    public MandateCreateRequest create() {
        return new MandateCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your mandates.
     */
    public MandateListRequest<ListResponse<Mandate>> list() {
        return new MandateListRequest<>(httpClient, ListRequest.<Mandate>pagingExecutor());
    }

    public MandateListRequest<Iterable<Mandate>> all() {
        return new MandateListRequest<>(httpClient, ListRequest.<Mandate>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing mandate.
     */
    public MandateGetRequest get(String identity) {
        return new MandateGetRequest(httpClient, identity);
    }

    /**
     * Updates a mandate object. This accepts only the metadata parameter.
     */
    public MandateUpdateRequest update(String identity) {
        return new MandateUpdateRequest(httpClient, identity);
    }

    /**
     * Immediately cancels a mandate and all associated cancellable payments. Any metadata supplied
     * to this endpoint will be stored on the mandate cancellation event it causes.
     * 
     * This will fail with a `cancellation_failed` error if the mandate is already cancelled.
     */
    public MandateCancelRequest cancel(String identity) {
        return new MandateCancelRequest(httpClient, identity);
    }

    /**
     * <a name="mandate_not_inactive"></a>Reinstates a cancelled or expired mandate to the banks.
     * You will receive a `resubmission_requested` webhook, but after that reinstating the mandate
     * follows the same process as its initial creation, so you will receive a `submitted` webhook,
     * followed by a `reinstated` or `failed` webhook up to two working days later. Any metadata
     * supplied to this endpoint will be stored on the `resubmission_requested` event it causes.
     * 
     * This will fail with a `mandate_not_inactive` error if the mandate is already being submitted,
     * or is active.
     * 
     * Mandates can be resubmitted up to 10 times.
     */
    public MandateReinstateRequest reinstate(String identity) {
        return new MandateReinstateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link MandateService#create }.
     *
     * Creates a new mandate object.
     */
    public static final class MandateCreateRequest extends IdempotentPostRequest<Mandate> {
        private Links links;
        private Map<String, String> metadata;
        private String payerIpAddress;
        private String reference;
        private String scheme;

        public MandateCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors). Only required if your account
         * manages multiple creditors.
         */
        public MandateCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * ID of the associated [customer bank account](#core-endpoints-customer-bank-accounts)
         * which the mandate is created and submits payments against.
         */
        public MandateCreateRequest withLinksCustomerBankAccount(String customerBankAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomerBankAccount(customerBankAccount);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * For ACH customers only. Required for ACH customers. A string containing the IP address of
         * the payer to whom the mandate belongs (i.e. as a result of their completion of a mandate
         * setup flow in their browser).
         */
        public MandateCreateRequest withPayerIpAddress(String payerIpAddress) {
            this.payerIpAddress = payerIpAddress;
            return this;
        }

        /**
         * Unique reference. Different schemes have different length and [character
         * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
         * satisfying the different scheme requirements if this field is left blank.
         */
        public MandateCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        /**
         * <a name="mandates_scheme"></a>Direct Debit scheme to which this mandate and associated
         * payments are submitted. Can be supplied or automatically detected from the customer's
         * bank account.
         */
        public MandateCreateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public MandateCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Mandate> handleConflict(HttpClient httpClient, String id) {
            MandateGetRequest request = new MandateGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private MandateCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public MandateCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "mandates";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String creditor;
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
             * ID of the associated [customer bank account](#core-endpoints-customer-bank-accounts)
             * which the mandate is created and submits payments against.
             */
            public Links withCustomerBankAccount(String customerBankAccount) {
                this.customerBankAccount = customerBankAccount;
                return this;
            }
        }
    }

    /**
     * Request class for {@link MandateService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your mandates.
     */
    public static final class MandateListRequest<S> extends ListRequest<S, Mandate> {
        private CreatedAt createdAt;
        private String creditor;
        private String customer;
        private String customerBankAccount;
        private String reference;
        private List<String> scheme;
        private List<Status> status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public MandateListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public MandateListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public MandateListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public MandateListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public MandateListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public MandateListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public MandateListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * ID of a [creditor](#core-endpoints-creditors). If specified, this endpoint will return
         * all mandates for the given creditor. Cannot be used in conjunction with `customer` or
         * `customer_bank_account`
         */
        public MandateListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * ID of a [customer](#core-endpoints-customers). If specified, this endpoint will return
         * all mandates for the given customer. Cannot be used in conjunction with
         * `customer_bank_account` or `creditor`
         */
        public MandateListRequest<S> withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * ID of a [customer bank account](#core-endpoints-customer-bank-accounts). If specified,
         * this endpoint will return all mandates for the given bank account. Cannot be used in
         * conjunction with `customer` or `creditor`
         */
        public MandateListRequest<S> withCustomerBankAccount(String customerBankAccount) {
            this.customerBankAccount = customerBankAccount;
            return this;
        }

        /**
         * Number of records to return.
         */
        public MandateListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique reference. Different schemes have different length and [character
         * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
         * satisfying the different scheme requirements if this field is left blank.
         */
        public MandateListRequest<S> withReference(String reference) {
            this.reference = reference;
            return this;
        }

        /**
         * Scheme you'd like to retrieve mandates for
         */
        public MandateListRequest<S> withScheme(List<String> scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * Scheme you'd like to retrieve mandates for
         */
        public MandateListRequest<S> withScheme(String scheme) {
            if (this.scheme == null) {
                this.scheme = new ArrayList<>();
            }
            this.scheme.add(scheme);
            return this;
        }

        /**
         * At most four valid status values
         */
        public MandateListRequest<S> withStatus(List<Status> status) {
            this.status = status;
            return this;
        }

        /**
         * At most four valid status values
         */
        public MandateListRequest<S> withStatus(Status status) {
            if (this.status == null) {
                this.status = new ArrayList<>();
            }
            this.status.add(status);
            return this;
        }

        private MandateListRequest(HttpClient httpClient,
                ListRequestExecutor<S, Mandate> executor) {
            super(httpClient, executor);
        }

        public MandateListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
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
            if (customerBankAccount != null) {
                params.put("customer_bank_account", customerBankAccount);
            }
            if (reference != null) {
                params.put("reference", reference);
            }
            if (scheme != null) {
                params.put("scheme", Joiner.on(",").join(scheme));
            }
            if (status != null) {
                params.put("status", Joiner.on(",").join(status));
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "mandates";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected TypeToken<List<Mandate>> getTypeToken() {
            return new TypeToken<List<Mandate>>() {};
        }

        public enum Status {
            @SerializedName("pending_customer_approval")
            PENDING_CUSTOMER_APPROVAL, @SerializedName("pending_submission")
            PENDING_SUBMISSION, @SerializedName("submitted")
            SUBMITTED, @SerializedName("active")
            ACTIVE, @SerializedName("failed")
            FAILED, @SerializedName("cancelled")
            CANCELLED, @SerializedName("expired")
            EXPIRED, @SerializedName("consumed")
            CONSUMED, @SerializedName("blocked")
            BLOCKED, @SerializedName("unknown")
            UNKNOWN;

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
     * Request class for {@link MandateService#get }.
     *
     * Retrieves the details of an existing mandate.
     */
    public static final class MandateGetRequest extends GetRequest<Mandate> {
        @PathParam
        private final String identity;

        private MandateGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateGetRequest withHeader(String headerName, String headerValue) {
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
            return "mandates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }
    }

    /**
     * Request class for {@link MandateService#update }.
     *
     * Updates a mandate object. This accepts only the metadata parameter.
     */
    public static final class MandateUpdateRequest extends PutRequest<Mandate> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private MandateUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateUpdateRequest withHeader(String headerName, String headerValue) {
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
            return "mandates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link MandateService#cancel }.
     *
     * Immediately cancels a mandate and all associated cancellable payments. Any metadata supplied
     * to this endpoint will be stored on the mandate cancellation event it causes.
     * 
     * This will fail with a `cancellation_failed` error if the mandate is already cancelled.
     */
    public static final class MandateCancelRequest extends PostRequest<Mandate> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateCancelRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private MandateCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateCancelRequest withHeader(String headerName, String headerValue) {
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
            return "mandates/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
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
     * Request class for {@link MandateService#reinstate }.
     *
     * <a name="mandate_not_inactive"></a>Reinstates a cancelled or expired mandate to the banks.
     * You will receive a `resubmission_requested` webhook, but after that reinstating the mandate
     * follows the same process as its initial creation, so you will receive a `submitted` webhook,
     * followed by a `reinstated` or `failed` webhook up to two working days later. Any metadata
     * supplied to this endpoint will be stored on the `resubmission_requested` event it causes.
     * 
     * This will fail with a `mandate_not_inactive` error if the mandate is already being submitted,
     * or is active.
     * 
     * Mandates can be resubmitted up to 10 times.
     */
    public static final class MandateReinstateRequest extends PostRequest<Mandate> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateReinstateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public MandateReinstateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private MandateReinstateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateReinstateRequest withHeader(String headerName, String headerValue) {
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
            return "mandates/:identity/actions/reinstate";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
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
