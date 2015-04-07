


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Creditor;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreditorRepository {
    private HttpClient httpClient;

    public CreditorRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public CreditorListRequest list() throws IOException {
                return new CreditorListRequest(httpClient
                
                );
        
        }
    
        
        
            public CreditorGetRequest get(String identity) throws IOException {
                return new CreditorGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class CreditorListRequest extends ListRequest<Creditor> {
              

              private CreditorListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/creditors", "creditors",
                      
                          new TypeToken<List<Creditor>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class CreditorGetRequest extends GetRequest<Creditor> {
              
                  private final String identity;
              

              private CreditorGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/creditors/:identity", "creditors",
                      
                          Creditor.class
                      
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
