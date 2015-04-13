package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Mandate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MandateService {
    private HttpClient httpClient;

    public MandateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public MandateCreateRequest create() throws IOException {
        return new MandateCreateRequest(httpClient);
    }

    public MandateListRequest list() throws IOException {
        return new MandateListRequest(httpClient);
    }

    public MandateGetRequest get(String identity) throws IOException {
        return new MandateGetRequest(httpClient, identity);
    }

    public MandateUpdateRequest update(String identity) throws IOException {
        return new MandateUpdateRequest(httpClient, identity);
    }

    public MandateCancelRequest cancel(String identity) throws IOException {
        return new MandateCancelRequest(httpClient, identity);
    }

    public MandateReinstateRequest reinstate(String identity) throws IOException {
        return new MandateReinstateRequest(httpClient, identity);
    }

    public static final class MandateCreateRequest extends PostRequest<Mandate> {
        private Object links;

        public MandateCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private Object metadata;

        public MandateCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private String reference;

        public MandateCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        private String scheme;

        public MandateCreateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        private MandateCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/mandates";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class MandateListRequest extends ListRequest<Mandate> {
        private String after;

        public MandateListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public MandateListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private String creditor;

        public MandateListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        private String customer;

        public MandateListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        private String customerBankAccount;

        public MandateListRequest withCustomerBankAccount(String customerBankAccount) {
            this.customerBankAccount = customerBankAccount;
            return this;
        }

        private Integer limit;

        public MandateListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String reference;

        public MandateListRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        private List<String> status;

        public MandateListRequest withStatus(List<String> status) {
            this.status = status;
            return this;
        }

        private MandateListRequest(HttpClient httpClient) {
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
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (customerBankAccount != null) {
                params.put("customer_bank_account", customerBankAccount);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (reference != null) {
                params.put("reference", reference);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/mandates";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected TypeToken<List<Mandate>> getTypeToken() {
            return new TypeToken<List<Mandate>>() {};
        }
    }

    public static final class MandateGetRequest extends GetRequest<Mandate> {
        @PathParam
        private final String identity;

        private MandateGetRequest(HttpClient httpClient, String identity) {
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
            return "/mandates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }
    }

    public static final class MandateUpdateRequest extends PutRequest<Mandate> {
        @PathParam
        private final String identity;
        private Object metadata;

        public MandateUpdateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private MandateUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/mandates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class MandateCancelRequest extends PostRequest<Mandate> {
        @PathParam
        private final String identity;
        private Object metadata;

        public MandateCancelRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private MandateCancelRequest(HttpClient httpClient, String identity) {
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
            return "/mandates/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class MandateReinstateRequest extends PostRequest<Mandate> {
        @PathParam
        private final String identity;
        private Object metadata;

        public MandateReinstateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private MandateReinstateRequest(HttpClient httpClient, String identity) {
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
            return "/mandates/:identity/actions/reinstate";
        }

        @Override
        protected String getEnvelope() {
            return "mandates";
        }

        @Override
        protected Class<Mandate> getResponseClass() {
            return Mandate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
