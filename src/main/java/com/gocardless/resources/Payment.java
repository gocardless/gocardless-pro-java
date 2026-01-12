package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a payment resource returned from the API.
 *
 * Payment objects represent payments from a [customer](#core-endpoints-customers) to a
 * [creditor](#core-endpoints-creditors), taken against a Direct Debit
 * [mandate](#core-endpoints-mandates).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) whenever the state of a payment
 * changes.
 */
public class Payment {
    private Payment() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer amountRefunded;
    private String chargeDate;
    private String createdAt;
    private Currency currency;
    private String description;
    private Boolean fasterAch;
    private Fx fx;
    private String id;
    private Links links;
    private Map<String, Object> metadata;
    private String reference;
    private Boolean retryIfPossible;
    private String scheme;
    private Status status;

    /**
     * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Amount [refunded](#core-endpoints-refunds), in the lowest denomination for the currency (e.g.
     * pence in GBP, cents in EUR).
     */
    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    /**
     * A future date on which the payment should be collected. If not specified, the payment will be
     * collected as soon as possible. If the value is before the
     * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date` creation will fail. If the
     * value is not a working day it will be rolled forwards to the next available one.
     */
    public String getChargeDate() {
        return chargeDate;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * A human-readable description of the payment. This will be included in the notification email
     * GoCardless sends to your customer if your organisation does not send its own notifications
     * (see [compliance requirements](#appendix-compliance-requirements)).
     */
    public String getDescription() {
        return description;
    }

    /**
     * This field indicates whether the ACH payment is processed through Faster ACH or standard ACH.
     * 
     * It is only present in the API response for ACH payments.
     */
    public Boolean getFasterAch() {
        return fasterAch;
    }

    public Fx getFx() {
        return fx;
    }

    /**
     * Unique identifier, beginning with "PM".
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
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * An optional reference that will appear on your customer's bank statement. The character limit
     * for this reference is dependent on the scheme.<br />
     * <strong>ACH</strong> - 10 characters<br />
     * <strong>Autogiro</strong> - 11 characters<br />
     * <strong>Bacs</strong> - 10 characters<br />
     * <strong>BECS</strong> - 30 characters<br />
     * <strong>BECS NZ</strong> - 12 characters<br />
     * <strong>Betalingsservice</strong> - 30 characters<br />
     * <strong>Faster Payments</strong> - 18 characters<br />
     * <strong>PAD</strong> - scheme doesn't offer references<br />
     * <strong>PayTo</strong> - 18 characters<br />
     * <strong>SEPA</strong> - 140 characters<br />
     * Note that this reference must be unique (for each merchant) for the BECS scheme as it is a
     * scheme requirement.
     * <p class='restricted-notice'>
     * <strong>Restricted</strong>: You can only specify a payment reference for Bacs payments (that
     * is, when collecting from the UK) if you're on the
     * <a href='https://gocardless.com/pricing'>GoCardless Plus, Pro or Enterprise packages</a>.
     * </p>
     * <p class='restricted-notice'>
     * <strong>Restricted</strong>: You can not specify a payment reference for Faster Payments.
     * </p>
     */
    public String getReference() {
        return reference;
    }

    /**
     * On failure, automatically retry the payment using [intelligent
     * retries](/success-plus/overview). Default is `false`.
     * <p class="notice">
     * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
     * enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
     * </p>
     */
    public Boolean getRetryIfPossible() {
        return retryIfPossible;
    }

    /**
     * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
     * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_customer_approval`: we're waiting for the customer to approve this payment</li>
     * <li>`pending_submission`: the payment has been created, but not yet submitted to the
     * banks</li>
     * <li>`submitted`: the payment has been submitted to the banks</li>
     * <li>`confirmed`: the payment has been confirmed as collected</li>
     * <li>`paid_out`: the payment has been included in a [payout](#core-endpoints-payouts)</li>
     * <li>`cancelled`: the payment has been cancelled</li>
     * <li>`customer_approval_denied`: the customer has denied approval for the payment. You should
     * contact the customer directly</li>
     * <li>`failed`: the payment failed to be processed. Note that payments can fail after being
     * confirmed if the failure message is sent late by the banks.</li>
     * <li>`charged_back`: the payment has been charged back</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
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
        USD, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("pending_customer_approval")
        PENDING_CUSTOMER_APPROVAL, @SerializedName("pending_submission")
        PENDING_SUBMISSION, @SerializedName("submitted")
        SUBMITTED, @SerializedName("confirmed")
        CONFIRMED, @SerializedName("paid_out")
        PAID_OUT, @SerializedName("cancelled")
        CANCELLED, @SerializedName("customer_approval_denied")
        CUSTOMER_APPROVAL_DENIED, @SerializedName("failed")
        FAILED, @SerializedName("charged_back")
        CHARGED_BACK, @SerializedName("unknown")
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
         * Estimated rate that will be used in the foreign exchange of the `amount` into the
         * `fx_currency`. This will vary based on the prevailing market rate until the moment that
         * it is paid out. Present only before a resource is paid out. Has up to 10 decimal places.
         */
        public String getEstimatedExchangeRate() {
            return estimatedExchangeRate;
        }

        /**
         * Rate used in the foreign exchange of the `amount` into the `fx_currency`. Present only
         * after a resource is paid out. Has up to 10 decimal places.
         */
        public String getExchangeRate() {
            return exchangeRate;
        }

        /**
         * Amount that was paid out in the `fx_currency` after foreign exchange. Present only after
         * the resource has been paid out.
         */
        public Integer getFxAmount() {
            return fxAmount;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) code for the currency in
         * which amounts will be paid out (after foreign exchange). Currently "AUD", "CAD", "DKK",
         * "EUR", "GBP", "NZD", "SEK" and "USD" are supported. Present only if payouts will be (or
         * were) made via foreign exchange.
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

        private String creditor;
        private String instalmentSchedule;
        private String mandate;
        private String payout;
        private String subscription;

        /**
         * ID of [creditor](#core-endpoints-creditors) to which the collected payment will be sent.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of [instalment_schedule](#core-endpoints-instalment-schedules) from which this payment
         * was created.<br/>
         * **Note**: this property will only be present if this payment is part of an instalment
         * schedule.
         */
        public String getInstalmentSchedule() {
            return instalmentSchedule;
        }

        /**
         * ID of the [mandate](#core-endpoints-mandates) against which this payment should be
         * collected.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of [payout](#core-endpoints-payouts) which contains the funds from this payment.<br/>
         * _Note_: this property will not be present until the payment has been successfully
         * collected.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * ID of [subscription](#core-endpoints-subscriptions) from which this payment was
         * created.<br/>
         * _Note_: this property will only be present if this payment is part of a subscription.
         */
        public String getSubscription() {
            return subscription;
        }
    }
}
