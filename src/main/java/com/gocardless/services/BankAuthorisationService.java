package com.gocardless.services;

import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.BankAuthorisation;

import com.google.common.collect.ImmutableMap;

/**
 * Service class for working with bank authorisation resources.
 *
 * Bank Authorisations can be used to authorise Billing Requests. Authorisations
 * are created against a specific bank, usually the bank that provides the payer's
 * account.
 * 
 * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs
 * (see Billing Request Flows) to ensure we meet regulatory requirements for
 * checkout flows. The exceptions are integrators with the custom payment pages
 * upgrade, who have been audited to check their flows meet requirements.
 */
public class BankAuthorisationService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#bankAuthorisations() }.
     */
    public BankAuthorisationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Fetches a bank authorisation
     */
    public BankAuthorisationGetRequest get(String identity) {
        return new BankAuthorisationGetRequest(httpClient, identity);
    }

    /**
     * Create a Bank Authorisation.
     */
    public BankAuthorisationCreateRequest create() {
        return new BankAuthorisationCreateRequest(httpClient);
    }

    /**
     * Request class for {@link BankAuthorisationService#get }.
     *
     * Fetches a bank authorisation
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

    /**
     * Request class for {@link BankAuthorisationService#create }.
     *
     * Create a Bank Authorisation.
     */
    public static final class BankAuthorisationCreateRequest extends
            IdempotentPostRequest<BankAuthorisation> {
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
         * ID of the [billing request](#core-endpoints-billing-requests) against which this authorisation was
         * created.
         */
        public BankAuthorisationCreateRequest withLinksBillingRequest(String billingRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withBillingRequest(billingRequest);
            return this;
        }

        /**
         * ID of the [institution](#core-endpoints-institutions) against which this authorisation was
         * created.
         */
        public BankAuthorisationCreateRequest withLinksInstitution(String institution) {
            if (links == null) {
                links = new Links();
            }
            links.withInstitution(institution);
            return this;
        }

        /**
         * ID of the [payment request](#core-endpoints-payment-requests) against which this authorisation was
         * created.
         */
        public BankAuthorisationCreateRequest withLinksPaymentRequest(String paymentRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withPaymentRequest(paymentRequest);
            return this;
        }

        /**
         * URL that the payer can be redirected to after authorising the payment.
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
            private String institution;
            private String paymentRequest;

            /**
             * ID of the [billing request](#core-endpoints-billing-requests) against which this authorisation was
             * created.
             */
            public Links withBillingRequest(String billingRequest) {
                this.billingRequest = billingRequest;
                return this;
            }

            /**
             * ID of the [institution](#core-endpoints-institutions) against which this authorisation was
             * created.
             */
            public Links withInstitution(String institution) {
                this.institution = institution;
                return this;
            }

            /**
             * ID of the [payment request](#core-endpoints-payment-requests) against which this authorisation was
             * created.
             */
            public Links withPaymentRequest(String paymentRequest) {
                this.paymentRequest = paymentRequest;
                return this;
            }
        }
    }
}
