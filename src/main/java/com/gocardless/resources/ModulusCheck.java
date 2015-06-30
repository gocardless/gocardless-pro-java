package com.gocardless.resources;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a modulus check resource returned from the API.
 *
 * Check whether an account number and bank / branch code combination are compatible.
 */
public class ModulusCheck {
    private ModulusCheck() {
        // blank to prevent instantiation
    }

    private List<AvailableScheme> availableSchemes;
    private List<Error> errors;
    private Boolean succeeded;

    /**
     * Array of [schemes](#mandates_scheme) supported for this bank account. This will be an empty array
     * for an unsuccessful modulus check.
     */
    public List<AvailableScheme> getAvailableSchemes() {
        return availableSchemes;
    }

    /**
     * Array of errors encountered during modulus checking, structured like the `errors` key in a
     * `validation_failed` response - see [errors](#overview-errors).
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * Whether the modulus check was successful (i.e. whether the bank account details provided were
     * valid).
     */
    public Boolean getSucceeded() {
        return succeeded;
    }

    public enum AvailableScheme {
        @SerializedName("bacs")
        BACS, @SerializedName("sepa_core")
        SEPA_CORE,
    }

    /**
     * Represents a error resource returned from the API.
     *
     * An error encountered during modulus checking
     */
    public static class Error {
        private Error() {
            // blank to prevent instantiation
        }

        private String field;
        private String message;

        /**
         * Field that caused the error.
         */
        public String getField() {
            return field;
        }

        /**
         * Description of the error.
         */
        public String getMessage() {
            return message;
        }
    }
}
