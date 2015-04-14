package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.CreditorBankAccount;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class CreditorBankAccountService {
    private HttpClient httpClient;

    public CreditorBankAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CreditorBankAccountCreateRequest create() {
        return new CreditorBankAccountCreateRequest(httpClient);
    }

    public CreditorBankAccountListRequest list() {
        return new CreditorBankAccountListRequest(httpClient);
    }

    public CreditorBankAccountGetRequest get(String identity) {
        return new CreditorBankAccountGetRequest(httpClient, identity);
    }

    public CreditorBankAccountDisableRequest disable(String identity) {
        return new CreditorBankAccountDisableRequest(httpClient, identity);
    }

    public static final class CreditorBankAccountCreateRequest extends
            PostRequest<CreditorBankAccount> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String currency;
        private String iban;
        private Links links;
        private Map<String, String> metadata;
        private Boolean setAsDefaultPayoutAccount;

        public CreditorBankAccountCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        public CreditorBankAccountCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public CreditorBankAccountCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public CreditorBankAccountCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public CreditorBankAccountCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorBankAccountCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreditorBankAccountCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public CreditorBankAccountCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public CreditorBankAccountCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public CreditorBankAccountCreateRequest withSetAsDefaultPayoutAccount(
                Boolean setAsDefaultPayoutAccount) {
            this.setAsDefaultPayoutAccount = setAsDefaultPayoutAccount;
            return this;
        }

        private CreditorBankAccountCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/creditor_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String creditor;

            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    public static final class CreditorBankAccountListRequest extends
            ListRequest<CreditorBankAccount> {
        private String after;
        private String before;
        private String creditor;
        private Enabled enabled;
        private Integer limit;

        public CreditorBankAccountListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public CreditorBankAccountListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public CreditorBankAccountListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        public CreditorBankAccountListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        public CreditorBankAccountListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private CreditorBankAccountListRequest(HttpClient httpClient) {
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
            if (creditor != null) {
                params.put("creditor", creditor);
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
            return "/creditor_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected TypeToken<List<CreditorBankAccount>> getTypeToken() {
            return new TypeToken<List<CreditorBankAccount>>() {};
        }

        public enum Enabled {
            TRUE, FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    public static final class CreditorBankAccountGetRequest extends GetRequest<CreditorBankAccount> {
        @PathParam
        private final String identity;

        private CreditorBankAccountGetRequest(HttpClient httpClient, String identity) {
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
            return "/creditor_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }
    }

    public static final class CreditorBankAccountDisableRequest extends
            PostRequest<CreditorBankAccount> {
        @PathParam
        private final String identity;

        private CreditorBankAccountDisableRequest(HttpClient httpClient, String identity) {
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
            return "/creditor_bank_accounts/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
