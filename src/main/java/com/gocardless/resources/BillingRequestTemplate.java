package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;
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
    private MandateRequestConstraints mandateRequestConstraints;
    private String mandateRequestCurrency;
    private String mandateRequestDescription;
    private Map<String, String> mandateRequestMetadata;
    private String mandateRequestScheme;
    private String mandateRequestVerify;
    private Map<String, String> metadata;
    private String name;
    private String paymentRequestAmount;
    private String paymentRequestCurrency;
    private String paymentRequestDescription;
    private Map<String, String> paymentRequestMetadata;
    private String paymentRequestScheme;
    private String redirectUri;
    private String updatedAt;

    /**
     * Permanent URL that customers can visit to allow them to complete a flow based on this
     * template, before being returned to the <code>redirect_uri</code>.
     */
    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
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
     * Constraints that will apply to the mandate_request. (Optional) Specifically required for
     * PayTo and VRP.
     */
    public MandateRequestConstraints getMandateRequestConstraints() {
        return mandateRequestConstraints;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     */
    public String getMandateRequestCurrency() {
        return mandateRequestCurrency;
    }

    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     */
    public String getMandateRequestDescription() {
        return mandateRequestDescription;
    }

    /**
     * Key-value store of custom data that will be applied to the mandate created when this request
     * is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and values up to
     * 500 characters.
     */
    public Map<String, String> getMandateRequestMetadata() {
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
     * Verification preference for the mandate.
     */
    public String getMandateRequestVerify() {
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
     * Amount in full.
     */
    public String getPaymentRequestAmount() {
        return paymentRequestAmount;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     * <code>GBP</code> and <code>EUR</code> supported; <code>GBP</code> with your customers in the
     * UK and for <code>EUR</code> with your customers in supported Eurozone countries only.
     */
    public String getPaymentRequestCurrency() {
        return paymentRequestCurrency;
    }

    /**
     * A human-readable description of the payment and/or mandate. This will be displayed to the
     * payer when authorising the billing request.
     */
    public String getPaymentRequestDescription() {
        return paymentRequestDescription;
    }

    /**
     * Key-value store of custom data that will be applied to the payment created when this request
     * is fulfilled. Up to 3 keys are permitted, with key names up to 50 characters and values up to
     * 500 characters.
     */
    public Map<String, String> getPaymentRequestMetadata() {
        return paymentRequestMetadata;
    }

    /**
     * (Optional) A scheme used for Open Banking payments. Currently <code>faster_payments</code> is
     * supported in the UK (GBP) and <code>sepa_credit_transfer</code> and
     * <code>sepa_instant_credit_transfer</code> are supported in supported Eurozone countries
     * (EUR). For Eurozone countries, <code>sepa_credit_transfer</code> is used as the default.
     * Please be aware that <code>sepa_instant_credit_transfer</code> may incur an additional fee
     * for your customer.
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
     * Dynamic <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>
     * recording when this resource was last updated.
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Represents a mandate request constraint resource returned from the API.
     *
     * Constraints that will apply to the mandate_request. (Optional) Specifically required for
     * PayTo and VRP.
     */
    public static class MandateRequestConstraints {
        private MandateRequestConstraints() {
            // blank to prevent instantiation
        }

        private String endDate;
        private Integer maxAmountPerPayment;
        private String paymentMethod;
        private List<PeriodicLimit> periodicLimits;
        private String startDate;

        /**
         * The latest date at which payments can be taken, must occur after start_date if present
         * 
         * This is an optional field and if it is not supplied the agreement will be considered open
         * and will not have an end date. Keep in mind the end date must take into account how long
         * it will take the user to set up this agreement via the Billing Request.
         */
        public String getEndDate() {
            return endDate;
        }

        /**
         * The maximum amount that can be charged for a single payment in the lowest denomination
         * for the currency (e.g. pence in GBP, cents in EUR). <em>Note:</em> Required for PayTo and
         * VRP.
         */
        public Integer getMaxAmountPerPayment() {
            return maxAmountPerPayment;
        }

        /**
         * A constraint where you can specify info (free text string) about how payments are
         * calculated. For use when payments vary and cannot be expressed as a fixed amount and
         * frequency. <em>Note:</em> This is only supported for ACH and PAD schemes.
         */
        public String getPaymentMethod() {
            return paymentMethod;
        }

        /**
         * Caps on the total amount and/or number of payments that can be collected within a
         * repeating period (e.g. no more than a set amount per month), as opposed to
         * <code>max_amount_per_payment</code> which caps a single payment.
         * 
         * <em>Note:</em> Required for VRP, where exactly one periodic limit must be provided.
         * Optional for PayTo.
         */
        public List<PeriodicLimit> getPeriodicLimits() {
            return periodicLimits;
        }

        /**
         * The date from which payments can be taken.
         * 
         * This is an optional field and if it is not supplied the start date will be set to the day
         * authorisation happens.
         */
        public String getStartDate() {
            return startDate;
        }

        /**
         * Represents a periodic limit resource returned from the API.
         *
         * 
         */
        public static class PeriodicLimit {
            private PeriodicLimit() {
                // blank to prevent instantiation
            }

            private Alignment alignment;
            private Integer maxPayments;
            private Integer maxTotalAmount;
            private Period period;

            /**
             * The alignment of the period. Defaults to <code>creation_date</code> if not specified.
             * 
             * <code>calendar</code>
             * <ul>
             * <li>the period follows fixed calendar boundaries, the same for every mandate:</li>
             * </ul>
             * <code>week</code> runs Monday to Sunday, <code>month</code> runs from the 1st to the
             * last day of the calendar month, and <code>year</code> runs from 1 January to 31
             * December. If the mandate starts partway through a period, the limit for that first
             * period is reduced proportionally to the days remaining (e.g. a monthly limit starting
             * on the 15th gives roughly half the limit for that first month).
             * 
             * <code>creation_date</code>
             * <ul>
             * <li>the period follows the mandate's own start date rather than the calendar. For
             * example, if the mandate starts on the 15th, each monthly period runs from the 15th to
             * the 14th of the following month. The first period is a full period, not reduced
             * proportionally.</li>
             * </ul>
             * 
             * <em>Note:</em> Has no effect when period is <code>flexible</code>.
             */
            public Alignment getAlignment() {
                return alignment;
            }

            /**
             * The maximum number of payments that can be collected in this periodic limit.
             * 
             * <em>Note:</em> Only supported for the PayTo scheme, where it is optional.
             */
            public Integer getMaxPayments() {
                return maxPayments;
            }

            /**
             * The maximum total amount that can be charged for all payments in this periodic limit,
             * in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
             * 
             * <em>Note:</em> Required for VRP. This is not permitted for the PayTo scheme.
             */
            public Integer getMaxTotalAmount() {
                return maxTotalAmount;
            }

            /**
             * The repeating period for this mandate. Required whenever a periodic limit is provided
             * (for both VRP and PayTo). If periodic_limits is omitted entirely for PayTo, this
             * defaults to flexible.
             */
            public Period getPeriod() {
                return period;
            }

            public enum Alignment {
                @SerializedName("calendar")
                CALENDAR, @SerializedName("creation_date")
                CREATION_DATE, @SerializedName("unknown")
                UNKNOWN
            }

            public enum Period {
                @SerializedName("day")
                DAY, @SerializedName("week")
                WEEK, @SerializedName("month")
                MONTH, @SerializedName("year")
                YEAR, @SerializedName("flexible")
                FLEXIBLE, @SerializedName("unknown")
                UNKNOWN
            }
        }
    }
}
