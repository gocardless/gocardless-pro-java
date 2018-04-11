package com.gocardless.resources;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a creditor resource returned from the API.
 *
 * Each [payment](#core-endpoints-payments) taken through the API is linked to a "creditor", to whom
 * the payment is then paid out. In most cases your organisation will have a single "creditor", but
 * the API also supports collecting payments on behalf of others.
 * 
 * Please get in touch if you wish to use this endpoint. Currently, for Anti Money Laundering
 * reasons, any creditors you add must be directly related to your organisation.
 */
public class Creditor {
    private Creditor() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private Boolean canCreateRefunds;
    private String city;
    private String countryCode;
    private String createdAt;
    private String id;
    private Links links;
    private String logoUrl;
    private String name;
    private String postalCode;
    private String region;
    private List<SchemeIdentifier> schemeIdentifiers;
    private VerificationStatus verificationStatus;

    /**
     * The first line of the creditor's address.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * The second line of the creditor's address.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * The third line of the creditor's address.
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Boolean indicating whether the creditor is permitted to create refunds
     */
    public Boolean getCanCreateRefunds() {
        return canCreateRefunds;
    }

    /**
     * The city of the creditor's address.
     */
    public String getCity() {
        return city;
    }

    /**
     * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "CR".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * URL for the creditor's logo, which may be shown on their payment pages.
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * The creditor's name.
     */
    public String getName() {
        return name;
    }

    /**
     * The creditor's postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The creditor's address region, county or department.
     */
    public String getRegion() {
        return region;
    }

    /**
     * An array of the scheme identifiers this creditor can create mandates against.
     * 
     * The support address, `phone_number` and `email` fields are for customers to contact the merchant
     * for support purposes. They must be displayed on the payment page, please see our [compliance
     * requirements](#appendix-compliance-requirements) for more details.
     */
    public List<SchemeIdentifier> getSchemeIdentifiers() {
        return schemeIdentifiers;
    }

    /**
     * The creditor's verification status, indicating whether they can yet receive payouts. For more
     * details on handling verification as a partner, see our ["Helping your users get verified"
     * guide](/getting-started/partners/helping-your-users-get-verified/). One of:
     * <ul>
     * <li>`successful`: The creditor's account is fully verified, and they can receive payouts. Once a
     * creditor has been successfully verified, they may in the future require further verification - for
     * example, if they change their payout bank account, we will have to check that they own the new
     * bank account before they can receive payouts again.</li>
     * <li>`in_review`: The creditor has provided all of the information currently requested, and it is
     * awaiting review by GoCardless before they can be verified and receive payouts.</li>
     * <li>`action_required`: The creditor needs to provide further information to verify their account
     * so they can receive payouts, and should visit the verification flow.</li>
     * </ul>
     */
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public enum VerificationStatus {
        @SerializedName("successful")
        SUCCESSFUL, @SerializedName("in_review")
        IN_REVIEW, @SerializedName("action_required")
        ACTION_REQUIRED,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String defaultAudPayoutAccount;
        private String defaultDkkPayoutAccount;
        private String defaultEurPayoutAccount;
        private String defaultGbpPayoutAccount;
        private String defaultSekPayoutAccount;

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to receive
         * payouts in AUD.
         */
        public String getDefaultAudPayoutAccount() {
            return defaultAudPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to receive
         * payouts in DKK.
         */
        public String getDefaultDkkPayoutAccount() {
            return defaultDkkPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to receive
         * payouts in EUR.
         */
        public String getDefaultEurPayoutAccount() {
            return defaultEurPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to receive
         * payouts in GBP.
         */
        public String getDefaultGbpPayoutAccount() {
            return defaultGbpPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to receive
         * payouts in SEK.
         */
        public String getDefaultSekPayoutAccount() {
            return defaultSekPayoutAccount;
        }
    }

    public static class SchemeIdentifier {
        private SchemeIdentifier() {
            // blank to prevent instantiation
        }

        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private Boolean canSpecifyMandateReference;
        private String city;
        private String countryCode;
        private Currency currency;
        private String email;
        private Integer minimumAdvanceNotice;
        private String name;
        private String phoneNumber;
        private String postalCode;
        private String reference;
        private String region;
        private Scheme scheme;

        /**
         * The first line of the support address.
         */
        public String getAddressLine1() {
            return addressLine1;
        }

        /**
         * The second line of the support address.
         */
        public String getAddressLine2() {
            return addressLine2;
        }

        /**
         * The third line of the support address.
         */
        public String getAddressLine3() {
            return addressLine3;
        }

        /**
         * Whether a custom reference can be submitted for mandates using this scheme identifier.
         */
        public Boolean getCanSpecifyMandateReference() {
            return canSpecifyMandateReference;
        }

        /**
         * The city of the support address.
         */
        public String getCity() {
            return city;
        }

        /**
         * The support [ISO 3166-1 country
         * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * The currency of the scheme identifier.
         */
        public Currency getCurrency() {
            return currency;
        }

        /**
         * The support email address.
         */
        public String getEmail() {
            return email;
        }

        /**
         * The minimum interval, in working days, between the sending of a pre-notification to the customer,
         * and the charge date of a payment using this scheme identifier.
         * 
         * By default, GoCardless sends these notifications automatically. Please see our [compliance
         * requirements](#appendix-compliance-requirements) for more details.
         */
        public Integer getMinimumAdvanceNotice() {
            return minimumAdvanceNotice;
        }

        /**
         * The name which appears on customers' bank statements.
         */
        public String getName() {
            return name;
        }

        /**
         * The support phone number.
         */
        public String getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * The support postal code.
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * The scheme-unique identifier against which payments are submitted.
         */
        public String getReference() {
            return reference;
        }

        /**
         * The support address region, county or department.
         */
        public String getRegion() {
            return region;
        }

        /**
         * The scheme which this scheme identifier applies to.
         */
        public Scheme getScheme() {
            return scheme;
        }

        public enum Currency {
            @SerializedName("AUD")
            AUD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("SEK")
            SEK,
        }

        public enum Scheme {
            @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("becs")
            BECS, @SerializedName("betalingsservice")
            BETALINGSSERVICE, @SerializedName("sepa")
            SEPA,
        }
    }
}
