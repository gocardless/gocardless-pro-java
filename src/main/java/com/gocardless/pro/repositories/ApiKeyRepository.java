


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.ApiKey;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiKeyRepository {
    private HttpClient httpClient;

    public ApiKeyRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public ApiKeyListRequest list() throws IOException {
                return new ApiKeyListRequest(httpClient
                
                );
        
        }
    
        
        
            public ApiKeyGetRequest get(String identity) throws IOException {
                return new ApiKeyGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void disable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class ApiKeyListRequest extends ListRequest<ApiKey> {
              

              private ApiKeyListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/api_keys", "api_keys",
                      
                          new TypeToken<List<ApiKey>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class ApiKeyGetRequest extends GetRequest<ApiKey> {
              
                  private final String identity;
              

              private ApiKeyGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/api_keys/:identity", "api_keys",
                      
                          ApiKey.class
                      
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
