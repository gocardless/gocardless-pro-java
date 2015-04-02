


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.User;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class UserRepository {
    private ExampleHttpClient httpClient;

    public UserRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public User create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public User list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public User get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/users/:identity", params.build(), "users", User.class);
        
        }
    
        
        public User update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public User enable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public User disable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
