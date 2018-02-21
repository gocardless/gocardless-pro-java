package com.gocardless.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Customer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with customer resources.
 *
 * Customer objects hold the contact details for a customer. A customer can have several [customer
 * bank accounts](#core-endpoints-customer-bank-accounts), which in turn can have several Direct
 * Debit [mandates](#core-endpoints-mandates).
 * 
 * Notes:
 * - the `swedish_identity_number` field may only be supplied for Swedish customers, and must be
 * supplied if you intend to set up an Autogiro mandate with the customer.
 * - the `danish_identity_number` field may only be supplied for Danish customers, and must be
 * supplied if you intend to set up a Betalingsservice mandate with the customer.
 */
public class CustomerService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#customers() }.
     */
    public CustomerService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new customer object.
     */
    public CustomerCreateRequest create() {
        return new CustomerCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your customers.
     */
    public CustomerListRequest<ListResponse<Customer>> list() {
        return new CustomerListRequest<>(httpClient, ListRequest.<Customer>pagingExecutor());
    }

    public CustomerListRequest<Iterable<Customer>> all() {
        return new CustomerListRequest<>(httpClient, ListRequest.<Customer>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing customer.
     */
    public CustomerGetRequest get(String identity) {
        return new CustomerGetRequest(httpClient, identity);
    }

    /**
     * Updates a customer object. Supports all of the fields supported when creating a customer.
     */
    public CustomerUpdateRequest update(String identity) {
        return new CustomerUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CustomerService#create }.
     *
     * Creates a new customer object.
     */
    public static final class CustomerCreateRequest extends IdempotentPostRequest<Customer> {
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String companyName;
        private String countryCode;
        private String danishIdentityNumber;
        private String email;
        private String familyName;
        private String givenName;
        private String language;
        private Map<String, String> metadata;
        private String postalCode;
        private String region;
        private String swedishIdentityNumber;

        /**
         * The first line of the customer's address.
         */
        public CustomerCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public CustomerCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public CustomerCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public CustomerCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided.
         */
        public CustomerCreateRequest withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code.
         */
        public CustomerCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
         * if the customer's bank account is denominated in Danish krone (DKK).
         */
        public CustomerCreateRequest withDanishIdentityNumber(String danishIdentityNumber) {
            this.danishIdentityNumber = danishIdentityNumber;
            return this;
        }

        /**
         * Customer's email address.
         */
        public CustomerCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public CustomerCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public CustomerCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        /**
         * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
         * notification emails sent by GoCardless if your organisation does not send its own (see [compliance
         * requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
         * "it", "nl", "sv" are supported. If this is not provided, the language will be chosen based on the
         * `country_code` (if supplied) or default to "en".
         */
        public CustomerCreateRequest withLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public CustomerCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public CustomerCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public CustomerCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The customer's address region, county or department.
         */
        public CustomerCreateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
         * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
         */
        public CustomerCreateRequest withSwedishIdentityNumber(String swedishIdentityNumber) {
            this.swedishIdentityNumber = swedishIdentityNumber;
            return this;
        }

        public CustomerCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Customer> handleConflict(HttpClient httpClient, String id) {
            return new CustomerGetRequest(httpClient, id);
        }

        private CustomerCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "customers";
        }

        @Override
        protected String getEnvelope() {
            return "customers";
        }

        @Override
        protected Class<Customer> getResponseClass() {
            return Customer.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link CustomerService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your customers.
     */
    public static final class CustomerListRequest<S> extends ListRequest<S, Customer> {
        private CreatedAt createdAt;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CustomerListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CustomerListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public CustomerListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public CustomerListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public CustomerListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public CustomerListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public CustomerListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Number of records to return.
         */
        public CustomerListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CustomerListRequest(HttpClient httpClient, ListRequestExecutor<S, Customer> executor) {
            super(httpClient, executor);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "customers";
        }

        @Override
        protected String getEnvelope() {
            return "customers";
        }

        @Override
        protected TypeToken<List<Customer>> getTypeToken() {
            return new TypeToken<List<Customer>>() {};
        }

        public static class CreatedAt {
            private String gt;
            private String gte;
            private String lt;
            private String lte;

            /**
             * Limit to records created after the specified date-time.
             */
            public CreatedAt withGt(String gt) {
                this.gt = gt;
                return this;
            }

            /**
             * Limit to records created on or after the specified date-time.
             */
            public CreatedAt withGte(String gte) {
                this.gte = gte;
                return this;
            }

            /**
             * Limit to records created before the specified date-time.
             */
            public CreatedAt withLt(String lt) {
                this.lt = lt;
                return this;
            }

            /**
             * Limit to records created on or before the specified date-time.
             */
            public CreatedAt withLte(String lte) {
                this.lte = lte;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (gt != null) {
                    params.put("created_at[gt]", gt);
                }
                if (gte != null) {
                    params.put("created_at[gte]", gte);
                }
                if (lt != null) {
                    params.put("created_at[lt]", lt);
                }
                if (lte != null) {
                    params.put("created_at[lte]", lte);
                }
                return params.build();
            }
        }
    }

    /**
     * Request class for {@link CustomerService#get }.
     *
     * Retrieves the details of an existing customer.
     */
    public static final class CustomerGetRequest extends GetRequest<Customer> {
        @PathParam
        private final String identity;

        private CustomerGetRequest(HttpClient httpClient, String identity) {
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
            return "customers/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customers";
        }

        @Override
        protected Class<Customer> getResponseClass() {
            return Customer.class;
        }
    }

    /**
     * Request class for {@link CustomerService#update }.
     *
     * Updates a customer object. Supports all of the fields supported when creating a customer.
     */
    public static final class CustomerUpdateRequest extends PutRequest<Customer> {
        @PathParam
        private final String identity;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String companyName;
        private String countryCode;
        private String danishIdentityNumber;
        private String email;
        private String familyName;
        private String givenName;
        private String language;
        private Map<String, String> metadata;
        private String postalCode;
        private String region;
        private String swedishIdentityNumber;

        /**
         * The first line of the customer's address.
         */
        public CustomerUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public CustomerUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public CustomerUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public CustomerUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided.
         */
        public CustomerUpdateRequest withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code.
         */
        public CustomerUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
         * if the customer's bank account is denominated in Danish krone (DKK).
         */
        public CustomerUpdateRequest withDanishIdentityNumber(String danishIdentityNumber) {
            this.danishIdentityNumber = danishIdentityNumber;
            return this;
        }

        /**
         * Customer's email address.
         */
        public CustomerUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public CustomerUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public CustomerUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        /**
         * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
         * notification emails sent by GoCardless if your organisation does not send its own (see [compliance
         * requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
         * "it", "nl", "sv" are supported. If this is not provided, the language will be chosen based on the
         * `country_code` (if supplied) or default to "en".
         */
        public CustomerUpdateRequest withLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public CustomerUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public CustomerUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public CustomerUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The customer's address region, county or department.
         */
        public CustomerUpdateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
         * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
         */
        public CustomerUpdateRequest withSwedishIdentityNumber(String swedishIdentityNumber) {
            this.swedishIdentityNumber = swedishIdentityNumber;
            return this;
        }

        private CustomerUpdateRequest(HttpClient httpClient, String identity) {
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
            return "customers/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customers";
        }

        @Override
        protected Class<Customer> getResponseClass() {
            return Customer.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
