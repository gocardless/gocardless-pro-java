package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Institution;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with institution resources.
 *
 * Institutions that are supported when creating [Bank
 * Authorisations](#core-endpoints-bank-authorisations).
 */
public class InstitutionService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#institutions() }.
     */
    public InstitutionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a list of all supported institutions.
     */
    public InstitutionListRequest<ListResponse<Institution>> list() {
        return new InstitutionListRequest<>(httpClient, ListRequest.<Institution>pagingExecutor());
    }

    public InstitutionListRequest<Iterable<Institution>> all() {
        return new InstitutionListRequest<>(httpClient,
                ListRequest.<Institution>iteratingExecutor());
    }

    /**
     * Request class for {@link InstitutionService#list }.
     *
     * Returns a list of all supported institutions.
     */
    public static final class InstitutionListRequest<S> extends ListRequest<S, Institution> {
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
}
