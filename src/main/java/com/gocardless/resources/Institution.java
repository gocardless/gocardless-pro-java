package com.gocardless.resources;

import java.util.List;

/**
 * Represents a institution resource returned from the API.
 *
 * Institutions that are supported when creating [Bank
 * Authorisations](#billing-requests-bank-authorisations) for a particular country or purpose.
 * 
 * Not all institutions support both Payment Initiation (PIS) and Account Information (AIS)
 * services.
 */
public class Institution {
    private Institution() {
        // blank to prevent instantiation
    }

    private String countryCode;
    private String iconUrl;
    private String id;
    private String logoUrl;
    private String name;
    private List<String> roles;

    /**
     * [ISO
     * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code. The country code of the institution.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * A URL pointing to the icon for this institution
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * The unique identifier for this institution
     */
    public String getId() {
        return id;
    }

    /**
     * A URL pointing to the logo for this institution
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * A human readable name for this institution
     */
    public String getName() {
        return name;
    }

    /**
     * Assigned roles for an institution
     */
    public List<String> getRoles() {
        return roles;
    }
}
