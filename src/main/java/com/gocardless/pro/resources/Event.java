package com.gocardless.pro.resources;

import java.util.Map;

public class Event {
    private Event() {
        // blank to prevent instantiation
    }

    private String action;
    private String createdAt;
    private Details details;
    private String id;
    private Links links;
    private Map<String, String> metadata;
    private ResourceType resourceType;

    public String getAction() {
        return action;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Details getDetails() {
        return details;
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

    public ResourceType getResourceType() {
        return resourceType;
    }

    public enum ResourceType {
        PAYMENTS, MANDATES, PAYOUTS, REFUNDS, SUBSCRIPTIONS,
    }

    public static class Details {
        private Details() {
            // blank to prevent instantiation
        }

        private String cause;
        private String description;
        private Origin origin;
        private String reasonCode;
        private ReportType reportType;
        private Scheme scheme;

        public String getCause() {
            return cause;
        }

        public String getDescription() {
            return description;
        }

        public Origin getOrigin() {
            return origin;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public ReportType getReportType() {
            return reportType;
        }

        public Scheme getScheme() {
            return scheme;
        }

        public enum Origin {
            BANK, API, GOCARDLESS,
        }

        public enum ReportType {
            AUDDIS, ADDACS, ARUDD, DDICA, SETTLEMENT, PROCESSING,
        }

        public enum Scheme {
            BACS, SEPA_CORE,
        }
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;
        private String newCustomerBankAccount;
        private String parentEvent;
        private String payment;
        private String payout;
        private String previousCustomerBankAccount;
        private String refund;
        private String subscription;

        public String getMandate() {
            return mandate;
        }

        public String getNewCustomerBankAccount() {
            return newCustomerBankAccount;
        }

        public String getParentEvent() {
            return parentEvent;
        }

        public String getPayment() {
            return payment;
        }

        public String getPayout() {
            return payout;
        }

        public String getPreviousCustomerBankAccount() {
            return previousCustomerBankAccount;
        }

        public String getRefund() {
            return refund;
        }

        public String getSubscription() {
            return subscription;
        }
    }
}
