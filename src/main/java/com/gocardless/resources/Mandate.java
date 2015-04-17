package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a mandate resource returned from the API.
 *
 * Mandates represent the Direct Debit mandate with a
 * [customer](https://developer.gocardless.com/pro/#api-endpoints-customers).
 * 
 * GoCardless will
 * notify you via a [webhook](https://developer.gocardless.com/pro/#webhooks) whenever the status of
 * a mandate changes.
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
    private String reference;
    private String scheme;
    private Status status;

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "MD"
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
     * values up to 200 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * The earliest date a newly created payment for this mandate could be charged
     */
    public String getNextPossibleChargeDate() {
        return nextPossibleChargeDate;
    }

    /**
     * Unique 6 to 18 character reference. Can be supplied or auto-generated.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Direct Debit scheme to which this mandate and associated payments are submitted. Can be supplied
     * or automatically detected from the customer's bank account. Currently only "bacs" and "sepa_core"
     * are supported.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * One of:
     * <ul>
     * <li>`pending_submission`: the mandate has not yet been submitted to the
     * customer's bank</li>
     * <li>`submitted`: the mandate has been submitted to the customer's bank but
     * has not been processed yet</li>
     * <li>`active`: the mandate has been successfully set up by the
     * customer's bank</li>
     * <li>`failed`: the mandate could not be created</li>
     * <li>`cancelled`: the
     * mandate has been cancelled</li>
     * <li>`expired`: the mandate has expired due to dormancy</li>
     *
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Status {
        PENDING_SUBMISSION, SUBMITTED, ACTIVE, FAILED, CANCELLED, EXPIRED,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String customerBankAccount;

        /**
         * ID of the associated [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors).
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the associated [customer bank
         * account](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account) which the
         * mandate is created and submits payments against.
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }
    }
}
