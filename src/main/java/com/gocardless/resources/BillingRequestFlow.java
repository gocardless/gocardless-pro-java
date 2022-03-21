package com.gocardless.resources;

/**
 * Represents a billing request flow resource returned from the API.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a
 * scheme with strong payer authorisation (such as open banking single payments).
 */
public class BillingRequestFlow {
    private BillingRequestFlow() {
        // blank to prevent instantiation
    }

    private String authorisationUrl;
    private Boolean autoFulfil;
    private String createdAt;
    private String exitUri;
    private String expiresAt;
    private String id;
    private Links links;
    private Boolean lockBankAccount;
    private Boolean lockCustomerDetails;
    private String redirectUri;
    private String sessionToken;
    private Boolean showRedirectButtons;

    /**
     * URL for a GC-controlled flow which will allow the payer to fulfil the billing request
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * (Experimental feature) Fulfil the Billing Request on completion of the flow (true by
     * default). Disabling the auto_fulfil is not allowed currently.
     */
    public Boolean getAutoFulfil() {
        return autoFulfil;
    }

    /**
     * Timestamp when the flow was created
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * URL that the payer can be taken to if there isn't a way to progress ahead in flow.
     */
    public String getExitUri() {
        return exitUri;
    }

    /**
     * Timestamp when the flow will expire. Each flow currently lasts for 7 days.
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Unique identifier, beginning with "BRF".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * If true, the payer will not be able to change their bank account within the flow. If the
     * bank_account details are collected as part of bank_authorisation then GC will set this value
     * to true mid flow
     */
    public Boolean getLockBankAccount() {
        return lockBankAccount;
    }

    /**
     * If true, the payer will not be able to edit their customer details within the flow. If the
     * customer details are collected as part of bank_authorisation then GC will set this value to
     * true mid flow
     */
    public Boolean getLockCustomerDetails() {
        return lockCustomerDetails;
    }

    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Session token populated when responding to the initalise action
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * If true, the payer will be able to see redirect action buttons on Thank You page. These
     * action buttons will provide a way to connect back to the billing request flow app if opened
     * within a mobile app. For successful flow, the button will take the payer back the billing
     * request flow where they will see the success screen. For failure, button will take the payer
     * to url being provided against exit_uri field.
     */
    public Boolean getShowRedirectButtons() {
        return showRedirectButtons;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String billingRequest;

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow
         * was created.
         */
        public String getBillingRequest() {
            return billingRequest;
        }
    }
}
