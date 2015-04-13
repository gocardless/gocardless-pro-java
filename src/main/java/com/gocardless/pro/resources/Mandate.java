package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class Mandate {
    private Mandate() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String nextPossibleChargeDate;
    private String reference;
    private String scheme;
    private Status status;

    public String getCreatedAt() {
        return createdAt;
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

    public String getNextPossibleChargeDate() {
        return nextPossibleChargeDate;
    }

    public String getReference() {
        return reference;
    }

    public String getScheme() {
        return scheme;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        PENDING_SUBMISSION, SUBMITTED, ACTIVE, FAILED, CANCELLED, EXPIRED,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String customerBankAccount;

        public String getCreditor() {
            return creditor;
        }

        public String getCustomerBankAccount() {
            return customerBankAccount;
        }
    }
}
