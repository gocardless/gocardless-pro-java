package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class CustomerBankAccount {
    private CustomerBankAccount() {
        // blank to prevent instantiation
    }

    private String accountHolderName;
    private String accountNumberEnding;
    private String bankName;
    private String countryCode;
    private String createdAt;
    private String currency;
    private Boolean enabled;
    private String id;
    private Links links;
    private Map<String, String> metadata;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumberEnding() {
        return accountNumberEnding;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String customer;

        public String getCustomer() {
            return customer;
        }
    }
}
