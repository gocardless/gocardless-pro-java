package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a subscription resource returned from the API.
 *
 * Subscriptions create [payments](#core-endpoints-payments) according to a schedule.
 * 
 * ### Recurrence Rules
 * 
 * The following rules apply when specifying recurrence:
 * 
 * - The first payment must be charged within 1 year. - If `day_of_month` and `start_date` are not
 * provided `start_date` will be the [mandate](#core-endpoints-mandates)'s
 * `next_possible_charge_date` and the subscription will then recur based on the `interval` &
 * `interval_unit` - If `month` or `day_of_month` are present the following validations apply:
 * 
 * | __interval_unit__ | __month__ | __day_of_month__ | | :---------------- |
 * :--------------------------------------------- | :----------------------------------------- | |
 * yearly | optional (required if `day_of_month` provided) | optional (invalid if `month` not
 * provided) | | monthly | invalid | optional | | weekly | invalid | invalid |
 * 
 * Examples:
 * 
 * | __interval_unit__ | __interval__ | __month__ | __day_of_month__ | valid? | | :----------------
 * | :----------- | :-------- | :--------------- |
 * :------------------------------------------------- | | yearly | 1 | january | -1 | valid | |
 * monthly | 6 | | | valid | | monthly | 6 | | 12 | valid | | weekly | 2 | | | valid | | yearly | 1
 * | march | | invalid - missing `day_of_month` | | yearly | 1 | | 2 | invalid - missing `month` | |
 * monthly | 6 | august | 12 | invalid - `month` must be blank | | weekly | 2 | october | 10 |
 * invalid - `month` and `day_of_month` must be blank |
 * 
 * ### Rolling dates
 * 
 * When a charge date falls on a non-business day, one of two things will happen:
 * 
 * - if the recurrence rule specified `-1` as the `day_of_month`, the charge date will be rolled
 * __backwards__ to the previous business day (i.e., the last working day of the month). - otherwise
 * the charge date will be rolled __forwards__ to the next business day.
 * 
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
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to indicate
     * the last day of the month.
     */
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * The earliest date that will be used as a `charge_date` on payments created for this
     * subscription if it is resumed. Only present for `paused` subscriptions. This value will
     * change over time.
     */
    public String getEarliestChargeDateAfterResume() {
        return earliestChargeDateAfterResume;
    }

    /**
     * Date on or after which no further payments should be created. <br />
     * If this field is blank and `count` is not specified, the subscription will continue forever.
     * <br />
     * <p class="deprecated-notice">
     * <strong>Deprecated</strong>: This field will be removed in a future API version. Use `count`
     * to specify a number of payments instead.
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
     * Number of `interval_units` between customer charge dates. Must be greater than or equal to
     * `1`. Must result in at least one charge date per year. Defaults to `1`.
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
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
     * interval_unit is `yearly`.
     * 
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
     * An optional payment reference. This will be set as the reference on each payment created and
     * will appear on your customer's bank statement. See the documentation for the [create payment
     * endpoint](#payments-create-a-payment) for more details. <br />
     * <p class="restricted-notice">
     * <strong>Restricted</strong>: You need your own Service User Number to specify a payment
     * reference for Bacs payments.
     * </p>
     */
    public String getPaymentReference() {
        return paymentReference;
    }

    /**
     * On failure, automatically retry payments using [intelligent
     * retries](#success-intelligent-retries). Default is `false`.
     */
    public Boolean getRetryIfPossible() {
        return retryIfPossible;
    }

    /**
     * The date on which the first payment should be charged. Must be on or after the
     * [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When left blank and
     * `month` or `day_of_month` are provided, this will be set to the date of the first payment. If
     * created without `month` or `day_of_month` this will be set as the mandate's
     * `next_possible_charge_date`
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_customer_approval`: the subscription is waiting for customer approval before
     * becoming active</li>
     * <li>`customer_approval_denied`: the customer did not approve the subscription</li>
     * <li>`active`: the subscription is currently active and will continue to create payments</li>
     * <li>`finished`: all of the payments scheduled for creation under this subscription have been
     * created</li>
     * <li>`cancelled`: the subscription has been cancelled and will no longer create payments</li>
     * <li>`paused`: the subscription has been paused and will not create payments</li>
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

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the subscription will
         * create payments against.
         */
        public String getMandate() {
            return mandate;
        }
    }

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
