package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a outbound payment import entry resource returned from the API.
 *
 * Import Entries are the individual rows of an outbound payment import, representing each payment
 * to be created.
 */
public class OutboundPaymentImportEntry {
    private OutboundPaymentImportEntry() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String createdAt;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String processedAt;
    private String reference;
    private Scheme scheme;
    private ValidationErrors validationErrors;
    private VerificationResult verificationResult;

    /**
     * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "IE".
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
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this entry was processed.
     */
    public String getProcessedAt() {
        return processedAt;
    }

    /**
     * An optional reference for the outbound payment.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Bank payment scheme to process the outbound payment. Currently only "faster_payments" (GBP)
     * is supported.
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * Per-field validation errors for this entry, keyed by resource type and then by field name.
     */
    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }

    /**
     * The result of bank account holder verification, if performed.
     */
    public VerificationResult getVerificationResult() {
        return verificationResult;
    }

    public enum Scheme {
        @SerializedName("faster_payments")
        FASTER_PAYMENTS, @SerializedName("unknown")
        UNKNOWN
    }

    public enum VerificationResult {
        @SerializedName("full_match")
        FULL_MATCH, @SerializedName("partial_match")
        PARTIAL_MATCH, @SerializedName("no_match")
        NO_MATCH, @SerializedName("unable_to_match")
        UNABLE_TO_MATCH, @SerializedName("unknown")
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

        private String outboundPayment;
        private String outboundPaymentImport;
        private String recipientBankAccount;

        /**
         * ID of the associated outbound payment, once the entry has been processed.
         */
        public String getOutboundPayment() {
            return outboundPayment;
        }

        /**
         * ID of the associated import.
         */
        public String getOutboundPaymentImport() {
            return outboundPaymentImport;
        }

        /**
         * ID of the recipient bank account.
         */
        public String getRecipientBankAccount() {
            return recipientBankAccount;
        }
    }

    /**
     * Represents a validation error resource returned from the API.
     *
     * Per-field validation errors for this entry, keyed by resource type and then by field name.
     */
    public static class ValidationErrors {
        private ValidationErrors() {
            // blank to prevent instantiation
        }

        private OutboundPayment outboundPayment;

        /**
         * Validation errors for the outbound payment fields.
         */
        public OutboundPayment getOutboundPayment() {
            return outboundPayment;
        }

        /**
         * Represents a outbound payment resource returned from the API.
         *
         * Validation errors for the outbound payment fields.
         */
        public static class OutboundPayment {
            private OutboundPayment() {
                // blank to prevent instantiation
            }

            private List<String> amount;
            private List<String> recipientBankAccount;
            private List<String> reference;
            private Scheme scheme;

            /**
             * Errors related to the amount field.
             */
            public List<String> getAmount() {
                return amount;
            }

            /**
             * Errors related to the recipient bank account.
             */
            public List<String> getRecipientBankAccount() {
                return recipientBankAccount;
            }

            /**
             * Errors related to the reference field.
             */
            public List<String> getReference() {
                return reference;
            }

            /**
             * Bank payment scheme to process the outbound payment. Currently only "faster_payments"
             * (GBP) is supported.
             */
            public Scheme getScheme() {
                return scheme;
            }

            public enum Scheme {
                @SerializedName("faster_payments")
                FASTER_PAYMENTS, @SerializedName("unknown")
                UNKNOWN
            }
        }
    }
}
