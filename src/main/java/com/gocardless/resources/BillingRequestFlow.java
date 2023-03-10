package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a billing request flow resource returned from the API.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a
 * scheme with strong payer authorisation (such as open banking single payments).
 */
public class BillingRequestFlow {
    private BillingRequestFlow() {
        // blank to prevent instantiation
    }

    private String authorisationUrl;
    private Boolean autoFulfil;
    private String createdAt;
    private String exitUri;
    private String expiresAt;
    private String id;
    private String language;
    private Links links;
    private Boolean lockBankAccount;
    private Boolean lockCurrency;
    private Boolean lockCustomerDetails;
    private PrefilledBankAccount prefilledBankAccount;
    private PrefilledCustomer prefilledCustomer;
    private String redirectUri;
    private String sessionToken;
    private Boolean showRedirectButtons;

    /**
     * URL for a GC-controlled flow which will allow the payer to fulfil the billing request
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * (Experimental feature) Fulfil the Billing Request on completion of the flow (true by
     * default). Disabling the auto_fulfil is not allowed currently.
     */
    public Boolean getAutoFulfil() {
        return autoFulfil;
    }

    /**
     * Timestamp when the flow was created
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * URL that the payer can be taken to if there isn't a way to progress ahead in flow.
     */
    public String getExitUri() {
        return exitUri;
    }

    /**
     * Timestamp when the flow will expire. Each flow currently lasts for 7 days.
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Unique identifier, beginning with "BRF".
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the default language of the Billing Request Flow and the customer. [ISO
     * 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
     */
    public String getLanguage() {
        return language;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * If true, the payer will not be able to change their bank account within the flow. If the
     * bank_account details are collected as part of bank_authorisation then GC will set this value
     * to true mid flow.
     * 
     * You can only lock bank account if these have already been completed as a part of the billing
     * request.
     * 
     */
    public Boolean getLockBankAccount() {
        return lockBankAccount;
    }

    /**
     * If true, the payer will not be able to change their currency/scheme manually within the flow.
     * Note that this only applies to the mandate only flows - currency/scheme can never be changed
     * when there is a specified subscription or payment.
     */
    public Boolean getLockCurrency() {
        return lockCurrency;
    }

    /**
     * If true, the payer will not be able to edit their customer details within the flow. If the
     * customer details are collected as part of bank_authorisation then GC will set this value to
     * true mid flow.
     * 
     * You can only lock customer details if these have already been completed as a part of the
     * billing request.
     * 
     */
    public Boolean getLockCustomerDetails() {
        return lockCustomerDetails;
    }

    /**
     * Bank account information used to prefill the payment page so your customer doesn't have to
     * re-type details you already hold about them. It will be stored unvalidated and the customer
     * will be able to review and amend it before completing the form.
     */
    public PrefilledBankAccount getPrefilledBankAccount() {
        return prefilledBankAccount;
    }

    /**
     * Customer information used to prefill the payment page so your customer doesn't have to
     * re-type details you already hold about them. It will be stored unvalidated and the customer
     * will be able to review and amend it before completing the form.
     */
    public PrefilledCustomer getPrefilledCustomer() {
        return prefilledCustomer;
    }

    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Session token populated when responding to the initialise action
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * If true, the payer will be able to see redirect action buttons on Thank You page. These
     * action buttons will provide a way to connect back to the billing request flow app if opened
     * within a mobile app. For successful flow, the button will take the payer back the billing
     * request flow where they will see the success screen. For failure, button will take the payer
     * to url being provided against exit_uri field.
     */
    public Boolean getShowRedirectButtons() {
        return showRedirectButtons;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String billingRequest;

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow
         * was created.
         */
        public String getBillingRequest() {
            return billingRequest;
        }
    }

    /**
     * Represents a prefilled bank account resource returned from the API.
     *
     * Bank account information used to prefill the payment page so your customer doesn't have to
     * re-type details you already hold about them. It will be stored unvalidated and the customer
     * will be able to review and amend it before completing the form.
     */
    public static class PrefilledBankAccount {
        private PrefilledBankAccount() {
            // blank to prevent instantiation
        }

        private AccountType accountType;

        /**
         * Bank account type for USD-denominated bank accounts. Must not be provided for bank
         * accounts in other currencies. See [local details](#local-bank-details-united-states) for
         * more information.
         */
        public AccountType getAccountType() {
            return accountType;
        }

        public enum AccountType {
            @SerializedName("savings")
            SAVINGS, @SerializedName("checking")
            CHECKING, @SerializedName("unknown")
            UNKNOWN
        }
    }

    /**
     * Represents a prefilled customer resource returned from the API.
     *
     * Customer information used to prefill the payment page so your customer doesn't have to
     * re-type details you already hold about them. It will be stored unvalidated and the customer
     * will be able to review and amend it before completing the form.
     */
    public static class PrefilledCustomer {
        private PrefilledCustomer() {
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
         * Customer's company name. Company name should only be provided if `given_name` and
         * `family_name` are null.
         */
        public String getCompanyName() {
            return companyName;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
         */
        public String getDanishIdentityNumber() {
            return danishIdentityNumber;
        }

        /**
         * Customer's email address.
         */
        public String getEmail() {
            return email;
        }

        /**
         * Customer's surname.
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * Customer's first name.
         */
        public String getGivenName() {
            return givenName;
        }

        /**
         * The customer's postal code.
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * The customer's address region, county or department.
         */
        public String getRegion() {
            return region;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer.
         */
        public String getSwedishIdentityNumber() {
            return swedishIdentityNumber;
        }
    }
}
