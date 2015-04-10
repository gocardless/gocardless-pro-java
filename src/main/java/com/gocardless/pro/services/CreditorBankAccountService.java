package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
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

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public CreditorBankAccountListRequest list() throws IOException {
        return new CreditorBankAccountListRequest(httpClient);
    }

    public CreditorBankAccountGetRequest get(String identity) throws IOException {
        return new CreditorBankAccountGetRequest(httpClient, identity);
    }

    public void disable(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
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
            super(httpClient, "/creditor_bank_accounts", "creditor_bank_accounts",
                    new TypeToken<List<CreditorBankAccount>>() {});
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
    }

    public static final class CreditorBankAccountGetRequest extends GetRequest<CreditorBankAccount> {
        private final String identity;

        private CreditorBankAccountGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/creditor_bank_accounts/:identity", "creditor_bank_accounts",
                    CreditorBankAccount.class);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }
    }
}
