package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.OutboundPaymentImport;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with outbound payment import resources.
 *
 * Outbound Payment Imports allow you to create multiple payments via a single API call.
 * 
 * The Workflow: 1. Create the outbound payment import. 2. Retrieve an authorisation link from the
 * response. 3. Redirect the user to the link to authorise the import. 4. Once the user authorises
 * the import, the individual outbound payments are automatically submitted.
 * 
 * Import entries are not processed as actual payments until they are reviewed and authorised in
 * GoCardless Dashboard. Upon approval, a unique outbound payment is generated for every entry in
 * the import.
 * 
 * <p class="notice">
 * Outbound Payment Imports are capped at 1000 entries. If you expect to exceed this limit, please
 * create multiple smaller imports.
 * </p>
 */
public class OutboundPaymentImportService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#outboundPaymentImports() }.
     */
    public OutboundPaymentImportService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
      * 
     */
    public OutboundPaymentImportCreateRequest create() {
        return new OutboundPaymentImportCreateRequest(httpClient);
    }

    /**
     * Returns a single outbound payment import.
     */
    public OutboundPaymentImportGetRequest get(String identity) {
        return new OutboundPaymentImportGetRequest(httpClient, identity);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your outbound payment
     * imports.
     */
    public OutboundPaymentImportListRequest<ListResponse<OutboundPaymentImport>> list() {
        return new OutboundPaymentImportListRequest<>(httpClient,
                ListRequest.<OutboundPaymentImport>pagingExecutor());
    }

    public OutboundPaymentImportListRequest<Iterable<OutboundPaymentImport>> all() {
        return new OutboundPaymentImportListRequest<>(httpClient,
                ListRequest.<OutboundPaymentImport>iteratingExecutor());
    }

    /**
     * Request class for {@link OutboundPaymentImportService#create }.
     *
     * 
     */
    public static final class OutboundPaymentImportCreateRequest
            extends IdempotentPostRequest<OutboundPaymentImport> {
        private List<EntryItems> entryItems;
        private Links links;

        public OutboundPaymentImportCreateRequest withEntryItems(List<EntryItems> entryItems) {
            this.entryItems = entryItems;
            return this;
        }

        public OutboundPaymentImportCreateRequest withEntryItems(EntryItems entryItems) {
            if (this.entryItems == null) {
                this.entryItems = new ArrayList<>();
            }
            this.entryItems.add(entryItems);
            return this;
        }

        public OutboundPaymentImportCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the creditor who sends the outbound payments from the import.
         */
        public OutboundPaymentImportCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        public OutboundPaymentImportCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<OutboundPaymentImport> handleConflict(HttpClient httpClient,
                String id) {
            OutboundPaymentImportGetRequest request =
                    new OutboundPaymentImportGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private OutboundPaymentImportCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public OutboundPaymentImportCreateRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payment_imports";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payment_imports";
        }

        @Override
        protected Class<OutboundPaymentImport> getResponseClass() {
            return OutboundPaymentImport.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class EntryItems {
            private Integer amount;
            private Map<String, String> metadata;
            private String recipientBankAccountId;
            private String reference;
            private Scheme scheme;

            /**
             * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in
             * EUR).
             */
            public EntryItems withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public EntryItems withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * ID of the customer bank account which receives the outbound payment.
             */
            public EntryItems withRecipientBankAccountId(String recipientBankAccountId) {
                this.recipientBankAccountId = recipientBankAccountId;
                return this;
            }

            /**
             * An optional reference that will appear on your customer's bank statement. The
             * character limit for this reference is dependent on the scheme.<br />
             * <strong>Faster Payments</strong> - 18 characters, including:
             * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 &-./"<br />
             */
            public EntryItems withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * Bank payment scheme to process the outbound payment. Currently only "faster_payments"
             * (GBP) is supported.
             */
            public EntryItems withScheme(Scheme scheme) {
                this.scheme = scheme;
                return this;
            }

            public enum Scheme {
                @SerializedName("faster_payments")
                FASTER_PAYMENTS, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }

        public static class Links {
            private String creditor;

            /**
             * ID of the creditor who sends the outbound payments from the import.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link OutboundPaymentImportService#get }.
     *
     * Returns a single outbound payment import.
     */
    public static final class OutboundPaymentImportGetRequest
            extends GetRequest<OutboundPaymentImport> {
        @PathParam
        private final String identity;

        private OutboundPaymentImportGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public OutboundPaymentImportGetRequest withHeader(String headerName, String headerValue) {
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
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payment_imports/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payment_imports";
        }

        @Override
        protected Class<OutboundPaymentImport> getResponseClass() {
            return OutboundPaymentImport.class;
        }
    }

    /**
     * Request class for {@link OutboundPaymentImportService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your outbound payment
     * imports.
     */
    public static final class OutboundPaymentImportListRequest<S>
            extends ListRequest<S, OutboundPaymentImport> {
        private CreatedAt createdAt;
        private Status status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public OutboundPaymentImportListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public OutboundPaymentImportListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public OutboundPaymentImportListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public OutboundPaymentImportListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public OutboundPaymentImportListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public OutboundPaymentImportListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public OutboundPaymentImportListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Number of records to return.
         */
        public OutboundPaymentImportListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * The status of the outbound payment import.
         * <ul>
         * <li>`created`: The initial state of a new import.</li>
         * <li>`validating`: Import validation in progress.</li>
         * <li>`invalid`: Import validation failed.</li>
         * <li>`valid`: Import validation succeeded.</li>
         * <li>`processing`: Authorisation received; payments are being generated.</li>
         * <li>`processed`: All entries have been successfully converted into outbound
         * payments.</li>
         * <li>`cancelled`: The import was cancelled by a user or automatically expired by the
         * system.</li>
         * </ul>
         */
        public OutboundPaymentImportListRequest<S> withStatus(Status status) {
            this.status = status;
            return this;
        }

        private OutboundPaymentImportListRequest(HttpClient httpClient,
                ListRequestExecutor<S, OutboundPaymentImport> executor) {
            super(httpClient, executor);
        }

        public OutboundPaymentImportListRequest<S> withHeader(String headerName,
                String headerValue) {
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
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payment_imports";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payment_imports";
        }

        @Override
        protected TypeToken<List<OutboundPaymentImport>> getTypeToken() {
            return new TypeToken<List<OutboundPaymentImport>>() {};
        }

        public enum Status {
            @SerializedName("created")
            CREATED, @SerializedName("validating")
            VALIDATING, @SerializedName("valid")
            VALID, @SerializedName("invalid")
            INVALID, @SerializedName("processing")
            PROCESSING, @SerializedName("processed")
            PROCESSED, @SerializedName("cancelled")
            CANCELLED, @SerializedName("unknown")
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
}
