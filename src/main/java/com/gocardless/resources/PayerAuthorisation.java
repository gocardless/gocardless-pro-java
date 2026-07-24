package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a payer authorisation resource returned from the API.
 *
 * <p class="restricted-notice">
 * Don't use Payer Authorisations for new integrations. It is deprecated in favour of
 * <a href="https://developer.gocardless.com/getting-started/billing-requests/overview/"> Billing
 * Requests</a>. Use Billing Requests to build any future integrations.
 * </p>
 * Payer Authorisation resource acts as a wrapper for creating customer, bank account and mandate
 * details in a single request. PayerAuthorisation API enables the integrators to build their own
 * custom payment pages.
 * 
 * The process to use the Payer Authorisation API is as follows:
 * 
 * <ol>
 * <li>Create a Payer Authorisation, either empty or with already available information</li>
 * <li>Update the authorisation with additional information or fix any mistakes</li>
 * <li>Submit the authorisation, after the payer has reviewed their information</li>
 * <li>[coming soon] Redirect the payer to the verification mechanisms from the response of the
 * Submit request (this will be introduced as a non-breaking change)</li>
 * <li>Confirm the authorisation to indicate that the resources can be created</li>
 * </ol>
 * After the Payer Authorisation is confirmed, resources will eventually be created as it's an
 * asynchronous process.
 * 
 * To retrieve the status and ID of the linked resources you can do one of the following:
 * 
 * <ol>
 * <li>Listen to <code>  payer_authorisation_completed </code>
 * <a href="https://developer.gocardless.com/api-reference/#appendix-webhooks"> webhook</a>
 * (recommended)</li>
 * <li>Poll the GET <a href=
 * "https://developer.gocardless.com/api-reference/#payer-authorisations-get-a-single-payer-authorisation">
 * endpoint</a></li>
 * <li>Poll the GET events API
 * <code>https://api.gocardless.com/events?payer_authorisation={id}&amp;action=completed</code></li>
 * </ol>
 * <p class="notice">
 * Note that the <code>create</code> and <code>update</code> endpoints behave differently than other
 * existing <code>create</code> and <code>update</code> endpoints. The Payer Authorisation is still
 * saved if incomplete data is provided. We return the list of incomplete data in the
 * <code>incomplete_fields</code> along with the resources in the body of the response. The bank
 * account details(account_number, bank_code &amp; branch_code) must be sent together rather than
 * splitting across different request for both <code>create</code> and <code>update</code>
 * endpoints. <br>
 * </br>
 * <br>
 * </br>
 * The API is designed to be flexible and allows you to collect information in multiple steps
 * without storing any sensitive data in the browser or in your servers.
 * </p>
 */
public class PayerAuthorisation {
    private PayerAuthorisation() {
        // blank to prevent instantiation
    }

    private BankAccount bankAccount;
    private String createdAt;
    private Customer customer;
    private String id;
    private List<IncompleteField> incompleteFields;
    private Links links;
    private Mandate mandate;
    private Status status;

    /**
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">Customer
     * Bank Account</a>.
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">Timestamp</a>,
     * recording when this Payer Authorisation was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">Customer</a>.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Unique identifier, beginning with "PA".
     */
    public String getId() {
        return id;
    }

    /**
     * An array of fields which are missing and is required to set up the mandate.
     */
    public List<IncompleteField> getIncompleteFields() {
        return incompleteFields;
    }

    /**
     * IDs of the created resources. Available after the Payer Authorisation is completed.
     */
    public Links getLinks() {
        return links;
    }

    /**
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">Mandate</a>.
     */
    public Mandate getMandate() {
        return mandate;
    }

    /**
     * One of:
     * 
     * <ul>
     * <li><code>created</code>: The PayerAuthorisation has been created, and not been confirmed
     * yet</li>
     * <li><code>submitted</code>: The payer information has been submitted</li>
     * <li><code>confirmed</code>: PayerAuthorisation is confirmed and resources are ready to be
     * created</li>
     * <li><code>completed</code>: The PayerAuthorisation has been completed and customer,
     * bank_account and mandate has been created</li>
     * <li><code>failed</code>: The PayerAuthorisation has failed and customer, bank_account and
     * mandate is not created</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("created")
        CREATED, @SerializedName("submitted")
        SUBMITTED, @SerializedName("confirmed")
        CONFIRMED, @SerializedName("completed")
        COMPLETED, @SerializedName("failed")
        FAILED, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a bank account resource returned from the API.
     *
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">Customer
     * Bank Account</a>.
     */
    public static class BankAccount {
        private BankAccount() {
            // blank to prevent instantiation
        }

        private String accountHolderName;
        private String accountNumber;
        private String accountNumberEnding;
        private String accountNumberSuffix;
        private AccountType accountType;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String currency;
        private String iban;
        private Map<String, String> metadata;

        /**
         * Name of the account holder, as known by the bank. The full name provided when the
         * customer is created is stored and is available via the API, but is transliterated,
         * upcased, and truncated to 18 characters in bank submissions. This field is required
         * unless the request includes a <a href=
         * "https://developer.gocardless.com/api-reference/#javascript-flow-customer-bank-account-tokens">customer
         * bank account token</a>.
         */
        public String getAccountHolderName() {
            return accountHolderName;
        }

        /**
         * Bank account number - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public String getAccountNumber() {
            return accountNumber;
        }

        /**
         * The last few digits of the account number. Currently 4 digits for NZD bank accounts and 2
         * digits for other currencies.
         */
        public String getAccountNumberEnding() {
            return accountNumberEnding;
        }

        /**
         * Account number suffix (only for bank accounts denominated in NZD) - see <a href=
         * "https://developer.gocardless.com/api-reference/#local-bank-details-new-zealand">local
         * details</a> for more information.
         */
        public String getAccountNumberSuffix() {
            return accountNumberSuffix;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for
         * bank accounts in other currencies. See <a href=
         * "https://developer.gocardless.com/api-reference/#local-bank-details-united-states">local
         * details</a> for more information.
         */
        public AccountType getAccountType() {
            return accountType;
        }

        /**
         * Bank code - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public String getBankCode() {
            return bankCode;
        }

        /**
         * Branch code - see <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a> for more information. Alternatively you can provide an <code>iban</code>.
         */
        public String getBranchCode() {
            return branchCode;
        }

        /**
         * <a href=
         * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
         * 3166-1 alpha-2 code</a>. Defaults to the country code of the <code>iban</code> if
         * supplied, otherwise is required.
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * International Bank Account Number. Alternatively you can provide <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-local-bank-details">local
         * details</a>. IBANs are not accepted for Swedish bank accounts denominated in SEK - you
         * must supply
         * <a href="https://developer.gocardless.com/api-reference/#local-bank-details-sweden">local
         * details</a>.
         */
        public String getIban() {
            return iban;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public Map<String, String> getMetadata() {
            return metadata;
        }

        public enum AccountType {
            @SerializedName("savings")
            SAVINGS, @SerializedName("checking")
            CHECKING, @SerializedName("unknown")
            UNKNOWN
        }
    }

    /**
     * Represents a customer resource returned from the API.
     *
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">Customer</a>.
     */
    public static class Customer {
        private Customer() {
            // blank to prevent instantiation
        }

        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String companyName;
        private String countryCode;
        private String danishIdentityNumber;
        private String email;
        private String familyName;
        private String givenName;
        private String locale;
        private Map<String, String> metadata;
        private String postalCode;
        private String region;
        private String swedishIdentityNumber;

        /**
         * The first line of the customer's address.
         */
        public String getAddressLine1() {
            return addressLine1;
        }

        /**
         * The second line of the customer's address.
         */
        public String getAddressLine2() {
            return addressLine2;
        }

        /**
         * The third line of the customer's address.
         */
        public String getAddressLine3() {
            return addressLine3;
        }

        /**
         * The city of the customer's address.
         */
        public String getCity() {
            return city;
        }

        /**
         * Customer's company name. Required unless a <code>given_name</code> and
         * <code>family_name</code> are provided. For Canadian customers, the use of a
         * <code>company_name</code> value will mean that any mandate created from this customer
         * will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
         * be a "Personal PAD").
         */
        public String getCompanyName() {
            return companyName;
        }

        /**
         * <a href=
         * "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements">ISO
         * 3166-1 alpha-2 code.</a>
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be
         * supplied if the customer's bank account is denominated in Danish krone (DKK).
         */
        public String getDanishIdentityNumber() {
            return danishIdentityNumber;
        }

        /**
         * Customer's email address. Required in most cases, as this allows GoCardless to send
         * notifications to this customer.
         */
        public String getEmail() {
            return email;
        }

        /**
         * Customer's surname. Required unless a <code>company_name</code> is provided.
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * Customer's first name. Required unless a <code>company_name</code> is provided.
         */
        public String getGivenName() {
            return givenName;
        }

        /**
         * An <a href="https://tools.ietf.org/html/rfc5646">IETF Language Tag</a>, used for both
         * language and regional variations of our product.
         */
        public String getLocale() {
            return locale;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public Map<String, String> getMetadata() {
            return metadata;
        }

        /**
         * The customer's postal code.
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter
         * <a href="https://en.wikipedia.org/wiki/ISO_3166-2:US">ISO3166-2:US</a> state code is
         * required (e.g. <code>CA</code> for California).
         */
        public String getRegion() {
            return region;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
         * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
         */
        public String getSwedishIdentityNumber() {
            return swedishIdentityNumber;
        }
    }

    /**
     * Represents a incomplete field resource returned from the API.
     *
     * 
     */
    public static class IncompleteField {
        private IncompleteField() {
            // blank to prevent instantiation
        }

        private String field;
        private String message;
        private String requestPointer;

        /**
         * The root resource.
         */
        public String getField() {
            return field;
        }

        /**
         * A localised error message
         */
        public String getMessage() {
            return message;
        }

        /**
         * The path to the field e.g. "/payer_authorisations/customer/city"
         */
        public String getRequestPointer() {
            return requestPointer;
        }
    }

    /**
     * Represents a link resource returned from the API.
     *
     * IDs of the created resources. Available after the Payer Authorisation is completed.
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String bankAccount;
        private String customer;
        private String mandate;

        /**
         * Unique identifier, beginning with "BA".
         */
        public String getBankAccount() {
            return bankAccount;
        }

        /**
         * Unique identifier, beginning with "CU".
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates
         * created before 2016.
         */
        public String getMandate() {
            return mandate;
        }
    }

    /**
     * Represents a mandate resource returned from the API.
     *
     * All details required for the creation of a <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">Mandate</a>.
     */
    public static class Mandate {
        private Mandate() {
            // blank to prevent instantiation
        }

        private Map<String, String> metadata;
        private String payerIpAddress;
        private String reference;
        private Scheme scheme;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public Map<String, String> getMetadata() {
            return metadata;
        }

        /**
         * For ACH customers only. Required for ACH customers. A string containing the IP address of
         * the payer to whom the mandate belongs (i.e. as a result of their completion of a mandate
         * setup flow in their browser).
         * 
         * Not required for creating offline mandates where <code>authorisation_source</code> is set
         * to telephone or paper.
         */
        public String getPayerIpAddress() {
            return payerIpAddress;
        }

        /**
         * Unique reference. Different schemes have different length and <a href=
         * "https://developer.gocardless.com/api-reference/#appendix-character-sets">character
         * set</a> requirements. GoCardless will generate a unique reference satisfying the
         * different scheme requirements if this field is left blank.
         */
        public String getReference() {
            return reference;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         */
        public Scheme getScheme() {
            return scheme;
        }

        public enum Scheme {
            @SerializedName("ach")
            ACH, @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("becs")
            BECS, @SerializedName("becs_nz")
            BECS_NZ, @SerializedName("betalingsservice")
            BETALINGSSERVICE, @SerializedName("faster_payments")
            FASTER_PAYMENTS, @SerializedName("pad")
            PAD, @SerializedName("pay_to")
            PAY_TO, @SerializedName("sepa_core")
            SEPA_CORE, @SerializedName("unknown")
            UNKNOWN
        }
    }
}
