package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class Role {
    private Role() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Boolean enabled;
    private String id;
    private String name;
    private List<Permissions> permissions;

    public String getCreatedAt() {
        return createdAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public static class Permissions {
        private Permissions() {
            // blank to prevent instantiation
        }

        private String access;
        private String resource;

        public String getAccess() {
            return access;
        }

        public String getResource() {
            return resource;
        }
    }
}
