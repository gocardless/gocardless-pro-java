package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BankAuthorisation;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with bank authorisation resources.
 *
 * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created against
 * a specific bank, usually the bank that provides the payer's account.
 * 
 * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing Request
 * Flows) to ensure we meet regulatory requirements for checkout flows.
 */
public class BankAuthorisationService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#bankAuthorisations() }.
     */
    public BankAuthorisationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Create a Bank Authorisation.
     */
    public BankAuthorisationCreateRequest create() {
        return new BankAuthorisationCreateRequest(httpClient);
    }

    /**
     * Get a single bank authorisation.
     */
    public BankAuthorisationGetRequest get(String identity) {
        return new BankAuthorisationGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BankAuthorisationService#create }.
     *
     * Create a Bank Authorisation.
     */
    public static final class BankAuthorisationCreateRequest
            extends IdempotentPostRequest<BankAuthorisation> {
        private String authorisationType;
        private Links links;
        private String redirectUri;

        /**
         * Type of authorisation, can be either 'mandate' or 'payment'.
         */
        public BankAuthorisationCreateRequest withAuthorisationType(String authorisationType) {
            this.authorisationType = authorisationType;
            return this;
        }

        public BankAuthorisationCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this
         * authorisation was created.
         */
        public BankAuthorisationCreateRequest withLinksBillingRequest(String billingRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withBillingRequest(billingRequest);
            return this;
        }

        /**
         * URL that the payer can be redirected to after authorising the payment.
         * 
         * On completion of bank authorisation, the query parameter of either `outcome=success` or
         * `outcome=failure` will be appended to the `redirect_uri` to indicate the result of the
         * bank authorisation. If the bank authorisation is expired, the query parameter
         * `outcome=timeout` will be appended to the `redirect_uri`, in which case you should prompt
         * the user to try the bank authorisation step again.
         * 
         * The `redirect_uri` you provide should handle the `outcome` query parameter for displaying
         * the result of the bank authorisation as outlined above.
         * 
         * The BillingRequestFlow ID will also be appended to the `redirect_uri` as query parameter
         * `id=BRF123`.
         * 
         * Defaults to `https://pay.gocardless.com/billing/static/thankyou`.
         */
        public BankAuthorisationCreateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public BankAuthorisationCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<BankAuthorisation> handleConflict(HttpClient httpClient, String id) {
            BankAuthorisationGetRequest request = new BankAuthorisationGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private BankAuthorisationCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BankAuthorisationCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "bank_authorisations";
        }

        @Override
        protected String getEnvelope() {
            return "bank_authorisations";
        }

        @Override
        protected Class<BankAuthorisation> getResponseClass() {
            return BankAuthorisation.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String billingRequest;

            /**
             * ID of the [billing request](#billing-requests-billing-requests) against which this
             * authorisation was created.
             */
            public Links withBillingRequest(String billingRequest) {
                this.billingRequest = billingRequest;
                return this;
            }
        }
    }

    /**
     * Request class for {@link BankAuthorisationService#get }.
     *
     * Get a single bank authorisation.
     */
    public static final class BankAuthorisationGetRequest extends GetRequest<BankAuthorisation> {
        @PathParam
        private final String identity;

        private BankAuthorisationGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BankAuthorisationGetRequest withHeader(String headerName, String headerValue) {
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
            return "bank_authorisations/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "bank_authorisations";
        }

        @Override
        protected Class<BankAuthorisation> getResponseClass() {
            return BankAuthorisation.class;
        }
    }
}
