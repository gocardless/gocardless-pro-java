


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Role;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RoleRepository {
    private HttpClient httpClient;

    public RoleRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public RoleListRequest list() throws IOException {
                return new RoleListRequest(httpClient
                
                );
        
        }
    
        
        
            public RoleGetRequest get(String identity) throws IOException {
                return new RoleGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void disable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class RoleListRequest extends ListRequest<Role> {
              

              private RoleListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/roles", "roles",
                      
                          new TypeToken<List<Role>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class RoleGetRequest extends GetRequest<Role> {
              
                  private final String identity;
              

              private RoleGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/roles/:identity", "roles",
                      
                          Role.class
                      
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
