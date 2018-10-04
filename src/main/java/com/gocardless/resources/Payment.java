package com.gocardless.resources;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

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
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String reference;
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
     * collected as soon as possible. This must be on or after the [mandate](#core-endpoints-mandates)'s
     * `next_possible_charge_date`, and will be rolled-forwards by GoCardless if it is not a working day.
     */
    public String getChargeDate() {
        return chargeDate;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
     * "DKK", "EUR", "GBP", "NZD" and "SEK" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * A human-readable description of the payment. This will be included in the notification email
     * GoCardless sends to your customer if your organisation does not send its own notifications (see
     * [compliance requirements](#appendix-compliance-requirements)).
     */
    public String getDescription() {
        return description;
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
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
     * values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * An optional payment reference that will appear on your customer's bank statement. For Bacs
     * payments this can be up to 10 characters, for SEPA payments the limit is 140 characters, for
     * Betalingsservice or BECS payments the limit is 30 characters and for Autogiro payments the limit
     * is 11 characters. <p class='restricted-notice'><strong>Restricted</strong>: You can only specify a
     * payment reference for Bacs payments (that is, when collecting from the UK) if you're on the <a
     * href='https://gocardless.com/pricing'>GoCardless Plus or Pro packages</a>.</p>
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_customer_approval`: we're waiting for the customer to approve this payment</li>
     * <li>`pending_submission`: the payment has been created, but not yet submitted to the banks</li>
     * <li>`submitted`: the payment has been submitted to the banks</li>
     * <li>`confirmed`: the payment has been confirmed as collected</li>
     * <li>`paid_out`:  the payment has been included in a [payout](#core-endpoints-payouts)</li>
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
        AUD, @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("NZD")
        NZD, @SerializedName("SEK")
        SEK,
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
        CHARGED_BACK,
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
         * ID of [creditor](#core-endpoints-creditors) to which the collected payment will be sent.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the [mandate](#core-endpoints-mandates) against which this payment should be collected.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of [payout](#core-endpoints-payouts) which contains the funds from this payment.<br/>**Note**:
         * this property will not be present until the payment has been successfully collected.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * ID of [subscription](#core-endpoints-subscriptions) from which this payment was
         * created.<br/>**Note**: this property will only be present if this payment is part of a
         * subscription.
         */
        public String getSubscription() {
            return subscription;
        }
    }
}
