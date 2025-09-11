package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BankAccountDetail;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with bank account detail resources.
 *
 * Retrieve bank account details in JWE encrypted format
 */
public class BankAccountDetailService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#bankAccountDetails() }.
     */
    public BankAccountDetailService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns bank account details in the flattened JSON Web Encryption format described in RFC
     * 7516
     */
    public BankAccountDetailGetRequest get(String identity) {
        return new BankAccountDetailGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BankAccountDetailService#get }.
     *
     * Returns bank account details in the flattened JSON Web Encryption format described in RFC
     * 7516
     */
    public static final class BankAccountDetailGetRequest extends GetRequest<BankAccountDetail> {
        @PathParam
        private final String identity;

        private BankAccountDetailGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BankAccountDetailGetRequest withHeader(String headerName, String headerValue) {
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
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "bank_account_details/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "bank_account_details";
        }

        @Override
        protected Class<BankAccountDetail> getResponseClass() {
            return BankAccountDetail.class;
        }
    }
}
