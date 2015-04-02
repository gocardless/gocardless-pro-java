


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Refund;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class RefundRepository {
    private GoCardlessHttpClient httpClient;

    public RefundRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Refund create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Refund list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Refund get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/refunds/:identity", params.build(), "refunds", Refund.class);
        
        }
    
        
        public Refund update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
