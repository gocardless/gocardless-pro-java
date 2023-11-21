package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.TransferredMandate;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
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
     * Returns encrypted bank details for the transferred mandate
     */
    public TransferredMandateTransferredMandatesRequest transferredMandates(String identity) {
        return new TransferredMandateTransferredMandatesRequest(httpClient, identity);
    }

    /**
     * Request class for {@link TransferredMandateService#transferredMandates }.
     *
     * Returns encrypted bank details for the transferred mandate
     */
    public static final class TransferredMandateTransferredMandatesRequest
            extends GetRequest<TransferredMandate> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public TransferredMandateTransferredMandatesRequest withMetadata(
                Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public TransferredMandateTransferredMandatesRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

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
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (metadata != null) {
                params.put("metadata", metadata);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "transferred_mandate/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "transferred_mandate";
        }

        @Override
        protected Class<TransferredMandate> getResponseClass() {
            return TransferredMandate.class;
        }
    }
}
