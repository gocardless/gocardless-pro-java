package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a billing request resource returned from the API.
 *
 * Billing Requests help create resources that require input or action from a customer. An example
 * of required input might be additional customer billing details, while an action would be asking a
 * customer to authorise a payment using their mobile banking app.
 * 
 * See [Billing Requests:
 * Overview](https://developer.gocardless.com/getting-started/billing-requests/overview/) for
 * how-to's, explanations and tutorials.
 */
public class BillingRequest {
    private BillingRequest() {
        // blank to prevent instantiation
    }

    private List<Action> actions;
    private String createdAt;
    private String id;
    private Links links;
    private MandateRequest mandateRequest;
    private Map<String, String> metadata;
    private PaymentRequest paymentRequest;
    private Resources resources;
    private Status status;

    /**
     * List of actions that can be performed before this billing request can be fulfilled.
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "BRQ".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * Request for a mandate
     */
    public MandateRequest getMandateRequest() {
        return mandateRequest;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Request for a one-off strongly authorised payment
     */
    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public Resources getResources() {
        return resources;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending`: the billing_request is pending and can be used</li>
     * <li>`ready_to_fulfil`: the billing_request is ready to fulfil</li>
     * <li>`fulfilled`: the billing_request has been fulfilled and a payment created</li>
     * <li>`cancelled`: the billing_request has been cancelled and cannot be used</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("ready_to_fulfil")
        READY_TO_FULFIL, @SerializedName("fulfilled")
        FULFILLED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Action {
        private Action() {
            // blank to prevent instantiation
        }

        private BankAuthorisation bankAuthorisation;
        private CollectCustomerDetails collectCustomerDetails;
        private List<String> completesActions;
        private Boolean required;
        private List<String> requiresActions;
        private Status status;
        private Type type;

        /**
         * Describes the behaviour of bank authorisations, for the bank_authorisation action
         */
        public BankAuthorisation getBankAuthorisation() {
            return bankAuthorisation;
        }

        /**
         * Additional parameters to help complete the collect_customer_details action
         */
        public CollectCustomerDetails getCollectCustomerDetails() {
            return collectCustomerDetails;
        }

        /**
         * Which other action types this action can complete.
         */
        public List<String> getCompletesActions() {
            return completesActions;
        }

        /**
         * Informs you whether the action is required to fulfil the billing request or not.
         */
        public Boolean getRequired() {
            return required;
        }

        /**
         * Requires completing these actions before this action can be completed.
         */
        public List<String> getRequiresActions() {
            return requiresActions;
        }

        /**
         * Status of the action
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Unique identifier for the action.
         */
        public Type getType() {
            return type;
        }

        public enum Status {
            @SerializedName("pending")
            PENDING, @SerializedName("completed")
            COMPLETED, @SerializedName("unknown")
            UNKNOWN
        }

        public enum Type {
            @SerializedName("choose_currency")
            CHOOSE_CURRENCY, @SerializedName("collect_customer_details")
            COLLECT_CUSTOMER_DETAILS, @SerializedName("collect_bank_account")
            COLLECT_BANK_ACCOUNT, @SerializedName("bank_authorisation")
            BANK_AUTHORISATION, @SerializedName("confirm_payer_details")
            CONFIRM_PAYER_DETAILS, @SerializedName("unknown")
            UNKNOWN
        }

        /**
         * Represents a bank authorisation resource returned from the API.
         *
         * Describes the behaviour of bank authorisations, for the bank_authorisation action
         */
        public static class BankAuthorisation {
            private BankAuthorisation() {
                // blank to prevent instantiation
            }

            private Adapter adapter;
            private AuthorisationType authorisationType;
            private Boolean requiresInstitution;

            /**
             * Which authorisation adapter will be used to power these authorisations (GoCardless
             * internal use only)
             */
            public Adapter getAdapter() {
                return adapter;
            }

            /**
             * What type of bank authorisations are supported on this billing request
             */
            public AuthorisationType getAuthorisationType() {
                return authorisationType;
            }

            /**
             * Whether an institution is a required field when creating this bank authorisation
             */
            public Boolean getRequiresInstitution() {
                return requiresInstitution;
            }

            public enum Adapter {
                @SerializedName("open_banking_gateway_pis")
                OPEN_BANKING_GATEWAY_PIS, @SerializedName("plaid_ais")
                PLAID_AIS, @SerializedName("unknown")
                UNKNOWN
            }

            public enum AuthorisationType {
                @SerializedName("payment")
                PAYMENT, @SerializedName("mandate")
                MANDATE, @SerializedName("unknown")
                UNKNOWN
            }
        }

        /**
         * Represents a collect customer detail resource returned from the API.
         *
         * Additional parameters to help complete the collect_customer_details action
         */
        public static class CollectCustomerDetails {
            private CollectCustomerDetails() {
                // blank to prevent instantiation
            }

            private String defaultCountryCode;

            /**
             * Default customer country code, as determined by scheme and payer location
             */
            public String getDefaultCountryCode() {
                return defaultCountryCode;
            }
        }
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String bankAuthorisation;
        private String creditor;
        private String customer;
        private String customerBankAccount;
        private String customerBillingDetail;
        private String mandateRequest;
        private String mandateRequestMandate;
        private String paymentRequest;
        private String paymentRequestPayment;

        /**
         * (Optional) ID of the [bank authorisation](#billing-requests-bank-authorisations) that was
         * used to verify this request.
         */
        public String getBankAuthorisation() {
            return bankAuthorisation;
        }

        /**
         * ID of the associated [creditor](#core-endpoints-creditors).
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the [customer](#core-endpoints-customers) that will be used for this request
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * (Optional) ID of the [customer_bank_account](#core-endpoints-customer-bank-accounts) that
         * will be used for this request
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * ID of the customer billing detail that will be used for this request
         */
        public String getCustomerBillingDetail() {
            return customerBillingDetail;
        }

        /**
         * (Optional) ID of the associated mandate request
         */
        public String getMandateRequest() {
            return mandateRequest;
        }

        /**
         * (Optional) ID of the [mandate](#core-endpoints-mandates) that was created from this
         * mandate request. this mandate request.
         */
        public String getMandateRequestMandate() {
            return mandateRequestMandate;
        }

        /**
         * (Optional) ID of the associated payment request
         */
        public String getPaymentRequest() {
            return paymentRequest;
        }

        /**
         * (Optional) ID of the [payment](#core-endpoints-payments) that was created from this
         * payment request.
         */
        public String getPaymentRequestPayment() {
            return paymentRequestPayment;
        }
    }

    /**
     * Represents a mandate request resource returned from the API.
     *
     * Request for a mandate
     */
    public static class MandateRequest {
        private MandateRequest() {
            // blank to prevent instantiation
        }

        private String currency;
        private Links links;
        private String scheme;
        private Verify verify;

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public String getCurrency() {
            return currency;
        }

        public Links getLinks() {
            return links;
        }

        /**
         * A Direct Debit scheme. Currently "ach", "bacs", "becs", "becs_nz", "betalingsservice",
         * "pad" and "sepa_core" are supported.
         */
        public String getScheme() {
            return scheme;
        }

        /**
         * Verification preference for the mandate. One of:
         * <ul>
         * <li>`minimum`: only verify if absolutely required, such as when part of scheme rules</li>
         * <li>`recommended`: in addition to minimum, use the GoCardless risk engine to decide an
         * appropriate level of verification</li>
         * <li>`when_available`: if verification mechanisms are available, use them</li>
         * <li>`always`: as `when_available`, but fail to create the Billing Request if a mechanism
         * isn't available</li>
         * </ul>
         * 
         * If not provided, the `recommended` level is chosen.
         */
        public Verify getVerify() {
            return verify;
        }

        public enum Verify {
            @SerializedName("minimum")
            MINIMUM, @SerializedName("recommended")
            RECOMMENDED, @SerializedName("when_available")
            WHEN_AVAILABLE, @SerializedName("always")
            ALWAYS, @SerializedName("unknown")
            UNKNOWN
        }

        public static class Links {
            private Links() {
                // blank to prevent instantiation
            }

            private String mandate;

            /**
             * (Optional) ID of the [mandate](#core-endpoints-mandates) that was created from this
             * mandate request. this mandate request.
             * 
             */
            public String getMandate() {
                return mandate;
            }
        }
    }

    /**
     * Represents a payment request resource returned from the API.
     *
     * Request for a one-off strongly authorised payment
     */
    public static class PaymentRequest {
        private PaymentRequest() {
            // blank to prevent instantiation
        }

        private Integer amount;
        private Integer appFee;
        private String currency;
        private String description;
        private Links links;
        private String scheme;

        /**
         * Amount in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public Integer getAmount() {
            return amount;
        }

        /**
         * The amount to be deducted from the payment as an app fee, to be paid to the partner
         * integration which created the billing request, in the lowest denomination for the
         * currency (e.g. pence in GBP, cents in EUR).
         */
        public Integer getAppFee() {
            return appFee;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * A human-readable description of the payment. This will be displayed to the payer when
         * authorising the billing request.
         * 
         */
        public String getDescription() {
            return description;
        }

        public Links getLinks() {
            return links;
        }

        /**
         * A Direct Debit scheme. Currently "ach", "bacs", "becs", "becs_nz", "betalingsservice",
         * "pad" and "sepa_core" are supported.
         */
        public String getScheme() {
            return scheme;
        }

        public static class Links {
            private Links() {
                // blank to prevent instantiation
            }

            private String payment;

            /**
             * (Optional) ID of the [payment](#core-endpoints-payments) that was created from this
             * payment request.
             */
            public String getPayment() {
                return payment;
            }
        }
    }

    public static class Resources {
        private Resources() {
            // blank to prevent instantiation
        }

        private Customer customer;
        private CustomerBankAccount customerBankAccount;
        private CustomerBillingDetail customerBillingDetail;

        /**
         * Embedded customer
         */
        public Customer getCustomer() {
            return customer;
        }

        /**
         * Embedded customer bank account, only if a bank account is linked
         */
        public CustomerBankAccount getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * Embedded customer billing detail
         */
        public CustomerBillingDetail getCustomerBillingDetail() {
            return customerBillingDetail;
        }

        /**
         * Represents a customer resource returned from the API.
         *
         * Embedded customer
         */
        public static class Customer {
            private Customer() {
                // blank to prevent instantiation
            }

            private String companyName;
            private String createdAt;
            private String email;
            private String familyName;
            private String givenName;
            private String id;
            private String language;
            private Map<String, String> metadata;
            private String phoneNumber;

            /**
             * Customer's company name. Required unless a `given_name` and `family_name` are
             * provided. For Canadian customers, the use of a `company_name` value will mean that
             * any mandate created from this customer will be considered to be a "Business PAD"
             * (otherwise, any mandate will be considered to be a "Personal PAD").
             */
            public String getCompanyName() {
                return companyName;
            }

            /**
             * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
             * created.
             */
            public String getCreatedAt() {
                return createdAt;
            }

            /**
             * Customer's email address. Required in most cases, as this allows GoCardless to send
             * notifications to this customer.
             */
            public String getEmail() {
                return email;
            }

            /**
             * Customer's surname. Required unless a `company_name` is provided.
             */
            public String getFamilyName() {
                return familyName;
            }

            /**
             * Customer's first name. Required unless a `company_name` is provided.
             */
            public String getGivenName() {
                return givenName;
            }

            /**
             * Unique identifier, beginning with "CU".
             */
            public String getId() {
                return id;
            }

            /**
             * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the
             * language for notification emails sent by GoCardless if your organisation does not
             * send its own (see [compliance requirements](#appendix-compliance-requirements)).
             * Currently only "en", "fr", "de", "pt", "es", "it", "nl", "da", "nb", "sl", "sv" are
             * supported. If this is not provided, the language will be chosen based on the
             * `country_code` (if supplied) or default to "en".
             */
            public String getLanguage() {
                return language;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, String> getMetadata() {
                return metadata;
            }

            /**
             * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including
             * country code.
             */
            public String getPhoneNumber() {
                return phoneNumber;
            }
        }

        /**
         * Represents a customer bank account resource returned from the API.
         *
         * Embedded customer bank account, only if a bank account is linked
         */
        public static class CustomerBankAccount {
            private CustomerBankAccount() {
                // blank to prevent instantiation
            }

            private String accountHolderName;
            private String accountNumberEnding;
            private AccountType accountType;
            private String bankName;
            private String countryCode;
            private String createdAt;
            private String currency;
            private Boolean enabled;
            private String id;
            private Links links;
            private Map<String, String> metadata;

            /**
             * Name of the account holder, as known by the bank. Usually this is the same as the
             * name stored with the linked [creditor](#core-endpoints-creditors). This field will be
             * transliterated, upcased and truncated to 18 characters. This field is required unless
             * the request includes a [customer bank account
             * token](#javascript-flow-customer-bank-account-tokens).
             */
            public String getAccountHolderName() {
                return accountHolderName;
            }

            /**
             * The last few digits of the account number. Currently 4 digits for NZD bank accounts
             * and 2 digits for other currencies.
             */
            public String getAccountNumberEnding() {
                return accountNumberEnding;
            }

            /**
             * Bank account type. Required for USD-denominated bank accounts. Must not be provided
             * for bank accounts in other currencies. See [local
             * details](#local-bank-details-united-states) for more information.
             */
            public AccountType getAccountType() {
                return accountType;
            }

            /**
             * Name of bank, taken from the bank details.
             */
            public String getBankName() {
                return bankName;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements).
             * Defaults to the country code of the `iban` if supplied, otherwise is required.
             */
            public String getCountryCode() {
                return countryCode;
            }

            /**
             * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
             * created.
             */
            public String getCreatedAt() {
                return createdAt;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * Boolean value showing whether the bank account is enabled or disabled.
             */
            public Boolean getEnabled() {
                return enabled;
            }

            /**
             * Unique identifier, beginning with "BA".
             */
            public String getId() {
                return id;
            }

            public Links getLinks() {
                return links;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, String> getMetadata() {
                return metadata;
            }

            public enum AccountType {
                @SerializedName("savings")
                SAVINGS, @SerializedName("checking")
                CHECKING, @SerializedName("unknown")
                UNKNOWN
            }

            public static class Links {
                private Links() {
                    // blank to prevent instantiation
                }

                private String customer;

                /**
                 * ID of the [customer](#core-endpoints-customers) that owns this bank account.
                 */
                public String getCustomer() {
                    return customer;
                }
            }
        }

        /**
         * Represents a customer billing detail resource returned from the API.
         *
         * Embedded customer billing detail
         */
        public static class CustomerBillingDetail {
            private CustomerBillingDetail() {
                // blank to prevent instantiation
            }

            private String addressLine1;
            private String addressLine2;
            private String addressLine3;
            private String city;
            private String countryCode;
            private String createdAt;
            private String danishIdentityNumber;
            private String id;
            private String ipAddress;
            private String postalCode;
            private String region;
            private List<String> schemes;
            private String swedishIdentityNumber;

            /**
             * The first line of the customer's address.
             */
            public String getAddressLine1() {
                return addressLine1;
            }

            /**
             * The second line of the customer's address.
             */
            public String getAddressLine2() {
                return addressLine2;
            }

            /**
             * The third line of the customer's address.
             */
            public String getAddressLine3() {
                return addressLine3;
            }

            /**
             * The city of the customer's address.
             */
            public String getCity() {
                return city;
            }

            /**
             * [ISO 3166-1 alpha-2
             * code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
             */
            public String getCountryCode() {
                return countryCode;
            }

            /**
             * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
             * created.
             */
            public String getCreatedAt() {
                return createdAt;
            }

            /**
             * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
             * Must be supplied if the customer's bank account is denominated in Danish krone (DKK).
             */
            public String getDanishIdentityNumber() {
                return danishIdentityNumber;
            }

            /**
             * Unique identifier, beginning with "CU".
             */
            public String getId() {
                return id;
            }

            /**
             * For ACH customers only. Required for ACH customers. A string containing the IP
             * address of the payer to whom the mandate belongs (i.e. as a result of their
             * completion of a mandate setup flow in their browser).
             */
            public String getIpAddress() {
                return ipAddress;
            }

            /**
             * The customer's postal code.
             */
            public String getPostalCode() {
                return postalCode;
            }

            /**
             * The customer's address region, county or department. For US customers a 2 letter
             * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required
             * (e.g. `CA` for California).
             */
            public String getRegion() {
                return region;
            }

            /**
             * The schemes associated with this customer billing detail
             */
            public List<String> getSchemes() {
                return schemes;
            }

            /**
             * For Swedish customers only. The civic/company number (personnummer,
             * samordningsnummer, or organisationsnummer) of the customer. Must be supplied if the
             * customer's bank account is denominated in Swedish krona (SEK). This field cannot be
             * changed once it has been set.
             */
            public String getSwedishIdentityNumber() {
                return swedishIdentityNumber;
            }
        }
    }
}
