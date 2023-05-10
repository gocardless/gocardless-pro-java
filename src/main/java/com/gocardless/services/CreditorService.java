package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Creditor;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with creditor resources.
 *
 * Each [payment](#core-endpoints-payments) taken through the API is linked to a "creditor", to whom
 * the payment is then paid out. In most cases your organisation will have a single "creditor", but
 * the API also supports collecting payments on behalf of others.
 * 
 * Currently, for Anti Money Laundering reasons, any creditors you add must be directly related to
 * your organisation.
 */
public class CreditorService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#creditors() }.
     */
    public CreditorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new creditor.
     */
    public CreditorCreateRequest create() {
        return new CreditorCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your creditors.
     */
    public CreditorListRequest<ListResponse<Creditor>> list() {
        return new CreditorListRequest<>(httpClient, ListRequest.<Creditor>pagingExecutor());
    }

    public CreditorListRequest<Iterable<Creditor>> all() {
        return new CreditorListRequest<>(httpClient, ListRequest.<Creditor>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing creditor.
     */
    public CreditorGetRequest get(String identity) {
        return new CreditorGetRequest(httpClient, identity);
    }

    /**
     * Updates a creditor object. Supports all of the fields supported when creating a creditor.
     */
    public CreditorUpdateRequest update(String identity) {
        return new CreditorUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CreditorService#create }.
     *
     * Creates a new creditor.
     */
    public static final class CreditorCreateRequest extends IdempotentPostRequest<Creditor> {
        private String bankReferencePrefix;
        private String countryCode;
        private CreditorType creditorType;
        private Map<String, String> links;
        private String name;

        /**
         * Prefix for the bank reference of payouts sent to this creditor. For instance, if the
         * creditor's `bank_reference_prefix` was `ACME`, the bank reference of a payout sent to
         * that creditor could be `ACME-8G7Q8`.
         * 
         * This prefix is also used for refunds in EUR and GBP.
         * 
         */
        public CreditorCreateRequest withBankReferencePrefix(String bankReferencePrefix) {
            this.bankReferencePrefix = bankReferencePrefix;
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public CreditorCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * The type of business of the creditor. Currently, `individual`, `company`, `charity`,
         * `partnership`, and `trust` are supported.
         */
        public CreditorCreateRequest withCreditorType(CreditorType creditorType) {
            this.creditorType = creditorType;
            return this;
        }

        public CreditorCreateRequest withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public CreditorCreateRequest withLinks(String key, String value) {
            if (links == null) {
                links = new HashMap<>();
            }
            links.put(key, value);
            return this;
        }

        /**
         * The creditor's trading name.
         */
        public CreditorCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        public CreditorCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Creditor> handleConflict(HttpClient httpClient, String id) {
            CreditorGetRequest request = new CreditorGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private CreditorCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public CreditorCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum CreditorType {
            @SerializedName("company")
            COMPANY, @SerializedName("individual")
            INDIVIDUAL, @SerializedName("charity")
            CHARITY, @SerializedName("partnership")
            PARTNERSHIP, @SerializedName("trust")
            TRUST, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link CreditorService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your creditors.
     */
    public static final class CreditorListRequest<S> extends ListRequest<S, Creditor> {
        private CreatedAt createdAt;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CreditorListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CreditorListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public CreditorListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public CreditorListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public CreditorListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public CreditorListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public CreditorListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Number of records to return.
         */
        public CreditorListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CreditorListRequest(HttpClient httpClient,
                ListRequestExecutor<S, Creditor> executor) {
            super(httpClient, executor);
        }

        public CreditorListRequest<S> withHeader(String headerName, String headerValue) {
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
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected TypeToken<List<Creditor>> getTypeToken() {
            return new TypeToken<List<Creditor>>() {};
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
     * Request class for {@link CreditorService#get }.
     *
     * Retrieves the details of an existing creditor.
     */
    public static final class CreditorGetRequest extends GetRequest<Creditor> {
        @PathParam
        private final String identity;

        private CreditorGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public CreditorGetRequest withHeader(String headerName, String headerValue) {
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
            return "creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }
    }

    /**
     * Request class for {@link CreditorService#update }.
     *
     * Updates a creditor object. Supports all of the fields supported when creating a creditor.
     */
    public static final class CreditorUpdateRequest extends PutRequest<Creditor> {
        @PathParam
        private final String identity;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String bankReferencePrefix;
        private String city;
        private String countryCode;
        private Links links;
        private String name;
        private String postalCode;
        private String region;

        /**
         * The first line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * Prefix for the bank reference of payouts sent to this creditor. For instance, if the
         * creditor's `bank_reference_prefix` was `ACME`, the bank reference of a payout sent to
         * that creditor could be `ACME-8G7Q8`.
         * 
         * This prefix is also used for refunds in EUR and GBP.
         * 
         */
        public CreditorUpdateRequest withBankReferencePrefix(String bankReferencePrefix) {
            this.bankReferencePrefix = bankReferencePrefix;
            return this;
        }

        /**
         * The city of the creditor's address.
         */
        public CreditorUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public CreditorUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorUpdateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in AUD.
         */
        public CreditorUpdateRequest withLinksDefaultAudPayoutAccount(
                String defaultAudPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultAudPayoutAccount(defaultAudPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in CAD.
         */
        public CreditorUpdateRequest withLinksDefaultCadPayoutAccount(
                String defaultCadPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultCadPayoutAccount(defaultCadPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in DKK.
         */
        public CreditorUpdateRequest withLinksDefaultDkkPayoutAccount(
                String defaultDkkPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultDkkPayoutAccount(defaultDkkPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in EUR.
         */
        public CreditorUpdateRequest withLinksDefaultEurPayoutAccount(
                String defaultEurPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultEurPayoutAccount(defaultEurPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in GBP.
         */
        public CreditorUpdateRequest withLinksDefaultGbpPayoutAccount(
                String defaultGbpPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultGbpPayoutAccount(defaultGbpPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in NZD.
         */
        public CreditorUpdateRequest withLinksDefaultNzdPayoutAccount(
                String defaultNzdPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultNzdPayoutAccount(defaultNzdPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in SEK.
         */
        public CreditorUpdateRequest withLinksDefaultSekPayoutAccount(
                String defaultSekPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultSekPayoutAccount(defaultSekPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in USD.
         */
        public CreditorUpdateRequest withLinksDefaultUsdPayoutAccount(
                String defaultUsdPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultUsdPayoutAccount(defaultUsdPayoutAccount);
            return this;
        }

        /**
         * The creditor's trading name.
         */
        public CreditorUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The creditor's postal code.
         */
        public CreditorUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The creditor's address region, county or department.
         */
        public CreditorUpdateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        private CreditorUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public CreditorUpdateRequest withHeader(String headerName, String headerValue) {
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
            return "creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String defaultAudPayoutAccount;
            private String defaultCadPayoutAccount;
            private String defaultDkkPayoutAccount;
            private String defaultEurPayoutAccount;
            private String defaultGbpPayoutAccount;
            private String defaultNzdPayoutAccount;
            private String defaultSekPayoutAccount;
            private String defaultUsdPayoutAccount;

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in AUD.
             */
            public Links withDefaultAudPayoutAccount(String defaultAudPayoutAccount) {
                this.defaultAudPayoutAccount = defaultAudPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in CAD.
             */
            public Links withDefaultCadPayoutAccount(String defaultCadPayoutAccount) {
                this.defaultCadPayoutAccount = defaultCadPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in DKK.
             */
            public Links withDefaultDkkPayoutAccount(String defaultDkkPayoutAccount) {
                this.defaultDkkPayoutAccount = defaultDkkPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in EUR.
             */
            public Links withDefaultEurPayoutAccount(String defaultEurPayoutAccount) {
                this.defaultEurPayoutAccount = defaultEurPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in GBP.
             */
            public Links withDefaultGbpPayoutAccount(String defaultGbpPayoutAccount) {
                this.defaultGbpPayoutAccount = defaultGbpPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in NZD.
             */
            public Links withDefaultNzdPayoutAccount(String defaultNzdPayoutAccount) {
                this.defaultNzdPayoutAccount = defaultNzdPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in SEK.
             */
            public Links withDefaultSekPayoutAccount(String defaultSekPayoutAccount) {
                this.defaultSekPayoutAccount = defaultSekPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
             * receive payouts in USD.
             */
            public Links withDefaultUsdPayoutAccount(String defaultUsdPayoutAccount) {
                this.defaultUsdPayoutAccount = defaultUsdPayoutAccount;
                return this;
            }
        }
    }
}
