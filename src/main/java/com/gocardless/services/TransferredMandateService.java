package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.TransferredMandate;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with transferred mandate resources.
 *
 * Mandates that have been transferred using Current Account Switch Service
 */
public class TransferredMandateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#transferredMandates() }.
     */
    public TransferredMandateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns new customer bank details for a mandate that's been recently transferred
     */
    public TransferredMandateTransferredMandatesRequest transferredMandates(String identity) {
        return new TransferredMandateTransferredMandatesRequest(httpClient, identity);
    }

    /**
     * Request class for {@link TransferredMandateService#transferredMandates }.
     *
     * Returns new customer bank details for a mandate that's been recently transferred
     */
    public static final class TransferredMandateTransferredMandatesRequest
            extends GetRequest<TransferredMandate> {
        @PathParam
        private final String identity;

        private TransferredMandateTransferredMandatesRequest(HttpClient httpClient,
                String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public TransferredMandateTransferredMandatesRequest withHeader(String headerName,
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
            return "transferred_mandates/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "transferred_mandates";
        }

        @Override
        protected Class<TransferredMandate> getResponseClass() {
            return TransferredMandate.class;
        }
    }
}
