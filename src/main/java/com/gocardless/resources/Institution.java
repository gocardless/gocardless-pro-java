package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

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

    private Boolean autocompletesCollectBankAccount;
    private String countryCode;
    private String iconUrl;
    private String id;
    private String logoUrl;
    private String name;
    private Status status;

    /**
     * Flag to show if selecting this institution in the select_institution action can auto-complete
     * the collect_bank_account action. The bank can return the payer's bank account details to
     * GoCardless.
     */
    public Boolean getAutocompletesCollectBankAccount() {
        return autocompletesCollectBankAccount;
    }

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
     * The status of the institution
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("enabled")
        ENABLED, @SerializedName("disabled")
        DISABLED, @SerializedName("temporarily_disabled")
        TEMPORARILY_DISABLED, @SerializedName("unknown")
        UNKNOWN
    }
}
