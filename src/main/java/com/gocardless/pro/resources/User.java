package com.gocardless.pro.resources;

public class User {
    private User() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String email;
    private Boolean enabled;
    private String familyName;
    private String givenName;
    private String id;
    private Links links;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
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
