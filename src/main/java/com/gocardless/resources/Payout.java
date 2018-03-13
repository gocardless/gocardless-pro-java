package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a payout resource returned from the API.
 *
 * Payouts represent transfers from GoCardless to a [creditor](#core-endpoints-creditors). Each
 * payout contains the funds collected from one or many [payments](#core-endpoints-payments). Payouts
 * are created automatically after a payment has been successfully collected.
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
    private String id;
    private Links links;
    private PayoutType payoutType;
    private String reference;
    private Status status;

    /**
     * Amount in pence or cents.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Date the payout is due to arrive in the creditor's bank account.
     * One of:
     * <ul>
     *   <li>`yyyy-mm-dd`: the payout has been paid and is due to arrive in the creditor's bank
     *   account on this day</li>
     *   <li>`null`: the payout hasn't been paid yet</li>
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
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "GBP",
     * "EUR", "SEK" and "DKK" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Fees that have already been deducted from the payout amount in pence or cents.
     * 
     * For each `late_failure_settled` or `chargeback_settled` action, we refund the transaction fees in
     * a payout. This means that a payout can have a negative `deducted_fees`. This field is calculated
     * as `GoCardless fees + app fees - refunded fees`
     * 
     * If the merchant is invoiced for fees separately from the payout, then `deducted_fees` will be 0.
     */
    public Integer getDeductedFees() {
        return deductedFees;
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
     * <li>`pending`: the payout has been created, but not yet sent to the banks</li>
     * <li>`paid`: the payout has been sent to the banks</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Currency {
        @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("SEK")
        SEK,
    }

    public enum PayoutType {
        @SerializedName("merchant")
        MERCHANT, @SerializedName("partner")
        PARTNER,
    }

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("paid")
        PAID,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String creditorBankAccount;

        /**
         * ID of [creditor](#core-endpoints-creditors) who will receive this payout, i.e. the owner of the
         * `creditor_bank_account`.
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
