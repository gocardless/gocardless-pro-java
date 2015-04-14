package com.gocardless.pro.resources;

public class RedirectFlow {
    private RedirectFlow() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String description;
    private String id;
    private Links links;
    private String redirectUrl;
    private Scheme scheme;
    private String sessionToken;
    private String successRedirectUrl;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getSuccessRedirectUrl() {
        return successRedirectUrl;
    }

    public enum Scheme {
        BACS, SEPA_CORE,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String mandate;

        public String getCreditor() {
            return creditor;
        }

        public String getMandate() {
            return mandate;
        }
    }
}
