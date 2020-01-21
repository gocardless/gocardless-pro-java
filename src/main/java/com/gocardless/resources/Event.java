package com.gocardless.resources;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a event resource returned from the API.
 *
 * Events are stored for all webhooks. An event refers to a resource which has been updated, for
 * example a payment which has been collected, or a mandate which has been transferred.
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
    private Map<String, String> metadata;
    private ResourceType resourceType;

    /**
     * What has happened to the resource.
     */
    public String getAction() {
        return action;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Present only in webhooks when an integrator is authorised to send their own
     * notifications. See [here](/getting-started/api/handling-customer-notifications/)
     * for further information.
     * 
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
     * If the `details[origin]` is `api`, this will contain any metadata you specified when triggering
     * this event. In other cases it will be an empty object.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * The resource type for this event. One of:
     * <ul>
     * <li>`payments`</li>
     * <li>`mandates`</li>
     * <li>`payouts`</li>
     * <li>`refunds`</li>
     * <li>`subscriptions`</li>
     * <li>`instalment_schedules`</li>
     * </ul>
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    public enum ResourceType {
        @SerializedName("payments")
        PAYMENTS, @SerializedName("mandates")
        MANDATES, @SerializedName("payouts")
        PAYOUTS, @SerializedName("refunds")
        REFUNDS, @SerializedName("subscriptions")
        SUBSCRIPTIONS, @SerializedName("instalment_schedules")
        INSTALMENT_SCHEDULES,
    }

    public static class CustomerNotification {
        private CustomerNotification() {
            // blank to prevent instantiation
        }

        private String deadline;
        private String id;
        private Boolean mandatory;
        private Type type;

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
         * The type of notification the customer shall receive.
         */
        public Type getType() {
            return type;
        }

        public enum Type {
            @SerializedName("payment_created")
            PAYMENT_CREATED, @SerializedName("mandate_created")
            MANDATE_CREATED, @SerializedName("subscription_created")
            SUBSCRIPTION_CREATED, @SerializedName("instalment_schedule_created")
            INSTALMENT_SCHEDULE_CREATED, @SerializedName("instalment_schedule_cancelled")
            INSTALMENT_SCHEDULE_CANCELLED,
        }
    }

    public static class Details {
        private Details() {
            // blank to prevent instantiation
        }

        private String cause;
        private String description;
        private Origin origin;
        private String reasonCode;
        private Scheme scheme;

        /**
         * What triggered the event. _Note:_ `cause` is our simplified and predictable key indicating what
         * triggered the event.
         */
        public String getCause() {
            return cause;
        }

        /**
         * Human readable description of the cause. _Note:_ Changes to event descriptions are not considered
         * breaking.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Who initiated the event. One of:
         * <ul>
         * <li>`bank`: this event was triggered by a report from the banks</li>
         * <li>`gocardless`: this event was performed by GoCardless automatically</li>
         * <li>`api`: this event was triggered by an API endpoint</li>
         * <li>`customer`: this event was triggered by a Customer</li>
         * </ul>
         */
        public Origin getOrigin() {
            return origin;
        }

        /**
         * Set when a `bank` is the origin of the event. This is the reason code received in the report from
         * the customer's bank. See the [GoCardless Direct Debit
         * guide](https://gocardless.com/direct-debit/receiving-messages) for information on the meanings of
         * different reason codes. _Note:_ `reason_code` is payment scheme-specific and can be inconsistent
         * between banks.
         */
        public String getReasonCode() {
            return reasonCode;
        }

        /**
         * A Direct Debit scheme. Set when a bank is the origin of the event.
         */
        public Scheme getScheme() {
            return scheme;
        }

        public enum Origin {
            @SerializedName("bank")
            BANK, @SerializedName("api")
            API, @SerializedName("gocardless")
            GOCARDLESS, @SerializedName("customer")
            CUSTOMER,
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
            SEPA_CORE, @SerializedName("sepa_cor1")
            SEPA_COR1,
        }
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String instalmentSchedule;
        private String mandate;
        private String newCustomerBankAccount;
        private String newMandate;
        private String organisation;
        private String parentEvent;
        private String payment;
        private String payout;
        private String previousCustomerBankAccount;
        private String refund;
        private String subscription;

        /**
         * If `resource_type` is `instalment_schedule`, this is the ID of the instalment schedule which has
         * been updated.
         */
        public String getInstalmentSchedule() {
            return instalmentSchedule;
        }

        /**
         * If `resource_type` is `mandates`, this is the ID of the [mandate](#core-endpoints-mandates) which
         * has been updated.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the [customer bank
         * account](#core-endpoints-customer-bank-accounts) which the mandate is being transferred to.
         */
        public String getNewCustomerBankAccount() {
            return newCustomerBankAccount;
        }

        /**
         * This is only included for mandate replaced events, when it is the ID of the new
         * [mandate](#core-endpoints-mandates) that replaces the existing mandate.
         */
        public String getNewMandate() {
            return newMandate;
        }

        /**
         * If the event is included in a [webhook](#webhooks-overview) to an [OAuth app](#appendix-oauth),
         * this is the ID of the account to which it belongs.
         */
        public String getOrganisation() {
            return organisation;
        }

        /**
         * If this event was caused by another, this is the ID of the cause. For example, if a mandate is
         * cancelled it automatically cancels all pending payments associated with it; in this case, the
         * payment cancellation events would have the ID of the mandate cancellation event in this field.
         */
        public String getParentEvent() {
            return parentEvent;
        }

        /**
         * If `resource_type` is `payments`, this is the ID of the [payment](#core-endpoints-payments) which
         * has been updated.
         */
        public String getPayment() {
            return payment;
        }

        /**
         * If `resource_type` is `payouts`, this is the ID of the [payout](#core-endpoints-payouts) which has
         * been updated.
         */
        public String getPayout() {
            return payout;
        }

        /**
         * This is only included for mandate transfer events, when it is the ID of the [customer bank
         * account](#core-endpoints-customer-bank-accounts) which the mandate is being transferred from.
         */
        public String getPreviousCustomerBankAccount() {
            return previousCustomerBankAccount;
        }

        /**
         * If `resource_type` is `refunds`, this is the ID of the [refund](#core-endpoints-refunds) which has
         * been updated.
         */
        public String getRefund() {
            return refund;
        }

        /**
         * If `resource_type` is `subscription`, this is the ID of the
         * [subscription](#core-endpoints-subscriptions) which has been updated.
         */
        public String getSubscription() {
            return subscription;
        }
    }
}
