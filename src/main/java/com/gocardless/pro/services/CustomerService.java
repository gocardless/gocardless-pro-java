package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Customer;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private HttpClient httpClient;

    public CustomerService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CustomerCreateRequest create() throws IOException {
        return new CustomerCreateRequest(httpClient);
    }

    public CustomerListRequest list() throws IOException {
        return new CustomerListRequest(httpClient);
    }

    public CustomerGetRequest get(String identity) throws IOException {
        return new CustomerGetRequest(httpClient, identity);
    }

    public CustomerUpdateRequest update(String identity) throws IOException {
        return new CustomerUpdateRequest(httpClient, identity);
    }

    public static final class CustomerCreateRequest extends PostRequest<Customer> {
        private String addressLine1;

        public CustomerCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        private String addressLine2;

        public CustomerCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        private String addressLine3;

        public CustomerCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        private String city;

        public CustomerCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        private String countryCode;

        public CustomerCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private String email;

        public CustomerCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        private String familyName;

        public CustomerCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        private String givenName;

        public CustomerCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        private Object metadata;

        public CustomerCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private String postalCode;

        public CustomerCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        private String region;

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

    public static final class CustomerListRequest extends ListRequest<Customer> {
        private String after;

        public CustomerListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public CustomerListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private Object createdAt;

        public CustomerListRequest withCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        private Integer limit;

        public CustomerListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private CustomerListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (createdAt != null) {
                params.put("created_at", createdAt);
            }
            if (limit != null) {
                params.put("limit", limit);
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
    }

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

    public static final class CustomerUpdateRequest extends PutRequest<Customer> {
        @PathParam
        private final String identity;
        private String addressLine1;

        public CustomerUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        private String addressLine2;

        public CustomerUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        private String addressLine3;

        public CustomerUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        private String city;

        public CustomerUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        private String countryCode;

        public CustomerUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private String email;

        public CustomerUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        private String familyName;

        public CustomerUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        private String givenName;

        public CustomerUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        private Object metadata;

        public CustomerUpdateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private String postalCode;

        public CustomerUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        private String region;

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
