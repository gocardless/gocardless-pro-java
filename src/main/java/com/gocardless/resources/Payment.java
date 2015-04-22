package com.gocardless.resources;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a payment resource returned from the API.
 *
 * Payment objects represent payments from a
 * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers) to a
 * [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors), taken against a Direct
 * Debit [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates).
 * 
 * GoCardless
 * will notify you via a [webhook](https://developer.gocardless.com/pro/#webhooks) whenever the state
 * of a payment changes.
 */
public class Payment {
    private Payment() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer amountRefunded;
    private String chargeDate;
    private String createdAt;
    private String currency;
    private String description;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String reference;
    private Status status;

    /**
     * Amount in pence or cents.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Amount [refunded](https://developer.gocardless.com/pro/#api-endpoints-refunds) in pence or cents.
     */
    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    /**
     * A future date on which the payment should be collected. If not specified, the payment will be
     * collected as soon as possible. This must be on or after the
     * [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates)'s
     * `next_possible_charge_date`, and will be rolled-forwards by GoCardless if it is not a working day.
     */
    public String getChargeDate() {
        return chargeDate;
    }

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code, currently only "GBP"
     * and "EUR" are supported.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * A human readable description of the payment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Unique identifier, beginning with "PM"
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

    /**
     * An optional payment reference. This will be appended to the mandate reference on your customer's
     * bank statement. For Bacs payments this can be up to 10 characters, for SEPA Core payments the
     * limit is 140 characters.
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_submission`: the payment has been created, but not yet submitted to
     * the banks</li>
     * <li>`submitted`: the payment has been submitted to the banks</li>
     *
     * <li>`confirmed`: the payment has been confirmed as collected</li>
     * <li>`failed`: the payment
     * failed to be processed. Note that payments can fail after being confirmed, if the failure message
     * is sent late by the banks.</li>
     * <li>`charged_back`: the payment has been charged back</li>
     *
     * <li>`paid_out`:  the payment has been paid out</li>
     * <li>`cancelled`: the payment has been
     * cancelled</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("pending_submission")
        PENDING_SUBMISSION, @SerializedName("submitted")
        SUBMITTED, @SerializedName("confirmed")
        CONFIRMED, @SerializedName("failed")
        FAILED, @SerializedName("charged_back")
        CHARGED_BACK, @SerializedName("paid_out")
        PAID_OUT, @SerializedName("cancelled")
        CANCELLED,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String mandate;
        private String payout;
        private String subscription;

        /**
         * ID of [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors) to which the
         * collected payment will be sent.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the [mandate](https://developer.gocardless.com/pro/#api-endpoints-mandates) against which
         * this payment should be collected.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of [payout](https://developer.gocardless.com/pro/#api-endpoints-payouts) which contains the
         * funds from this payment.<br/>**Note**: this property will not be present until the payment has
         * been successfully collected.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * ID of [subscription](https://developer.gocardless.com/pro/#api-endpoints-subscriptions) from which
         * this payment was created.<br/>**Note**: this property will only be present if this payment is part
         * of a subscription.
         */
        public String getSubscription() {
            return subscription;
        }
    }
}
