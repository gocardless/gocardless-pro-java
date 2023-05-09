package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.NegativeBalanceLimit;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with negative balance limit resources.
 *
 * The negative balance limit for a creditor. If the creditor would exceed this limit, we will not
 * allow the creation of refunds.
 */
public class NegativeBalanceLimitService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#negativeBalanceLimits() }.
     */
    public NegativeBalanceLimitService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of negative balance limits.
     */
    public NegativeBalanceLimitListRequest<ListResponse<NegativeBalanceLimit>> list() {
        return new NegativeBalanceLimitListRequest<>(httpClient,
                ListRequest.<NegativeBalanceLimit>pagingExecutor());
    }

    public NegativeBalanceLimitListRequest<Iterable<NegativeBalanceLimit>> all() {
        return new NegativeBalanceLimitListRequest<>(httpClient,
                ListRequest.<NegativeBalanceLimit>iteratingExecutor());
    }

    /**
     * Creates a new negative balance limit, which also deactivates the existing limit (if present)
     * for that currency and creditor combination.
     */
    public NegativeBalanceLimitCreateRequest create() {
        return new NegativeBalanceLimitCreateRequest(httpClient);
    }

    /**
     * Request class for {@link NegativeBalanceLimitService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of negative balance limits.
     */
    public static final class NegativeBalanceLimitListRequest<S>
            extends ListRequest<S, NegativeBalanceLimit> {
        private Active active;
        private CreatedAt createdAt;
        private String creditor;
        private Currency currency;

        /**
         * Whether or not this limit is currently active
         */
        public NegativeBalanceLimitListRequest<S> withActive(Active active) {
            this.active = active;
            return this;
        }

        /**
         * Cursor pointing to the start of the desired set.
         */
        public NegativeBalanceLimitListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public NegativeBalanceLimitListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public NegativeBalanceLimitListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public NegativeBalanceLimitListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public NegativeBalanceLimitListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public NegativeBalanceLimitListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public NegativeBalanceLimitListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Unique identifier, beginning with "CR".
         */
        public NegativeBalanceLimitListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public NegativeBalanceLimitListRequest<S> withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Number of records to return.
         */
        public NegativeBalanceLimitListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private NegativeBalanceLimitListRequest(HttpClient httpClient,
                ListRequestExecutor<S, NegativeBalanceLimit> executor) {
            super(httpClient, executor);
        }

        public NegativeBalanceLimitListRequest<S> withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (active != null) {
                params.put("active", active);
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
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "negative_balance_limits";
        }

        @Override
        protected String getEnvelope() {
            return "negative_balance_limits";
        }

        @Override
        protected TypeToken<List<NegativeBalanceLimit>> getTypeToken() {
            return new TypeToken<List<NegativeBalanceLimit>>() {};
        }

        public enum Active {
            @SerializedName("true")
            TRUE, @SerializedName("false")
            FALSE, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
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
     * Request class for {@link NegativeBalanceLimitService#create }.
     *
     * Creates a new negative balance limit, which also deactivates the existing limit (if present)
     * for that currency and creditor combination.
     */
    public static final class NegativeBalanceLimitCreateRequest
            extends PostRequest<NegativeBalanceLimit> {
        private Integer balanceLimit;
        private Currency currency;
        private Links links;
        private String reason;

        /**
         * The limit amount in pence (e.g. 10000 for a -100 GBP limit).
         */
        public NegativeBalanceLimitCreateRequest withBalanceLimit(Integer balanceLimit) {
            this.balanceLimit = balanceLimit;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public NegativeBalanceLimitCreateRequest withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public NegativeBalanceLimitCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [creditor](#core-endpoints-creditors) this limit relates to
         */
        public NegativeBalanceLimitCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * the reason this limit was created
         */
        public NegativeBalanceLimitCreateRequest withReason(String reason) {
            this.reason = reason;
            return this;
        }

        private NegativeBalanceLimitCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public NegativeBalanceLimitCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "negative_balance_limits";
        }

        @Override
        protected String getEnvelope() {
            return "negative_balance_limits";
        }

        @Override
        protected Class<NegativeBalanceLimit> getResponseClass() {
            return NegativeBalanceLimit.class;
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
            private String creditor;

            /**
             * ID of the [creditor](#core-endpoints-creditors) this limit relates to
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }
}
