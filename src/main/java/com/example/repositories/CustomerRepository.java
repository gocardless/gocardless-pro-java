


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.Customer;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class CustomerRepository {
    private ExampleHttpClient httpClient;

    public CustomerRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Customer create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Customer list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Customer get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/customers/:identity", params.build(), "customers", Customer.class);
        
        }
    
        
        public Customer update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
