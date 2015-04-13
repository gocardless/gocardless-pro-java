package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.CreditorBankAccount;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreditorBankAccountService {
    private HttpClient httpClient;

    public CreditorBankAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CreditorBankAccountCreateRequest create() throws IOException {
        return new CreditorBankAccountCreateRequest(httpClient);
    }

    public CreditorBankAccountListRequest list() throws IOException {
        return new CreditorBankAccountListRequest(httpClient);
    }

    public CreditorBankAccountGetRequest get(String identity) throws IOException {
        return new CreditorBankAccountGetRequest(httpClient, identity);
    }

    public CreditorBankAccountDisableRequest disable(String identity) throws IOException {
        return new CreditorBankAccountDisableRequest(httpClient, identity);
    }

    public static final class CreditorBankAccountCreateRequest extends
            PostRequest<CreditorBankAccount> {
        private String accountHolderName;

        public CreditorBankAccountCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        private String accountNumber;

        public CreditorBankAccountCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        private String bankCode;

        public CreditorBankAccountCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        private String branchCode;

        public CreditorBankAccountCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        private String countryCode;

        public CreditorBankAccountCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private String currency;

        public CreditorBankAccountCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        private String iban;

        public CreditorBankAccountCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        private Object links;

        public CreditorBankAccountCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private Object metadata;

        public CreditorBankAccountCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private Boolean setAsDefaultPayoutAccount;

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
    }

    public static final class CreditorBankAccountListRequest extends
            ListRequest<CreditorBankAccount> {
        private String after;

        public CreditorBankAccountListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public CreditorBankAccountListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private String creditor;

        public CreditorBankAccountListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public CreditorBankAccountListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

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
