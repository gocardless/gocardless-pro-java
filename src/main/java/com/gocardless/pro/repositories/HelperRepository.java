


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Helper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class HelperRepository {
    private GoCardlessHttpClient httpClient;

    public HelperRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Helper mandate() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Helper modulusCheck() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
