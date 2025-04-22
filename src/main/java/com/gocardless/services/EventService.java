package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Event;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with event resources.
 *
 * Events are stored for all webhooks. An event refers to a resource which has been updated, for
 * example a payment which has been collected, or a mandate which has been transferred. Event
 * creation is an asynchronous process, so it can take some time between an action occurring and its
 * corresponding event getting included in API responses. See [here](#event-actions) for a complete
 * list of event types.
 */
public class EventService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#events() }.
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
        private String billingRequest;
        private CreatedAt createdAt;
        private String creditor;
        private String export;
        private Include include;
        private String instalmentSchedule;
        private String mandate;
        private String outboundPayment;
        private String parentEvent;
        private String payerAuthorisation;
        private String payment;
        private String payout;
        private String refund;
        private ResourceType resourceType;
        private String schemeIdentifier;
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

        /**
         * ID of a [billing request](#billing-requests-billing-requests). If specified, this
         * endpoint will return all events for the given billing request.
         */
        public EventListRequest<S> withBillingRequest(String billingRequest) {
            this.billingRequest = billingRequest;
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
         * ID of an [creditor](#core-endpoints-creditors). If specified, this endpoint will return
         * all events for the given creditor.
         */
        public EventListRequest<S> withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * ID of an export. If specified, this endpoint will return all events for the given export.
         */
        public EventListRequest<S> withExport(String export) {
            this.export = export;
            return this;
        }

        /**
         * Includes linked resources in the response. Must be used with the `resource_type`
         * parameter specified. The include should be one of:
         * <ul>
         * <li>`billing_request`</li>
         * <li>`creditor`</li>
         * <li>`instalment_schedule`</li>
         * <li>`mandate`</li>
         * <li>`payer_authorisation`</li>
         * <li>`payment`</li>
         * <li>`payout`</li>
         * <li>`refund`</li>
         * <li>`scheme_identifier`</li>
         * <li>`subscription`</li>
         * </ul>
         */
        public EventListRequest<S> withInclude(Include include) {
            this.include = include;
            return this;
        }

        /**
         * ID of an [instalment schedule](#core-endpoints-instalment-schedules). If specified, this
         * endpoint will return all events for the given instalment schedule.
         */
        public EventListRequest<S> withInstalmentSchedule(String instalmentSchedule) {
            this.instalmentSchedule = instalmentSchedule;
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
         * ID of a [mandate](#core-endpoints-mandates). If specified, this endpoint will return all
         * events for the given mandate.
         */
        public EventListRequest<S> withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * ID of an outbound_payment. If specified, this endpoint will return all events for the
         * given payment.
         */
        public EventListRequest<S> withOutboundPayment(String outboundPayment) {
            this.outboundPayment = outboundPayment;
            return this;
        }

        /**
         * ID of an event. If specified, this endpoint will return all events whose parent_event is
         * the given event ID.
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
         * ID of a [payment](#core-endpoints-payments). If specified, this endpoint will return all
         * events for the given payment.
         */
        public EventListRequest<S> withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        /**
         * ID of a [payout](#core-endpoints-payouts). If specified, this endpoint will return all
         * events for the given payout.
         */
        public EventListRequest<S> withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        /**
         * ID of a [refund](#core-endpoints-refunds). If specified, this endpoint will return all
         * events for the given refund.
         */
        public EventListRequest<S> withRefund(String refund) {
            this.refund = refund;
            return this;
        }

        /**
         * Type of resource that you'd like to get all events for. Cannot be used together with the
         * `billing_request`, `creditor`, `export`,`instalment_schedule`, `mandate`,
         * `payer_authorisation`, `payment`, `payout`, `refund`, `scheme_identifier` or
         * `subscription` parameters. The type can be one of:
         * <ul>
         * <li>`billing_requests`</li>
         * <li>`creditors`</li>
         * <li>`exports`</li>
         * <li>`instalment_schedules`</li>
         * <li>`mandates`</li>
         * <li>`payer_authorisations`</li>
         * <li>`payments`</li>
         * <li>`payouts`</li>
         * <li>`refunds`</li>
         * <li>`scheme_identifiers`</li>
         * <li>`subscriptions`</li>
         * </ul>
         */
        public EventListRequest<S> withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        /**
         * ID of a [scheme identifier](#core-endpoints-scheme-identifiers). If specified, this
         * endpoint will return all events for the given scheme identifier.
         */
        public EventListRequest<S> withSchemeIdentifier(String schemeIdentifier) {
            this.schemeIdentifier = schemeIdentifier;
            return this;
        }

        /**
         * ID of a [subscription](#core-endpoints-subscriptions). If specified, this endpoint will
         * return all events for the given subscription.
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
            if (billingRequest != null) {
                params.put("billing_request", billingRequest);
            }
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (export != null) {
                params.put("export", export);
            }
            if (include != null) {
                params.put("include", include);
            }
            if (instalmentSchedule != null) {
                params.put("instalment_schedule", instalmentSchedule);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            if (outboundPayment != null) {
                params.put("outbound_payment", outboundPayment);
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
            if (schemeIdentifier != null) {
                params.put("scheme_identifier", schemeIdentifier);
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
            @SerializedName("billing_request")
            BILLING_REQUEST, @SerializedName("creditor")
            CREDITOR, @SerializedName("instalment_schedule")
            INSTALMENT_SCHEDULE, @SerializedName("mandate")
            MANDATE, @SerializedName("outbound_payment")
            OUTBOUND_PAYMENT, @SerializedName("payer_authorisation")
            PAYER_AUTHORISATION, @SerializedName("payment")
            PAYMENT, @SerializedName("payout")
            PAYOUT, @SerializedName("refund")
            REFUND, @SerializedName("scheme_identifier")
            SCHEME_IDENTIFIER, @SerializedName("subscription")
            SUBSCRIPTION, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum ResourceType {
            @SerializedName("billing_requests")
            BILLING_REQUESTS, @SerializedName("creditors")
            CREDITORS, @SerializedName("exports")
            EXPORTS, @SerializedName("instalment_schedules")
            INSTALMENT_SCHEDULES, @SerializedName("mandates")
            MANDATES, @SerializedName("organisations")
            ORGANISATIONS, @SerializedName("outbound_payments")
            OUTBOUND_PAYMENTS, @SerializedName("payer_authorisations")
            PAYER_AUTHORISATIONS, @SerializedName("payments")
            PAYMENTS, @SerializedName("payouts")
            PAYOUTS, @SerializedName("refunds")
            REFUNDS, @SerializedName("scheme_identifiers")
            SCHEME_IDENTIFIERS, @SerializedName("subscriptions")
            SUBSCRIPTIONS, @SerializedName("outbound_payment")
            OUTBOUND_PAYMENT, @SerializedName("unknown")
            UNKNOWN;

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
