


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Creditor;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class CreditorRepository {
    private GoCardlessHttpClient httpClient;

    public CreditorRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Creditor create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Creditor list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Creditor get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/creditors/:identity", params.build(), "creditors", Creditor.class);
        
        }
    
        
        public Creditor update(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
