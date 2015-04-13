package com.gocardless.pro.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Event;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class EventService {
    private HttpClient httpClient;

    public EventService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public EventListRequest list() throws IOException {
        return new EventListRequest(httpClient);
    }

    public EventGetRequest get(String identity) throws IOException {
        return new EventGetRequest(httpClient, identity);
    }

    public static final class EventListRequest extends ListRequest<Event> {
        private String action;

        public EventListRequest withAction(String action) {
            this.action = action;
            return this;
        }

        private String after;

        public EventListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public EventListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private Object createdAt;

        public EventListRequest withCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public enum Include {
            PAYMENT, MANDATE, PAYOUT, REFUND, SUBSCRIPTION,
        }

        private Include include;

        public EventListRequest withInclude(Include include) {
            this.include = include;
            return this;
        }

        private Integer limit;

        public EventListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String mandate;

        public EventListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        private String parentEvent;

        public EventListRequest withParentEvent(String parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        private String payment;

        public EventListRequest withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        private String payout;

        public EventListRequest withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        private String refund;

        public EventListRequest withRefund(String refund) {
            this.refund = refund;
            return this;
        }

        public enum ResourceType {
            PAYMENTS, MANDATES, PAYOUTS, REFUNDS, SUBSCRIPTIONS,
        }

        private ResourceType resourceType;

        public EventListRequest withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        private String subscription;

        public EventListRequest withSubscription(String subscription) {
            this.subscription = subscription;
            return this;
        }

        private EventListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (action != null) {
                params.put("action", action);
            }
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (createdAt != null) {
                params.put("created_at", createdAt);
            }
            if (include != null) {
                params.put("include", include);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            if (parentEvent != null) {
                params.put("parent_event", parentEvent);
            }
            if (payment != null) {
                params.put("payment", payment);
            }
            if (payout != null) {
                params.put("payout", payout);
            }
            if (refund != null) {
                params.put("refund", refund);
            }
            if (resourceType != null) {
                params.put("resource_type", resourceType);
            }
            if (subscription != null) {
                params.put("subscription", subscription);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/events";
        }

        @Override
        protected String getEnvelope() {
            return "events";
        }

        @Override
        protected TypeToken<List<Event>> getTypeToken() {
            return new TypeToken<List<Event>>() {};
        }
    }

    public static final class EventGetRequest extends GetRequest<Event> {
        @PathParam
        private final String identity;

        private EventGetRequest(HttpClient httpClient, String identity) {
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
            return "/events/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "events";
        }

        @Override
        protected Class<Event> getResponseClass() {
            return Event.class;
        }
    }
}
