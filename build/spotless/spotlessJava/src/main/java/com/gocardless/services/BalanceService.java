package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Balance;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with balance resources.
 *
 * Returns the balances for a creditor. These balances are the same as what’s shown in the dashboard
 * with one exception (mentioned below under balance_type).
 * 
 * These balances will typically be 3-5 minutes old. The balance amounts likely won’t match what’s
 * shown in the dashboard as the dashboard balances are updated much less frequently (once per day).
 */
public class BalanceService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#balances() }.
     */
    public BalanceService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of balances for a given
     * creditor. This endpoint is rate limited to 60 requests per minute.
     */
    public BalanceListRequest<ListResponse<Balance>> list() {
        return new BalanceListRequest<>(httpClient, ListRequest.<Balance>pagingExecutor());
    }

    public BalanceListRequest<Iterable<Balance>> all() {
        return new BalanceListRequest<>(httpClient, ListRequest.<Balance>iteratingExecutor());
    }

    /**
     * Request class for {@link BalanceService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of balances for a given
     * creditor. This endpoint is rate limited to 60 requests per minute.
     */
    public static final class BalanceListRequest<S> extends ListRequest<S, Balance> {
        private String creditor;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public BalanceListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public BalanceListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * ID of a [creditor](#core-endpoints-creditors).
         */
        public BalanceListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Number of records to return.
         */
        public BalanceListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private BalanceListRequest(HttpClient httpClient,
                ListRequestExecutor<S, Balance> executor) {
            super(httpClient, executor);
        }

        public BalanceListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "balances";
        }

        @Override
        protected String getEnvelope() {
            return "balances";
        }

        @Override
        protected TypeToken<List<Balance>> getTypeToken() {
            return new TypeToken<List<Balance>>() {};
        }
    }
}
