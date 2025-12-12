package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a bank account holder verification resource returned from the API.
 *
 * Create a bank account holder verification for a bank account.
 */
public class BankAccountHolderVerification {
    private BankAccountHolderVerification() {
        // blank to prevent instantiation
    }

    private String actualAccountName;
    private String id;
    private Result result;
    private Status status;
    private Type type;

    /**
     * The actual account name returned by the recipient's bank, populated only in the case of a
     * partial match.
     */
    public String getActualAccountName() {
        return actualAccountName;
    }

    /**
     * The unique identifier for the bank account holder verification resource, e.g. "BAHV123".
     */
    public String getId() {
        return id;
    }

    /**
     * Result of the verification, could be one of
     * <ul>
     * <li>`full_match`: The verification has confirmed that the account name exactly matches the
     * details provided.</li>
     * <li>`partial_match`: The verification has confirmed that the account name is similar but does
     * not match to the details provided.</li>
     * <li>`no_match`: The verification concludes the provided name does not match the account
     * details.</li>
     * <li>`unable_to_match`: The verification could not be performed due to recipient bank issues
     * or technical issues</li>
     * </ul>
     */
    public Result getResult() {
        return result;
    }

    /**
     * The status of the bank account holder verification.
     * <ul>
     * <li>`pending`: We have triggered the verification, but the result has not come back yet.</li>
     * <li>`completed`: The verification is complete and is ready to be used.</li>
     * </ul>
     * 
     */
    public Status getStatus() {
        return status;
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

    public enum Status {
        @SerializedName("pending")
        PENDING, @SerializedName("completed")
        COMPLETED, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Type {
        @SerializedName("confirmation_of_payee")
        CONFIRMATION_OF_PAYEE, @SerializedName("unknown")
        UNKNOWN
    }
}
