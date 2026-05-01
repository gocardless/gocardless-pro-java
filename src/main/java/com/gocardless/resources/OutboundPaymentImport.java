package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a outbound payment import resource returned from the API.
 *
 * Outbound Payment Imports allow you to create multiple payments via a single API call.
 * 
 * The Workflow: 1. Create the outbound payment import. 2. Retrieve an authorisation link from the
 * response. 3. Redirect the user to the link to authorise the import. 4. Once the user authorises
 * the import, the individual outbound payments are automatically submitted.
 * 
 * Import entries are not processed as actual payments until they are reviewed and authorised in
 * GoCardless Dashboard. Upon approval, a unique outbound payment is generated for every entry in
 * the import.
 * 
 * <p class="notice">
 * Outbound Payment Imports are capped at 1000 entries. If you expect to exceed this limit, please
 * create multiple smaller imports.
 * </p>
 */
public class OutboundPaymentImport {
    private OutboundPaymentImport() {
        // blank to prevent instantiation
    }

    private Integer amountSum;
    private String authorisationUrl;
    private String createdAt;
    private Currency currency;
    private EntryCounts entryCounts;
    private String id;
    private Links links;
    private Status status;

    /**
     * The sum of all import entry amounts, in the lowest denomination for the currency (e.g. pence
     * in GBP, cents in EUR).
     */
    public Integer getAmountSum() {
        return amountSum;
    }

    /**
     * The link to the GoCardless dashboard to review and authorise the import
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency. Currently only "GBP"
     * is supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    public EntryCounts getEntryCounts() {
        return entryCounts;
    }

    /**
     * Unique identifier, beginning with "IM".
     */
    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The status of the outbound payment import.
     * <ul>
     * <li>`created`: The initial state of a new import.</li>
     * <li>`validating`: Import validation in progress.</li>
     * <li>`invalid`: Import validation failed.</li>
     * <li>`valid`: Import validation succeeded.</li>
     * <li>`processing`: Authorisation received; payments are being generated.</li>
     * <li>`processed`: All entries have been successfully converted into outbound payments.</li>
     * <li>`cancelled`: The import was cancelled by a user or automatically expired by the
     * system.</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Currency {
        @SerializedName("GBP")
        GBP, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("created")
        CREATED, @SerializedName("validating")
        VALIDATING, @SerializedName("valid")
        VALID, @SerializedName("invalid")
        INVALID, @SerializedName("processing")
        PROCESSING, @SerializedName("processed")
        PROCESSED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a entry count resource returned from the API.
     *
     * 
     */
    public static class EntryCounts {
        private EntryCounts() {
            // blank to prevent instantiation
        }

        private Integer failedToProcess;
        private Integer invalid;
        private Integer processed;
        private Integer total;
        private Integer valid;
        private Integer verified;
        private Integer verifiedWithFullMatch;
        private Integer verifiedWithNoMatch;
        private Integer verifiedWithPartialMatch;
        private Integer verifiedWithUnableToMatch;

        /**
         * Count of entries that encountered a terminal error during the outbound payment generation
         * process.
         */
        public Integer getFailedToProcess() {
            return failedToProcess;
        }

        /**
         * Count of entries that failed validation checks.
         */
        public Integer getInvalid() {
            return invalid;
        }

        /**
         * Count of entries successfully converted into outbound payments after the import was
         * authorised.
         */
        public Integer getProcessed() {
            return processed;
        }

        /**
         * The total number of entries included in the import.
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * Count of entries that passed validation checks.
         */
        public Integer getValid() {
            return valid;
        }

        /**
         * Total count of entries checked against bank account holder verification services (e.g.,
         * CoP).
         */
        public Integer getVerified() {
            return verified;
        }

        /**
         * Count of entries where the account holder name was a direct match.
         */
        public Integer getVerifiedWithFullMatch() {
            return verifiedWithFullMatch;
        }

        /**
         * Count of entries where the account holder name did not match the records.
         */
        public Integer getVerifiedWithNoMatch() {
            return verifiedWithNoMatch;
        }

        /**
         * Count of entries where the account holder name was a close match.
         */
        public Integer getVerifiedWithPartialMatch() {
            return verifiedWithPartialMatch;
        }

        /**
         * Count of entries where the verification service could not return a definitive result.
         */
        public Integer getVerifiedWithUnableToMatch() {
            return verifiedWithUnableToMatch;
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

        private String creditor;

        /**
         * ID of the creditor who sends the outbound payments from the import.
         */
        public String getCreditor() {
            return creditor;
        }
    }
}
