package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a event resource returned from the API.
 *
 * Events are stored for all webhooks. An event refers to a resource which has been updated, for
 * example a payment which has been collected, or a mandate which has been transferred. Event
 * creation is an asynchronous process, so it can take some time between an action occurring and its
 * corresponding event getting included in API responses. See [here](#event-actions) for a complete
 * list of event types.
 */
public class Event {
    private Event() {
        // blank to prevent instantiation
    }

    private String action;
    private List<CustomerNotification> customerNotifications;
    private Map<String, Object> metadata;
    private Links links;
    private String id;
    private String createdAt;
    private ResourceType resourceType;
    private Details details;
    private Map<String, Object> resourceMetadata;
    private Source source;

    /**
     * What has happened to the resource. See [Event Actions](#event-actions) for the possible
     * actions.
     */
    public String getAction() {
        return action;
    }

    /**
     * Present only in webhooks when an integrator is authorised to send their own notifications.
     * See [here](/getting-started/api/handling-customer-notifications/) for further information.
     * 
     */
    public List<CustomerNotification> getCustomerNotifications() {
        return customerNotifications;
    }

    /**
     * The metadata that was passed when making the API request that triggered the event (for
     * instance, cancelling a mandate).
     * 
     * This field will only be populated if the `details[origin]` field is `api` otherwise it will
     * be an empty object.
     * 
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
    * 
    */
    public Links getLinks() {
        return links;
    }

    /**
     * Unique identifier, beginning with "EV".
     */
    public String getId() {
        return id;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * The resource type for this event. One of:
     * <ul>
     * <li>`billing_requests`</li>
     * <li>`creditors`</li>
     * <li>`exports`</li>
     * <li>`instalment_schedules`</li>
     * <li>`mandates`</li>
     * <li>`payer_authorisations`</li>
     * <li>`payments`</li>
     * <li>`payouts`</li>
     * <li>`refunds`</li>
     * <li>`scheme_identifiers`</li>
     * <li>`subscriptions`</li>
     * <li>`outbound_payments`</li>
     * </ul>
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
    * 
    */
    public Details getDetails() {
        return details;
    }

    /**
     * The metadata of the resource that the event is for. For example, this field will have the
     * same value of the `mandate[metadata]` field on the response you would receive from performing
     * a GET request on a mandate.
     * 
     */
    public Map<String, Object> getResourceMetadata() {
        return resourceMetadata;
    }

    /**
     * Audit information about the source of the event.
     */
    public Source getSource() {
        return source;
    }

    public enum ResourceType {
        @SerializedName("billing_requests")
        BILLING_REQUESTS, @SerializedName("creditors")
        CREDITORS, @SerializedName("customers")
        CUSTOMERS, @SerializedName("exports")
        EXPORTS, @SerializedName("instalment_schedules")
        INSTALMENT_SCHEDULES, @SerializedName("mandates")
        MANDATES, @SerializedName("organisations")
        ORGANISATIONS, @SerializedName("outbound_payments")
        OUTBOUND_PAYMENTS, @SerializedName("payer_authorisations")
        PAYER_AUTHORISATIONS, @SerializedName("payments")
        PAYMENTS, @SerializedName("payouts")
        PAYOUTS, @SerializedName("refunds")
        REFUNDS, @SerializedName("scheme_identifiers")
        SCHEME_IDENTIFIERS, @SerializedName("subscriptions")
        SUBSCRIPTIONS, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a detail resource returned from the API.
     *
     * 
     */
    public static class Details {
        private Details() {
            // blank to prevent instantiation
        }

        private Origin origin;
        private String reasonCode;
        private String notRetriedReason;
        private String property;
        private Scheme scheme;
        private Boolean willAttemptRetry;
        private String description;
        private String cause;
        private String bankAccountId;
        private String currency;
        private Integer itemCount;

        /**
         * Who initiated the event. One of:
         * <ul>
         * <li>`bank`: this event was triggered by a report from the banks</li>
         * <li>`gocardless`: this event was performed by GoCardless automatically</li>
         * <li>`api`: this event was triggered by an API endpoint</li>
         * <li>`customer`: this event was triggered by a Customer</li>
         * <li>`payer`: this event was triggered by a Payer</li>
         * </ul>
         */
        public Origin getOrigin() {
            return origin;
        }

        /**
         * Set when a `bank` is the origin of the event. This is the reason code received in the
         * report from the customer's bank. See the [GoCardless Direct Debit
         * guide](https://gocardless.com/direct-debit/receiving-messages) for information on the
         * meanings of different reason codes. _Note:_ `reason_code` is payment scheme-specific and
         * can be inconsistent between banks.
         */
        public String getReasonCode() {
            return reasonCode;
        }

        /**
         * When will_attempt_retry is set to false, this field will contain the reason the payment
         * was not retried. This can be one of:
         * <ul>
         * <li>`failure_filter_applied`: The payment won't be intelligently retried as there is a
         * high likelihood of failure on retry.</li>
         * <li>`other`: The payment won't be intelligently retried due to any other reason.</li>
         * </ul>
         */
        public String getNotRetriedReason() {
            return notRetriedReason;
        }

        /**
         * When we send a creditor `creditor_updated` webhook, this tells you which property on the
         * creditor has been updated
         */
        public String getProperty() {
            return property;
        }

        /**
         * A bank payment scheme. Set when a bank is the origin of the event.
         */
        public Scheme getScheme() {
            return scheme;
        }

        /**
         * Whether the payment will be retried automatically. Set on a payment failed event.
         */
        public Boolean getWillAttemptRetry() {
            return willAttemptRetry;
        }

        /**
         * Human readable description of the cause. _Note:_ Changes to event descriptions are not
         * considered breaking.
         */
        public String getDescription() {
            return description;
        }

        /**
         * What triggered the event. _Note:_ `cause` is our simplified and predictable key
         * indicating what triggered the event.
         */
        public String getCause() {
            return cause;
        }

        /**
         * When we send a creditor `new_payout_currency_added` webhook, we also send the bank
         * account id of the new account
         */
        public String getBankAccountId() {
            return bankAccountId;
        }

        /**
         * When we send a creditor `new_payout_currency_added` webhook, we also send the currency of
         * the new account
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Count of rows in the csv. This is sent for export events
         */
        public Integer getItemCount() {
            return itemCount;
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
            SEPA_CORE, @SerializedName("sepa_cor1")
            SEPA_COR1, @SerializedName("unknown")
            UNKNOWN
        }

        public enum Origin {
            @SerializedName("bank")
            BANK, @SerializedName("api")
            API, @SerializedName("gocardless")
            GOCARDLESS, @SerializedName("customer")
            CUSTOMER, @SerializedName("payer")
            PAYER, @SerializedName("unknown")
            UNKNOWN
        }
    }

    /**
     * Represents a source resource returned from the API.
     *
     * Audit information about the source of the event.
     */
    public static class Source {
        private Source() {
            // blank to prevent instantiation
        }

        private Type type;
        private String name;

        /**
         * The type of the event's source.
         */
        public Type getType() {
            return type;
        }

        /**
         * The name of the event's source.
         */
        public String getName() {
            return name;
        }

        public enum Type {
            @SerializedName("app")
            APP, @SerializedName("user")
            USER, @SerializedName("gc_team")
            GC_TEAM, @SerializedName("access_token")
            ACCESS_TOKEN, @SerializedName("unknown")
            UNKNOWN
        }
    }

    /**
     * Represents a customer notification resource returned from the API.
     *
     * 
     */
    public static class CustomerNotification {
        private CustomerNotification() {
            // blank to prevent instantiation
        }

        private String id;
        private String type;
        private Boolean mandatory;
        private String deadline;

        /**
         * The id of the notification.
         */
        public String getId() {
            return id;
        }

        /**
         * See [here](#core-endpoints-customer-notifications) for a complete list of customer
         * notification types.
         */
        public String getType() {
            return type;
        }

        /**
         * Whether or not the notification must be sent.
         */
        public Boolean getMandatory() {
            return mandatory;
        }

        /**
         * Time after which GoCardless will send the notification by email.
         */
        public String getDeadline() {
            return deadline;
        }
    }

    /**
     * Represents a link resource returned from the API.
     *
     * 
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandateRequest;
        private String parentEvent;
        private String subscription;
        private String newMandate;
        private String bankAuthorisation;
        private String customerBankAccount;
        private String refund;
        private String organisation;
        private String customer;
        private String previousCustomerBankAccount;
        private String newCustomerBankAccount;
        private String billingRequestFlow;
        private String mandate;
        private String schemeIdentifier;
        private String paymentRequestPayment;
        private String payout;
        private String instalmentSchedule;
        private String billingRequest;
        private String mandateRequestMandate;
        private String creditor;
        private String payment;
        private String payerAuthorisation;

        /**
         * This is the id of the mandate request associated to this event
         */
        public String getMandateRequest() {
            return mandateRequest;
        }

        /**
         * If this event was caused by another, this is the ID of the cause. For example, if a
         * mandate is cancelled it automatically cancels all pending payments associated with it; in
         * this case, the payment cancellation events would have the ID of the mandate cancellation
         * event in this field.
         */
        public String getParentEvent() {
            return parentEvent;
        }

        /**
         * If `resource_type` is `subscription`, this is the ID of the
         * [subscription](#core-endpoints-subscriptions) which has been updated.
         */
        public String getSubscription() {
            return subscription;
        }

        /**
         * This is only included for mandate replaced events, when it is the ID of the new
         * [mandate](#core-endpoints-mandates) that replaces the existing mandate.
         */
        public String getNewMandate() {
            return newMandate;
        }

        /**
         * ID of a [bank authorisation](#billing-requests-bank-authorisations).
         */
        public String getBankAuthorisation() {
            return bankAuthorisation;
        }

        /**
         * ID of a [customer bank account](#core-endpoints-customer-bank-accounts).
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * If `resource_type` is `refunds`, this is the ID of the [refund](#core-endpoints-refunds)
         * which has been updated.
         */
        public String getRefund() {
            return refund;
        }

        /**
         * If the event is included in a [webhook](#webhooks-overview) to an [OAuth
         * app](#appendix-oauth), this is the ID of the account to which it belongs.
         */
        public String getOrganisation() {
            return organisation;
        }

        /**
         * ID of a [customer](#core-endpoints-customers).
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the [customer
         * bank account](#core-endpoints-customer-bank-accounts) which the mandate is being
         * transferred from.
         */
        public String getPreviousCustomerBankAccount() {
            return previousCustomerBankAccount;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the [customer
         * bank account](#core-endpoints-customer-bank-accounts) which the mandate is being
         * transferred to.
         */
        public String getNewCustomerBankAccount() {
            return newCustomerBankAccount;
        }

        /**
         * ID of a [billing request flow](#billing-requests-billing-request-flows).
         */
        public String getBillingRequestFlow() {
            return billingRequestFlow;
        }

        /**
         * If `resource_type` is `mandates`, this is the ID of the
         * [mandate](#core-endpoints-mandates) which has been updated.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * If `resource_type` is `scheme_identifiers`, this is the ID of the
         * [scheme_identifier](#core-endpoints-scheme-identifiers) which has been updated.
         */
        public String getSchemeIdentifier() {
            return schemeIdentifier;
        }

        /**
         * If `resource_type` is `billing_requests`, this is the ID of the
         * [payment](#core-endpoints-payments) which has been created for Instant Bank Payment.
         */
        public String getPaymentRequestPayment() {
            return paymentRequestPayment;
        }

        /**
         * If `resource_type` is `payouts`, this is the ID of the [payout](#core-endpoints-payouts)
         * which has been updated.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * If `resource_type` is `instalment_schedule`, this is the ID of the [instalment
         * schedule](#core-endpoints-instalment-schedules) which has been updated.
         */
        public String getInstalmentSchedule() {
            return instalmentSchedule;
        }

        /**
         * ID of a [billing request](#billing-requests-billing-requests).
         */
        public String getBillingRequest() {
            return billingRequest;
        }

        /**
         * If `resource_type` is `billing_requests`, this is the ID of the
         * [mandate](#core-endpoints-mandates) which has been created.
         */
        public String getMandateRequestMandate() {
            return mandateRequestMandate;
        }

        /**
         * If `resource_type` is `creditor`, this is the ID of the
         * [creditor](#core-endpoints-creditors) which has been updated.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * If `resource_type` is `payments`, this is the ID of the
         * [payment](#core-endpoints-payments) which has been updated.
         */
        public String getPayment() {
            return payment;
        }

        /**
         * ID of a [payer authorisation](#core-endpoints-payer-authorisations).
         */
        public String getPayerAuthorisation() {
            return payerAuthorisation;
        }
    }
}
