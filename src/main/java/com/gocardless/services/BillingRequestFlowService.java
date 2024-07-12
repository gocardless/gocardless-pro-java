package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BillingRequestFlow;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Service class for working with billing request flow resources.
 *
 * Billing Request Flows can be created to enable a payer to authorise a payment created for a
 * scheme with strong payer authorisation (such as open banking single payments).
 */
public class BillingRequestFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#billingRequestFlows() }.
     */
    public BillingRequestFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new billing request flow.
     */
    public BillingRequestFlowCreateRequest create() {
        return new BillingRequestFlowCreateRequest(httpClient);
    }

    /**
     * Returns the flow having generated a fresh session token which can be used to power
     * integrations that manipulate the flow.
     */
    public BillingRequestFlowInitialiseRequest initialise(String identity) {
        return new BillingRequestFlowInitialiseRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BillingRequestFlowService#create }.
     *
     * Creates a new billing request flow.
     */
    public static final class BillingRequestFlowCreateRequest
            extends PostRequest<BillingRequestFlow> {
        private Boolean autoFulfil;
        private Boolean customerDetailsCaptured;
        private String exitUri;
        private String language;
        private Links links;
        private Boolean lockBankAccount;
        private Boolean lockCurrency;
        private Boolean lockCustomerDetails;
        private PrefilledBankAccount prefilledBankAccount;
        private PrefilledCustomer prefilledCustomer;
        private String redirectUri;
        private Boolean showRedirectButtons;
        private Boolean showSuccessRedirectButton;

        /**
         * (Experimental feature) Fulfil the Billing Request on completion of the flow (true by
         * default). Disabling the auto_fulfil is not allowed currently.
         */
        public BillingRequestFlowCreateRequest withAutoFulfil(Boolean autoFulfil) {
            this.autoFulfil = autoFulfil;
            return this;
        }

        /**
         * Identifies whether a Billing Request belongs to a specific customer
         */
        public BillingRequestFlowCreateRequest withCustomerDetailsCaptured(
                Boolean customerDetailsCaptured) {
            this.customerDetailsCaptured = customerDetailsCaptured;
            return this;
        }

        /**
         * URL that the payer can be taken to if there isn't a way to progress ahead in flow.
         */
        public BillingRequestFlowCreateRequest withExitUri(String exitUri) {
            this.exitUri = exitUri;
            return this;
        }

        /**
         * Sets the default language of the Billing Request Flow and the customer. [ISO
         * 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
         */
        public BillingRequestFlowCreateRequest withLanguage(String language) {
            this.language = language;
            return this;
        }

        public BillingRequestFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [billing request](#billing-requests-billing-requests) against which this flow
         * was created.
         */
        public BillingRequestFlowCreateRequest withLinksBillingRequest(String billingRequest) {
            if (links == null) {
                links = new Links();
            }
            links.withBillingRequest(billingRequest);
            return this;
        }

        /**
         * If true, the payer will not be able to change their bank account within the flow. If the
         * bank_account details are collected as part of bank_authorisation then GC will set this
         * value to true mid flow.
         * 
         * You can only lock bank account if these have already been completed as a part of the
         * billing request.
         * 
         */
        public BillingRequestFlowCreateRequest withLockBankAccount(Boolean lockBankAccount) {
            this.lockBankAccount = lockBankAccount;
            return this;
        }

        /**
         * If true, the payer will not be able to change their currency/scheme manually within the
         * flow. Note that this only applies to the mandate only flows - currency/scheme can never
         * be changed when there is a specified subscription or payment.
         */
        public BillingRequestFlowCreateRequest withLockCurrency(Boolean lockCurrency) {
            this.lockCurrency = lockCurrency;
            return this;
        }

        /**
         * If true, the payer will not be able to edit their customer details within the flow. If
         * the customer details are collected as part of bank_authorisation then GC will set this
         * value to true mid flow.
         * 
         * You can only lock customer details if these have already been completed as a part of the
         * billing request.
         * 
         */
        public BillingRequestFlowCreateRequest withLockCustomerDetails(
                Boolean lockCustomerDetails) {
            this.lockCustomerDetails = lockCustomerDetails;
            return this;
        }

        /**
         * Bank account information used to prefill the payment page so your customer doesn't have
         * to re-type details you already hold about them. It will be stored unvalidated and the
         * customer will be able to review and amend it before completing the form.
         */
        public BillingRequestFlowCreateRequest withPrefilledBankAccount(
                PrefilledBankAccount prefilledBankAccount) {
            this.prefilledBankAccount = prefilledBankAccount;
            return this;
        }

        /**
         * Bank account type for USD-denominated bank accounts. Must not be provided for bank
         * accounts in other currencies. See [local details](#local-bank-details-united-states) for
         * more information.
         */
        public BillingRequestFlowCreateRequest withPrefilledBankAccountAccountType(
                PrefilledBankAccount.AccountType accountType) {
            if (prefilledBankAccount == null) {
                prefilledBankAccount = new PrefilledBankAccount();
            }
            prefilledBankAccount.withAccountType(accountType);
            return this;
        }

        /**
         * Customer information used to prefill the payment page so your customer doesn't have to
         * re-type details you already hold about them. It will be stored unvalidated and the
         * customer will be able to review and amend it before completing the form.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomer(
                PrefilledCustomer prefilledCustomer) {
            this.prefilledCustomer = prefilledCustomer;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerAddressLine1(
                String addressLine1) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerAddressLine2(
                String addressLine2) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerAddressLine3(
                String addressLine3) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerCity(String city) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withCity(city);
            return this;
        }

        /**
         * Customer's company name. Company name should only be provided if `given_name` and
         * `family_name` are null.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerCompanyName(
                String companyName) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withCompanyName(companyName);
            return this;
        }

        /**
         * [ISO 3166-1 alpha-2
         * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerCountryCode(
                String countryCode) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerDanishIdentityNumber(
                String danishIdentityNumber) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withDanishIdentityNumber(danishIdentityNumber);
            return this;
        }

        /**
         * Customer's email address.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerEmail(String email) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerFamilyName(String familyName) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerGivenName(String givenName) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withGivenName(givenName);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerPostalCode(String postalCode) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerRegion(String region) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withRegion(region);
            return this;
        }

        /**
         * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
         * organisationsnummer) of the customer.
         */
        public BillingRequestFlowCreateRequest withPrefilledCustomerSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        /**
         * URL that the payer can be redirected to after completing the request flow.
         */
        public BillingRequestFlowCreateRequest withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        /**
         * If true, the payer will be able to see redirect action buttons on Thank You page. These
         * action buttons will provide a way to connect back to the billing request flow app if
         * opened within a mobile app. For successful flow, the button will take the payer back the
         * billing request flow where they will see the success screen. For failure, button will
         * take the payer to url being provided against exit_uri field.
         */
        public BillingRequestFlowCreateRequest withShowRedirectButtons(
                Boolean showRedirectButtons) {
            this.showRedirectButtons = showRedirectButtons;
            return this;
        }

        /**
         * If true, the payer will be able to see a redirect action button on the Success page. This
         * action button will provide a way to redirect the payer to the given redirect_uri. This
         * functionality is helpful when merchants do not want payers to be automatically redirected
         * or on Android devices, where automatic redirections are not possible.
         */
        public BillingRequestFlowCreateRequest withShowSuccessRedirectButton(
                Boolean showSuccessRedirectButton) {
            this.showSuccessRedirectButton = showSuccessRedirectButton;
            return this;
        }

        private BillingRequestFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BillingRequestFlowCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "billing_request_flows";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_flows";
        }

        @Override
        protected Class<BillingRequestFlow> getResponseClass() {
            return BillingRequestFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String billingRequest;

            /**
             * ID of the [billing request](#billing-requests-billing-requests) against which this
             * flow was created.
             */
            public Links withBillingRequest(String billingRequest) {
                this.billingRequest = billingRequest;
                return this;
            }
        }

        public static class PrefilledBankAccount {
            private AccountType accountType;

            /**
             * Bank account type for USD-denominated bank accounts. Must not be provided for bank
             * accounts in other currencies. See [local details](#local-bank-details-united-states)
             * for more information.
             */
            public PrefilledBankAccount withAccountType(AccountType accountType) {
                this.accountType = accountType;
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

        public static class PrefilledCustomer {
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
            private String postalCode;
            private String region;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address.
             */
            public PrefilledCustomer withAddressLine1(String addressLine1) {
                this.addressLine1 = addressLine1;
                return this;
            }

            /**
             * The second line of the customer's address.
             */
            public PrefilledCustomer withAddressLine2(String addressLine2) {
                this.addressLine2 = addressLine2;
                return this;
            }

            /**
             * The third line of the customer's address.
             */
            public PrefilledCustomer withAddressLine3(String addressLine3) {
                this.addressLine3 = addressLine3;
                return this;
            }

            /**
             * The city of the customer's address.
             */
            public PrefilledCustomer withCity(String city) {
                this.city = city;
                return this;
            }

            /**
             * Customer's company name. Company name should only be provided if `given_name` and
             * `family_name` are null.
             */
            public PrefilledCustomer withCompanyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             */
            public PrefilledCustomer withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
             */
            public PrefilledCustomer withDanishIdentityNumber(String danishIdentityNumber) {
                this.danishIdentityNumber = danishIdentityNumber;
                return this;
            }

            /**
             * Customer's email address.
             */
            public PrefilledCustomer withEmail(String email) {
                this.email = email;
                return this;
            }

            /**
             * Customer's surname.
             */
            public PrefilledCustomer withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * Customer's first name.
             */
            public PrefilledCustomer withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * The customer's postal code.
             */
            public PrefilledCustomer withPostalCode(String postalCode) {
                this.postalCode = postalCode;
                return this;
            }

            /**
             * The customer's address region, county or department.
             */
            public PrefilledCustomer withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer,
             * samordningsnummer, or organisationsnummer) of the customer.
             */
            public PrefilledCustomer withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }
        }
    }

    /**
     * Request class for {@link BillingRequestFlowService#initialise }.
     *
     * Returns the flow having generated a fresh session token which can be used to power
     * integrations that manipulate the flow.
     */
    public static final class BillingRequestFlowInitialiseRequest
            extends PostRequest<BillingRequestFlow> {
        @PathParam
        private final String identity;

        private BillingRequestFlowInitialiseRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BillingRequestFlowInitialiseRequest withHeader(String headerName,
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
            return "billing_request_flows/:identity/actions/initialise";
        }

        @Override
        protected String getEnvelope() {
            return "billing_request_flows";
        }

        @Override
        protected Class<BillingRequestFlow> getResponseClass() {
            return BillingRequestFlow.class;
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
