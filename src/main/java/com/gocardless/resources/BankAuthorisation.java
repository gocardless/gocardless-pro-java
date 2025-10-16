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
    private String qrCodeUrl;
    private String redirectUri;
    private String url;

    /**
     * Type of authorisation, can be either 'mandate' or 'payment'.
     */
    public AuthorisationType getAuthorisationType() {
        return authorisationType;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when the user has been authorised.
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
     * Fixed [timestamp](#api-usage-dates-and-times), recording when the authorisation URL has been
     * visited.
     */
    public String getLastVisitedAt() {
        return lastVisitedAt;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * URL to a QR code PNG image of the bank authorisation url. This QR code can be used as an
     * alternative to providing the `url` to the payer to allow them to authorise with their mobile
     * devices.
     */
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    /**
     * URL that the payer can be redirected to after authorising the payment.
     * 
     * On completion of bank authorisation, the query parameter of either `outcome=success` or
     * `outcome=failure` will be appended to the `redirect_uri` to indicate the result of the bank
     * authorisation. If the bank authorisation is expired, the query parameter `outcome=timeout`
     * will be appended to the `redirect_uri`, in which case you should prompt the user to try the
     * bank authorisation step again.
     * 
     * Please note: bank authorisations can still fail despite an `outcome=success` on the
     * `redirect_uri`. It is therefore recommended to wait for the relevant bank authorisation
     * event, such as
     * [`BANK_AUTHORISATION_AUTHORISED`](#billing-request-bankauthorisationauthorised),
     * [`BANK_AUTHORISATION_DENIED`](#billing-request-bankauthorisationdenied), or
     * [`BANK_AUTHORISATION_FAILED`](#billing-request-bankauthorisationfailed) in order to show the
     * correct outcome to the user.
     * 
     * The BillingRequestFlow ID will also be appended to the `redirect_uri` as query parameter
     * `id=BRF123`.
     * 
     * Defaults to `https://pay.gocardless.com/billing/static/thankyou`.
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

    /**
     * Represents a link resource returned from the API.
     *
     * 
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String billingRequest;
        private String institution;

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
    }
}
