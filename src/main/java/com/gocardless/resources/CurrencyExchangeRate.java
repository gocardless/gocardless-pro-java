





package com.gocardless.resources;

      /**
       * Represents a currency exchange rate resource returned from the API.
       *
        * Currency exchange rates from our foreign exchange provider.
       */
    
    public class CurrencyExchangeRate {
        private CurrencyExchangeRate() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 rate;
        
            
            private 
    
        String
    
 source;
        
            
            private 
    
        String
    
 target;
        
            
            private 
    
        String
    
 time;
        

        
            

            
                /**
                 * The exchange rate from the source to target currencies provided with up to 10 decimal places.
                 */
            
            public 
    
        String
    
 getRate() {
                return rate;
            }
        
            

            
                /**
                 * Source currency
                 */
            
            public 
    
        String
    
 getSource() {
                return source;
            }
        
            

            
                /**
                 * Target currency
                 */
            
            public 
    
        String
    
 getTarget() {
                return target;
            }
        
            

            
                /**
                 * Time at which the rate was retrieved from the provider.
                 */
            
            public 
    
        String
    
 getTime() {
                return time;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
    }

