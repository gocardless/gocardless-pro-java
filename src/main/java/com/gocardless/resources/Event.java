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
 * corresponding event getting included in API responses. See
 * <a href="https://developer.gocardless.com/api-reference/#event-types">here</a> for a complete
 * list of event types.
 * 
 * <p class="notice">
 * <strong>Important</strong>: Events older than 18 months will be archived and no longer accessible
 * via the API or exports. Archival will begin no sooner than 1 August 2026 in sandbox environments,
 * and no sooner than 1 October 2026 in live environments. Events within the 18-month window are
 * unaffected. If you need archived data, contact GoCardless support.
 * </p>
 */
public class Event {
    private Event() {
        // blank to prevent instantiation
    }

    private String action;
    private String createdAt;
    private List<CustomerNotification> customerNotifications;
    private Details details;
    private String id;
    private Links links;
    private Map<String, Object> metadata;
    private Map<String, Object> resourceMetadata;
    private ResourceType resourceType;
    private Source source;

    /**
     * What has happened to the resource. See
     * <a href="https://developer.gocardless.com/api-reference/#event-types">Event Types</a> for the
     * possible actions.
     */
    public String getAction() {
        return action;
    }

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Present only in webhooks when an integrator is authorised to send their own notifications.
     * See <a href=
     * "https://developer.gocardless.com/getting-started/api/handling-customer-notifications/">here</a>
     * for further information.
     */
    public List<CustomerNotification> getCustomerNotifications() {
        return customerNotifications;
    }

    public Details getDetails() {
        return details;
    }

    /**
     * Unique identifier, beginning with "EV".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The metadata that was passed when making the API request that triggered the event (for
     * instance, cancelling a mandate).
     * 
     * This field will only be populated if the <code>details[origin]</code> field is
     * <code>api</code> otherwise it will be an empty object.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * The metadata of the resource that the event is for. For example, this field will have the
     * same value of the <code>mandate[metadata]</code> field on the response you would receive from
     * performing a GET request on a mandate.
     */
    public Map<String, Object> getResourceMetadata() {
        return resourceMetadata;
    }

    /**
     * The resource type for this event. One of:
     * 
     * <ul>
     * <li><code>billing_requests</code></li>
     * <li><code>creditors</code></li>
     * <li><code>exports</code></li>
     * <li><code>instalment_schedules</code></li>
     * <li><code>mandates</code></li>
     * <li><code>payer_authorisations</code></li>
     * <li><code>payments</code></li>
     * <li><code>payouts</code></li>
     * <li><code>refunds</code></li>
     * <li><code>scheme_identifiers</code></li>
     * <li><code>subscriptions</code></li>
     * <li><code>outbound_payments</code></li>
     * <li><code>payment_account_transactions</code></li>
     * </ul>
     */
    public ResourceType getResourceType() {
        return resourceType;
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
        PAYMENTS, @SerializedName("payment_account_transactions")
        PAYMENT_ACCOUNT_TRANSACTIONS, @SerializedName("payouts")
        PAYOUTS, @SerializedName("refunds")
        REFUNDS, @SerializedName("scheme_identifiers")
        SCHEME_IDENTIFIERS, @SerializedName("subscriptions")
        SUBSCRIPTIONS, @SerializedName("unknown")
        UNKNOWN
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

        private String deadline;
        private String id;
        private Boolean mandatory;
        private String type;

        /**
         * Time after which GoCardless will send the notification by email.
         */
        public String getDeadline() {
            return deadline;
        }

        /**
         * The id of the notification.
         */
        public String getId() {
            return id;
        }

        /**
         * Whether or not the notification must be sent.
         */
        public Boolean getMandatory() {
            return mandatory;
        }

        /**
         * See <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-notifications">here</a>
         * for a complete list of customer notification types.
         */
        public String getType() {
            return type;
        }
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

        private String bankAccountId;
        private String cause;
        private String currency;
        private String description;
        private Integer itemCount;
        private String notRetriedReason;
        private Origin origin;
        private String property;
        private String reasonCode;
        private Scheme scheme;
        private Boolean willAttemptRetry;

        /**
         * When we send a creditor <code>new_payout_currency_added</code> webhook, we also send the
         * bank account id of the new account
         */
        public String getBankAccountId() {
            return bankAccountId;
        }

        /**
         * What triggered the event. <em>Note:</em> <code>cause</code> is our simplified and
         * predictable key indicating what triggered the event.
         */
        public String getCause() {
            return cause;
        }

        /**
         * When we send a creditor <code>new_payout_currency_added</code> webhook, we also send the
         * currency of the new account
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Human readable description of the cause. <em>Note:</em> Changes to event descriptions are
         * not considered breaking.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Count of rows in the csv. This is sent for export events
         */
        public Integer getItemCount() {
            return itemCount;
        }

        /**
         * When will_attempt_retry is set to false, this field will contain the reason the payment
         * was not retried. This can be one of:
         * 
         * <ul>
         * <li><code>failure_filter_applied</code>: The payment won't be intelligently retried as
         * there is a high likelihood of failure on retry.</li>
         * <li><code>other</code>: The payment won't be intelligently retried due to any other
         * reason.</li>
         * </ul>
         */
        public String getNotRetriedReason() {
            return notRetriedReason;
        }

        /**
         * Who initiated the event. One of:
         * 
         * <ul>
         * <li><code>bank</code>: this event was triggered by a report from the banks</li>
         * <li><code>gocardless</code>: this event was performed by GoCardless automatically</li>
         * <li><code>api</code>: this event was triggered by an API endpoint</li>
         * <li><code>customer</code>: this event was triggered by a Customer</li>
         * <li><code>payer</code>: this event was triggered by a Payer</li>
         * </ul>
         */
        public Origin getOrigin() {
            return origin;
        }

        /**
         * When we send a creditor <code>creditor_updated</code> webhook, this tells you which
         * property on the creditor has been updated
         */
        public String getProperty() {
            return property;
        }

        /**
         * Set when a <code>bank</code> is the origin of the event. This is the reason code received
         * in the report from the customer's bank. See the
         * <a href="https://gocardless.com/direct-debit/receiving-messages">GoCardless Direct Debit
         * guide</a> for information on the meanings of different reason codes. <em>Note:</em>
         * <code>reason_code</code> is payment scheme-specific and can be inconsistent between
         * banks.
         */
        public String getReasonCode() {
            return reasonCode;
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

        public enum Origin {
            @SerializedName("bank")
            BANK, @SerializedName("api")
            API, @SerializedName("gocardless")
            GOCARDLESS, @SerializedName("customer")
            CUSTOMER, @SerializedName("payer")
            PAYER, @SerializedName("unknown")
            UNKNOWN
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

        private String bankAuthorisation;
        private String billingRequest;
        private String billingRequestFlow;
        private String creditor;
        private String customer;
        private String customerBankAccount;
        private String instalmentSchedule;
        private String mandate;
        private String mandateRequest;
        private String mandateRequestMandate;
        private String newCustomerBankAccount;
        private String newMandate;
        private String organisation;
        private String outboundPayment;
        private String parentEvent;
        private String payerAuthorisation;
        private String payment;
        private String paymentAccountTransaction;
        private String paymentRequestPayment;
        private String payout;
        private String previousCustomerBankAccount;
        private String refund;
        private String schemeIdentifier;
        private String subscription;

        /**
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#billing-requests-bank-authorisations">bank
         * authorisation</a>.
         */
        public String getBankAuthorisation() {
            return bankAuthorisation;
        }

        /**
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#billing-requests-billing-requests">billing
         * request</a>.
         */
        public String getBillingRequest() {
            return billingRequest;
        }

        /**
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#billing-requests-billing-request-flows">billing
         * request flow</a>.
         */
        public String getBillingRequestFlow() {
            return billingRequestFlow;
        }

        /**
         * If <code>resource_type</code> is <code>creditor</code>, this is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>
         * which has been updated.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer
         * bank account</a>.
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * If <code>resource_type</code> is <code>instalment_schedule</code>, this is the ID of the
         * <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-instalment-schedules">instalment
         * schedule</a> which has been updated.
         */
        public String getInstalmentSchedule() {
            return instalmentSchedule;
        }

        /**
         * If <code>resource_type</code> is <code>mandates</code>, this is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * which has been updated.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * This is the id of the mandate request associated to this event
         */
        public String getMandateRequest() {
            return mandateRequest;
        }

        /**
         * If <code>resource_type</code> is <code>billing_requests</code>, this is the ID of the
         * <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * which has been created.
         */
        public String getMandateRequestMandate() {
            return mandateRequestMandate;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer
         * bank account</a> which the mandate is being transferred to.
         */
        public String getNewCustomerBankAccount() {
            return newCustomerBankAccount;
        }

        /**
         * This is only included for mandate replaced events, when it is the ID of the new <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * that replaces the existing mandate.
         */
        public String getNewMandate() {
            return newMandate;
        }

        /**
         * If the event is included in a
         * <a href="https://developer.gocardless.com/api-reference/#webhooks-overview">webhook</a>
         * to an <a href="https://developer.gocardless.com/api-reference/#appendix-oauth">OAuth
         * app</a>, this is the ID of the account to which it belongs.
         */
        public String getOrganisation() {
            return organisation;
        }

        /**
         * If <code>resource_type</code> is <code>outbound_payments</code>, this is the ID of the
         * outbound_payment which has been updated.
         */
        public String getOutboundPayment() {
            return outboundPayment;
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
         * ID of a <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payer-authorisations">payer
         * authorisation</a>.
         */
        public String getPayerAuthorisation() {
            return payerAuthorisation;
        }

        /**
         * If <code>resource_type</code> is <code>payments</code>, this is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payments">payment</a>
         * which has been updated.
         */
        public String getPayment() {
            return payment;
        }

        /**
         * If <code>resource_type</code> is <code>payment_account_transaction</code>, this is the ID
         * of a transaction which has been recorded on the payment account.
         */
        public String getPaymentAccountTransaction() {
            return paymentAccountTransaction;
        }

        /**
         * If <code>resource_type</code> is <code>billing_requests</code>, this is the ID of the
         * <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payments">payment</a>
         * which has been created for Pay by Bank.
         */
        public String getPaymentRequestPayment() {
            return paymentRequestPayment;
        }

        /**
         * If <code>resource_type</code> is <code>payouts</code>, this is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payouts">payout</a> which
         * has been updated.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer
         * bank account</a> which the mandate is being transferred from.
         */
        public String getPreviousCustomerBankAccount() {
            return previousCustomerBankAccount;
        }

        /**
         * If <code>resource_type</code> is <code>refunds</code>, this is the ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-refunds">refund</a> which
         * has been updated.
         */
        public String getRefund() {
            return refund;
        }

        /**
         * If <code>resource_type</code> is <code>scheme_identifiers</code>, this is the ID of the
         * <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-scheme-identifiers">scheme_identifier</a>
         * which has been updated.
         */
        public String getSchemeIdentifier() {
            return schemeIdentifier;
        }

        /**
         * If <code>resource_type</code> is <code>subscription</code>, this is the ID of the
         * <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-subscriptions">subscription</a>
         * which has been updated.
         */
        public String getSubscription() {
            return subscription;
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

        private String name;
        private Type type;

        /**
         * The name of the event's source.
         */
        public String getName() {
            return name;
        }

        /**
         * The type of the event's source.
         */
        public Type getType() {
            return type;
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
}
