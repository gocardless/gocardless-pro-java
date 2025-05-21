package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a billing request with action resource returned from the API.
 *
 * Billing Requests help create resources that require input or action from a customer. An example
 * of required input might be additional customer billing details, while an action would be asking a
 * customer to authorise a payment using their mobile banking app.
 * 
 * See [Billing Requests:
 * Overview](https://developer.gocardless.com/getting-started/billing-requests/overview/) for
 * how-to's, explanations and tutorials.
 */
public class BillingRequestWithAction {
    private BillingRequestWithAction() {
        // blank to prevent instantiation
    }

    private BankAuthorisations bankAuthorisations;
    private BillingRequests billingRequests;

    /**
     * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created
     * against a specific bank, usually the bank that provides the payer's account.
     * 
     * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing
     * Request Flows) to ensure we meet regulatory requirements for checkout flows.
     */
    public BankAuthorisations getBankAuthorisations() {
        return bankAuthorisations;
    }

    /**
     * Billing Requests help create resources that require input or action from a customer. An
     * example of required input might be additional customer billing details, while an action would
     * be asking a customer to authorise a payment using their mobile banking app.
     * 
     * See [Billing Requests:
     * Overview](https://developer.gocardless.com/getting-started/billing-requests/overview/) for
     * how-to's, explanations and tutorials.
     * <p class="notice">
     * <strong>Important</strong>: All properties associated with `subscription_request` and
     * `instalment_schedule_request` are only supported for ACH and PAD schemes.
     * </p>
     */
    public BillingRequests getBillingRequests() {
        return billingRequests;
    }

    /**
     * Represents a bank authorisation resource returned from the API.
     *
     * Bank Authorisations can be used to authorise Billing Requests. Authorisations are created
     * against a specific bank, usually the bank that provides the payer's account.
     * 
     * Creation of Bank Authorisations is only permitted from GoCardless hosted UIs (see Billing
     * Request Flows) to ensure we meet regulatory requirements for checkout flows.
     */
    public static class BankAuthorisations {
        private BankAuthorisations() {
            // blank to prevent instantiation
        }

        private AuthorisationType authorisationType;
        private String authorisedAt;
        private String createdAt;
        private String expiresAt;
        private String id;
        private String lastVisitedAt;
        private Links links;
        private String qrCodeUrl;
        private String redirectUri;
        private String url;

        /**
         * Type of authorisation, can be either 'mandate' or 'payment'.
         */
        public AuthorisationType getAuthorisationType() {
            return authorisationType;
        }

        /**
         * Fixed [timestamp](#api-usage-dates-and-times), recording when the user has been
         * authorised.
         */
        public String getAuthorisedAt() {
            return authorisedAt;
        }

        /**
         * Timestamp when the flow was created
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * Timestamp when the url will expire. Each authorisation url currently lasts for 15
         * minutes, but this can vary by bank.
         */
        public String getExpiresAt() {
            return expiresAt;
        }

        /**
         * Unique identifier, beginning with "BAU".
         */
        public String getId() {
            return id;
        }

        /**
         * Fixed [timestamp](#api-usage-dates-and-times), recording when the authorisation URL has
         * been visited.
         */
        public String getLastVisitedAt() {
            return lastVisitedAt;
        }

        public Links getLinks() {
            return links;
        }

        /**
         * URL to a QR code PNG image of the bank authorisation url. This QR code can be used as an
         * alternative to providing the `url` to the payer to allow them to authorise with their
         * mobile devices.
         */
        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        /**
         * URL that the payer can be redirected to after authorising the payment.
         * 
         * On completion of bank authorisation, the query parameter of either `outcome=success` or
         * `outcome=failure` will be appended to the `redirect_uri` to indicate the result of the
         * bank authorisation. If the bank authorisation is expired, the query parameter
         * `outcome=timeout` will be appended to the `redirect_uri`, in which case you should prompt
         * the user to try the bank authorisation step again.
         * 
         * Please note: bank authorisations can still fail despite an `outcome=success` on the
         * `redirect_uri`. It is therefore recommended to wait for the relevant bank authorisation
         * event, such as
         * [`BANK_AUTHORISATION_AUTHORISED`](#billing-request-bankauthorisationauthorised),
         * [`BANK_AUTHORISATION_DENIED`](#billing-request-bankauthorisationdenied), or
         * [`BANK_AUTHORISATION_FAILED`](#billing-request-bankauthorisationfailed) in order to show
         * the correct outcome to the user.
         * 
         * The BillingRequestFlow ID will also be appended to the `redirect_uri` as query parameter
         * `id=BRF123`.
         * 
         * Defaults to `https://pay.gocardless.com/billing/static/thankyou`.
         */
        public String getRedirectUri() {
            return redirectUri;
        }

        /**
         * URL for an oauth flow that will allow the user to authorise the payment
         */
        public String getUrl() {
            return url;
        }

        public enum AuthorisationType {
            @SerializedName("mandate")
            MANDATE, @SerializedName("payment")
            PAYMENT, @SerializedName("unknown")
            UNKNOWN
        }

        public static class Links {
            private Links() {
                // blank to prevent instantiation
            }

            private String billingRequest;
            private String institution;

            /**
             * ID of the [billing request](#billing-requests-billing-requests) against which this
             * authorisation was created.
             */
            public String getBillingRequest() {
                return billingRequest;
            }

            /**
             * ID of the [institution](#billing-requests-institutions) against which this
             * authorisation was created.
             */
            public String getInstitution() {
                return institution;
            }
        }
    }

    /**
     * Represents a billing request resource returned from the API.
     *
     * Billing Requests help create resources that require input or action from a customer. An
     * example of required input might be additional customer billing details, while an action would
     * be asking a customer to authorise a payment using their mobile banking app.
     * 
     * See [Billing Requests:
     * Overview](https://developer.gocardless.com/getting-started/billing-requests/overview/) for
     * how-to's, explanations and tutorials.
     * <p class="notice">
     * <strong>Important</strong>: All properties associated with `subscription_request` and
     * `instalment_schedule_request` are only supported for ACH and PAD schemes.
     * </p>
     */
    public static class BillingRequests {
        private BillingRequests() {
            // blank to prevent instantiation
        }

        private List<Action> actions;
        private String createdAt;
        private Boolean fallbackEnabled;
        private Boolean fallbackOccurred;
        private String id;
        private InstalmentScheduleRequest instalmentScheduleRequest;
        private Links links;
        private MandateRequest mandateRequest;
        private Map<String, Object> metadata;
        private PaymentRequest paymentRequest;
        private PurposeCode purposeCode;
        private Resources resources;
        private Status status;
        private SubscriptionRequest subscriptionRequest;

        /**
         * List of actions that can be performed before this billing request can be fulfilled.
         */
        public List<Action> getActions() {
            return actions;
        }

        /**
         * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * (Optional) If true, this billing request can fallback from instant payment to direct
         * debit. Should not be set if GoCardless payment intelligence feature is used.
         * 
         * See [Billing Requests: Retain customers with
         * Fallbacks](https://developer.gocardless.com/billing-requests/retain-customers-with-fallbacks/)
         * for more information.
         */
        public Boolean getFallbackEnabled() {
            return fallbackEnabled;
        }

        /**
         * True if the billing request was completed with direct debit.
         */
        public Boolean getFallbackOccurred() {
            return fallbackOccurred;
        }

        /**
         * Unique identifier, beginning with "BRQ".
         */
        public String getId() {
            return id;
        }

        /**
         * Request for an instalment schedule. Has to contain either `instalments_with_schedule`
         * object or an array of `instalments_with_dates` objects
         */
        public InstalmentScheduleRequest getInstalmentScheduleRequest() {
            return instalmentScheduleRequest;
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
        public Map<String, Object> getMetadata() {
            return metadata;
        }

        /**
         * Request for a one-off strongly authorised payment
         */
        public PaymentRequest getPaymentRequest() {
            return paymentRequest;
        }

        /**
         * Specifies the high-level purpose of a mandate and/or payment using a set of pre-defined
         * categories. Required for the PayTo scheme, optional for all others. Currently `mortgage`,
         * `utility`, `loan`, `dependant_support`, `gambling`, `retail`, `salary`, `personal`,
         * `government`, `pension`, `tax` and `other` are supported.
         */
        public PurposeCode getPurposeCode() {
            return purposeCode;
        }

        public Resources getResources() {
            return resources;
        }

        /**
         * One of:
         * <ul>
         * <li>`pending`: the billing request is pending and can be used</li>
         * <li>`ready_to_fulfil`: the billing request is ready to fulfil</li>
         * <li>`fulfilling`: the billing request is currently undergoing fulfilment</li>
         * <li>`fulfilled`: the billing request has been fulfilled and a payment created</li>
         * <li>`cancelled`: the billing request has been cancelled and cannot be used</li>
         * </ul>
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Request for a subscription
         */
        public SubscriptionRequest getSubscriptionRequest() {
            return subscriptionRequest;
        }

        public enum PurposeCode {
            @SerializedName("mortgage")
            MORTGAGE, @SerializedName("utility")
            UTILITY, @SerializedName("loan")
            LOAN, @SerializedName("dependant_support")
            DEPENDANT_SUPPORT, @SerializedName("gambling")
            GAMBLING, @SerializedName("retail")
            RETAIL, @SerializedName("salary")
            SALARY, @SerializedName("personal")
            PERSONAL, @SerializedName("government")
            GOVERNMENT, @SerializedName("pension")
            PENSION, @SerializedName("tax")
            TAX, @SerializedName("other")
            OTHER, @SerializedName("unknown")
            UNKNOWN
        }

        public enum Status {
            @SerializedName("pending")
            PENDING, @SerializedName("ready_to_fulfil")
            READY_TO_FULFIL, @SerializedName("fulfilling")
            FULFILLING, @SerializedName("fulfilled")
            FULFILLED, @SerializedName("cancelled")
            CANCELLED, @SerializedName("unknown")
            UNKNOWN
        }

        public static class Action {
            private Action() {
                // blank to prevent instantiation
            }

            private List<String> availableCurrencies;
            private BankAuthorisation bankAuthorisation;
            private CollectCustomerDetails collectCustomerDetails;
            private List<String> completesActions;
            private InstitutionGuessStatus institutionGuessStatus;
            private Boolean required;
            private List<String> requiresActions;
            private Status status;
            private Type type;

            /**
             * List of currencies the current mandate supports
             */
            public List<String> getAvailableCurrencies() {
                return availableCurrencies;
            }

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
             * Describes whether we inferred the institution from the provided bank account details.
             * One of: - `not_needed`: we won't attempt to infer the institution as it is not
             * needed. Either because it was manually selected or the billing request does not
             * support this feature - `pending`: we are waiting on the bank details in order to
             * infer the institution - `failed`: we weren't able to infer the institution -
             * `success`: we inferred the institution and added it to the resources of a Billing
             * Request
             * 
             */
            public InstitutionGuessStatus getInstitutionGuessStatus() {
                return institutionGuessStatus;
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

            public enum InstitutionGuessStatus {
                @SerializedName("not_needed")
                NOT_NEEDED, @SerializedName("pending")
                PENDING, @SerializedName("failed")
                FAILED, @SerializedName("success")
                SUCCESS, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Status {
                @SerializedName("pending")
                PENDING, @SerializedName("completed")
                COMPLETED, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Type {
                @SerializedName("choose_currency")
                CHOOSE_CURRENCY, @SerializedName("collect_amount")
                COLLECT_AMOUNT, @SerializedName("collect_customer_details")
                COLLECT_CUSTOMER_DETAILS, @SerializedName("collect_bank_account")
                COLLECT_BANK_ACCOUNT, @SerializedName("bank_authorisation")
                BANK_AUTHORISATION, @SerializedName("confirm_payer_details")
                CONFIRM_PAYER_DETAILS, @SerializedName("select_institution")
                SELECT_INSTITUTION, @SerializedName("unknown")
                UNKNOWN
            }

            /**
             * Represents a available currency resource returned from the API.
             *
             * List of currencies the current mandate supports
             */
            public static class AvailableCurrencies {
                private AvailableCurrencies() {
                    // blank to prevent instantiation
                }

                private String currency;

                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
                 */
                public String getCurrency() {
                    return currency;
                }
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

                /**
                 * Which authorisation adapter will be used to power these authorisations
                 * (GoCardless internal use only)
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

                public enum Adapter {
                    @SerializedName("open_banking_gateway_pis")
                    OPEN_BANKING_GATEWAY_PIS, @SerializedName("plaid_ais")
                    PLAID_AIS, @SerializedName("open_banking_gateway_ais")
                    OPEN_BANKING_GATEWAY_AIS, @SerializedName("bankid_ais")
                    BANKID_AIS, @SerializedName("bank_pay_recurring")
                    BANK_PAY_RECURRING, @SerializedName("unknown")
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
                private IncompleteFields incompleteFields;

                /**
                 * Default customer country code, as determined by scheme and payer location
                 */
                public String getDefaultCountryCode() {
                    return defaultCountryCode;
                }

                public IncompleteFields getIncompleteFields() {
                    return incompleteFields;
                }

                public static class IncompleteFields {
                    private IncompleteFields() {
                        // blank to prevent instantiation
                    }

                    private List<String> customer;
                    private List<String> customerBillingDetail;

                    public List<String> getCustomer() {
                        return customer;
                    }

                    public List<String> getCustomerBillingDetail() {
                        return customerBillingDetail;
                    }
                }
            }
        }

        /**
         * Represents a instalment schedule request resource returned from the API.
         *
         * Request for an instalment schedule. Has to contain either `instalments_with_schedule`
         * object or an array of `instalments_with_dates` objects
         */
        public static class InstalmentScheduleRequest {
            private InstalmentScheduleRequest() {
                // blank to prevent instantiation
            }

            private Integer appFee;
            private String currency;
            private List<InstalmentsWithDate> instalmentsWithDates;
            private InstalmentsWithSchedule instalmentsWithSchedule;
            private Links links;
            private Map<String, Object> metadata;
            private String name;
            private String paymentReference;
            private Boolean retryIfPossible;
            private Integer totalAmount;

            /**
             * The amount to be deducted from each payment as an app fee, to be paid to the partner
             * integration which created the subscription, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             */
            public Integer getAppFee() {
                return appFee;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "USD" and "CAD" are supported.
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * An explicit array of instalment payments, each specifying at least an `amount` and
             * `charge_date`. See [create (with dates)](#instalment-schedules-create-with-dates)
             */
            public List<InstalmentsWithDate> getInstalmentsWithDates() {
                return instalmentsWithDates;
            }

            /**
             * Frequency of the payments you want to create, together with an array of payment
             * amounts to be collected, with a specified start date for the first payment. See
             * [create (with schedule)](#instalment-schedules-create-with-schedule)
             * 
             */
            public InstalmentsWithSchedule getInstalmentsWithSchedule() {
                return instalmentsWithSchedule;
            }

            public Links getLinks() {
                return links;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, Object> getMetadata() {
                return metadata;
            }

            /**
             * Name of the instalment schedule, up to 100 chars. This name will also be copied to
             * the payments of the instalment schedule if you use schedule-based creation.
             */
            public String getName() {
                return name;
            }

            /**
             * An optional payment reference. This will be set as the reference on each payment
             * created and will appear on your customer's bank statement. See the documentation for
             * the [create payment endpoint](#payments-create-a-payment) for more details. <br />
             */
            public String getPaymentReference() {
                return paymentReference;
            }

            /**
             * On failure, automatically retry payments using [intelligent
             * retries](#success-intelligent-retries). Default is `false`.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
             * </p>
             */
            public Boolean getRetryIfPossible() {
                return retryIfPossible;
            }

            /**
             * The total amount of the instalment schedule, defined as the sum of all individual
             * payments, in the lowest denomination for the currency (e.g. pence in GBP, cents in
             * EUR). If the requested payment amounts do not sum up correctly, a validation error
             * will be returned.
             */
            public Integer getTotalAmount() {
                return totalAmount;
            }

            public static class InstalmentsWithDate {
                private InstalmentsWithDate() {
                    // blank to prevent instantiation
                }

                private Integer amount;
                private String chargeDate;
                private String description;

                /**
                 * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in
                 * EUR).
                 */
                public Integer getAmount() {
                    return amount;
                }

                /**
                 * A future date on which the payment should be collected. If the date is before the
                 * next_possible_charge_date on the [mandate](#core-endpoints-mandates), it will be
                 * automatically rolled forwards to that date.
                 */
                public String getChargeDate() {
                    return chargeDate;
                }

                /**
                 * A human-readable description of the payment. This will be included in the
                 * notification email GoCardless sends to your customer if your organisation does
                 * not send its own notifications (see [compliance
                 * requirements](#appendix-compliance-requirements)).
                 */
                public String getDescription() {
                    return description;
                }
            }

            /**
             * Represents a instalments with schedule resource returned from the API.
             *
             * Frequency of the payments you want to create, together with an array of payment
             * amounts to be collected, with a specified start date for the first payment. See
             * [create (with schedule)](#instalment-schedules-create-with-schedule)
             * 
             */
            public static class InstalmentsWithSchedule {
                private InstalmentsWithSchedule() {
                    // blank to prevent instantiation
                }

                private List<Integer> amounts;
                private Integer interval;
                private IntervalUnit intervalUnit;
                private String startDate;

                /**
                 * List of amounts of each instalment, in the lowest denomination for the currency
                 * (e.g. cents in USD).
                 * 
                 */
                public List<Integer> getAmounts() {
                    return amounts;
                }

                /**
                 * Number of `interval_units` between charge dates. Must be greater than or equal to
                 * `1`.
                 * 
                 */
                public Integer getInterval() {
                    return interval;
                }

                /**
                 * The unit of time between customer charge dates. One of `weekly`, `monthly` or
                 * `yearly`.
                 */
                public IntervalUnit getIntervalUnit() {
                    return intervalUnit;
                }

                /**
                 * The date on which the first payment should be charged. Must be on or after the
                 * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When left
                 * blank and `month` or `day_of_month` are provided, this will be set to the date of
                 * the first payment. If created without `month` or `day_of_month` this will be set
                 * as the mandate's `next_possible_charge_date`
                 */
                public String getStartDate() {
                    return startDate;
                }

                public enum IntervalUnit {
                    @SerializedName("weekly")
                    WEEKLY, @SerializedName("monthly")
                    MONTHLY, @SerializedName("yearly")
                    YEARLY, @SerializedName("unknown")
                    UNKNOWN
                }
            }

            public static class Links {
                private Links() {
                    // blank to prevent instantiation
                }

                private String instalmentSchedule;

                /**
                 * (Optional) ID of the [instalment_schedule](#core-endpoints-instalment-schedules)
                 * that was created from this instalment schedule request.
                 * 
                 */
                public String getInstalmentSchedule() {
                    return instalmentSchedule;
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
            private String instalmentScheduleRequest;
            private String instalmentScheduleRequestInstalmentSchedule;
            private String mandateRequest;
            private String mandateRequestMandate;
            private String organisation;
            private String paymentProvider;
            private String paymentRequest;
            private String paymentRequestPayment;
            private String subscriptionRequest;
            private String subscriptionRequestSubscription;

            /**
             * (Optional) ID of the [bank authorisation](#billing-requests-bank-authorisations) that
             * was used to verify this request.
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
             * (Optional) ID of the [customer_bank_account](#core-endpoints-customer-bank-accounts)
             * that will be used for this request
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
             * (Optional) ID of the associated instalment schedule request
             */
            public String getInstalmentScheduleRequest() {
                return instalmentScheduleRequest;
            }

            /**
             * (Optional) ID of the [instalment_schedule](#core-endpoints-instalment-schedules) that
             * was created from this instalment schedule request.
             */
            public String getInstalmentScheduleRequestInstalmentSchedule() {
                return instalmentScheduleRequestInstalmentSchedule;
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
             * ID of the associated organisation.
             */
            public String getOrganisation() {
                return organisation;
            }

            /**
             * (Optional) ID of the associated payment provider
             */
            public String getPaymentProvider() {
                return paymentProvider;
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

            /**
             * (Optional) ID of the associated subscription request
             */
            public String getSubscriptionRequest() {
                return subscriptionRequest;
            }

            /**
             * (Optional) ID of the [subscription](#core-endpoints-subscriptions) that was created
             * from this subscription request.
             */
            public String getSubscriptionRequestSubscription() {
                return subscriptionRequestSubscription;
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

            private AuthorisationSource authorisationSource;
            private String consentType;
            private Constraints constraints;
            private String currency;
            private String description;
            private Links links;
            private Map<String, Object> metadata;
            private Boolean payerRequestedDualSignature;
            private String scheme;
            private Boolean sweeping;
            private Verify verify;

            /**
             * This field is ACH specific, sometimes referred to as [SEC
             * code](https://www.moderntreasury.com/learn/sec-codes).
             * 
             * This is the way that the payer gives authorisation to the merchant. web:
             * Authorisation is Internet Initiated or via Mobile Entry (maps to SEC code: WEB)
             * telephone: Authorisation is provided orally over telephone (maps to SEC code: TEL)
             * paper: Authorisation is provided in writing and signed, or similarly authenticated
             * (maps to SEC code: PPD)
             * 
             */
            public AuthorisationSource getAuthorisationSource() {
                return authorisationSource;
            }

            /**
             * This attribute represents the authorisation type between the payer and merchant. It
             * can be set to `one_off`, `recurring` or `standing` for ACH scheme. And `single`,
             * `recurring` and `sporadic` for PAD scheme. _Note:_ This is only supported for ACH and
             * PAD schemes.
             * 
             */
            public String getConsentType() {
                return consentType;
            }

            /**
             * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo
             * and VRP.
             */
            public Constraints getConstraints() {
                return constraints;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             * 
             */
            public String getDescription() {
                return description;
            }

            public Links getLinks() {
                return links;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, Object> getMetadata() {
                return metadata;
            }

            /**
             * This attribute can be set to true if the payer has indicated that multiple signatures
             * are required for the mandate. As long as every other Billing Request actions have
             * been completed, the payer will receive an email notification containing instructions
             * on how to complete the additional signature. The dual signature flow can only be
             * completed using GoCardless branded pages.
             */
            public Boolean getPayerRequestedDualSignature() {
                return payerRequestedDualSignature;
            }

            /**
             * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
             * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
             * Optional for mandate only requests - if left blank, the payer will be able to select
             * the currency/scheme to pay with from a list of your available schemes.
             */
            public String getScheme() {
                return scheme;
            }

            /**
             * If true, this billing request would be used to set up a mandate solely for moving (or
             * sweeping) money from one account owned by the payer to another account that the payer
             * also owns. This is required for Faster Payments
             */
            public Boolean getSweeping() {
                return sweeping;
            }

            /**
             * Verification preference for the mandate. One of:
             * <ul>
             * <li>`minimum`: only verify if absolutely required, such as when part of scheme
             * rules</li>
             * <li>`recommended`: in addition to `minimum`, use the GoCardless payment intelligence
             * solution to decide if a payer should be verified</li>
             * <li>`when_available`: if verification mechanisms are available, use them</li>
             * <li>`always`: as `when_available`, but fail to create the Billing Request if a
             * mechanism isn't available</li>
             * </ul>
             * 
             * By default, all Billing Requests use the `recommended` verification preference. It
             * uses GoCardless payment intelligence solution to determine if a payer is fraudulent
             * or not. The verification mechanism is based on the response and the payer may be
             * asked to verify themselves. If the feature is not available, `recommended` behaves
             * like `minimum`.
             * 
             * If you never wish to take advantage of our reduced risk products and Verified
             * Mandates as they are released in new schemes, please use the `minimum` verification
             * preference.
             * 
             * See [Billing Requests: Creating Verified
             * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
             * for more information.
             */
            public Verify getVerify() {
                return verify;
            }

            public enum AuthorisationSource {
                @SerializedName("web")
                WEB, @SerializedName("telephone")
                TELEPHONE, @SerializedName("paper")
                PAPER, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Verify {
                @SerializedName("minimum")
                MINIMUM, @SerializedName("recommended")
                RECOMMENDED, @SerializedName("when_available")
                WHEN_AVAILABLE, @SerializedName("always")
                ALWAYS, @SerializedName("unknown")
                UNKNOWN
            }

            /**
             * Represents a constraint resource returned from the API.
             *
             * Constraints that will apply to the mandate_request. (Optional) Specifically for PayTo
             * and VRP.
             */
            public static class Constraints {
                private Constraints() {
                    // blank to prevent instantiation
                }

                private String endDate;
                private Integer maxAmountPerPayment;
                private String paymentMethod;
                private List<PeriodicLimit> periodicLimits;
                private String startDate;

                /**
                 * The latest date at which payments can be taken, must occur after start_date if
                 * present
                 * 
                 * This is an optional field and if it is not supplied the agreement will be
                 * considered open and will not have an end date. Keep in mind the end date must
                 * take into account how long it will take the user to set up this agreement via the
                 * Billing Request.
                 * 
                 */
                public String getEndDate() {
                    return endDate;
                }

                /**
                 * The maximum amount that can be charged for a single payment. Required for VRP.
                 */
                public Integer getMaxAmountPerPayment() {
                    return maxAmountPerPayment;
                }

                /**
                 * A constraint where you can specify info (free text string) about how payments are
                 * calculated. _Note:_ This is only supported for ACH and PAD schemes.
                 * 
                 */
                public String getPaymentMethod() {
                    return paymentMethod;
                }

                /**
                 * List of periodic limits and constraints which apply to them
                 */
                public List<PeriodicLimit> getPeriodicLimits() {
                    return periodicLimits;
                }

                /**
                 * The date from which payments can be taken.
                 * 
                 * This is an optional field and if it is not supplied the start date will be set to
                 * the day authorisation happens.
                 * 
                 */
                public String getStartDate() {
                    return startDate;
                }

                public static class PeriodicLimit {
                    private PeriodicLimit() {
                        // blank to prevent instantiation
                    }

                    private Alignment alignment;
                    private Integer maxPayments;
                    private Integer maxTotalAmount;
                    private Period period;

                    /**
                     * The alignment of the period.
                     * 
                     * `calendar` - this will finish on the end of the current period. For example
                     * this will expire on the Monday for the current week or the January for the
                     * next year.
                     * 
                     * `creation_date` - this will finish on the next instance of the current
                     * period. For example Monthly it will expire on the same day of the next month,
                     * or yearly the same day of the next year.
                     * 
                     */
                    public Alignment getAlignment() {
                        return alignment;
                    }

                    /**
                     * (Optional) The maximum number of payments that can be collected in this
                     * periodic limit.
                     */
                    public Integer getMaxPayments() {
                        return maxPayments;
                    }

                    /**
                     * The maximum total amount that can be charged for all payments in this
                     * periodic limit. Required for VRP.
                     * 
                     */
                    public Integer getMaxTotalAmount() {
                        return maxTotalAmount;
                    }

                    /**
                     * The repeating period for this mandate
                     */
                    public Period getPeriod() {
                        return period;
                    }

                    public enum Alignment {
                        @SerializedName("calendar")
                        CALENDAR, @SerializedName("creation_date")
                        CREATION_DATE, @SerializedName("unknown")
                        UNKNOWN
                    }

                    public enum Period {
                        @SerializedName("day")
                        DAY, @SerializedName("week")
                        WEEK, @SerializedName("month")
                        MONTH, @SerializedName("year")
                        YEAR, @SerializedName("flexible")
                        FLEXIBLE, @SerializedName("unknown")
                        UNKNOWN
                    }
                }
            }

            public static class Links {
                private Links() {
                    // blank to prevent instantiation
                }

                private String mandate;

                /**
                 * (Optional) ID of the [mandate](#core-endpoints-mandates) that was created from
                 * this mandate request. this mandate request.
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
            private FundsSettlement fundsSettlement;
            private Links links;
            private Map<String, Object> metadata;
            private String reference;
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
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP`
             * and `EUR` supported; `GBP` with your customers in the UK and for `EUR` with your
             * customers in supported Eurozone countries only.
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * A human-readable description of the payment and/or mandate. This will be displayed to
             * the payer when authorising the billing request.
             * 
             */
            public String getDescription() {
                return description;
            }

            /**
             * This field will decide how GoCardless handles settlement of funds from the customer.
             * 
             * - `managed` will be moved through GoCardless' account, batched, and payed out. -
             * `direct` will be a direct transfer from the payer's account to the merchant where
             * invoicing will be handled separately.
             * 
             */
            public FundsSettlement getFundsSettlement() {
                return fundsSettlement;
            }

            public Links getLinks() {
                return links;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, Object> getMetadata() {
                return metadata;
            }

            /**
             * A custom payment reference defined by the merchant. It is only available for payments
             * using the Direct Funds settlement model on the Faster Payments scheme.
             * 
             */
            public String getReference() {
                return reference;
            }

            /**
             * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is
             * supported in the UK (GBP) and `sepa_credit_transfer` and
             * `sepa_instant_credit_transfer` are supported in supported Eurozone countries (EUR).
             * For Eurozone countries, `sepa_credit_transfer` is used as the default. Please be
             * aware that `sepa_instant_credit_transfer` may incur an additional fee for your
             * customer.
             */
            public String getScheme() {
                return scheme;
            }

            public enum FundsSettlement {
                @SerializedName("managed")
                MANAGED, @SerializedName("direct")
                DIRECT, @SerializedName("unknown")
                UNKNOWN
            }

            public static class Links {
                private Links() {
                    // blank to prevent instantiation
                }

                private String payment;

                /**
                 * (Optional) ID of the [payment](#core-endpoints-payments) that was created from
                 * this payment request.
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
                private Map<String, Object> metadata;
                private String phoneNumber;

                /**
                 * Customer's company name. Required unless a `given_name` and `family_name` are
                 * provided. For Canadian customers, the use of a `company_name` value will mean
                 * that any mandate created from this customer will be considered to be a "Business
                 * PAD" (otherwise, any mandate will be considered to be a "Personal PAD").
                 */
                public String getCompanyName() {
                    return companyName;
                }

                /**
                 * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was
                 * created.
                 */
                public String getCreatedAt() {
                    return createdAt;
                }

                /**
                 * Customer's email address. Required in most cases, as this allows GoCardless to
                 * send notifications to this customer.
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
                 * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as
                 * the language for notification emails sent by GoCardless if your organisation does
                 * not send its own (see [compliance
                 * requirements](#appendix-compliance-requirements)). Currently only "en", "fr",
                 * "de", "pt", "es", "it", "nl", "da", "nb", "sl", "sv" are supported. If this is
                 * not provided, the language will be chosen based on the `country_code` (if
                 * supplied) or default to "en".
                 */
                public String getLanguage() {
                    return language;
                }

                /**
                 * Key-value store of custom data. Up to 3 keys are permitted, with key names up to
                 * 50 characters and values up to 500 characters.
                 */
                public Map<String, Object> getMetadata() {
                    return metadata;
                }

                /**
                 * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number,
                 * including country code.
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
                private String bankAccountToken;
                private String bankName;
                private String countryCode;
                private String createdAt;
                private String currency;
                private Boolean enabled;
                private String id;
                private Links links;
                private Map<String, Object> metadata;

                /**
                 * Name of the account holder, as known by the bank. This field will be
                 * transliterated, upcased and truncated to 18 characters. This field is required
                 * unless the request includes a [customer bank account
                 * token](#javascript-flow-customer-bank-account-tokens).
                 */
                public String getAccountHolderName() {
                    return accountHolderName;
                }

                /**
                 * The last few digits of the account number. Currently 4 digits for NZD bank
                 * accounts and 2 digits for other currencies.
                 */
                public String getAccountNumberEnding() {
                    return accountNumberEnding;
                }

                /**
                 * Bank account type. Required for USD-denominated bank accounts. Must not be
                 * provided for bank accounts in other currencies. See [local
                 * details](#local-bank-details-united-states) for more information.
                 */
                public AccountType getAccountType() {
                    return accountType;
                }

                /**
                 * A token to uniquely refer to a set of bank account details. This feature is still
                 * in early access and is only available for certain organisations.
                 */
                public String getBankAccountToken() {
                    return bankAccountToken;
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
                 * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was
                 * created.
                 */
                public String getCreatedAt() {
                    return createdAt;
                }

                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
                 * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are
                 * supported.
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
                 * Key-value store of custom data. Up to 3 keys are permitted, with key names up to
                 * 50 characters and values up to 500 characters.
                 */
                public Map<String, Object> getMetadata() {
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
                 * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was
                 * created.
                 */
                public String getCreatedAt() {
                    return createdAt;
                }

                /**
                 * For Danish customers only. The civic/company number (CPR or CVR) of the customer.
                 * Must be supplied if the customer's bank account is denominated in Danish krone
                 * (DKK).
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
                 * 
                 * Not required for creating offline mandates where `authorisation_source` is set to
                 * telephone or paper.
                 * 
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
                 * [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is
                 * required (e.g. `CA` for California).
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
                 * samordningsnummer, or organisationsnummer) of the customer. Must be supplied if
                 * the customer's bank account is denominated in Swedish krona (SEK). This field
                 * cannot be changed once it has been set.
                 */
                public String getSwedishIdentityNumber() {
                    return swedishIdentityNumber;
                }
            }
        }

        /**
         * Represents a subscription request resource returned from the API.
         *
         * Request for a subscription
         */
        public static class SubscriptionRequest {
            private SubscriptionRequest() {
                // blank to prevent instantiation
            }

            private Integer amount;
            private Integer appFee;
            private Integer count;
            private String currency;
            private Integer dayOfMonth;
            private Integer interval;
            private IntervalUnit intervalUnit;
            private Links links;
            private Map<String, Object> metadata;
            private Month month;
            private String name;
            private String paymentReference;
            private Boolean retryIfPossible;
            private String startDate;

            /**
             * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             */
            public Integer getAmount() {
                return amount;
            }

            /**
             * The amount to be deducted from each payment as an app fee, to be paid to the partner
             * integration which created the subscription, in the lowest denomination for the
             * currency (e.g. pence in GBP, cents in EUR).
             */
            public Integer getAppFee() {
                return appFee;
            }

            /**
             * The total number of payments that should be taken by this subscription.
             */
            public Integer getCount() {
                return count;
            }

            /**
             * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
             * Currently "USD" and "CAD" are supported.
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to
             * indicate the last day of the month.
             */
            public Integer getDayOfMonth() {
                return dayOfMonth;
            }

            /**
             * Number of `interval_units` between customer charge dates. Must be greater than or
             * equal to `1`. Must result in at least one charge date per year. Defaults to `1`.
             */
            public Integer getInterval() {
                return interval;
            }

            /**
             * The unit of time between customer charge dates. One of `weekly`, `monthly` or
             * `yearly`.
             */
            public IntervalUnit getIntervalUnit() {
                return intervalUnit;
            }

            public Links getLinks() {
                return links;
            }

            /**
             * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
             * characters and values up to 500 characters.
             */
            public Map<String, Object> getMetadata() {
                return metadata;
            }

            /**
             * Name of the month on which to charge a customer. Must be lowercase. Only applies when
             * the interval_unit is `yearly`.
             * 
             */
            public Month getMonth() {
                return month;
            }

            /**
             * Optional name for the subscription. This will be set as the description on each
             * payment created. Must not exceed 255 characters.
             */
            public String getName() {
                return name;
            }

            /**
             * An optional payment reference. This will be set as the reference on each payment
             * created and will appear on your customer's bank statement. See the documentation for
             * the [create payment endpoint](#payments-create-a-payment) for more details. <br />
             */
            public String getPaymentReference() {
                return paymentReference;
            }

            /**
             * On failure, automatically retry payments using [intelligent
             * retries](#success-intelligent-retries). Default is `false`.
             * <p class="notice">
             * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to
             * be enabled in [GoCardless dashboard](https://manage.gocardless.com/success-plus).
             * </p>
             */
            public Boolean getRetryIfPossible() {
                return retryIfPossible;
            }

            /**
             * The date on which the first payment should be charged. If fulfilled after this date,
             * this will be set as the mandate's `next_possible_charge_date`. When left blank and
             * `month` or `day_of_month` are provided, this will be set to the date of the first
             * payment. If created without `month` or `day_of_month` this will be set as the
             * mandate's `next_possible_charge_date`.
             * 
             */
            public String getStartDate() {
                return startDate;
            }

            public enum IntervalUnit {
                @SerializedName("weekly")
                WEEKLY, @SerializedName("monthly")
                MONTHLY, @SerializedName("yearly")
                YEARLY, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Month {
                @SerializedName("january")
                JANUARY, @SerializedName("february")
                FEBRUARY, @SerializedName("march")
                MARCH, @SerializedName("april")
                APRIL, @SerializedName("may")
                MAY, @SerializedName("june")
                JUNE, @SerializedName("july")
                JULY, @SerializedName("august")
                AUGUST, @SerializedName("september")
                SEPTEMBER, @SerializedName("october")
                OCTOBER, @SerializedName("november")
                NOVEMBER, @SerializedName("december")
                DECEMBER, @SerializedName("unknown")
                UNKNOWN
            }

            public static class Links {
                private Links() {
                    // blank to prevent instantiation
                }

                private String subscription;

                /**
                 * (Optional) ID of the [subscription](#core-endpoints-subscriptions) that was
                 * created from this subscription request.
                 * 
                 */
                public String getSubscription() {
                    return subscription;
                }
            }
        }
    }
}
