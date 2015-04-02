


package com.example.repositories;

import com.example.ExampleHttpClient;
import com.example.resources.CreditorBankAccount;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class CreditorBankAccountRepository {
    private ExampleHttpClient httpClient;

    public CreditorBankAccountRepository(ExampleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
        
        public CreditorBankAccount create() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public CreditorBankAccount list() throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
        
        public CreditorBankAccount get(String identity) throws IOException {
        
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();

            
                params.put("identity", identity);
            

            return httpClient.get("/creditor_bank_accounts/:identity", params.build(), "creditor_bank_accounts", CreditorBankAccount.class);
        
        }
    
        
        public CreditorBankAccount disable(String identity) throws IOException {
        
            throw new IllegalStateException("Not implemented!");
        
        }
    
}
