package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.ApiKey;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiKeyService {
    private HttpClient httpClient;

    public ApiKeyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public ApiKeyListRequest list() throws IOException {
        return new ApiKeyListRequest(httpClient);
    }

    public ApiKeyGetRequest get(String identity) throws IOException {
        return new ApiKeyGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void disable(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public static final class ApiKeyListRequest extends ListRequest<ApiKey> {
        private String after;

        public ApiKeyListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public ApiKeyListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public ApiKeyListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

        public ApiKeyListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String role;

        public ApiKeyListRequest withRole(String role) {
            this.role = role;
            return this;
        }

        private ApiKeyListRequest(HttpClient httpClient) {
            super(httpClient, "/api_keys", "api_keys", new TypeToken<List<ApiKey>>() {});
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
            if (enabled != null) {
                params.put("enabled", enabled);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (role != null) {
                params.put("role", role);
            }
            return params.build();
        }
    }

    public static final class ApiKeyGetRequest extends GetRequest<ApiKey> {
        private final String identity;

        private ApiKeyGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/api_keys/:identity", "api_keys", ApiKey.class);
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
