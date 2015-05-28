package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a customer resource returned from the API.
 *
 * Customer objects hold the contact details for a customer. A customer can have several [customer
 * bank
 * accounts](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-customer-bank-accounts),
 * which in turn can have several Direct Debit
 * [mandates](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-mandates).
 */
public class Customer {
    private Customer() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String countryCode;
    private String createdAt;
    private String email;
    private String familyName;
    private String givenName;
    private String id;
    private Map<String, String> metadata;
    private String postalCode;
    private String region;

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
     * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/2015-04-29/#overview-time-zones-dates),
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
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
     * Unique identifier, beginning with "CU".
     */
    public String getId() {
        return id;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
     * values up to 200 characters.
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
}
