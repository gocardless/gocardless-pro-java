package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.PayerAuthorisation;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Service class for working with payer authorisation resources.
 *
 * <p class="restricted-notice">
 * Don't use Payer Authorisations for new integrations. It is deprecated in favour of
 * <a href="https://developer.gocardless.com/getting-started/billing-requests/overview/"> Billing
 * Requests</a>. Use Billing Requests to build any future integrations.
 * </p>
 * 
 * Payer Authorisation resource acts as a wrapper for creating customer, bank account and mandate
 * details in a single request. PayerAuthorisation API enables the integrators to build their own
 * custom payment pages.
 * 
 * The process to use the Payer Authorisation API is as follows:
 * 
 * 1. Create a Payer Authorisation, either empty or with already available information 2. Update the
 * authorisation with additional information or fix any mistakes 3. Submit the authorisation, after
 * the payer has reviewed their information 4. [coming soon] Redirect the payer to the verification
 * mechanisms from the response of the Submit request (this will be introduced as a non-breaking
 * change) 5. Confirm the authorisation to indicate that the resources can be created
 * 
 * After the Payer Authorisation is confirmed, resources will eventually be created as it's an
 * asynchronous process.
 * 
 * To retrieve the status and ID of the linked resources you can do one of the following:
 * <ol>
 * <li>Listen to <code>  payer_authorisation_completed </code> <a href="#appendix-webhooks">
 * webhook</a> (recommended)</li>
 * <li>Poll the GET <a href="#payer-authorisations-get-a-single-payer-authorisation">
 * endpoint</a></li>
 * <li>Poll the GET events API
 * <code>https://api.gocardless.com/events?payer_authorisation={id}&action=completed</code></li>
 * </ol>
 * 
 * <p class="notice">
 * Note that the `create` and `update` endpoints behave differently than other existing `create` and
 * `update` endpoints. The Payer Authorisation is still saved if incomplete data is provided. We
 * return the list of incomplete data in the `incomplete_fields` along with the resources in the
 * body of the response. The bank account details(account_number, bank_code & branch_code) must be
 * sent together rather than splitting across different request for both `create` and `update`
 * endpoints. <br>
 * <br>
 * The API is designed to be flexible and allows you to collect information in multiple steps
 * without storing any sensitive data in the browser or in your servers.
 * </p>
 */
public class PayerAuthorisationService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#payerAuthorisations() }.
     */
    public PayerAuthorisationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Retrieves the details of a single existing Payer Authorisation. It can be used for polling
     * the status of a Payer Authorisation.
     */
    public PayerAuthorisationGetRequest get(String identity) {
        return new PayerAuthorisationGetRequest(httpClient, identity);
    }

    /**
     * Creates a Payer Authorisation. The resource is saved to the database even if incomplete. An
     * empty array of incomplete_fields means that the resource is valid. The ID of the resource is
     * used for the other actions. This endpoint has been designed this way so you do not need to
     * save any payer data on your servers or the browser while still being able to implement a
     * progressive solution, such as a multi-step form.
     */
    public PayerAuthorisationCreateRequest create() {
        return new PayerAuthorisationCreateRequest(httpClient);
    }

    /**
     * Updates a Payer Authorisation. Updates the Payer Authorisation with the request data. Can be
     * invoked as many times as needed. Only fields present in the request will be modified. An
     * empty array of incomplete_fields means that the resource is valid. This endpoint has been
     * designed this way so you do not need to save any payer data on your servers or the browser
     * while still being able to implement a progressive solution, such a multi-step form.
     * <p class="notice">
     * Note that in order to update the `metadata` attribute values it must be sent completely as it
     * overrides the previously existing values.
     * </p>
     */
    public PayerAuthorisationUpdateRequest update(String identity) {
        return new PayerAuthorisationUpdateRequest(httpClient, identity);
    }

    /**
     * Submits all the data previously pushed to this PayerAuthorisation for verification. This
     * time, a 200 HTTP status is returned if the resource is valid and a 422 error response in case
     * of validation errors. After it is successfully submitted, the Payer Authorisation can no
     * longer be edited.
     */
    public PayerAuthorisationSubmitRequest submit(String identity) {
        return new PayerAuthorisationSubmitRequest(httpClient, identity);
    }

    /**
     * Confirms the Payer Authorisation, indicating that the resources are ready to be created. A
     * Payer Authorisation cannot be confirmed if it hasn't been submitted yet.
     * 
     * <p class="notice">
     * The main use of the confirm endpoint is to enable integrators to acknowledge the end of the
     * setup process. They might want to make the payers go through some other steps after they go
     * through our flow or make them go through the necessary verification mechanism (upcoming
     * feature).
     * </p>
     */
    public PayerAuthorisationConfirmRequest confirm(String identity) {
        return new PayerAuthorisationConfirmRequest(httpClient, identity);
    }

    /**
     * Request class for {@link PayerAuthorisationService#get }.
     *
     * Retrieves the details of a single existing Payer Authorisation. It can be used for polling
     * the status of a Payer Authorisation.
     */
    public static final class PayerAuthorisationGetRequest extends GetRequest<PayerAuthorisation> {
        @PathParam
        private final String identity;

        private PayerAuthorisationGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PayerAuthorisationGetRequest withHeader(String headerName, String headerValue) {
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
            return "payer_authorisations/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payer_authorisations";
        }

        @Override
        protected Class<PayerAuthorisation> getResponseClass() {
            return PayerAuthorisation.class;
        }
    }

    /**
     * Request class for {@link PayerAuthorisationService#create }.
     *
     * Creates a Payer Authorisation. The resource is saved to the database even if incomplete. An
     * empty array of incomplete_fields means that the resource is valid. The ID of the resource is
     * used for the other actions. This endpoint has been designed this way so you do not need to
     * save any payer data on your servers or the browser while still being able to implement a
     * progressive solution, such as a multi-step form.
     */
    public static final class PayerAuthorisationCreateRequest
            extends IdempotentPostRequest<PayerAuthorisation> {
        private BankAccount bankAccount;
        private Customer customer;
        private Mandate mandate;

        /**
         * All details required for the creation of a [Customer Bank
         * Account](#core-endpoints-customer-bank-accounts).
         */
        public PayerAuthorisationCreateRequest withBankAccount(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        /**
         * Name of the account holder, as known by the bank. This field will be transliterated,
         * upcased and truncated to 18 characters. This field is required unless the request
         * includes a [customer bank account token](#javascript-flow-customer-bank-account-tokens).
         */
        public PayerAuthorisationCreateRequest withBankAccountAccountHolderName(
                String accountHolderName) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountHolderName(accountHolderName);
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public PayerAuthorisationCreateRequest withBankAccountAccountNumber(String accountNumber) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumber(accountNumber);
            return this;
        }

        /**
         * The last few digits of the account number. Currently 4 digits for NZD bank accounts and 2
         * digits for other currencies.
         */
        public PayerAuthorisationCreateRequest withBankAccountAccountNumberEnding(
                String accountNumberEnding) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumberEnding(accountNumberEnding);
            return this;
        }

        /**
         * Account number suffix (only for bank accounts denominated in NZD) - see [local
         * details](#local-bank-details-new-zealand) for more information.
         */
        public PayerAuthorisationCreateRequest withBankAccountAccountNumberSuffix(
                String accountNumberSuffix) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumberSuffix(accountNumberSuffix);
            return this;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for
         * bank accounts in other currencies. See [local details](#local-bank-details-united-states)
         * for more information.
         */
        public PayerAuthorisationCreateRequest withBankAccountAccountType(
                BankAccount.AccountType accountType) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountType(accountType);
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public PayerAuthorisationCreateRequest withBankAccountBankCode(String bankCode) {
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
        public PayerAuthorisationCreateRequest withBankAccountBranchCode(String branchCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withBranchCode(branchCode);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
         * Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public PayerAuthorisationCreateRequest withBankAccountCountryCode(String countryCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withCountryCode(countryCode);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public PayerAuthorisationCreateRequest withBankAccountCurrency(String currency) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withCurrency(currency);
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
         * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
         */
        public PayerAuthorisationCreateRequest withBankAccountIban(String iban) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withIban(iban);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationCreateRequest withBankAccountMetadata(
                Map<String, String> metadata) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withMetadata(metadata);
            return this;
        }

        /**
         * All details required for the creation of a [Customer](#core-endpoints-customers).
         */
        public PayerAuthorisationCreateRequest withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public PayerAuthorisationCreateRequest withCustomerAddressLine1(String addressLine1) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public PayerAuthorisationCreateRequest withCustomerAddressLine2(String addressLine2) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public PayerAuthorisationCreateRequest withCustomerAddressLine3(String addressLine3) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public PayerAuthorisationCreateRequest withCustomerCity(String city) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCity(city);
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided.
         * For Canadian customers, the use of a `company_name` value will mean that any mandate
         * created from this customer will be considered to be a "Business PAD" (otherwise, any
         * mandate will be considered to be a "Personal PAD").
         */
        public PayerAuthorisationCreateRequest withCustomerCompanyName(String companyName) {
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
        public PayerAuthorisationCreateRequest withCustomerCountryCode(String countryCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be
         * supplied if the customer's bank account is denominated in Danish krone (DKK).
         */
        public PayerAuthorisationCreateRequest withCustomerDanishIdentityNumber(
                String danishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withDanishIdentityNumber(danishIdentityNumber);
            return this;
        }

        /**
         * Customer's email address. Required in most cases, as this allows GoCardless to send
         * notifications to this customer.
         */
        public PayerAuthorisationCreateRequest withCustomerEmail(String email) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public PayerAuthorisationCreateRequest withCustomerFamilyName(String familyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public PayerAuthorisationCreateRequest withCustomerGivenName(String givenName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withGivenName(givenName);
            return this;
        }

        /**
         * An [IETF Language Tag](https://tools.ietf.org/html/rfc5646), used for both language and
         * regional variations of our product.
         * 
         */
        public PayerAuthorisationCreateRequest withCustomerLocale(String locale) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withLocale(locale);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationCreateRequest withCustomerMetadata(Map<String, String> metadata) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withMetadata(metadata);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public PayerAuthorisationCreateRequest withCustomerPostalCode(String postalCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter
         * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g.
         * `CA` for California).
         */
        public PayerAuthorisationCreateRequest withCustomerRegion(String region) {
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
        public PayerAuthorisationCreateRequest withCustomerSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        /**
         * All details required for the creation of a [Mandate](#core-endpoints-mandates).
         */
        public PayerAuthorisationCreateRequest withMandate(Mandate mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationCreateRequest withMandateMetadata(Map<String, String> metadata) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withMetadata(metadata);
            return this;
        }

        /**
         * For ACH customers only. Required for ACH customers. A string containing the IP address of
         * the payer to whom the mandate belongs (i.e. as a result of their completion of a mandate
         * setup flow in their browser).
         * 
         * Not required for creating offline mandates where `authorisation_source` is set to
         * telephone or paper.
         * 
         */
        public PayerAuthorisationCreateRequest withMandatePayerIpAddress(String payerIpAddress) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withPayerIpAddress(payerIpAddress);
            return this;
        }

        /**
         * Unique reference. Different schemes have different length and [character
         * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
         * satisfying the different scheme requirements if this field is left blank.
         */
        public PayerAuthorisationCreateRequest withMandateReference(String reference) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withReference(reference);
            return this;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         */
        public PayerAuthorisationCreateRequest withMandateScheme(Mandate.Scheme scheme) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withScheme(scheme);
            return this;
        }

        public PayerAuthorisationCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<PayerAuthorisation> handleConflict(HttpClient httpClient, String id) {
            PayerAuthorisationGetRequest request = new PayerAuthorisationGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private PayerAuthorisationCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public PayerAuthorisationCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "payer_authorisations";
        }

        @Override
        protected String getEnvelope() {
            return "payer_authorisations";
        }

        @Override
        protected Class<PayerAuthorisation> getResponseClass() {
            return PayerAuthorisation.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class BankAccount {
            private String accountHolderName;
            private String accountNumber;
            private String accountNumberEnding;
            private String accountNumberSuffix;
            private AccountType accountType;
            private String bankCode;
            private String branchCode;
            private String countryCode;
            private String currency;
            private String iban;
            private Map<String, String> metadata;

            /**
             * Name of the account holder, as known by the bank. This field will be transliterated,
             * upcased and truncated to 18 characters. This field is required unless the request
             * includes a [customer bank account
             * token](#javascript-flow-customer-bank-account-tokens).
             */
            public BankAccount withAccountHolderName(String accountHolderName) {
                this.accountHolderName = accountHolderName;
                return this;
            }

            /**
             * Bank account number - see [local details](#appendix-local-bank-details) for more
             * information. Alternatively you can provide an `iban`.
             */
            public BankAccount withAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
                return this;
            }

            /**
             * The last few digits of the account number. Currently 4 digits for NZD bank accounts
             * and 2 digits for other currencies.
             */
            public BankAccount withAccountNumberEnding(String accountNumberEnding) {
                this.accountNumberEnding = accountNumberEnding;
                return this;
            }

            /**
             * Account number suffix (only for bank accounts denominated in NZD) - see [local
             * details](#local-bank-details-new-zealand) for more information.
             */
            public BankAccount withAccountNumberSuffix(String accountNumberSuffix) {
                this.accountNumberSuffix = accountNumberSuffix;
                return this;
            }

            /**
             * Bank account type. Required for USD-denominated bank accounts. Must not be provided
             * for bank accounts in other currencies. See [local
             * details](#local-bank-details-united-states) for more information.
             */
            public BankAccount withAccountType(AccountType accountType) {
                this.accountType = accountType;
                return this;
            }

            /**
             * Bank code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
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
             * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
             * Defaults to the country code of the `iban` if supplied, otherwise is required.
             */
            public BankAccount withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
             */
            public BankAccount withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * International Bank Account Number. Alternatively you can provide [local
             * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank
             * accounts denominated in SEK - you must supply [local
             * details](#local-bank-details-sweden).
             */
            public BankAccount withIban(String iban) {
                this.iban = iban;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public BankAccount withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            public enum AccountType {
                @SerializedName("savings")
                SAVINGS, @SerializedName("checking")
                CHECKING, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
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
            private String locale;
            private Map<String, String> metadata;
            private String postalCode;
            private String region;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address.
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
             * Customer's company name. Required unless a `given_name` and `family_name` are
             * provided. For Canadian customers, the use of a `company_name` value will mean that
             * any mandate created from this customer will be considered to be a "Business PAD"
             * (otherwise, any mandate will be considered to be a "Personal PAD").
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
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
             * Must be supplied if the customer's bank account is denominated in Danish krone (DKK).
             */
            public Customer withDanishIdentityNumber(String danishIdentityNumber) {
                this.danishIdentityNumber = danishIdentityNumber;
                return this;
            }

            /**
             * Customer's email address. Required in most cases, as this allows GoCardless to send
             * notifications to this customer.
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
             * An [IETF Language Tag](https://tools.ietf.org/html/rfc5646), used for both language
             * and regional variations of our product.
             * 
             */
            public Customer withLocale(String locale) {
                this.locale = locale;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Customer withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * The customer's postal code.
             */
            public Customer withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The customer's address region, county or department. For US customers a 2 letter
             * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required
             * (e.g. `CA` for California).
             */
            public Customer withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer,
             * samordningsnummer, or organisationsnummer) of the customer. Must be supplied if the
             * customer's bank account is denominated in Swedish krona (SEK). This field cannot be
             * changed once it has been set.
             */
            public Customer withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }
        }

        public static class Mandate {
            private Map<String, String> metadata;
            private String payerIpAddress;
            private String reference;
            private Scheme scheme;

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Mandate withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * For ACH customers only. Required for ACH customers. A string containing the IP
             * address of the payer to whom the mandate belongs (i.e. as a result of their
             * completion of a mandate setup flow in their browser).
             * 
             * Not required for creating offline mandates where `authorisation_source` is set to
             * telephone or paper.
             * 
             */
            public Mandate withPayerIpAddress(String payerIpAddress) {
                this.payerIpAddress = payerIpAddress;
                return this;
            }

            /**
             * Unique reference. Different schemes have different length and [character
             * set](#appendix-character-sets) requirements. GoCardless will generate a unique
             * reference satisfying the different scheme requirements if this field is left blank.
             */
            public Mandate withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
             * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
             */
            public Mandate withScheme(Scheme scheme) {
                this.scheme = scheme;
                return this;
            }

            public enum Scheme {
                @SerializedName("ach")
                ACH, @SerializedName("autogiro")
                AUTOGIRO, @SerializedName("bacs")
                BACS, @SerializedName("becs")
                BECS, @SerializedName("becs_nz")
                BECS_NZ, @SerializedName("betalingsservice")
                BETALINGSSERVICE, @SerializedName("faster_payments")
                FASTER_PAYMENTS, @SerializedName("pad")
                PAD, @SerializedName("pay_to")
                PAY_TO, @SerializedName("sepa_core")
                SEPA_CORE, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }
    }

    /**
     * Request class for {@link PayerAuthorisationService#update }.
     *
     * Updates a Payer Authorisation. Updates the Payer Authorisation with the request data. Can be
     * invoked as many times as needed. Only fields present in the request will be modified. An
     * empty array of incomplete_fields means that the resource is valid. This endpoint has been
     * designed this way so you do not need to save any payer data on your servers or the browser
     * while still being able to implement a progressive solution, such a multi-step form.
     * <p class="notice">
     * Note that in order to update the `metadata` attribute values it must be sent completely as it
     * overrides the previously existing values.
     * </p>
     */
    public static final class PayerAuthorisationUpdateRequest
            extends PutRequest<PayerAuthorisation> {
        @PathParam
        private final String identity;
        private BankAccount bankAccount;
        private Customer customer;
        private Mandate mandate;

        /**
         * All details required for the creation of a [Customer Bank
         * Account](#core-endpoints-customer-bank-accounts).
         */
        public PayerAuthorisationUpdateRequest withBankAccount(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        /**
         * Name of the account holder, as known by the bank. This field will be transliterated,
         * upcased and truncated to 18 characters. This field is required unless the request
         * includes a [customer bank account token](#javascript-flow-customer-bank-account-tokens).
         */
        public PayerAuthorisationUpdateRequest withBankAccountAccountHolderName(
                String accountHolderName) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountHolderName(accountHolderName);
            return this;
        }

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public PayerAuthorisationUpdateRequest withBankAccountAccountNumber(String accountNumber) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumber(accountNumber);
            return this;
        }

        /**
         * The last few digits of the account number. Currently 4 digits for NZD bank accounts and 2
         * digits for other currencies.
         */
        public PayerAuthorisationUpdateRequest withBankAccountAccountNumberEnding(
                String accountNumberEnding) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumberEnding(accountNumberEnding);
            return this;
        }

        /**
         * Account number suffix (only for bank accounts denominated in NZD) - see [local
         * details](#local-bank-details-new-zealand) for more information.
         */
        public PayerAuthorisationUpdateRequest withBankAccountAccountNumberSuffix(
                String accountNumberSuffix) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountNumberSuffix(accountNumberSuffix);
            return this;
        }

        /**
         * Bank account type. Required for USD-denominated bank accounts. Must not be provided for
         * bank accounts in other currencies. See [local details](#local-bank-details-united-states)
         * for more information.
         */
        public PayerAuthorisationUpdateRequest withBankAccountAccountType(
                BankAccount.AccountType accountType) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withAccountType(accountType);
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public PayerAuthorisationUpdateRequest withBankAccountBankCode(String bankCode) {
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
        public PayerAuthorisationUpdateRequest withBankAccountBranchCode(String branchCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withBranchCode(branchCode);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
         * Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public PayerAuthorisationUpdateRequest withBankAccountCountryCode(String countryCode) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withCountryCode(countryCode);
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
         * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public PayerAuthorisationUpdateRequest withBankAccountCurrency(String currency) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withCurrency(currency);
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank accounts
         * denominated in SEK - you must supply [local details](#local-bank-details-sweden).
         */
        public PayerAuthorisationUpdateRequest withBankAccountIban(String iban) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withIban(iban);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationUpdateRequest withBankAccountMetadata(
                Map<String, String> metadata) {
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            bankAccount.withMetadata(metadata);
            return this;
        }

        /**
         * All details required for the creation of a [Customer](#core-endpoints-customers).
         */
        public PayerAuthorisationUpdateRequest withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public PayerAuthorisationUpdateRequest withCustomerAddressLine1(String addressLine1) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public PayerAuthorisationUpdateRequest withCustomerAddressLine2(String addressLine2) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public PayerAuthorisationUpdateRequest withCustomerAddressLine3(String addressLine3) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public PayerAuthorisationUpdateRequest withCustomerCity(String city) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCity(city);
            return this;
        }

        /**
         * Customer's company name. Required unless a `given_name` and `family_name` are provided.
         * For Canadian customers, the use of a `company_name` value will mean that any mandate
         * created from this customer will be considered to be a "Business PAD" (otherwise, any
         * mandate will be considered to be a "Personal PAD").
         */
        public PayerAuthorisationUpdateRequest withCustomerCompanyName(String companyName) {
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
        public PayerAuthorisationUpdateRequest withCustomerCountryCode(String countryCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be
         * supplied if the customer's bank account is denominated in Danish krone (DKK).
         */
        public PayerAuthorisationUpdateRequest withCustomerDanishIdentityNumber(
                String danishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withDanishIdentityNumber(danishIdentityNumber);
            return this;
        }

        /**
         * Customer's email address. Required in most cases, as this allows GoCardless to send
         * notifications to this customer.
         */
        public PayerAuthorisationUpdateRequest withCustomerEmail(String email) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname. Required unless a `company_name` is provided.
         */
        public PayerAuthorisationUpdateRequest withCustomerFamilyName(String familyName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name. Required unless a `company_name` is provided.
         */
        public PayerAuthorisationUpdateRequest withCustomerGivenName(String givenName) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withGivenName(givenName);
            return this;
        }

        /**
         * An [IETF Language Tag](https://tools.ietf.org/html/rfc5646), used for both language and
         * regional variations of our product.
         * 
         */
        public PayerAuthorisationUpdateRequest withCustomerLocale(String locale) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withLocale(locale);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationUpdateRequest withCustomerMetadata(Map<String, String> metadata) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withMetadata(metadata);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public PayerAuthorisationUpdateRequest withCustomerPostalCode(String postalCode) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department. For US customers a 2 letter
         * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g.
         * `CA` for California).
         */
        public PayerAuthorisationUpdateRequest withCustomerRegion(String region) {
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
        public PayerAuthorisationUpdateRequest withCustomerSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (customer == null) {
                customer = new Customer();
            }
            customer.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        /**
         * All details required for the creation of a [Mandate](#core-endpoints-mandates).
         */
        public PayerAuthorisationUpdateRequest withMandate(Mandate mandate) {
            this.mandate = mandate;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public PayerAuthorisationUpdateRequest withMandateMetadata(Map<String, String> metadata) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withMetadata(metadata);
            return this;
        }

        /**
         * For ACH customers only. Required for ACH customers. A string containing the IP address of
         * the payer to whom the mandate belongs (i.e. as a result of their completion of a mandate
         * setup flow in their browser).
         * 
         * Not required for creating offline mandates where `authorisation_source` is set to
         * telephone or paper.
         * 
         */
        public PayerAuthorisationUpdateRequest withMandatePayerIpAddress(String payerIpAddress) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withPayerIpAddress(payerIpAddress);
            return this;
        }

        /**
         * Unique reference. Different schemes have different length and [character
         * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
         * satisfying the different scheme requirements if this field is left blank.
         */
        public PayerAuthorisationUpdateRequest withMandateReference(String reference) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withReference(reference);
            return this;
        }

        /**
         * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
         * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
         */
        public PayerAuthorisationUpdateRequest withMandateScheme(Mandate.Scheme scheme) {
            if (mandate == null) {
                mandate = new Mandate();
            }
            mandate.withScheme(scheme);
            return this;
        }

        private PayerAuthorisationUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PayerAuthorisationUpdateRequest withHeader(String headerName, String headerValue) {
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
            return "payer_authorisations/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "payer_authorisations";
        }

        @Override
        protected Class<PayerAuthorisation> getResponseClass() {
            return PayerAuthorisation.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class BankAccount {
            private String accountHolderName;
            private String accountNumber;
            private String accountNumberEnding;
            private String accountNumberSuffix;
            private AccountType accountType;
            private String bankCode;
            private String branchCode;
            private String countryCode;
            private String currency;
            private String iban;
            private Map<String, String> metadata;

            /**
             * Name of the account holder, as known by the bank. This field will be transliterated,
             * upcased and truncated to 18 characters. This field is required unless the request
             * includes a [customer bank account
             * token](#javascript-flow-customer-bank-account-tokens).
             */
            public BankAccount withAccountHolderName(String accountHolderName) {
                this.accountHolderName = accountHolderName;
                return this;
            }

            /**
             * Bank account number - see [local details](#appendix-local-bank-details) for more
             * information. Alternatively you can provide an `iban`.
             */
            public BankAccount withAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
                return this;
            }

            /**
             * The last few digits of the account number. Currently 4 digits for NZD bank accounts
             * and 2 digits for other currencies.
             */
            public BankAccount withAccountNumberEnding(String accountNumberEnding) {
                this.accountNumberEnding = accountNumberEnding;
                return this;
            }

            /**
             * Account number suffix (only for bank accounts denominated in NZD) - see [local
             * details](#local-bank-details-new-zealand) for more information.
             */
            public BankAccount withAccountNumberSuffix(String accountNumberSuffix) {
                this.accountNumberSuffix = accountNumberSuffix;
                return this;
            }

            /**
             * Bank account type. Required for USD-denominated bank accounts. Must not be provided
             * for bank accounts in other currencies. See [local
             * details](#local-bank-details-united-states) for more information.
             */
            public BankAccount withAccountType(AccountType accountType) {
                this.accountType = accountType;
                return this;
            }

            /**
             * Bank code - see [local details](#appendix-local-bank-details) for more information.
             * Alternatively you can provide an `iban`.
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
             * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
             * Defaults to the country code of the `iban` if supplied, otherwise is required.
             */
            public BankAccount withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
             */
            public BankAccount withCurrency(String currency) {
                this.currency = currency;
                return this;
            }

            /**
             * International Bank Account Number. Alternatively you can provide [local
             * details](#appendix-local-bank-details). IBANs are not accepted for Swedish bank
             * accounts denominated in SEK - you must supply [local
             * details](#local-bank-details-sweden).
             */
            public BankAccount withIban(String iban) {
                this.iban = iban;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public BankAccount withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            public enum AccountType {
                @SerializedName("savings")
                SAVINGS, @SerializedName("checking")
                CHECKING, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
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
            private String locale;
            private Map<String, String> metadata;
            private String postalCode;
            private String region;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address.
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
             * Customer's company name. Required unless a `given_name` and `family_name` are
             * provided. For Canadian customers, the use of a `company_name` value will mean that
             * any mandate created from this customer will be considered to be a "Business PAD"
             * (otherwise, any mandate will be considered to be a "Personal PAD").
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
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
             * Must be supplied if the customer's bank account is denominated in Danish krone (DKK).
             */
            public Customer withDanishIdentityNumber(String danishIdentityNumber) {
                this.danishIdentityNumber = danishIdentityNumber;
                return this;
            }

            /**
             * Customer's email address. Required in most cases, as this allows GoCardless to send
             * notifications to this customer.
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
             * An [IETF Language Tag](https://tools.ietf.org/html/rfc5646), used for both language
             * and regional variations of our product.
             * 
             */
            public Customer withLocale(String locale) {
                this.locale = locale;
                return this;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Customer withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * The customer's postal code.
             */
            public Customer withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The customer's address region, county or department. For US customers a 2 letter
             * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required
             * (e.g. `CA` for California).
             */
            public Customer withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer,
             * samordningsnummer, or organisationsnummer) of the customer. Must be supplied if the
             * customer's bank account is denominated in Swedish krona (SEK). This field cannot be
             * changed once it has been set.
             */
            public Customer withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }
        }

        public static class Mandate {
            private Map<String, String> metadata;
            private String payerIpAddress;
            private String reference;
            private Scheme scheme;

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Mandate withMetadata(Map<String, String> metadata) {
                this.metadata = metadata;
                return this;
            }

            /**
             * For ACH customers only. Required for ACH customers. A string containing the IP
             * address of the payer to whom the mandate belongs (i.e. as a result of their
             * completion of a mandate setup flow in their browser).
             * 
             * Not required for creating offline mandates where `authorisation_source` is set to
             * telephone or paper.
             * 
             */
            public Mandate withPayerIpAddress(String payerIpAddress) {
                this.payerIpAddress = payerIpAddress;
                return this;
            }

            /**
             * Unique reference. Different schemes have different length and [character
             * set](#appendix-character-sets) requirements. GoCardless will generate a unique
             * reference satisfying the different scheme requirements if this field is left blank.
             */
            public Mandate withReference(String reference) {
                this.reference = reference;
                return this;
            }

            /**
             * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
             * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
             */
            public Mandate withScheme(Scheme scheme) {
                this.scheme = scheme;
                return this;
            }

            public enum Scheme {
                @SerializedName("ach")
                ACH, @SerializedName("autogiro")
                AUTOGIRO, @SerializedName("bacs")
                BACS, @SerializedName("becs")
                BECS, @SerializedName("becs_nz")
                BECS_NZ, @SerializedName("betalingsservice")
                BETALINGSSERVICE, @SerializedName("faster_payments")
                FASTER_PAYMENTS, @SerializedName("pad")
                PAD, @SerializedName("pay_to")
                PAY_TO, @SerializedName("sepa_core")
                SEPA_CORE, @SerializedName("unknown")
                UNKNOWN;

                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }
    }

    /**
     * Request class for {@link PayerAuthorisationService#submit }.
     *
     * Submits all the data previously pushed to this PayerAuthorisation for verification. This
     * time, a 200 HTTP status is returned if the resource is valid and a 422 error response in case
     * of validation errors. After it is successfully submitted, the Payer Authorisation can no
     * longer be edited.
     */
    public static final class PayerAuthorisationSubmitRequest
            extends PostRequest<PayerAuthorisation> {
        @PathParam
        private final String identity;

        private PayerAuthorisationSubmitRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PayerAuthorisationSubmitRequest withHeader(String headerName, String headerValue) {
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
            return "payer_authorisations/:identity/actions/submit";
        }

        @Override
        protected String getEnvelope() {
            return "payer_authorisations";
        }

        @Override
        protected Class<PayerAuthorisation> getResponseClass() {
            return PayerAuthorisation.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    /**
     * Request class for {@link PayerAuthorisationService#confirm }.
     *
     * Confirms the Payer Authorisation, indicating that the resources are ready to be created. A
     * Payer Authorisation cannot be confirmed if it hasn't been submitted yet.
     * 
     * <p class="notice">
     * The main use of the confirm endpoint is to enable integrators to acknowledge the end of the
     * setup process. They might want to make the payers go through some other steps after they go
     * through our flow or make them go through the necessary verification mechanism (upcoming
     * feature).
     * </p>
     */
    public static final class PayerAuthorisationConfirmRequest
            extends PostRequest<PayerAuthorisation> {
        @PathParam
        private final String identity;

        private PayerAuthorisationConfirmRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public PayerAuthorisationConfirmRequest withHeader(String headerName, String headerValue) {
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
            return "payer_authorisations/:identity/actions/confirm";
        }

        @Override
        protected String getEnvelope() {
            return "payer_authorisations";
        }

        @Override
        protected Class<PayerAuthorisation> getResponseClass() {
            return PayerAuthorisation.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
