package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Payment;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PaymentService {
    private HttpClient httpClient;

    public PaymentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public PaymentListRequest list() throws IOException {
        return new PaymentListRequest(httpClient);
    }

    public PaymentGetRequest get(String identity) throws IOException {
        return new PaymentGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void cancel(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void retry(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
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
            super(httpClient, "/payments", "payments", new TypeToken<List<Payment>>() {});
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
    }

    public static final class PaymentGetRequest extends GetRequest<Payment> {
        private final String identity;

        private PaymentGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/payments/:identity", "payments", Payment.class);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }
    }
}
