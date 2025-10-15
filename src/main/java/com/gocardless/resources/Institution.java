package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

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

    private String logoUrl;
    private String countryCode;
    private Limits limits;
    private Boolean autocompletesCollectBankAccount;
    private Status status;
    private String id;
    private String name;
    private String iconUrl;

    /**
     * A URL pointing to the logo for this institution
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * [ISO
     * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
     * alpha-2 code. The country code of the institution. If nothing is provided, institutions with
     * the country code 'GB' are returned by default.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Defines individual limits for business and personal accounts.
     */
    public Limits getLimits() {
        return limits;
    }

    /**
     * Flag to show if selecting this institution in the select_institution action can auto-complete
     * the collect_bank_account action. The bank can return the payer's bank account details to
     * GoCardless.
     */
    public Boolean getAutocompletesCollectBankAccount() {
        return autocompletesCollectBankAccount;
    }

    /**
     * The status of the institution
     */
    public Status getStatus() {
        return status;
    }

    /**
     * The unique identifier for this institution
     */
    public String getId() {
        return id;
    }

    /**
     * A human readable name for this institution
     */
    public String getName() {
        return name;
    }

    /**
     * A URL pointing to the icon for this institution
     */
    public String getIconUrl() {
        return iconUrl;
    }

    public enum Status {
        @SerializedName("enabled")
        ENABLED, @SerializedName("disabled")
        DISABLED, @SerializedName("temporarily_disabled")
        TEMPORARILY_DISABLED, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a limit resource returned from the API.
     *
     * Defines individual limits for business and personal accounts.
     */
    public static class Limits {
        private Limits() {
            // blank to prevent instantiation
        }

        private Map<String, Object> single;
        private Map<String, Object> daily;

        /**
         * Single transaction limit details for this institution, in the lowest denomination for the
         * currency (e.g. pence in GBP, cents in EUR). The 'limits' property is only available via
         * an authenticated request with a generated access token
         */
        public Map<String, Object> getSingle() {
            return single;
        }

        /**
         * Daily limit details for this institution, in the lowest denomination for the currency
         * (e.g. pence in GBP, cents in EUR). The 'limits' property is only available via an
         * authenticated request with a generated access token
         */
        public Map<String, Object> getDaily() {
            return daily;
        }
    }
}
