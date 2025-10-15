package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a block resource returned from the API.
 *
 * Blocks are created to prevent certain customer details from being used when creating mandates.
 * 
 * The details used to create blocks can be exact matches, like a bank account or an email, or a
 * more generic match such as an email domain or bank name. Please be careful when creating blocks
 * for more generic matches as this may block legitimate payers from using your service.
 * 
 * New block types may be added over time.
 * 
 * A block is in essence a simple rule that is used to match against details in a newly created
 * mandate. If there is a successful match then the mandate is transitioned to a "blocked" state.
 * 
 * Please note:
 * 
 * - Payments and subscriptions cannot be created against a mandate in blocked state. - A mandate
 * can never be transitioned out of the blocked state.
 * 
 * The one exception to this is when blocking a 'bank_name'. This block will prevent bank accounts
 * from being created for banks that match the given name. To ensure we match bank names correctly
 * an existing bank account must be used when creating this block. Please be aware that we cannot
 * always match a bank account to a given bank name.
 * 
 * <p class="notice">
 * This API is currently only available for GoCardless Protect+ integrators - please
 * <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class Block {
    private Block() {
        // blank to prevent instantiation
    }

    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String id;
    private BlockType blockType;
    private ReasonType reasonType;
    private String reasonDescription;
    private String resourceReference;

    /**
     * Shows if the block is active or disabled. Only active blocks will be used when deciding if a
     * mandate should be blocked.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was updated.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Unique identifier, beginning with "BLC".
     */
    public String getId() {
        return id;
    }

    /**
     * Type of entity we will seek to match against when blocking the mandate. This can currently be
     * one of 'email', 'email_domain', 'bank_account', or 'bank_name'.
     */
    public BlockType getBlockType() {
        return blockType;
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
     * This field is required if the reason_type is other. It should be a description of the reason
     * for why you wish to block this payer and why it does not align with the given reason_types.
     * This is intended to help us improve our knowledge of types of fraud.
     */
    public String getReasonDescription() {
        return reasonDescription;
    }

    /**
     * This field is a reference to the value you wish to block. This may be the raw value (in the
     * case of emails or email domains) or the ID of the resource (in the case of bank accounts and
     * bank names). This means in order to block a specific bank account (even if you wish to block
     * generically by name) it must already have been created as a resource.
     */
    public String getResourceReference() {
        return resourceReference;
    }

    public enum BlockType {
        @SerializedName("email")
        EMAIL, @SerializedName("email_domain")
        EMAIL_DOMAIN, @SerializedName("bank_account")
        BANK_ACCOUNT, @SerializedName("bank_name")
        BANK_NAME, @SerializedName("unknown")
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
