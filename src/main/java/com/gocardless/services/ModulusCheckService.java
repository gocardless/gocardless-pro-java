package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.ModulusCheck;

/**
 * Service class for working with modulus check resources.
 *
 * Check whether an account number and bank / branch code combination are compatible.
 */
public class ModulusCheckService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#modulusChecks() }.
     */
    public ModulusCheckService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Performs a modulus check.
     * 
     * Bank account details may be supplied using [local
     * details](#ui-local-bank-details) or an IBAN.
     */
    public ModulusCheckCreateRequest create() {
        return new ModulusCheckCreateRequest(httpClient);
    }

    /**
     * Request class for {@link ModulusCheckService#create }.
     *
     * Performs a modulus check.
     * 
     * Bank account details may be supplied using [local
     * details](#ui-local-bank-details) or an IBAN.
     */
    public static final class ModulusCheckCreateRequest extends PostRequest<ModulusCheck> {
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String iban;

        /**
         * Bank account number - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public ModulusCheckCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public ModulusCheckCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public ModulusCheckCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Must be provided if specifying local details.
         */
        public ModulusCheckCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details).
         */
        public ModulusCheckCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        private ModulusCheckCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/modulus_checks";
        }

        @Override
        protected String getEnvelope() {
            return "modulus_checks";
        }

        @Override
        protected Class<ModulusCheck> getResponseClass() {
            return ModulusCheck.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
