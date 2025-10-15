package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a instalment schedule resource returned from the API.
 *
 * Instalment schedules are objects which represent a collection of related payments, with the
 * intention to collect the `total_amount` specified. The API supports both schedule-based creation
 * (similar to subscriptions) as well as explicit selection of differing payment amounts and charge
 * dates.
 * 
 * Unlike subscriptions, the payments are created immediately, so the instalment schedule cannot be
 * modified once submitted and instead can only be cancelled (which will cancel any of the payments
 * which have not yet been submitted).
 * 
 * Customers will receive a single notification about the complete schedule of collection.
 * 
 */
public class InstalmentSchedule {
    private InstalmentSchedule() {
        // blank to prevent instantiation
    }

    private String id;
    private String createdAt;
    private Currency currency;
    private Map<String, Object> metadata;
    private Links links;
    private Integer totalAmount;
    private Map<String, Object> paymentErrors;
    private String name;
    private Status status;

    /**
     * Unique identifier, beginning with "IS".
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
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Links to associated objects
     */
    public Links getLinks() {
        return links;
    }

    /**
     * The total amount of the instalment schedule, defined as the sum of all individual payments,
     * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR). If the
     * requested payment amounts do not sum up correctly, a validation error will be returned.
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * If the status is `creation_failed`, this property will be populated with validation failures
     * from the individual payments, arranged by the index of the payment that failed.
     * 
     */
    public Map<String, Object> getPaymentErrors() {
        return paymentErrors;
    }

    /**
     * Name of the instalment schedule, up to 100 chars. This name will also be copied to the
     * payments of the instalment schedule if you use schedule-based creation.
     */
    public String getName() {
        return name;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending`: we're waiting for GC to create the payments</li>
     * <li>`active`: the payments have been created, and the schedule is active</li>
     * <li>`creation_failed`: payment creation failed</li>
     * <li>`completed`: we have passed the date of the final payment and all payments have been
     * collected</li>
     * <li>`cancelled`: the schedule has been cancelled</li>
     * <li>`errored`: one or more payments have failed</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("active")
        ACTIVE, @SerializedName("creation_failed")
        CREATION_FAILED, @SerializedName("completed")
        COMPLETED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("errored")
        ERRORED, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Currency {
        @SerializedName("AUD")
        AUD, @SerializedName("CAD")
        CAD, @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("NZD")
        NZD, @SerializedName("SEK")
        SEK, @SerializedName("USD")
        USD, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a link resource returned from the API.
     *
     * Links to associated objects
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;
        private String customer;
        private List<String> payments;

        /**
         * ID of the associated [mandate](#core-endpoints-mandates) which the instalment schedule
         * will create payments against.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * ID of the associated [customer](#core-endpoints-customers).
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * Array of IDs of the associated [payments](#core-endpoints-payments)
         */
        public List<String> getPayments() {
            return payments;
        }
    }
}
