package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Mandate;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class MandateService {
    private HttpClient httpClient;

    public MandateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public MandateCreateRequest create() {
        return new MandateCreateRequest(httpClient);
    }

    public MandateListRequest list() {
        return new MandateListRequest(httpClient);
    }

    public MandateGetRequest get(String identity) {
        return new MandateGetRequest(httpClient, identity);
    }

    public MandateUpdateRequest update(String identity) {
        return new MandateUpdateRequest(httpClient, identity);
    }

    public MandateCancelRequest cancel(String identity) {
        return new MandateCancelRequest(httpClient, identity);
    }

    public MandateReinstateRequest reinstate(String identity) {
        return new MandateReinstateRequest(httpClient, identity);
    }

    public static final class MandateCreateRequest extends PostRequest<Mandate> {
        private Links links;
        private Map<String, String> metadata;
        private String reference;
        private String scheme;

        public MandateCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public MandateCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public MandateCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

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

        public static class Links {
            private String creditor;
            private String customerBankAccount;

            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }

            public Links withCustomerBankAccount(String customerBankAccount) {
                this.customerBankAccount = customerBankAccount;
                return this;
            }
        }
    }

    public static final class MandateListRequest extends ListRequest<Mandate> {
        private String after;
        private String before;
        private String creditor;
        private String customer;
        private String customerBankAccount;
        private Integer limit;
        private String reference;
        private List<Status> status;

        public MandateListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public MandateListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public MandateListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        public MandateListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public MandateListRequest withCustomerBankAccount(String customerBankAccount) {
            this.customerBankAccount = customerBankAccount;
            return this;
        }

        public MandateListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public MandateListRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public MandateListRequest withStatus(List<Status> status) {
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
                params.put("status", Joiner.on(",").join(status));
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

        public enum Status {
            PENDING_SUBMISSION, SUBMITTED, ACTIVE, FAILED, CANCELLED, EXPIRED;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
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
        private Map<String, String> metadata;

        public MandateUpdateRequest withMetadata(Map<String, String> metadata) {
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
        private Map<String, String> metadata;

        public MandateCancelRequest withMetadata(Map<String, String> metadata) {
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
        private Map<String, String> metadata;

        public MandateReinstateRequest withMetadata(Map<String, String> metadata) {
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
