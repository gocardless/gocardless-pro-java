package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.MandatePdf;

/**
 * Service class for working with mandate pdf resources.
 *
 * Construct a mandate PDF for a given set of bank details or an already-existing mandate.
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
     * Generates a temporary URL for viewing a PDF mandate.
     * 
     * Bank account details may be supplied
     * using an IBAN (international bank account number), or [local details](#ui-local-bank-details), or
     * alternatively you can provide the ID of an existing [mandate](#core-endpoints-mandates) to create
     * a PDF of.
     * 
     * To generate a mandate in a foreign language, set your `Accept-Language` header to
     * the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
     */
    public MandatePdfCreateRequest create() {
        return new MandatePdfCreateRequest(httpClient);
    }

    /**
     * Request class for {@link MandatePdfService#create }.
     *
     * Generates a temporary URL for viewing a PDF mandate.
     * 
     * Bank account details may be supplied
     * using an IBAN (international bank account number), or [local details](#ui-local-bank-details), or
     * alternatively you can provide the ID of an existing [mandate](#core-endpoints-mandates) to create
     * a PDF of.
     * 
     * To generate a mandate in a foreign language, set your `Accept-Language` header to
     * the relevant [ISO
     * 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) language code.
     * Currently Dutch, English, French, German, Italian, Portuguese and Spanish are supported.
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
         * Alternatively you can provide an `iban` or link to an existing mandate.
         */
        public MandatePdfCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local details](#ui-local-bank-details) for more information. Alternatively you
         * can provide an `iban` or link to an existing mandate.
         */
        public MandatePdfCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * SWIFT BIC. Optional (will be derived from the `iban` or local details if not provided).
         */
        public MandatePdfCreateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        /**
         * Branch code - see [local details](#ui-local-bank-details) for more information. Alternatively you
         * can provide an `iban` or link to an existing mandate.
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
         * details](#ui-local-bank-details) or link to an existing mandate.
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
         * ID of an existing [mandate](#core-endpoints-mandates) to build the PDF from.
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
         * Direct Debit scheme. Can be supplied or automatically detected from the bank account details or
         * mandate provided.
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
             * ID of an existing [mandate](#core-endpoints-mandates) to build the PDF from.
             */
            public Links withMandate(String mandate) {
                this.mandate = mandate;
                return this;
            }
        }
    }
}
