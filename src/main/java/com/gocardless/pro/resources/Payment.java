package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class Payment {
    private Payment() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer amountRefunded;
    private String chargeDate;
    private String createdAt;
    private String currency;
    private String description;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private String reference;
    private String status;

    public Integer getAmount() {
        return amount;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCurrency() {
        return currency;
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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getReference() {
        return reference;
    }

    public String getStatus() {
        return status;
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;
        private String mandate;
        private String payout;
        private String subscription;

        public String getCreditor() {
            return creditor;
        }

        public String getMandate() {
            return mandate;
        }

        public String getPayout() {
            return payout;
        }

        public String getSubscription() {
            return subscription;
        }
    }
}
