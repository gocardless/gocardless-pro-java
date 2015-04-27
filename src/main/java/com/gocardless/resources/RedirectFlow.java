package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a redirect flow resource returned from the API.
 *
 * Redirect flows enable you to use GoCardless Pro's secure payment pages to set up mandates with
 * your customers.
 * 
 * The overall flow is:
 * 
 * 1. You
 * [create](https://developer.gocardless.com/pro/2014-11-03/#create-a-redirect-flow) a redirect flow
 * for your customer, and redirect them to the returned redirect url, e.g.
 * `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your customer supplies their name, email,
 * address, and bank account details, and submits the form. This securely stores their details, and
 * redirects them back to your `success_redirect_url` with `redirect_flow_id=RE123` in the
 * querystring.
 * 
 * 3. You
 * [complete](https://developer.gocardless.com/pro/2014-11-03/#complete-a-redirect-flow) the redirect
 * flow, which creates a
 * [customer](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-customers), [customer
 * bank
 * account](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-customer-bank-accounts),
 * and [mandate](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-mandates), and
 * returns the ID of the mandate. You may wish to create a
 * [subscription](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-subscriptions) or
 * [payment](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-payments) at this
 * point.
 * 
 * It is recommended that you link the redirect flow to your user object as soon as it is
 * created, and attach the created resources to that user in the complete step.
 * 
 * Redirect flows
 * expire 30 minutes after they are first created. You cannot
 * [complete](https://developer.gocardless.com/pro/2014-11-03/#complete-a-redirect-flow) an expired
 * redirect flow.
 * 
 * [View an example
 * integration](https://pay-sandbox.gocardless.com/AL000000AKFPFF) that uses redirect flows.
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
     * Fixed [timestamp](https://developer.gocardless.com/pro/2014-11-03/#overview-time-zones-dates),
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * A description of the item the customer is paying for
     */
    public String getDescription() {
        return description;
    }

    /**
     * Unique identifier, beginning with "RE"
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The URI to redirect the customer to to setup their mandate
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
     * The customer's session ID
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * The URI to redirect to upon success mandate setup
     */
    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public enum Scheme {
        @SerializedName("bacs")
        BACS, @SerializedName("sepa_core")
        SEPA_CORE,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String mandate;

        /**
         * The [creditor](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-creditors) for whom
         * the mandate will be created. The `name` of the creditor will be displayed on the payment page.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of [mandate](https://developer.gocardless.com/pro/2014-11-03/#api-endpoints-mandates) created
         * by this redirect flow.<br/>**Note**: this property will not be present until the redirect flow has
         * been successfully completed.
         */
        public String getMandate() {
            return mandate;
        }
    }
}
