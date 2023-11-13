package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a billing request template resource returned from the API.
 *
 * Billing Request Templates are reusable templates that result in numerous Billing Requests with
 * similar attributes. They provide a no-code solution for generating various types of multi-user
 * payment links.
 * 
 * Each template includes a reusable URL that can be embedded in a website or shared with customers
 * via email. Every time the URL is opened, it generates a new Billing Request.
 * 
 * Billing Request Templates overcome the key limitation of the Billing Request: a Billing Request
 * cannot be shared among multiple users because it is intended for single-use and is designed to
 * cater to the unique needs of individual customers.
 */
public class BillingRequestTemplate {
    private BillingRequestTemplate() {
        // blank to prevent instantiation
    }

    private String authorisationUrl;
    private String createdAt;
    private String id;
    private String mandateRequestCurrency;
    private String mandateRequestDescription;
    private Map<String, Object> mandateRequestMetadata;
    private String mandateRequestScheme;
    private MandateRequestVerify mandateRequestVerify;
    private Map<String, Object> metadata;
    private String name;
    private Integer paymentRequestAmount;
    private String paymentRequestCurrency;
    private String paymentRequestDescription;
    private Map<String, Object> paymentRequestMetadata;
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
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code.
     */
    public String getMandateRequestCurrency() {
        return mandateRequestCurrency;
    }

    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     * 
     */
    public String getMandateRequestDescription() {
        return mandateRequestDescription;
    }

    /**
     * Key-value store of custom data that will be applied to the mandate created when this request
     * is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and values up to
     * 500 characters.
     */
    public Map<String, Object> getMandateRequestMetadata() {
        return mandateRequestMetadata;
    }

    /**
     * A bank payment scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz",
     * "betalingsservice", "faster_payments", "pad", "pay_to" and "sepa_core" are supported.
     * Optional for mandate only requests - if left blank, the payer will be able to select the
     * currency/scheme to pay with from a list of your available schemes.
     */
    public String getMandateRequestScheme() {
        return mandateRequestScheme;
    }

    /**
     * Verification preference for the mandate. One of:
     * <ul>
     * <li>`minimum`: only verify if absolutely required, such as when part of scheme rules</li>
     * <li>`recommended`: in addition to `minimum`, use the GoCardless payment intelligence solution
     * to decide if a payer should be verified</li>
     * <li>`when_available`: if verification mechanisms are available, use them</li>
     * <li>`always`: as `when_available`, but fail to create the Billing Request if a mechanism
     * isn't available</li>
     * </ul>
     * 
     * By default, all Billing Requests use the `recommended` verification preference. It uses
     * GoCardless payment intelligence solution to determine if a payer is fraudulent or not. The
     * verification mechanism is based on the response and the payer may be asked to verify
     * themselves. If the feature is not available, `recommended` behaves like `minimum`.
     * 
     * If you never wish to take advantage of our reduced risk products and Verified Mandates as
     * they are released in new schemes, please use the `minimum` verification preference.
     * 
     * See [Billing Requests: Creating Verified
     * Mandates](https://developer.gocardless.com/getting-started/billing-requests/verified-mandates/)
     * for more information.
     */
    public MandateRequestVerify getMandateRequestVerify() {
        return mandateRequestVerify;
    }

    /**
     * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50
     * characters and values up to 500 characters.
     */
    public Map<String, Object> getMetadata() {
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
     * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. `GBP` and `EUR`
     * supported; `GBP` with your customers in the UK and for `EUR` with your customers in Germany
     * only.
     */
    public String getPaymentRequestCurrency() {
        return paymentRequestCurrency;
    }

    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     * 
     */
    public String getPaymentRequestDescription() {
        return paymentRequestDescription;
    }

    /**
     * Key-value store of custom data that will be applied to the payment created when this request
     * is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and values up to
     * 500 characters.
     */
    public Map<String, Object> getPaymentRequestMetadata() {
        return paymentRequestMetadata;
    }

    /**
     * (Optional) A scheme used for Open Banking payments. Currently `faster_payments` is supported
     * in the UK (GBP) and `sepa_credit_transfer` and `sepa_instant_credit_transfer` are supported
     * in Germany (EUR). In Germany, `sepa_credit_transfer` is used as the default. Please be aware
     * that `sepa_instant_credit_transfer` may incur an additional fee for your customer.
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

    public enum MandateRequestVerify {
        @SerializedName("minimum")
        MINIMUM, @SerializedName("recommended")
        RECOMMENDED, @SerializedName("when_available")
        WHEN_AVAILABLE, @SerializedName("always")
        ALWAYS, @SerializedName("unknown")
        UNKNOWN
    }
}
