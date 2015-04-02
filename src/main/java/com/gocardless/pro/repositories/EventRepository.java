


package com.gocardless.pro.repositories;

import com.gocardless.pro.GoCardlessHttpClient;
import com.gocardless.pro.resources.Event;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class EventRepository {
    private GoCardlessHttpClient httpClient;

    public EventRepository(GoCardlessHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public Event list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public Event get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/events/:identity", params.build(), "events", Event.class);
        
        }
    
}
