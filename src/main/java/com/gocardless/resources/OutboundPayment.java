package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a outbound payment resource returned from the API.
 *
 * Outbound Payments represent payments sent from [creditors](#core-endpoints-creditors).
 * 
 * GoCardless will notify you via a [webhook](#appendix-webhooks) when the status of the outbound
 * payment [changes](#event-actions-outbound-payment).
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: Outbound Payments are currently in Early Access and available only
 * to a limited list of organisations. If you are interested in using this feature, please stay
 * tuned for our public launch announcement. We are actively testing and refining our API to ensure
 * it meets your needs and provides the best experience.
 * </p>
 */
public class OutboundPayment {
    private OutboundPayment() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String createdAt;
    private Currency currency;
    private String description;
    private String executionDate;
    private String id;
    private Boolean isWithdrawal;
    private Links links;
    private Map<String, Object> metadata;
    private String reference;
    private Scheme scheme;
    private Status status;
    private Verifications verifications;

    /**
     * Amount, in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when the outbound payment was
     * created.
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

    /**
     * A human-readable description of the outbound payment
     */
    public String getDescription() {
        return description;
    }

    /**
     * A future date on which the outbound payment should be sent. If not specified, the payment
     * will be sent as soon as possible.
     */
    public String getExecutionDate() {
        return executionDate;
    }

    /**
     * Unique identifier of the outbound payment.
     */
    public String getId() {
        return id;
    }

    /**
     * Indicates whether the outbound payment is a withdrawal to your verified business bank
     * account.
     */
    public Boolean getIsWithdrawal() {
        return isWithdrawal;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * An optional reference that will appear on your customer's bank statement. The character limit
     * for this reference is dependent on the scheme.<br />
     * <strong>Faster Payments</strong> - 18 characters, including:
     * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 &-./"<br />
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
     * One of:
     * <ul>
     * <li>`verifying`: The payment has been
     * [created](#outbound-payments-create-an-outbound-payment) and the verification process has
     * begun.</li>
     * <li>`pending_approval`: The payment is awaiting
     * [approval](#outbound-payments-approve-an-outbound-payment).</li>
     * <li>`scheduled`: The payment has passed verification &
     * [approval](#outbound-payments-approve-an-outbound-payment), but processing has not yet
     * begun.</li>
     * <li>`executing`: The execution date has arrived and the payment has been placed in queue for
     * processing.</li>
     * <li>`executed`: The payment has been accepted by the scheme and is now on its way to the
     * recipient.</li>
     * <li>`cancelled`: The payment has been
     * [cancelled](#outbound-payments-cancel-an-outbound-payment) or was not
     * [approved](#outbound-payments-approve-an-outbound-payment) on time.</li>
     * <li>`failed`: The payment was not sent, usually due to an error while or after
     * executing.</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Contains details of the verifications performed for the outbound payment
     */
    public Verifications getVerifications() {
        return verifications;
    }

    public enum Currency {
        @SerializedName("GBP")
        GBP, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Scheme {
        @SerializedName("faster_payments")
        FASTER_PAYMENTS, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("verifying")
        VERIFYING, @SerializedName("pending_approval")
        PENDING_APPROVAL, @SerializedName("scheduled")
        SCHEDULED, @SerializedName("executing")
        EXECUTING, @SerializedName("executed")
        EXECUTED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("failed")
        FAILED, @SerializedName("unknown")
        UNKNOWN
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String customer;
        private String recipientBankAccount;

        /**
         * ID of the creditor who sends the outbound payment.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the [customer](#core-endpoints-customers) that receives this outbound payment
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of the customer bank account which receives the outbound payment.
         */
        public String getRecipientBankAccount() {
            return recipientBankAccount;
        }
    }

    /**
     * Represents a verification resource returned from the API.
     *
     * Contains details of the verifications performed for the outbound payment
     */
    public static class Verifications {
        private Verifications() {
            // blank to prevent instantiation
        }

        private RecipientBankAccountHolderVerification recipientBankAccountHolderVerification;

        /**
         * Checks if the recipient owns the provided bank account
         */
        public RecipientBankAccountHolderVerification getRecipientBankAccountHolderVerification() {
            return recipientBankAccountHolderVerification;
        }

        /**
         * Represents a recipient bank account holder verification resource returned from the API.
         *
         * Checks if the recipient owns the provided bank account
         */
        public static class RecipientBankAccountHolderVerification {
            private RecipientBankAccountHolderVerification() {
                // blank to prevent instantiation
            }

            private String actualAccountName;
            private Result result;
            private Type type;

            /**
             * The actual account name returned by the recipient's bank, populated only in the case
             * of a partial match.
             */
            public String getActualAccountName() {
                return actualAccountName;
            }

            /**
             * Result of the verification, could be one of
             * <ul>
             * <li>`full_match`: The verification has confirmed that the account name exactly
             * matches the details provided.</li>
             * <li>`partial_match`: The verification has confirmed that the account name is similar
             * but does not match to the details provided.</li>
             * <li>`no_match`: The verification concludes the provided name does not match the
             * account details.</li>
             * <li>`unable_to_match`: The verification could not be performed due to recipient bank
             * issues or technical issues</li>
             * </ul>
             */
            public Result getResult() {
                return result;
            }

            /**
             * Type of the verification that has been performed eg. [Confirmation of
             * Payee](https://www.wearepay.uk/what-we-do/overlay-services/confirmation-of-payee/)
             */
            public Type getType() {
                return type;
            }

            public enum Result {
                @SerializedName("full_match")
                FULL_MATCH, @SerializedName("partial_match")
                PARTIAL_MATCH, @SerializedName("no_match")
                NO_MATCH, @SerializedName("unable_to_match")
                UNABLE_TO_MATCH, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Type {
                @SerializedName("confirmation_of_payee")
                CONFIRMATION_OF_PAYEE, @SerializedName("unknown")
                UNKNOWN
            }
        }
    }
}
