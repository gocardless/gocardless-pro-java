


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.ApiKey;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class ApiKeyRepository {
    private GoCardlessHttpClient httpClient;

    public ApiKeyRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public ApiKey create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public ApiKey list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public ApiKey get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/api_keys/:identity", params.build(), "api_keys", ApiKey.class);
        
        }
    
        
        public ApiKey update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public ApiKey disable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
