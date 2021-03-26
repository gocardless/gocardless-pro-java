package com.gocardless.resources;

/**
 * Represents a institution resource returned from the API.
 *
 * Institutions that are supported when creating [Bank
 * Authorisations](#billing-requests-bank-authorisations).
 */
public class Institution {
    private Institution() {
        // blank to prevent instantiation
    }

    private String iconUrl;
    private String id;
    private String logoUrl;
    private String name;

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
}
