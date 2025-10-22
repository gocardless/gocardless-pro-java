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

    private String encryptedCustomerBankDetails;
    private String encryptedDecryptionKey;
    private Links links;
    private String publicKeyId;

    /**
     * Encrypted customer bank account details, containing: `iban`, `account_holder_name`,
     * `swift_bank_code`, `swift_branch_code`, `swift_account_number`
     */
    public String getEncryptedCustomerBankDetails() {
        return encryptedCustomerBankDetails;
    }

    /**
     * Random AES-256 key used to encrypt bank account details, itself encrypted with your public
     * key.
     */
    public String getEncryptedDecryptionKey() {
        return encryptedDecryptionKey;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * The ID of an RSA-2048 public key, from your JWKS, used to encrypt the AES key.
     */
    public String getPublicKeyId() {
        return publicKeyId;
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
