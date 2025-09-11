package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a customer notification resource returned from the API.
 *
 * Customer Notifications represent the notification which is due to be sent to a customer after an
 * event has happened. The event, the resource and the customer to be notified are all identified in
 * the `links` property.
 * 
 * Note that these are ephemeral records - once the notification has been actioned in some way, it
 * is no longer visible using this API.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is currently only available for approved integrators -
 * please <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class CustomerNotification {
    private CustomerNotification() {
        // blank to prevent instantiation
    }

    private ActionTaken actionTaken;
    private String actionTakenAt;
    private String actionTakenBy;
    private String id;
    private Links links;
    private Type type;

    /**
     * The action that was taken on the notification. Currently this can only be `handled`, which
     * means the integrator sent the notification themselves.
     * 
     */
    public ActionTaken getActionTaken() {
        return actionTaken;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this action was taken.
     */
    public String getActionTakenAt() {
        return actionTakenAt;
    }

    /**
     * A string identifying the integrator who was able to handle this notification.
     */
    public String getActionTakenBy() {
        return actionTakenBy;
    }

    /**
     * The id of the notification.
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The type of notification the customer shall receive. One of:
     * <ul>
     * <li>`payment_created`</li>
     * <li>`payment_cancelled`</li>
     * <li>`mandate_created`</li>
     * <li>`mandate_blocked`</li>
     * <li>`subscription_created`</li>
     * <li>`subscription_cancelled`</li>
     * <li>`instalment_schedule_created`</li>
     * <li>`instalment_schedule_cancelled`</li>
     * </ul>
     */
    public Type getType() {
        return type;
    }

    public enum ActionTaken {
        @SerializedName("handled")
        HANDLED, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Type {
        @SerializedName("payment_created")
        PAYMENT_CREATED, @SerializedName("payment_cancelled")
        PAYMENT_CANCELLED, @SerializedName("mandate_created")
        MANDATE_CREATED, @SerializedName("mandate_blocked")
        MANDATE_BLOCKED, @SerializedName("subscription_created")
        SUBSCRIPTION_CREATED, @SerializedName("subscription_cancelled")
        SUBSCRIPTION_CANCELLED, @SerializedName("instalment_schedule_created")
        INSTALMENT_SCHEDULE_CREATED, @SerializedName("instalment_schedule_cancelled")
        INSTALMENT_SCHEDULE_CANCELLED, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String customer;
        private String event;
        private String mandate;
        private String payment;
        private String refund;
        private String subscription;

        /**
         * The customer who should be contacted with this notification.
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * The event that triggered the notification to be scheduled.
         */
        public String getEvent() {
            return event;
        }

        /**
         * The identifier of the related mandate.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * The identifier of the related payment.
         */
        public String getPayment() {
            return payment;
        }

        /**
         * The identifier of the related refund.
         */
        public String getRefund() {
            return refund;
        }

        /**
         * The identifier of the related subscription.
         */
        public String getSubscription() {
            return subscription;
        }
    }
}
