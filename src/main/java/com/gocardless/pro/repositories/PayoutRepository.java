


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Payout;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class PayoutRepository {
    private GoCardlessHttpClient httpClient;

    public PayoutRepository(GoCardlessHttpClient httpClient) {
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
