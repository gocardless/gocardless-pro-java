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

    private String publicKeyId;
    private String encryptedCustomerBankDetails;
    private Links links;
    private String encryptedDecryptionKey;

    /**
     * The ID of an RSA-2048 public key, from your JWKS, used to encrypt the AES key.
     */
    public String getPublicKeyId() {
        return publicKeyId;
    }

    /**
     * Encrypted customer bank account details, containing: `iban`, `account_holder_name`,
     * `swift_bank_code`, `swift_branch_code`, `swift_account_number`
     */
    public String getEncryptedCustomerBankDetails() {
        return encryptedCustomerBankDetails;
    }

    /**
    * 
    */
    public Links getLinks() {
        return links;
    }

    /**
     * Random AES-256 key used to encrypt bank account details, itself encrypted with your public
     * key.
     */
    public String getEncryptedDecryptionKey() {
        return encryptedDecryptionKey;
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
