package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.CurrencyExchangeRate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with currency exchange rate resources.
 *
 * Currency exchange rates from our foreign exchange provider.
 */
public class CurrencyExchangeRateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#currencyExchangeRates() }.
     */
    public CurrencyExchangeRateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of all exchange rates.
     */
    public CurrencyExchangeRateListRequest<ListResponse<CurrencyExchangeRate>> list() {
        return new CurrencyExchangeRateListRequest<>(httpClient,
                ListRequest.<CurrencyExchangeRate>pagingExecutor());
    }

    public CurrencyExchangeRateListRequest<Iterable<CurrencyExchangeRate>> all() {
        return new CurrencyExchangeRateListRequest<>(httpClient,
                ListRequest.<CurrencyExchangeRate>iteratingExecutor());
    }

    /**
     * Request class for {@link CurrencyExchangeRateService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of all exchange rates.
     */
    public static final class CurrencyExchangeRateListRequest<S>
            extends ListRequest<S, CurrencyExchangeRate> {
        private String source;
        private String target;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CurrencyExchangeRateListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CurrencyExchangeRateListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public CurrencyExchangeRateListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Source currency
         */
        public CurrencyExchangeRateListRequest<S> withSource(String source) {
            this.source = source;
            return this;
        }

        /**
         * Target currency
         */
        public CurrencyExchangeRateListRequest<S> withTarget(String target) {
            this.target = target;
            return this;
        }

        private CurrencyExchangeRateListRequest(HttpClient httpClient,
                ListRequestExecutor<S, CurrencyExchangeRate> executor) {
            super(httpClient, executor);
        }

        public CurrencyExchangeRateListRequest<S> withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (source != null) {
                params.put("source", source);
            }
            if (target != null) {
                params.put("target", target);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "currency_exchange_rates";
        }

        @Override
        protected String getEnvelope() {
            return "currency_exchange_rates";
        }

        @Override
        protected TypeToken<List<CurrencyExchangeRate>> getTypeToken() {
            return new TypeToken<List<CurrencyExchangeRate>>() {};
        }
    }
}
