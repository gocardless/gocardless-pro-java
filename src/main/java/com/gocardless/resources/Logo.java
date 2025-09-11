





package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


    

    
      /**
       * Represents a logo resource returned from the API.
       *
        * Logos are image uploads that, when associated with a creditor, are shown on the [billing request
* flow](#billing-requests-billing-request-flows) payment pages.
       */
    
    public class Logo {
        private Logo() {
            // blank to prevent instantiation
        }

        
            
            
                private 
    
    

    
        String
    
 id;
            
        

        
            

            
                /**
                 * Unique identifier, beginning with "LO".
                 */
            

            
                public 
    
    

    
        String
    
 getId() {
                    return id;
                }
            
        

        
            

            
        

        
            

            
        
    }

