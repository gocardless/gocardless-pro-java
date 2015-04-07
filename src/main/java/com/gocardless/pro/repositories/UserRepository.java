


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.User;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private HttpClient httpClient;

    public UserRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public UserListRequest list() throws IOException {
                return new UserListRequest(httpClient
                
                );
        
        }
    
        
        
            public UserGetRequest get(String identity) throws IOException {
                return new UserGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void enable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void disable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class UserListRequest extends ListRequest<User> {
              

              private UserListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/users", "users",
                      
                          new TypeToken<List<User>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class UserGetRequest extends GetRequest<User> {
              
                  private final String identity;
              

              private UserGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/users/:identity", "users",
                      
                          User.class
                      
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
