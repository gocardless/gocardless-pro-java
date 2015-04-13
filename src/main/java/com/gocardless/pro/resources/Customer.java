package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

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

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRegion() {
        return region;
    }
}
