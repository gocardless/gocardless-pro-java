package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.MandatePdf;

/**
 * Service class for working with mandate pdf resources.
 *
 * Mandate PDFs allow you to easily display [scheme-rules
 * compliant](#appendix-compliance-requirements) Direct Debit mandates to your customers.
 */
public class MandatePdfService {
    private final HttpClient httpClient;

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
     * Customer and bank account details can be left blank (for a blank mandate), provided manually, or
     * inferred from the ID of an existing [mandate](#core-endpoints-mandates).
     * 
     * By default, we'll generate PDF mandates in English.
     * 
     * To generate a PDF mandate in another language, set the `Accept-Language` header when creating the
     * PDF mandate to the relevant [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     * language code supported for the scheme.
     * 
     * | Scheme           | Supported languages                                                          
     *                                                                |
     * | :--------------- |
     * :-------------------------------------------------------------------------------------------------------------------------------------------
     * |
     * | ACH              | English (`en`)                                                               
     *                                                                |
     * | Autogiro         | English (`en`), Swedish (`sv`)                                               
     *                                                                |
     * | Bacs             | English (`en`)                                                               
     *                                                                |
     * | BECS             | English (`en`)                                                               
     *                                                                |
     * | BECS NZ          | English (`en`)                                                               
     *                                                                |
     * | Betalingsservice | Danish (`da`), English (`en`)                                                
     *                                                                |
     * | PAD              | English (`en`)                                                               
     *                                                                |
     * | SEPA Core        | Danish (`da`), Dutch (`nl`), English (`en`), French (`fr`), German (`de`),
     * Italian (`it`), Portuguese (`pt`), Spanish (`es`), Swedish (`sv`) |
     */
    public MandatePdfCreateRequest create() {
        return new MandatePdfCreateRequest(httpClient);
    }

    /**
     * Request class for {@link MandatePdfService#create }.
     *
     * Generates a PDF mandate and returns its temporary URL.
     * 
     * Customer and bank account details can be left blank (for a blank mandate), provided manually, or
     * inferred from the ID of an existing [mandate](#core-endpoints-mandates).
     * 
     * By default, we'll generate PDF mandates in English.
     * 
     * To generate a PDF mandate in another language, set the `Accept-Language` header when creating the
     * PDF mandate to the relevant [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     * language code supported for the scheme.
     * 
     * | Scheme           | Supported languages                                                          
     *                                                                |
     * | :--------------- |
     * :-------------------------------------------------------------------------------------------------------------------------------------------
     * |
     * | ACH              | English (`en`)                                                               
     *                                                                |
     * | Autogiro         | English (`en`), Swedish (`sv`)                                               
     *                                                                |
     * | Bacs             | English (`en`)                                                               
     *                                                                |
     * | BECS             | English (`en`)                                                               
     *                                                                |
     * | BECS NZ          | English (`en`)                                                               
     *                                                                |
     * | Betalingsservice | Danish (`da`), English (`en`)                                                
     *                                                                |
     * | PAD              | English (`en`)                                                               
     *                                                                |
     * | SEPA Core        | Danish (`da`), Dutch (`nl`), English (`en`), French (`fr`), German (`de`),
     * Italian (`it`), Portuguese (`pt`), Spanish (`es`), Swedish (`sv`) |
     */
    public static final class MandatePdfCreateRequest extends PostRequest<MandatePdf> {
        private String accountHolderName;
        private String accountNumber;
        private String accountType;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String bankCode;
        private String bic;
        private String branchCode;
        private String city;
        private String countryCode;
        private String danishIdentityNumber;
        private String iban;
        private Links links;
        private String mandateReference;
        private String payerIpAddress;
        private String phoneNumber;
        private String postalCode;
        private String region;
        private String scheme;
        private String signatureDate;
        private Integer subscriptionAmount;
        private String subscriptionFrequency;
        private String swedishIdentityNumber;

        /**
         * Name of the account holder, as known by the bank. Usually this matches the name of the
         * [customer](#core-endpoints-customers). This field cannot exceed 18 characters.
         */
        public MandatePdfCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public MandatePdfCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for bank
         * accounts in other currencies. See [local details](#local-bank-details-united-states) for more
         * information.
         */
        public MandatePdfCreateRequest withAccountType(String accountType) {
            this.accountType = accountType;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public MandatePdfCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public MandatePdfCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public MandatePdfCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information. Alternatively
         * you can provide an `iban`.
         */
        public MandatePdfCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * SWIFT BIC. Will be derived automatically if a valid `iban` or [local
         * details](#appendix-local-bank-details) are provided.
         */
        public MandatePdfCreateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        /**
         * Branch code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public MandatePdfCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public MandatePdfCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Required if providing local details.
         */
        public MandatePdfCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Should only be
         * supplied for Betalingsservice mandates.
         */
        public MandatePdfCreateRequest withDanishIdentityNumber(String danishIdentityNumber) {
            this.danishIdentityNumber = danishIdentityNumber;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs cannot be provided for Autogiro mandates.
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
         * For American customers only. IP address of the computer used by the customer to set up the
         * mandate. This is required in order to create compliant Mandate PDFs according to the ACH scheme
         * rules.
         */
        public MandatePdfCreateRequest withPayerIpAddress(String payerIpAddress) {
            this.payerIpAddress = payerIpAddress;
            return this;
        }

        /**
         * The customer phone number. Should only be provided for BECS NZ mandates.
         */
        public MandatePdfCreateRequest withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * The customer's postal code.
         */
        public MandatePdfCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter state code ([ISO
         * 3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) e.g CA) is required.
         */
        public MandatePdfCreateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        /**
         * Direct Debit scheme. Can be supplied or automatically detected from the bank account details
         * provided. If you do not provide a scheme, you must provide either a mandate, an `iban`, or [local
         * details](#appendix-local-bank-details) including a `country_code`.
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

        /**
         * For American customers only. Subscription amount being authorised by the mandate. In the lowest
         * denomination for the currency (cents in USD). Is required if `subscription_frequency` has been
         * provided.
         */
        public MandatePdfCreateRequest withSubscriptionAmount(Integer subscriptionAmount) {
            this.subscriptionAmount = subscriptionAmount;
            return this;
        }

        /**
         * For American customers only. Frequency of the subscription being authorised by the mandate. One of
         * `weekly`, `monthly` or `yearly`. Is required if `subscription_amount` has been provided.
         */
        public MandatePdfCreateRequest withSubscriptionFrequency(String subscriptionFrequency) {
            this.subscriptionFrequency = subscriptionFrequency;
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Should only be supplied for Autogiro mandates.
         */
        public MandatePdfCreateRequest withSwedishIdentityNumber(String swedishIdentityNumber) {
            this.swedishIdentityNumber = swedishIdentityNumber;
            return this;
        }

        private MandatePdfCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public MandatePdfCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_pdfs";
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
