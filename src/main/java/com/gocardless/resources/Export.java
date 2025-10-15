package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a export resource returned from the API.
 *
 * File-based exports of data
 */
public class Export {
    private Export() {
        // blank to prevent instantiation
    }

    private String id;
    private String createdAt;
    private ExportType exportType;
    private String downloadUrl;
    private String currency;

    /**
     * Unique identifier, beginning with "EX".
     */
    public String getId() {
        return id;
    }

    /**
     * Fixed [timestamp](#api-usage-dates-and-times), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * The type of the export
     */
    public ExportType getExportType() {
        return exportType;
    }

    /**
     * Download url for the export file. Subject to expiry.
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * The currency of the export (if applicable)
     */
    public String getCurrency() {
        return currency;
    }

    public enum ExportType {
        @SerializedName("payments_index")
        PAYMENTS_INDEX, @SerializedName("events_index")
        EVENTS_INDEX, @SerializedName("refunds_index")
        REFUNDS_INDEX, @SerializedName("payouts_index")
        PAYOUTS_INDEX, @SerializedName("customers_index")
        CUSTOMERS_INDEX, @SerializedName("subscriptions_index")
        SUBSCRIPTIONS_INDEX, @SerializedName("payment_events")
        PAYMENT_EVENTS, @SerializedName("subscription_events")
        SUBSCRIPTION_EVENTS, @SerializedName("payout_events")
        PAYOUT_EVENTS, @SerializedName("refund_events")
        REFUND_EVENTS, @SerializedName("mandate_events")
        MANDATE_EVENTS, @SerializedName("payout_events_breakdown")
        PAYOUT_EVENTS_BREAKDOWN, @SerializedName("payout_events_reconciliation")
        PAYOUT_EVENTS_RECONCILIATION, @SerializedName("payout_transactions_breakdown")
        PAYOUT_TRANSACTIONS_BREAKDOWN, @SerializedName("payout_transactions_reconciliation")
        PAYOUT_TRANSACTIONS_RECONCILIATION, @SerializedName("authorisation_requests")
        AUTHORISATION_REQUESTS, @SerializedName("customer_bank_accounts")
        CUSTOMER_BANK_ACCOUNTS, @SerializedName("users")
        USERS, @SerializedName("organisation_authorisations")
        ORGANISATION_AUTHORISATIONS, @SerializedName("gc_invalid_authorisation_requests")
        GC_INVALID_AUTHORISATION_REQUESTS, @SerializedName("partner_fees")
        PARTNER_FEES, @SerializedName("payments_import_template")
        PAYMENTS_IMPORT_TEMPLATE, @SerializedName("payment_account_statement")
        PAYMENT_ACCOUNT_STATEMENT, @SerializedName("unknown")
        UNKNOWN
    }
}
