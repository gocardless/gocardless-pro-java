package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a negative balance limit resource returned from the API.
 *
 * The negative balance limit is a threshold for the creditor balance beyond which refunds are not
 * permitted. The default limit is zero — refunds are not permitted if the creditor has a negative
 * balance. The limit can be changed on a per-creditor basis.
 * 
 */
public class NegativeBalanceLimit {
    private NegativeBalanceLimit() {
        // blank to prevent instantiation
    }

    private String id;
    private String createdAt;
    private Integer balanceLimit;
    private Currency currency;
    private Links links;

    /**
     * Unique identifier, beginning with "NBL".
     */
    public String getId() {
        return id;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this limit was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * The limit amount in pence (e.g. 10000 for a -100 GBP limit).
     */
    public Integer getBalanceLimit() {
        return balanceLimit;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently
     * "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
    * 
    */
    public Links getLinks() {
        return links;
    }

    public enum Currency {
        @SerializedName("AUD")
        AUD, @SerializedName("CAD")
        CAD, @SerializedName("DKK")
        DKK, @SerializedName("EUR")
        EUR, @SerializedName("GBP")
        GBP, @SerializedName("NZD")
        NZD, @SerializedName("SEK")
        SEK, @SerializedName("USD")
        USD, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a link resource returned from the API.
     *
     * 
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creatorUser;
        private String creditor;

        /**
         * ID of the creator_user who created this limit
         */
        public String getCreatorUser() {
            return creatorUser;
        }

        /**
         * ID of [creditor](#core-endpoints-creditors) which this limit relates to
         */
        public String getCreditor() {
            return creditor;
        }
    }
}
