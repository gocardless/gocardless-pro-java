package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class PublishableApiKey {
    private PublishableApiKey() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Boolean enabled;
    private String id;
    private String key;
    private String name;

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

    public String getName() {
        return name;
    }
}
