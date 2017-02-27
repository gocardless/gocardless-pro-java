package com.gocardless.services;

import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.RedirectFlow;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

/**
 * Service class for working with redirect flow resources.
 *
 * Redirect flows enable you to use GoCardless' [hosted payment
 * pages](https://pay-sandbox.gocardless.com/AL000000AKFPFF) to set up mandates with your customers.
 * These pages are fully compliant and have been translated into Dutch, French, German, Italian,
 * Portuguese, Spanish and Swedish.
 * 
 * The overall flow is:
 * 
 * 1. You
 * [create](#redirect-flows-create-a-redirect-flow) a redirect flow for your customer, and redirect
 * them to the returned redirect url, e.g. `https://pay.gocardless.com/flow/RE123`.
 * 
 * 2. Your
 * customer supplies their name, email, address, and bank account details, and submits the form. This
 * securely stores their details, and redirects them back to your `success_redirect_url` with
 * `redirect_flow_id=RE123` in the querystring.
 * 
 * 3. You
 * [complete](#redirect-flows-complete-a-redirect-flow) the redirect flow, which creates a
 * [customer](#core-endpoints-customers), [customer bank
 * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates), and
 * returns the ID of the mandate. You may wish to create a
 * [subscription](#core-endpoints-subscriptions) or [payment](#core-endpoints-payments) at this
 * point.
 * 
 * It is recommended that you link the redirect flow to your user object as soon as it is
 * created, and attach the created resources to that user in the complete step.
 * 
 * Redirect flows
 * expire 30 minutes after they are first created. You cannot complete an expired redirect flow.
 */
public class RedirectFlowService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#redirectFlows() }.
     */
    public RedirectFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * hosted payment pages.
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
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates) using
     * the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will
     * return a `redirect_flow_incomplete` error if your customer has not yet been redirected back to
     * your site, and a `redirect_flow_already_completed` error if your integration has already completed
     * this flow. It will return a `bad_request` error if the `session_token` differs to the one supplied
     * when the redirect flow was created.
     */
    public RedirectFlowCompleteRequest complete(String identity) {
        return new RedirectFlowCompleteRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RedirectFlowService#create }.
     *
     * Creates a redirect flow object which can then be used to redirect your customer to the GoCardless
     * hosted payment pages.
     */
    public static final class RedirectFlowCreateRequest extends IdempotentPostRequest<RedirectFlow> {
        private String description;
        private Links links;
        private PrefillDetails prefillDetails;
        private Scheme scheme;
        private String sessionToken;
        private String successRedirectUrl;

        /**
         * A description of the item the customer is paying for. This will be shown on the hosted payment
         * pages.
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
         * The [creditor](#core-endpoints-creditors) for whom the mandate will be created. The `name` of the
         * creditor will be displayed on the payment page. Required if your account manages multiple
         * creditors.
         */
        public RedirectFlowCreateRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        public RedirectFlowCreateRequest withPrefillDetails(PrefillDetails prefillDetails) {
            this.prefillDetails = prefillDetails;
            return this;
        }

        /**
         * Information used to prefill the form. It will be stored unvalidated and the customer will be able
         * to review and amend it before completing the form.
         */
        public RedirectFlowCreateRequest withPrefillDetailsCustomer(Customer customer) {
            if (prefillDetails == null) {
                prefillDetails = new PrefillDetails();
            }
            prefillDetails.withCustomer(customer);
            return this;
        }

        /**
         * The Direct Debit scheme of the mandate. If specified, the payment pages will only allow the set-up
         * of a mandate for the specified scheme. It is recommended that you leave this blank so the most
         * appropriate scheme is picked based on the customer's bank account.
         */
        public RedirectFlowCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again when it is
         * completed. This allows integrators to ensure that the user who was originally sent to the
         * GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCreateRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        /**
         * The URL to redirect to upon successful mandate setup. You must use a URL beginning `https` in the
         * live environment.
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
            return new RedirectFlowGetRequest(httpClient, id);
        }

        private RedirectFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows";
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
            @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("sepa_core")
            SEPA_CORE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        public static class Links {
            private String creditor;

            /**
             * The [creditor](#core-endpoints-creditors) for whom the mandate will be created. The `name` of the
             * creditor will be displayed on the payment page. Required if your account manages multiple
             * creditors.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
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
            private String email;
            private String familyName;
            private String givenName;
            private String language;
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
             * Customer's company name.
             */
            public Customer withCompanyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            /**
             * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             * alpha-2 code.
             */
            public Customer withCountryCode(String countryCode) {
                this.countryCode = countryCode;
                return this;
            }

            /**
             * Customer's email address.
             */
            public Customer withEmail(String email) {
                this.email = email;
                return this;
            }

            /**
             * Customer's surname.
             */
            public Customer withFamilyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            /**
             * Customer's first name.
             */
            public Customer withGivenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            /**
             * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code.
             */
            public Customer withLanguage(String language) {
                this.language = language;
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
             * The customer's address region, county or department.
             */
            public Customer withRegion(String region) {
                this.region = region;
                return this;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
             * organisationsnummer) of the customer.
             */
            public Customer withSwedishIdentityNumber(String swedishIdentityNumber) {
                this.swedishIdentityNumber = swedishIdentityNumber;
                return this;
            }

            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                if (addressLine1 != null) {
                    params.put("customer[address_line1]", addressLine1);
                }
                if (addressLine2 != null) {
                    params.put("customer[address_line2]", addressLine2);
                }
                if (addressLine3 != null) {
                    params.put("customer[address_line3]", addressLine3);
                }
                if (city != null) {
                    params.put("customer[city]", city);
                }
                if (companyName != null) {
                    params.put("customer[company_name]", companyName);
                }
                if (countryCode != null) {
                    params.put("customer[country_code]", countryCode);
                }
                if (email != null) {
                    params.put("customer[email]", email);
                }
                if (familyName != null) {
                    params.put("customer[family_name]", familyName);
                }
                if (givenName != null) {
                    params.put("customer[given_name]", givenName);
                }
                if (language != null) {
                    params.put("customer[language]", language);
                }
                if (postalCode != null) {
                    params.put("customer[postal_code]", postalCode);
                }
                if (region != null) {
                    params.put("customer[region]", region);
                }
                if (swedishIdentityNumber != null) {
                    params.put("customer[swedish_identity_number]", swedishIdentityNumber);
                }
                return params.build();
            }
        }

        public static class PrefillDetails {
            private Customer customer;

            /**
             * Information used to prefill the form. It will be stored unvalidated and the customer will be able
             * to review and amend it before completing the form.
             */
            public PrefillDetails withCustomer(Customer customer) {
                this.customer = customer;
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

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows/:identity";
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
     * account](#core-endpoints-customer-bank-accounts), and [mandate](#core-endpoints-mandates) using
     * the details supplied by your customer and returns the ID of the created mandate.
     * 
     * This will
     * return a `redirect_flow_incomplete` error if your customer has not yet been redirected back to
     * your site, and a `redirect_flow_already_completed` error if your integration has already completed
     * this flow. It will return a `bad_request` error if the `session_token` differs to the one supplied
     * when the redirect flow was created.
     */
    public static final class RedirectFlowCompleteRequest extends PostRequest<RedirectFlow> {
        @PathParam
        private final String identity;
        private String sessionToken;

        /**
         * The customer's session ID must be provided when the redirect flow is set up and again when it is
         * completed. This allows integrators to ensure that the user who was originally sent to the
         * GoCardless payment pages is the one who has completed them.
         */
        public RedirectFlowCompleteRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private RedirectFlowCompleteRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows/:identity/actions/complete";
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
