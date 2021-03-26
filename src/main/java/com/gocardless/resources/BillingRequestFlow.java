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
    private String createdAt;
    private String expiresAt;
    private Links links;
    private String redirectUri;

    /**
     * URL for a GC-controlled flow which will allow the payer to fulfil the billing request
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
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

    public Links getLinks() {
        return links;
    }

    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    public String getRedirectUri() {
        return redirectUri;
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
