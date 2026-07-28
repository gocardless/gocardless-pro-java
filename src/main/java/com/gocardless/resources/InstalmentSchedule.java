package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a instalment schedule resource returned from the API.
 *
 * Instalment schedules are objects which represent a collection of related payments, with the
 * intention to collect the <code>total_amount</code> specified. The API supports both
 * schedule-based creation (similar to subscriptions) as well as explicit selection of differing
 * payment amounts and charge dates.
 * 
 * Unlike subscriptions, the payments are created immediately, so the instalment schedule cannot be
 * modified once submitted and instead can only be cancelled (which will cancel any of the payments
 * which have not yet been submitted).
 * 
 * Customers will receive a single notification about the complete schedule of collection.
 */
public class InstalmentSchedule {
    private InstalmentSchedule() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Currency currency;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String name;
    private Map<String, Object> paymentErrors;
    private Status status;
    private Integer totalAmount;

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
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Unique identifier, beginning with "IS".
     */
    public String getId() {
        return id;
    }

    /**
     * Links to associated objects
     */
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
     * Name of the instalment schedule, up to 100 chars. This name will also be copied to the
     * payments of the instalment schedule if you use schedule-based creation.
     */
    public String getName() {
        return name;
    }

    /**
     * If the status is <code>creation_failed</code>, this property will be populated with
     * validation failures from the individual payments, arranged by the index of the payment that
     * failed.
     */
    public Map<String, Object> getPaymentErrors() {
        return paymentErrors;
    }

    /**
     * One of:
     * 
     * <ul>
     * <li><code>pending</code>: we're waiting for GC to create the payments</li>
     * <li><code>active</code>: the payments have been created, and the schedule is active</li>
     * <li><code>creation_failed</code>: payment creation failed</li>
     * <li><code>completed</code>: we have passed the date of the final payment and all payments
     * have been collected</li>
     * <li><code>cancelled</code>: the schedule has been cancelled</li>
     * <li><code>errored</code>: one or more payments have failed</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    /**
     * The total amount of the instalment schedule, defined as the sum of all individual payments,
     * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR). If the
     * requested payment amounts do not sum up correctly, a validation error will be returned.
     */
    public Integer getTotalAmount() {
        return totalAmount;
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

    /**
     * Represents a link resource returned from the API.
     *
     * Links to associated objects
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String customer;
        private String mandate;
        private List<String> payments;

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-mandates">mandate</a>
         * which the instalment schedule will create payments against.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * Array of IDs of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-payments">payments</a>
         */
        public List<String> getPayments() {
            return payments;
        }
    }
}
