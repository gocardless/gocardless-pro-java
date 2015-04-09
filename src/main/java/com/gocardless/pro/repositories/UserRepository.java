


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
    

    
        
        
    
        
        
            public static final class UserListRequest extends ListRequest<User> {
              

              
                  
                      

                      
                          

                          private String after;

                          public UserListRequest withAfter(String after) {
                              this.after = after;
                              return this;
                          }
                      
                  
                      

                      
                          

                          private String before;

                          public UserListRequest withBefore(String before) {
                              this.before = before;
                              return this;
                          }
                      
                  
                      

                      
                          

                          public enum Enabled {
                              
                                  TRUE,
                              
                                  FALSE,
                              
                          }

                          private Enabled enabled;

                          public UserListRequest withEnabled(Enabled enabled) {
                              this.enabled = enabled;
                              return this;
                          }
                      
                  
                      

                      
                          

                          private Integer limit;

                          public UserListRequest withLimit(Integer limit) {
                              this.limit = limit;
                              return this;
                          }
                      
                  
                      

                      
                          

                          private String role;

                          public UserListRequest withRole(String role) {
                              this.role = role;
                              return this;
                          }
                      
                  
              

              private UserListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/users", "users",
                      
                          new TypeToken<List<User>>() {}
                      
                  );

                  
              }

              

              
                  @Override
                  protected Map<String, Object> getQueryParams() {
                      ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();

                      
                          if (after != null) {
                              params.put("after", after);
                          }
                      
                          if (before != null) {
                              params.put("before", before);
                          }
                      
                          if (enabled != null) {
                              params.put("enabled", enabled);
                          }
                      
                          if (limit != null) {
                              params.put("limit", limit);
                          }
                      
                          if (role != null) {
                              params.put("role", role);
                          }
                      

                      return params.build();
                  }
              
            }
        
    
        
        
            public static final class UserGetRequest extends GetRequest<User> {
              
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
                  protected Map<String, String> getPathParams() {
                      ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                      
                          params.put("identity", identity);
                      

                      return params.build();
                  }
              

              
            }
        
    
        
        
    
        
        
    
        
        
    
}
