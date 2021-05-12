package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.RedirectFlow;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with redirect flow resources.
 *
 * Redirect flows enable you to use GoCardless' [hosted payment
 * pages](https://pay-sandbox.gocardless.com/AL000000AKFPFF) to set up mandates with your customers.
 * These pages are fully compliant and have been translated into Danish, Dutch, French, German,
 * Italian, Norwegian, Portuguese, Slovak, Spanish and Swedish.
 * 
 * The overall flow is:
 * 
 * 1. You [create](#redirect-flows-create-a-redirect-flow) a redirect flow for your customer, and
 * redirect them to the returned redirect url, e.g. `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your customer supplies their name, email, address, and bank account details, and submits the
 * form. This securely stores their details, and redirects them back to your `success_redirect_url`
 * with `redirect_flow_id=RE123` in the querystring.
 * 
 * 3. You [complete](#redirect-flows-complete-a-redirect-flow) the redirect flow, which creates a
 * [customer](#core-endpoints-customers), [customer bank
 * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates), and
 * returns the ID of the mandate. You may wish to create a
 * [subscription](#core-endpoints-subscriptions) or [payment](#core-endpoints-payments) at this
 * point.
 * 
 * Once you have [completed](#redirect-flows-complete-a-redirect-flow) the redirect flow via the
 * API, you should display a confirmation page to your customer, confirming that their Direct Debit
 * has been set up. You can build your own page, or redirect to the one we provide in the
 * `confirmation_url` attribute of the redirect flow.
 * 
 * Redirect flows expire 30 minutes after they are first created. You cannot complete an expired
 * redirect flow.
 */
public class RedirectFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#redirectFlows() }.
     */
    public RedirectFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a redirect flow object which can then be used to redirect your customer to the
     * GoCardless hosted payment pages.
     */
    public RedirectFlowCreateRequest create() {
        return new RedirectFlowCreateRequest(httpClient);
    }

    /**
     * Returns all details about a single redirect flow
     */
    public RedirectFlowGetRequest get(String identity) {
        return new RedirectFlowGetRequest(httpClient, identity);
    }

    /**
     * This creates a [customer](#core-endpoints-customers), [customer bank
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates)
     * using the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will return a `redirect_flow_incomplete` error if your customer has not yet been
     * redirected back to your site, and a `redirect_flow_already_completed` error if your
     * integration has already completed this flow. It will return a `bad_request` error if the
     * `session_token` differs to the one supplied when the redirect flow was created.
     */
    public RedirectFlowCompleteRequest complete(String identity) {
        return new RedirectFlowCompleteRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RedirectFlowService#create }.
     *
     * Creates a redirect flow object which can then be used to redirect your customer to the
     * GoCardless hosted payment pages.
     */
    public static final class RedirectFlowCreateRequest
            extends IdempotentPostRequest<RedirectFlow> {
        private String description;
        private Links links;
        private Map<String, String> metadata;
        private PrefilledBankAccount prefilledBankAccount;
        private PrefilledCustomer prefilledCustomer;
        private Scheme scheme;
        private String sessionToken;
        private String successRedirectUrl;

        /**
         * A description of the item the customer is paying for. This will be shown on the hosted
         * payment pages.
         */
        public RedirectFlowCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        public RedirectFlowCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * The [creditor](#core-endpoints-creditors) for whom the mandate will be created. The
         * `name` of the creditor will be displayed on the payment page. Required if your account
         * manages multiple creditors.
         */
        public RedirectFlowCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public RedirectFlowCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
         * characters and values up to 500 characters.
         */
        public RedirectFlowCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        /**
         * Bank account information used to prefill the payment page so your customer doesn't have
         * to re-type details you already hold about them. It will be stored unvalidated and the
         * customer will be able to review and amend it before completing the form.
         */
        public RedirectFlowCreateRequest withPrefilledBankAccount(
                PrefilledBankAccount prefilledBankAccount) {
            this.prefilledBankAccount = prefilledBankAccount;
            return this;
        }

        /**
         * Bank account type for USD-denominated bank accounts. Must not be provided for bank
         * accounts in other currencies. See [local details](#local-bank-details-united-states) for
         * more information.
         */
        public RedirectFlowCreateRequest withPrefilledBankAccountAccountType(
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
        public RedirectFlowCreateRequest withPrefilledCustomer(
                PrefilledCustomer prefilledCustomer) {
            this.prefilledCustomer = prefilledCustomer;
            return this;
        }

        /**
         * The first line of the customer's address.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerAddressLine1(String addressLine1) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine1(addressLine1);
            return this;
        }

        /**
         * The second line of the customer's address.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerAddressLine2(String addressLine2) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine2(addressLine2);
            return this;
        }

        /**
         * The third line of the customer's address.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerAddressLine3(String addressLine3) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withAddressLine3(addressLine3);
            return this;
        }

        /**
         * The city of the customer's address.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerCity(String city) {
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
        public RedirectFlowCreateRequest withPrefilledCustomerCompanyName(String companyName) {
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
        public RedirectFlowCreateRequest withPrefilledCustomerCountryCode(String countryCode) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withCountryCode(countryCode);
            return this;
        }

        /**
         * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerDanishIdentityNumber(
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
        public RedirectFlowCreateRequest withPrefilledCustomerEmail(String email) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withEmail(email);
            return this;
        }

        /**
         * Customer's surname.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerFamilyName(String familyName) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withFamilyName(familyName);
            return this;
        }

        /**
         * Customer's first name.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerGivenName(String givenName) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withGivenName(givenName);
            return this;
        }

        /**
         * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerLanguage(String language) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withLanguage(language);
            return this;
        }

        /**
         * For New Zealand customers only.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerPhoneNumber(String phoneNumber) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withPhoneNumber(phoneNumber);
            return this;
        }

        /**
         * The customer's postal code.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerPostalCode(String postalCode) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withPostalCode(postalCode);
            return this;
        }

        /**
         * The customer's address region, county or department.
         */
        public RedirectFlowCreateRequest withPrefilledCustomerRegion(String region) {
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
        public RedirectFlowCreateRequest withPrefilledCustomerSwedishIdentityNumber(
                String swedishIdentityNumber) {
            if (prefilledCustomer == null) {
                prefilledCustomer = new PrefilledCustomer();
            }
            prefilledCustomer.withSwedishIdentityNumber(swedishIdentityNumber);
            return this;
        }

        /**
         * The Direct Debit scheme of the mandate. If specified, the payment pages will only allow
         * the set-up of a mandate for the specified scheme. It is recommended that you leave this
         * blank so the most appropriate scheme is picked based on the customer's bank account.
         */
        public RedirectFlowCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again
         * when it is completed. This allows integrators to ensure that the user who was originally
         * sent to the GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCreateRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        /**
         * The URL to redirect to upon successful mandate setup. You must use a URL beginning
         * `https` in the live environment.
         */
        public RedirectFlowCreateRequest withSuccessRedirectUrl(String successRedirectUrl) {
            this.successRedirectUrl = successRedirectUrl;
            return this;
        }

        public RedirectFlowCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<RedirectFlow> handleConflict(HttpClient httpClient, String id) {
            RedirectFlowGetRequest request = new RedirectFlowGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private RedirectFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public RedirectFlowCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "redirect_flows";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Scheme {
            @SerializedName("ach")
            ACH, @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("becs")
            BECS, @SerializedName("becs_nz")
            BECS_NZ, @SerializedName("betalingsservice")
            BETALINGSSERVICE, @SerializedName("pad")
            PAD, @SerializedName("sepa_core")
            SEPA_CORE;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String creditor;

            /**
             * The [creditor](#core-endpoints-creditors) for whom the mandate will be created. The
             * `name` of the creditor will be displayed on the payment page. Required if your
             * account manages multiple creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
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
                CHECKING;

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
            private String language;
            private String phoneNumber;
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
             * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
             */
            public PrefilledCustomer withLanguage(String language) {
                this.language = language;
                return this;
            }

            /**
             * For New Zealand customers only.
             */
            public PrefilledCustomer withPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
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
     * Request class for {@link RedirectFlowService#get }.
     *
     * Returns all details about a single redirect flow
     */
    public static final class RedirectFlowGetRequest extends GetRequest<RedirectFlow> {
        @PathParam
        private final String identity;

        private RedirectFlowGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public RedirectFlowGetRequest withHeader(String headerName, String headerValue) {
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
            return "redirect_flows/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }
    }

    /**
     * Request class for {@link RedirectFlowService#complete }.
     *
     * This creates a [customer](#core-endpoints-customers), [customer bank
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates)
     * using the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will return a `redirect_flow_incomplete` error if your customer has not yet been
     * redirected back to your site, and a `redirect_flow_already_completed` error if your
     * integration has already completed this flow. It will return a `bad_request` error if the
     * `session_token` differs to the one supplied when the redirect flow was created.
     */
    public static final class RedirectFlowCompleteRequest extends PostRequest<RedirectFlow> {
        @PathParam
        private final String identity;
        private String sessionToken;

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again
         * when it is completed. This allows integrators to ensure that the user who was originally
         * sent to the GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCompleteRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private RedirectFlowCompleteRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public RedirectFlowCompleteRequest withHeader(String headerName, String headerValue) {
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
            return "redirect_flows/:identity/actions/complete";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
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
