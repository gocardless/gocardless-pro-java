package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a redirect flow resource returned from the API.
 *
 * Redirect flows enable you to use GoCardless' [hosted payment
 * pages](https://pay-sandbox.gocardless.com/AL000000AKFPFF) to set up mandates with your customers.
 * These pages are fully compliant and have been translated into Dutch, French, German, Italian,
 * Portuguese, Spanish and Swedish.
 * 
 * The overall flow is:
 * 
 * 1. You [create](#redirect-flows-create-a-redirect-flow) a redirect flow for your customer, and
 * redirect them to the returned redirect url, e.g. `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your customer supplies their name, email, address, and bank account details, and submits the
 * form. This securely stores their details, and redirects them back to your `success_redirect_url`
 * with `redirect_flow_id=RE123` in the querystring.
 * 
 * 3. You [complete](#redirect-flows-complete-a-redirect-flow) the redirect flow, which creates a
 * [customer](#core-endpoints-customers), [customer bank
 * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates), and
 * returns the ID of the mandate. You may wish to create a
 * [subscription](#core-endpoints-subscriptions) or [payment](#core-endpoints-payments) at this
 * point.
 * 
 * Once you have [completed](#redirect-flows-complete-a-redirect-flow) the redirect flow via the API,
 * you should display a confirmation page to your customer, confirming that their Direct Debit has
 * been set up. You can build your own page, or redirect to the one we provide in the
 * `confirmation_url` attribute of the redirect flow.
 * 
 * Redirect flows expire 30 minutes after they are first created. You cannot complete an expired
 * redirect flow.
 */
public class RedirectFlow {
    private RedirectFlow() {
        // blank to prevent instantiation
    }

    private String confirmationUrl;
    private String createdAt;
    private String description;
    private String id;
    private Links links;
    private String redirectUrl;
    private Scheme scheme;
    private String sessionToken;
    private String successRedirectUrl;

    /**
     * The URL of a confirmation page, which you may optionally redirect the customer to rather than use
     * your own page, that confirms in their chosen language that their Direct Debit has been set up
     * successfully. Only returned once the customer has set up their mandate via the payment pages and
     * the redirect flow has been [completed](#redirect-flows-complete-a-redirect-flow), and only
     * available for 15 minutes from when you complete the redirect flow. The structure of this URL may
     * change at any time, so you should read it directly from the API response.
     */
    public String getConfirmationUrl() {
        return confirmationUrl;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * A description of the item the customer is paying for. This will be shown on the hosted payment
     * pages.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Unique identifier, beginning with "RE".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The URL of the hosted payment pages for this redirect flow. This is the URL you should redirect
     * your customer to.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * The Direct Debit scheme of the mandate. If specified, the payment pages will only allow the set-up
     * of a mandate for the specified scheme. It is recommended that you leave this blank so the most
     * appropriate scheme is picked based on the customer's bank account.
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * The customer's session ID must be provided when the redirect flow is set up and again when it is
     * completed. This allows integrators to ensure that the user who was originally sent to the
     * GoCardless payment pages is the one who has completed them.
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * The URL to redirect to upon successful mandate setup. You must use a URL beginning `https` in the
     * live environment.
     */
    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public enum Scheme {
        @SerializedName("autogiro")
        AUTOGIRO, @SerializedName("bacs")
        BACS, @SerializedName("becs")
        BECS, @SerializedName("becs_nz")
        BECS_NZ, @SerializedName("betalingsservice")
        BETALINGSSERVICE, @SerializedName("sepa_core")
        SEPA_CORE,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String customer;
        private String customerBankAccount;
        private String mandate;

        /**
         * The [creditor](#core-endpoints-creditors) for whom the mandate will be created. The `name` of the
         * creditor will be displayed on the payment page.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of [customer](#core-endpoints-customers) created by this redirect flow.<br/>**Note**: this
         * property will not be present until the redirect flow has been successfully completed.
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of [customer bank account](#core-endpoints-customer-bank-accounts) created by this redirect
         * flow.<br/>**Note**: this property will not be present until the redirect flow has been
         * successfully completed.
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * ID of [mandate](#core-endpoints-mandates) created by this redirect flow.<br/>**Note**: this
         * property will not be present until the redirect flow has been successfully completed.
         */
        public String getMandate() {
            return mandate;
        }
    }
}
