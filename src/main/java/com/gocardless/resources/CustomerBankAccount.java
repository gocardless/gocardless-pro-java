package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a customer bank account resource returned from the API.
 *
 * Customer Bank Accounts hold the bank details of a
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
 * They always belong to a
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>,
 * and may be linked to several Direct Debit
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandates</a>.
 * 
 * Note that customer bank accounts must be unique, and so you will encounter a
 * <code>bank_account_exists</code> error if you try to create a duplicate bank account. You may
 * wish to handle this by updating the existing record instead, the ID of which will be provided as
 * <code>links[customer_bank_account]</code> in the error response.
 * 
 * <em>Note:</em> To ensure the customer's bank accounts are valid, verify them first using <a href=
 * "https://developer.gocardless.com/api-reference/#bank-details-lookups-perform-a-bank-details-lookup">bank_details_lookups</a>,
 * before proceeding with creating the accounts
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
    private Map<String, String> metadata;
    private PayerNameVerificationResult payerNameVerificationResult;
    private Boolean trustedRecipient;

    /**
     * Name of the account holder, as known by the bank. The full name provided when the customer is
     * created is stored and is available via the API, but is transliterated, upcased, and truncated
     * to 18 characters in bank submissions. This field is required unless the request includes a
     * <a href=
     * "https://developer.gocardless.com/api-reference/#javascript-flow-customer-bank-account-tokens">customer
     * bank account token</a>.
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
     * accounts in other currencies. See <a href=
     * "https://developer.gocardless.com/api-reference/#local-bank-details-united-states">local
     * details</a> for more information.
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
     * <a href=
     * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
     * 3166-1 alpha-2 code</a>. Defaults to the country code of the <code>iban</code> if supplied,
     * otherwise is required.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
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
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * The result of the payer name verification check performed when the bank account was created.
     * Only present if a check was performed.
     * 
     * <ul>
     * <li><code>full</code>: The name provided matches the name held by the bank.</li>
     * <li><code>close</code>: The name provided is a close but not exact match to the name held by
     * the bank.</li>
     * <li><code>cannot_perform_verification</code>: A verification was attempted but could not be
     * completed. This can happen for a number of reasons, including the account holder's bank not
     * participating in the verification scheme, the account not being eligible for verification
     * (e.g. the account holder has opted out), or the bank details not being resolvable, among
     * others.</li>
     * </ul>
     */
    public PayerNameVerificationResult getPayerNameVerificationResult() {
        return payerNameVerificationResult;
    }

    /**
     * Whether this customer bank account is registered as a trusted recipient for Outbound
     * Payments. Only present when the feature is enabled for the organisation.
     */
    public Boolean getTrustedRecipient() {
        return trustedRecipient;
    }

    public enum AccountType {
        @SerializedName("savings")
        SAVINGS, @SerializedName("checking")
        CHECKING, @SerializedName("unknown")
        UNKNOWN
    }

    public enum PayerNameVerificationResult {
        @SerializedName("full")
        FULL, @SerializedName("close")
        CLOSE, @SerializedName("cannot_perform_verification")
        CANNOT_PERFORM_VERIFICATION, @SerializedName("unknown")
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
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>
         * that owns this bank account.
         */
        public String getCustomer() {
            return customer;
        }
    }
}
