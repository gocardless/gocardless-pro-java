package com.gocardless.pro.services;

import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Helper;

/**
 * Service class for working with helper resources.
 *
 * 
 */
public class HelperService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.pro.GoCardlessClient#helpers() }.
     */
    public HelperService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a PDF mandate form with a signature field, ready to be signed by your customer. May be
     * fully or partially pre-filled.
     * 
     * You must specify `Accept: application/pdf` on requests to this
     * endpoint.
     * 
     * Bank account details may either be supplied using the IBAN (international bank
     * account number), or [local
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details) section.
     * 
     * To
     * generate a mandate in a foreign language, set your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
     * 
     *
     * _Note:_ If you want to render a PDF of an existing mandate you can also do so using the [mandate
     * show endpoint](https://developer.gocardless.com/pro/#mandates-get-a-single-mandate).
     */
    public HelperMandateRequest mandate() {
        return new HelperMandateRequest(httpClient);
    }

    /**
     * Check whether an account number and bank / branch code combination are compatible.
     * 
     * Bank
     * account details may either be supplied using the IBAN (international bank account number), or
     * [local details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details) section.
     */
    public HelperModulusCheckRequest modulusCheck() {
        return new HelperModulusCheckRequest(httpClient);
    }

    /**
     * Request class for {@link HelperService#mandate }.
     *
     * Returns a PDF mandate form with a signature field, ready to be signed by your customer. May be
     * fully or partially pre-filled.
     * 
     * You must specify `Accept: application/pdf` on requests to this
     * endpoint.
     * 
     * Bank account details may either be supplied using the IBAN (international bank
     * account number), or [local
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details) section.
     * 
     * To
     * generate a mandate in a foreign language, set your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
     * 
     *
     * _Note:_ If you want to render a PDF of an existing mandate you can also do so using the [mandate
     * show endpoint](https://developer.gocardless.com/pro/#mandates-get-a-single-mandate).
     */
    public static final class HelperMandateRequest extends PostRequest<Helper> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String bic;
        private String branchCode;
        private String countryCode;
        private String iban;
        private Map<String, String> links;
        private String mandateReference;
        private String scheme;
        private String signedAt;
        private String sortCode;

        /**
         * Name of the account holder, as known by the bank. Usually this matches the name of the linked
         * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers). This field cannot
         * exceed 18 characters.
         */
        public HelperMandateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * 8 digit, valid UK bank account number.
         */
        public HelperMandateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank identifier code.
         */
        public HelperMandateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Bank Identifier Code
         */
        public HelperMandateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        /**
         * Branch identifier code.
         */
        public HelperMandateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public HelperMandateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * International Bank Account Number
         */
        public HelperMandateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public HelperMandateRequest withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        /**
         * Mandate reference (normally set by GoCardless)
         */
        public HelperMandateRequest withMandateReference(String mandateReference) {
            this.mandateReference = mandateReference;
            return this;
        }

        /**
         * Direct Debit scheme
         */
        public HelperMandateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * Will render a form with this date and no signature field.
         */
        public HelperMandateRequest withSignedAt(String signedAt) {
            this.signedAt = signedAt;
            return this;
        }

        /**
         * 6 digit, valid UK sort code.
         */
        public HelperMandateRequest withSortCode(String sortCode) {
            this.sortCode = sortCode;
            return this;
        }

        private HelperMandateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/helpers/mandate";
        }

        @Override
        protected String getEnvelope() {
            return "data";
        }

        @Override
        protected Class<Helper> getResponseClass() {
            return Helper.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link HelperService#modulusCheck }.
     *
     * Check whether an account number and bank / branch code combination are compatible.
     * 
     * Bank
     * account details may either be supplied using the IBAN (international bank account number), or
     * [local details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/#ui-compliance-local-bank-details) section.
     */
    public static final class HelperModulusCheckRequest extends PostRequest<Helper> {
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String iban;
        private String sortCode;

        /**
         * 8 digit, valid UK bank account number.
         */
        public HelperModulusCheckRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank identifier code.
         */
        public HelperModulusCheckRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch identifier code.
         */
        public HelperModulusCheckRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public HelperModulusCheckRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * International Bank Account Number
         */
        public HelperModulusCheckRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        /**
         * 6 digit, valid UK sort code.
         */
        public HelperModulusCheckRequest withSortCode(String sortCode) {
            this.sortCode = sortCode;
            return this;
        }

        private HelperModulusCheckRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/helpers/modulus_check";
        }

        @Override
        protected String getEnvelope() {
            return "data";
        }

        @Override
        protected Class<Helper> getResponseClass() {
            return Helper.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
