


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Payout;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PayoutRepository {
    private HttpClient httpClient;

    public PayoutRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public PayoutListRequest list() throws IOException {
                return new PayoutListRequest(httpClient
                
                );
        
        }
    
        
        
            public PayoutGetRequest get(String identity) throws IOException {
                return new PayoutGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    

    
        
        
            public final class PayoutListRequest extends ListRequest<Payout> {
              

              private PayoutListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/payouts", "payouts",
                      
                          new TypeToken<List<Payout>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class PayoutGetRequest extends GetRequest<Payout> {
              
                  private final String identity;
              

              private PayoutGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/payouts/:identity", "payouts",
                      
                          Payout.class
                      
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
