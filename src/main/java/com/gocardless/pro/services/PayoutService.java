package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Payout;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with Payout resources.
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
      {@link com.gocardless.pro.GoCardlessClient#payouts() }.
     */
    public PayoutService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your payouts.
     */
    public PayoutListRequest list() {
        return new PayoutListRequest(httpClient);
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
    public static final class PayoutListRequest extends ListRequest<Payout> {
        private String after;
        private String before;
        private String creditor;
        private String creditorBankAccount;
        private Integer limit;
        private Status status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PayoutListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PayoutListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        /**
         * Unique identifier, beginning with "CR".
         */
        public PayoutListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Unique identifier, beginning with "BA"
         */
        public PayoutListRequest withCreditorBankAccount(String creditorBankAccount) {
            this.creditorBankAccount = creditorBankAccount;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PayoutListRequest withLimit(Integer limit) {
            this.limit = limit;
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
        public PayoutListRequest withStatus(Status status) {
            this.status = status;
            return this;
        }

        private PayoutListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (creditorBankAccount != null) {
                params.put("creditor_bank_account", creditorBankAccount);
            }
            if (limit != null) {
                params.put("limit", limit);
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
            PENDING, PAID;
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
