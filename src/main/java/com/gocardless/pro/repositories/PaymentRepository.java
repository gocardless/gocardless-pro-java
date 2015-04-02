


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Payment;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class PaymentRepository {
    private GoCardlessHttpClient httpClient;

    public PaymentRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Payment create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Payment list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Payment get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/payments/:identity", params.build(), "payments", Payment.class);
        
        }
    
        
        public Payment update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Payment cancel(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Payment retry(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
