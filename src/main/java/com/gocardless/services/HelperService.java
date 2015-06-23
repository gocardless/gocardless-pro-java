package com.gocardless.services;

import java.util.HashMap;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Helper;

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
      {@link com.gocardless.GoCardlessClient#helpers() }.
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
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) section.
     * 
     * To
     * generate a mandate in a foreign language, set your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
     * 
     *
     * _Note:_ If you want to render a PDF of an existing mandate you can also do so using the [mandate
     * show endpoint](https://developer.gocardless.com/pro/2015-04-29/#mandates-get-a-single-mandate).
     */
    public HelperMandateRequest mandate() {
        return new HelperMandateRequest(httpClient);
    }

    /**
     * Check whether an account number and bank / branch code combination are compatible.
     * 
     * Bank
     * account details may either be supplied using the IBAN (international bank account number), or
     * [local details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) section.
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
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) section.
     * 
     * To
     * generate a mandate in a foreign language, set your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
     * 
     *
     * _Note:_ If you want to render a PDF of an existing mandate you can also do so using the [mandate
     * show endpoint](https://developer.gocardless.com/pro/2015-04-29/#mandates-get-a-single-mandate).
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

        /**
         * Name of the account holder, as known by the bank. Usually this matches the name of the
         * [customer](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-customers). This field
         * cannot exceed 18 characters.
         */
        public HelperMandateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
         */
        public HelperMandateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
         */
        public HelperMandateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * SWIFT BIC. Optional (will be derived from the `iban` or local details if not provided).
         */
        public HelperMandateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        /**
         * Branch code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
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
         * International Bank Account Number. Alternatively you can provide [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details).
         */
        public HelperMandateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public HelperMandateRequest withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public HelperMandateRequest withLinks(String key, String value) {
            if (links == null) {
                links = new HashMap<>();
            }
            links.put(key, value);
            return this;
        }

        /**
         * Unique 6 to 18 character reference. This may be left blank at the point of signing.
         */
        public HelperMandateRequest withMandateReference(String mandateReference) {
            this.mandateReference = mandateReference;
            return this;
        }

        /**
         * Direct Debit scheme. Can be supplied or automatically detected from the customer's bank account
         * details (if provided).
         */
        public HelperMandateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * If provided, this endpoint will render a form with this date and no signature field.
         */
        public HelperMandateRequest withSignedAt(String signedAt) {
            this.signedAt = signedAt;
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

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link HelperService#modulusCheck }.
     *
     * Check whether an account number and bank / branch code combination are compatible.
     * 
     * Bank
     * account details may either be supplied using the IBAN (international bank account number), or
     * [local details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details). For more
     * information on the different fields required in each country, please see the [local bank
     * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) section.
     */
    public static final class HelperModulusCheckRequest extends PostRequest<Helper> {
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String iban;

        /**
         * Bank account number - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
         */
        public HelperModulusCheckRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
         */
        public HelperModulusCheckRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details) for more
         * information.
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
         * International Bank Account Number. Alternatively you can provide [local
         * details](https://developer.gocardless.com/pro/2015-04-29/#ui-local-bank-details).
         */
        public HelperModulusCheckRequest withIban(String iban) {
            this.iban = iban;
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

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
