


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.RedirectFlow;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class RedirectFlowRepository {
    private ExampleHttpClient httpClient;

    public RedirectFlowRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public RedirectFlow create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public RedirectFlow get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/redirect_flows/:identity", params.build(), "redirect_flows", RedirectFlow.class);
        
        }
    
        
        public RedirectFlow complete(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
