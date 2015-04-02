


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.Helper;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class HelperRepository {
    private ExampleHttpClient httpClient;

    public HelperRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Helper mandate() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Helper modulusCheck() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
