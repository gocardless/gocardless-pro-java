package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a mandate resource returned from the API.
 *
 * Mandates represent the Direct Debit mandate with a [customer](#core-endpoints-customers).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) whenever the status of a mandate
 * changes.
 */
public class Mandate {
    private Mandate() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String nextPossibleChargeDate;
    private Boolean paymentsRequireApproval;
    private String reference;
    private String scheme;
    private Status status;

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates
     * created before 2016.
     */
    public String getId() {
        return id;
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
     * The earliest date that can be used as a `charge_date` on any newly created payment for this
     * mandate. This value will change over time.
     */
    public String getNextPossibleChargeDate() {
        return nextPossibleChargeDate;
    }

    /**
     * Boolean value showing whether payments and subscriptions under this mandate require approval
     * via an automated email before being processed.
     */
    public Boolean getPaymentsRequireApproval() {
        return paymentsRequireApproval;
    }

    /**
     * Unique reference. Different schemes have different length and [character
     * set](#appendix-character-sets) requirements. GoCardless will generate a unique reference
     * satisfying the different scheme requirements if this field is left blank.
     */
    public String getReference() {
        return reference;
    }

    /**
     * <a name="mandates_scheme"></a>Direct Debit scheme to which this mandate and associated
     * payments are submitted. Can be supplied or automatically detected from the customer's bank
     * account.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_customer_approval`: the mandate has not yet been signed by the second
     * customer</li>
     * <li>`pending_submission`: the mandate has not yet been submitted to the customer's bank</li>
     * <li>`submitted`: the mandate has been submitted to the customer's bank but has not been
     * processed yet</li>
     * <li>`active`: the mandate has been successfully set up by the customer's bank</li>
     * <li>`failed`: the mandate could not be created</li>
     * <li>`cancelled`: the mandate has been cancelled</li>
     * <li>`expired`: the mandate has expired due to dormancy</li>
     * <li>`consumed`: the mandate has been consumed and cannot be reused (note that this only
     * applies to schemes that are per-payment authorised)</li>
     * <li>`blocked`: the mandate has been blocked and payments cannot be created</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        @SerializedName("pending_customer_approval")
        PENDING_CUSTOMER_APPROVAL, @SerializedName("pending_submission")
        PENDING_SUBMISSION, @SerializedName("submitted")
        SUBMITTED, @SerializedName("active")
        ACTIVE, @SerializedName("failed")
        FAILED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("expired")
        EXPIRED, @SerializedName("consumed")
        CONSUMED, @SerializedName("blocked")
        BLOCKED, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String customer;
        private String customerBankAccount;
        private String newMandate;

        /**
         * ID of the associated [creditor](#core-endpoints-creditors).
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the associated [customer](#core-endpoints-customers)
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of the associated [customer bank account](#core-endpoints-customer-bank-accounts)
         * which the mandate is created and submits payments against.
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * ID of the new mandate if this mandate has been replaced.
         */
        public String getNewMandate() {
            return newMandate;
        }
    }
}
