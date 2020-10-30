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
 * example a payment which has been collected, or a mandate which has been transferred. See
 * [here](#event-actions) for a complete list of event types.
 */
public class EventService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#events() }.
     */
    public EventService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your events.
     */
    public EventListRequest<ListResponse<Event>> list() {
        return new EventListRequest<>(httpClient, ListRequest.<Event>pagingExecutor());
    }

    public EventListRequest<Iterable<Event>> all() {
        return new EventListRequest<>(httpClient, ListRequest.<Event>iteratingExecutor());
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
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your events.
     */
    public static final class EventListRequest<S> extends ListRequest<S, Event> {
        private String action;
        private CreatedAt createdAt;
        private Include include;
        private String mandate;
        private String parentEvent;
        private String payerAuthorisation;
        private String payment;
        private String payout;
        private String refund;
        private ResourceType resourceType;
        private String subscription;

        /**
         * Limit to events with a given `action`.
         */
        public EventListRequest<S> withAction(String action) {
            this.action = action;
            return this;
        }

        /**
         * Cursor pointing to the start of the desired set.
         */
        public EventListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public EventListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public EventListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public EventListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public EventListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public EventListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public EventListRequest<S> withCreatedAtLte(String lte) {
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
         * <li>`payer_authorisation`</li>
         * <li>`payout`</li>
         * <li>`refund`</li>
         * <li>`subscription`</li>
         * <li>`instalment_schedule`</li>
         * <li>`creditor`</li>
         * </ul>
         */
        public EventListRequest<S> withInclude(Include include) {
            this.include = include;
            return this;
        }

        /**
         * Number of records to return.
         */
        public EventListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * ID of a [mandate](#core-endpoints-mandates). If specified, this endpoint will return all events
         * for the given mandate.
         */
        public EventListRequest<S> withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * ID of an event. If specified, this endpoint will return all events whose parent_event is the given
         * event ID.
         */
        public EventListRequest<S> withParentEvent(String parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        /**
         * ID of a [payer authorisation](#core-endpoints-payer-authorisations).
         */
        public EventListRequest<S> withPayerAuthorisation(String payerAuthorisation) {
            this.payerAuthorisation = payerAuthorisation;
            return this;
        }

        /**
         * ID of a [payment](#core-endpoints-payments). If specified, this endpoint will return all events
         * for the given payment.
         */
        public EventListRequest<S> withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        /**
         * ID of a [payout](#core-endpoints-payouts). If specified, this endpoint will return all events for
         * the given payout.
         */
        public EventListRequest<S> withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        /**
         * ID of a [refund](#core-endpoints-refunds). If specified, this endpoint will return all events for
         * the given refund.
         */
        public EventListRequest<S> withRefund(String refund) {
            this.refund = refund;
            return this;
        }

        /**
         * Type of resource that you'd like to get all events for. Cannot be used together with the
         * `payment`,    `payer_authorisation`, `mandate`, `subscription`, `instalment_schedule`, `creditor`,
         * `refund` or `payout` parameter. The type can be one of:
         * <ul>
         * <li>`payments`</li>
         * <li>`mandates`</li>
         * <li>`payer_authorisations`</li>
         * <li>`payouts`</li>
         * <li>`subscriptions`</li>
         * <li>`instalment_schedules`</li>
         * <li>`creditors`</li>
         * <li>`refunds`</li>
         * </ul>
         */
        public EventListRequest<S> withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        /**
         * ID of a [subscription](#core-endpoints-subscriptions). If specified, this endpoint will return all
         * events for the given subscription.
         */
        public EventListRequest<S> withSubscription(String subscription) {
            this.subscription = subscription;
            return this;
        }

        private EventListRequest(HttpClient httpClient, ListRequestExecutor<S, Event> executor) {
            super(httpClient, executor);
        }

        public EventListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
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
            if (payerAuthorisation != null) {
                params.put("payer_authorisation", payerAuthorisation);
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
            return "events";
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
            SUBSCRIPTION, @SerializedName("instalment_schedule")
            INSTALMENT_SCHEDULE, @SerializedName("creditor")
            CREDITOR, @SerializedName("payer_authorisation")
            PAYER_AUTHORISATION;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum ResourceType {
            @SerializedName("creditors")
            CREDITORS, @SerializedName("instalment_schedules")
            INSTALMENT_SCHEDULES, @SerializedName("mandates")
            MANDATES, @SerializedName("payer_authorisations")
            PAYER_AUTHORISATIONS, @SerializedName("payments")
            PAYMENTS, @SerializedName("payouts")
            PAYOUTS, @SerializedName("refunds")
            REFUNDS, @SerializedName("subscriptions")
            SUBSCRIPTIONS, @SerializedName("organisations")
            ORGANISATIONS;
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

        public EventGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "events/:identity";
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
