package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Institution;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with institution resources.
 *
 * Institutions that are supported when creating [Bank
 * Authorisations](#billing-requests-bank-authorisations) for a particular country or purpose.
 * 
 * Not all institutions support both Payment Initiation (PIS) and Account Information (AIS)
 * services.
 */
public class InstitutionService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#institutions() }.
     */
    public InstitutionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a list of supported institutions.
     */
    public InstitutionListRequest<ListResponse<Institution>> list() {
        return new InstitutionListRequest<>(httpClient, ListRequest.<Institution>pagingExecutor());
    }

    public InstitutionListRequest<Iterable<Institution>> all() {
        return new InstitutionListRequest<>(httpClient,
                ListRequest.<Institution>iteratingExecutor());
    }

    /**
     * Returns all institutions valid for a Billing Request.
     * 
     * This endpoint is currently supported only for FasterPayments.
     */
    public InstitutionListForBillingRequestRequest<Iterable<Institution>> listForBillingRequest(
            String identity) {
        return new InstitutionListForBillingRequestRequest<>(httpClient,
                ListRequest.<Institution>iteratingExecutor(), identity);
    }

    /**
     * Request class for {@link InstitutionService#list }.
     *
     * Returns a list of supported institutions.
     */
    public static final class InstitutionListRequest<S> extends ListRequest<S, Institution> {
        private String countryCode;

        /**
         * [ISO
         * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. The country code of the institution.
         */
        public InstitutionListRequest<S> withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private InstitutionListRequest(HttpClient httpClient,
                ListRequestExecutor<S, Institution> executor) {
            super(httpClient, executor);
        }

        public InstitutionListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (countryCode != null) {
                params.put("country_code", countryCode);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "institutions";
        }

        @Override
        protected String getEnvelope() {
            return "institutions";
        }

        @Override
        protected TypeToken<List<Institution>> getTypeToken() {
            return new TypeToken<List<Institution>>() {};
        }
    }

    /**
     * Request class for {@link InstitutionService#listForBillingRequest }.
     *
     * Returns all institutions valid for a Billing Request.
     * 
     * This endpoint is currently supported only for FasterPayments.
     */
    public static final class InstitutionListForBillingRequestRequest<S>
            extends ListRequest<S, Institution> {
        @PathParam
        private final String identity;
        private List<String> ids;
        private String search;

        /**
         * ID(s) of the institution(s) to retrieve. More than one ID can be specified using a
         * comma-separated string.
         */
        public InstitutionListForBillingRequestRequest<S> withIds(List<String> ids) {
            this.ids = ids;
            return this;
        }

        /**
         * ID(s) of the institution(s) to retrieve. More than one ID can be specified using a
         * comma-separated string.
         */
        public InstitutionListForBillingRequestRequest<S> withIds(String ids) {
            if (this.ids == null) {
                this.ids = new ArrayList<>();
            }
            this.ids.add(ids);
            return this;
        }

        /**
         * A search substring for retrieving institution(s), based on the institution's name.
         */
        public InstitutionListForBillingRequestRequest<S> withSearch(String search) {
            this.search = search;
            return this;
        }

        private InstitutionListForBillingRequestRequest(HttpClient httpClient,
                ListRequestExecutor<S, Institution> executor, String identity) {
            super(httpClient, executor, "GET");
            this.identity = identity;
        }

        public InstitutionListForBillingRequestRequest<S> withHeader(String headerName,
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
            if (ids != null) {
                params.put("ids", Joiner.on(",").join(ids));
            }
            if (search != null) {
                params.put("search", search);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_requests/:identity/institutions";
        }

        @Override
        protected String getEnvelope() {
            return "institutions";
        }

        @Override
        protected TypeToken<List<Institution>> getTypeToken() {
            return new TypeToken<List<Institution>>() {};
        }
    }
}
