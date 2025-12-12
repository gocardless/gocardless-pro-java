package com.gocardless.resources;

/**
 * Represents a payment account resource returned from the API.
 *
 * Access the details of bank accounts provided for you by GoCardless that are used to fund
 * [Outbound Payments](#core-endpoints-outbound-payments).
 */
public class PaymentAccount {
    private PaymentAccount() {
        // blank to prevent instantiation
    }

    private Integer accountBalance;
    private String accountHolderName;
    private String accountNumberEnding;
    private String bankName;
    private String currency;
    private String id;
    private Links links;

    /**
     * Current balance on a payment account in the lowest denomination for the currency (e.g. pence
     * in GBP, cents in EUR). It is time-sensitive as it is ever changing.
     */
    public Integer getAccountBalance() {
        return accountBalance;
    }

    /**
     * Name of the account holder, as known by the bank. Usually this is the same as the name stored
     * with the linked [creditor](#core-endpoints-creditors). This field will be transliterated,
     * upcased and truncated to 18 characters.
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * The last few digits of the account number. Currently 4 digits for NZD bank accounts and 2
     * digits for other currencies.
     */
    public String getAccountNumberEnding() {
        return accountNumberEnding;
    }

    /**
     * Name of bank, taken from the bank details.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Unique identifier, beginning with "BA".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
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

        /**
         * ID of the [creditor](#core-endpoints-creditors) that owns this bank account.
         */
        public String getCreditor() {
            return creditor;
        }
    }
}
