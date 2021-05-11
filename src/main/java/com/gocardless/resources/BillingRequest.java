





package com.gocardless.resources;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

      /**
       * Represents a billing request resource returned from the API.
       *
        * Billing Requests
       */
    
    public class BillingRequest {
        private BillingRequest() {
            // blank to prevent instantiation
        }

        
            
            private 
    
      List<
    
        Action
    
>
    
 actions;
        
            
            private 
    
        String
    
 createdAt;
        
            
            private 
    
        String
    
 id;
        
            
            private 
    
        Links
    
 links;
        
            
            private 
    
        MandateRequest
    
 mandateRequest;
        
            
            private 
    
        Map<String, String>
    
 metadata;
        
            
            private 
    
        PaymentRequest
    
 paymentRequest;
        
            
            private 
    
        Resources
    
 resources;
        
            
            private 
    
        Status
    
 status;
        

        
            

            
                /**
                 * List of actions that can be performed before this billing request can be fulfilled.
                 */
            
            public 
    
      List<
    
        Action
    
>
    
 getActions() {
                return actions;
            }
        
            

            
                /**
                 * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
                 */
            
            public 
    
        String
    
 getCreatedAt() {
                return createdAt;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "BRQ".
                 */
            
            public 
    
        String
    
 getId() {
                return id;
            }
        
            

            
            public 
    
        Links
    
 getLinks() {
                return links;
            }
        
            

            
                /**
                 * Request for a mandate
                 */
            
            public 
    
        MandateRequest
    
 getMandateRequest() {
                return mandateRequest;
            }
        
            

            
                /**
                 * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                 */
            
            public 
    
        Map<String, String>
    
 getMetadata() {
                return metadata;
            }
        
            

            
                /**
                 * Request for a one-off strongly authorised payment
                 */
            
            public 
    
        PaymentRequest
    
 getPaymentRequest() {
                return paymentRequest;
            }
        
            

            
            public 
    
        Resources
    
 getResources() {
                return resources;
            }
        
            

            
                /**
                 * One of:
* <ul>
* <li>`pending`: the billing_request is pending and can be used</li>
* <li>`ready_to_fulfil`: the billing_request is ready to fulfil</li>
* <li>`fulfilled`: the billing_request has been fulfilled and a payment created</li>
* <li>`cancelled`: the billing_request has been cancelled and cannot be used</li>
* </ul>
                 */
            
            public 
    
        Status
    
 getStatus() {
                return status;
            }
        

        
            

            
                
                
            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            
                
    
    public enum 
    
        Status
    
 {
        
            @SerializedName("pending") PENDING,
        
            @SerializedName("ready_to_fulfil") READY_TO_FULFIL,
        
            @SerializedName("fulfilled") FULFILLED,
        
            @SerializedName("cancelled") CANCELLED,
        
    }

            

            
        

        
            

            
                
                
                    
    

    
    public static class Action {
        private Action() {
            // blank to prevent instantiation
        }

        
            
            private 
    
      List<
    
        String
    
>
    
 completesActions;
        
            
            private 
    
        Boolean
    
 required;
        
            
            private 
    
      List<
    
        String
    
>
    
 requiresActions;
        
            
            private 
    
        Status
    
 status;
        
            
            private 
    
        Type
    
 type;
        

        
            

            
                /**
                 * Which other action types this action can complete.
                 */
            
            public 
    
      List<
    
        String
    
>
    
 getCompletesActions() {
                return completesActions;
            }
        
            

            
                /**
                 * Informs you whether the action is required to fulfil the billing request or not.
                 */
            
            public 
    
        Boolean
    
 getRequired() {
                return required;
            }
        
            

            
                /**
                 * Requires completing these actions before this action can be completed.
                 */
            
            public 
    
      List<
    
        String
    
>
    
 getRequiresActions() {
                return requiresActions;
            }
        
            

            
                /**
                 * Status of the action
                 */
            
            public 
    
        Status
    
 getStatus() {
                return status;
            }
        
            

            
                /**
                 * Unique identifier for the action.
                 */
            
            public 
    
        Type
    
 getType() {
                return type;
            }
        

        
            

            
                
                
            
        
            

            
        
            

            
                
                
            
        
            
                
    
    public enum 
    
        Status
    
 {
        
            @SerializedName("pending") PENDING,
        
            @SerializedName("completed") COMPLETED,
        
    }

            

            
        
            
                
    
    public enum 
    
        Type
    
 {
        
            @SerializedName("choose_currency") CHOOSE_CURRENCY,
        
            @SerializedName("collect_customer_details") COLLECT_CUSTOMER_DETAILS,
        
            @SerializedName("collect_bank_account") COLLECT_BANK_ACCOUNT,
        
            @SerializedName("bank_authorisation") BANK_AUTHORISATION,
        
    }

            

            
        

        
            

            
                
                
            
        
            

            
        
            

            
                
                
            
        
            

            
        
            

            
        
    }

                
            
        
            

            
        
            

            
        
            
                
    

    
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 bankAuthorisation;
        
            
            private 
    
        String
    
 creditor;
        
            
            private 
    
        String
    
 customer;
        
            
            private 
    
        String
    
 customerBankAccount;
        
            
            private 
    
        String
    
 customerBillingDetail;
        

        
            

            
                /**
                 * (Optional) ID of the [bank authorisation](#billing-requests-bank-authorisations) that was used to
* verify this request.
                 */
            
            public 
    
        String
    
 getBankAuthorisation() {
                return bankAuthorisation;
            }
        
            

            
                /**
                 * ID of the associated [creditor](#core-endpoints-creditors).
                 */
            
            public 
    
        String
    
 getCreditor() {
                return creditor;
            }
        
            

            
                /**
                 * ID of the [customer](#core-endpoints-customers) that will be used for this request
                 */
            
            public 
    
        String
    
 getCustomer() {
                return customer;
            }
        
            

            
                /**
                 * (Optional) ID of the [customer_bank_account](#core-endpoints-customer-bank-accounts) that will be
* used for this request
                 */
            
            public 
    
        String
    
 getCustomerBankAccount() {
                return customerBankAccount;
            }
        
            

            
                /**
                 * ID of the customer billing detail that will be used for this request
                 */
            
            public 
    
        String
    
 getCustomerBillingDetail() {
                return customerBillingDetail;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
    }

            

            
        
            
                
    

    
      /**
       * Represents a mandate request resource returned from the API.
       *
        * Request for a mandate
       */
    
    public static class MandateRequest {
        private MandateRequest() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 currency;
        
            
            private 
    
        Links
    
 links;
        
            
            private 
    
        String
    
 scheme;
        

        
            

            
                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently only "GBP"
* is supported as we only have one scheme that is per_payment_authorised.
                 */
            
            public 
    
        String
    
 getCurrency() {
                return currency;
            }
        
            

            
            public 
    
        Links
    
 getLinks() {
                return links;
            }
        
            

            
                /**
                 * A Direct Debit scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz", "betalingsservice",
* "pad" and "sepa_core" are supported.
                 */
            
            public 
    
        String
    
 getScheme() {
                return scheme;
            }
        

        
            

            
        
            

            
        
            

            
        

        
            

            
        
            
                
    

    
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 mandate;
        

        
            

            
                /**
                 * (Optional) ID of the [mandate](#core-endpoints-mandates) that was created from this mandate
* request. this mandate request.
* 
                 */
            
            public 
    
        String
    
 getMandate() {
                return mandate;
            }
        

        
            

            
        

        
            

            
        
    }

            

            
        
            

            
        
    }

            

            
        
            

            
        
            
                
    

    
      /**
       * Represents a payment request resource returned from the API.
       *
        * Request for a one-off strongly authorised payment
       */
    
    public static class PaymentRequest {
        private PaymentRequest() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        Integer
    
 amount;
        
            
            private 
    
        String
    
 currency;
        
            
            private 
    
        String
    
 description;
        
            
            private 
    
        Links
    
 links;
        
            
            private 
    
        String
    
 scheme;
        

        
            

            
                /**
                 * Amount in minor unit (e.g. pence in GBP, cents in EUR).
                 */
            
            public 
    
        Integer
    
 getAmount() {
                return amount;
            }
        
            

            
                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently only "GBP"
* is supported as we only have one scheme that is per_payment_authorised.
                 */
            
            public 
    
        String
    
 getCurrency() {
                return currency;
            }
        
            

            
                /**
                 * A human-readable description of the payment. This will be displayed to the payer when authorising
* the billing request.
* 
                 */
            
            public 
    
        String
    
 getDescription() {
                return description;
            }
        
            

            
            public 
    
        Links
    
 getLinks() {
                return links;
            }
        
            

            
                /**
                 * A Direct Debit scheme. Currently "ach", "autogiro", "bacs", "becs", "becs_nz", "betalingsservice",
* "pad" and "sepa_core" are supported.
                 */
            
            public 
    
        String
    
 getScheme() {
                return scheme;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            
                
    

    
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 payment;
        

        
            

            
                /**
                 * (Optional) ID of the [payment](#core-endpoints-payments) that was created from this payment
* request.
                 */
            
            public 
    
        String
    
 getPayment() {
                return payment;
            }
        

        
            

            
        

        
            

            
        
    }

            

            
        
            

            
        
    }

            

            
        
            
                
    

    
    public static class Resources {
        private Resources() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        Customer
    
 customer;
        
            
            private 
    
        CustomerBankAccount
    
 customerBankAccount;
        
            
            private 
    
        CustomerBillingDetail
    
 customerBillingDetail;
        

        
            

            
                /**
                 * Embedded customer
                 */
            
            public 
    
        Customer
    
 getCustomer() {
                return customer;
            }
        
            

            
                /**
                 * Embedded customer bank account, only if a bank account is linked
                 */
            
            public 
    
        CustomerBankAccount
    
 getCustomerBankAccount() {
                return customerBankAccount;
            }
        
            

            
                /**
                 * Embedded customer billing detail
                 */
            
            public 
    
        CustomerBillingDetail
    
 getCustomerBillingDetail() {
                return customerBillingDetail;
            }
        

        
            

            
        
            

            
        
            

            
        

        
            
                
    

    
      /**
       * Represents a customer resource returned from the API.
       *
        * Embedded customer
       */
    
    public static class Customer {
        private Customer() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 companyName;
        
            
            private 
    
        String
    
 createdAt;
        
            
            private 
    
        String
    
 email;
        
            
            private 
    
        String
    
 familyName;
        
            
            private 
    
        String
    
 givenName;
        
            
            private 
    
        String
    
 id;
        
            
            private 
    
        String
    
 language;
        
            
            private 
    
        Map<String, String>
    
 metadata;
        
            
            private 
    
        String
    
 phoneNumber;
        

        
            

            
                /**
                 * Customer's company name. Required unless a `given_name` and `family_name` are provided. For
* Canadian customers, the use of a `company_name` value will mean that any mandate created from this
* customer will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
* be a "Personal PAD").
                 */
            
            public 
    
        String
    
 getCompanyName() {
                return companyName;
            }
        
            

            
                /**
                 * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
                 */
            
            public 
    
        String
    
 getCreatedAt() {
                return createdAt;
            }
        
            

            
                /**
                 * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
* to this customer.
                 */
            
            public 
    
        String
    
 getEmail() {
                return email;
            }
        
            

            
                /**
                 * Customer's surname. Required unless a `company_name` is provided.
                 */
            
            public 
    
        String
    
 getFamilyName() {
                return familyName;
            }
        
            

            
                /**
                 * Customer's first name. Required unless a `company_name` is provided.
                 */
            
            public 
    
        String
    
 getGivenName() {
                return givenName;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "CU".
                 */
            
            public 
    
        String
    
 getId() {
                return id;
            }
        
            

            
                /**
                 * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
* notification emails sent by GoCardless if your organisation does not send its own (see [compliance
* requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
* "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
* chosen based on the `country_code` (if supplied) or default to "en".
                 */
            
            public 
    
        String
    
 getLanguage() {
                return language;
            }
        
            

            
                /**
                 * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                 */
            
            public 
    
        Map<String, String>
    
 getMetadata() {
                return metadata;
            }
        
            

            
                /**
                 * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including country code.
                 */
            
            public 
    
        String
    
 getPhoneNumber() {
                return phoneNumber;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
    }

            

            
        
            
                
    

    
      /**
       * Represents a customer bank account resource returned from the API.
       *
        * Embedded customer bank account, only if a bank account is linked
       */
    
    public static class CustomerBankAccount {
        private CustomerBankAccount() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 accountHolderName;
        
            
            private 
    
        String
    
 accountNumberEnding;
        
            
            private 
    
        AccountType
    
 accountType;
        
            
            private 
    
        String
    
 bankName;
        
            
            private 
    
        String
    
 countryCode;
        
            
            private 
    
        String
    
 createdAt;
        
            
            private 
    
        String
    
 currency;
        
            
            private 
    
        Boolean
    
 enabled;
        
            
            private 
    
        String
    
 id;
        
            
            private 
    
        Links
    
 links;
        
            
            private 
    
        Map<String, String>
    
 metadata;
        

        
            

            
                /**
                 * Name of the account holder, as known by the bank. Usually this is the same as the name stored with
* the linked [creditor](#core-endpoints-creditors). This field will be transliterated, upcased and
* truncated to 18 characters. This field is required unless the request includes a [customer bank
* account token](#javascript-flow-customer-bank-account-tokens).
                 */
            
            public 
    
        String
    
 getAccountHolderName() {
                return accountHolderName;
            }
        
            

            
                /**
                 * The last few digits of the account number. Currently 4 digits for NZD bank accounts and 2 digits
* for other currencies.
                 */
            
            public 
    
        String
    
 getAccountNumberEnding() {
                return accountNumberEnding;
            }
        
            

            
                /**
                 * Bank account type. Required for USD-denominated bank accounts. Must not be provided for bank
* accounts in other currencies. See [local details](#local-bank-details-united-states) for more
* information.
                 */
            
            public 
    
        AccountType
    
 getAccountType() {
                return accountType;
            }
        
            

            
                /**
                 * Name of bank, taken from the bank details.
                 */
            
            public 
    
        String
    
 getBankName() {
                return bankName;
            }
        
            

            
                /**
                 * [ISO 3166-1 alpha-2
* code](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements). Defaults
* to the country code of the `iban` if supplied, otherwise is required.
                 */
            
            public 
    
        String
    
 getCountryCode() {
                return countryCode;
            }
        
            

            
                /**
                 * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
                 */
            
            public 
    
        String
    
 getCreatedAt() {
                return createdAt;
            }
        
            

            
                /**
                 * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
* "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
                 */
            
            public 
    
        String
    
 getCurrency() {
                return currency;
            }
        
            

            
                /**
                 * Boolean value showing whether the bank account is enabled or disabled.
                 */
            
            public 
    
        Boolean
    
 getEnabled() {
                return enabled;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "BA".
                 */
            
            public 
    
        String
    
 getId() {
                return id;
            }
        
            

            
            public 
    
        Links
    
 getLinks() {
                return links;
            }
        
            

            
                /**
                 * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                 */
            
            public 
    
        Map<String, String>
    
 getMetadata() {
                return metadata;
            }
        

        
            

            
        
            

            
        
            
                
    
    public enum 
    
        AccountType
    
 {
        
            @SerializedName("savings") SAVINGS,
        
            @SerializedName("checking") CHECKING,
        
    }

            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            
                
    

    
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 customer;
        

        
            

            
                /**
                 * ID of the [customer](#core-endpoints-customers) that owns this bank account.
                 */
            
            public 
    
        String
    
 getCustomer() {
                return customer;
            }
        

        
            

            
        

        
            

            
        
    }

            

            
        
            

            
        
    }

            

            
        
            
                
    

    
      /**
       * Represents a customer billing detail resource returned from the API.
       *
        * Embedded customer billing detail
       */
    
    public static class CustomerBillingDetail {
        private CustomerBillingDetail() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 addressLine1;
        
            
            private 
    
        String
    
 addressLine2;
        
            
            private 
    
        String
    
 addressLine3;
        
            
            private 
    
        String
    
 city;
        
            
            private 
    
        String
    
 countryCode;
        
            
            private 
    
        String
    
 createdAt;
        
            
            private 
    
        String
    
 danishIdentityNumber;
        
            
            private 
    
        String
    
 id;
        
            
            private 
    
        String
    
 postalCode;
        
            
            private 
    
        String
    
 region;
        
            
            private 
    
      List<
    
        String
    
>
    
 schemes;
        
            
            private 
    
        String
    
 swedishIdentityNumber;
        

        
            

            
                /**
                 * The first line of the customer's address.
                 */
            
            public 
    
        String
    
 getAddressLine1() {
                return addressLine1;
            }
        
            

            
                /**
                 * The second line of the customer's address.
                 */
            
            public 
    
        String
    
 getAddressLine2() {
                return addressLine2;
            }
        
            

            
                /**
                 * The third line of the customer's address.
                 */
            
            public 
    
        String
    
 getAddressLine3() {
                return addressLine3;
            }
        
            

            
                /**
                 * The city of the customer's address.
                 */
            
            public 
    
        String
    
 getCity() {
                return city;
            }
        
            

            
                /**
                 * [ISO 3166-1 alpha-2
* code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
                 */
            
            public 
    
        String
    
 getCountryCode() {
                return countryCode;
            }
        
            

            
                /**
                 * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
                 */
            
            public 
    
        String
    
 getCreatedAt() {
                return createdAt;
            }
        
            

            
                /**
                 * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
* if the customer's bank account is denominated in Danish krone (DKK).
                 */
            
            public 
    
        String
    
 getDanishIdentityNumber() {
                return danishIdentityNumber;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "CU".
                 */
            
            public 
    
        String
    
 getId() {
                return id;
            }
        
            

            
                /**
                 * The customer's postal code.
                 */
            
            public 
    
        String
    
 getPostalCode() {
                return postalCode;
            }
        
            

            
                /**
                 * The customer's address region, county or department. For US customers a 2 letter
* [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g. `CA` for
* California).
                 */
            
            public 
    
        String
    
 getRegion() {
                return region;
            }
        
            

            
                /**
                 * The schemes associated with this customer billing detail
                 */
            
            public 
    
      List<
    
        String
    
>
    
 getSchemes() {
                return schemes;
            }
        
            

            
                /**
                 * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
* organisationsnummer) of the customer. Must be supplied if the customer's bank account is
* denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
                 */
            
            public 
    
        String
    
 getSwedishIdentityNumber() {
                return swedishIdentityNumber;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
                
                
            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
                
                
            
        
            

            
        
    }

            

            
        
    }

            

            
        
            

            
        
    }

