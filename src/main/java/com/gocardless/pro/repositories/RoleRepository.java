


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Role;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class RoleRepository {
    private GoCardlessHttpClient httpClient;

    public RoleRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Role create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Role list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Role get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/roles/:identity", params.build(), "roles", Role.class);
        
        }
    
        
        public Role update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Role disable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
