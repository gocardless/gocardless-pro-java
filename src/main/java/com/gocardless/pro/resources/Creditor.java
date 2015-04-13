package com.gocardless.pro.resources;

public class Creditor {
    private Creditor() {
        // blank to prevent instantiation
    }

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String countryCode;
    private String createdAt;
    private String id;
    private Links links;
    private String name;
    private String postalCode;
    private String region;

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRegion() {
        return region;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String defaultEurPayoutAccount;
        private String defaultGbpPayoutAccount;
        private String logo;

        public String getDefaultEurPayoutAccount() {
            return defaultEurPayoutAccount;
        }

        public String getDefaultGbpPayoutAccount() {
            return defaultGbpPayoutAccount;
        }

        public String getLogo() {
            return logo;
        }
    }
}
