package com.gocardless.pro.resources;

/**
 * Represents a API Key resource returned from the API.
 *
 * <a name="api_key_not_active"></a>API keys are designed to be used by any integrations you build.
 * You should generate a key and then use it to make requests to the API and set the webhook URL for
 * that integration. They do not expire, but can be disabled.
 */
public class ApiKey {
    private ApiKey() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Boolean enabled;
    private String id;
    private String key;
    private Links links;
    private String name;
    private String webhookUrl;

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this record was created.
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
     * Unique identifier, beginning with "AK"
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

    public Links getLinks() {
        return links;
    }

    /**
     * Human readable name for the key. This field cannot exceed 75 characters.
     */
    public String getName() {
        return name;
    }

    /**
     * Optional https url, to which all webhooks will be sent. Note that if this is set on multiple API
     * keys, each event will be sent to each `webhook_url`; for example, if you have two keys with
     * `webhook_url` set to `https://example.com/webhooks`, then we will send two requests to that url
     * for each event that occurs.
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String role;

        /**
         * Unique identifier, beginning with "RO"
         */
        public String getRole() {
            return role;
        }
    }
}
