package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequestFlow;

/**
 * Service class for working with billing request flow resources.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a scheme
 * with strong payer
 * authorisation (such as open banking single payments).
 */
public class BillingRequestFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#billingRequestFlows() }.
     */
    public BillingRequestFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new billing request flow.
     */
    public BillingRequestFlowCreateRequest create() {
        return new BillingRequestFlowCreateRequest(httpClient);
    }

    /**
     * Request class for {@link BillingRequestFlowService#create }.
     *
     * Creates a new billing request flow.
     */
    public static final class BillingRequestFlowCreateRequest extends
            PostRequest<BillingRequestFlow> {
        private Links links;
        private Boolean lockExistingDetails;
        private String redirectUri;

        public BillingRequestFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow was
         * created.
         */
        public BillingRequestFlowCreateRequest withLinksBillingRequest(String billingRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withBillingRequest(billingRequest);
            return this;
        }

        /**
         * If true, the payer will not be able to edit their existing details (e.g. customer and bank
         * account) within the billing request flow.
         */
        public BillingRequestFlowCreateRequest withLockExistingDetails(Boolean lockExistingDetails) {
            this.lockExistingDetails = lockExistingDetails;
            return this;
        }

        /**
         * URL that the payer can be redirected to after completing the request flow.
         */
        public BillingRequestFlowCreateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        private BillingRequestFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BillingRequestFlowCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "billing_request_flows";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_flows";
        }

        @Override
        protected Class<BillingRequestFlow> getResponseClass() {
            return BillingRequestFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String billingRequest;

            /**
             * ID of the [billing request](#billing-requests-billing-requests) against which this flow was
             * created.
             */
            public Links withBillingRequest(String billingRequest) {
                this.billingRequest = billingRequest;
                return this;
            }
        }
    }
}
