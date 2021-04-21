package com.gocardless.resources;

/**
 * Represents a billing request flow resource returned from the API.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a scheme
 * with strong payer
 * authorisation (such as open banking single payments).
 */
public class BillingRequestFlow {
    private BillingRequestFlow() {
        // blank to prevent instantiation
    }

    private String authorisationUrl;
    private Boolean autoFulfil;
    private String createdAt;
    private String expiresAt;
    private String id;
    private Links links;
    private Boolean lockBankAccountDetails;
    private Boolean lockCustomerDetails;
    private String redirectUri;
    private String sessionToken;

    /**
     * URL for a GC-controlled flow which will allow the payer to fulfil the billing request
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * Fulfil the Billing Request on completion of the flow (true by default)
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
     * If true, the payer will not be able to change their bank account within the flow
     */
    public Boolean getLockBankAccountDetails() {
        return lockBankAccountDetails;
    }

    /**
     * If true, the payer will not be able to edit their customer details within the flow
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String billingRequest;

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow was
         * created.
         */
        public String getBillingRequest() {
            return billingRequest;
        }
    }
}
