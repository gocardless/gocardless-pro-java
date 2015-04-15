package com.gocardless.pro.resources;

import java.util.Map;

/**
 * Represents a customer bank account resource returned from the API.
 *
 * Customer Bank Accounts hold the bank details of a
 * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers). They always belong to a
 * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers), and may be linked to
 * several Direct Debit [mandates](https://developer.gocardless.com/pro/#api-endpoints-mandates).
 *
 * 
 * Note that customer bank accounts must be unique, and so you will encounter a
 * `bank_account_exists` error if you try to create a duplicate bank account. You may wish to handle
 * this by updating the existing record instead, the ID of which will be provided as
 * links[customer_bank_account] in the error response.
 */
public class CustomerBankAccount {
    private CustomerBankAccount() {
        // blank to prevent instantiation
    }

    private String accountHolderName;
    private String accountNumberEnding;
    private String bankName;
    private String countryCode;
    private String createdAt;
    private String currency;
    private Boolean enabled;
    private String id;
    private Links links;
    private Map<String, String> metadata;

    /**
     * Name of the account holder, as known by the bank. Usually this matches the name of the linked
     * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers). This field cannot
     * exceed 18 characters.
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Last two digits of account number.
     */
    public String getAccountNumberEnding() {
        return accountNumberEnding;
    }

    /**
     * Name of bank, taken from sort code.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code. Defaults to the country code of the `iban` if supplied, otherwise is required.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code, defaults to national
     * currency of `country_code`.
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
     * Unique identifier, beginning with "BA"
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String customer;

        /**
         * ID of the [customer](https://developer.gocardless.com/pro/#api-endpoints-customers) that owns this
         * bank account.
         */
        public String getCustomer() {
            return customer;
        }
    }
}
