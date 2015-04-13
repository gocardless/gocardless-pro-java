package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Links getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String role;

        public String getRole() {
            return role;
        }
    }
}
