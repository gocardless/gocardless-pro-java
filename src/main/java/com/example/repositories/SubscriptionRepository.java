


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.Subscription;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class SubscriptionRepository {
    private ExampleHttpClient httpClient;

    public SubscriptionRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Subscription create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Subscription list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Subscription get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/subscriptions/:identity", params.build(), "subscriptions", Subscription.class);
        
        }
    
        
        public Subscription update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Subscription cancel(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
