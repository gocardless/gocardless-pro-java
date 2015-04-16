package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Customer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with customer resources.
 *
 * Customer objects hold the contact details for a customer. A customer can have several [customer
 * bank accounts](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-accounts), which
 * in turn can have several Direct Debit
 * [mandates](https://developer.gocardless.com/pro/#api-endpoints-mandates).
 */
public class CustomerService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.pro.GoCardlessClient#customers() }.
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
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your customers.
     */
    public CustomerListRequest list() {
        return new CustomerListRequest(httpClient);
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
    public static final class CustomerCreateRequest extends PostRequest<Customer> {
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String countryCode;
        private String email;
        private String familyName;
        private String givenName;
        private Map<String, String> metadata;
        private String postalCode;
        private String region;

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
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code.
         */
        public CustomerCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
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
         * Customer's surname.
         */
        public CustomerCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * Customer's first name.
         */
        public CustomerCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
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

        private CustomerCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/customers";
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
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your customers.
     */
    public static final class CustomerListRequest extends ListRequest<Customer> {
        private CreatedAt createdAt;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CustomerListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CustomerListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        public CustomerListRequest withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Number of records to return.
         */
        public CustomerListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CustomerListRequest(HttpClient httpClient) {
            super(httpClient);
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
            return "/customers";
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
            return "/customers/:identity";
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
        private String countryCode;
        private String email;
        private String familyName;
        private String givenName;
        private Map<String, String> metadata;
        private String postalCode;
        private String region;

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
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code.
         */
        public CustomerUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
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
         * Customer's surname.
         */
        public CustomerUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * Customer's first name.
         */
        public CustomerUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
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
            return "/customers/:identity";
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
