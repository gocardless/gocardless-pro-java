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
 * Payouts represent transfers from GoCardless to a
 * [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors). Each payout contains
 * the funds collected from one or many
 * [payments](https://developer.gocardless.com/pro/#api-endpoints-payments). Payouts are created
 * automatically after a payment has been successfully collected.
 */
public class PayoutService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#payouts() }.
     */
    public PayoutService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your payouts.
     */
    public PayoutListRequest<ListResponse<Payout>> list() {
        return new PayoutListRequest<>(httpClient, ListRequest.<Payout>pagingExecutor());
    }

    public PayoutListRequest<Iterable<Payout>> all() {
        return new PayoutListRequest<>(httpClient, ListRequest.<Payout>iteratingExecutor());
    }

    /**
     * Retrieves the details of a single payout.
     */
    public PayoutGetRequest get(String identity) {
        return new PayoutGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link PayoutService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your payouts.
     */
    public static final class PayoutListRequest<S> extends ListRequest<S, Payout> {
        private String creditor;
        private String creditorBankAccount;
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

        /**
         * Unique identifier, beginning with "CR".
         */
        public PayoutListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Unique identifier, beginning with "BA"
         */
        public PayoutListRequest<S> withCreditorBankAccount(String creditorBankAccount) {
            this.creditorBankAccount = creditorBankAccount;
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
         * One of:
         * <ul>
         * <li>`pending`: the payout has been created, but not yet sent to the banks</li>
         *
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
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (creditorBankAccount != null) {
                params.put("creditor_bank_account", creditorBankAccount);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payouts";
        }

        @Override
        protected String getEnvelope() {
            return "payouts";
        }

        @Override
        protected TypeToken<List<Payout>> getTypeToken() {
            return new TypeToken<List<Payout>>() {};
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
    }

    /**
     * Request class for {@link PayoutService#get }.
     *
     * Retrieves the details of a single payout.
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
            return "/payouts/:identity";
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
