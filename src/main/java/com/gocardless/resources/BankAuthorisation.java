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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when the user has been authorised.
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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when the authorisation URL has been visited.
     */
    public String getLastVisitedAt() {
        return lastVisitedAt;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * URL to a QR code PNG image of the bank authorisation url. This QR code can be used as an
     * alternative to providing the <code>url</code> to the payer to allow them to authorise with
     * their mobile devices.
     */
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    /**
     * URL that the payer can be redirected to after authorising the payment.
     * 
     * On completion of bank authorisation, the query parameter of either
     * <code>outcome=success</code> or <code>outcome=failure</code> will be appended to the
     * <code>redirect_uri</code> to indicate the result of the bank authorisation. If the bank
     * authorisation is expired, the query parameter <code>outcome=timeout</code> will be appended
     * to the <code>redirect_uri</code>, in which case you should prompt the user to try the bank
     * authorisation step again.
     * 
     * Please note: bank authorisations can still fail despite an <code>outcome=success</code> on
     * the <code>redirect_uri</code>. It is therefore recommended to wait for the relevant bank
     * authorisation event, such as <a href=
     * "https://developer.gocardless.com/api-reference/#billing-request-bankauthorisationauthorised"><code>BANK_AUTHORISATION_AUTHORISED</code></a>,
     * <a href=
     * "https://developer.gocardless.com/api-reference/#billing-request-bankauthorisationdenied"><code>BANK_AUTHORISATION_DENIED</code></a>,
     * or <a href=
     * "https://developer.gocardless.com/api-reference/#billing-request-bankauthorisationfailed"><code>BANK_AUTHORISATION_FAILED</code></a>
     * in order to show the correct outcome to the user.
     * 
     * The BillingRequestFlow ID will also be appended to the <code>redirect_uri</code> as query
     * parameter <code>id=BRF123</code>.
     * 
     * Defaults to <code>https://pay.gocardless.com/billing/static/thankyou</code>.
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
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#billing-requests-billing-requests">billing
         * request</a> against which this authorisation was created.
         */
        public String getBillingRequest() {
            return billingRequest;
        }

        /**
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#billing-requests-institutions">institution</a>
         * against which this authorisation was created.
         */
        public String getInstitution() {
            return institution;
        }
    }
}
