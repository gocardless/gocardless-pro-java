package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.CreditorBankAccountValidate;

/**
 * Service class for working with creditor bank account validate resources.
 *
 * Creditor Bank Accounts hold the bank details of a [creditor](#core-endpoints-creditors). These
 * are the bank accounts which your [payouts](#core-endpoints-payouts) will be sent to.
 * 
 * When all locale details and Iban are supplied validates creditor bank details without creating a
 * creditor bank account and also provdes bank details such as name and icon url. When partial
 * details are are provided the endpoint will only provide bank details such as name and icon url
 * but will not be able to determine if the provided details are valid.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is not available for partner integrations.
 * </p>
 */
public class CreditorBankAccountValidateService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#creditorBankAccountValidates() }.
     */
    public CreditorBankAccountValidateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Validate bank details without creating a creditor bank account
     */
    public CreditorBankAccountValidateValidateRequest validate() {
        return new CreditorBankAccountValidateValidateRequest(httpClient);
    }

    /**
     * Request class for {@link CreditorBankAccountValidateService#validate }.
     *
     * Validate bank details without creating a creditor bank account
     */
    public static final class CreditorBankAccountValidateValidateRequest
            extends PostRequest<CreditorBankAccountValidate> {
        private String iban;
        private LocalDetails localDetails;

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
         * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
         */
        public CreditorBankAccountValidateValidateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public CreditorBankAccountValidateValidateRequest withLocalDetails(
                LocalDetails localDetails) {
            this.localDetails = localDetails;
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public CreditorBankAccountValidateValidateRequest withLocalDetailsBankNumber(
                String bankNumber) {
            if (localDetails == null) {
                localDetails = new LocalDetails();
            }
            localDetails.withBankNumber(bankNumber);
            return this;
        }

        /**
         * Branch code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public CreditorBankAccountValidateValidateRequest withLocalDetailsSortCode(
                String sortCode) {
            if (localDetails == null) {
                localDetails = new LocalDetails();
            }
            localDetails.withSortCode(sortCode);
            return this;
        }

        private CreditorBankAccountValidateValidateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public CreditorBankAccountValidateValidateRequest withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "creditor_bank_accounts/validate";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccountValidate> getResponseClass() {
            return CreditorBankAccountValidate.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }

        public static class LocalDetails {
            private String bankNumber;
            private String sortCode;

            /**
             * Bank account number - see [local details](#appendix-local-bank-details) for more
             * information. Alternatively you can provide an `iban`.
             */
            public LocalDetails withBankNumber(String bankNumber) {
                this.bankNumber = bankNumber;
                return this;
            }

            /**
             * Branch code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
             */
            public LocalDetails withSortCode(String sortCode) {
                this.sortCode = sortCode;
                return this;
            }
        }
    }
}
