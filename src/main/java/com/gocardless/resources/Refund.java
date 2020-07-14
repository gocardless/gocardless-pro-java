package com.gocardless.resources;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

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
    private Fx fx;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String reference;
    private Status status;

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

    public Fx getFx() {
        return fx;
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
     * <strong>SEPA</strong> - 140 characters<br /> Note that this reference must be unique (for each
     * merchant) for the BECS scheme as it is a scheme requirement. <p
     * class='restricted-notice'><strong>Restricted</strong>: You can only specify a payment reference
     * for Bacs payments (that is, when collecting from the UK) if you're on the <a
     * href='https://gocardless.com/pricing'>GoCardless Plus, Pro or Enterprise packages</a>.</p>
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * <ul>
     * <li>`created`: the refund has been created</li>
     * <li>`pending_submission`: the refund has been created, but not yet submitted to the banks</li>
     * <li>`submitted`: the refund has been submitted to the banks</li>
     * <li>`paid`:  the refund has been included in a [payout](#core-endpoints-payouts)</li>
     * <li>`cancelled`: the refund has been cancelled</li>
     * <li>`bounced`: the refund has failed to be paid</li>
     * <li>`funds_returned`: the refund has had its funds returned</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("created")
        CREATED, @SerializedName("pending_submission")
        PENDING_SUBMISSION, @SerializedName("submitted")
        SUBMITTED, @SerializedName("paid")
        PAID, @SerializedName("cancelled")
        CANCELLED, @SerializedName("bounced")
        BOUNCED, @SerializedName("funds_returned")
        FUNDS_RETURNED,
    }

    public static class Fx {
        private Fx() {
            // blank to prevent instantiation
        }

        private String estimatedExchangeRate;
        private String exchangeRate;
        private Integer fxAmount;
        private FxCurrency fxCurrency;

        /**
         * Estimated rate that will be used in the foreign exchange of the `amount` into the `fx_currency`.
         * This will vary based on the prevailing market rate until the moment that it is paid out.
         * Present only before a resource is paid out. Has up to 10 decimal places.
         */
        public String getEstimatedExchangeRate() {
            return estimatedExchangeRate;
        }

        /**
         * Rate used in the foreign exchange of the `amount` into the `fx_currency`.
         * Present only after a resource is paid out. Has up to 10 decimal places.
         */
        public String getExchangeRate() {
            return exchangeRate;
        }

        /**
         * Amount that was paid out in the `fx_currency` after foreign exchange.
         * Present only after the resource has been paid out.
         */
        public Integer getFxAmount() {
            return fxAmount;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) code for the currency in which
         * amounts will be paid out (after foreign exchange). Currently "AUD", "CAD", "DKK", "EUR", "GBP",
         * "NZD", "SEK" and "USD" are supported. Present only if payouts will be (or were) made via foreign
         * exchange.
         */
        public FxCurrency getFxCurrency() {
            return fxCurrency;
        }

        public enum FxCurrency {
            @SerializedName("AUD")
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD,
        }
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
