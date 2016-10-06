package com.gocardless.services;

import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.RedirectFlow;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

/**
 * Service class for working with redirect flow resources.
 *
 * Redirect flows enable you to use GoCardless' [hosted payment
 * pages](https://pay-sandbox.gocardless.com/AL000000AKFPFF) to set up mandates with your customers.
 * These pages are fully compliant and have been translated into Dutch, French, German, Italian,
 * Portuguese, Spanish and Swedish.
 * 
 * The overall flow is:
 * 
 * 1. You
 * [create](#redirect-flows-create-a-redirect-flow) a redirect flow for your customer, and redirect
 * them to the returned redirect url, e.g. `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your
 * customer supplies their name, email, address, and bank account details, and submits the form. This
 * securely stores their details, and redirects them back to your `success_redirect_url` with
 * `redirect_flow_id=RE123` in the querystring.
 * 
 * 3. You
 * [complete](#redirect-flows-complete-a-redirect-flow) the redirect flow, which creates a
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
public class RedirectFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#redirectFlows() }.
     */
    public RedirectFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * hosted payment pages.
     */
    public RedirectFlowCreateRequest create() {
        return new RedirectFlowCreateRequest(httpClient);
    }

    /**
     * Returns all details about a single redirect flow
     */
    public RedirectFlowGetRequest get(String identity) {
        return new RedirectFlowGetRequest(httpClient, identity);
    }

    /**
     * This creates a [customer](#core-endpoints-customers), [customer bank
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates) using
     * the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will
     * return a `redirect_flow_incomplete` error if your customer has not yet been redirected back to
     * your site, and a `redirect_flow_already_completed` error if your integration has already completed
     * this flow. It will return a `bad_request` error if the `session_token` differs to the one supplied
     * when the redirect flow was created.
     */
    public RedirectFlowCompleteRequest complete(String identity) {
        return new RedirectFlowCompleteRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RedirectFlowService#create }.
     *
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * hosted payment pages.
     */
    public static final class RedirectFlowCreateRequest extends IdempotentPostRequest<RedirectFlow> {
        private String description;
        private Links links;
        private Scheme scheme;
        private String sessionToken;
        private String successRedirectUrl;

        /**
         * A description of the item the customer is paying for. This will be shown on the hosted payment
         * pages.
         */
        public RedirectFlowCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        public RedirectFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * The [creditor](#whitelabel-partner-endpoints-creditors) for whom the mandate will be created. The
         * `name` of the creditor will be displayed on the payment page. Required if your account manages
         * multiple creditors.
         */
        public RedirectFlowCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * The Direct Debit scheme of the mandate. If specified, the payment pages will only allow the set-up
         * of a mandate for the specified scheme.
         */
        public RedirectFlowCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again when it is
         * completed. This allows integrators to ensure that the user who was originally sent to the
         * GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCreateRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        /**
         * The URL to redirect to upon successful mandate setup. You must use a URL beginning `https` in the
         * live environment.
         */
        public RedirectFlowCreateRequest withSuccessRedirectUrl(String successRedirectUrl) {
            this.successRedirectUrl = successRedirectUrl;
            return this;
        }

        public RedirectFlowCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<RedirectFlow> handleConflict(HttpClient httpClient, String id) {
            return new RedirectFlowGetRequest(httpClient, id);
        }

        private RedirectFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Scheme {
            @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("sepa_core")
            SEPA_CORE, @SerializedName("sepa_cor1")
            SEPA_COR1;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String creditor;

            /**
             * The [creditor](#whitelabel-partner-endpoints-creditors) for whom the mandate will be created. The
             * `name` of the creditor will be displayed on the payment page. Required if your account manages
             * multiple creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link RedirectFlowService#get }.
     *
     * Returns all details about a single redirect flow
     */
    public static final class RedirectFlowGetRequest extends GetRequest<RedirectFlow> {
        @PathParam
        private final String identity;

        private RedirectFlowGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }
    }

    /**
     * Request class for {@link RedirectFlowService#complete }.
     *
     * This creates a [customer](#core-endpoints-customers), [customer bank
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates) using
     * the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will
     * return a `redirect_flow_incomplete` error if your customer has not yet been redirected back to
     * your site, and a `redirect_flow_already_completed` error if your integration has already completed
     * this flow. It will return a `bad_request` error if the `session_token` differs to the one supplied
     * when the redirect flow was created.
     */
    public static final class RedirectFlowCompleteRequest extends PostRequest<RedirectFlow> {
        @PathParam
        private final String identity;
        private String sessionToken;

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again when it is
         * completed. This allows integrators to ensure that the user who was originally sent to the
         * GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCompleteRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private RedirectFlowCompleteRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows/:identity/actions/complete";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
