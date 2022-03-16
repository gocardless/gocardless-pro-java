package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequestFlow;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with billing request flow resources.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a
 * scheme with strong payer authorisation (such as open banking single payments).
 */
public class BillingRequestFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#billingRequestFlows() }.
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
     * Returns the flow having generated a fresh session token which can be used to power
     * integrations that manipulate the flow.
     */
    public BillingRequestFlowInitialiseRequest initialise(String identity) {
        return new BillingRequestFlowInitialiseRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BillingRequestFlowService#create }.
     *
     * Creates a new billing request flow.
     */
    public static final class BillingRequestFlowCreateRequest
            extends PostRequest<BillingRequestFlow> {
        private Boolean autoFulfil;
        private String exitUri;
        private Links links;
        private Boolean lockBankAccount;
        private Boolean lockCustomerDetails;
        private String redirectUri;
        private Boolean showRedirectButtons;

        /**
         * (Experimental feature) Fulfil the Billing Request on completion of the flow (true by
         * default). Disabling the auto_fulfil is not allowed currently.
         */
        public BillingRequestFlowCreateRequest withAutoFulfil(Boolean autoFulfil) {
            this.autoFulfil = autoFulfil;
            return this;
        }

        /**
         * URL that the payer can be taken to if there isn't a way to progress ahead in flow.
         */
        public BillingRequestFlowCreateRequest withExitUri(String exitUri) {
            this.exitUri = exitUri;
            return this;
        }

        public BillingRequestFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow
         * was created.
         */
        public BillingRequestFlowCreateRequest withLinksBillingRequest(String billingRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withBillingRequest(billingRequest);
            return this;
        }

        /**
         * If true, the payer will not be able to change their bank account within the flow. If the
         * bank_account details are collected as part of bank_authorisation then GC will set this
         * value to true mid flow
         */
        public BillingRequestFlowCreateRequest withLockBankAccount(Boolean lockBankAccount) {
            this.lockBankAccount = lockBankAccount;
            return this;
        }

        /**
         * If true, the payer will not be able to edit their customer details within the flow. If
         * the customer details are collected as part of bank_authorisation then GC will set this
         * value to true mid flow
         */
        public BillingRequestFlowCreateRequest withLockCustomerDetails(
                Boolean lockCustomerDetails) {
            this.lockCustomerDetails = lockCustomerDetails;
            return this;
        }

        /**
         * URL that the payer can be redirected to after completing the request flow.
         */
        public BillingRequestFlowCreateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        /**
         * If true, the payer will be able to see redirect action buttons on Thank You page. These
         * action buttons will provide a way to connect back to the billing request flow app if
         * opened within a mobile app. For successful flow, the button will take the payer back the
         * billing request flow where they will see the success screen. For failure, button will
         * take the payer to url being provided against exit_uri field.
         */
        public BillingRequestFlowCreateRequest withShowRedirectButtons(
                Boolean showRedirectButtons) {
            this.showRedirectButtons = showRedirectButtons;
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
             * ID of the [billing request](#billing-requests-billing-requests) against which this
             * flow was created.
             */
            public Links withBillingRequest(String billingRequest) {
                this.billingRequest = billingRequest;
                return this;
            }
        }
    }

    /**
     * Request class for {@link BillingRequestFlowService#initialise }.
     *
     * Returns the flow having generated a fresh session token which can be used to power
     * integrations that manipulate the flow.
     */
    public static final class BillingRequestFlowInitialiseRequest
            extends PostRequest<BillingRequestFlow> {
        @PathParam
        private final String identity;

        private BillingRequestFlowInitialiseRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestFlowInitialiseRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "billing_request_flows/:identity/actions/initialise";
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

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
