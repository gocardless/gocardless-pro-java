package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.CustomerBankAccount;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class CustomerBankAccountService {
    private HttpClient httpClient;

    public CustomerBankAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CustomerBankAccountCreateRequest create() {
        return new CustomerBankAccountCreateRequest(httpClient);
    }

    public CustomerBankAccountListRequest list() {
        return new CustomerBankAccountListRequest(httpClient);
    }

    public CustomerBankAccountGetRequest get(String identity) {
        return new CustomerBankAccountGetRequest(httpClient, identity);
    }

    public CustomerBankAccountUpdateRequest update(String identity) {
        return new CustomerBankAccountUpdateRequest(httpClient, identity);
    }

    public CustomerBankAccountDisableRequest disable(String identity) {
        return new CustomerBankAccountDisableRequest(httpClient, identity);
    }

    public static final class CustomerBankAccountCreateRequest extends
            PostRequest<CustomerBankAccount> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String currency;
        private String iban;
        private Links links;
        private Map<String, String> metadata;

        public CustomerBankAccountCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        public CustomerBankAccountCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public CustomerBankAccountCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public CustomerBankAccountCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public CustomerBankAccountCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CustomerBankAccountCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CustomerBankAccountCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public CustomerBankAccountCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public CustomerBankAccountCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        private CustomerBankAccountCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String customer;
            private String customerBankAccountToken;

            public Links withCustomer(String customer) {
                this.customer = customer;
                return this;
            }

            public Links withCustomerBankAccountToken(String customerBankAccountToken) {
                this.customerBankAccountToken = customerBankAccountToken;
                return this;
            }
        }
    }

    public static final class CustomerBankAccountListRequest extends
            ListRequest<CustomerBankAccount> {
        private String after;
        private String before;
        private String customer;
        private Enabled enabled;
        private Integer limit;

        public CustomerBankAccountListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public CustomerBankAccountListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public CustomerBankAccountListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public CustomerBankAccountListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomerBankAccountListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private CustomerBankAccountListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (customer != null) {
                params.put("customer", customer);
            }
            if (enabled != null) {
                params.put("enabled", enabled);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected TypeToken<List<CustomerBankAccount>> getTypeToken() {
            return new TypeToken<List<CustomerBankAccount>>() {};
        }

        public enum Enabled {
            TRUE, FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    public static final class CustomerBankAccountGetRequest extends GetRequest<CustomerBankAccount> {
        @PathParam
        private final String identity;

        private CustomerBankAccountGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }
    }

    public static final class CustomerBankAccountUpdateRequest extends
            PutRequest<CustomerBankAccount> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        public CustomerBankAccountUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        private CustomerBankAccountUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class CustomerBankAccountDisableRequest extends
            PostRequest<CustomerBankAccount> {
        @PathParam
        private final String identity;

        private CustomerBankAccountDisableRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
