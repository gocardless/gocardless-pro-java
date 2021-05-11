





package com.gocardless.resources;

import java.util.List;

import com.google.gson.annotations.SerializedName;

      /**
       * Represents a payout item resource returned from the API.
       *
        * When we collect a payment on your behalf, we add the money you've collected to your
* GoCardless balance, minus any fees paid. Periodically (usually every working day),
* we take any positive balance in your GoCardless account, and pay it out to your
* nominated bank account.
* 
* Other actions in your GoCardless account can also affect your balance. For example,
* if a customer charges back a payment, we'll deduct the payment's amount from your
* balance, but add any fees you paid for that payment back to your balance.
* 
* The Payout Items API allows you to view, on a per-payout basis, the credit and debit
* items that make up that payout's amount.
* 
       */
    
    public class PayoutItem {
        private PayoutItem() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 amount;
        
            
            private 
    
        Links
    
 links;
        
            
            private 
    
      List<
    
        Taxis
    
>
    
 taxes;
        
            
            private 
    
        Type
    
 type;
        

        
            

            
                /**
                 * The positive (credit) or negative (debit) value of the item, in fractional currency;
* the lowest denomination for the currency (e.g. pence in GBP, cents in EUR), to one decimal place.
* <p class="notice">For accuracy, we store some of our fees to greater precision than we can
* actually pay out (for example, a GoCardless fee we record might come to 0.5 pence, but it is not
* possible to send a payout via bank transfer including a half penny).<br><br>To calculate the final
* amount of the payout, we sum all of the items and then round to the nearest currency unit.</p>
                 */
            
            public 
    
        String
    
 getAmount() {
                return amount;
            }
        
            

            
            public 
    
        Links
    
 getLinks() {
                return links;
            }
        
            

            
                /**
                 * An array of tax items <em>beta</em>
* 
* _Note_: VAT applies to transaction and surcharge fees for merchants operating in the <a
* href="https://gocardless.com/legal/vat-faqs">UK</a> and <a
* href="https://gocardless.com/fr-fr/legal/faq-tva">France</a>.
                 */
            
            public 
    
      List<
    
        Taxis
    
>
    
 getTaxes() {
                return taxes;
            }
        
            

            
                /**
                 * The type of the credit (positive) or debit (negative) item in the payout (inclusive of VAT if
* applicable). One of:
* <ul>
* <li>`payment_paid_out` (credit)</li>
* <li>`payment_failed` (debit): The payment failed to be processed.</li>
* <li>`payment_charged_back` (debit): The payment has been charged back.</li>
* <li>`payment_refunded` (debit): The payment has been refunded to the customer.</li>
* <li>`refund` (debit): A refund sent to a customer, not linked to a payment.</li>
* <li>`refund_funds_returned` (credit): The refund could not be sent to the customer, and the funds
* have been returned to you.</li>
* <li>`gocardless_fee` (credit/debit): The fees that GoCardless charged for a payment. In the case
* of a payment failure or chargeback, these will appear as credits. Will include taxes if applicable
* for merchants.</li>
* <li>`app_fee` (credit/debit): The optional fees that a partner may have taken for a payment. In
* the case of a payment failure or chargeback, these will appear as credits.</li>
* <li>`revenue_share` (credit/debit): A share of the fees that GoCardless collected which some
* partner integrations receive when their users take payments. Only shown in partner payouts. In the
* case of a payment failure or chargeback, these will appear as credits.</li>
* <li>`surcharge_fee` (credit/debit): GoCardless deducted a surcharge fee as the payment failed or
* was charged back, or refunded a surcharge fee as the bank or customer cancelled the chargeback.
* Will include taxes if applicable for merchants.</li>
* </ul>
* 
                 */
            
            public 
    
        Type
    
 getType() {
                return type;
            }
        

        
            

            
        
            

            
        
            

            
                
                
            
        
            
                
    
    public enum 
    
        Type
    
 {
        
            @SerializedName("payment_paid_out") PAYMENT_PAID_OUT,
        
            @SerializedName("payment_failed") PAYMENT_FAILED,
        
            @SerializedName("payment_charged_back") PAYMENT_CHARGED_BACK,
        
            @SerializedName("payment_refunded") PAYMENT_REFUNDED,
        
            @SerializedName("refund") REFUND,
        
            @SerializedName("gocardless_fee") GOCARDLESS_FEE,
        
            @SerializedName("app_fee") APP_FEE,
        
            @SerializedName("revenue_share") REVENUE_SHARE,
        
            @SerializedName("surcharge_fee") SURCHARGE_FEE,
        
    }

            

            
        

        
            

            
        
            
                
    

    
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 mandate;
        
            
            private 
    
        String
    
 payment;
        
            
            private 
    
        String
    
 refund;
        

        
            

            
                /**
                 * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates created
* before 2016.
                 */
            
            public 
    
        String
    
 getMandate() {
                return mandate;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "PM".
                 */
            
            public 
    
        String
    
 getPayment() {
                return payment;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "RF".
                 */
            
            public 
    
        String
    
 getRefund() {
                return refund;
            }
        

        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
    }

            

            
        
            

            
                
                
                    
    

    
    public static class Taxis {
        private Taxis() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 amount;
        
            
            private 
    
        Currency
    
 currency;
        
            
            private 
    
        String
    
 destinationAmount;
        
            
            private 
    
        String
    
 destinationCurrency;
        
            
            private 
    
        String
    
 exchangeRate;
        
            
            private 
    
        String
    
 taxRateId;
        

        
            

            
                /**
                 * The amount of tax applied to a fee in fractional currency; the lowest denomination for the
* currency (e.g. pence in GBP, cents in EUR), to one decimal place.
                 */
            
            public 
    
        String
    
 getAmount() {
                return amount;
            }
        
            

            
                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
* "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
                 */
            
            public 
    
        Currency
    
 getCurrency() {
                return currency;
            }
        
            

            
                /**
                 * The amount of tax to be paid out to the tax authorities in fractional currency; the lowest
* denomination for the currency (e.g. pence in GBP, cents in EUR), to one decimal place.
* 
* When `currency` and `destination_currency` don't match this will be `null` until the
* `exchange_rate` has been finalised.
                 */
            
            public 
    
        String
    
 getDestinationAmount() {
                return destinationAmount;
            }
        
            

            
                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) code for the currency in which tax
* is paid out to the tax authorities of your tax jurisdiction. Currently “EUR” for French merchants
* and “GBP” for British merchants.
                 */
            
            public 
    
        String
    
 getDestinationCurrency() {
                return destinationCurrency;
            }
        
            

            
                /**
                 * The exchange rate for the tax from the currency into the destination currency.
* 
* Present only if the currency and the destination currency don't match and the exchange rate has
* been finalised.
* 
* You can listen for the payout's [`tax_exchange_rates_confirmed`
* webhook](https://developer.gocardless.com/api-reference/#event-actions-payout) to know when the
* exchange rate has been finalised for all fees in the payout.
                 */
            
            public 
    
        String
    
 getExchangeRate() {
                return exchangeRate;
            }
        
            

            
                /**
                 * The unique identifier created by the jurisdiction, tax type and version
                 */
            
            public 
    
        String
    
 getTaxRateId() {
                return taxRateId;
            }
        

        
            

            
        
            
                
    
    public enum 
    
        Currency
    
 {
        
            @SerializedName("AUD") AUD,
        
            @SerializedName("CAD") CAD,
        
            @SerializedName("DKK") DKK,
        
            @SerializedName("EUR") EUR,
        
            @SerializedName("GBP") GBP,
        
            @SerializedName("NZD") NZD,
        
            @SerializedName("SEK") SEK,
        
            @SerializedName("USD") USD,
        
    }

            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
    }

                
            
        
            

            
        
    }

