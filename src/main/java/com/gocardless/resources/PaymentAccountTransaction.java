package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a payment account transaction resource returned from the API.
 *
 * Payment account transactions represent movements of funds on a given payment account. The payment
 * account is provisioned by GoCardless and is used to fund [outbound
 * payments](#core-endpoints-outbound-payments).
 */
public class PaymentAccountTransaction {
    private PaymentAccountTransaction() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer balanceAfterTransaction;
    private String counterpartyName;
    private Currency currency;
    private String description;
    private Direction direction;
    private String id;
    private Links links;
    private String reference;
    private String valueDate;

    /**
     * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Balance after transaction, in the lowest denomination for the currency (e.g. pence in GBP,
     * cents in EUR).
     */
    public Integer getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    /**
     * The name of the counterparty of the transaction. The counterparty is the recipient for a
     * credit, or the sender for a debit.
     */
    public String getCounterpartyName() {
        return counterpartyName;
    }

    /**
     * The currency of the transaction.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * The description of the transaction, if available
     */
    public String getDescription() {
        return description;
    }

    /**
     * The direction of the transaction. Debits mean money leaving the account (e.g. outbound
     * payment), while credits signify money coming in (e.g. manual top-up).
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * The unique ID of the payment account transaction.
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The reference of the transaction. This is typically supplied by the sender.
     */
    public String getReference() {
        return reference;
    }

    /**
     * The date of when the transaction occurred.
     */
    public String getValueDate() {
        return valueDate;
    }

    public enum Currency {
        @SerializedName("GBP")
        GBP, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Direction {
        @SerializedName("credit")
        CREDIT, @SerializedName("debit")
        DEBIT, @SerializedName("unknown")
        UNKNOWN
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

        private String outboundPayment;
        private String paymentBankAccount;
        private String payout;

        /**
         * ID of the [outbound_payment](#core-endpoints-outbound-payments) linked to the transaction
         */
        public String getOutboundPayment() {
            return outboundPayment;
        }

        /**
         * ID of the payment bank account.
         */
        public String getPaymentBankAccount() {
            return paymentBankAccount;
        }

        /**
         * ID of the [payout](#core-endpoints-payouts) linked to the transaction.
         */
        public String getPayout() {
            return payout;
        }
    }
}
