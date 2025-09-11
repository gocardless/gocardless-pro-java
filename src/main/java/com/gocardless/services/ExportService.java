












package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Export;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with export resources.
 *
  * File-based exports of data
 */
public class ExportService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#exports() }.
     */
    public ExportService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
        /**
          * Returns a single export.
         */
        public ExportGetRequest
        
        get(String identity) {
            return new ExportGetRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        
        /**
          * Returns a list of exports which are available for download.
         */
        public ExportListRequest
        
            <ListResponse<Export>>
        
        list() {
            return new ExportListRequest
            
                <>
            
            (httpClient
            
                , ListRequest.<Export>pagingExecutor()
            

            
            );
        }

        
            public ExportListRequest<Iterable<Export>> all() {
                return new ExportListRequest<>(httpClient, ListRequest.<Export>iteratingExecutor()

                
                );
            }
        
    

    
        
        
        /**
         * Request class for {@link ExportService#get }.
         *
          * Returns a single export.
         */
        public static final class ExportGetRequest
        
        extends
        
            GetRequest<Export>
         {
          
              @PathParam
              private final String identity;
          

          

          private ExportGetRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    ExportGetRequest
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          
              @Override
              protected Map<String, String> getPathParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  
                      params.put("identity", identity);
                  

                  return params.build();
              }
          

          
              
          

          @Override
          protected String getPathTemplate() {
              return "exports/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "exports";
          }

          
              @Override
              protected Class<Export> getResponseClass() {
                  return Export.class;
              }
          

          

          

          

          
        }
    
        
        
        /**
         * Request class for {@link ExportService#list }.
         *
          * Returns a list of exports which are available for download.
         */
        public static final class ExportListRequest
        
            <S>
        
        extends
        
            ListRequest<S, Export>
         {
          

          
              
                  
                  
              
                  
                  
              
                  
                  
              

              
                  

                  
                      /**
                       * Cursor pointing to the start of the desired set.
                       */
                  
                  public 
    ExportListRequest<S>

                      withAfter(
    
        String
    
 after) {
                      
                          setAfter(after);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Cursor pointing to the end of the desired set.
                       */
                  
                  public 
    ExportListRequest<S>

                      withBefore(
    
        String
    
 before) {
                      
                          setBefore(before);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of records to return.
                       */
                  
                  public 
    ExportListRequest<S>

                      withLimit(
    
        Integer
    
 limit) {
                      
                          setLimit(limit);
                      

                      return this;
                  }

                  
              

              
          

          private ExportListRequest(HttpClient httpClient
              
                  , ListRequestExecutor<S, Export> executor
              
              
          ) {

              
                  super(httpClient, executor);
              

              
          }

              public 
    ExportListRequest<S>
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          
              
                  @Override
                  protected Map<String, Object> getQueryParams() {
                      ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                      params.putAll(super.getQueryParams());

                      
                          
                          
                      
                          
                          
                      
                          
                          
                      

                      return params.build();
                  }
              
          

          @Override
          protected String getPathTemplate() {
              return "exports";
          }

          @Override
          protected String getEnvelope() {
              return "exports";
          }

          
              @Override
              protected TypeToken<List<Export>> getTypeToken() {
                  return new TypeToken<List<Export>>() {};
              }
          

          

          

          

          
              
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
}
