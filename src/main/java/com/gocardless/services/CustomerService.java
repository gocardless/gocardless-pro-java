












package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Customer;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with customer resources.
 *
  * Customer objects hold the contact details for a customer. A customer can have several [customer
* bank accounts](#core-endpoints-customer-bank-accounts), which in turn can have several Direct
* Debit [mandates](#core-endpoints-mandates).
 */
public class CustomerService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#customers() }.
     */
    public CustomerService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
        /**
          * Creates a new customer object.
         */
        public CustomerCreateRequest
        
        create() {
            return new CustomerCreateRequest
            
            (httpClient
            

            
            );
        }

        
    
        
        
        /**
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your customers.
         */
        public CustomerListRequest
        
            <ListResponse<Customer>>
        
        list() {
            return new CustomerListRequest
            
                <>
            
            (httpClient
            
                , ListRequest.<Customer>pagingExecutor()
            

            
            );
        }

        
            public CustomerListRequest<Iterable<Customer>> all() {
                return new CustomerListRequest<>(httpClient, ListRequest.<Customer>iteratingExecutor()

                
                );
            }
        
    
        
        
        /**
          * Retrieves the details of an existing customer.
         */
        public CustomerGetRequest
        
        get(String identity) {
            return new CustomerGetRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        
        /**
          * Updates a customer object. Supports all of the fields supported when creating a customer.
         */
        public CustomerUpdateRequest
        
        update(String identity) {
            return new CustomerUpdateRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        
        /**
          * Removed customers will not appear in search results or lists of customers (in our API
* or exports), and it will not be possible to load an individually removed customer by
* ID.
* 
* <p class="restricted-notice"><strong>The action of removing a customer cannot be reversed, so
* please use with care.</strong></p>
         */
        public CustomerRemoveRequest
        
        remove(String identity) {
            return new CustomerRemoveRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    

    
        
        
        /**
         * Request class for {@link CustomerService#create }.
         *
          * Creates a new customer object.
         */
        public static final class CustomerCreateRequest
        
        extends
        
            IdempotentPostRequest<Customer>
         {
          

          
              
                  
                  
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
    
 companyName;
                  
              
                  
                  
                      private 
    
        String
    
 countryCode;
                  
              
                  
                  
                      private 
    
        String
    
 danishIdentityNumber;
                  
              
                  
                  
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
    
 language;
                  
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              
                  
                  
                      private 
    
        String
    
 phoneNumber;
                  
              
                  
                  
                      private 
    
        String
    
 postalCode;
                  
              
                  
                  
                      private 
    
        String
    
 region;
                  
              
                  
                  
                      private 
    
        String
    
 swedishIdentityNumber;
                  
              

              
                  

                  
                      /**
                       * The first line of the customer's address.
                       */
                  
                  public 
    CustomerCreateRequest

                      withAddressLine1(
    
        String
    
 addressLine1) {
                      
                          this.addressLine1 = addressLine1;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The second line of the customer's address.
                       */
                  
                  public 
    CustomerCreateRequest

                      withAddressLine2(
    
        String
    
 addressLine2) {
                      
                          this.addressLine2 = addressLine2;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The third line of the customer's address.
                       */
                  
                  public 
    CustomerCreateRequest

                      withAddressLine3(
    
        String
    
 addressLine3) {
                      
                          this.addressLine3 = addressLine3;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The city of the customer's address.
                       */
                  
                  public 
    CustomerCreateRequest

                      withCity(
    
        String
    
 city) {
                      
                          this.city = city;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's company name. Required unless a `given_name` and `family_name` are provided. For
* Canadian customers, the use of a `company_name` value will mean that any mandate created from this
* customer will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
* be a "Personal PAD").
                       */
                  
                  public 
    CustomerCreateRequest

                      withCompanyName(
    
        String
    
 companyName) {
                      
                          this.companyName = companyName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * [ISO 3166-1 alpha-2
* code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
                       */
                  
                  public 
    CustomerCreateRequest

                      withCountryCode(
    
        String
    
 countryCode) {
                      
                          this.countryCode = countryCode;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
* if the customer's bank account is denominated in Danish krone (DKK).
                       */
                  
                  public 
    CustomerCreateRequest

                      withDanishIdentityNumber(
    
        String
    
 danishIdentityNumber) {
                      
                          this.danishIdentityNumber = danishIdentityNumber;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
* to this customer.
                       */
                  
                  public 
    CustomerCreateRequest

                      withEmail(
    
        String
    
 email) {
                      
                          this.email = email;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's surname. Required unless a `company_name` is provided.
                       */
                  
                  public 
    CustomerCreateRequest

                      withFamilyName(
    
        String
    
 familyName) {
                      
                          this.familyName = familyName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's first name. Required unless a `company_name` is provided.
                       */
                  
                  public 
    CustomerCreateRequest

                      withGivenName(
    
        String
    
 givenName) {
                      
                          this.givenName = givenName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
* notification emails sent by GoCardless if your organisation does not send its own (see [compliance
* requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
* "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
* chosen based on the `country_code` (if supplied) or default to "en".
                       */
                  
                  public 
    CustomerCreateRequest

                      withLanguage(
    
        String
    
 language) {
                      
                          this.language = language;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    CustomerCreateRequest

                      withMetadata(
    
        Map<String, String>
    
 metadata) {
                      
                          this.metadata = metadata;
                      

                      return this;
                  }

                  
                      
                          
                              /**
                               * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                               */
                          
                          public 
    CustomerCreateRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              
                  

                  
                      /**
                       * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including country code.
                       */
                  
                  public 
    CustomerCreateRequest

                      withPhoneNumber(
    
        String
    
 phoneNumber) {
                      
                          this.phoneNumber = phoneNumber;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The customer's postal code.
                       */
                  
                  public 
    CustomerCreateRequest

                      withPostalCode(
    
        String
    
 postalCode) {
                      
                          this.postalCode = postalCode;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The customer's address region, county or department. For US customers a 2 letter
* [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g. `CA` for
* California).
                       */
                  
                  public 
    CustomerCreateRequest

                      withRegion(
    
        String
    
 region) {
                      
                          this.region = region;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
* organisationsnummer) of the customer. Must be supplied if the customer's bank account is
* denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
                       */
                  
                  public 
    CustomerCreateRequest

                      withSwedishIdentityNumber(
    
        String
    
 swedishIdentityNumber) {
                      
                          this.swedishIdentityNumber = swedishIdentityNumber;
                      

                      return this;
                  }

                  
              

              
                  
                      
                  
                      
                  
                      
                          public 
    CustomerCreateRequest
 withIdempotencyKey(String idempotencyKey) {
                              super.setIdempotencyKey(idempotencyKey);
                              return this;
                          }

                          @Override
                          protected GetRequest<Customer> handleConflict(HttpClient httpClient, String id) {
                              CustomerGetRequest request = new CustomerGetRequest(httpClient, id);

                              for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                                  request = request.withHeader(header.getKey(), header.getValue());
                              }

                              return request;
                          }
                      
                  
                      
                  
                      
                  
              
          

          private CustomerCreateRequest(HttpClient httpClient
              
              
          ) {

              
                  super(httpClient);
              

              
          }

              public 
    CustomerCreateRequest
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          

          @Override
          protected String getPathTemplate() {
              return "customers";
          }

          @Override
          protected String getEnvelope() {
              return "customers";
          }

          
              @Override
              protected Class<Customer> getResponseClass() {
                  return Customer.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        
        /**
         * Request class for {@link CustomerService#list }.
         *
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your customers.
         */
        public static final class CustomerListRequest
        
            <S>
        
        extends
        
            ListRequest<S, Customer>
         {
          

          
              
                  
                  
              
                  
                  
              
                  
                  
                      private 
    
        CreatedAt
    
 createdAt;
                  
              
                  
                  
                      private 
    
        Currency
    
 currency;
                  
              
                  
                  
              
                  
                  
                      private 
    
        SortDirection
    
 sortDirection;
                  
              
                  
                  
                      private 
    
        SortField
    
 sortField;
                  
              

              
                  

                  
                      /**
                       * Cursor pointing to the start of the desired set.
                       */
                  
                  public 
    CustomerListRequest<S>

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
    CustomerListRequest<S>

                      withBefore(
    
        String
    
 before) {
                      
                          setBefore(before);
                      

                      return this;
                  }

                  
              
                  

                  
                  public 
    CustomerListRequest<S>

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
    CustomerListRequest<S>

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
    CustomerListRequest<S>

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
    CustomerListRequest<S>

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
    CustomerListRequest<S>

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
                       * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
* "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
                       */
                  
                  public 
    CustomerListRequest<S>

                      withCurrency(
    
        Currency
    
 currency) {
                      
                          this.currency = currency;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of records to return.
                       */
                  
                  public 
    CustomerListRequest<S>

                      withLimit(
    
        Integer
    
 limit) {
                      
                          setLimit(limit);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The direction to sort in.
* One of:
* <ul>
* <li>`asc`</li>
* <li>`desc`</li>
* </ul>
                       */
                  
                  public 
    CustomerListRequest<S>

                      withSortDirection(
    
        SortDirection
    
 sortDirection) {
                      
                          this.sortDirection = sortDirection;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Field by which to sort records.
* One of:
* <ul>
* <li>`name`</li>
* <li>`company_name`</li>
* <li>`created_at`</li>
* </ul>
                       */
                  
                  public 
    CustomerListRequest<S>

                      withSortField(
    
        SortField
    
 sortField) {
                      
                          this.sortField = sortField;
                      

                      return this;
                  }

                  
              

              
          

          private CustomerListRequest(HttpClient httpClient
              
                  , ListRequestExecutor<S, Customer> executor
              
              
          ) {

              
                  super(httpClient, executor);
              

              
          }

              public 
    CustomerListRequest<S>
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
                          
                      
                          
                          
                              if (currency != null) {
                                  
                                      params.put("currency", currency);
                                  
                              }
                          
                      
                          
                          
                      
                          
                          
                              if (sortDirection != null) {
                                  
                                      params.put("sort_direction", sortDirection);
                                  
                              }
                          
                      
                          
                          
                              if (sortField != null) {
                                  
                                      params.put("sort_field", sortField);
                                  
                              }
                          
                      

                      return params.build();
                  }
              
          

          @Override
          protected String getPathTemplate() {
              return "customers";
          }

          @Override
          protected String getEnvelope() {
              return "customers";
          }

          
              @Override
              protected TypeToken<List<Customer>> getTypeToken() {
                  return new TypeToken<List<Customer>>() {};
              }
          

          

          

          

          
              
    
        

        
    
        

        
    
        

        
    
        
            
                
    public enum Currency {
        
            @SerializedName("AUD") AUD
        
            ,@SerializedName("CAD") CAD
        
            ,@SerializedName("DKK") DKK
        
            ,@SerializedName("EUR") EUR
        
            ,@SerializedName("GBP") GBP
        
            ,@SerializedName("NZD") NZD
        
            ,@SerializedName("SEK") SEK
        
            ,@SerializedName("USD") USD
        
        , @SerializedName("unknown") UNKNOWN;

        @Override
        public String toString() {
          
            return name();
          
        }
    }

            
        

        
    
        

        
    
        
            
                
    public enum SortDirection {
        
            @SerializedName("asc") ASC
        
            ,@SerializedName("desc") DESC
        
        , @SerializedName("unknown") UNKNOWN;

        @Override
        public String toString() {
          
            return name().toLowerCase();
          
        }
    }

            
        

        
    
        
            
                
    public enum SortField {
        
            @SerializedName("name") NAME
        
            ,@SerializedName("company_name") COMPANY_NAME
        
            ,@SerializedName("created_at") CREATED_AT
        
        , @SerializedName("unknown") UNKNOWN;

        @Override
        public String toString() {
          
            return name().toLowerCase();
          
        }
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
         * Request class for {@link CustomerService#get }.
         *
          * Retrieves the details of an existing customer.
         */
        public static final class CustomerGetRequest
        
        extends
        
            GetRequest<Customer>
         {
          
              @PathParam
              private final String identity;
          

          

          private CustomerGetRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    CustomerGetRequest
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
              return "customers/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "customers";
          }

          
              @Override
              protected Class<Customer> getResponseClass() {
                  return Customer.class;
              }
          

          

          

          

          
        }
    
        
        
        /**
         * Request class for {@link CustomerService#update }.
         *
          * Updates a customer object. Supports all of the fields supported when creating a customer.
         */
        public static final class CustomerUpdateRequest
        
        extends
        
            PutRequest<Customer>
         {
          
              @PathParam
              private final String identity;
          

          
              
                  
                  
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
    
 companyName;
                  
              
                  
                  
                      private 
    
        String
    
 countryCode;
                  
              
                  
                  
                      private 
    
        String
    
 danishIdentityNumber;
                  
              
                  
                  
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
    
 language;
                  
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              
                  
                  
                      private 
    
        String
    
 phoneNumber;
                  
              
                  
                  
                      private 
    
        String
    
 postalCode;
                  
              
                  
                  
                      private 
    
        String
    
 region;
                  
              
                  
                  
                      private 
    
        String
    
 swedishIdentityNumber;
                  
              

              
                  

                  
                      /**
                       * The first line of the customer's address.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withAddressLine1(
    
        String
    
 addressLine1) {
                      
                          this.addressLine1 = addressLine1;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The second line of the customer's address.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withAddressLine2(
    
        String
    
 addressLine2) {
                      
                          this.addressLine2 = addressLine2;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The third line of the customer's address.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withAddressLine3(
    
        String
    
 addressLine3) {
                      
                          this.addressLine3 = addressLine3;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The city of the customer's address.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withCity(
    
        String
    
 city) {
                      
                          this.city = city;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's company name. Required unless a `given_name` and `family_name` are provided. For
* Canadian customers, the use of a `company_name` value will mean that any mandate created from this
* customer will be considered to be a "Business PAD" (otherwise, any mandate will be considered to
* be a "Personal PAD").
                       */
                  
                  public 
    CustomerUpdateRequest

                      withCompanyName(
    
        String
    
 companyName) {
                      
                          this.companyName = companyName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * [ISO 3166-1 alpha-2
* code.](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
                       */
                  
                  public 
    CustomerUpdateRequest

                      withCountryCode(
    
        String
    
 countryCode) {
                      
                          this.countryCode = countryCode;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * For Danish customers only. The civic/company number (CPR or CVR) of the customer. Must be supplied
* if the customer's bank account is denominated in Danish krone (DKK).
                       */
                  
                  public 
    CustomerUpdateRequest

                      withDanishIdentityNumber(
    
        String
    
 danishIdentityNumber) {
                      
                          this.danishIdentityNumber = danishIdentityNumber;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's email address. Required in most cases, as this allows GoCardless to send notifications
* to this customer.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withEmail(
    
        String
    
 email) {
                      
                          this.email = email;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's surname. Required unless a `company_name` is provided.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withFamilyName(
    
        String
    
 familyName) {
                      
                          this.familyName = familyName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Customer's first name. Required unless a `company_name` is provided.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withGivenName(
    
        String
    
 givenName) {
                      
                          this.givenName = givenName;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Used as the language for
* notification emails sent by GoCardless if your organisation does not send its own (see [compliance
* requirements](#appendix-compliance-requirements)). Currently only "en", "fr", "de", "pt", "es",
* "it", "nl", "da", "nb", "sl", "sv" are supported. If this is not provided, the language will be
* chosen based on the `country_code` (if supplied) or default to "en".
                       */
                  
                  public 
    CustomerUpdateRequest

                      withLanguage(
    
        String
    
 language) {
                      
                          this.language = language;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withMetadata(
    
        Map<String, String>
    
 metadata) {
                      
                          this.metadata = metadata;
                      

                      return this;
                  }

                  
                      
                          
                              /**
                               * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                               */
                          
                          public 
    CustomerUpdateRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              
                  

                  
                      /**
                       * [ITU E.123](https://en.wikipedia.org/wiki/E.123) formatted phone number, including country code.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withPhoneNumber(
    
        String
    
 phoneNumber) {
                      
                          this.phoneNumber = phoneNumber;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The customer's postal code.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withPostalCode(
    
        String
    
 postalCode) {
                      
                          this.postalCode = postalCode;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The customer's address region, county or department. For US customers a 2 letter
* [ISO3166-2:US](https://en.wikipedia.org/wiki/ISO_3166-2:US) state code is required (e.g. `CA` for
* California).
                       */
                  
                  public 
    CustomerUpdateRequest

                      withRegion(
    
        String
    
 region) {
                      
                          this.region = region;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * For Swedish customers only. The civic/company number (personnummer, samordningsnummer, or
* organisationsnummer) of the customer. Must be supplied if the customer's bank account is
* denominated in Swedish krona (SEK). This field cannot be changed once it has been set.
                       */
                  
                  public 
    CustomerUpdateRequest

                      withSwedishIdentityNumber(
    
        String
    
 swedishIdentityNumber) {
                      
                          this.swedishIdentityNumber = swedishIdentityNumber;
                      

                      return this;
                  }

                  
              

              
          

          private CustomerUpdateRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    CustomerUpdateRequest
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
              return "customers/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "customers";
          }

          
              @Override
              protected Class<Customer> getResponseClass() {
                  return Customer.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        
        /**
         * Request class for {@link CustomerService#remove }.
         *
          * Removed customers will not appear in search results or lists of customers (in our API
* or exports), and it will not be possible to load an individually removed customer by
* ID.
* 
* <p class="restricted-notice"><strong>The action of removing a customer cannot be reversed, so
* please use with care.</strong></p>
         */
        public static final class CustomerRemoveRequest
        
        extends
        
            DeleteRequest<Customer>
         {
          
              @PathParam
              private final String identity;
          

          
              

              

              
          

          private CustomerRemoveRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    CustomerRemoveRequest
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
              return "customers/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "customers";
          }

          
              @Override
              protected Class<Customer> getResponseClass() {
                  return Customer.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          
              @Override
              protected String getRequestEnvelope() {
                  return "data";
              }
          

          
              
    


              
          
        }
    
}
