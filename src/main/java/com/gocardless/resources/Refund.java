package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a refund resource returned from the API.
 *
 * Refund objects represent (partial) refunds of a [payment](#core-endpoints-payment) back to the
 * [customer](#core-endpoints-customers).
 * 
 * GoCardless will notify you via a [webhook](#webhooks)
 * whenever a refund is created, and will update the `amount_refunded` property of the payment.
 * 
 *
 * _Note:_ A payment that has been (partially) refunded can still receive a late failure or
 * chargeback from the banks.
 */
public class Refund {
    private Refund() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String createdAt;
    private String currency;
    private String id;
    private Links links;
    private Map<String, String> metadata;

    /**
     * Amount in pence or cents.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Fixed [timestamp](#overview-time-zones-dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. This is set to the
     * currency of the refund's [payment](#core-endpoints-payments).
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Unique identifier, beginning with "RF".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
     * values up to 200 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String payment;

        /**
         * ID of the [payment](#core-endpoints-payments) against which the refund is being made.
         */
        public String getPayment() {
            return payment;
        }
    }
}
