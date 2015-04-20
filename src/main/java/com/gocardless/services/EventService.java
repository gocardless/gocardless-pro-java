package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Event;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with event resources.
 *
 * Events are stored for all webhooks. An event refers to a resource which has been updated, for
 * example a payment which has been collected, or a mandate which has been transferred.
 */
public class EventService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#events() }.
     */
    public EventService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your events.
     */
    public EventListRequest list() {
        return new EventListRequest(httpClient);
    }

    /**
     * Retrieves the details of a single event.
     */
    public EventGetRequest get(String identity) {
        return new EventGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link EventService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your events.
     */
    public static final class EventListRequest extends ListRequest<Event> {
        private String action;
        private CreatedAt createdAt;
        private Include include;
        private String mandate;
        private String parentEvent;
        private String payment;
        private String payout;
        private String refund;
        private ResourceType resourceType;
        private String subscription;

        /**
         * Limit to events with a given `action`.
         */
        public EventListRequest withAction(String action) {
            this.action = action;
            return this;
        }

        /**
         * Cursor pointing to the start of the desired set.
         */
        public EventListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public EventListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        public EventListRequest withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public EventListRequest withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public EventListRequest withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public EventListRequest withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public EventListRequest withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * Includes linked resources in the response. Must be used with the `resource_type` parameter
         * specified. The include should be one of:
         * <ul>
         * <li>`payment`</li>
         * <li>`mandate`</li>
         *
         * <li>`payout`</li>
         * <li>`refund`</li>
         * <li>`subscription`</li>
         * </ul>
         */
        public EventListRequest withInclude(Include include) {
            this.include = include;
            return this;
        }

        /**
         * Number of records to return.
         */
        public EventListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * ID of a [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates). If specified,
         * this endpoint will return all events for the given mandate.
         */
        public EventListRequest withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * ID of an event. If specified, this endpint will return all events whose parent_event is the given
         * event ID.
         */
        public EventListRequest withParentEvent(String parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        /**
         * ID of a [payment](https://developer.gocardless.com/pro/#api-endpoints-payments). If specified,
         * this endpoint will return all events for the given payment.
         */
        public EventListRequest withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        /**
         * ID of a [payout](https://developer.gocardless.com/pro/#api-endpoints-payouts). If specified, this
         * endpoint will return all events for the given payout.
         */
        public EventListRequest withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        /**
         * ID of a [refund](https://developer.gocardless.com/pro/#api-endpoints-refunds). If specified, this
         * endpoint will return all events for the given refund.
         */
        public EventListRequest withRefund(String refund) {
            this.refund = refund;
            return this;
        }

        /**
         * Type of resource that you'd like to get all events for. Cannot be used together with the
         * `payment`, `mandate`, `subscription`, `refund` or `payout` parameter. The type can be one of:
         *
         * <ul>
         * <li>`payments`</li>
         * <li>`mandates`</li>
         * <li>`payouts`</li>
         * <li>`subscriptions`</li>
         *
         * <li>`refunds`</li>
         * </ul>
         */
        public EventListRequest withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        /**
         * ID of a [subscription](#api-endpoints-subscriptions). If specified, this endpoint will return all
         * events for the given subscription.
         */
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
            params.putAll(super.getQueryParams());
            if (action != null) {
                params.put("action", action);
            }
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            if (include != null) {
                params.put("include", include);
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
            @SerializedName("payment")
            PAYMENT, @SerializedName("mandate")
            MANDATE, @SerializedName("payout")
            PAYOUT, @SerializedName("refund")
            REFUND, @SerializedName("subscription")
            SUBSCRIPTION;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum ResourceType {
            @SerializedName("payments")
            PAYMENTS, @SerializedName("mandates")
            MANDATES, @SerializedName("payouts")
            PAYOUTS, @SerializedName("refunds")
            REFUNDS, @SerializedName("subscriptions")
            SUBSCRIPTIONS;
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

            /**
             * Limit to records created after the specified date-time.
             */
            public CreatedAt withGt(String gt) {
                this.gt = gt;
                return this;
            }

            /**
             * Limit to records created on or after the specified date-time.
             */
            public CreatedAt withGte(String gte) {
                this.gte = gte;
                return this;
            }

            /**
             * Limit to records created before the specified date-time.
             */
            public CreatedAt withLt(String lt) {
                this.lt = lt;
                return this;
            }

            /**
             * Limit to records created on or before the specified date-time.
             */
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

    /**
     * Request class for {@link EventService#get }.
     *
     * Retrieves the details of a single event.
     */
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
