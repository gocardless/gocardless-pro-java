


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.CustomerBankAccount;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomerBankAccountRepository {
    private HttpClient httpClient;

    public CustomerBankAccountRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public CustomerBankAccountListRequest list() throws IOException {
                return new CustomerBankAccountListRequest(httpClient
                
                );
        
        }
    
        
        
            public CustomerBankAccountGetRequest get(String identity) throws IOException {
                return new CustomerBankAccountGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void update(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public void disable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class CustomerBankAccountListRequest extends ListRequest<CustomerBankAccount> {
              

              private CustomerBankAccountListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/customer_bank_accounts", "customer_bank_accounts",
                      
                          new TypeToken<List<CustomerBankAccount>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class CustomerBankAccountGetRequest extends GetRequest<CustomerBankAccount> {
              
                  private final String identity;
              

              private CustomerBankAccountGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/customer_bank_accounts/:identity", "customer_bank_accounts",
                      
                          CustomerBankAccount.class
                      
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
