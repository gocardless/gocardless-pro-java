package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BankAccountHolderVerification;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Service class for working with bank account holder verification resources.
 *
 * Create a bank account holder verification for a bank account.
 */
public class BankAccountHolderVerificationService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#bankAccountHolderVerifications() }.
     */
    public BankAccountHolderVerificationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Verify the account holder of the bank account. A complete verification can be attached when
     * creating an outbound payment. This endpoint allows partner merchants to create Confirmation
     * of Payee checks on customer bank accounts before sending outbound payments.
     */
    public BankAccountHolderVerificationCreateRequest create() {
        return new BankAccountHolderVerificationCreateRequest(httpClient);
    }

    /**
     * Fetches a bank account holder verification by ID.
     */
    public BankAccountHolderVerificationGetRequest get(String identity) {
        return new BankAccountHolderVerificationGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BankAccountHolderVerificationService#create }.
     *
     * Verify the account holder of the bank account. A complete verification can be attached when
     * creating an outbound payment. This endpoint allows partner merchants to create Confirmation
     * of Payee checks on customer bank accounts before sending outbound payments.
     */
    public static final class BankAccountHolderVerificationCreateRequest
            extends IdempotentPostRequest<BankAccountHolderVerification> {
        private Links links;
        private Type type;

        public BankAccountHolderVerificationCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * The ID of the bank account to verify, e.g. "BA123".
         */
        public BankAccountHolderVerificationCreateRequest withLinksBankAccount(String bankAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withBankAccount(bankAccount);
            return this;
        }

        /**
         * Type of the verification that has been performed eg. [Confirmation of
         * Payee](https://www.wearepay.uk/what-we-do/overlay-services/confirmation-of-payee/)
         */
        public BankAccountHolderVerificationCreateRequest withType(Type type) {
            this.type = type;
            return this;
        }

        public BankAccountHolderVerificationCreateRequest withIdempotencyKey(
                String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<BankAccountHolderVerification> handleConflict(HttpClient httpClient,
                String id) {
            BankAccountHolderVerificationGetRequest request =
                    new BankAccountHolderVerificationGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private BankAccountHolderVerificationCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BankAccountHolderVerificationCreateRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "bank_account_holder_verifications";
        }

        @Override
        protected String getEnvelope() {
            return "bank_account_holder_verifications";
        }

        @Override
        protected Class<BankAccountHolderVerification> getResponseClass() {
            return BankAccountHolderVerification.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Type {
            @SerializedName("confirmation_of_payee")
            CONFIRMATION_OF_PAYEE, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String bankAccount;

            /**
             * The ID of the bank account to verify, e.g. "BA123".
             */
            public Links withBankAccount(String bankAccount) {
                this.bankAccount = bankAccount;
                return this;
            }
        }
    }

    /**
     * Request class for {@link BankAccountHolderVerificationService#get }.
     *
     * Fetches a bank account holder verification by ID.
     */
    public static final class BankAccountHolderVerificationGetRequest
            extends GetRequest<BankAccountHolderVerification> {
        @PathParam
        private final String identity;

        private BankAccountHolderVerificationGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BankAccountHolderVerificationGetRequest withHeader(String headerName,
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
            return "bank_account_holder_verifications/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "bank_account_holder_verifications";
        }

        @Override
        protected Class<BankAccountHolderVerification> getResponseClass() {
            return BankAccountHolderVerification.class;
        }
    }
}
