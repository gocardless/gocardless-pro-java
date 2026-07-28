package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a refund resource returned from the API.
 *
 * Refund objects represent (partial) refunds of a
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-payments">payment</a>
 * back to the
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
 * 
 * GoCardless will notify you via a
 * <a href="https://developer.gocardless.com/api-reference/#appendix-webhooks">webhook</a> whenever
 * a refund is created, and will update the <code>amount_refunded</code> property of the payment.
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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     * This is set to the currency of the refund's <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-payments">payment</a>.
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
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * An optional reference that will appear on your customer's bank statement. The character limit
     * for this reference is dependent on the scheme.<br>
     * </br>
     * <strong>ACH</strong>
     * <ul>
     * <li>10 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>Autogiro</strong>
     * <ul>
     * <li>11 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>Bacs</strong>
     * <ul>
     * <li>10 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>BECS</strong>
     * <ul>
     * <li>30 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>BECS NZ</strong>
     * <ul>
     * <li>12 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>Betalingsservice</strong>
     * <ul>
     * <li>30 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>Faster Payments</strong>
     * <ul>
     * <li>18 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>PAD</strong>
     * <ul>
     * <li>scheme doesn't offer references</li>
     * </ul>
     * <br>
     * </br>
     * <strong>PayTo</strong>
     * <ul>
     * <li>18 characters</li>
     * </ul>
     * <br>
     * </br>
     * <strong>SEPA</strong>
     * <ul>
     * <li>140 characters</li>
     * </ul>
     * <br>
     * </br>
     * Note that this reference must be unique (for each merchant) for the BECS scheme as it is a
     * scheme requirement.
     * <p class="restricted-notice">
     * <strong>Restricted</strong>: You can only specify a payment reference for Bacs payments (that
     * is, when collecting from the UK) if you're on the
     * <a href="https://gocardless.com/pricing">GoCardless Plus, Pro or Enterprise packages</a>.
     * </p>
     * <p class="restricted-notice">
     * <strong>Restricted</strong>: You can not specify a payment reference for Faster Payments.
     * </p>
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * 
     * <ul>
     * <li><code>created</code>: the refund has been created</li>
     * <li><code>pending_submission</code>: the refund has been created, but not yet submitted to
     * the banks</li>
     * <li><code>submitted</code>: the refund has been submitted to the banks</li>
     * <li><code>paid</code>: the refund has been included in a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-payouts">payout</a></li>
     * <li><code>cancelled</code>: the refund has been cancelled</li>
     * <li><code>bounced</code>: the refund has failed to be paid</li>
     * <li><code>funds_returned</code>: the refund has had its funds returned</li>
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
        FUNDS_RETURNED, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a fx resource returned from the API.
     *
     * 
     */
    public static class Fx {
        private Fx() {
            // blank to prevent instantiation
        }

        private String estimatedExchangeRate;
        private String exchangeRate;
        private Integer fxAmount;
        private FxCurrency fxCurrency;

        /**
         * Estimated rate that will be used in the foreign exchange of the <code>amount</code> into
         * the <code>fx_currency</code>. This will vary based on the prevailing market rate until
         * the moment that it is paid out. Present only before a resource is paid out. Has up to 10
         * decimal places.
         */
        public String getEstimatedExchangeRate() {
            return estimatedExchangeRate;
        }

        /**
         * Rate used in the foreign exchange of the <code>amount</code> into the
         * <code>fx_currency</code>. Present only after a resource is paid out. Has up to 10 decimal
         * places.
         */
        public String getExchangeRate() {
            return exchangeRate;
        }

        /**
         * Amount that was paid out in the <code>fx_currency</code> after foreign exchange. Present
         * only after the resource has been paid out.
         */
        public Integer getFxAmount() {
            return fxAmount;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> code for the
         * currency in which amounts will be paid out (after foreign exchange). Currently "AUD",
         * "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported. Present only if payouts
         * will be (or were) made via foreign exchange.
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
            USD, @SerializedName("unknown")
            UNKNOWN
        }
    }

    /**
     * Represents a link resource returned from the API.
     *
     * 
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;
        private String payment;

        /**
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * against which the refund is being made.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payments">payment</a>
         * against which the refund is being made.
         */
        public String getPayment() {
            return payment;
        }
    }
}
