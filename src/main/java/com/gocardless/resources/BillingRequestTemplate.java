package com.gocardless.resources;

import java.util.Map;

/**
 * Represents a billing request template resource returned from the API.
 *
 * Billing Request Templates
 */
public class BillingRequestTemplate {
    private BillingRequestTemplate() {
        // blank to prevent instantiation
    }

    private String authorisationUrl;
    private String createdAt;
    private String id;
    private String mandateRequestCurrency;
    private Map<String, String> mandateRequestMetadata;
    private String mandateRequestScheme;
    private Map<String, String> mandateRequestVerify;
    private Map<String, String> metadata;
    private String name;
    private Integer paymentRequestAmount;
    private String paymentRequestCurrency;
    private String paymentRequestDescription;
    private Map<String, String> paymentRequestMetadata;
    private String paymentRequestScheme;
    private String redirectUri;
    private String updatedAt;

    /**
     * Permanent URL that customers can visit to allow them to complete a flow based on this
     * template, before being returned to the `redirect_uri`.
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "BRT".
     */
    public String getId() {
        return id;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently only
     * "GBP" is supported as we only have one scheme that is per_payment_authorised.
     */
    public String getMandateRequestCurrency() {
        return mandateRequestCurrency;
    }

    public Map<String, String> getMandateRequestMetadata() {
        return mandateRequestMetadata;
    }

    /**
     * A Direct Debit scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
     * "betalingsservice", "pad" and "sepa_core" are supported.
     */
    public String getMandateRequestScheme() {
        return mandateRequestScheme;
    }

    public Map<String, String> getMandateRequestVerify() {
        return mandateRequestVerify;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Name for the template. Provides a friendly human name for the template, as it is shown in the
     * dashboard. Must not exceed 255 characters.
     */
    public String getName() {
        return name;
    }

    /**
     * Amount in minor unit (e.g. pence in GBP, cents in EUR).
     */
    public Integer getPaymentRequestAmount() {
        return paymentRequestAmount;
    }

    /**
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently only
     * "GBP" is supported as we only have one scheme that is per_payment_authorised.
     */
    public String getPaymentRequestCurrency() {
        return paymentRequestCurrency;
    }

    /**
     * A human-readable description of the payment. This will be displayed to the payer when
     * authorising the billing request.
     * 
     */
    public String getPaymentRequestDescription() {
        return paymentRequestDescription;
    }

    public Map<String, String> getPaymentRequestMetadata() {
        return paymentRequestMetadata;
    }

    /**
     * A Direct Debit scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
     * "betalingsservice", "pad" and "sepa_core" are supported.
     */
    public String getPaymentRequestScheme() {
        return paymentRequestScheme;
    }

    /**
     * URL that the payer can be redirected to after completing the request flow.
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Dynamic [timestamp](#api-usage-time-zones--dates) recording when this resource was last
     * updated.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }
}
