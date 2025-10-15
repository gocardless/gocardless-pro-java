package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a mandate import entry resource returned from the API.
 *
 * Mandate Import Entries are added to a [Mandate Import](#core-endpoints-mandate-imports). Each
 * entry corresponds to one mandate to be imported into GoCardless.
 * 
 * To import a mandate you will need:
 * <ol>
 * <li>Identifying information about the customer (name/company and address)</li>
 * <li>Bank account details, consisting of an account holder name and either an IBAN or
 * <a href="#appendix-local-bank-details">local bank details</a></li>
 * <li>Amendment details (SEPA only)</li>
 * </ol>
 * 
 * We suggest you provide a `record_identifier` (which is unique within the context of a single
 * mandate import) to help you to identify mandates that have been created once the import has been
 * processed by GoCardless. You can [list the mandate import
 * entries](#mandate-import-entries-list-all-mandate-import-entries), match them up in your system
 * using the `record_identifier`, and look at the `links` fields to find the mandate, customer and
 * customer bank account that have been imported.
 * 
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is currently only available for approved integrators -
 * please <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class MandateImportEntry {
    private MandateImportEntry() {
        // blank to prevent instantiation
    }

    private Links links;
    private Map<String, Object> processingErrors;
    private String recordIdentifier;
    private String createdAt;

    /**
     * Related resources
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Per-resource processing errors
     */
    public Map<String, Object> getProcessingErrors() {
        return processingErrors;
    }

    /**
     * A unique identifier for this entry, which you can use (once the import has been processed by
     * GoCardless) to identify the records that have been created. Limited to 255 characters.
     * 
     */
    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Represents a link resource returned from the API.
     *
     * Related resources
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandateImport;
        private Mandate mandate;
        private Customer customer;
        private CustomerBankAccount customerBankAccount;

        /**
         * The ID of the mandate import. This is returned when you [create a Mandate
         * Import](#mandate-imports-create-a-new-mandate-import).
         * 
         */
        public String getMandateImport() {
            return mandateImport;
        }

        /**
         * The ID of the mandate which was created when the mandate import was processed.
         */
        public Mandate getMandate() {
            return mandate;
        }

        /**
         * The ID of the customer which was created when the mandate import was processed.
         */
        public Customer getCustomer() {
            return customer;
        }

        /**
         * The ID of the customer bank account which was created when the mandate import was
         * processed.
         */
        public CustomerBankAccount getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * Represents a mandate resource returned from the API.
         *
         * The ID of the mandate which was created when the mandate import was processed.
         */
        public static class Mandate {
            private Mandate() {
                // blank to prevent instantiation
            }
        }

        /**
         * Represents a customer resource returned from the API.
         *
         * The ID of the customer which was created when the mandate import was processed.
         */
        public static class Customer {
            private Customer() {
                // blank to prevent instantiation
            }
        }

        /**
         * Represents a customer bank account resource returned from the API.
         *
         * The ID of the customer bank account which was created when the mandate import was
         * processed.
         */
        public static class CustomerBankAccount {
            private CustomerBankAccount() {
                // blank to prevent instantiation
            }
        }
    }
}
