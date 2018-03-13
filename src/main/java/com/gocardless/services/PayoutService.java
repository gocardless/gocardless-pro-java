package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Payout;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with payout resources.
 *
 * Payouts represent transfers from GoCardless to a [creditor](#core-endpoints-creditors). Each
 * payout contains the funds collected from one or many [payments](#core-endpoints-payments). Payouts
 * are created automatically after a payment has been successfully collected.
 */
public class PayoutService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#payouts() }.
     */
    public PayoutService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payouts.
     */
    public PayoutListRequest<ListResponse<Payout>> list() {
        return new PayoutListRequest<>(httpClient, ListRequest.<Payout>pagingExecutor());
    }

    public PayoutListRequest<Iterable<Payout>> all() {
        return new PayoutListRequest<>(httpClient, ListRequest.<Payout>iteratingExecutor());
    }

    /**
     * Retrieves the details of a single payout. For an example of how to reconcile the transactions in a
     * payout, see [this guide](#events-reconciling-payouts-with-events).
     */
    public PayoutGetRequest get(String identity) {
        return new PayoutGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link PayoutService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payouts.
     */
    public static final class PayoutListRequest<S> extends ListRequest<S, Payout> {
        private CreatedAt createdAt;
        private String creditor;
        private String creditorBankAccount;
        private Currency currency;
        private PayoutType payoutType;
        private String reference;
        private Status status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PayoutListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PayoutListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public PayoutListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public PayoutListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public PayoutListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public PayoutListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public PayoutListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Unique identifier, beginning with "CR".
         */
        public PayoutListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Unique identifier, beginning with "BA".
         */
        public PayoutListRequest<S> withCreditorBankAccount(String creditorBankAccount) {
            this.creditorBankAccount = creditorBankAccount;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "GBP",
         * "EUR", "SEK" and "DKK" are supported.
         */
        public PayoutListRequest<S> withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PayoutListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Whether a payout contains merchant revenue or partner fees.
         */
        public PayoutListRequest<S> withPayoutType(PayoutType payoutType) {
            this.payoutType = payoutType;
            return this;
        }

        /**
         * Reference which appears on the creditor's bank statement.
         */
        public PayoutListRequest<S> withReference(String reference) {
            this.reference = reference;
            return this;
        }

        /**
         * One of:
         * <ul>
         * <li>`pending`: the payout has been created, but not yet sent to the banks</li>
         * <li>`paid`: the payout has been sent to the banks</li>
         * </ul>
         */
        public PayoutListRequest<S> withStatus(Status status) {
            this.status = status;
            return this;
        }

        private PayoutListRequest(HttpClient httpClient, ListRequestExecutor<S, Payout> executor) {
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
            if (creditorBankAccount != null) {
                params.put("creditor_bank_account", creditorBankAccount);
            }
            if (currency != null) {
                params.put("currency", currency);
            }
            if (payoutType != null) {
                params.put("payout_type", payoutType);
            }
            if (reference != null) {
                params.put("reference", reference);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "payouts";
        }

        @Override
        protected String getEnvelope() {
            return "payouts";
        }

        @Override
        protected TypeToken<List<Payout>> getTypeToken() {
            return new TypeToken<List<Payout>>() {};
        }

        public enum Currency {
            @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("SEK")
            SEK;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum PayoutType {
            @SerializedName("merchant")
            MERCHANT, @SerializedName("partner")
            PARTNER;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum Status {
            @SerializedName("pending")
            PENDING, @SerializedName("paid")
            PAID;
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
     * Request class for {@link PayoutService#get }.
     *
     * Retrieves the details of a single payout. For an example of how to reconcile the transactions in a
     * payout, see [this guide](#events-reconciling-payouts-with-events).
     */
    public static final class PayoutGetRequest extends GetRequest<Payout> {
        @PathParam
        private final String identity;

        private PayoutGetRequest(HttpClient httpClient, String identity) {
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
            return "payouts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payouts";
        }

        @Override
        protected Class<Payout> getResponseClass() {
            return Payout.class;
        }
    }
}
