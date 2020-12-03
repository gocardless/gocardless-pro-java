package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.PayoutItem;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with payout item resources.
 *
 * When we collect a payment on your behalf, we add the money you've collected to your
 * GoCardless balance, minus any fees paid. Periodically (usually every working day),
 * we take any positive balance in your GoCardless account, and pay it out to your
 * nominated bank account.
 * 
 * Other actions in your GoCardless account can also affect your balance. For example,
 * if a customer charges back a payment, we'll deduct the payment's amount from your
 * balance, but add any fees you paid for that payment back to your balance.
 * 
 * The Payout Items API allows you to view, on a per-payout basis, the credit and debit
 * items that make up that payout's amount.
 * 
 */
public class PayoutItemService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#payoutItems() }.
     */
    public PayoutItemService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of items in the payout.
     * 
     */
    public PayoutItemListRequest<ListResponse<PayoutItem>> list() {
        return new PayoutItemListRequest<>(httpClient, ListRequest.<PayoutItem>pagingExecutor());
    }

    public PayoutItemListRequest<Iterable<PayoutItem>> all() {
        return new PayoutItemListRequest<>(httpClient, ListRequest.<PayoutItem>iteratingExecutor());
    }

    /**
     * Request class for {@link PayoutItemService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of items in the payout.
     * 
     */
    public static final class PayoutItemListRequest<S> extends ListRequest<S, PayoutItem> {
        private Include2020TaxCutover include2020TaxCutover;
        private String payout;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PayoutItemListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PayoutItemListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Boolean value indicating whether the API should return tax data for the cutover period of April to
         * August 2020. Defaults to false.
         */
        public PayoutItemListRequest<S> withInclude2020TaxCutover(
                Include2020TaxCutover include2020TaxCutover) {
            this.include2020TaxCutover = include2020TaxCutover;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PayoutItemListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "PO".
         */
        public PayoutItemListRequest<S> withPayout(String payout) {
            this.payout = payout;
            return this;
        }

        private PayoutItemListRequest(HttpClient httpClient,
                ListRequestExecutor<S, PayoutItem> executor) {
            super(httpClient, executor);
        }

        public PayoutItemListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (include2020TaxCutover != null) {
                params.put("include_2020_tax_cutover", include2020TaxCutover);
            }
            if (payout != null) {
                params.put("payout", payout);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "payout_items";
        }

        @Override
        protected String getEnvelope() {
            return "payout_items";
        }

        @Override
        protected TypeToken<List<PayoutItem>> getTypeToken() {
            return new TypeToken<List<PayoutItem>>() {};
        }

        public enum Include2020TaxCutover {
            @SerializedName("true")
            TRUE, @SerializedName("false")
            FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }
}
