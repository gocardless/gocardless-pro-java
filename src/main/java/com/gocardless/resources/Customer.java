package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a customer resource returned from the API.
 *
 * Customer objects hold the contact details for a customer. A customer can have several <a href=
 * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer
 * bank accounts</a>, which in turn can have several Direct Debit
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandates</a>.
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
    private String phoneNumber;
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
     * <code>company_name</code> value will mean that any mandate created from this customer will be
     * considered to be a "Business PAD" (otherwise, any mandate will be considered to be a
     * "Personal PAD").
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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
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
     * Unique identifier, beginning with "CU".
     */
    public String getId() {
        return id;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">ISO 639-1</a> code. Used as
     * the language for notification emails sent by GoCardless if your organisation does not send
     * its own (see <a href=
     * "https://developer.gocardless.com/api-reference/#appendix-compliance-requirements">compliance
     * requirements</a>). Currently only "en", "fr", "de", "pt", "es", "it", "nl", "da", "nb", "sl",
     * "sv" are supported. If this is not provided, the language will be chosen based on the
     * <code>country_code</code> (if supplied) or default to "en".
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/E.123">ITU E.123</a> formatted phone number, including
     * country code.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The customer's postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The customer's address region, county or department. For US customers a 2 letter
     * <a href="https://en.wikipedia.org/wiki/ISO_3166-2:US">ISO3166-2:US</a> state code is required
     * (e.g. <code>CA</code> for California).
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
