package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a payout resource returned from the API.
 *
 * Payouts represent transfers from GoCardless to a [creditor](#core-endpoints-creditors). Each
 * payout contains the funds collected from one or many [payments](#core-endpoints-payments). All
 * the payments in a payout will have been collected in the same currency. Payouts are created
 * automatically after a payment has been successfully collected.
 */
public class Payout {
    private Payout() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String arrivalDate;
    private String createdAt;
    private Currency currency;
    private Integer deductedFees;
    private Fx fx;
    private String id;
    private Links links;
    private Map<String, Object> metadata;
    private PayoutType payoutType;
    private String reference;
    private Status status;
    private String taxCurrency;

    /**
     * Amount in minor unit (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Date the payout is due to arrive in the creditor's bank account. One of:
     * <ul>
     * <li>`yyyy-mm-dd`: the payout has been paid and is due to arrive in the creditor's bank
     * account on this day</li>
     * <li>`null`: the payout hasn't been paid yet</li>
     * </ul>
     * 
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
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
     * Fees that have already been deducted from the payout amount in minor unit (e.g. pence in GBP,
     * cents in EUR), inclusive of tax if applicable. <br />
     * For each `late_failure_settled` or `chargeback_settled` action, we refund the transaction
     * fees in a payout. This means that a payout can have a negative `deducted_fees` value. <br />
     * This field is calculated as `(GoCardless fees + app fees + surcharge fees) - (refunded fees)`
     * <br />
     * If the merchant is invoiced for fees separately from the payout, then `deducted_fees` will be
     * 0.
     */
    public Integer getDeductedFees() {
        return deductedFees;
    }

    public Fx getFx() {
        return fx;
    }

    /**
     * Unique identifier, beginning with "PO".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters. _Note:_ This should not be used for storing PII
     * data.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Whether a payout contains merchant revenue or partner fees.
     */
    public PayoutType getPayoutType() {
        return payoutType;
    }

    /**
     * Reference which appears on the creditor's bank statement.
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending`: the payout has been created, but not yet sent to your bank or it is in the
     * process of being exchanged through our FX provider.</li>
     * <li>`paid`: the payout has been sent to the your bank. FX payouts will become `paid` after we
     * emit the `fx_rate_confirmed` webhook.</li>
     * <li>`bounced`: the payout bounced when sent, the payout can be retried.</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) code for the currency in which
     * tax is paid out to the tax authorities of your tax jurisdiction. Currently “EUR”, “GBP”, for
     * French or British merchants, this will be `null` if tax is not applicable <em>beta</em>
     */
    public String getTaxCurrency() {
        return taxCurrency;
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

    public enum PayoutType {
        @SerializedName("merchant")
        MERCHANT, @SerializedName("partner")
        PARTNER, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("paid")
        PAID, @SerializedName("bounced")
        BOUNCED, @SerializedName("unknown")
        UNKNOWN
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String creditorBankAccount;

        /**
         * ID of [creditor](#core-endpoints-creditors) who will receive this payout, i.e. the owner
         * of the `creditor_bank_account`.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of [bank account](#core-endpoints-creditor-bank-accounts) which this will be sent to.
         */
        public String getCreditorBankAccount() {
            return creditorBankAccount;
        }
    }
}
