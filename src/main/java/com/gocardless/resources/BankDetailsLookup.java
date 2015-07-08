package com.gocardless.resources;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a bank details lookup resource returned from the API.
 *
 * Look up the name and reachability of a bank.
 */
public class BankDetailsLookup {
    private BankDetailsLookup() {
        // blank to prevent instantiation
    }

    private List<AvailableDebitScheme> availableDebitSchemes;
    private String bankName;

    /**
     * Array of [schemes](#mandates_scheme) supported for this bank account. This will be an empty array
     * if the bank account is not reachable by any schemes.
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

    public enum AvailableDebitScheme {
        @SerializedName("bacs")
        BACS, @SerializedName("sepa_core")
        SEPA_CORE,
    }
}
