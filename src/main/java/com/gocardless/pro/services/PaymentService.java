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

        public PaymentCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        private String chargeDate;

        public PaymentCreateRequest withChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
            return this;
        }

        private String currency;

        public PaymentCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        private String description;

        public PaymentCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        private Object links;

        public PaymentCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private Object metadata;

        public PaymentCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private String reference;

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
    }

    public static final class PaymentListRequest extends ListRequest<Payment> {
        private String after;

        public PaymentListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public PaymentListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private Object createdAt;

        public PaymentListRequest withCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        private String creditor;

        public PaymentListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        private String customer;

        public PaymentListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        private Integer limit;

        public PaymentListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String mandate;

        public PaymentListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        private String status;

        public PaymentListRequest withStatus(String status) {
            this.status = status;
            return this;
        }

        private String subscription;

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
                params.put("created_at", createdAt);
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
        private Object metadata;

        public PaymentUpdateRequest withMetadata(Object metadata) {
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
        private Object metadata;

        public PaymentCancelRequest withMetadata(Object metadata) {
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
        private Object metadata;

        public PaymentRetryRequest withMetadata(Object metadata) {
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
