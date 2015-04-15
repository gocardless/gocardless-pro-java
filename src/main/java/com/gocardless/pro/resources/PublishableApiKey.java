package com.gocardless.pro.resources;

/**
 * Represents a Publishable API Key resource returned from the API.
 *
 * Publishable API keys are designed to be used by the [js
 * flow](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account-tokens). You
 * should generate a key and then use it to make requests to the API. They do not expire, but can be
 * disabled.
 * 
 * Publishable API keys only have permissions to create [customer bank account
 * tokens](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account-tokens).
 */
public class PublishableApiKey {
    private PublishableApiKey() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Boolean enabled;
    private String id;
    private String key;
    private String name;

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Boolean value showing whether the API key is enabled or disabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Unique identifier, beginning with "PK"
     */
    public String getId() {
        return id;
    }

    /**
     * Secret key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Human readable name for the key. This should not exceed 75 characters.
     */
    public String getName() {
        return name;
    }
}
