package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Subscription;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SubscriptionService {
    private HttpClient httpClient;

    public SubscriptionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public SubscriptionListRequest list() throws IOException {
        return new SubscriptionListRequest(httpClient);
    }

    public SubscriptionGetRequest get(String identity) throws IOException {
        return new SubscriptionGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void cancel(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public static final class SubscriptionListRequest extends ListRequest<Subscription> {
        private String after;

        public SubscriptionListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public SubscriptionListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private String customer;

        public SubscriptionListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        private Integer limit;

        public SubscriptionListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String mandate;

        public SubscriptionListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        private SubscriptionListRequest(HttpClient httpClient) {
            super(httpClient, "/subscriptions", "subscriptions",
                    new TypeToken<List<Subscription>>() {});
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
            if (customer != null) {
                params.put("customer", customer);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            return params.build();
        }
    }

    public static final class SubscriptionGetRequest extends GetRequest<Subscription> {
        private final String identity;

        private SubscriptionGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/subscriptions/:identity", "subscriptions", Subscription.class);
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
