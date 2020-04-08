package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.MandateImportEntry;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with mandate import entry resources.
 *
 * Mandate Import Entries are added to a [Mandate Import](#core-endpoints-mandate-imports).
 * Each entry corresponds to one mandate to be imported into GoCardless.
 * 
 * To import a mandate you will need:
 * <ol>
 *   <li>Identifying information about the customer (name/company and address)</li>
 *   <li>Bank account details, consisting of an account holder name and
 *      either an IBAN or <a href="#appendix-local-bank-details">local bank details</a></li>
 *   <li>Amendment details (SEPA only)</li>
 * </ol>
 * 
 * We suggest you provide a `record_identifier` (which is unique within the context of a
 * single mandate import) to help you to identify mandates that have been created once the
 * import has been processed by GoCardless. You can
 * [list the mandate import entries](#mandate-import-entries-list-all-mandate-import-entries),
 * match them up in your system using the `record_identifier`, and look at the `links`
 * fields to find the mandate, customer and customer bank account that have been imported.
 * 
 * <p class="restricted-notice"><strong>Restricted</strong>: This API is currently only available for
 * approved integrators - please <a href="mailto:help@gocardless.com">get in touch</a> if you would
 * like to use this API.</p>
 */
public class MandateImportEntryService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#mandateImportEntries() }.
     */
    public MandateImportEntryService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * For an existing [mandate import](#core-endpoints-mandate-imports), this endpoint can
     * be used to add individual mandates to be imported into GoCardless.
     * 
     * You can add no more than 30,000 rows to a single mandate import.
     * If you attempt to go over this limit, the API will return a `record_limit_exceeded` error.
     */
    public MandateImportEntryCreateRequest create() {
        return new MandateImportEntryCreateRequest(httpClient);
    }

    /**
     * For an existing mandate import, this endpoint lists all of the entries attached.
     * 
     * After a mandate import has been submitted, you can use this endpoint to associate records
     * in your system (using the `record_identifier` that you provided when creating the
     * mandate import).
     * 
     */
    public MandateImportEntryListRequest<ListResponse<MandateImportEntry>> list() {
        return new MandateImportEntryListRequest<>(httpClient,
                ListRequest.<MandateImportEntry>pagingExecutor());
    }

    public MandateImportEntryListRequest<Iterable<MandateImportEntry>> all() {
        return new MandateImportEntryListRequest<>(httpClient,
                ListRequest.<MandateImportEntry>iteratingExecutor());
    }

    /**
     * Request class for {@link MandateImportEntryService#create }.
     *
     * For an existing [mandate import](#core-endpoints-mandate-imports), this endpoint can
     * be used to add individual mandates to be imported into GoCardless.
     * 
     * You can add no more than 30,000 rows to a single mandate import.
     * If you attempt to go over this limit, the API will return a `record_limit_exceeded` error.
     */
    public static final class MandateImportEntryCreateRequest extends
            PostRequest<MandateImportEntry> {
        private Amendment amendment;
        private BankAccount bankAccount;
        private Customer customer;
        private Links links;
        private String recordIdentifier;

        public MandateImportEntryCreateRequest withAmendment(Amendment amendment) {
            this.amendment = amendment;
            return this;
        }

        /**
         * The creditor identifier of the direct debit originator. Required if mandate
         * import scheme is `sepa`.
         * 
         */
        public MandateImportEntryCreateRequest withAmendmentOriginalCreditorId(
                String originalCreditorId) {
            if (amendment == null) {
                amendment = new Amendment();
            }
            amendment.withOriginalCreditorId(originalCreditorId);
            return this;
        }

        /**
         * Data about the original mandate to be moved or modified.
         * 
         */
        public MandateImportEntryCreateRequest withAmendmentOriginalCreditorName(
                String originalCreditorName) {
            if (amendment == null) {
                amendment = new Amendment();
            }
            amendment.withOriginalCreditorName(originalCreditorName);
            return this;
        }

        /**
         * The unique SEPA reference for the mandate being amended. Required if mandate
         * import scheme is `sepa`.
         * 
         */
        public MandateImportEntryCreateRequest withAmendmentOriginalMandateReference(
                String originalMandateReference) {
            if (amendment == null) {
                amendment = new Amendment();
            }
            amendment.withOriginalMandateReference(originalMandateReference);
            return this;
        }

        public MandateImportEntryCreateRequest withBankAccount(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        /**
         * Name of the account holder, as known by the bank. Usually this is the same as the name stored with
         * the linked [creditor](#core-endpoints-creditors). This field will be transliterated, upcased and
         * truncated to 18 characters.
         */
        public MandateImportEntryCreateRequest withBankAccountAccountHolderName(
                String accountHolderName) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountHolderName(accountHolderName);
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public MandateImportEntryCreateRequest withBankAccountAccountNumber(String accountNumber) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumber(accountNumber);
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information. Alternatively
         * you can provide an `iban`.
         */
        public MandateImportEntryCreateRequest withBankAccountBankCode(String bankCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withBankCode(bankCode);
            return this;
        }

        /**
         * Branch code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public MandateImportEntryCreateRequest withBankAccountBranchCode(String branchCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withBranchCode(branchCode);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements). Defaults
         * to the country code of the `iban` if supplied, otherwise is required.
         */
        public MandateImportEntryCreateRequest withBankAccountCountryCode(String countryCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withCountryCode(countryCode);
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
         * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
         */
        public MandateImportEntryCreateRequest withBankAccountIban(String iban) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withIban(iban);
            return this;
        }

        public MandateImportEntryCreateRequest withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        /**
         * The first line of the customer's address. Required if mandate import scheme is `bacs`.
         * 
         */
        public MandateImportEntryCreateRequest withCustomerAddressLine1(String addressLine1) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public MandateImportEntryCreateRequest withCustomerAddressLine2(String addressLine2) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public MandateImportEntryCreateRequest withCustomerAddressLine3(String addressLine3) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public MandateImportEntryCreateRequest withCustomerCity(String city) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCity(city);
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided. For
         * Canadian customers, the use of a `company_name` value will mean that any mandate created from this
         * customer will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
         * be a "Personal PAD").
         */
        public MandateImportEntryCreateRequest withCustomerCompanyName(String companyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCompanyName(companyName);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public MandateImportEntryCreateRequest withCustomerCountryCode(String countryCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
         * if the customer's bank account is denominated in Danish krone (DKK).
         */
        public MandateImportEntryCreateRequest withCustomerDanishIdentityNumber(
                String danishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withDanishIdentityNumber(danishIdentityNumber);
            return this;
        }

        /**
         * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
         * to this customer.
         */
        public MandateImportEntryCreateRequest withCustomerEmail(String email) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public MandateImportEntryCreateRequest withCustomerFamilyName(String familyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public MandateImportEntryCreateRequest withCustomerGivenName(String givenName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withGivenName(givenName);
            return this;
        }

        /**
         * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
         * notification emails sent by GoCardless if your organisation does not send its own (see [compliance
         * requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
         * "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
         * chosen based on the `country_code` (if supplied) or default to "en".
         */
        public MandateImportEntryCreateRequest withCustomerLanguage(String language) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withLanguage(language);
            return this;
        }

        /**
         * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including country code.
         */
        public MandateImportEntryCreateRequest withCustomerPhoneNumber(String phoneNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withPhoneNumber(phoneNumber);
            return this;
        }

        /**
         * The customer's postal code. Required if mandate import scheme is `bacs`.
         * 
         */
        public MandateImportEntryCreateRequest withCustomerPostalCode(String postalCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter state code ([ISO
         * 3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) e.g CA) is required.
         */
        public MandateImportEntryCreateRequest withCustomerRegion(String region) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withRegion(region);
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
         * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
         */
        public MandateImportEntryCreateRequest withCustomerSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        public MandateImportEntryCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * Unique identifier, beginning with "IM".
         */
        public MandateImportEntryCreateRequest withLinksMandateImport(String mandateImport) {
            if (links == null) {
                links = new Links();
            }
            links.withMandateImport(mandateImport);
            return this;
        }

        /**
         * A unique identifier for this entry, which you can use (once the import has been
         * processed by GoCardless) to identify the records that have been created. Limited
         * to 255 characters.
         * 
         */
        public MandateImportEntryCreateRequest withRecordIdentifier(String recordIdentifier) {
            this.recordIdentifier = recordIdentifier;
            return this;
        }

        private MandateImportEntryCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public MandateImportEntryCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_import_entries";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_import_entries";
        }

        @Override
        protected Class<MandateImportEntry> getResponseClass() {
            return MandateImportEntry.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Amendment {
            private String originalCreditorId;
            private String originalCreditorName;
            private String originalMandateReference;

            /**
             * The creditor identifier of the direct debit originator. Required if mandate
             * import scheme is `sepa`.
             * 
             */
            public Amendment withOriginalCreditorId(String originalCreditorId) {
                this.originalCreditorId = originalCreditorId;
                return this;
            }

            /**
             * Data about the original mandate to be moved or modified.
             * 
             */
            public Amendment withOriginalCreditorName(String originalCreditorName) {
                this.originalCreditorName = originalCreditorName;
                return this;
            }

            /**
             * The unique SEPA reference for the mandate being amended. Required if mandate
             * import scheme is `sepa`.
             * 
             */
            public Amendment withOriginalMandateReference(String originalMandateReference) {
                this.originalMandateReference = originalMandateReference;
                return this;
            }
        }

        public static class BankAccount {
            private String accountHolderName;
            private String accountNumber;
            private String bankCode;
            private String branchCode;
            private String countryCode;
            private String iban;

            /**
             * Name of the account holder, as known by the bank. Usually this is the same as the name stored with
             * the linked [creditor](#core-endpoints-creditors). This field will be transliterated, upcased and
             * truncated to 18 characters.
             */
            public BankAccount withAccountHolderName(String accountHolderName) {
                this.accountHolderName = accountHolderName;
                return this;
            }

            /**
             * Bank account number - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
             */
            public BankAccount withAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
                return this;
            }

            /**
             * Bank code - see [local details](#appendix-local-bank-details) for more information. Alternatively
             * you can provide an `iban`.
             */
            public BankAccount withBankCode(String bankCode) {
                this.bankCode = bankCode;
                return this;
            }

            /**
             * Branch code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
             */
            public BankAccount withBranchCode(String branchCode) {
                this.branchCode = branchCode;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements). Defaults
             * to the country code of the `iban` if supplied, otherwise is required.
             */
            public BankAccount withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * International Bank Account Number. Alternatively you can provide [local
             * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
             * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
             */
            public BankAccount withIban(String iban) {
                this.iban = iban;
                return this;
            }
        }

        public static class Customer {
            private String addressLine1;
            private String addressLine2;
            private String addressLine3;
            private String city;
            private String companyName;
            private String countryCode;
            private String danishIdentityNumber;
            private String email;
            private String familyName;
            private String givenName;
            private String language;
            private String phoneNumber;
            private String postalCode;
            private String region;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address. Required if mandate import scheme is `bacs`.
             * 
             */
            public Customer withAddressLine1(String addressLine1) {
                this.addressLine1 = addressLine1;
                return this;
            }

            /**
             * The second line of the customer's address.
             */
            public Customer withAddressLine2(String addressLine2) {
                this.addressLine2 = addressLine2;
                return this;
            }

            /**
             * The third line of the customer's address.
             */
            public Customer withAddressLine3(String addressLine3) {
                this.addressLine3 = addressLine3;
                return this;
            }

            /**
             * The city of the customer's address.
             */
            public Customer withCity(String city) {
                this.city = city;
                return this;
            }

            /**
             * Customer's company name. Required unless a `given_name` and `family_name` are provided. For
             * Canadian customers, the use of a `company_name` value will mean that any mandate created from this
             * customer will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
             * be a "Personal PAD").
             */
            public Customer withCompanyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             */
            public Customer withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
             * if the customer's bank account is denominated in Danish krone (DKK).
             */
            public Customer withDanishIdentityNumber(String danishIdentityNumber) {
                this.danishIdentityNumber = danishIdentityNumber;
                return this;
            }

            /**
             * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
             * to this customer.
             */
            public Customer withEmail(String email) {
                this.email = email;
                return this;
            }

            /**
             * Customer's surname. Required unless a `company_name` is provided.
             */
            public Customer withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * Customer's first name. Required unless a `company_name` is provided.
             */
            public Customer withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
             * notification emails sent by GoCardless if your organisation does not send its own (see [compliance
             * requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
             * "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
             * chosen based on the `country_code` (if supplied) or default to "en".
             */
            public Customer withLanguage(String language) {
                this.language = language;
                return this;
            }

            /**
             * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including country code.
             */
            public Customer withPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
                return this;
            }

            /**
             * The customer's postal code. Required if mandate import scheme is `bacs`.
             * 
             */
            public Customer withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The customer's address region, county or department. For US customers a 2 letter state code ([ISO
             * 3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) e.g CA) is required.
             */
            public Customer withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
             * organisationsnummer) of the customer. Must be supplied if the customer's bank account is
             * denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
             */
            public Customer withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }
        }

        public static class Links {
            private String mandateImport;

            /**
             * Unique identifier, beginning with "IM".
             */
            public Links withMandateImport(String mandateImport) {
                this.mandateImport = mandateImport;
                return this;
            }
        }
    }

    /**
     * Request class for {@link MandateImportEntryService#list }.
     *
     * For an existing mandate import, this endpoint lists all of the entries attached.
     * 
     * After a mandate import has been submitted, you can use this endpoint to associate records
     * in your system (using the `record_identifier` that you provided when creating the
     * mandate import).
     * 
     */
    public static final class MandateImportEntryListRequest<S> extends
            ListRequest<S, MandateImportEntry> {
        private String mandateImport;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public MandateImportEntryListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public MandateImportEntryListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public MandateImportEntryListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "IM".
         */
        public MandateImportEntryListRequest<S> withMandateImport(String mandateImport) {
            this.mandateImport = mandateImport;
            return this;
        }

        private MandateImportEntryListRequest(HttpClient httpClient,
                ListRequestExecutor<S, MandateImportEntry> executor) {
            super(httpClient, executor);
        }

        public MandateImportEntryListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (mandateImport != null) {
                params.put("mandate_import", mandateImport);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_import_entries";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_import_entries";
        }

        @Override
        protected TypeToken<List<MandateImportEntry>> getTypeToken() {
            return new TypeToken<List<MandateImportEntry>>() {};
        }
    }
}
