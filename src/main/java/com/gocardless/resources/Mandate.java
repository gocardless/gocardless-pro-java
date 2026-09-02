package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a mandate resource returned from the API.
 *
 * Mandates represent the Direct Debit mandate with a
 * <a href="https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>.
 * 
 * GoCardless will notify you via a
 * <a href="https://developer.gocardless.com/api-reference/#appendix-webhooks">webhook</a> whenever
 * the status of a mandate changes.
 */
public class Mandate {
    private Mandate() {
        // blank to prevent instantiation
    }

    private AuthorisationSource authorisationSource;
    private ConsentParameters consentParameters;
    private ConsentType consentType;
    private String createdAt;
    private FundsSettlement fundsSettlement;
    private String id;
    private Links links;
    private MandateType mandateType;
    private Map<String, String> metadata;
    private String nextPossibleChargeDate;
    private String nextPossibleStandardAchChargeDate;
    private Boolean paymentsRequireApproval;
    private String reference;
    private String scheme;
    private Status status;
    private String verifiedAt;

    /**
     * This field is ACH specific, sometimes referred to as
     * <a href="https://www.moderntreasury.com/learn/sec-codes">SEC code</a>.
     * 
     * This is the way that the payer gives authorisation to the merchant. web: Authorisation is
     * Internet Initiated or via Mobile Entry (maps to SEC code: WEB) telephone: Authorisation is
     * provided orally over telephone (maps to SEC code: TEL) paper: Authorisation is provided in
     * writing and signed, or similarly authenticated (maps to SEC code: PPD)
     */
    public AuthorisationSource getAuthorisationSource() {
        return authorisationSource;
    }

    /**
     * (Optional) Payto and VRP Scheme specific information
     */
    public ConsentParameters getConsentParameters() {
        return consentParameters;
    }

    /**
     * (Optional) Specifies the type of authorisation agreed between the payer and merchant. It can
     * be set to one-off, recurring or standing for ACH, or single, recurring and sporadic for PAD.
     */
    public ConsentType getConsentType() {
        return consentType;
    }

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * This field will decide how GoCardless handles settlement of funds from the customer.
     * 
     * <ul>
     * <li><code>managed</code> will be moved through GoCardless' account, batched, and payed
     * out.</li>
     * <li><code>direct</code> will be a direct transfer from the payer's account to the merchant
     * where invoicing will be handled separately.</li>
     * </ul>
     */
    public FundsSettlement getFundsSettlement() {
        return fundsSettlement;
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
     * Mandate type
     */
    public MandateType getMandateType() {
        return mandateType;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * The earliest date that can be used as a <code>charge_date</code> on any newly created payment
     * for this mandate. This value will change over time.
     */
    public String getNextPossibleChargeDate() {
        return nextPossibleChargeDate;
    }

    /**
     * If this is an ACH mandate, the earliest date that can be used as a <code>charge_date</code>
     * on any newly created payment to be charged through standard ACH, rather than Faster ACH. This
     * value will change over time.
     * 
     * It is only present in the API response for ACH mandates.
     */
    public String getNextPossibleStandardAchChargeDate() {
        return nextPossibleStandardAchChargeDate;
    }

    /**
     * Boolean value showing whether payments and subscriptions under this mandate require approval
     * via an automated email before being processed.
     */
    public Boolean getPaymentsRequireApproval() {
        return paymentsRequireApproval;
    }

    /**
     * Unique reference. Different schemes have different length and
     * <a href="https://developer.gocardless.com/api-reference/#appendix-character-sets">character
     * set</a> requirements. GoCardless will generate a unique reference satisfying the different
     * scheme requirements if this field is left blank.
     */
    public String getReference() {
        return reference;
    }

    /**
     * <a name="mandates_scheme"></a>Bank payment scheme to which this mandate and associated
     * payments are submitted. Can be supplied or automatically detected from the customer's bank
     * account.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * One of:
     * 
     * <ul>
     * <li><code>pending_customer_approval</code>: the mandate has not yet been signed by the second
     * customer</li>
     * <li><code>pending_submission</code>: the mandate has not yet been submitted to the customer's
     * bank</li>
     * <li><code>submitted</code>: the mandate has been submitted to the customer's bank but has not
     * been processed yet</li>
     * <li><code>active</code>: the mandate has been successfully set up by the customer's bank</li>
     * <li><code>suspended_by_payer</code>: the mandate has been suspended by payer</li>
     * <li><code>failed</code>: the mandate could not be created</li>
     * <li><code>cancelled</code>: the mandate has been cancelled</li>
     * <li><code>expired</code>: the mandate has expired due to dormancy</li>
     * <li><code>consumed</code>: the mandate has been consumed and cannot be reused (note that this
     * only applies to schemes that are per-payment authorised)</li>
     * <li><code>blocked</code>: the mandate has been blocked and payments cannot be created</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">Timestamp</a>
     * recording when this mandate was verified.
     */
    public String getVerifiedAt() {
        return verifiedAt;
    }

    public enum AuthorisationSource {
        @SerializedName("web")
        WEB, @SerializedName("telephone")
        TELEPHONE, @SerializedName("paper")
        PAPER, @SerializedName("unknown")
        UNKNOWN
    }

    public enum ConsentType {
        @SerializedName("one_off")
        ONE_OFF, @SerializedName("single")
        SINGLE, @SerializedName("recurring")
        RECURRING, @SerializedName("standing")
        STANDING, @SerializedName("sporadic")
        SPORADIC, @SerializedName("unknown")
        UNKNOWN
    }

    public enum FundsSettlement {
        @SerializedName("managed")
        MANAGED, @SerializedName("direct")
        DIRECT, @SerializedName("unknown")
        UNKNOWN
    }

    public enum MandateType {
        @SerializedName("bank_debit")
        BANK_DEBIT, @SerializedName("instant")
        INSTANT, @SerializedName("recurring")
        RECURRING, @SerializedName("vrp_commercial")
        VRP_COMMERCIAL, @SerializedName("vrp_sweeping")
        VRP_SWEEPING, @SerializedName("unknown")
        UNKNOWN
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
        BLOCKED, @SerializedName("suspended_by_payer")
        SUSPENDED_BY_PAYER, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a consent parameter resource returned from the API.
     *
     * (Optional) Payto and VRP Scheme specific information
     */
    public static class ConsentParameters {
        private ConsentParameters() {
            // blank to prevent instantiation
        }

        private String endDate;
        private Integer maxAmountPerPayment;
        private Integer maxAmountPerPeriod;
        private Integer maxPaymentsPerPeriod;
        private Period period;
        private String startDate;

        /**
         * The latest date at which payments can be taken, must occur after start_date if present
         */
        public String getEndDate() {
            return endDate;
        }

        /**
         * The maximum amount that can be charged for a single payment
         */
        public Integer getMaxAmountPerPayment() {
            return maxAmountPerPayment;
        }

        /**
         * The maximum total amount that can be charged for all payments in this period
         */
        public Integer getMaxAmountPerPeriod() {
            return maxAmountPerPeriod;
        }

        /**
         * The maximum number of payments that can be collected in this period
         */
        public Integer getMaxPaymentsPerPeriod() {
            return maxPaymentsPerPeriod;
        }

        /**
         * The repeating period for this mandate
         */
        public Period getPeriod() {
            return period;
        }

        /**
         * The date from which payments can be taken
         */
        public String getStartDate() {
            return startDate;
        }

        public enum Period {
            @SerializedName("day")
            DAY, @SerializedName("week")
            WEEK, @SerializedName("month")
            MONTH, @SerializedName("year")
            YEAR, @SerializedName("flexible")
            FLEXIBLE, @SerializedName("unknown")
            UNKNOWN
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
        private String customer;
        private String customerBankAccount;
        private String newMandate;

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>.
         */
        public String getCreditor() {
            return creditor;
        }

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customers">customer</a>
         */
        public String getCustomer() {
            return customer;
        }

        /**
         * ID of the associated <a href=
         * "https://developer.gocardless.com/api-reference/#core-endpoints-customer-bank-accounts">customer
         * bank account</a> which the mandate is created and submits payments against.
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
