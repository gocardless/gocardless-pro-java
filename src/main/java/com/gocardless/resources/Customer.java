package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a customer resource returned from the API.
 *
 * Customer objects hold the contact details for a customer. A customer can have several [customer
 * bank accounts](#core-endpoints-customer-bank-accounts), which in turn can have several Direct
 * Debit [mandates](#core-endpoints-mandates).
 * 
 * Notes:
 * - the `swedish_identity_number` field may only be supplied for Swedish customers, and must be
 * supplied if you intend to set up an Autogiro mandate with the customer.
 * - the `danish_identity_number` field may only be supplied for Danish customers, and must be
 * supplied if you intend to set up a Betalingsservice mandate with the customer.
 */
public class Customer {
    private Customer() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String companyName;
    private String countryCode;
    private String createdAt;
    private String danishIdentityNumber;
    private String email;
    private String familyName;
    private String givenName;
    private String id;
    private String language;
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
     * Customer's company name. Required unless a `given_name` and `family_name` are provided.
     */
    public String getCompanyName() {
        return companyName;
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
     * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
     * if the customer's bank account is denominated in Danish krone (DKK).
     */
    public String getDanishIdentityNumber() {
        return danishIdentityNumber;
    }

    /**
     * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
     * to this customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Customer's surname. Required unless a `company_name` is provided.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Customer's first name. Required unless a `company_name` is provided.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Unique identifier, beginning with "CU".
     */
    public String getId() {
        return id;
    }

    /**
     * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
     * notification emails sent by GoCardless if your organisation does not send its own (see [compliance
     * requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
     * "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
     * chosen based on the `country_code` (if supplied) or default to "en".
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
     * values up to 500 characters.
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
     * The customer's address region, county or department.
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
