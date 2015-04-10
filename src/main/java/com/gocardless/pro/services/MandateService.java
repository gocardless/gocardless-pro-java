package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Mandate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MandateService {
    private HttpClient httpClient;

    public MandateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public MandateListRequest list() throws IOException {
        return new MandateListRequest(httpClient);
    }

    public MandateGetRequest get(String identity) throws IOException {
        return new MandateGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void cancel(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void reinstate(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public static final class MandateListRequest extends ListRequest<Mandate> {
        private String after;

        public MandateListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public MandateListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private String creditor;

        public MandateListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        private String customer;

        public MandateListRequest withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        private String customerBankAccount;

        public MandateListRequest withCustomerBankAccount(String customerBankAccount) {
            this.customerBankAccount = customerBankAccount;
            return this;
        }

        private Integer limit;

        public MandateListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String reference;

        public MandateListRequest withReference(String reference) {
            this.reference = reference;
            return this;
        }

        private List<String> status;

        public MandateListRequest withStatus(List<String> status) {
            this.status = status;
            return this;
        }

        private MandateListRequest(HttpClient httpClient) {
            super(httpClient, "/mandates", "mandates", new TypeToken<List<Mandate>>() {});
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
            if (customer != null) {
                params.put("customer", customer);
            }
            if (customerBankAccount != null) {
                params.put("customer_bank_account", customerBankAccount);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (reference != null) {
                params.put("reference", reference);
            }
            if (status != null) {
                params.put("status", status);
            }
            return params.build();
        }
    }

    public static final class MandateGetRequest extends GetRequest<Mandate> {
        private final String identity;

        private MandateGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/mandates/:identity", "mandates", Mandate.class);
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
