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
    private String createdAt;
    private String currency;
    private String id;
    private Links links;
    private String reference;
    private Status status;

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
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently only "GBP"
     * and "EUR" are supported.
     */
    public String getCurrency() {
        return currency;
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
     * Reference which appears on the creditor's bank statement.
     */
    public String getReference() {
        return reference;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending`: the payout has been created, but not yet sent to the banks</li>
     *
     * <li>`paid`: the payout has been sent to the banks</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
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
