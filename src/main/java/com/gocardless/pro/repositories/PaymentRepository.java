


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Payment;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PaymentRepository {
    private HttpClient httpClient;

    public PaymentRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public PaymentListRequest list() throws IOException {
                return new PaymentListRequest(httpClient
                
                );
        
        }
    
        
        
            public PaymentGetRequest get(String identity) throws IOException {
                return new PaymentGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void cancel(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void retry(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class PaymentListRequest extends ListRequest<Payment> {
              

              private PaymentListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/payments", "payments",
                      
                          new TypeToken<List<Payment>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class PaymentGetRequest extends GetRequest<Payment> {
              
                  private final String identity;
              

              private PaymentGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/payments/:identity", "payments",
                      
                          Payment.class
                      
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
