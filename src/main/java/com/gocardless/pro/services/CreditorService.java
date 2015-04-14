package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Creditor;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class CreditorService {
    private HttpClient httpClient;

    public CreditorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CreditorCreateRequest create() {
        return new CreditorCreateRequest(httpClient);
    }

    public CreditorListRequest list() {
        return new CreditorListRequest(httpClient);
    }

    public CreditorGetRequest get(String identity) {
        return new CreditorGetRequest(httpClient, identity);
    }

    public CreditorUpdateRequest update(String identity) {
        return new CreditorUpdateRequest(httpClient, identity);
    }

    public static final class CreditorCreateRequest extends PostRequest<Creditor> {
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String countryCode;
        private Links links;
        private String name;
        private String postalCode;
        private String region;

        public CreditorCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public CreditorCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public CreditorCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        public CreditorCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        public CreditorCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public CreditorCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        public CreditorCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CreditorCreateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        private CreditorCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String logo;

            public Links withLogo(String logo) {
                this.logo = logo;
                return this;
            }
        }
    }

    public static final class CreditorListRequest extends ListRequest<Creditor> {
        private String after;
        private String before;
        private Integer limit;

        public CreditorListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public CreditorListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public CreditorListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private CreditorListRequest(HttpClient httpClient) {
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
            if (limit != null) {
                params.put("limit", limit);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected TypeToken<List<Creditor>> getTypeToken() {
            return new TypeToken<List<Creditor>>() {};
        }
    }

    public static final class CreditorGetRequest extends GetRequest<Creditor> {
        @PathParam
        private final String identity;

        private CreditorGetRequest(HttpClient httpClient, String identity) {
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
            return "/creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }
    }

    public static final class CreditorUpdateRequest extends PutRequest<Creditor> {
        @PathParam
        private final String identity;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String countryCode;
        private Links links;
        private String name;
        private String postalCode;
        private String region;

        public CreditorUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public CreditorUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public CreditorUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        public CreditorUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        public CreditorUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorUpdateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public CreditorUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        public CreditorUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CreditorUpdateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        private CreditorUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String defaultEurPayoutAccount;
            private String defaultGbpPayoutAccount;
            private String logo;

            public Links withDefaultEurPayoutAccount(String defaultEurPayoutAccount) {
                this.defaultEurPayoutAccount = defaultEurPayoutAccount;
                return this;
            }

            public Links withDefaultGbpPayoutAccount(String defaultGbpPayoutAccount) {
                this.defaultGbpPayoutAccount = defaultGbpPayoutAccount;
                return this;
            }

            public Links withLogo(String logo) {
                this.logo = logo;
                return this;
            }
        }
    }
}
