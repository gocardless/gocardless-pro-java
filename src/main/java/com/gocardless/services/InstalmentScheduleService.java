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
     * Creates a new instalment schedule object, along with the associated payments.
     * 
     * The `instalments` property can either be an array of payment properties (`amount`
     * and `charge_date`) or a schedule object with `interval`, `interval_unit` and
     * `amounts`.
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent
     * GET request for the instalment schedule will either have the status `success` and link to
     * the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public InstalmentScheduleCreateRequest create() {
        return new InstalmentScheduleCreateRequest(httpClient);
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
     * Request class for {@link InstalmentScheduleService#create }.
     *
     * Creates a new instalment schedule object, along with the associated payments.
     * 
     * The `instalments` property can either be an array of payment properties (`amount`
     * and `charge_date`) or a schedule object with `interval`, `interval_unit` and
     * `amounts`.
     * 
     * It can take quite a while to create the associated payments, so the API will return
     * the status as `pending` initially. When processing has completed, a subsequent
     * GET request for the instalment schedule will either have the status `success` and link to
     * the created payments, or the status `error` and detailed information about the
     * failures.
     */
    public static final class InstalmentScheduleCreateRequest extends
            IdempotentPostRequest<InstalmentSchedule> {
        private Integer appFee;
        private Currency currency;
        private Map<String, String> instalments;
        private Links links;
        private Map<String, String> metadata;
        private String name;
        private String paymentReference;
        private Integer totalAmount;

        /**
         * The amount to be deducted from each payment as an app fee, to be paid to the partner integration
         * which created the subscription, in the lowest denomination for the currency (e.g. pence in GBP,
         * cents in EUR).
         */
        public InstalmentScheduleCreateRequest withAppFee(Integer appFee) {
            this.appFee = appFee;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
         * "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public InstalmentScheduleCreateRequest withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public InstalmentScheduleCreateRequest withInstalments(Map<String, String> instalments) {
            this.instalments = instalments;
            return this;
        }

        public InstalmentScheduleCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule will create
         * payments against.
         */
        public InstalmentScheduleCreateRequest withLinksMandate(String mandate) {
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
        public InstalmentScheduleCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 500 characters.
         */
        public InstalmentScheduleCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Name of the instalment schedule, up to 100 chars. This name will also be
         * copied to the payments of the instalment schedule if you use schedule-based creation.
         * 
         */
        public InstalmentScheduleCreateRequest withName(String name) {
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
        public InstalmentScheduleCreateRequest withPaymentReference(String paymentReference) {
            this.paymentReference = paymentReference;
            return this;
        }

        /**
         * The total amount of the instalment schedule, defined as the sum of all individual
         * payments. If the requested payment amounts do not sum up correctly, a validation
         * error will be returned.
         * 
         */
        public InstalmentScheduleCreateRequest withTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public InstalmentScheduleCreateRequest withIdempotencyKey(String idempotencyKey) {
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

        private InstalmentScheduleCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public InstalmentScheduleCreateRequest withHeader(String headerName, String headerValue) {
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
