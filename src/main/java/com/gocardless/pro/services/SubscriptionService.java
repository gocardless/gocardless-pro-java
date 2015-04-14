package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Subscription;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class SubscriptionService {
    private HttpClient httpClient;

    public SubscriptionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public SubscriptionCreateRequest create() {
        return new SubscriptionCreateRequest(httpClient);
    }

    public SubscriptionListRequest list() {
        return new SubscriptionListRequest(httpClient);
    }

    public SubscriptionGetRequest get(String identity) {
        return new SubscriptionGetRequest(httpClient, identity);
    }

    public SubscriptionUpdateRequest update(String identity) {
        return new SubscriptionUpdateRequest(httpClient, identity);
    }

    public SubscriptionCancelRequest cancel(String identity) {
        return new SubscriptionCancelRequest(httpClient, identity);
    }

    public static final class SubscriptionCreateRequest extends PostRequest<Subscription> {
        private Integer amount;

        public SubscriptionCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        private Integer count;

        public SubscriptionCreateRequest withCount(Integer count) {
            this.count = count;
            return this;
        }

        private String currency;

        public SubscriptionCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        private Integer dayOfMonth;

        public SubscriptionCreateRequest withDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        private String endAt;

        public SubscriptionCreateRequest withEndAt(String endAt) {
            this.endAt = endAt;
            return this;
        }

        private Integer interval;

        public SubscriptionCreateRequest withInterval(Integer interval) {
            this.interval = interval;
            return this;
        }

        private String intervalUnit;

        public SubscriptionCreateRequest withIntervalUnit(String intervalUnit) {
            this.intervalUnit = intervalUnit;
            return this;
        }

        private Object links;

        public SubscriptionCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private Object metadata;

        public SubscriptionCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        public enum Month {
            JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
        }

        private Month month;

        public SubscriptionCreateRequest withMonth(Month month) {
            this.month = month;
            return this;
        }

        private String name;

        public SubscriptionCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private String startAt;

        public SubscriptionCreateRequest withStartAt(String startAt) {
            this.startAt = startAt;
            return this;
        }

        private SubscriptionCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/subscriptions";
        }

        @Override
        protected String getEnvelope() {
            return "subscriptions";
        }

        @Override
        protected Class<Subscription> getResponseClass() {
            return Subscription.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
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

        @Override
        protected String getPathTemplate() {
            return "/subscriptions";
        }

        @Override
        protected String getEnvelope() {
            return "subscriptions";
        }

        @Override
        protected TypeToken<List<Subscription>> getTypeToken() {
            return new TypeToken<List<Subscription>>() {};
        }
    }

    public static final class SubscriptionGetRequest extends GetRequest<Subscription> {
        @PathParam
        private final String identity;

        private SubscriptionGetRequest(HttpClient httpClient, String identity) {
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
            return "/subscriptions/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "subscriptions";
        }

        @Override
        protected Class<Subscription> getResponseClass() {
            return Subscription.class;
        }
    }

    public static final class SubscriptionUpdateRequest extends PutRequest<Subscription> {
        @PathParam
        private final String identity;
        private Object metadata;

        public SubscriptionUpdateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private String name;

        public SubscriptionUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private SubscriptionUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/subscriptions/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "subscriptions";
        }

        @Override
        protected Class<Subscription> getResponseClass() {
            return Subscription.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class SubscriptionCancelRequest extends PostRequest<Subscription> {
        @PathParam
        private final String identity;
        private Object metadata;

        public SubscriptionCancelRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private SubscriptionCancelRequest(HttpClient httpClient, String identity) {
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
            return "/subscriptions/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "subscriptions";
        }

        @Override
        protected Class<Subscription> getResponseClass() {
            return Subscription.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
