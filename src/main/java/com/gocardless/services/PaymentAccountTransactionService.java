package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.PaymentAccountTransaction;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with payment account transaction resources.
 *
 * Payment account transactions represent movements of funds on a given payment account. The payment
 * account is provisioned by GoCardless and is used to fund [outbound
 * payments](#core-endpoints-outbound-payments).
 */
public class PaymentAccountTransactionService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#paymentAccountTransactions() }.
     */
    public PaymentAccountTransactionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * List transactions for a given payment account.
     */
    public PaymentAccountTransactionListRequest<ListResponse<PaymentAccountTransaction>> list(
            String identity) {
        return new PaymentAccountTransactionListRequest<>(httpClient,
                ListRequest.<PaymentAccountTransaction>pagingExecutor(), identity);
    }

    public PaymentAccountTransactionListRequest<Iterable<PaymentAccountTransaction>> all(
            String identity) {
        return new PaymentAccountTransactionListRequest<>(httpClient,
                ListRequest.<PaymentAccountTransaction>iteratingExecutor(), identity);
    }

    /**
     * Request class for {@link PaymentAccountTransactionService#list }.
     *
     * List transactions for a given payment account.
     */
    public static final class PaymentAccountTransactionListRequest<S>
            extends ListRequest<S, PaymentAccountTransaction> {
        @PathParam
        private final String identity;
        private Direction direction;
        private String valueDateFrom;
        private String valueDateTo;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PaymentAccountTransactionListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PaymentAccountTransactionListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * The direction of the transaction. Debits mean money leaving the account (e.g. outbound
         * payment), while credits signify money coming in (e.g. manual top-up).
         */
        public PaymentAccountTransactionListRequest<S> withDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PaymentAccountTransactionListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * The beginning of query period
         */
        public PaymentAccountTransactionListRequest<S> withValueDateFrom(String valueDateFrom) {
            this.valueDateFrom = valueDateFrom;
            return this;
        }

        /**
         * The end of query period
         */
        public PaymentAccountTransactionListRequest<S> withValueDateTo(String valueDateTo) {
            this.valueDateTo = valueDateTo;
            return this;
        }

        private PaymentAccountTransactionListRequest(HttpClient httpClient,
                ListRequestExecutor<S, PaymentAccountTransaction> executor, String identity) {
            super(httpClient, executor);
            this.identity = identity;
        }

        public PaymentAccountTransactionListRequest<S> withHeader(String headerName,
                String headerValue) {
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
            if (direction != null) {
                params.put("direction", direction);
            }
            if (valueDateFrom != null) {
                params.put("value_date_from", valueDateFrom);
            }
            if (valueDateTo != null) {
                params.put("value_date_to", valueDateTo);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "payment_accounts/:identity/transactions";
        }

        @Override
        protected String getEnvelope() {
            return "payment_account_transactions";
        }

        @Override
        protected TypeToken<List<PaymentAccountTransaction>> getTypeToken() {
            return new TypeToken<List<PaymentAccountTransaction>>() {};
        }

        public enum Direction {
            @SerializedName("credit")
            CREDIT, @SerializedName("debit")
            DEBIT, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }
}
