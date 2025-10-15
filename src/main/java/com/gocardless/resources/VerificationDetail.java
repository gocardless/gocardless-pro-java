package com.gocardless.resources;

import java.util.List;

/**
 * Represents a verification detail resource returned from the API.
 *
 * Verification details represent any information needed by GoCardless to verify a creditor.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: These endpoints are restricted to customers who want to collect
 * their merchant's verification details and pass them to GoCardless via our API. Please [get in
 * touch](mailto:help@gocardless.com) if you wish to enable this feature on your account.
 * </p>
 */
public class VerificationDetail {
    private VerificationDetail() {
        // blank to prevent instantiation
    }

    private String postalCode;
    private String addressLine3;
    private String city;
    private List<Director> directors;
    private String name;
    private String companyNumber;
    private String addressLine1;
    private String addressLine2;
    private Links links;
    private String description;

    /**
     * The company's postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The third line of the company's address.
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * The city of the company's address.
     */
    public String getCity() {
        return city;
    }

    /**
     * The company's directors.
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * The company's legal name.
     */
    public String getName() {
        return name;
    }

    /**
     * The company's registration number.
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * The first line of the company's address.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * The second line of the company's address.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
    * 
    */
    public Links getLinks() {
        return links;
    }

    /**
     * A summary describing what the company does.
     */
    public String getDescription() {
        return description;
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

        private String creditor;

        /**
         * ID of the [creditor](#core-endpoints-creditors)
         */
        public String getCreditor() {
            return creditor;
        }
    }

    /**
     * Represents a director resource returned from the API.
     *
     * A primary director of the company represented by the creditor.
     */
    public static class Director {
        private Director() {
            // blank to prevent instantiation
        }

        private String postalCode;
        private String countryCode;
        private String givenName;
        private String familyName;
        private String dateOfBirth;
        private String street;
        private String city;

        /**
         * The person's postal code.
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * The person's given name.
         */
        public String getGivenName() {
            return givenName;
        }

        /**
         * The person's family name.
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * The person's date of birth.
         */
        public String getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * The street of the person's address.
         */
        public String getStreet() {
            return street;
        }

        /**
         * The city of the person's address.
         */
        public String getCity() {
            return city;
        }
    }
}
