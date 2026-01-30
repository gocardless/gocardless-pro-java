package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.FundsAvailability;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with funds availability resources.
 *
 * Checks if the payer's current balance is sufficient to cover the amount the merchant wants to
 * charge within the consent parameters defined on the mandate.
 */
public class FundsAvailabilityService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#fundsAvailabilities() }.
     */
    public FundsAvailabilityService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Checks if the payer's current balance is sufficient to cover the amount the merchant wants to
     * charge within the consent parameters defined on the mandate.
     */
    public FundsAvailabilityCheckRequest check(String identity) {
        return new FundsAvailabilityCheckRequest(httpClient, identity);
    }

    /**
     * Request class for {@link FundsAvailabilityService#check }.
     *
     * Checks if the payer's current balance is sufficient to cover the amount the merchant wants to
     * charge within the consent parameters defined on the mandate.
     */
    public static final class FundsAvailabilityCheckRequest extends GetRequest<FundsAvailability> {
        @PathParam
        private final String identity;
        private String amount;

        /**
         * The amount of the payment
         */
        public FundsAvailabilityCheckRequest withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        private FundsAvailabilityCheckRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public FundsAvailabilityCheckRequest withHeader(String headerName, String headerValue) {
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
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (amount != null) {
                params.put("amount", amount);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "funds_availability/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "funds_availability";
        }

        @Override
        protected Class<FundsAvailability> getResponseClass() {
            return FundsAvailability.class;
        }
    }
}
