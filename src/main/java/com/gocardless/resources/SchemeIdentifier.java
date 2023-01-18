package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

public class SchemeIdentifier {
    private SchemeIdentifier() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private Boolean canSpecifyMandateReference;
    private String city;
    private String countryCode;
    private String createdAt;
    private Currency currency;
    private String email;
    private String id;
    private Integer minimumAdvanceNotice;
    private String name;
    private String phoneNumber;
    private String postalCode;
    private String reference;
    private String region;
    private Scheme scheme;
    private Status status;

    /**
     * The first line of the scheme identifier's support address.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * The second line of the scheme identifier's support address.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * The third line of the scheme identifier's support address.
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Whether a custom reference can be submitted for mandates using this scheme identifier.
     */
    public Boolean getCanSpecifyMandateReference() {
        return canSpecifyMandateReference;
    }

    /**
     * The city of the scheme identifier's support address.
     */
    public String getCity() {
        return city;
    }

    /**
     * [ISO 3166-1 alpha-2
     * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
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
     * The currency of the scheme identifier.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Scheme identifier's support email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Unique identifier, usually beginning with "SU".
     */
    public String getId() {
        return id;
    }

    /**
     * The minimum interval, in working days, between the sending of a pre-notification to the
     * customer, and the charge date of a payment using this scheme identifier.
     * 
     * By default, GoCardless sends these notifications automatically. Please see our [compliance
     * requirements](#appendix-compliance-requirements) for more details.
     */
    public Integer getMinimumAdvanceNotice() {
        return minimumAdvanceNotice;
    }

    /**
     * The name which appears on customers' bank statements.
     */
    public String getName() {
        return name;
    }

    /**
     * Scheme identifier's support phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The scheme identifier's support postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The scheme-unique identifier against which payments are submitted.
     */
    public String getReference() {
        return reference;
    }

    /**
     * The scheme identifier's support address region, county or department.
     */
    public String getRegion() {
        return region;
    }

    /**
     * The scheme which this scheme identifier applies to.
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * The status of the scheme identifier. Only `active` scheme identifiers will be applied to a
     * creditor and used against payments.
     */
    public Status getStatus() {
        return status;
    }

    public enum Currency {
        @SerializedName("AUD")
        AUD, @SerializedName("CAD")
        CAD, @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("NZD")
        NZD, @SerializedName("SEK")
        SEK, @SerializedName("USD")
        USD, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Scheme {
        @SerializedName("ach")
        ACH, @SerializedName("autogiro")
        AUTOGIRO, @SerializedName("bacs")
        BACS, @SerializedName("becs")
        BECS, @SerializedName("becs_nz")
        BECS_NZ, @SerializedName("betalingsservice")
        BETALINGSSERVICE, @SerializedName("faster_payments")
        FASTER_PAYMENTS, @SerializedName("pad")
        PAD, @SerializedName("pay_to")
        PAY_TO, @SerializedName("sepa")
        SEPA, @SerializedName("sepa_credit_transfer")
        SEPA_CREDIT_TRANSFER, @SerializedName("sepa_instant_credit_transfer")
        SEPA_INSTANT_CREDIT_TRANSFER, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("active")
        ACTIVE, @SerializedName("unknown")
        UNKNOWN
    }
}
