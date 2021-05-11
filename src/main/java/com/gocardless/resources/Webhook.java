





package com.gocardless.resources;

import java.util.Map;

      /**
       * Represents a webhook resource returned from the API.
       *
        * Basic description of a webhook
       */
    
    public class Webhook {
        private Webhook() {
            // blank to prevent instantiation
        }

        
            
            private 
    
        String
    
 createdAt;
        
            
            private 
    
        String
    
 id;
        
            
            private 
    
        Boolean
    
 isTest;
        
            
            private 
    
        String
    
 requestBody;
        
            
            private 
    
        Map<String, String>
    
 requestHeaders;
        
            
            private 
    
        String
    
 responseBody;
        
            
            private 
    
        Boolean
    
 responseBodyTruncated;
        
            
            private 
    
        Integer
    
 responseCode;
        
            
            private 
    
        Map<String, String>
    
 responseHeaders;
        
            
            private 
    
        Boolean
    
 responseHeadersContentTruncated;
        
            
            private 
    
        Boolean
    
 responseHeadersCountTruncated;
        
            
            private 
    
        Boolean
    
 successful;
        
            
            private 
    
        String
    
 url;
        

        
            

            
                /**
                 * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was created.
                 */
            
            public 
    
        String
    
 getCreatedAt() {
                return createdAt;
            }
        
            

            
                /**
                 * Unique identifier, beginning with "WB".
                 */
            
            public 
    
        String
    
 getId() {
                return id;
            }
        
            

            
                /**
                 * Boolean value showing whether this was a demo webhook for testing
                 */
            
            public 
    
        Boolean
    
 getIsTest() {
                return isTest;
            }
        
            

            
                /**
                 * The body of the request sent to the webhook URL
                 */
            
            public 
    
        String
    
 getRequestBody() {
                return requestBody;
            }
        
            

            
                /**
                 * The request headers sent with the webhook
                 */
            
            public 
    
        Map<String, String>
    
 getRequestHeaders() {
                return requestHeaders;
            }
        
            

            
                /**
                 * The body of the response from the webhook URL
                 */
            
            public 
    
        String
    
 getResponseBody() {
                return responseBody;
            }
        
            

            
                /**
                 * Boolean value indicating the webhook response body was truncated
                 */
            
            public 
    
        Boolean
    
 getResponseBodyTruncated() {
                return responseBodyTruncated;
            }
        
            

            
                /**
                 * The response code from the webhook request
                 */
            
            public 
    
        Integer
    
 getResponseCode() {
                return responseCode;
            }
        
            

            
                /**
                 * The headers sent with the response from the webhook URL
                 */
            
            public 
    
        Map<String, String>
    
 getResponseHeaders() {
                return responseHeaders;
            }
        
            

            
                /**
                 * Boolean indicating the content of response headers was truncated
                 */
            
            public 
    
        Boolean
    
 getResponseHeadersContentTruncated() {
                return responseHeadersContentTruncated;
            }
        
            

            
                /**
                 * Boolean indicating the number of response headers was truncated
                 */
            
            public 
    
        Boolean
    
 getResponseHeadersCountTruncated() {
                return responseHeadersCountTruncated;
            }
        
            

            
                /**
                 * Boolean indicating whether the request was successful or failed
                 */
            
            public 
    
        Boolean
    
 getSuccessful() {
                return successful;
            }
        
            

            
                /**
                 * URL the webhook was POST-ed to
                 */
            
            public 
    
        String
    
 getUrl() {
                return url;
            }
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        

        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
            

            
        
    }

