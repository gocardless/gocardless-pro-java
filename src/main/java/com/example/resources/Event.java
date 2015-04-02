

package com.example.resources;

import java.util.List;

public class Event {
    
        
            
            
                

                private String action;

                public String getAction() {
                    return action;
                }

                public void setAction(String action) {
                    this.action = action;
                }
            
        
            
            
                

                private String createdAt;

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }
            
        
            
            
                

                private Object details;

                public Object getDetails() {
                    return details;
                }

                public void setDetails(Object details) {
                    this.details = details;
                }
            
        
            
            
                

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            
        
            
            
                

                private Object links;

                public Object getLinks() {
                    return links;
                }

                public void setLinks(Object links) {
                    this.links = links;
                }
            
        
            
            
                

                private Object metadata;

                public Object getMetadata() {
                    return metadata;
                }

                public void setMetadata(Object metadata) {
                    this.metadata = metadata;
                }
            
        
            
            
                

                public enum ResourceType {
                    
                        PAYMENTS,
                    
                        MANDATES,
                    
                        PAYOUTS,
                    
                        REFUNDS,
                    
                        SUBSCRIPTIONS,
                    
                }

                private ResourceType resourceType;

                public ResourceType getResourceType() {
                    return resourceType;
                }

                public void setResourceType(ResourceType resourceType) {
                    this.resourceType = resourceType;
                }
            
        
    
}
