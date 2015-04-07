


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.RedirectFlow;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RedirectFlowRepository {
    private HttpClient httpClient;

    public RedirectFlowRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public RedirectFlowGetRequest get(String identity) throws IOException {
                return new RedirectFlowGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void complete(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class RedirectFlowGetRequest extends GetRequest<RedirectFlow> {
              
                  private final String identity;
              

              private RedirectFlowGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/redirect_flows/:identity", "redirect_flows",
                      
                          RedirectFlow.class
                      
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
