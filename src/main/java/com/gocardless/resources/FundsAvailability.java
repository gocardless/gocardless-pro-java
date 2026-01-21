package com.gocardless.resources;

/**
 * Represents a funds availability resource returned from the API.
 *
 * Checks if the payer's current balance is sufficient to cover the amount the merchant wants to
 * charge within the consent parameters defined on the mandate.
 */
public class FundsAvailability {
    private FundsAvailability() {
        // blank to prevent instantiation
    }

    private Boolean available;

    /**
     * Indicates if the amount is available
     */
    public Boolean getAvailable() {
        return available;
    }
}
