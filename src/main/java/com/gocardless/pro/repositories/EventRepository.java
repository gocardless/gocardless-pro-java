


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Event;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EventRepository {
    private HttpClient httpClient;

    public EventRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public EventListRequest list() throws IOException {
                return new EventListRequest(httpClient
                
                );
        
        }
    
        
        
            public EventGetRequest get(String identity) throws IOException {
                return new EventGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    

    
        
        
            public final class EventListRequest extends ListRequest<Event> {
              

              private EventListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/events", "events",
                      
                          new TypeToken<List<Event>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class EventGetRequest extends GetRequest<Event> {
              
                  private final String identity;
              

              private EventGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/events/:identity", "events",
                      
                          Event.class
                      
                  );

                  
                      
                      this.identity = identity;
                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  
                      params.put("identity", identity);
                  

                  return params.build();
              }
            }
        
    
}
