package com.gocardless.pro.resources;

import java.util.Map;

public class Refund {
    private Refund() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private String createdAt;
    private String currency;
    private String id;
    private Links links;
    private Map<String, String> metadata;

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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String payment;

        public String getPayment() {
            return payment;
        }
    }
}
