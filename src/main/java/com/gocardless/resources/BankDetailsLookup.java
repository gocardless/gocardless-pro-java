package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents a bank details lookup resource returned from the API.
 *
 * Look up the name and reachability of a bank account.
 */
public class BankDetailsLookup {
    private BankDetailsLookup() {
        // blank to prevent instantiation
    }

    private List<AvailableDebitScheme> availableDebitSchemes;
    private String bankName;
    private String bic;
    private PayerNameVerificationResult payerNameVerificationResult;

    /**
     * Array of
     * <a href="https://developer.gocardless.com/api-reference/#mandates_scheme">schemes</a>
     * supported for this bank account. This will be an empty array if the bank account is not
     * reachable by any schemes.
     */
    public List<AvailableDebitScheme> getAvailableDebitSchemes() {
        return availableDebitSchemes;
    }

    /**
     * The name of the bank with which the account is held (if available).
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * ISO 9362 SWIFT BIC of the bank with which the account is held.
     * 
     * <p class="notice">
     * Even if no BIC is returned for an account, GoCardless may still be able to collect payments
     * from it - you should refer to the <code>available_debit_schemes</code> attribute to determine
     * reachability.
     * </p>
     */
    public String getBic() {
        return bic;
    }

    /**
     * The result of the payer name verification check performed during the lookup.
     * <code>null</code> if no check was performed.
     * 
     * <ul>
     * <li><code>full</code>: The name provided matches the name held by the bank.</li>
     * <li><code>close</code>: The name provided is a close but not exact match to the name held by
     * the bank.</li>
     * <li><code>cannot_perform_verification</code>: A verification was attempted but could not be
     * completed. This can happen for a number of reasons, including the account holder's bank not
     * participating in the verification scheme, the account not being eligible for verification
     * (e.g. the account holder has opted out), or the bank details not being resolvable, among
     * others.</li>
     * <li><code>null</code>: Verification was not triggered. Either PNV is not supported for the
     * scheme, or PNV feature is disabled for your organisation.</li>
     * </ul>
     */
    public PayerNameVerificationResult getPayerNameVerificationResult() {
        return payerNameVerificationResult;
    }

    public enum AvailableDebitScheme {
        @SerializedName("ach")
        ACH, @SerializedName("autogiro")
        AUTOGIRO, @SerializedName("bacs")
        BACS, @SerializedName("becs")
        BECS, @SerializedName("becs_nz")
        BECS_NZ, @SerializedName("betalingsservice")
        BETALINGSSERVICE, @SerializedName("faster_payments")
        FASTER_PAYMENTS, @SerializedName("pad")
        PAD, @SerializedName("pay_to")
        PAY_TO, @SerializedName("sepa_core")
        SEPA_CORE, @SerializedName("unknown")
        UNKNOWN
    }

    public enum PayerNameVerificationResult {
        @SerializedName("full")
        FULL, @SerializedName("close")
        CLOSE, @SerializedName("cannot_perform_verification")
        CANNOT_PERFORM_VERIFICATION, @SerializedName("unknown")
        UNKNOWN
    }
}
