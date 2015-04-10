package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Creditor;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreditorService {
    private HttpClient httpClient;

    public CreditorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public CreditorListRequest list() throws IOException {
        return new CreditorListRequest(httpClient);
    }

    public CreditorGetRequest get(String identity) throws IOException {
        return new CreditorGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public static final class CreditorListRequest extends ListRequest<Creditor> {
        private String after;

        public CreditorListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public CreditorListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private Integer limit;

        public CreditorListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private CreditorListRequest(HttpClient httpClient) {
            super(httpClient, "/creditors", "creditors", new TypeToken<List<Creditor>>() {});
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
            if (limit != null) {
                params.put("limit", limit);
            }
            return params.build();
        }
    }

    public static final class CreditorGetRequest extends GetRequest<Creditor> {
        private final String identity;

        private CreditorGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/creditors/:identity", "creditors", Creditor.class);
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
