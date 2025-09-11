












package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.PayerTheme;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with payer theme resources.
 *
  * Custom colour themes for payment pages and customer notifications.
 */
public class PayerThemeService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#payerThemes() }.
     */
    public PayerThemeService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
        /**
          * Creates a new payer theme associated with a creditor. If a creditor already has payer themes, this
* will update the existing payer theme linked to the creditor.
         */
        public PayerThemeCreateForCreditorRequest
        
        createForCreditor() {
            return new PayerThemeCreateForCreditorRequest
            
            (httpClient
            

            
            );
        }

        
    

    
        
        
        /**
         * Request class for {@link PayerThemeService#createForCreditor }.
         *
          * Creates a new payer theme associated with a creditor. If a creditor already has payer themes, this
* will update the existing payer theme linked to the creditor.
         */
        public static final class PayerThemeCreateForCreditorRequest
        
        extends
        
            PostRequest<PayerTheme>
         {
          

          
              
                  
                  
                      private 
    
        String
    
 buttonBackgroundColour;
                  
              
                  
                  
                      private 
    
        String
    
 contentBoxBorderColour;
                  
              
                  
                  
                      private 
    
        String
    
 headerBackgroundColour;
                  
              
                  
                  
                      private 
    
        String
    
 linkTextColour;
                  
              
                  
                  
                      private 
    
        Links
    
 links;
                  
              

              
                  

                  
                      /**
                       * Colour for buttons background (hexcode)
                       */
                  
                  public 
    PayerThemeCreateForCreditorRequest

                      withButtonBackgroundColour(
    
        String
    
 buttonBackgroundColour) {
                      
                          this.buttonBackgroundColour = buttonBackgroundColour;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Colour for content box border (hexcode)
                       */
                  
                  public 
    PayerThemeCreateForCreditorRequest

                      withContentBoxBorderColour(
    
        String
    
 contentBoxBorderColour) {
                      
                          this.contentBoxBorderColour = contentBoxBorderColour;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Colour for header background (hexcode)
                       */
                  
                  public 
    PayerThemeCreateForCreditorRequest

                      withHeaderBackgroundColour(
    
        String
    
 headerBackgroundColour) {
                      
                          this.headerBackgroundColour = headerBackgroundColour;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Colour for text links (hexcode)
                       */
                  
                  public 
    PayerThemeCreateForCreditorRequest

                      withLinkTextColour(
    
        String
    
 linkTextColour) {
                      
                          this.linkTextColour = linkTextColour;
                      

                      return this;
                  }

                  
              
                  

                  
                  public 
    PayerThemeCreateForCreditorRequest

                      withLinks(
    
        Links
    
 links) {
                      
                          this.links = links;
                      

                      return this;
                  }

                  
                      
                          
                              
                              
                                  /**
                                    * ID of the creditor the payer theme belongs to
                                   */
                              
                              public 
    PayerThemeCreateForCreditorRequest

                                  withLinksCreditor(
                                      
    
        String
    
 creditor
                                  ) {
                                  if (links == null) {
                                      links = new 
    
        Links
    
();
                                  }

                                  links.withCreditor(creditor);
                                  return this;
                              }
                          
                      
                  
              

              
                  
                      
                  
              
          

          private PayerThemeCreateForCreditorRequest(HttpClient httpClient
              
              
          ) {

              
                  super(httpClient);
              

              
          }

              public 
    PayerThemeCreateForCreditorRequest
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          

          @Override
          protected String getPathTemplate() {
              return "branding/payer_themes";
          }

          @Override
          protected String getEnvelope() {
              return "payer_themes";
          }

          
              @Override
              protected Class<PayerTheme> getResponseClass() {
                  return PayerTheme.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  
                      
    

    
        

        
    

    public static class Links {
        
            
            private 
    
        String
    
 creditor;
        

        
            
            
                /**
                 * ID of the creditor the payer theme belongs to
                 */
            
            public Links withCreditor(
    
        String
    
 creditor) {
                this.creditor = creditor;
                return this;
            }
        

        

        
    
        

        
    

    }

                  

                  
              
          
        }
    
}
