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

    private String expiresAt;
    private Boolean showRedirectButtons;
    private PrefilledCustomer prefilledCustomer;
    private Boolean lockCurrency;
    private String sessionToken;
    private Boolean customerDetailsCaptured;
    private Boolean lockCustomerDetails;
    private String language;
    private String id;
    private Boolean autoFulfil;
    private Links links;
    private Boolean showSuccessRedirectButton;
    private PrefilledBankAccount prefilledBankAccount;
    private String createdAt;
    private String exitUri;
    private String authorisationUrl;
    private String redirectUri;
    private Boolean skipSuccessScreen;
    private Boolean lockBankAccount;

    /**
     * Timestamp when the flow will expire. Each flow currently lasts for 7 days.
     */
    public String getExpiresAt() {
        return expiresAt;
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

    /**
     * Customer information used to prefill the payment page so your customer doesn't have to
     * re-type details you already hold about them. It will be stored unvalidated and the customer
     * will be able to review and amend it before completing the form.
     */
    public PrefilledCustomer getPrefilledCustomer() {
        return prefilledCustomer;
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
     * Session token populated when responding to the initialise action
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Identifies whether a Billing Request belongs to a specific customer
     */
    public Boolean getCustomerDetailsCaptured() {
        return customerDetailsCaptured;
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
     * Sets the default language of the Billing Request Flow and the customer. [ISO
     * 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Unique identifier, beginning with "BRF".
     */
    public String getId() {
        return id;
    }

    /**
     * (Experimental feature) Fulfil the Billing Request on completion of the flow (true by
     * default). Disabling the auto_fulfil is not allowed currently.
     */
    public Boolean getAutoFulfil() {
        return autoFulfil;
    }

    /**
    * 
    */
    public Links getLinks() {
        return links;
    }

    /**
     * If true, the payer will be able to see a redirect action button on the Success page. This
     * action button will provide a way to redirect the payer to the given redirect_uri. This
     * functionality is helpful when merchants do not want payers to be automatically redirected or
     * on Android devices, where automatic redirections are not possible.
     */
    public Boolean getShowSuccessRedirectButton() {
        return showSuccessRedirectButton;
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
     * URL for a GC-controlled flow which will allow the payer to fulfil the billing request
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * If true, the payer will not be redirected to the success screen after completing the flow. A
     * redirect_uri needs to be provided for this parameter to be taken into account.
     */
    public Boolean getSkipSuccessScreen() {
        return skipSuccessScreen;
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
     * Represents a link resource returned from the API.
     *
     * 
     */
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

        private String city;
        private String countryCode;
        private String email;
        private String familyName;
        private String addressLine1;
        private String addressLine3;
        private String danishIdentityNumber;
        private String companyName;
        private String region;
        private String postalCode;
        private String swedishIdentityNumber;
        private String givenName;
        private String addressLine2;

        /**
         * The city of the customer's address.
         */
        public String getCity() {
            return city;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public String getCountryCode() {
            return countryCode;
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
         * The first line of the customer's address.
         */
        public String getAddressLine1() {
            return addressLine1;
        }

        /**
         * The third line of the customer's address.
         */
        public String getAddressLine3() {
            return addressLine3;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
         */
        public String getDanishIdentityNumber() {
            return danishIdentityNumber;
        }

        /**
         * Customer's company name. Company name should only be provided if `given_name` and
         * `family_name` are null.
         */
        public String getCompanyName() {
            return companyName;
        }

        /**
         * The customer's address region, county or department.
         */
        public String getRegion() {
            return region;
        }

        /**
         * The customer's postal code.
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer.
         */
        public String getSwedishIdentityNumber() {
            return swedishIdentityNumber;
        }

        /**
         * Customer's first name.
         */
        public String getGivenName() {
            return givenName;
        }

        /**
         * The second line of the customer's address.
         */
        public String getAddressLine2() {
            return addressLine2;
        }
    }
}
