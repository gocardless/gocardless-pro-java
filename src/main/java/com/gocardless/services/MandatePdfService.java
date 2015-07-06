package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.MandatePdf;

/**
 * Service class for working with mandate pdf resources.
 *
 * Mandate PDFs allow you to easily display [scheme-rules compliant](#ui-compliance-requirements)
 * Direct Debit mandates to your customers.
 */
public class MandatePdfService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#mandatePdfs() }.
     */
    public MandatePdfService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Generates a PDF mandate and returns its temporary URL.
     * 
     * Customer and bank account details can
     * be left blank (for a blank mandate), provided manually, or inferred from the ID of an existing
     * [mandate](#core-endpoints-mandates).
     * 
     * To generate a PDF mandate in a foreign language, set
     * your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Supported languages are Dutch, English, French, German, Italian, Portuguese and Spanish.
     */
    public MandatePdfCreateRequest create() {
        return new MandatePdfCreateRequest(httpClient);
    }

    /**
     * Request class for {@link MandatePdfService#create }.
     *
     * Generates a PDF mandate and returns its temporary URL.
     * 
     * Customer and bank account details can
     * be left blank (for a blank mandate), provided manually, or inferred from the ID of an existing
     * [mandate](#core-endpoints-mandates).
     * 
     * To generate a PDF mandate in a foreign language, set
     * your `Accept-Language` header to the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Supported languages are Dutch, English, French, German, Italian, Portuguese and Spanish.
     */
    public static final class MandatePdfCreateRequest extends PostRequest<MandatePdf> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String bic;
        private String branchCode;
        private String countryCode;
        private String iban;
        private Links links;
        private String mandateReference;
        private String scheme;
        private String signatureDate;

        /**
         * Name of the account holder, as known by the bank. Usually this matches the name of the
         * [customer](#api-endpoints-customers). This field cannot exceed 18 characters.
         */
        public MandatePdfCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see [local details](#ui-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public MandatePdfCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local details](#ui-local-bank-details) for more information. Alternatively you
         * can provide an `iban`.
         */
        public MandatePdfCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * SWIFT BIC. Will be derived automatically if a valid `iban` or [local
         * details](#ui-local-bank-details) are provided.
         */
        public MandatePdfCreateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        /**
         * Branch code - see [local details](#ui-local-bank-details) for more information. Alternatively you
         * can provide an `iban`.
         */
        public MandatePdfCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public MandatePdfCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#ui-local-bank-details).
         */
        public MandatePdfCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public MandatePdfCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of an existing [mandate](#core-endpoints-mandates) to build the PDF from. The customer's bank
         * details will be censored in the generated PDF. No other parameters may be provided alongside this.
         */
        public MandatePdfCreateRequest withLinksMandate(String mandate) {
            if (links == null) {
                links = new Links();
            }
            links.withMandate(mandate);
            return this;
        }

        /**
         * Unique 6 to 18 character reference. This may be left blank at the point of signing.
         */
        public MandatePdfCreateRequest withMandateReference(String mandateReference) {
            this.mandateReference = mandateReference;
            return this;
        }

        /**
         * Direct Debit scheme. Can be supplied or automatically detected from the bank account details
         * provided. If you do not provide a scheme, you must provide either a mandate, an `iban`, or [local
         * details](#ui-local-bank-details) including a `country_code`.
         */
        public MandatePdfCreateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * If provided, a form will be generated with this date and no signature field.
         */
        public MandatePdfCreateRequest withSignatureDate(String signatureDate) {
            this.signatureDate = signatureDate;
            return this;
        }

        private MandatePdfCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/mandate_pdfs";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_pdfs";
        }

        @Override
        protected Class<MandatePdf> getResponseClass() {
            return MandatePdf.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String mandate;

            /**
             * ID of an existing [mandate](#core-endpoints-mandates) to build the PDF from. The customer's bank
             * details will be censored in the generated PDF. No other parameters may be provided alongside this.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }
}
