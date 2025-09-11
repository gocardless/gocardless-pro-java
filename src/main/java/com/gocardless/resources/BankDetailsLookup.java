





package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


    

    
      /**
       * Represents a bank details lookup resource returned from the API.
       *
        * Look up the name and reachability of a bank account.
       */
    
    public class BankDetailsLookup {
        private BankDetailsLookup() {
            // blank to prevent instantiation
        }

        
            
            
                private 
    
    

    
      List<
    
    

    
        
            AvailableDebitScheme
        
    
>
    
 availableDebitSchemes;
            
        
            
            
                private 
    
    

    
        String
    
 bankName;
            
        
            
            
                private 
    
    

    
        String
    
 bic;
            
        

        
            

            
                /**
                 * Array of [schemes](#mandates_scheme) supported for this bank account. This will be an empty array
* if the bank account is not reachable by any schemes.
                 */
            

            
                public 
    
    

    
      List<
    
    

    
        
            AvailableDebitScheme
        
    
>
    
 getAvailableDebitSchemes() {
                    return availableDebitSchemes;
                }
            
        
            

            
                /**
                 * The name of the bank with which the account is held (if available).
                 */
            

            
                public 
    
    

    
        String
    
 getBankName() {
                    return bankName;
                }
            
        
            

            
                /**
                 * ISO 9362 SWIFT BIC of the bank with which the account is held.
* 
* <p class="notice">Even if no BIC is returned for an account, GoCardless may still be able to
* collect payments from it - you should refer to the `available_debit_schemes` attribute to
* determine reachability.</p>
                 */
            

            
                public 
    
    

    
        String
    
 getBic() {
                    return bic;
                }
            
        

        
            

            
                
                
                    
    
    

    public enum 
    
    

    
        
            AvailableDebitScheme
        
    
 {
        
            @SerializedName("ach") ACH,
        
            @SerializedName("autogiro") AUTOGIRO,
        
            @SerializedName("bacs") BACS,
        
            @SerializedName("becs") BECS,
        
            @SerializedName("becs_nz") BECS_NZ,
        
            @SerializedName("betalingsservice") BETALINGSSERVICE,
        
            @SerializedName("faster_payments") FASTER_PAYMENTS,
        
            @SerializedName("pad") PAD,
        
            @SerializedName("pay_to") PAY_TO,
        
            @SerializedName("sepa_core") SEPA_CORE,
        
        @SerializedName("unknown") UNKNOWN
    }

                
            
        
            

            
        
            

            
        

        
            

            
                
                
            
        
            

            
        
            

            
        
    }

