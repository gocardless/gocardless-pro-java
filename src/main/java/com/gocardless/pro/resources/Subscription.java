package com.gocardless.pro.resources;

import java.util.List;
import java.util.Map;

public class Subscription {
    private Subscription() {
        // blank to prevent instantiation
    }

    private Integer amount;
    private Integer count;
    private String createdAt;
    private String currency;
    private Integer dayOfMonth;
    private String endAt;
    private String id;
    private Integer interval;
    private String intervalUnit;
    private Links links;
    private Map<String, String> metadata;
    private Month month;
    private String name;
    private String startAt;
    private String status;
    private List<UpcomingPayments> upcomingPayments;

    public Integer getAmount() {
        return amount;
    }

    public Integer getCount() {
        return count;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public String getEndAt() {
        return endAt;
    }

    public String getId() {
        return id;
    }

    public Integer getInterval() {
        return interval;
    }

    public String getIntervalUnit() {
        return intervalUnit;
    }

    public Links getLinks() {
        return links;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Month getMonth() {
        return month;
    }

    public String getName() {
        return name;
    }

    public String getStartAt() {
        return startAt;
    }

    public String getStatus() {
        return status;
    }

    public List<UpcomingPayments> getUpcomingPayments() {
        return upcomingPayments;
    }

    public enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
    }

    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String mandate;

        public String getMandate() {
            return mandate;
        }
    }

    public static class UpcomingPayments {
        private UpcomingPayments() {
            // blank to prevent instantiation
        }

        private Integer amount;
        private String chargeDate;

        public Integer getAmount() {
            return amount;
        }

        public String getChargeDate() {
            return chargeDate;
        }
    }
}
