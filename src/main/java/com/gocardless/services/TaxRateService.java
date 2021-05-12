package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.TaxRate;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with tax rate resources.
 *
 * Tax rates from tax authority.
 * 
 * We also maintain a [static list of the tax rates for each jurisdiction](#appendix-tax-rates).
 */
public class TaxRateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#taxRates() }.
     */
    public TaxRateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of all tax rates.
     */
    public TaxRateListRequest<ListResponse<TaxRate>> list() {
        return new TaxRateListRequest<>(httpClient, ListRequest.<TaxRate>pagingExecutor());
    }

    public TaxRateListRequest<Iterable<TaxRate>> all() {
        return new TaxRateListRequest<>(httpClient, ListRequest.<TaxRate>iteratingExecutor());
    }

    /**
     * Retrieves the details of a tax rate.
     */
    public TaxRateGetRequest get(String identity) {
        return new TaxRateGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link TaxRateService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of all tax rates.
     */
    public static final class TaxRateListRequest<S> extends ListRequest<S, TaxRate> {
        private String jurisdiction;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public TaxRateListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public TaxRateListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * The jurisdiction this tax rate applies to
         */
        public TaxRateListRequest<S> withJurisdiction(String jurisdiction) {
            this.jurisdiction = jurisdiction;
            return this;
        }

        private TaxRateListRequest(HttpClient httpClient,
                ListRequestExecutor<S, TaxRate> executor) {
            super(httpClient, executor);
        }

        public TaxRateListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (jurisdiction != null) {
                params.put("jurisdiction", jurisdiction);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "tax_rates";
        }

        @Override
        protected String getEnvelope() {
            return "tax_rates";
        }

        @Override
        protected TypeToken<List<TaxRate>> getTypeToken() {
            return new TypeToken<List<TaxRate>>() {};
        }
    }

    /**
     * Request class for {@link TaxRateService#get }.
     *
     * Retrieves the details of a tax rate.
     */
    public static final class TaxRateGetRequest extends GetRequest<TaxRate> {
        @PathParam
        private final String identity;

        private TaxRateGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public TaxRateGetRequest withHeader(String headerName, String headerValue) {
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
            return "tax_rates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "tax_rates";
        }

        @Override
        protected Class<TaxRate> getResponseClass() {
            return TaxRate.class;
        }
    }
}
