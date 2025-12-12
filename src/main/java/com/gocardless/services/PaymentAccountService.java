package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.PaymentAccount;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with payment account resources.
 *
 * Access the details of bank accounts provided for you by GoCardless that are used to fund
 * [Outbound Payments](#core-endpoints-outbound-payments).
 */
public class PaymentAccountService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#paymentAccounts() }.
     */
    public PaymentAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payment accounts.
     */
    public PaymentAccountListRequest<ListResponse<PaymentAccount>> list() {
        return new PaymentAccountListRequest<>(httpClient,
                ListRequest.<PaymentAccount>pagingExecutor());
    }

    public PaymentAccountListRequest<Iterable<PaymentAccount>> all() {
        return new PaymentAccountListRequest<>(httpClient,
                ListRequest.<PaymentAccount>iteratingExecutor());
    }

    /**
     * Request class for {@link PaymentAccountService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your payment accounts.
     */
    public static final class PaymentAccountListRequest<S> extends ListRequest<S, PaymentAccount> {
        private PaymentAccountListRequest(HttpClient httpClient,
                ListRequestExecutor<S, PaymentAccount> executor) {
            super(httpClient, executor);
        }

        public PaymentAccountListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "payment_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "payment_accounts";
        }

        @Override
        protected TypeToken<List<PaymentAccount>> getTypeToken() {
            return new TypeToken<List<PaymentAccount>>() {};
        }
    }
}
