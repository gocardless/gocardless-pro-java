





package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


    

    
      /**
       * Represents a tax rate resource returned from the API.
       *
        * Tax rates from tax authority.
* 
* We also maintain a [static list of the tax rates for each jurisdiction](#appendix-tax-rates).
       */
    
    public class TaxRate {
        private TaxRate() {
            // blank to prevent instantiation
        }

        
            
            
                private 
    
    

    
        String
    
 endDate;
            
        
            
            
                private 
    
    

    
        String
    
 id;
            
        
            
            
                private 
    
    

    
        String
    
 jurisdiction;
            
        
            
            
                private 
    
    

    
        String
    
 percentage;
            
        
            
            
                private 
    
    

    
        String
    
 startDate;
            
        
            
            
                private 
    
    

    
        String
    
 type;
            
        

        
            

            
                /**
                 * Date at which GoCardless stopped applying the tax rate for the jurisdiction.
                 */
            

            
                public 
    
    

    
        String
    
 getEndDate() {
                    return endDate;
                }
            
        
            

            
                /**
                 * The unique identifier created by the jurisdiction, tax type and version
                 */
            

            
                public 
    
    

    
        String
    
 getId() {
                    return id;
                }
            
        
            

            
                /**
                 * The jurisdiction this tax rate applies to
                 */
            

            
                public 
    
    

    
        String
    
 getJurisdiction() {
                    return jurisdiction;
                }
            
        
            

            
                /**
                 * The percentage of tax that is applied onto of GoCardless fees
                 */
            

            
                public 
    
    

    
        String
    
 getPercentage() {
                    return percentage;
                }
            
        
            

            
                /**
                 * Date at which GoCardless started applying the tax rate in the jurisdiction.
                 */
            

            
                public 
    
    

    
        String
    
 getStartDate() {
                    return startDate;
                }
            
        
            

            
                /**
                 * The type of tax applied by this rate
                 */
            

            
                public 
    
    

    
        String
    
 getType() {
                    return type;
                }
            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
    }

