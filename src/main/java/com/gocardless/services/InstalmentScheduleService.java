package com.gocardless.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.InstalmentSchedule;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with instalment schedule resources.
 *
 * Instalment schedules are objects which represent a collection of related payments, with the
 * intention to collect the `total_amount` specified. The API supports both schedule-based
 * creation (similar to subscriptions) as well as explicit selection of differing payment
 * amounts and charge dates.
 * 
 * Unlike subscriptions, the payments are created immediately, so the instalment schedule
 * cannot be modified once submitted and instead can only be cancelled (which will cancel
 * any of the payments which have not yet been submitted).
 * 
 * Customers will receive a single notification about the complete schedule of collection.
 * 
 */
public class InstalmentScheduleService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#instalmentSchedules() }.
     */
    public InstalmentScheduleService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new instalment schedule object, along with the associated payments. This
     * API is recommended if you know the specific dates you wish to charge. Otherwise,
     * please check out the [scheduling version](#instalment-schedules-create-with-schedule).
     * 
     * The `instalments` property is an array of payment properties (`amount` and
     * `charge_date`).
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent GET
     * request for the instalment schedule will either have the status `success` and link
     * to the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public InstalmentScheduleCreateWithDatesRequest createWithDates() {
        return new InstalmentScheduleCreateWithDatesRequest(httpClient);
    }

    /**
     * Creates a new instalment schedule object, along with the associated payments. This
     * API is recommended if you wish to use the GoCardless scheduling logic. For finer
     * control over the individual dates, please check out the [alternative
     * version](#instalment-schedules-create-with-dates).
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent
     * GET request for the instalment schedule will either have the status `success` and link to
     * the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public InstalmentScheduleCreateWithScheduleRequest createWithSchedule() {
        return new InstalmentScheduleCreateWithScheduleRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your instalment schedules.
     */
    public InstalmentScheduleListRequest<ListResponse<InstalmentSchedule>> list() {
        return new InstalmentScheduleListRequest<>(httpClient,
                ListRequest.<InstalmentSchedule>pagingExecutor());
    }

    public InstalmentScheduleListRequest<Iterable<InstalmentSchedule>> all() {
        return new InstalmentScheduleListRequest<>(httpClient,
                ListRequest.<InstalmentSchedule>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing instalment schedule.
     */
    public InstalmentScheduleGetRequest get(String identity) {
        return new InstalmentScheduleGetRequest(httpClient, identity);
    }

    /**
     * Immediately cancels an instalment schedule; no further payments will be collected for it.
     * 
     * This will fail with a `cancellation_failed` error if the instalment schedule is already cancelled
     * or has completed.
     */
    public InstalmentScheduleCancelRequest cancel(String identity) {
        return new InstalmentScheduleCancelRequest(httpClient, identity);
    }

    /**
     * Request class for {@link InstalmentScheduleService#createWithDates }.
     *
     * Creates a new instalment schedule object, along with the associated payments. This
     * API is recommended if you know the specific dates you wish to charge. Otherwise,
     * please check out the [scheduling version](#instalment-schedules-create-with-schedule).
     * 
     * The `instalments` property is an array of payment properties (`amount` and
     * `charge_date`).
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent GET
     * request for the instalment schedule will either have the status `success` and link
     * to the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public static final class InstalmentScheduleCreateWithDatesRequest extends
            IdempotentPostRequest<InstalmentSchedule> {
        private Integer appFee;
        private Currency currency;
        private List<Instalments> instalments;
        private Links links;
        private Map<String, String> metadata;
        private String name;
        private String paymentReference;
        private Boolean retryIfPossible;
        private Integer totalAmount;

        /**
         * The amount to be deducted from each payment as an app fee, to be paid to the partner integration
         * which created the subscription, in the lowest denomination for the currency (e.g. pence in GBP,
         * cents in EUR).
         */
        public InstalmentScheduleCreateWithDatesRequest withAppFee(Integer appFee) {
            this.appFee = appFee;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
         * "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public InstalmentScheduleCreateWithDatesRequest withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * An explicit array of instalment payments, each specifying at least an `amount` and `charge_date`.
         */
        public InstalmentScheduleCreateWithDatesRequest withInstalments(
                List<Instalments> instalments) {
            this.instalments = instalments;
            return this;
        }

        /**
         * An explicit array of instalment payments, each specifying at least an `amount` and `charge_date`.
         */
        public InstalmentScheduleCreateWithDatesRequest withInstalments(Instalments instalments) {
            if (this.instalments == null) {
                this.instalments = new ArrayList<>();
            }
            this.instalments.add(instalments);
            return this;
        }

        public InstalmentScheduleCreateWithDatesRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
         * payments against.
         */
        public InstalmentScheduleCreateWithDatesRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public InstalmentScheduleCreateWithDatesRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public InstalmentScheduleCreateWithDatesRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name of the instalment schedule, up to 100 chars. This name will also be
         * copied to the payments of the instalment schedule if you use schedule-based creation.
         */
        public InstalmentScheduleCreateWithDatesRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * An optional reference that will appear on your customer's bank statement. The character limit for
         * this reference is dependent on the scheme.<br /> <strong>ACH</strong> - 10 characters<br />
         * <strong>Autogiro</strong> - 11 characters<br /> <strong>Bacs</strong> - 10 characters<br />
         * <strong>BECS</strong> - 30 characters<br /> <strong>BECS NZ</strong> - 12 characters<br />
         * <strong>Betalingsservice</strong> - 30 characters<br /> <strong>PAD</strong> - 12 characters<br />
         * <strong>SEPA</strong> - 140 characters <p class='restricted-notice'><strong>Restricted</strong>:
         * You can only specify a payment reference for Bacs payments (that is, when collecting from the UK)
         * if you're on the <a href='https://gocardless.com/pricing'>GoCardless Plus, Pro or Enterprise
         * packages</a>.</p>
         */
        public InstalmentScheduleCreateWithDatesRequest withPaymentReference(String paymentReference) {
            this.paymentReference = paymentReference;
            return this;
        }

        /**
         * On failure, automatically retry payments using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         */
        public InstalmentScheduleCreateWithDatesRequest withRetryIfPossible(Boolean retryIfPossible) {
            this.retryIfPossible = retryIfPossible;
            return this;
        }

        /**
         * The total amount of the instalment schedule, defined as the sum of all individual
         * payments, in the lowest denomination for the currency (e.g. pence in GBP, cents in
         * EUR). If the requested payment amounts do not sum up correctly, a validation error
         * will be returned.
         */
        public InstalmentScheduleCreateWithDatesRequest withTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public InstalmentScheduleCreateWithDatesRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<InstalmentSchedule> handleConflict(HttpClient httpClient, String id) {
            InstalmentScheduleGetRequest request = new InstalmentScheduleGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private InstalmentScheduleCreateWithDatesRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public InstalmentScheduleCreateWithDatesRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "instalment_schedules";
        }

        @Override
        protected String getEnvelope() {
            return "instalment_schedules";
        }

        @Override
        protected Class<InstalmentSchedule> getResponseClass() {
            return InstalmentSchedule.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }

        public enum Currency {
            @SerializedName("AUD")
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Instalments {
            private Integer amount;
            private String chargeDate;
            private String description;

            /**
             * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             */
            public Instalments withAmount(Integer amount) {
                this.amount = amount;
                return this;
            }

            /**
             * A future date on which the payment should be collected. If the date
             * is before the next_possible_charge_date on the
             * [mandate](#core-endpoints-mandates), it will be automatically rolled
             * forwards to that date.
             */
            public Instalments withChargeDate(String chargeDate) {
                this.chargeDate = chargeDate;
                return this;
            }

            /**
             * A human-readable description of the payment. This will be included in the notification email
             * GoCardless sends to your customer if your organisation does not send its own notifications (see
             * [compliance requirements](#appendix-compliance-requirements)).
             */
            public Instalments withDescription(String description) {
                this.description = description;
                return this;
            }
        }

        public static class Links {
            private String mandate;

            /**
             * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
             * payments against.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }

    /**
     * Request class for {@link InstalmentScheduleService#createWithSchedule }.
     *
     * Creates a new instalment schedule object, along with the associated payments. This
     * API is recommended if you wish to use the GoCardless scheduling logic. For finer
     * control over the individual dates, please check out the [alternative
     * version](#instalment-schedules-create-with-dates).
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent
     * GET request for the instalment schedule will either have the status `success` and link to
     * the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public static final class InstalmentScheduleCreateWithScheduleRequest extends
            IdempotentPostRequest<InstalmentSchedule> {
        private Integer appFee;
        private Currency currency;
        private Instalments instalments;
        private Links links;
        private Map<String, String> metadata;
        private String name;
        private String paymentReference;
        private Boolean retryIfPossible;
        private Integer totalAmount;

        /**
         * The amount to be deducted from each payment as an app fee, to be paid to the partner integration
         * which created the subscription, in the lowest denomination for the currency (e.g. pence in GBP,
         * cents in EUR).
         */
        public InstalmentScheduleCreateWithScheduleRequest withAppFee(Integer appFee) {
            this.appFee = appFee;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
         * "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public InstalmentScheduleCreateWithScheduleRequest withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Frequency of the payments you want to create, together with an array of payment
         * amounts to be collected, with a specified start date for the first payment.
         * 
         */
        public InstalmentScheduleCreateWithScheduleRequest withInstalments(Instalments instalments) {
            this.instalments = instalments;
            return this;
        }

        /**
         * List of amounts of each instalment, in the lowest denomination for the
         * currency (e.g. pence in GBP, cents in EUR).
         * 
         */
        public InstalmentScheduleCreateWithScheduleRequest withInstalmentsAmounts(
                List<Integer> amounts) {
            if (instalments == null) {
                instalments = new Instalments();
            }
            instalments.withAmounts(amounts);
            return this;
        }

        /**
         * Number of `interval_units` between charge dates. Must be greater than or
         * equal to `1`.
         * 
         */
        public InstalmentScheduleCreateWithScheduleRequest withInstalmentsInterval(Integer interval) {
            if (instalments == null) {
                instalments = new Instalments();
            }
            instalments.withInterval(interval);
            return this;
        }

        /**
         * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
         */
        public InstalmentScheduleCreateWithScheduleRequest withInstalmentsIntervalUnit(
                Instalments.IntervalUnit intervalUnit) {
            if (instalments == null) {
                instalments = new Instalments();
            }
            instalments.withIntervalUnit(intervalUnit);
            return this;
        }

        /**
         * The date on which the first payment should be charged. Must be on or after the
         * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When blank, this will be set as
         * the mandate's `next_possible_charge_date`.
         */
        public InstalmentScheduleCreateWithScheduleRequest withInstalmentsStartDate(String startDate) {
            if (instalments == null) {
                instalments = new Instalments();
            }
            instalments.withStartDate(startDate);
            return this;
        }

        public InstalmentScheduleCreateWithScheduleRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
         * payments against.
         */
        public InstalmentScheduleCreateWithScheduleRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public InstalmentScheduleCreateWithScheduleRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public InstalmentScheduleCreateWithScheduleRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name of the instalment schedule, up to 100 chars. This name will also be
         * copied to the payments of the instalment schedule if you use schedule-based creation.
         */
        public InstalmentScheduleCreateWithScheduleRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * An optional reference that will appear on your customer's bank statement. The character limit for
         * this reference is dependent on the scheme.<br /> <strong>ACH</strong> - 10 characters<br />
         * <strong>Autogiro</strong> - 11 characters<br /> <strong>Bacs</strong> - 10 characters<br />
         * <strong>BECS</strong> - 30 characters<br /> <strong>BECS NZ</strong> - 12 characters<br />
         * <strong>Betalingsservice</strong> - 30 characters<br /> <strong>PAD</strong> - 12 characters<br />
         * <strong>SEPA</strong> - 140 characters <p class='restricted-notice'><strong>Restricted</strong>:
         * You can only specify a payment reference for Bacs payments (that is, when collecting from the UK)
         * if you're on the <a href='https://gocardless.com/pricing'>GoCardless Plus, Pro or Enterprise
         * packages</a>.</p>
         */
        public InstalmentScheduleCreateWithScheduleRequest withPaymentReference(
                String paymentReference) {
            this.paymentReference = paymentReference;
            return this;
        }

        /**
         * On failure, automatically retry payments using [intelligent
         * retries](#success-intelligent-retries). Default is `false`.
         */
        public InstalmentScheduleCreateWithScheduleRequest withRetryIfPossible(
                Boolean retryIfPossible) {
            this.retryIfPossible = retryIfPossible;
            return this;
        }

        /**
         * The total amount of the instalment schedule, defined as the sum of all individual
         * payments, in the lowest denomination for the currency (e.g. pence in GBP, cents in
         * EUR). If the requested payment amounts do not sum up correctly, a validation error
         * will be returned.
         */
        public InstalmentScheduleCreateWithScheduleRequest withTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public InstalmentScheduleCreateWithScheduleRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<InstalmentSchedule> handleConflict(HttpClient httpClient, String id) {
            InstalmentScheduleGetRequest request = new InstalmentScheduleGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private InstalmentScheduleCreateWithScheduleRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public InstalmentScheduleCreateWithScheduleRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "instalment_schedules";
        }

        @Override
        protected String getEnvelope() {
            return "instalment_schedules";
        }

        @Override
        protected Class<InstalmentSchedule> getResponseClass() {
            return InstalmentSchedule.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }

        public enum Currency {
            @SerializedName("AUD")
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Instalments {
            private List<Integer> amounts;
            private Integer interval;
            private IntervalUnit intervalUnit;
            private String startDate;

            /**
             * List of amounts of each instalment, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             * 
             */
            public Instalments withAmounts(List<Integer> amounts) {
                this.amounts = amounts;
                return this;
            }

            /**
             * Number of `interval_units` between charge dates. Must be greater than or
             * equal to `1`.
             * 
             */
            public Instalments withInterval(Integer interval) {
                this.interval = interval;
                return this;
            }

            /**
             * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
             */
            public Instalments withIntervalUnit(IntervalUnit intervalUnit) {
                this.intervalUnit = intervalUnit;
                return this;
            }

            /**
             * The date on which the first payment should be charged. Must be on or after the
             * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When blank, this will be set as
             * the mandate's `next_possible_charge_date`.
             */
            public Instalments withStartDate(String startDate) {
                this.startDate = startDate;
                return this;
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
        }

        public static class Links {
            private String mandate;

            /**
             * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
             * payments against.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }

    /**
     * Request class for {@link InstalmentScheduleService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your instalment schedules.
     */
    public static final class InstalmentScheduleListRequest<S> extends
            ListRequest<S, InstalmentSchedule> {
        private CreatedAt createdAt;
        private String customer;
        private String mandate;
        private List<Status> status;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public InstalmentScheduleListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public InstalmentScheduleListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        public InstalmentScheduleListRequest<S> withCreatedAt(CreatedAt createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Limit to records created after the specified date-time.
         */
        public InstalmentScheduleListRequest<S> withCreatedAtGt(String gt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGt(gt);
            return this;
        }

        /**
         * Limit to records created on or after the specified date-time.
         */
        public InstalmentScheduleListRequest<S> withCreatedAtGte(String gte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withGte(gte);
            return this;
        }

        /**
         * Limit to records created before the specified date-time.
         */
        public InstalmentScheduleListRequest<S> withCreatedAtLt(String lt) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLt(lt);
            return this;
        }

        /**
         * Limit to records created on or before the specified date-time.
         */
        public InstalmentScheduleListRequest<S> withCreatedAtLte(String lte) {
            if (createdAt == null) {
                createdAt = new CreatedAt();
            }
            createdAt.withLte(lte);
            return this;
        }

        /**
         * ID of the associated [customer](#core-endpoints-customers).
         */
        public InstalmentScheduleListRequest<S> withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Number of records to return.
         */
        public InstalmentScheduleListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
         * payments against.
         */
        public InstalmentScheduleListRequest<S> withMandate(String mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * At most five valid status values
         */
        public InstalmentScheduleListRequest<S> withStatus(List<Status> status) {
            this.status = status;
            return this;
        }

        /**
         * At most five valid status values
         */
        public InstalmentScheduleListRequest<S> withStatus(Status status) {
            if (this.status == null) {
                this.status = new ArrayList<>();
            }
            this.status.add(status);
            return this;
        }

        private InstalmentScheduleListRequest(HttpClient httpClient,
                ListRequestExecutor<S, InstalmentSchedule> executor) {
            super(httpClient, executor);
        }

        public InstalmentScheduleListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (createdAt != null) {
                params.putAll(createdAt.getQueryParams());
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (mandate != null) {
                params.put("mandate", mandate);
            }
            if (status != null) {
                params.put("status", Joiner.on(",").join(status));
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "instalment_schedules";
        }

        @Override
        protected String getEnvelope() {
            return "instalment_schedules";
        }

        @Override
        protected TypeToken<List<InstalmentSchedule>> getTypeToken() {
            return new TypeToken<List<InstalmentSchedule>>() {};
        }

        public enum Status {
            @SerializedName("pending")
            PENDING, @SerializedName("active")
            ACTIVE, @SerializedName("creation_failed")
            CREATION_FAILED, @SerializedName("completed")
            COMPLETED, @SerializedName("cancelled")
            CANCELLED, @SerializedName("errored")
            ERRORED;
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
     * Request class for {@link InstalmentScheduleService#get }.
     *
     * Retrieves the details of an existing instalment schedule.
     */
    public static final class InstalmentScheduleGetRequest extends GetRequest<InstalmentSchedule> {
        @PathParam
        private final String identity;

        private InstalmentScheduleGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public InstalmentScheduleGetRequest withHeader(String headerName, String headerValue) {
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
            return "instalment_schedules/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "instalment_schedules";
        }

        @Override
        protected Class<InstalmentSchedule> getResponseClass() {
            return InstalmentSchedule.class;
        }
    }

    /**
     * Request class for {@link InstalmentScheduleService#cancel }.
     *
     * Immediately cancels an instalment schedule; no further payments will be collected for it.
     * 
     * This will fail with a `cancellation_failed` error if the instalment schedule is already cancelled
     * or has completed.
     */
    public static final class InstalmentScheduleCancelRequest extends
            PostRequest<InstalmentSchedule> {
        @PathParam
        private final String identity;

        private InstalmentScheduleCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public InstalmentScheduleCancelRequest withHeader(String headerName, String headerValue) {
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
            return "instalment_schedules/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "instalment_schedules";
        }

        @Override
        protected Class<InstalmentSchedule> getResponseClass() {
            return InstalmentSchedule.class;
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
