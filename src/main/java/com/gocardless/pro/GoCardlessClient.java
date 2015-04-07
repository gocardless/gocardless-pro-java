

package com.gocardless.pro;

import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.repositories.*;
import com.google.common.annotations.VisibleForTesting;
import com.squareup.okhttp.OkHttpClient;

import java.net.URI;

public class GoCardlessClient {
    private final HttpClient httpClient;
    
        private final ApiKeyRepository apiKeys;
    
        private final CreditorRepository creditors;
    
        private final CreditorBankAccountRepository creditorBankAccounts;
    
        private final CustomerRepository customers;
    
        private final CustomerBankAccountRepository customerBankAccounts;
    
        private final EventRepository events;
    
        private final HelperRepository helpers;
    
        private final MandateRepository mandates;
    
        private final PaymentRepository payments;
    
        private final PayoutRepository payouts;
    
        private final PublishableApiKeyRepository publishableApiKeys;
    
        private final RedirectFlowRepository redirectFlows;
    
        private final RefundRepository refunds;
    
        private final RoleRepository roles;
    
        private final SubscriptionRepository subscriptions;
    
        private final UserRepository users;
    

    private GoCardlessClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        
            this.apiKeys = new ApiKeyRepository(httpClient);
        
            this.creditors = new CreditorRepository(httpClient);
        
            this.creditorBankAccounts = new CreditorBankAccountRepository(httpClient);
        
            this.customers = new CustomerRepository(httpClient);
        
            this.customerBankAccounts = new CustomerBankAccountRepository(httpClient);
        
            this.events = new EventRepository(httpClient);
        
            this.helpers = new HelperRepository(httpClient);
        
            this.mandates = new MandateRepository(httpClient);
        
            this.payments = new PaymentRepository(httpClient);
        
            this.payouts = new PayoutRepository(httpClient);
        
            this.publishableApiKeys = new PublishableApiKeyRepository(httpClient);
        
            this.redirectFlows = new RedirectFlowRepository(httpClient);
        
            this.refunds = new RefundRepository(httpClient);
        
            this.roles = new RoleRepository(httpClient);
        
            this.subscriptions = new SubscriptionRepository(httpClient);
        
            this.users = new UserRepository(httpClient);
        
    }

    
        
        
        public ApiKeyRepository apiKeys() {
            return apiKeys;
        }
    
        
        
        public CreditorRepository creditors() {
            return creditors;
        }
    
        
        
        public CreditorBankAccountRepository creditorBankAccounts() {
            return creditorBankAccounts;
        }
    
        
        
        public CustomerRepository customers() {
            return customers;
        }
    
        
        
        public CustomerBankAccountRepository customerBankAccounts() {
            return customerBankAccounts;
        }
    
        
        
        public EventRepository events() {
            return events;
        }
    
        
        
        public HelperRepository helpers() {
            return helpers;
        }
    
        
        
        public MandateRepository mandates() {
            return mandates;
        }
    
        
        
        public PaymentRepository payments() {
            return payments;
        }
    
        
        
        public PayoutRepository payouts() {
            return payouts;
        }
    
        
        
        public PublishableApiKeyRepository publishableApiKeys() {
            return publishableApiKeys;
        }
    
        
        
        public RedirectFlowRepository redirectFlows() {
            return redirectFlows;
        }
    
        
        
        public RefundRepository refunds() {
            return refunds;
        }
    
        
        
        public RoleRepository roles() {
            return roles;
        }
    
        
        
        public SubscriptionRepository subscriptions() {
            return subscriptions;
        }
    
        
        
        public UserRepository users() {
            return users;
        }
    

    @VisibleForTesting
    HttpClient getHttpClient() {
        return httpClient;
    }

    public static GoCardlessClient create(String apiKey, String apiSecret, String baseUrl) {
        return new GoCardlessClient(new HttpClient(apiKey, apiSecret, baseUrl));
    }
}
