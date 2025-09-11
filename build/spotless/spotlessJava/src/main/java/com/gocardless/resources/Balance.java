package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a balance resource returned from the API.
 *
 * Returns the balances for a creditor. These balances are the same as what’s shown in the dashboard
 * with one exception (mentioned below under balance_type).
 * 
 * These balances will typically be 3-5 minutes old. The balance amounts likely won’t match what’s
 * shown in the dashboard as the dashboard balances are updated much less frequently (once per day).
 */
public class Balance {
    private Balance() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private BalanceType balanceType;
    private Currency currency;
    private String lastUpdatedAt;
    private Links links;

    /**
     * The total amount in the balance, defined as the sum of all debits subtracted from the sum of
     * all credits, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Type of the balance. Could be one of
     * <ul>
     * <li>pending_payments_submitted: Payments we have submitted to the scheme but not yet
     * confirmed. This does not exactly correspond to <i>Pending payments</i> in the dashboard,
     * because this balance does not include payments that are pending submission.</li>
     * <li>confirmed_funds: Payments that have been confirmed minus fees and unclaimed debits for
     * refunds, failures and chargebacks. These funds have not yet been moved into a payout.</li>
     * <li>pending_payouts: Confirmed payments that have been moved into a payout. This is the total
     * due to be paid into your bank account in the next payout run (payouts happen once every
     * business day). pending_payouts will only be non-zero while we are generating and submitting
     * the payouts to our partner bank.</li>
     * </ul>
     */
    public BalanceType getBalanceType() {
        return balanceType;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Dynamic [timestamp](#api-usage-dates-and-times) recording when this resource was last
     * updated.
     */
    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Links getLinks() {
        return links;
    }

    public enum BalanceType {
        @SerializedName("confirmed_funds")
        CONFIRMED_FUNDS, @SerializedName("pending_payouts")
        PENDING_PAYOUTS, @SerializedName("pending_payments_submitted")
        PENDING_PAYMENTS_SUBMITTED, @SerializedName("unknown")
        UNKNOWN
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;

        /**
         * ID of the associated [creditor](#core-endpoints-creditors).
         */
        public String getCreditor() {
            return creditor;
        }
    }
}
