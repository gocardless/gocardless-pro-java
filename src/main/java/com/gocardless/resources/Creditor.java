package com.gocardless.resources;

/**
 * Represents a creditor resource returned from the API.
 *
 * Each [payment](#core-endpoints-payments) taken through the API is linked to a "creditor", to whom
 * the payment is then paid out. In most cases your organisation will have a single "creditor", but
 * the API also supports collecting payments on behalf of others.
 * 
 * Please get in touch if you
 * wish to use this endpoint. Currently, for Anti Money Laundering reasons, any creditors you add
 * must be directly related to your organisation.
 */
public class Creditor {
    private Creditor() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String countryCode;
    private String createdAt;
    private String id;
    private Links links;
    private String name;
    private String postalCode;
    private String region;

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
     * The city of the creditor's address.
     */
    public String getCity() {
        return city;
    }

    /**
     * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code, currently only "GB" is supported.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Fixed [timestamp](#overview-time-zones-dates), recording when this resource was created.
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String defaultEurPayoutAccount;
        private String defaultGbpPayoutAccount;

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
    }
}
