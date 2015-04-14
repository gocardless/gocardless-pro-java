package com.gocardless.pro.services;

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

    public EventListRequest list() {
        return new EventListRequest(httpClient);
    }

    public EventGetRequest get(String identity) {
        return new EventGetRequest(httpClient, identity);
    }

    public static final class EventListRequest extends ListRequest<Event> {
        private String action;
        private String after;
        private String before;
        private CreatedAt createdAt;
        private Include include;
        private Integer limit;
        private String mandate;
        private String parentEvent;
        private String payment;
        private String payout;
        private String refund;
        private ResourceType resourceType;
        private String subscription;

        public EventListRequest withAction(String action) {
            this.action = action;
            return this;
        }

        public EventListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public EventListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public EventListRequest withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventListRequest withInclude(Include include) {
            this.include = include;
            return this;
        }

        public EventListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public EventListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        public EventListRequest withParentEvent(String parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        public EventListRequest withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        public EventListRequest withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        public EventListRequest withRefund(String refund) {
            this.refund = refund;
            return this;
        }

        public EventListRequest withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

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
                params.putAll(createdAt.getQueryParams());
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

        public enum Include {
            PAYMENT, MANDATE, PAYOUT, REFUND, SUBSCRIPTION;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum ResourceType {
            PAYMENTS, MANDATES, PAYOUTS, REFUNDS, SUBSCRIPTIONS;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
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
