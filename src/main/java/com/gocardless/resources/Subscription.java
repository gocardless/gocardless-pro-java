package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a subscription resource returned from the API.
 *
 * Subscriptions create
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-payments">payments</a>
 * according to a schedule.
 * 
 * <h3>Recurrence Rules</h3> The following rules apply when specifying recurrence:
 * 
 * <ul>
 * <li>If <code>day_of_month</code> and <code>start_date</code> are not provided
 * <code>start_date</code> will be the
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>'s
 * <code>next_possible_charge_date</code> and the subscription will then recur based on the
 * <code>interval</code> &amp; <code>interval_unit</code></li>
 * <li>If <code>month</code> or <code>day_of_month</code> are present the following validations
 * apply:</li>
 * </ul>
 * | <strong>interval_unit</strong> | <strong>month</strong> | <strong>day_of_month</strong> | |
 * :---------------- | :--------------------------------------------- |
 * :----------------------------------------- | | yearly | optional (required if
 * <code>day_of_month</code> provided) | optional (invalid if <code>month</code> not provided) | |
 * monthly | invalid | optional | | weekly | invalid | invalid |
 * 
 * Examples:
 * 
 * | <strong>interval_unit</strong> | <strong>interval</strong> | <strong>month</strong> |
 * <strong>day_of_month</strong> | valid? | | :---------------- | :----------- | :-------- |
 * :--------------- | :------------------------------------------------- | | yearly | 1 | january |
 * -1 | valid | | monthly | 6 | | | valid | | monthly | 6 | | 12 | valid | | weekly | 2 | | | valid
 * | | yearly | 1 | march | | invalid - missing <code>day_of_month</code> | | yearly | 1 | | 2 |
 * invalid - missing <code>month</code> | | monthly | 6 | august | 12 | invalid - <code>month</code>
 * must be blank | | weekly | 2 | october | 10 | invalid - <code>month</code> and
 * <code>day_of_month</code> must be blank |
 * 
 * <h3>Rolling dates</h3> When a charge date falls on a non-business day, one of two things will
 * happen:
 * 
 * <ul>
 * <li>if the recurrence rule specified <code>-1</code> as the <code>day_of_month</code>, the charge
 * date will be rolled <strong>backwards</strong> to the previous business day (i.e., the last
 * working day of the month).</li>
 * <li>otherwise the charge date will be rolled <strong>forwards</strong> to the next business
 * day.</li>
 * </ul>
 */
public class Subscription {
    private Subscription() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer appFee;
    private Integer count;
    private String createdAt;
    private String currency;
    private Integer dayOfMonth;
    private String earliestChargeDateAfterResume;
    private String endDate;
    private String id;
    private Integer interval;
    private IntervalUnit intervalUnit;
    private Links links;
    private Map<String, String> metadata;
    private Month month;
    private String name;
    private Boolean parentPlanPaused;
    private String paymentReference;
    private Boolean retryIfPossible;
    private String startDate;
    private Status status;
    private List<UpcomingPayment> upcomingPayments;

    /**
     * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * The amount to be deducted from each payment as an app fee, to be paid to the partner
     * integration which created the subscription, in the lowest denomination for the currency (e.g.
     * pence in GBP, cents in EUR).
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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * As per RFC 2445. The day of the month to charge customers on. <code>1</code>
     * <ul>
     * <li></li>
     * </ul>
     * <code>28</code> or <code>-1</code> to indicate the last day of the month.
     */
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * The earliest date that will be used as a <code>charge_date</code> on payments created for
     * this subscription if it is resumed. Only present for <code>paused</code> subscriptions. This
     * value will change over time.
     */
    public String getEarliestChargeDateAfterResume() {
        return earliestChargeDateAfterResume;
    }

    /**
     * Date on or after which no further payments should be created. <br>
     * </br>
     * If this field is blank and <code>count</code> is not specified, the subscription will
     * continue forever. <br>
     * </br>
     * 
     * <p class="deprecated-notice">
     * <strong>Deprecated</strong>: This field will be removed in a future API version. Use
     * <code>count</code> to specify a number of payments instead.
     * </p>
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Unique identifier, beginning with "SB".
     */
    public String getId() {
        return id;
    }

    /**
     * Number of <code>interval_units</code> between customer charge dates. Must be greater than or
     * equal to <code>1</code>. Must result in at least one charge date per year. Defaults to
     * <code>1</code>.
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * The unit of time between customer charge dates. One of <code>weekly</code>,
     * <code>monthly</code> or <code>yearly</code>.
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
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Name of the month on which to charge a customer. Must be lowercase. Only applies when the
     * interval_unit is <code>yearly</code>.
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Optional name for the subscription. This will be set as the description on each payment
     * created. Must not exceed 255 characters.
     */
    public String getName() {
        return name;
    }

    /**
     * Whether the parent plan of this subscription is paused.
     */
    public Boolean getParentPlanPaused() {
        return parentPlanPaused;
    }

    /**
     * An optional payment reference. This will be set as the reference on each payment created and
     * will appear on your customer's bank statement. See the documentation for the
     * <a href="https://developer.gocardless.com/api-reference/#payments-create-a-payment">create
     * payment endpoint</a> for more details. <br>
     * </br>
     * 
     * <p class="restricted-notice">
     * <strong>Restricted</strong>: You need your own Service User Number to specify a payment
     * reference for Bacs payments.
     * </p>
     */
    public String getPaymentReference() {
        return paymentReference;
    }

    /**
     * On failure, automatically retry payments using
     * <a href="https://developer.gocardless.com/success-plus/overview">intelligent retries</a>.
     * Default is <code>false</code>.
     * <p class="notice">
     * <strong>Important</strong>: To be able to use intelligent retries, Success+ needs to be
     * enabled in <a href="https://manage.gocardless.com/success-plus">GoCardless dashboard</a>.
     * </p>
     */
    public Boolean getRetryIfPossible() {
        return retryIfPossible;
    }

    /**
     * The date on which the first payment should be charged. Must be on or after the <a href=
     * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>'s
     * <code>next_possible_charge_date</code>. When left blank and <code>month</code> or
     * <code>day_of_month</code> are provided, this will be set to the date of the first payment. If
     * created without <code>month</code> or <code>day_of_month</code> this will be set as the
     * mandate's <code>next_possible_charge_date</code>
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * One of:
     * 
     * <ul>
     * <li><code>pending_customer_approval</code>: the subscription is waiting for customer approval
     * before becoming active</li>
     * <li><code>customer_approval_denied</code>: the customer did not approve the subscription</li>
     * <li><code>active</code>: the subscription is currently active and will continue to create
     * payments</li>
     * <li><code>finished</code>: all of the payments scheduled for creation under this subscription
     * have been created</li>
     * <li><code>cancelled</code>: the subscription has been cancelled and will no longer create
     * payments</li>
     * <li><code>paused</code>: the subscription has been paused and will not create payments</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Up to 10 upcoming payments with their amounts and charge dates.
     */
    public List<UpcomingPayment> getUpcomingPayments() {
        return upcomingPayments;
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

    public enum Status {
        @SerializedName("pending_customer_approval")
        PENDING_CUSTOMER_APPROVAL, @SerializedName("customer_approval_denied")
        CUSTOMER_APPROVAL_DENIED, @SerializedName("active")
        ACTIVE, @SerializedName("finished")
        FINISHED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("paused")
        PAUSED, @SerializedName("unknown")
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

        private String mandate;

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * which the subscription will create payments against.
         */
        public String getMandate() {
            return mandate;
        }
    }

    /**
     * Represents a upcoming payment resource returned from the API.
     *
     * 
     */
    public static class UpcomingPayment {
        private UpcomingPayment() {
            // blank to prevent instantiation
        }

        private Integer amount;
        private String chargeDate;

        /**
         * The amount of this payment, in minor unit (e.g. pence in GBP, cents in EUR).
         */
        public Integer getAmount() {
            return amount;
        }

        /**
         * The date on which this payment will be charged.
         */
        public String getChargeDate() {
            return chargeDate;
        }
    }
}
