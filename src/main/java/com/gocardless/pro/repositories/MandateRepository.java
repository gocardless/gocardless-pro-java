


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Mandate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MandateRepository {
    private HttpClient httpClient;

    public MandateRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public MandateListRequest list() throws IOException {
                return new MandateListRequest(httpClient
                
                );
        
        }
    
        
        
            public MandateGetRequest get(String identity) throws IOException {
                return new MandateGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void cancel(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void reinstate(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class MandateListRequest extends ListRequest<Mandate> {
              

              private MandateListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/mandates", "mandates",
                      
                          new TypeToken<List<Mandate>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class MandateGetRequest extends GetRequest<Mandate> {
              
                  private final String identity;
              

              private MandateGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/mandates/:identity", "mandates",
                      
                          Mandate.class
                      
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
