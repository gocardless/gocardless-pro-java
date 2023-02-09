package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.VerificationDetail;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with verification detail resources.
 *
 * Details of a creditor that are required for verification
 */
public class VerificationDetailService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#verificationDetails() }.
     */
    public VerificationDetailService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a list of verification details belonging to a creditor.
     */
    public VerificationDetailListRequest<ListResponse<VerificationDetail>> list() {
        return new VerificationDetailListRequest<>(httpClient,
                ListRequest.<VerificationDetail>pagingExecutor());
    }

    public VerificationDetailListRequest<Iterable<VerificationDetail>> all() {
        return new VerificationDetailListRequest<>(httpClient,
                ListRequest.<VerificationDetail>iteratingExecutor());
    }

    /**
     * Verification details represent any information needed by GoCardless to verify a creditor.
     * Currently, only UK-based companies are supported. In other words, to submit verification
     * details for a creditor, their creditor_type must be company and their country_code must be
     * GB.
     */
    public VerificationDetailCreateRequest create() {
        return new VerificationDetailCreateRequest(httpClient);
    }

    /**
     * Request class for {@link VerificationDetailService#list }.
     *
     * Returns a list of verification details belonging to a creditor.
     */
    public static final class VerificationDetailListRequest<S>
            extends ListRequest<S, VerificationDetail> {
        private String creditor;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public VerificationDetailListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public VerificationDetailListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Unique identifier, beginning with "CR".
         */
        public VerificationDetailListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Number of records to return.
         */
        public VerificationDetailListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private VerificationDetailListRequest(HttpClient httpClient,
                ListRequestExecutor<S, VerificationDetail> executor) {
            super(httpClient, executor);
        }

        public VerificationDetailListRequest<S> withHeader(String headerName, String headerValue) {
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
            return "verification_details";
        }

        @Override
        protected String getEnvelope() {
            return "verification_details";
        }

        @Override
        protected TypeToken<List<VerificationDetail>> getTypeToken() {
            return new TypeToken<List<VerificationDetail>>() {};
        }
    }

    /**
     * Request class for {@link VerificationDetailService#create }.
     *
     * Verification details represent any information needed by GoCardless to verify a creditor.
     * Currently, only UK-based companies are supported. In other words, to submit verification
     * details for a creditor, their creditor_type must be company and their country_code must be
     * GB.
     */
    public static final class VerificationDetailCreateRequest
            extends PostRequest<VerificationDetail> {
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String companyNumber;
        private String description;
        private List<Directors> directors;
        private Links links;
        private String postalCode;

        /**
         * The first line of the company's address.
         */
        public VerificationDetailCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the company's address.
         */
        public VerificationDetailCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the company's address.
         */
        public VerificationDetailCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * The city of the company's address.
         */
        public VerificationDetailCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * The company's registration number.
         */
        public VerificationDetailCreateRequest withCompanyNumber(String companyNumber) {
            this.companyNumber = companyNumber;
            return this;
        }

        /**
         * A summary describing what the company does.
         */
        public VerificationDetailCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * The company's directors.
         */
        public VerificationDetailCreateRequest withDirectors(List<Directors> directors) {
            this.directors = directors;
            return this;
        }

        /**
         * The company's directors.
         */
        public VerificationDetailCreateRequest withDirectors(Directors directors) {
            if (this.directors == null) {
                this.directors = new ArrayList<>();
            }
            this.directors.add(directors);
            return this;
        }

        public VerificationDetailCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors).
         */
        public VerificationDetailCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * The company's postal code.
         */
        public VerificationDetailCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        private VerificationDetailCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public VerificationDetailCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "verification_details";
        }

        @Override
        protected String getEnvelope() {
            return "verification_details";
        }

        @Override
        protected Class<VerificationDetail> getResponseClass() {
            return VerificationDetail.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Directors {
            private String city;
            private String countryCode;
            private String dateOfBirth;
            private String familyName;
            private String givenName;
            private String postalCode;
            private String street;

            /**
             * The city of the person's address.
             */
            public Directors withCity(String city) {
                this.city = city;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             */
            public Directors withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * The person's date of birth.
             */
            public Directors withDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
                return this;
            }

            /**
             * The person's family name.
             */
            public Directors withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * The person's given name.
             */
            public Directors withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * The person's postal code.
             */
            public Directors withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The street of the person's address.
             */
            public Directors withStreet(String street) {
                this.street = street;
                return this;
            }
        }

        public static class Links {
            private String creditor;

            /**
             * ID of the associated [creditor](#core-endpoints-creditors).
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }
}
