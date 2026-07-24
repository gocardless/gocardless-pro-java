package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a outbound payment resource returned from the API.
 *
 * Outbound Payments represent payments sent from
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditors</a>.
 * 
 * GoCardless will notify you via a
 * <a href="https://developer.gocardless.com/api-reference/#appendix-webhooks">webhook</a> when the
 * status of the outbound payment <a href=
 * "https://developer.gocardless.com/api-reference/#event-types-outbound-payment">changes</a>.
 * 
 * <h4>Rate limiting</h4> Two rate limits apply to the Outbound Payments APIs:
 * 
 * <ul>
 * <li>All POST Outbound Payment endpoints (create, withdraw, approve, cancel and etc.) share a
 * single rate-limit group of 300 requests per minute. As initiating a payment typically requires
 * two API calls (one to create the payment and one to approve it), this allows you to add
 * approximately 150 outbound payments per minute.</li>
 * <li>All remaining Outbound Payment endpoints are limited to 500 requests per minute.</li>
 * </ul>
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
    private Map<String, String> metadata;
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
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when the outbound payment was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency.
     * Currently only "GBP" is supported.
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
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * An optional reference that will appear on your customer's bank statement. The character limit
     * for this reference is dependent on the scheme.<br>
     * </br>
     * <strong>Faster Payments</strong>
     * <ul>
     * <li>18 characters, including: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789
     * &amp;-./"</li>
     * </ul>
     * <br>
     * </br>
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
     * 
     * <ul>
     * <li><code>verifying</code>: The payment has been <a href=
     * "https://developer.gocardless.com/api-reference/#outbound-payments-create-an-outbound-payment">created</a>
     * and the verification process has begun.</li>
     * <li><code>pending_approval</code>: The payment is awaiting <a href=
     * "https://developer.gocardless.com/api-reference/#outbound-payments-approve-an-outbound-payment">approval</a>.</li>
     * <li><code>scheduled</code>: The payment has passed verification &amp; <a href=
     * "https://developer.gocardless.com/api-reference/#outbound-payments-approve-an-outbound-payment">approval</a>,
     * but processing has not yet begun.</li>
     * <li><code>executing</code>: The execution date has arrived and the payment has been placed in
     * queue for processing.</li>
     * <li><code>executed</code>: The payment has been accepted by the scheme and is now on its way
     * to the recipient.</li>
     * <li><code>cancelled</code>: The payment has been <a href=
     * "https://developer.gocardless.com/api-reference/#outbound-payments-cancel-an-outbound-payment">cancelled</a>
     * or was not <a href=
     * "https://developer.gocardless.com/api-reference/#outbound-payments-approve-an-outbound-payment">approved</a>
     * on time.</li>
     * <li><code>failed</code>: The payment was not sent, usually due to an error while or after
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
        private String customer;
        private String outboundPaymentImport;
        private String recipientBankAccount;

        /**
         * ID of the creditor who sends the outbound payment.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>
         * that receives this outbound payment
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of the outbound payment import that created this outbound payment.
         */
        public String getOutboundPaymentImport() {
            return outboundPaymentImport;
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
             * 
             * <ul>
             * <li><code>full_match</code>: The verification has confirmed that the account name
             * exactly matches the details provided.</li>
             * <li><code>partial_match</code>: The verification has confirmed that the account name
             * is similar but does not match to the details provided.</li>
             * <li><code>no_match</code>: The verification concludes the provided name does not
             * match the account details.</li>
             * <li><code>unable_to_match</code>: The verification could not be performed due to
             * recipient bank issues or technical issues</li>
             * </ul>
             */
            public Result getResult() {
                return result;
            }

            /**
             * Type of the verification that has been performed eg. <a href=
             * "https://www.wearepay.uk/what-we-do/overlay-services/confirmation-of-payee/">Confirmation
             * of Payee</a>
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
