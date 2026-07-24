












package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.SchemeIdentifier;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with schemeentifier resources.
 *
  * This represents a scheme identifier (e.g. a SUN in Bacs or a CID in SEPA). Scheme identifiers are
* used to specify the beneficiary name that appears on customers' bank statements.
 */
public class SchemeIdentifierService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#schemeIdentifiers() }.
     */
    public SchemeIdentifierService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
        /**
          * Creates a new scheme identifier. The scheme identifier status will be <code>pending</code> while
* GoCardless is
* processing the request. Once the scheme identifier is ready to be used the status will be updated
* to <code>active</code>.
* At this point, GoCardless will emit a scheme identifier activated event via webhook to notify you
* of this change.
* In Bacs, it will take up to five working days for a scheme identifier to become active. On other
* schemes, including SEPA,
* this happens instantly.
* 
* <h4>Scheme identifier name validations</h4>
* The <code>name</code> field of a scheme identifier can contain alphanumeric characters, spaces and
* special characters.
* 
* Its maximum length and the special characters it supports depend on the scheme:
* 
* | <strong>scheme</strong>        | <strong>maximum length</strong> | <strong>special characters
* allowed</strong>                      |
* | :---------------- | :----------------- | :-------------------------------------------------- |
* | <code>bacs</code>            | 18 characters      | <code>/</code> <code>.</code>
* <code>&amp;</code> <code><ul>
* <li></li>
* </ul></code>                                     |
* | <code>sepa</code>            | 70 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code><ul>
* <li></li>
* </ul></code> <code>&amp;</code> <code>&lt;</code> <code><blockquote>
* </blockquote></code> <code>'</code> <code>"</code> |
* | <code>ach</code>             | 16 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code>'</code> <code><ul>
* <li></li>
* </ul></code> <code><ul>
* <li></li>
* </ul></code>             |
* | <code>faster_payments</code> | 18 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code>'</code> <code><ul>
* <li></li>
* </ul></code> <code><ul>
* <li></li>
* </ul></code>             |
* 
* The validation error that gets returned for an invalid name will contain a suggested name
* in the metadata that is guaranteed to pass name validations.
* 
* You should ensure that the name you set matches the legal name or the trading name of
* the creditor, otherwise, there is an increased risk of chargeback.
         */
        public SchemeIdentifierCreateRequest
        
        create() {
            return new SchemeIdentifierCreateRequest
            
            (httpClient
            

            
            );
        }

        
    
        
        
        /**
          * Returns a <a
* href="https://developer.gocardless.com/api-reference/#api-usage-cursor-pagination">cursor-paginated</a>
* list of your scheme identifiers.
         */
        public SchemeIdentifierListRequest
        
            <ListResponse<SchemeIdentifier>>
        
        list() {
            return new SchemeIdentifierListRequest
            
                <>
            
            (httpClient
            
                , ListRequest.<SchemeIdentifier>pagingExecutor()
            

            
            );
        }

        
            public SchemeIdentifierListRequest<Iterable<SchemeIdentifier>> all() {
                return new SchemeIdentifierListRequest<>(httpClient, ListRequest.<SchemeIdentifier>iteratingExecutor()

                
                );
            }
        
    
        
        
        /**
          * Retrieves the details of an existing scheme identifier.
         */
        public SchemeIdentifierGetRequest
        
        get(String identity) {
            return new SchemeIdentifierGetRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    

    
        
        
        /**
         * Request class for {@link SchemeIdentifierService#create }.
         *
          * Creates a new scheme identifier. The scheme identifier status will be <code>pending</code> while
* GoCardless is
* processing the request. Once the scheme identifier is ready to be used the status will be updated
* to <code>active</code>.
* At this point, GoCardless will emit a scheme identifier activated event via webhook to notify you
* of this change.
* In Bacs, it will take up to five working days for a scheme identifier to become active. On other
* schemes, including SEPA,
* this happens instantly.
* 
* <h4>Scheme identifier name validations</h4>
* The <code>name</code> field of a scheme identifier can contain alphanumeric characters, spaces and
* special characters.
* 
* Its maximum length and the special characters it supports depend on the scheme:
* 
* | <strong>scheme</strong>        | <strong>maximum length</strong> | <strong>special characters
* allowed</strong>                      |
* | :---------------- | :----------------- | :-------------------------------------------------- |
* | <code>bacs</code>            | 18 characters      | <code>/</code> <code>.</code>
* <code>&amp;</code> <code><ul>
* <li></li>
* </ul></code>                                     |
* | <code>sepa</code>            | 70 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code><ul>
* <li></li>
* </ul></code> <code>&amp;</code> <code>&lt;</code> <code><blockquote>
* </blockquote></code> <code>'</code> <code>"</code> |
* | <code>ach</code>             | 16 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code>'</code> <code><ul>
* <li></li>
* </ul></code> <code><ul>
* <li></li>
* </ul></code>             |
* | <code>faster_payments</code> | 18 characters      | <code>/</code> <code>?</code> <code>:</code>
* <code>(</code> <code>)</code> <code>.</code> <code>,</code> <code>'</code> <code><ul>
* <li></li>
* </ul></code> <code><ul>
* <li></li>
* </ul></code>             |
* 
* The validation error that gets returned for an invalid name will contain a suggested name
* in the metadata that is guaranteed to pass name validations.
* 
* You should ensure that the name you set matches the legal name or the trading name of
* the creditor, otherwise, there is an increased risk of chargeback.
         */
        public static final class SchemeIdentifierCreateRequest
        
        extends
        
            IdempotentPostRequest<SchemeIdentifier>
         {
          

          
              
                  
                  
                      private 
    
        Links
    
 links;
                  
              
                  
                  
                      private 
    
        String
    
 name;
                  
              
                  
                  
                      private 
    
        Scheme
    
 scheme;
                  
              

              
                  

                  
                  public 
    SchemeIdentifierCreateRequest

                      withLinks(
    
        Links
    
 links) {
                      
                          this.links = links;
                      

                      return this;
                  }

                  
                      
                          
                              
                              
                                  /**
                                    * <em>required</em> ID of the associated <a
* href="https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>.
                                   */
                              
                              public 
    SchemeIdentifierCreateRequest

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
                          
                      
                  
              
                  

                  
                      /**
                       * The name which appears on customers' bank statements. This should usually be the merchant's
* trading name.
                       */
                  
                  public 
    SchemeIdentifierCreateRequest

                      withName(
    
        String
    
 name) {
                      
                          this.name = name;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The scheme which this scheme identifier applies to.
                       */
                  
                  public 
    SchemeIdentifierCreateRequest

                      withScheme(
    
        Scheme
    
 scheme) {
                      
                          this.scheme = scheme;
                      

                      return this;
                  }

                  
              

              
                  
                      
                  
                      
                  
                      
                          public 
    SchemeIdentifierCreateRequest
 withIdempotencyKey(String idempotencyKey) {
                              super.setIdempotencyKey(idempotencyKey);
                              return this;
                          }

                          @Override
                          protected GetRequest<SchemeIdentifier> handleConflict(HttpClient httpClient, String id) {
                              SchemeIdentifierGetRequest request = new SchemeIdentifierGetRequest(httpClient, id);

                              for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                                  request = request.withHeader(header.getKey(), header.getValue());
                              }

                              return request;
                          }
                      
                  
              
          

          private SchemeIdentifierCreateRequest(HttpClient httpClient
              
              
          ) {

              
                  super(httpClient);
              

              
          }

              public 
    SchemeIdentifierCreateRequest
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          

          @Override
          protected String getPathTemplate() {
              return "scheme_identifiers";
          }

          @Override
          protected String getEnvelope() {
              return "scheme_identifiers";
          }

          
              @Override
              protected Class<SchemeIdentifier> getResponseClass() {
                  return SchemeIdentifier.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        
            
                
    public enum Scheme {
        
            @SerializedName("ach") ACH
        
            ,@SerializedName("autogiro") AUTOGIRO
        
            ,@SerializedName("bacs") BACS
        
            ,@SerializedName("becs") BECS
        
            ,@SerializedName("becs_nz") BECS_NZ
        
            ,@SerializedName("betalingsservice") BETALINGSSERVICE
        
            ,@SerializedName("faster_payments") FASTER_PAYMENTS
        
            ,@SerializedName("pad") PAD
        
            ,@SerializedName("pay_to") PAY_TO
        
            ,@SerializedName("sepa") SEPA
        
            ,@SerializedName("sepa_credit_transfer") SEPA_CREDIT_TRANSFER
        
            ,@SerializedName("sepa_instant_credit_transfer") SEPA_INSTANT_CREDIT_TRANSFER
        
        , @SerializedName("unknown") UNKNOWN;

        @Override
        public String toString() {
          
            return name().toLowerCase();
          
        }
    }

            
        

        
    


              
                  
                      
    

    
        

        
    

    public static class Links {
        
            
            private 
    
        String
    
 creditor;
        

        
            
            
                /**
                 * <em>required</em> ID of the associated <a
* href="https://developer.gocardless.com/api-reference/#core-endpoints-creditors">creditor</a>.
                 */
            
            public Links withCreditor(
    
        String
    
 creditor) {
                this.creditor = creditor;
                return this;
            }
        

        

        
    
        

        
    

    }

                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        
        /**
         * Request class for {@link SchemeIdentifierService#list }.
         *
          * Returns a <a
* href="https://developer.gocardless.com/api-reference/#api-usage-cursor-pagination">cursor-paginated</a>
* list of your scheme identifiers.
         */
        public static final class SchemeIdentifierListRequest
        
            <S>
        
        extends
        
            ListRequest<S, SchemeIdentifier>
         {
          

          
              
                  
                  
              
                  
                  
              
                  
                  
                      private 
    
        String
    
 creditor;
                  
              
                  
                  
              

              
                  

                  
                      /**
                       * Cursor pointing to the start of the desired set.
                       */
                  
                  public 
    SchemeIdentifierListRequest<S>

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
    SchemeIdentifierListRequest<S>

                      withBefore(
    
        String
    
 before) {
                      
                          setBefore(before);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Unique identifier, beginning with "CR".
                       */
                  
                  public 
    SchemeIdentifierListRequest<S>

                      withCreditor(
    
        String
    
 creditor) {
                      
                          this.creditor = creditor;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of records to return.
                       */
                  
                  public 
    SchemeIdentifierListRequest<S>

                      withLimit(
    
        Integer
    
 limit) {
                      
                          setLimit(limit);
                      

                      return this;
                  }

                  
              

              
          

          private SchemeIdentifierListRequest(HttpClient httpClient
              
                  , ListRequestExecutor<S, SchemeIdentifier> executor
              
              
          ) {

              
                  super(httpClient, executor);
              

              
          }

              public 
    SchemeIdentifierListRequest<S>
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          
              
                  @Override
                  protected Map<String, Object> getQueryParams() {
                      ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
                      params.putAll(super.getQueryParams());

                      
                          
                          
                      
                          
                          
                      
                          
                          
                              if (creditor != null) {
                                  
                                      params.put("creditor", creditor);
                                  
                              }
                          
                      
                          
                          
                      

                      return params.build();
                  }
              
          

          @Override
          protected String getPathTemplate() {
              return "scheme_identifiers";
          }

          @Override
          protected String getEnvelope() {
              return "scheme_identifiers";
          }

          
              @Override
              protected TypeToken<List<SchemeIdentifier>> getTypeToken() {
                  return new TypeToken<List<SchemeIdentifier>>() {};
              }
          

          

          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        
        /**
         * Request class for {@link SchemeIdentifierService#get }.
         *
          * Retrieves the details of an existing scheme identifier.
         */
        public static final class SchemeIdentifierGetRequest
        
        extends
        
            GetRequest<SchemeIdentifier>
         {
          
              @PathParam
              private final String identity;
          

          

          private SchemeIdentifierGetRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SchemeIdentifierGetRequest
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
              return "scheme_identifiers/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "scheme_identifiers";
          }

          
              @Override
              protected Class<SchemeIdentifier> getResponseClass() {
                  return SchemeIdentifier.class;
              }
          

          

          

          

          
        }
    
}
