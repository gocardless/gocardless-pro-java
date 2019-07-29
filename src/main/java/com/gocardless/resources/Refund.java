package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a refund resource returned from the API.
 *
 * Refund objects represent (partial) refunds of a [payment](#core-endpoints-payments) back to the
 * [customer](#core-endpoints-customers).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) whenever a refund is created, and
 * will update the `amount_refunded` property of the payment.
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
    private String reference;

    /**
     * Amount in minor unit (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
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
     * values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * An optional reference that will appear on your customer's bank statement. The character limit for
     * this reference is dependent on the scheme.<br /> <strong>ACH</strong> - 10 characters<br />
     * <strong>Autogiro</strong> - 11 characters<br /> <strong>Bacs</strong> - 10 characters<br />
     * <strong>BECS</strong> - 30 characters<br /> <strong>BECS NZ</strong> - 12 characters<br />
     * <strong>Betalingsservice</strong> - 30 characters<br /> <strong>PAD</strong> - 12 characters<br />
     * <strong>SEPA</strong> - 140 characters <p class='restricted-notice'><strong>Restricted</strong>:
     * You can only specify a payment reference for Bacs payments (that is, when collecting from the UK)
     * if you're on the <a href='https://gocardless.com/pricing'>GoCardless Plus or Pro packages</a>.</p>
     */
    public String getReference() {
        return reference;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;
        private String payment;

        /**
         * ID of the [mandate](#core-endpoints-mandates) against which the refund is being made.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of the [payment](#core-endpoints-payments) against which the refund is being made.
         */
        public String getPayment() {
            return payment;
        }
    }
}
