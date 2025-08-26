package com.gocardless.resources;

import java.util.List;

/**
 * Represents a creditor bank account validate resource returned from the API.
 *
 * Creditor Bank Accounts hold the bank details of a [creditor](#core-endpoints-creditors). These
 * are the bank accounts which your [payouts](#core-endpoints-payouts) will be sent to.
 * 
 * When all locale details and Iban are supplied validates creditor bank details without creating a
 * creditor bank account and also provdes bank details such as name and icon url. When partial
 * details are are provided the endpoint will only provide bank details such as name and icon url
 * but will not be able to determine if the provided details are valid.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is not available for partner integrations.
 * </p>
 */
public class CreditorBankAccountValidate {
    private CreditorBankAccountValidate() {
        // blank to prevent instantiation
    }

    private String bankName;
    private String iconUrl;
    private List<InvalidReason> invalidReasons;
    private Boolean isValid;

    /**
     * Name of bank, taken from the bank details.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * URL of the bank's icon.
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * The reason why the bank details are invalid, if applicable.
     */
    public List<InvalidReason> getInvalidReasons() {
        return invalidReasons;
    }

    /**
     * Whether the bank account details are valid.
     */
    public Boolean getIsValid() {
        return isValid;
    }

    public static class InvalidReason {
        private InvalidReason() {
            // blank to prevent instantiation
        }

        private String field;
        private String message;

        /**
         * The name of the field with the error
         */
        public String getField() {
            return field;
        }

        /**
         * The error message
         */
        public String getMessage() {
            return message;
        }
    }
}
