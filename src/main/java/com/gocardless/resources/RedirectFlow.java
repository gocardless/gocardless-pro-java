package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a redirect flow resource returned from the API.
 *
 * Redirect flows enable you to use GoCardless Pro's [hosted payment
 * pages](https://pay-sandbox.gocardless.com/AL000000AKFPFF) to set up mandates with your customers.
 * These pages are fully compliant and have been translated into Dutch, French, German, Italian,
 * Portuguese and Spanish.
 * 
 * The overall flow is:
 * 
 * 1. You [create](#create-a-redirect-flow) a
 * redirect flow for your customer, and redirect them to the returned redirect url, e.g.
 * `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your customer supplies their name, email,
 * address, and bank account details, and submits the form. This securely stores their details, and
 * redirects them back to your `success_redirect_url` with `redirect_flow_id=RE123` in the
 * querystring.
 * 
 * 3. You [complete](#complete-a-redirect-flow) the redirect flow, which creates a
 * [customer](#core-endpoints-customers), [customer bank
 * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates), and
 * returns the ID of the mandate. You may wish to create a
 * [subscription](#core-endpoints-subscriptions) or [payment](#core-endpoints-payments) at this
 * point.
 * 
 * It is recommended that you link the redirect flow to your user object as soon as it is
 * created, and attach the created resources to that user in the complete step.
 * 
 * Redirect flows
 * expire 30 minutes after they are first created. You cannot complete an expired redirect flow.
 */
public class RedirectFlow {
    private RedirectFlow() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String description;
    private String id;
    private Links links;
    private String redirectUrl;
    private Scheme scheme;
    private String sessionToken;
    private String successRedirectUrl;

    /**
     * Fixed [timestamp](#overview-time-zones-dates), recording when this resource was created.
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
     * of a mandate for the specified scheme.
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
     * The URL to redirect to upon successful mandate setup.
     */
    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public enum Scheme {
        @SerializedName("autogiro")
        AUTOGIRO, @SerializedName("bacs")
        BACS, @SerializedName("sepa_core")
        SEPA_CORE, @SerializedName("sepa_cor1")
        SEPA_COR1,
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
         * The [creditor](#whitelabel-partner-endpoints-creditors) for whom the mandate will be created. The
         * `name` of the creditor will be displayed on the payment page.
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
