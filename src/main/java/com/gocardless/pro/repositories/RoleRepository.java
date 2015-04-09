package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Role;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RoleRepository {
    private HttpClient httpClient;

    public RoleRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public RoleListRequest list() throws IOException {
        return new RoleListRequest(httpClient);
    }

    public RoleGetRequest get(String identity) throws IOException {
        return new RoleGetRequest(httpClient, identity);
    }

    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void disable(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public static final class RoleListRequest extends ListRequest<Role> {
        private String after;

        public RoleListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public RoleListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public RoleListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

        public RoleListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private RoleListRequest(HttpClient httpClient) {
            super(httpClient, "/roles", "roles", new TypeToken<List<Role>>() {});
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
            return params.build();
        }
    }

    public static final class RoleGetRequest extends GetRequest<Role> {
        private final String identity;

        private RoleGetRequest(HttpClient httpClient, String identity) {
            super(httpClient, "/roles/:identity", "roles", Role.class);
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
