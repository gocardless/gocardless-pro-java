package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a customer notification resource returned from the API.
 *
 * Customer Notifications represent the notification which is due to be sent to a customer after an
 * event has happened. The event, the resource and the customer to be notified are all identified in
 * the <code>links</code> property.
 * 
 * Only <code>payment_created</code>, <code>mandate_created</code> and
 * <code>subscription_created</code> notifications are supported.
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
     * The action that was taken on the notification. Currently this can only be
     * <code>handled</code>, which means the integrator sent the notification themselves.
     */
    public ActionTaken getActionTaken() {
        return actionTaken;
    }

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this action was taken.
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
     * The type of notification the customer shall receive.
     * 
     * Note: today, only <code>payment_created</code>, <code>mandate_created</code> and
     * <code>subscription_created</code> notifications are actually supported. The remaining values
     * are reserved for now.
     * 
     * Making a request for an event of any other type will get a <code>403</code>
     * <code>customer_notifications_notification_type_forbidden</code> error.
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

    /**
     * Represents a link resource returned from the API.
     *
     * 
     */
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
