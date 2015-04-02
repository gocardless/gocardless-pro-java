


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.PublishableApiKey;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class PublishableApiKeyRepository {
    private ExampleHttpClient httpClient;

    public PublishableApiKeyRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public PublishableApiKey create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public PublishableApiKey list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public PublishableApiKey get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/publishable_api_key/:identity", params.build(), "publishable_api_keys", PublishableApiKey.class);
        
        }
    
        
        public PublishableApiKey update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public PublishableApiKey disable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
