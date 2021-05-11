












package com.gocardless.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Subscription;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with subscription resources.
 *
  * Subscriptions create [payments](#core-endpoints-payments) according to a schedule.
* 
* ### Recurrence Rules
* 
* The following rules apply when specifying recurrence:
* 
* - The first payment must be charged within 1 year.
* - If `day_of_month` and `start_date` are not provided `start_date` will be the
* [mandate](#core-endpoints-mandates)'s `next_possible_charge_date` and the subscription will then
* recur based on the `interval` & `interval_unit`
* - If `month` or `day_of_month` are present the following validations apply:
* 
* | __interval_unit__ | __month__                                      | __day_of_month__           
*                |
* | :---------------- | :--------------------------------------------- |
* :----------------------------------------- |
* | yearly            | optional (required if `day_of_month` provided) | optional (invalid if
* `month` not provided) |
* | monthly           | invalid                                        | optional                   
*                |
* | weekly            | invalid                                        | invalid                    
*                |
* 
* Examples:
* 
* | __interval_unit__ | __interval__ | __month__ | __day_of_month__ | valid?                        
*                     |
* | :---------------- | :----------- | :-------- | :--------------- |
* :------------------------------------------------- |
* | yearly            | 1            | january   | -1               | valid                         
*                     |
* | monthly           | 6            |           |                  | valid                         
*                     |
* | monthly           | 6            |           | 12               | valid                         
*                     |
* | weekly            | 2            |           |                  | valid                         
*                     |
* | yearly            | 1            | march     |                  | invalid - missing
* `day_of_month`                   |
* | yearly            | 1            |           | 2                | invalid - missing `month`     
*                     |
* | monthly           | 6            | august    | 12               | invalid - `month` must be
* blank                    |
* | weekly            | 2            | october   | 10               | invalid - `month` and
* `day_of_month` must be blank |
* 
* ### Rolling dates
* 
* When a charge date falls on a non-business day, one of two things will happen:
* 
* - if the recurrence rule specified `-1` as the `day_of_month`, the charge date will be rolled
* __backwards__ to the previous business day (i.e., the last working day of the month).
* - otherwise the charge date will be rolled __forwards__ to the next business day.
* 
 */
public class SubscriptionService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#subscriptions() }.
     */
    public SubscriptionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        /**
          * Creates a new subscription object
         */
        public SubscriptionCreateRequest
        
        create() {
            return new SubscriptionCreateRequest
            
            (httpClient
            

            
            );
        }

        
    
        
        /**
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your subscriptions.
         */
        public SubscriptionListRequest
        
            <ListResponse<Subscription>>
        
        list() {
            return new SubscriptionListRequest
            
                <>
            
            (httpClient
            
                , ListRequest.<Subscription>pagingExecutor()
            

            
            );
        }

        
            public SubscriptionListRequest<Iterable<Subscription>> all() {
                return new SubscriptionListRequest<>(httpClient, ListRequest.<Subscription>iteratingExecutor()

                
                );
            }
        
    
        
        /**
          * Retrieves the details of a single subscription.
         */
        public SubscriptionGetRequest
        
        get(String identity) {
            return new SubscriptionGetRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        /**
          * Updates a subscription object.
* 
* This fails with:
* 
* - `validation_failed` if invalid data is provided when attempting to update a subscription.
* 
* - `subscription_not_active` if the subscription is no longer active.
* 
* - `subscription_already_ended` if the subscription has taken all payments.
* 
* - `mandate_payments_require_approval` if the amount is being changed and the mandate requires
* approval.
* 
* - `number_of_subscription_amendments_exceeded` error if the subscription amount has already been
* changed 10 times.
* 
* - `forbidden` if the amount is being changed, and the subscription was created by an app and you
* are not authenticated as that app, or if the subscription was not created by an app and you are
* authenticated as an app
* 
* - `resource_created_by_another_app` if the app fee is being changed, and the subscription was
* created by an app other than the app you are authenticated as
* 
         */
        public SubscriptionUpdateRequest
        
        update(String identity) {
            return new SubscriptionUpdateRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        /**
          * Pause a subscription object.
* No payments will be created until it is resumed.
* 
* This can only be used when a subscription is collecting a fixed number of payments (created using
* `count`),
* when they continue forever (created without `count` or `end_date`) or
* the subscription is already paused for a number of cycles.
* 
* When `pause_cycles` is omitted the subscription is paused until the [resume
* endpoint](#subscriptions-resume-a-subscription) is called.
* If the subscription is collecting a fixed number of payments, `end_date` will be set to `null`.
* When paused indefinitely, `upcoming_payments` will be empty.
* 
* When `pause_cycles` is provided the subscription will be paused for the number of cycles
* requested.
* If the subscription is collecting a fixed number of payments, `end_date` will be set to a new
* value.
* When paused for a number of cycles, `upcoming_payments` will still contain the upcoming charge
* dates.
* 
* This fails with:
* 
* - `forbidden` if the subscription was created by an app and you are not authenticated as that app,
* or if the subscription was not created by an app and you are authenticated as an app
* 
* - `validation_failed` if invalid data is provided when attempting to pause a subscription.
* 
* - `subscription_paused_cannot_update_cycles` if the subscription is already paused for a number of
* cycles and the request provides a value for `pause_cycle`.
* 
* - `subscription_cannot_be_paused` if the subscription cannot be paused.
* 
* - `subscription_already_ended` if the subscription has taken all payments.
* 
* - `pause_cycles_must_be_greater_than_or_equal_to` if the provided value for `pause_cycles` cannot
* be satisfied.
* 
         */
        public SubscriptionPauseRequest
        
        pause(String identity) {
            return new SubscriptionPauseRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        /**
          * Resume a subscription object.
* Payments will start to be created again based on the subscriptions recurrence rules.
* The `charge_date` on the next payment will be the same as the subscriptions
* `earliest_charge_date_after_resume`
* 
* This fails with:
* 
* - `forbidden` if the subscription was created by an app and you are not authenticated as that app,
* or if the subscription was not created by an app and you are authenticated as an app
* 
* - `validation_failed` if invalid data is provided when attempting to resume a subscription.
* 
* - `subscription_not_paused` if the subscription is not paused.
* 
         */
        public SubscriptionResumeRequest
        
        resume(String identity) {
            return new SubscriptionResumeRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    
        
        /**
          * Immediately cancels a subscription; no more payments will be created under it. Any metadata
* supplied to this endpoint will be stored on the payment cancellation event it causes.
* 
* This will fail with a cancellation_failed error if the subscription is already cancelled or
* finished.
         */
        public SubscriptionCancelRequest
        
        cancel(String identity) {
            return new SubscriptionCancelRequest
            
            (httpClient
            

            
                , identity
            
            );
        }

        
    

    
        
        /**
         * Request class for {@link SubscriptionService#create }.
         *
          * Creates a new subscription object
         */
        public static final class SubscriptionCreateRequest
        
        extends
        
            IdempotentPostRequest<Subscription>
         {
          

          
              
                  
                  
                      private 
    
        Integer
    
 amount;
                  
              
                  
                  
                      private 
    
        Integer
    
 appFee;
                  
              
                  
                  
                      private 
    
        Integer
    
 count;
                  
              
                  
                  
                      private 
    
        String
    
 currency;
                  
              
                  
                  
                      private 
    
        Integer
    
 dayOfMonth;
                  
              
                  
                  
                      private 
    
        String
    
 endDate;
                  
              
                  
                  
                      private 
    
        Integer
    
 interval;
                  
              
                  
                  
                      private 
    
        IntervalUnit
    
 intervalUnit;
                  
              
                  
                  
                      private 
    
        Links
    
 links;
                  
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              
                  
                  
                      private 
    
        Month
    
 month;
                  
              
                  
                  
                      private 
    
        String
    
 name;
                  
              
                  
                  
                      private 
    
        String
    
 paymentReference;
                  
              
                  
                  
                      private 
    
        Boolean
    
 retryIfPossible;
                  
              
                  
                  
                      private 
    
        String
    
 startDate;
                  
              

              
                  

                  
                      /**
                       * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withAmount(
    
        Integer
    
 amount) {
                      
                          this.amount = amount;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The amount to be deducted from each payment as an app fee, to be paid to the partner integration
* which created the subscription, in the lowest denomination for the currency (e.g. pence in GBP,
* cents in EUR).
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withAppFee(
    
        Integer
    
 appFee) {
                      
                          this.appFee = appFee;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The total number of payments that should be taken by this subscription.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withCount(
    
        Integer
    
 count) {
                      
                          this.count = count;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code. Currently "AUD",
* "CAD", "DKK", "EUR", "GBP", "NZD", "SEK" and "USD" are supported.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withCurrency(
    
        String
    
 currency) {
                      
                          this.currency = currency;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * As per RFC 2445. The day of the month to charge customers on. `1`-`28` or `-1` to indicate the
* last day of the month.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withDayOfMonth(
    
        Integer
    
 dayOfMonth) {
                      
                          this.dayOfMonth = dayOfMonth;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Date on or after which no further payments should be created.
* <br />
* If this field is blank and `count` is not specified, the subscription will continue forever.
* <br />
* <p class="deprecated-notice"><strong>Deprecated</strong>: This field will be removed in a future
* API version. Use `count` to specify a number of payments instead.</p>
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withEndDate(
    
        String
    
 endDate) {
                      
                          this.endDate = endDate;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of `interval_units` between customer charge dates. Must be greater than or equal to `1`.
* Must result in at least one charge date per year. Defaults to `1`.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withInterval(
    
        Integer
    
 interval) {
                      
                          this.interval = interval;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The unit of time between customer charge dates. One of `weekly`, `monthly` or `yearly`.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withIntervalUnit(
    
        IntervalUnit
    
 intervalUnit) {
                      
                          this.intervalUnit = intervalUnit;
                      

                      return this;
                  }

                  
              
                  

                  
                  public 
    SubscriptionCreateRequest

                      withLinks(
    
        Links
    
 links) {
                      
                          this.links = links;
                      

                      return this;
                  }

                  
                      
                          
                              
                              
                                  /**
                                    * ID of the associated [mandate](#core-endpoints-mandates) which the subscription will create
* payments against.
                                   */
                              
                              public 
    SubscriptionCreateRequest

                                  withLinksMandate(
                                      
    
        String
    
 mandate
                                  ) {
                                  if (links == null) {
                                      links = new 
    
        Links
    
();
                                  }

                                  links.withMandate(mandate);
                                  return this;
                              }
                          
                      
                  
              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    SubscriptionCreateRequest

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
    SubscriptionCreateRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              
                  

                  
                      /**
                       * Name of the month on which to charge a customer. Must be lowercase. Only applies
* when the interval_unit is `yearly`.
* 
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withMonth(
    
        Month
    
 month) {
                      
                          this.month = month;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Optional name for the subscription. This will be set as the description on each payment created.
* Must not exceed 255 characters.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withName(
    
        String
    
 name) {
                      
                          this.name = name;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * An optional payment reference. This will be set as the reference on each payment
* created and will appear on your customer's bank statement. See the documentation for
* the [create payment endpoint](#payments-create-a-payment) for more details.
* <br />
* <p class="restricted-notice"><strong>Restricted</strong>: You need your own Service User Number to
* specify a payment reference for Bacs payments.</p>
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withPaymentReference(
    
        String
    
 paymentReference) {
                      
                          this.paymentReference = paymentReference;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * On failure, automatically retry payments using [intelligent
* retries](#success-intelligent-retries). Default is `false`.
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withRetryIfPossible(
    
        Boolean
    
 retryIfPossible) {
                      
                          this.retryIfPossible = retryIfPossible;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The date on which the first payment should be charged. Must be on or after the
* [mandate](#core-endpoints-mandates)'s `next_possible_charge_date`. When left blank and `month` or
* `day_of_month` are provided, this will be set to the date of the first payment. If created without
* `month` or `day_of_month` this will be set as the mandate's `next_possible_charge_date`
                       */
                  
                  public 
    SubscriptionCreateRequest

                      withStartDate(
    
        String
    
 startDate) {
                      
                          this.startDate = startDate;
                      

                      return this;
                  }

                  
              

              
                  
                      
                  
                      
                  
                      
                          public 
    SubscriptionCreateRequest
 withIdempotencyKey(String idempotencyKey) {
                              super.setIdempotencyKey(idempotencyKey);
                              return this;
                          }

                          @Override
                          protected GetRequest<Subscription> handleConflict(HttpClient httpClient, String id) {
                              SubscriptionGetRequest request = new SubscriptionGetRequest(httpClient, id);

                              for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                                  request = request.withHeader(header.getKey(), header.getValue());
                              }

                              return request;
                          }
                      
                  
                      
                  
                      
                  
                      
                  
                      
                  
              
          

          private SubscriptionCreateRequest(HttpClient httpClient
              
              
          ) {

              
                  super(httpClient);
              

              
          }

              public 
    SubscriptionCreateRequest
 withHeader(String headerName, String headerValue) {
                  this.addHeader(headerName, headerValue);
                  return this;
              }

          

          

          @Override
          protected String getPathTemplate() {
              return "subscriptions";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        
            
                
    public enum IntervalUnit {
        
            @SerializedName("weekly") WEEKLY
        
            ,@SerializedName("monthly") MONTHLY
        
            ,@SerializedName("yearly") YEARLY
        ;

        @Override
        public String toString() {
          
            return name().toLowerCase();
          
        }
    }

            
        

        
    
        

        
    
        

        
    
        
            
                
    public enum Month {
        
            @SerializedName("january") JANUARY
        
            ,@SerializedName("february") FEBRUARY
        
            ,@SerializedName("march") MARCH
        
            ,@SerializedName("april") APRIL
        
            ,@SerializedName("may") MAY
        
            ,@SerializedName("june") JUNE
        
            ,@SerializedName("july") JULY
        
            ,@SerializedName("august") AUGUST
        
            ,@SerializedName("september") SEPTEMBER
        
            ,@SerializedName("october") OCTOBER
        
            ,@SerializedName("november") NOVEMBER
        
            ,@SerializedName("december") DECEMBER
        ;

        @Override
        public String toString() {
          
            return name().toLowerCase();
          
        }
    }

            
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  
                      
    

    public static class Links {
        
            
            private 
    
        String
    
 mandate;
        

        
            
            
                /**
                 * ID of the associated [mandate](#core-endpoints-mandates) which the subscription will create
* payments against.
                 */
            
            public Links withMandate(
    
        String
    
 mandate) {
                this.mandate = mandate;
                return this;
            }
        

        

        
    
        

        
    

    }

                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        /**
         * Request class for {@link SubscriptionService#list }.
         *
          * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your subscriptions.
         */
        public static final class SubscriptionListRequest
        
            <S>
        
        extends
        
            ListRequest<S, Subscription>
         {
          

          
              
                  
                  
              
                  
                  
              
                  
                  
                      private 
    
        CreatedAt
    
 createdAt;
                  
              
                  
                  
                      private 
    
        String
    
 customer;
                  
              
                  
                  
              
                  
                  
                      private 
    
        String
    
 mandate;
                  
              
                  
                  
                      private 
    
      List<
    
        String
    
>
    
 status;
                  
              

              
                  

                  
                      /**
                       * Cursor pointing to the start of the desired set.
                       */
                  
                  public 
    SubscriptionListRequest<S>

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
    SubscriptionListRequest<S>

                      withBefore(
    
        String
    
 before) {
                      
                          setBefore(before);
                      

                      return this;
                  }

                  
              
                  

                  
                  public 
    SubscriptionListRequest<S>

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
    SubscriptionListRequest<S>

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
    SubscriptionListRequest<S>

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
    SubscriptionListRequest<S>

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
    SubscriptionListRequest<S>

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
                       * Unique identifier, beginning with "CU".
                       */
                  
                  public 
    SubscriptionListRequest<S>

                      withCustomer(
    
        String
    
 customer) {
                      
                          this.customer = customer;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Number of records to return.
                       */
                  
                  public 
    SubscriptionListRequest<S>

                      withLimit(
    
        Integer
    
 limit) {
                      
                          setLimit(limit);
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Unique identifier, beginning with "MD". Note that this prefix may not apply to mandates created
* before 2016.
                       */
                  
                  public 
    SubscriptionListRequest<S>

                      withMandate(
    
        String
    
 mandate) {
                      
                          this.mandate = mandate;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Upto 5 of:
* <ul>
* <li>`pending_customer_approval`</li>
* <li>`customer_approval_denied`</li>
* <li>`active`</li>
* <li>`finished`</li>
* <li>`cancelled`</li>
* <li>`paused`</li>
* </ul>
* Omit entirely to include subscriptions in all states.
                       */
                  
                  public 
    SubscriptionListRequest<S>

                      withStatus(
    
      List<
    
        String
    
>
    
 status) {
                      
                          this.status = status;
                      

                      return this;
                  }

                  
                      
                          /**
                           * Upto 5 of:
* <ul>
* <li>`pending_customer_approval`</li>
* <li>`customer_approval_denied`</li>
* <li>`active`</li>
* <li>`finished`</li>
* <li>`cancelled`</li>
* <li>`paused`</li>
* </ul>
* Omit entirely to include subscriptions in all states.
                           */
                      
                      public 
    SubscriptionListRequest<S>

                          withStatus(
                              
    
        String
    
 status) {
                          if (this.status == null) {
                              this.status = new ArrayList<>();
                          }

                          this.status.add(status);
                          return this;
                      }
                  
              

              
          

          private SubscriptionListRequest(HttpClient httpClient
              
                  , ListRequestExecutor<S, Subscription> executor
              
              
          ) {

              
                  super(httpClient, executor);
              

              
          }

              public 
    SubscriptionListRequest<S>
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
                          
                      
                          
                          
                              if (customer != null) {
                                  
                                      params.put("customer", customer);
                                  
                              }
                          
                      
                          
                          
                      
                          
                          
                              if (mandate != null) {
                                  
                                      params.put("mandate", mandate);
                                  
                              }
                          
                      
                          
                          
                              if (status != null) {
                                  
                                      params.put("status", Joiner.on(",").join(status));
                                  
                              }
                          
                      

                      return params.build();
                  }
              
          

          @Override
          protected String getPathTemplate() {
              return "subscriptions";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected TypeToken<List<Subscription>> getTypeToken() {
                  return new TypeToken<List<Subscription>>() {};
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
         * Request class for {@link SubscriptionService#get }.
         *
          * Retrieves the details of a single subscription.
         */
        public static final class SubscriptionGetRequest
        
        extends
        
            GetRequest<Subscription>
         {
          
              @PathParam
              private final String identity;
          

          

          private SubscriptionGetRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SubscriptionGetRequest
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
              return "subscriptions/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
              }
          

          

          

          

          
        }
    
        
        /**
         * Request class for {@link SubscriptionService#update }.
         *
          * Updates a subscription object.
* 
* This fails with:
* 
* - `validation_failed` if invalid data is provided when attempting to update a subscription.
* 
* - `subscription_not_active` if the subscription is no longer active.
* 
* - `subscription_already_ended` if the subscription has taken all payments.
* 
* - `mandate_payments_require_approval` if the amount is being changed and the mandate requires
* approval.
* 
* - `number_of_subscription_amendments_exceeded` error if the subscription amount has already been
* changed 10 times.
* 
* - `forbidden` if the amount is being changed, and the subscription was created by an app and you
* are not authenticated as that app, or if the subscription was not created by an app and you are
* authenticated as an app
* 
* - `resource_created_by_another_app` if the app fee is being changed, and the subscription was
* created by an app other than the app you are authenticated as
* 
         */
        public static final class SubscriptionUpdateRequest
        
        extends
        
            PutRequest<Subscription>
         {
          
              @PathParam
              private final String identity;
          

          
              
                  
                  
                      private 
    
        Integer
    
 amount;
                  
              
                  
                  
                      private 
    
        Integer
    
 appFee;
                  
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              
                  
                  
                      private 
    
        String
    
 name;
                  
              
                  
                  
                      private 
    
        String
    
 paymentReference;
                  
              
                  
                  
                      private 
    
        Boolean
    
 retryIfPossible;
                  
              

              
                  

                  
                      /**
                       * Amount in the lowest denomination for the currency (e.g. pence in GBP, cents in EUR).
                       */
                  
                  public 
    SubscriptionUpdateRequest

                      withAmount(
    
        Integer
    
 amount) {
                      
                          this.amount = amount;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * The amount to be deducted from each payment as an app fee, to be paid to the partner integration
* which created the subscription, in the lowest denomination for the currency (e.g. pence in GBP,
* cents in EUR).
                       */
                  
                  public 
    SubscriptionUpdateRequest

                      withAppFee(
    
        Integer
    
 appFee) {
                      
                          this.appFee = appFee;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    SubscriptionUpdateRequest

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
    SubscriptionUpdateRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              
                  

                  
                      /**
                       * Optional name for the subscription. This will be set as the description on each payment created.
* Must not exceed 255 characters.
                       */
                  
                  public 
    SubscriptionUpdateRequest

                      withName(
    
        String
    
 name) {
                      
                          this.name = name;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * An optional payment reference. This will be set as the reference on each payment
* created and will appear on your customer's bank statement. See the documentation for
* the [create payment endpoint](#payments-create-a-payment) for more details.
* <br />
* <p class="restricted-notice"><strong>Restricted</strong>: You need your own Service User Number to
* specify a payment reference for Bacs payments.</p>
                       */
                  
                  public 
    SubscriptionUpdateRequest

                      withPaymentReference(
    
        String
    
 paymentReference) {
                      
                          this.paymentReference = paymentReference;
                      

                      return this;
                  }

                  
              
                  

                  
                      /**
                       * On failure, automatically retry payments using [intelligent
* retries](#success-intelligent-retries). Default is `false`.
                       */
                  
                  public 
    SubscriptionUpdateRequest

                      withRetryIfPossible(
    
        Boolean
    
 retryIfPossible) {
                      
                          this.retryIfPossible = retryIfPossible;
                      

                      return this;
                  }

                  
              

              
          

          private SubscriptionUpdateRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SubscriptionUpdateRequest
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
              return "subscriptions/:identity";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
              }
          

          

          
              @Override
              protected boolean hasBody() {
                  return true;
              }
          

          

          
              
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    
        

        
    


              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
                  

                  
              
          
        }
    
        
        /**
         * Request class for {@link SubscriptionService#pause }.
         *
          * Pause a subscription object.
* No payments will be created until it is resumed.
* 
* This can only be used when a subscription is collecting a fixed number of payments (created using
* `count`),
* when they continue forever (created without `count` or `end_date`) or
* the subscription is already paused for a number of cycles.
* 
* When `pause_cycles` is omitted the subscription is paused until the [resume
* endpoint](#subscriptions-resume-a-subscription) is called.
* If the subscription is collecting a fixed number of payments, `end_date` will be set to `null`.
* When paused indefinitely, `upcoming_payments` will be empty.
* 
* When `pause_cycles` is provided the subscription will be paused for the number of cycles
* requested.
* If the subscription is collecting a fixed number of payments, `end_date` will be set to a new
* value.
* When paused for a number of cycles, `upcoming_payments` will still contain the upcoming charge
* dates.
* 
* This fails with:
* 
* - `forbidden` if the subscription was created by an app and you are not authenticated as that app,
* or if the subscription was not created by an app and you are authenticated as an app
* 
* - `validation_failed` if invalid data is provided when attempting to pause a subscription.
* 
* - `subscription_paused_cannot_update_cycles` if the subscription is already paused for a number of
* cycles and the request provides a value for `pause_cycle`.
* 
* - `subscription_cannot_be_paused` if the subscription cannot be paused.
* 
* - `subscription_already_ended` if the subscription has taken all payments.
* 
* - `pause_cycles_must_be_greater_than_or_equal_to` if the provided value for `pause_cycles` cannot
* be satisfied.
* 
         */
        public static final class SubscriptionPauseRequest
        
        extends
        
            PostRequest<Subscription>
         {
          
              @PathParam
              private final String identity;
          

          
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              
                  
                  
                      private 
    
        Integer
    
 pauseCycles;
                  
              

              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    SubscriptionPauseRequest

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
    SubscriptionPauseRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              
                  

                  
                      /**
                       * The number of cycles to pause a subscription for. A cycle is one duration of `interval` and
* `interval_unit`.
                       */
                  
                  public 
    SubscriptionPauseRequest

                      withPauseCycles(
    
        Integer
    
 pauseCycles) {
                      
                          this.pauseCycles = pauseCycles;
                      

                      return this;
                  }

                  
              

              
          

          private SubscriptionPauseRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SubscriptionPauseRequest
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
              return "subscriptions/:identity/actions/pause";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
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
    
        
        /**
         * Request class for {@link SubscriptionService#resume }.
         *
          * Resume a subscription object.
* Payments will start to be created again based on the subscriptions recurrence rules.
* The `charge_date` on the next payment will be the same as the subscriptions
* `earliest_charge_date_after_resume`
* 
* This fails with:
* 
* - `forbidden` if the subscription was created by an app and you are not authenticated as that app,
* or if the subscription was not created by an app and you are authenticated as an app
* 
* - `validation_failed` if invalid data is provided when attempting to resume a subscription.
* 
* - `subscription_not_paused` if the subscription is not paused.
* 
         */
        public static final class SubscriptionResumeRequest
        
        extends
        
            PostRequest<Subscription>
         {
          
              @PathParam
              private final String identity;
          

          
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              

              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    SubscriptionResumeRequest

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
    SubscriptionResumeRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              

              
          

          private SubscriptionResumeRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SubscriptionResumeRequest
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
              return "subscriptions/:identity/actions/resume";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
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
    
        
        /**
         * Request class for {@link SubscriptionService#cancel }.
         *
          * Immediately cancels a subscription; no more payments will be created under it. Any metadata
* supplied to this endpoint will be stored on the payment cancellation event it causes.
* 
* This will fail with a cancellation_failed error if the subscription is already cancelled or
* finished.
         */
        public static final class SubscriptionCancelRequest
        
        extends
        
            PostRequest<Subscription>
         {
          
              @PathParam
              private final String identity;
          

          
              
                  
                  
                      private 
    
        Map<String, String>
    
 metadata;
                  
              

              
                  

                  
                      /**
                       * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
* values up to 500 characters.
                       */
                  
                  public 
    SubscriptionCancelRequest

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
    SubscriptionCancelRequest

                              withMetadata(String key, String value) {
                              if (metadata == null) {
                                  metadata = new HashMap<>();
                              }

                              metadata.put(key, value);
                              return this;
                          }
                      
                  
              

              
          

          private SubscriptionCancelRequest(HttpClient httpClient
              
              
                  , String identity
              
          ) {

              
                  super(httpClient);
              

              
                  
                  this.identity = identity;
              
          }

              public 
    SubscriptionCancelRequest
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
              return "subscriptions/:identity/actions/cancel";
          }

          @Override
          protected String getEnvelope() {
              return "subscriptions";
          }

          
              @Override
              protected Class<Subscription> getResponseClass() {
                  return Subscription.class;
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
