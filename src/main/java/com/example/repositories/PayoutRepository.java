


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.Payout;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class PayoutRepository {
    private ExampleHttpClient httpClient;

    public PayoutRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Payout list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Payout get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/payouts/:identity", params.build(), "payouts", Payout.class);
        
        }
    
}
