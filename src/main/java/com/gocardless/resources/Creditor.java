package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents a creditor resource returned from the API.
 *
 * Each [payment](#core-endpoints-payments) taken through the API is linked to a "creditor", to whom
 * the payment is then paid out. In most cases your organisation will have a single "creditor", but
 * the API also supports collecting payments on behalf of others.
 * 
 * Currently, for Anti Money Laundering reasons, any creditors you add must be directly related to
 * your organisation.
 */
public class Creditor {
    private Creditor() {
        // blank to prevent instantiation
    }

    private Boolean activated;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private Boolean canCreateRefunds;
    private String city;
    private String countryCode;
    private String createdAt;
    private CreditorType creditorType;
    private Boolean customPaymentPagesEnabled;
    private FxPayoutCurrency fxPayoutCurrency;
    private String id;
    private Links links;
    private String logoUrl;
    private Boolean mandateImportsEnabled;
    private Boolean merchantResponsibleForNotifications;
    private String name;
    private String postalCode;
    private String region;
    private List<SchemeIdentifier> schemeIdentifiers;
    private VerificationStatus verificationStatus;

    /**
     * Boolean value indicating whether the creditor is activated in the product.
     */
    public Boolean getActivated() {
        return activated;
    }

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
     * [ISO 3166-1 alpha-2
     * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
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
     * The type of business of the creditor
     */
    public CreditorType getCreditorType() {
        return creditorType;
    }

    /**
     * Boolean value indicating whether creditor has the [Custom Payment
     * Pages](https://hub.gocardless.com/s/article/Custom-payment-pages) functionality enabled.
     */
    public Boolean getCustomPaymentPagesEnabled() {
        return customPaymentPagesEnabled;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) code for the currency in which
     * amounts will be paid out (after foreign exchange). Currently "AUD", "CAD", "DKK", "EUR",
     * "GBP", "NZD", "SEK" and "USD" are supported. Present only if payouts will be (or were) made
     * via foreign exchange.
     */
    public FxPayoutCurrency getFxPayoutCurrency() {
        return fxPayoutCurrency;
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
     * Boolean value indicating whether creditor has the [Mandate
     * Imports](#core-endpoints-mandate-imports) functionality enabled.
     */
    public Boolean getMandateImportsEnabled() {
        return mandateImportsEnabled;
    }

    /**
     * Boolean value indicating whether the organisation is responsible for sending all customer
     * notifications (note this is separate from the functionality described
     * [here](/getting-started/api/handling-customer-notifications/)). If you are a partner app, and
     * this value is true, you should not send notifications on behalf of this organisation.
     */
    public Boolean getMerchantResponsibleForNotifications() {
        return merchantResponsibleForNotifications;
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
     * The support address, `phone_number` and `email` fields are for customers to contact the
     * merchant for support purposes. They must be displayed on the payment page, please see our
     * [compliance requirements](#appendix-compliance-requirements) for more details.
     */
    public List<SchemeIdentifier> getSchemeIdentifiers() {
        return schemeIdentifiers;
    }

    /**
     * The creditor's verification status, indicating whether they can yet receive payouts. For more
     * details on handling verification as a partner, see our ["Helping your users get verified"
     * guide](/getting-started/partners/helping-your-users-get-verified/). One of:
     * <ul>
     * <li>`successful`: The creditor's account is fully verified, and they can receive payouts.
     * Once a creditor has been successfully verified, they may in the future require further
     * verification - for example, if they change their payout bank account, we will have to check
     * that they own the new bank account before they can receive payouts again.</li>
     * <li>`in_review`: The creditor has provided all of the information currently requested, and it
     * is awaiting review by GoCardless before they can be verified and receive payouts.</li>
     * <li>`action_required`: The creditor needs to provide further information to verify their
     * account so they can receive payouts, and should visit the verification flow.</li>
     * </ul>
     */
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public enum CreditorType {
        @SerializedName("company")
        COMPANY, @SerializedName("individual")
        INDIVIDUAL, @SerializedName("charity")
        CHARITY, @SerializedName("partnership")
        PARTNERSHIP, @SerializedName("trust")
        TRUST, @SerializedName("unknown")
        UNKNOWN
    }

    public enum FxPayoutCurrency {
        @SerializedName("AUD")
        AUD, @SerializedName("CAD")
        CAD, @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("NZD")
        NZD, @SerializedName("SEK")
        SEK, @SerializedName("USD")
        USD, @SerializedName("unknown")
        UNKNOWN
    }

    public enum VerificationStatus {
        @SerializedName("successful")
        SUCCESSFUL, @SerializedName("in_review")
        IN_REVIEW, @SerializedName("action_required")
        ACTION_REQUIRED, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String defaultAudPayoutAccount;
        private String defaultCadPayoutAccount;
        private String defaultDkkPayoutAccount;
        private String defaultEurPayoutAccount;
        private String defaultGbpPayoutAccount;
        private String defaultNzdPayoutAccount;
        private String defaultSekPayoutAccount;
        private String defaultUsdPayoutAccount;

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in AUD.
         */
        public String getDefaultAudPayoutAccount() {
            return defaultAudPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in CAD.
         */
        public String getDefaultCadPayoutAccount() {
            return defaultCadPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in DKK.
         */
        public String getDefaultDkkPayoutAccount() {
            return defaultDkkPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in EUR.
         */
        public String getDefaultEurPayoutAccount() {
            return defaultEurPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in GBP.
         */
        public String getDefaultGbpPayoutAccount() {
            return defaultGbpPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in NZD.
         */
        public String getDefaultNzdPayoutAccount() {
            return defaultNzdPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in SEK.
         */
        public String getDefaultSekPayoutAccount() {
            return defaultSekPayoutAccount;
        }

        /**
         * ID of the [bank account](#core-endpoints-creditor-bank-accounts) which is set up to
         * receive payouts in USD.
         */
        public String getDefaultUsdPayoutAccount() {
            return defaultUsdPayoutAccount;
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
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
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
         * The minimum interval, in working days, between the sending of a pre-notification to the
         * customer, and the charge date of a payment using this scheme identifier.
         * 
         * By default, GoCardless sends these notifications automatically. Please see our
         * [compliance requirements](#appendix-compliance-requirements) for more details.
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
            AUD, @SerializedName("CAD")
            CAD, @SerializedName("DKK")
            DKK, @SerializedName("EUR")
            EUR, @SerializedName("GBP")
            GBP, @SerializedName("NZD")
            NZD, @SerializedName("SEK")
            SEK, @SerializedName("USD")
            USD, @SerializedName("unknown")
            UNKNOWN
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
            PAY_TO, @SerializedName("sepa")
            SEPA, @SerializedName("sepa_credit_transfer")
            SEPA_CREDIT_TRANSFER, @SerializedName("sepa_instant_credit_transfer")
            SEPA_INSTANT_CREDIT_TRANSFER, @SerializedName("unknown")
            UNKNOWN
        }
    }
}
