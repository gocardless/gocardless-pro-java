package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents a payout item resource returned from the API.
 *
 * When we collect a payment on your behalf, we add the money you've collected to your GoCardless
 * balance, minus any fees paid. Periodically (usually every working day), we take any positive
 * balance in your GoCardless account, and pay it out to your nominated bank account.
 * 
 * Other actions in your GoCardless account can also affect your balance. For example, if a customer
 * charges back a payment, we'll deduct the payment's amount from your balance, but add any fees you
 * paid for that payment back to your balance.
 * 
 * The Payout Items API allows you to view, on a per-payout basis, the credit and debit items that
 * make up that payout's amount. Payout items can only be retrieved for payouts created in the last
 * 6 months. Requests for older payouts will return an HTTP status <code>410 Gone</code>.
 */
public class PayoutItem {
    private PayoutItem() {
        // blank to prevent instantiation
    }

    private String amount;
    private Links links;
    private List<Tax> taxes;
    private Type type;

    /**
     * The positive (credit) or negative (debit) value of the item, in fractional currency; the
     * lowest denomination for the currency (e.g. pence in GBP, cents in EUR), to one decimal place.
     * 
     * <p class="notice">
     * For accuracy, we store some of our fees to greater precision than we can actually pay out
     * (for example, a GoCardless fee we record might come to 0.5 pence, but it is not possible to
     * send a payout via bank transfer including a half penny).<br>
     * </br>
     * <br>
     * </br>
     * To calculate the final amount of the payout, we sum all of the items and then round to the
     * nearest currency unit.
     * </p>
     */
    public String getAmount() {
        return amount;
    }

    public Links getLinks() {
        return links;
    }

    /**
     * An array of tax items <em>beta</em>
     * 
     * <em>Note</em>: VAT applies to transaction and surcharge fees for merchants operating in the
     * UK and France.
     */
    public List<Tax> getTaxes() {
        return taxes;
    }

    /**
     * The type of the credit (positive) or debit (negative) item in the payout (inclusive of VAT if
     * applicable). One of:
     * 
     * <ul>
     * <li><code>payment_paid_out</code> (credit)</li>
     * <li><code>payment_failed</code> (debit): The payment failed to be processed.</li>
     * <li><code>payment_charged_back</code> (debit): The payment has been charged back.</li>
     * <li><code>payment_refunded</code> (debit): The payment has been refunded to the
     * customer.</li>
     * <li><code>refund</code> (debit): A refund sent to a customer, not linked to a payment.</li>
     * <li><code>refund_funds_returned</code> (credit): The refund could not be sent to the
     * customer, and the funds have been returned to you.</li>
     * <li><code>gocardless_fee</code> (credit/debit): The fees that GoCardless charged for a
     * payment. In the case of a payment failure or chargeback, these will appear as credits. Will
     * include taxes if applicable for merchants.</li>
     * <li><code>app_fee</code> (credit/debit): The optional fees that a partner may have taken for
     * a payment. In the case of a payment failure or chargeback, these will appear as credits.</li>
     * <li><code>revenue_share</code> (credit/debit): A share of the fees that GoCardless collected
     * which some partner integrations receive when their users take payments. Only shown in partner
     * payouts. In the case of a payment failure or chargeback, these will appear as credits.</li>
     * <li><code>surcharge_fee</code> (credit/debit): GoCardless deducted a surcharge fee as the
     * payment failed or was charged back, or refunded a surcharge fee as the bank or customer
     * cancelled the chargeback. Will include taxes if applicable for merchants.</li>
     * </ul>
     */
    public Type getType() {
        return type;
    }

    public enum Type {
        @SerializedName("payment_paid_out")
        PAYMENT_PAID_OUT, @SerializedName("payment_failed")
        PAYMENT_FAILED, @SerializedName("payment_charged_back")
        PAYMENT_CHARGED_BACK, @SerializedName("payment_refunded")
        PAYMENT_REFUNDED, @SerializedName("refund")
        REFUND, @SerializedName("gocardless_fee")
        GOCARDLESS_FEE, @SerializedName("app_fee")
        APP_FEE, @SerializedName("revenue_share")
        REVENUE_SHARE, @SerializedName("surcharge_fee")
        SURCHARGE_FEE, @SerializedName("refund_funds_returned")
        REFUND_FUNDS_RETURNED, @SerializedName("unknown")
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

        private String mandate;
        private String payment;
        private String refund;

        /**
         * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates
         * created before 2016. Present only for the items of type <code>payment_refunded</code>,
         * <code>refund</code> and <code>refund_funds_returned</code>.
         */
        public String getMandate() {
            return mandate;
        }

        /**
         * Unique identifier, beginning with "PM".
         */
        public String getPayment() {
            return payment;
        }

        /**
         * Unique identifier, beginning with "RF". Present only for the items of type
         * <code>payment_refunded</code>, <code>refund</code> and
         * <code>refund_funds_returned</code>.
         */
        public String getRefund() {
            return refund;
        }
    }

    /**
     * Represents a tax resource returned from the API.
     *
     * 
     */
    public static class Tax {
        private Tax() {
            // blank to prevent instantiation
        }

        private String amount;
        private Currency currency;
        private String destinationAmount;
        private String destinationCurrency;
        private String exchangeRate;
        private String taxRateId;

        /**
         * The amount of tax applied to a fee in fractional currency; the lowest denomination for
         * the currency (e.g. pence in GBP, cents in EUR), to one decimal place.
         */
        public String getAmount() {
            return amount;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
         * Currently "AUD", "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
         */
        public Currency getCurrency() {
            return currency;
        }

        /**
         * The amount of tax to be paid out to the tax authorities in fractional currency; the
         * lowest denomination for the currency (e.g. pence in GBP, cents in EUR), to one decimal
         * place.
         * 
         * When <code>currency</code> and <code>destination_currency</code> don't match this will be
         * <code>null</code> until the <code>exchange_rate</code> has been finalised.
         */
        public String getDestinationAmount() {
            return destinationAmount;
        }

        /**
         * <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> code for the
         * currency in which tax is paid out to the tax authorities of your tax jurisdiction.
         * Currently “EUR” for French merchants and “GBP” for British merchants.
         */
        public String getDestinationCurrency() {
            return destinationCurrency;
        }

        /**
         * The exchange rate for the tax from the currency into the destination currency.
         * 
         * Present only if the currency and the destination currency don't match and the exchange
         * rate has been finalised.
         * 
         * You can listen for the payout's <a href=
         * "https://developer.gocardless.com/api-reference/#event-types-payout"><code>tax_exchange_rates_confirmed</code>
         * webhook</a> to know when the exchange rate has been finalised for all fees in the payout.
         */
        public String getExchangeRate() {
            return exchangeRate;
        }

        /**
         * The unique identifier created by the jurisdiction, tax type and version
         */
        public String getTaxRateId() {
            return taxRateId;
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
    }
}
