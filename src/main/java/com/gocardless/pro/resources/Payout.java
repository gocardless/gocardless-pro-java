package com.gocardless.pro.resources;

public class Payout {
    private Payout() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String createdAt;
    private String currency;
    private String id;
    private Links links;
    private String reference;
    private Status status;

    public Integer getAmount() {
        return amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public Links getLinks() {
        return links;
    }

    public String getReference() {
        return reference;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        PENDING, PAID,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String creditorBankAccount;

        public String getCreditor() {
            return creditor;
        }

        public String getCreditorBankAccount() {
            return creditorBankAccount;
        }
    }
}
