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
        private String branchCode;
        private String countryCode;
        private String feature;
        private String scheme;

        /**
         * (Currently only supports UK sort-codes) The six-digit number that identifies both the
         * bank and the specific branch where an account is held, eg. '601234'.
         */
        public InstitutionListRequest<S> withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO
         * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. The country code of the institution. If nothing is provided, institutions
         * with the country code 'GB' are returned by default.
         */
        public InstitutionListRequest<S> withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * The feature that institutions support. The available options include `pis`, and
         * `vrp_sweeping`. If nothing is provided, institutions supporting 'pis' are returned by
         * default.
         */
        public InstitutionListRequest<S> withFeature(String feature) {
            this.feature = feature;
            return this;
        }

        /**
         * The scheme that institutions support. The available options include `faster_payments`,
         * `sepa_credit_transfer`, and `sepa_instant_credit_transfer`. If nothing is provided,
         * institutions supporting 'faster_payments' are returned by default.
         */
        public InstitutionListRequest<S> withScheme(String scheme) {
            this.scheme = scheme;
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
            if (branchCode != null) {
                params.put("branch_code", branchCode);
            }
            if (countryCode != null) {
                params.put("country_code", countryCode);
            }
            if (feature != null) {
                params.put("feature", feature);
            }
            if (scheme != null) {
                params.put("scheme", scheme);
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
        private String countryCode;
        private List<String> ids;
        private Boolean includeDisabled;
        private String search;

        /**
         * [ISO
         * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. The country code of the institution. If nothing is provided, institutions
         * with the country code 'GB' are returned by default.
         */
        public InstitutionListForBillingRequestRequest<S> withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

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
         * Indicates whether to include temporarily disabled institutions in the response. If not
         * provided or set to false, only enabled institutions will be returned.
         * 
         */
        public InstitutionListForBillingRequestRequest<S> withIncludeDisabled(
                Boolean includeDisabled) {
            this.includeDisabled = includeDisabled;
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
            if (countryCode != null) {
                params.put("country_code", countryCode);
            }
            if (ids != null) {
                params.put("ids", Joiner.on(",").join(ids));
            }
            if (includeDisabled != null) {
                params.put("include_disabled", includeDisabled);
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
