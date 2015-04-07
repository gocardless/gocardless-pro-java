


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Refund;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RefundRepository {
    private HttpClient httpClient;

    public RefundRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public RefundListRequest list() throws IOException {
                return new RefundListRequest(httpClient
                
                );
        
        }
    
        
        
            public RefundGetRequest get(String identity) throws IOException {
                return new RefundGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class RefundListRequest extends ListRequest<Refund> {
              

              private RefundListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/refunds", "refunds",
                      
                          new TypeToken<List<Refund>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class RefundGetRequest extends GetRequest<Refund> {
              
                  private final String identity;
              

              private RefundGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/refunds/:identity", "refunds",
                      
                          Refund.class
                      
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
