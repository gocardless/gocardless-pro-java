package com.gocardless.resources;

/**
 * Represents a transferred mandate resource returned from the API.
 *
 * Mandates that have been transferred using Current Account Switch Service
 */
public class TransferredMandate {
    private TransferredMandate() {
        // blank to prevent instantiation
    }

    private String encryptedData;
    private String key;
    private String kid;
    private Links links;

    /**
     * Encrypted bank account details
     */
    public String getEncryptedData() {
        return encryptedData;
    }

    /**
     * Encrypted AES key
     */
    public String getKey() {
        return key;
    }

    /**
     * Public key id used to encrypt AES key
     */
    public String getKid() {
        return kid;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String customerBankAccount;
        private String mandate;

        /**
         * The ID of the updated [customer_bank_account](#core-endpoints-customer-bank-accounts)
         */
        public String getCustomerBankAccount() {
            return customerBankAccount;
        }

        /**
         * The ID of the transferred mandate
         */
        public String getMandate() {
            return mandate;
        }
    }
}
