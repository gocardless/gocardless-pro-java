package com.gocardless.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Subscription;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with subscription resources.
 *
 * Subscriptions create [payments](https://developer.gocardless.com/pro/#api-endpoints-payments)
 * according to a schedule.
 * 
 * #### Recurrence Rules
 * 
 * The following rules apply when specifying
 * recurrence:
 * - The first payment must be charged within 1 year.
 * - When neither `month` nor
 * `day_of_month` are present, the subscription will recur from the `start_at` based on the
 * `interval_unit`.
 * - If `month` or `day_of_month` are present, the recurrence rules will be
 * applied from the `start_at`, and the following validations apply:
 * 
 * | interval_unit   | month  
 *                                        | day_of_month                            |
 * |
 * :-------------- | :--------------------------------------------- |
 * :-------------------------------------- |
 * | yearly          | optional (required if
 * `day_of_month` provided) | optional (required if `month` provided) |
 * | monthly         | invalid
 *                                        | required                                |
 * | weekly     
 *     | invalid                                        | invalid                                 |
 *
 * 
 * Examples:
 * 
 * | interval_unit   | interval   | month   | day_of_month   | valid?              
 *                               |
 * | :-------------- | :--------- | :------ | :------------- |
 * :------------------------------------------------- |
 * | yearly          | 1          | january |
 * -1             | valid                                              |
 * | yearly          | 1     
 *     | march   |                | invalid - missing `day_of_month`                   |
 * | monthly 
 *        | 6          |         | 12             | valid                                            
 *  |
 * | monthly         | 6          | august  | 12             | invalid - `month` must be blank  
 *                  |
 * | weekly          | 2          |         |                | valid            
 *                                  |
 * | weekly          | 2          | october | 10             |
 * invalid - `month` and `day_of_month` must be blank |
 * 
 * #### Rolling dates
 * 
 * When a charge
 * date falls on a non-business day, one of two things will happen:
 * 
 * - if the recurrence rule
 * specified `-1` as the `day_of_month`, the charge date will be rolled __backwards__ to the previous
 * business day (i.e., the last working day of the month).
 * - otherwise the charge date will be
 * rolled __forwards__ to the next business day.
 * 
 */
public class SubscriptionService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#subscriptions() }.
     */
    public SubscriptionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new subscription object
     */
    public SubscriptionCreateRequest create() {
        return new SubscriptionCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your subscriptions.
     */
    public SubscriptionListRequest list() {
        return new SubscriptionListRequest(httpClient);
    }

    /**
     * Retrieves the details of a single subscription.
     */
    public SubscriptionGetRequest get(String identity) {
        return new SubscriptionGetRequest(httpClient, identity);
    }

    /**
     * Updates a subscription object.
     */
    public SubscriptionUpdateRequest update(String identity) {
        return new SubscriptionUpdateRequest(httpClient, identity);
    }

    /**
     * Immediately cancels a subscription; no more payments will be created under it. Any metadata
     * supplied to this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This
     * will fail with a cancellation_failed error if the subscription is already cancelled or finished.
     */
    public SubscriptionCancelRequest cancel(String identity) {
        return new SubscriptionCancelRequest(httpClient, identity);
    }

    /**
     * Request class for {@link SubscriptionService#create }.
     *
     * Creates a new subscription object
     */
    public static final class SubscriptionCreateRequest extends PostRequest<Subscription> {
        private Integer amount;
        private Integer count;
        private String currency;
        private Integer dayOfMonth;
        private String endAt;
        private Integer interval;
        private IntervalUnit intervalUnit;
        private Links links;
        private Map<String, String> metadata;
        private Month month;
        private String name;
        private String startAt;

        /**
         * Amount in pence or cents.
         */
        public SubscriptionCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * An alternative way to set `end_at`. The total number of payments that should be taken by this
         * subscription. This will set `end_at` automatically.
         */
        public SubscriptionCreateRequest withCount(Integer count) {
            this.count = count;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code. Currently only `GBP` and `EUR`
         * are supported.
         */
        public SubscriptionCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to indicate the
         * last day of the month
         */
        public SubscriptionCreateRequest withDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        /**
         * Date after which no further payments should be charged. If a payment falls on this date, it **will
         * not** be charged. If blank, the subscription will continue forever. Alternatively, `count` can be
         * set to achieve a specific number of payments.
         */
        public SubscriptionCreateRequest withEndAt(String endAt) {
            this.endAt = endAt;
            return this;
        }

        /**
         * Number of `interval_units` between customer charge dates. Must result in at least one charge date
         * per year. Defaults to `1`.
         */
        public SubscriptionCreateRequest withInterval(Integer interval) {
            this.interval = interval;
            return this;
        }

        /**
         * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
         */
        public SubscriptionCreateRequest withIntervalUnit(IntervalUnit intervalUnit) {
            this.intervalUnit = intervalUnit;
            return this;
        }

        public SubscriptionCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates) which
         * the subscription will create payments against.
         */
        public SubscriptionCreateRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name of the month on which to charge a customer. Must be lowercase.
         */
        public SubscriptionCreateRequest withMonth(Month month) {
            this.month = month;
            return this;
        }

        /**
         * Optional name for the subscription. This field must not exceed 255 characters.
         */
        public SubscriptionCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The date on which the first payment should be charged. Must be within one year of creation and on
         * or after the [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates)'s
         * `next_possible_charge_date`. When blank, this will be set as the mandate's
         * `next_possible_charge_date`.
         */
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

        public enum IntervalUnit {
            @SerializedName("weekly")
            WEEKLY, @SerializedName("monthly")
            MONTHLY, @SerializedName("yearly")
            YEARLY;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public enum Month {
            @SerializedName("january")
            JANUARY, @SerializedName("february")
            FEBRUARY, @SerializedName("march")
            MARCH, @SerializedName("april")
            APRIL, @SerializedName("may")
            MAY, @SerializedName("june")
            JUNE, @SerializedName("july")
            JULY, @SerializedName("august")
            AUGUST, @SerializedName("september")
            SEPTEMBER, @SerializedName("october")
            OCTOBER, @SerializedName("november")
            NOVEMBER, @SerializedName("december")
            DECEMBER;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String mandate;

            /**
             * ID of the associated [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates) which
             * the subscription will create payments against.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }

    /**
     * Request class for {@link SubscriptionService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your subscriptions.
     */
    public static final class SubscriptionListRequest extends ListRequest<Subscription> {
        private String customer;
        private String mandate;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public SubscriptionListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public SubscriptionListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Unique identifier, beginning with "CU".
         */
        public SubscriptionListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Number of records to return.
         */
        public SubscriptionListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "MD"
         */
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
            params.putAll(super.getQueryParams());
            if (customer != null) {
                params.put("customer", customer);
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

    /**
     * Request class for {@link SubscriptionService#get }.
     *
     * Retrieves the details of a single subscription.
     */
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

    /**
     * Request class for {@link SubscriptionService#update }.
     *
     * Updates a subscription object.
     */
    public static final class SubscriptionUpdateRequest extends PutRequest<Subscription> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;
        private String name;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Optional name for the subscription. This field must not exceed 255 characters.
         */
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

    /**
     * Request class for {@link SubscriptionService#cancel }.
     *
     * Immediately cancels a subscription; no more payments will be created under it. Any metadata
     * supplied to this endpoint will be stored on the payment cancellation event it causes.
     * 
     * This
     * will fail with a cancellation_failed error if the subscription is already cancelled or finished.
     */
    public static final class SubscriptionCancelRequest extends PostRequest<Subscription> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionCancelRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public SubscriptionCancelRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
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

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
