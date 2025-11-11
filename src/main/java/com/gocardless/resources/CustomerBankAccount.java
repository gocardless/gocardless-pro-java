package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a customer bank account resource returned from the API.
 *
 * Customer Bank Accounts hold the bank details of a [customer](#core-endpoints-customers). They
 * always belong to a [customer](#core-endpoints-customers), and may be linked to several Direct
 * Debit [mandates](#core-endpoints-mandates).
 * 
 * Note that customer bank accounts must be unique, and so you will encounter a
 * `bank_account_exists` error if you try to create a duplicate bank account. You may wish to handle
 * this by updating the existing record instead, the ID of which will be provided as
 * `links[customer_bank_account]` in the error response.
 * 
 * _Note:_ To ensure the customer's bank accounts are valid, verify them first using
 * [bank_details_lookups](#bank-details-lookups-perform-a-bank-details-lookup), before proceeding
 * with creating the accounts
 */
public class CustomerBankAccount {
    private CustomerBankAccount() {
        // blank to prevent instantiation
    }

    private String accountHolderName;
    private String accountNumberEnding;
    private AccountType accountType;
    private String bankAccountToken;
    private String bankName;
    private String countryCode;
    private String createdAt;
    private String currency;
    private Boolean enabled;
    private String id;
    private Links links;
    private Map<String, Object> metadata;

    /**
     * Name of the account holder, as known by the bank. The full name provided when the customer is
     * created is stored and is available via the API, but is transliterated, upcased, and truncated
     * to 18 characters in bank submissions. This field is required unless the request includes a
     * [customer bank account token](#javascript-flow-customer-bank-account-tokens).
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
     * Bank account type. Required for USD-denominated bank accounts. Must not be provided for bank
     * accounts in other currencies. See [local details](#local-bank-details-united-states) for more
     * information.
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * A token to uniquely refer to a set of bank account details. This feature is still in early
     * access and is only available for certain organisations.
     */
    public String getBankAccountToken() {
        return bankAccountToken;
    }

    /**
     * Name of bank, taken from the bank details.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * [ISO 3166-1 alpha-2
     * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
     * Defaults to the country code of the `iban` if supplied, otherwise is required.
     */
    public String getCountryCode() {
        return countryCode;
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
    public String getCurrency() {
        return currency;
    }

    /**
     * Boolean value showing whether the bank account is enabled or disabled.
     */
    public Boolean getEnabled() {
        return enabled;
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
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public enum AccountType {
        @SerializedName("savings")
        SAVINGS, @SerializedName("checking")
        CHECKING, @SerializedName("unknown")
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

        private String customer;

        /**
         * ID of the [customer](#core-endpoints-customers) that owns this bank account.
         */
        public String getCustomer() {
            return customer;
        }
    }
}
