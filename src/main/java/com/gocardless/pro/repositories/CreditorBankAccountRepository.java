


package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.CreditorBankAccount;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreditorBankAccountRepository {
    private HttpClient httpClient;

    public CreditorBankAccountRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        
            public void create() throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    
        
        
            public CreditorBankAccountListRequest list() throws IOException {
                return new CreditorBankAccountListRequest(httpClient
                
                );
        
        }
    
        
        
            public CreditorBankAccountGetRequest get(String identity) throws IOException {
                return new CreditorBankAccountGetRequest(httpClient
                
                    , identity
                
                );
        
        }
    
        
        
            public void disable(String identity) throws IOException {
                throw new IllegalStateException("Not implemented!");
        
        }
    

    
        
        
    
        
        
            public final class CreditorBankAccountListRequest extends ListRequest<CreditorBankAccount> {
              

              private CreditorBankAccountListRequest(HttpClient httpClient
                  
              ) {
                  super(httpClient, "/creditor_bank_accounts", "creditor_bank_accounts",
                      
                          new TypeToken<List<CreditorBankAccount>>() {}
                      
                  );

                  
              }

              @Override
              protected Map<String, String> getParams() {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

                  

                  return params.build();
              }
            }
        
    
        
        
            public final class CreditorBankAccountGetRequest extends GetRequest<CreditorBankAccount> {
              
                  private final String identity;
              

              private CreditorBankAccountGetRequest(HttpClient httpClient
                  
                      , String identity
                  
              ) {
                  super(httpClient, "/creditor_bank_accounts/:identity", "creditor_bank_accounts",
                      
                          CreditorBankAccount.class
                      
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
