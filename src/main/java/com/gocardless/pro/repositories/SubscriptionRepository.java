


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Subscription;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SubscriptionRepository {
    private HttpClient httpClient;

    public SubscriptionRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public SubscriptionListRequest list() throws IOException {
                return new SubscriptionListRequest(httpClient
                
                );
        
        }
    
        
        
            public SubscriptionGetRequest get(String identity) throws IOException {
                return new SubscriptionGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void cancel(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class SubscriptionListRequest extends ListRequest<Subscription> {
              

              private SubscriptionListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/subscriptions", "subscriptions",
                      
                          new TypeToken<List<Subscription>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class SubscriptionGetRequest extends GetRequest<Subscription> {
              
                  private final String identity;
              

              private SubscriptionGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/subscriptions/:identity", "subscriptions",
                      
                          Subscription.class
                      
                  );

                  
                      
                      this.identity = identity;
                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  
                      params.put("identity", identity);
                  

                  return params.build();
              }
            }
        
    
        
        
    
        
        
    
}
