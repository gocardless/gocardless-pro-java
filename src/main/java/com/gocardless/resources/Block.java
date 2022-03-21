package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a block resource returned from the API.
 *
 * Blocks are created to prevent certain customer details from being used when creating mandates.
 * 
 * The details used to create blocks can be exact matches, like a bank account or an email, or a
 * more generic match such as an email domain. New block types may be added over time.
 * 
 * A block object is in essence a simple rule that is used to match against details in a newly
 * created mandate. If there is a successful match then the mandate is transitioned to a "blocked"
 * state.
 * 
 * Payments and subscriptions cannot be created against a mandate in blocked state.
 * 
 * A mandate can never be transitioned out of the blocked state.
 * 
 * <p class="notice">
 * This API is currently only available for approved integrators - please
 * <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class Block {
    private Block() {
        // blank to prevent instantiation
    }

    private Boolean active;
    private BlockType blockType;
    private String createdAt;
    private String id;
    private String reasonDescription;
    private ReasonType reasonType;
    private String resourceReference;
    private String updatedAt;

    /**
     * Shows if the block is active or disabled. Only active blocks will be used when deciding if a
     * mandate should be blocked.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Type of entity we will seek to match against when blocking the mandate. This can currently be
     * one of 'email', 'email_domain', or 'bank_account'.
     */
    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "BLC".
     */
    public String getId() {
        return id;
    }

    /**
     * This field is required if the reason_type is other. It should be a description of the reason
     * for why you wish to block this payer and why it does not align with the given reason_types.
     * This is intended to help us improve our knowledge of types of fraud.
     */
    public String getReasonDescription() {
        return reasonDescription;
    }

    /**
     * The reason you wish to block this payer, can currently be one of 'identity_fraud',
     * 'no_intent_to_pay', 'unfair_chargeback'. If the reason isn't captured by one of the above
     * then 'other' can be selected but you must provide a reason description.
     */
    public ReasonType getReasonType() {
        return reasonType;
    }

    /**
     * This field is a reference to the value you wish to block. This may be the raw value (in the
     * case of emails or email domains) or the ID of the resource (in the case of bank accounts).
     * This means in order to block a specific bank account it must already have been created as a
     * resource.
     */
    public String getResourceReference() {
        return resourceReference;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was updated.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    public enum BlockType {
        @SerializedName("email")
        EMAIL, @SerializedName("email_domain")
        EMAIL_DOMAIN, @SerializedName("bank_account")
        BANK_ACCOUNT, @SerializedName("unknown")
        UNKNOWN
    }

    public enum ReasonType {
        @SerializedName("identity_fraud")
        IDENTITY_FRAUD, @SerializedName("no_intent_to_pay")
        NO_INTENT_TO_PAY, @SerializedName("unfair_chargeback")
        UNFAIR_CHARGEBACK, @SerializedName("other")
        OTHER, @SerializedName("unknown")
        UNKNOWN
    }
}
