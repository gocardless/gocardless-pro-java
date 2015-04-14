package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Customer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class CustomerService {
    private HttpClient httpClient;

    public CustomerService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CustomerCreateRequest create() {
        return new CustomerCreateRequest(httpClient);
    }

    public CustomerListRequest list() {
        return new CustomerListRequest(httpClient);
    }

    public CustomerGetRequest get(String identity) {
        return new CustomerGetRequest(httpClient, identity);
    }

    public CustomerUpdateRequest update(String identity) {
        return new CustomerUpdateRequest(httpClient, identity);
    }

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

        public CustomerCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public CustomerCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public CustomerCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        public CustomerCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        public CustomerCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CustomerCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public CustomerCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public CustomerCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public CustomerCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

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
        private String before;
        private CreatedAt createdAt;
        private Integer limit;

        public CustomerListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public CustomerListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public CustomerListRequest withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

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
                params.putAll(createdAt.getQueryParams());
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

        public static class CreatedAt {
            private String gt;
            private String gte;
            private String lt;
            private String lte;

            public CreatedAt withGt(String gt) {
                this.gt = gt;
                return this;
            }

            public CreatedAt withGte(String gte) {
                this.gte = gte;
                return this;
            }

            public CreatedAt withLt(String lt) {
                this.lt = lt;
                return this;
            }

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

        public CustomerUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public CustomerUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public CustomerUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        public CustomerUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        public CustomerUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CustomerUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public CustomerUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public CustomerUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public CustomerUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

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
