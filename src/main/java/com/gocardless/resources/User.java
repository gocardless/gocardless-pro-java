package com.gocardless.resources;

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

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique email address, used as a username.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Boolean value showing whether the user is enabled or disabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * User's surname. This field may not exceed 100 characters.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * User's given name. This field may not exceed 100 characters.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Unique identifier, beginning with "US"
     */
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

        /**
         * ID of the user's [role](https://developer.gocardless.com/pro/#api-endpoints-roles), which dictates
         * what resources the user can see.
         */
        public String getRole() {
            return role;
        }
    }
}
