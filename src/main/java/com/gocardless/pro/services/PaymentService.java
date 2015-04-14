package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Payment;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class PaymentService {
    private HttpClient httpClient;

    public PaymentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public PaymentCreateRequest create() {
        return new PaymentCreateRequest(httpClient);
    }

    public PaymentListRequest list() {
        return new PaymentListRequest(httpClient);
    }

    public PaymentGetRequest get(String identity) {
        return new PaymentGetRequest(httpClient, identity);
    }

    public PaymentUpdateRequest update(String identity) {
        return new PaymentUpdateRequest(httpClient, identity);
    }

    public PaymentCancelRequest cancel(String identity) {
        return new PaymentCancelRequest(httpClient, identity);
    }

    public PaymentRetryRequest retry(String identity) {
        return new PaymentRetryRequest(httpClient, identity);
    }

    public static final class PaymentCreateRequest extends PostRequest<Payment> {
        private Integer amount;
        private String chargeDate;
        private String currency;
        private String description;
        private Links links;
        private Map<String, String> metadata;
        private String reference;

        public PaymentCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public PaymentCreateRequest withChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        public PaymentCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public PaymentCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        public PaymentCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public PaymentCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public PaymentCreateRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        private PaymentCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/payments";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String mandate;

            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }

    public static final class PaymentListRequest extends ListRequest<Payment> {
        private String after;
        private String before;
        private CreatedAt createdAt;
        private String creditor;
        private String customer;
        private Integer limit;
        private String mandate;
        private String status;
        private String subscription;

        public PaymentListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public PaymentListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public PaymentListRequest withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        public PaymentListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public PaymentListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public PaymentListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        public PaymentListRequest withStatus(String status) {
            this.status = status;
            return this;
        }

        public PaymentListRequest withSubscription(String subscription) {
            this.subscription = subscription;
            return this;
        }

        private PaymentListRequest(HttpClient httpClient) {
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
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            if (status != null) {
                params.put("status", status);
            }
            if (subscription != null) {
                params.put("subscription", subscription);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/payments";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected TypeToken<List<Payment>> getTypeToken() {
            return new TypeToken<List<Payment>>() {};
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

    public static final class PaymentGetRequest extends GetRequest<Payment> {
        @PathParam
        private final String identity;

        private PaymentGetRequest(HttpClient httpClient, String identity) {
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
            return "/payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }
    }

    public static final class PaymentUpdateRequest extends PutRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        public PaymentUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        private PaymentUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/payments/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class PaymentCancelRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        public PaymentCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        private PaymentCancelRequest(HttpClient httpClient, String identity) {
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
            return "/payments/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class PaymentRetryRequest extends PostRequest<Payment> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        public PaymentRetryRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        private PaymentRetryRequest(HttpClient httpClient, String identity) {
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
            return "/payments/:identity/actions/retry";
        }

        @Override
        protected String getEnvelope() {
            return "payments";
        }

        @Override
        protected Class<Payment> getResponseClass() {
            return Payment.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
