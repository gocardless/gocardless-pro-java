package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a bank account detail resource returned from the API.
 *
 * Retrieve bank account details in JWE encrypted format
 */
public class BankAccountDetail {
    private BankAccountDetail() {
        // blank to prevent instantiation
    }

    private String ciphertext;
    private String encryptedKey;
    private String iv;
    @SerializedName("protected")
    private String protectedValue;
    private String tag;

    /**
     * Base64 URL encoded encrypted payload, in this case bank details.
     */
    public String getCiphertext() {
        return ciphertext;
    }

    /**
     * Base64 URL encoded symmetric content encryption key, encrypted with the asymmetric key from
     * your JWKS.
     */
    public String getEncryptedKey() {
        return encryptedKey;
    }

    /**
     * Base64 URL encoded initialization vector, used during content encryption.
     */
    public String getIv() {
        return iv;
    }

    /**
     * Base64 URL encoded JWE header values, containing the following keys:
     * 
     * - `alg`: the asymmetric encryption type used to encrypt symmetric key, e.g: `RSA-OAEP`. -
     * `enc`: the content encryption type, e.g: `A256GCM`. - `kid`: the ID of an RSA-2048 public
     * key, from your JWKS, used to encrypt the AES key.
     */
    public String getProtectedValue() {
        return protectedValue;
    }

    /**
     * Base64 URL encoded authentication tag, used to verify payload integrity during decryption.
     */
    public String getTag() {
        return tag;
    }
}
