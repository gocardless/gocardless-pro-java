package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a creditor bank account resource returned from the API.
 *
 * Creditor Bank Accounts hold the bank details of a [creditor](#core-endpoints-creditors). These
 * are the bank accounts which your [payouts](#core-endpoints-payouts) will be sent to.
 * 
 * Note that creditor bank accounts must be unique, and so you will encounter a
 * `bank_account_exists` error if you try to create a duplicate bank account. You may wish to handle
 * this by updating the existing record instead, the ID of which will be provided as
 * `links[creditor_bank_account]` in the error response.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is not available for partner integrations.
 * </p>
 */
public class CreditorBankAccount {
    private CreditorBankAccount() {
        // blank to prevent instantiation
    }

    private String accountHolderName;
    private String accountNumberEnding;
    private AccountType accountType;
    private String bankName;
    private String countryCode;
    private String createdAt;
    private String currency;
    private Boolean enabled;
    private String id;
    private Links links;
    private Map<String, Object> metadata;
    private VerificationStatus verificationStatus;

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
     * Bank account type. Required for USD-denominated bank accounts. Must not be provided for bank
     * accounts in other currencies. See [local details](#local-bank-details-united-states) for more
     * information.
     */
    public AccountType getAccountType() {
        return accountType;
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

    /**
     * Verification status of the Bank Account. Can be one of `pending`, `in_review` or `successful`
     */
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public enum AccountType {
        @SerializedName("savings")
        SAVINGS, @SerializedName("checking")
        CHECKING, @SerializedName("unknown")
        UNKNOWN
    }

    public enum VerificationStatus {
        @SerializedName("pending")
        PENDING, @SerializedName("in_review")
        IN_REVIEW, @SerializedName("successful")
        SUCCESSFUL, @SerializedName("could_not_verify")
        COULD_NOT_VERIFY, @SerializedName("unknown")
        UNKNOWN
    }

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
