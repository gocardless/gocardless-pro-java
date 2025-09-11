












package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Webhook;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with webhook resources.
 *
  * Basic description of a webhook
 */
public class WebhookService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#webhooks() }.
     */
    public WebhookService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
        /**
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your webhooks.
         */
        public WebhookListRequest
        
            <ListResponse<Webhook>>
        
        list() {
            return new WebhookListRequest
            
                <>
            
            (httpClient
            
                , ListRequest.<Webhook>pagingExecutor()
            

            
            );
        }

        
            public WebhookListRequest<Iterable<Webhook>> all() {
                return new WebhookListRequest<>(httpClient, ListRequest.<Webhook>iteratingExecutor()

                
                );
            }
        
    
        
        
        /**
          * Retrieves the details of an existing webhook.
         */
        public WebhookGetRequest
        
        get(String identity) {
            return new WebhookGetRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        
        /**
          * Requests for a previous webhook to be sent again
         */
        public WebhookRetryRequest
        
        retry(String identity) {
            return new WebhookRetryRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    

    
        
        
        /**
         * Request class for {@link WebhookService#list }.
         *
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your webhooks.
         */
        public static final class WebhookListRequest
        
            <S>
        
        extends
        
            ListRequest<S, Webhook>
         {
          

          
              
                  
                  
              
                  
                  
              
                  
                  
                      private 
    
        CreatedAt
    
 createdAt;
                  
              
                  
                  
                      private 
    
        Boolean
    
 isTest;
                  
              
                  
                  
              
                  
                  
                      private 
    
        Boolean
    
 successful;
                  
              

              
                  

                  
                      /**
                       * Cursor pointing to the start of the desired set.
                       */
                  
                  public 
    WebhookListRequest<S>

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
    WebhookListRequest<S>

                      withBefore(
    
        String
    
 before) {
                      
                          setBefore(before);
                      

                      return this;
                  }

                  
              
                  

                  
                  public 
    WebhookListRequest<S>

                      withCreatedAt(
    
        CreatedAt
    
 createdAt) {
                      
                          this.createdAt = createdAt;
                      

                      return this;
                  }

                  
                      
                          
                              
                              
                                  /**
                                    * Limit to records created after the specified date-time.
                                   */
                              
                              public 
    WebhookListRequest<S>

                                  withCreatedAtGt(
                                      
    
        String
    
 gt
                                  ) {
                                  if (createdAt == null) {
                                      createdAt = new 
    
        CreatedAt
    
();
                                  }

                                  createdAt.withGt(gt);
                                  return this;
                              }
                          
                              
                              
                                  /**
                                    * Limit to records created on or after the specified date-time.
                                   */
                              
                              public 
    WebhookListRequest<S>

                                  withCreatedAtGte(
                                      
    
        String
    
 gte
                                  ) {
                                  if (createdAt == null) {
                                      createdAt = new 
    
        CreatedAt
    
();
                                  }

                                  createdAt.withGte(gte);
                                  return this;
                              }
                          
                              
                              
                                  /**
                                    * Limit to records created before the specified date-time.
                                   */
                              
                              public 
    WebhookListRequest<S>

                                  withCreatedAtLt(
                                      
    
        String
    
 lt
                                  ) {
                                  if (createdAt == null) {
                                      createdAt = new 
    
        CreatedAt
    
();
                                  }

                                  createdAt.withLt(lt);
                                  return this;
                              }
                          
                              
                              
                                  /**
                                    * Limit to records created on or before the specified date-time.
                                   */
                              
                              public 
    WebhookListRequest<S>

                                  withCreatedAtLte(
                                      
    
        String
    
 lte
                                  ) {
                                  if (createdAt == null) {
                                      createdAt = new 
    
        CreatedAt
    
();
                                  }

                                  createdAt.withLte(lte);
                                  return this;
                              }
                          
                      
                  
              
                  

                  
                      /**
                       * Show only test/non test webhooks
                       */
                  
                  public 
    WebhookListRequest<S>

                      withIsTest(
    
        Boolean
    
 isTest) {
                      
                          this.isTest = isTest;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of records to return.
                       */
                  
                  public 
    WebhookListRequest<S>

                      withLimit(
    
        Integer
    
 limit) {
                      
                          setLimit(limit);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Show only successful/failed webhooks
                       */
                  
                  public 
    WebhookListRequest<S>

                      withSuccessful(
    
        Boolean
    
 successful) {
                      
                          this.successful = successful;
                      

                      return this;
                  }

                  
              

              
          

          private WebhookListRequest(HttpClient httpClient
              
                  , ListRequestExecutor<S, Webhook> executor
              
              
          ) {

              
                  super(httpClient, executor);
              

              
          }

              public 
    WebhookListRequest<S>
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          
              
                  @Override
                  protected Map<String, Object> getQueryParams() {
                      ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                      params.putAll(super.getQueryParams());

                      
                          
                          
                      
                          
                          
                      
                          
                          
                              if (createdAt != null) {
                                  
                                      params.putAll(createdAt.getQueryParams());
                                  
                              }
                          
                      
                          
                          
                              if (isTest != null) {
                                  
                                      params.put("is_test", isTest);
                                  
                              }
                          
                      
                          
                          
                      
                          
                          
                              if (successful != null) {
                                  
                                      params.put("successful", successful);
                                  
                              }
                          
                      

                      return params.build();
                  }
              
          

          @Override
          protected String getPathTemplate() {
              return "webhooks";
          }

          @Override
          protected String getEnvelope() {
              return "webhooks";
          }

          
              @Override
              protected TypeToken<List<Webhook>> getTypeToken() {
                  return new TypeToken<List<Webhook>>() {};
              }
          

          

          

          

          
              
    
        

        
    
        

        
    
        

        
    
        
            
        

        
    
        

        
    
        
            
        

        
    


              
                  

                  
              
                  

                  
              
                  
                      
    

    
        

        
    
        

        
    
        

        
    
        

        
    

    public static class CreatedAt {
        
            
            private 
    
        String
    
 gt;
        
            
            private 
    
        String
    
 gte;
        
            
            private 
    
        String
    
 lt;
        
            
            private 
    
        String
    
 lte;
        

        
            
            
                /**
                 * Limit to records created after the specified date-time.
                 */
            
            public CreatedAt withGt(
    
        String
    
 gt) {
                this.gt = gt;
                return this;
            }
        
            
            
                /**
                 * Limit to records created on or after the specified date-time.
                 */
            
            public CreatedAt withGte(
    
        String
    
 gte) {
                this.gte = gte;
                return this;
            }
        
            
            
                /**
                 * Limit to records created before the specified date-time.
                 */
            
            public CreatedAt withLt(
    
        String
    
 lt) {
                this.lt = lt;
                return this;
            }
        
            
            
                /**
                 * Limit to records created on or before the specified date-time.
                 */
            
            public CreatedAt withLte(
    
        String
    
 lte) {
                this.lte = lte;
                return this;
            }
        

        
            
            public Map<String, Object> getQueryParams() {
                ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();

                
                    
                    if (gt != null) {
                        params.put("created_at[gt]", gt);
                    }
                
                    
                    if (gte != null) {
                        params.put("created_at[gte]", gte);
                    }
                
                    
                    if (lt != null) {
                        params.put("created_at[lt]", lt);
                    }
                
                    
                    if (lte != null) {
                        params.put("created_at[lte]", lte);
                    }
                

                return params.build();
            }
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    

    }

                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        
        /**
         * Request class for {@link WebhookService#get }.
         *
          * Retrieves the details of an existing webhook.
         */
        public static final class WebhookGetRequest
        
        extends
        
            GetRequest<Webhook>
         {
          
              @PathParam
              private final String identity;
          

          

          private WebhookGetRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    WebhookGetRequest
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
              return "webhooks/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "webhooks";
          }

          
              @Override
              protected Class<Webhook> getResponseClass() {
                  return Webhook.class;
              }
          

          

          

          

          
        }
    
        
        
        /**
         * Request class for {@link WebhookService#retry }.
         *
          * Requests for a previous webhook to be sent again
         */
        public static final class WebhookRetryRequest
        
        extends
        
            PostRequest<Webhook>
         {
          
              @PathParam
              private final String identity;
          

          

          private WebhookRetryRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    WebhookRetryRequest
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
              return "webhooks/:identity/actions/retry";
          }

          @Override
          protected String getEnvelope() {
              return "webhooks";
          }

          
              @Override
              protected Class<Webhook> getResponseClass() {
                  return Webhook.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return false;
              }
          

          

          
        }
    
}
