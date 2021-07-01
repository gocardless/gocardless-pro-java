package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a bank authorisation resource returned from the API.
 *
 * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created against
 * a specific bank, usually the bank that provides the payer's account.
 * 
 * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing Request
 * Flows) to ensure we meet regulatory requirements for checkout flows.
 */
public class BankAuthorisation {
    private BankAuthorisation() {
        // blank to prevent instantiation
    }

    private AuthorisationType authorisationType;
    private String authorisedAt;
    private String createdAt;
    private String expiresAt;
    private String id;
    private String lastVisitedAt;
    private Links links;
    private String redirectUri;
    private String url;

    /**
     * Type of authorisation, can be either 'mandate' or 'payment'.
     */
    public AuthorisationType getAuthorisationType() {
        return authorisationType;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when the user has been authorised.
     */
    public String getAuthorisedAt() {
        return authorisedAt;
    }

    /**
     * Timestamp when the flow was created
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Timestamp when the url will expire. Each authorisation url currently lasts for 15 minutes,
     * but this can vary by bank.
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Unique identifier, beginning with "BAU".
     */
    public String getId() {
        return id;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when the authorisation URL has
     * been visited.
     */
    public String getLastVisitedAt() {
        return lastVisitedAt;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * URL that the payer can be redirected to after authorising the payment.
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * URL for an oauth flow that will allow the user to authorise the payment
     */
    public String getUrl() {
        return url;
    }

    public enum AuthorisationType {
        @SerializedName("mandate")
        MANDATE, @SerializedName("payment")
        PAYMENT, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String billingRequest;
        private String institution;
        private String paymentRequest;

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this
         * authorisation was created.
         */
        public String getBillingRequest() {
            return billingRequest;
        }

        /**
         * ID of the [institution](#billing-requests-institutions) against which this authorisation
         * was created.
         */
        public String getInstitution() {
            return institution;
        }

        /**
         * ID of the payment request against which this authorisation was created.
         */
        public String getPaymentRequest() {
            return paymentRequest;
        }
    }
}
