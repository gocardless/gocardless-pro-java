package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Role;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class RoleService {
    private HttpClient httpClient;

    public RoleService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RoleCreateRequest create() {
        return new RoleCreateRequest(httpClient);
    }

    public RoleListRequest list() {
        return new RoleListRequest(httpClient);
    }

    public RoleGetRequest get(String identity) {
        return new RoleGetRequest(httpClient, identity);
    }

    public RoleUpdateRequest update(String identity) {
        return new RoleUpdateRequest(httpClient, identity);
    }

    public RoleDisableRequest disable(String identity) {
        return new RoleDisableRequest(httpClient, identity);
    }

    public static final class RoleCreateRequest extends PostRequest<Role> {
        private String name;
        private List<Permissions> permissions;

        public RoleCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        public RoleCreateRequest withPermissions(List<Permissions> permissions) {
            this.permissions = permissions;
            return this;
        }

        private RoleCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/roles";
        }

        @Override
        protected String getEnvelope() {
            return "roles";
        }

        @Override
        protected Class<Role> getResponseClass() {
            return Role.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Permissions {
            private String access;
            private String resource;

            public Permissions withAccess(String access) {
                this.access = access;
                return this;
            }

            public Permissions withResource(String resource) {
                this.resource = resource;
                return this;
            }
        }
    }

    public static final class RoleListRequest extends ListRequest<Role> {
        private String after;
        private String before;
        private Enabled enabled;
        private Integer limit;

        public RoleListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public RoleListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public RoleListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        public RoleListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private RoleListRequest(HttpClient httpClient) {
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
            return "/roles";
        }

        @Override
        protected String getEnvelope() {
            return "roles";
        }

        @Override
        protected TypeToken<List<Role>> getTypeToken() {
            return new TypeToken<List<Role>>() {};
        }

        public enum Enabled {
            TRUE, FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    public static final class RoleGetRequest extends GetRequest<Role> {
        @PathParam
        private final String identity;

        private RoleGetRequest(HttpClient httpClient, String identity) {
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
            return "/roles/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "roles";
        }

        @Override
        protected Class<Role> getResponseClass() {
            return Role.class;
        }
    }

    public static final class RoleUpdateRequest extends PutRequest<Role> {
        @PathParam
        private final String identity;
        private String name;
        private List<Permissions> permissions;

        public RoleUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        public RoleUpdateRequest withPermissions(List<Permissions> permissions) {
            this.permissions = permissions;
            return this;
        }

        private RoleUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/roles/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "roles";
        }

        @Override
        protected Class<Role> getResponseClass() {
            return Role.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Permissions {
            private String access;
            private String resource;

            public Permissions withAccess(String access) {
                this.access = access;
                return this;
            }

            public Permissions withResource(String resource) {
                this.resource = resource;
                return this;
            }
        }
    }

    public static final class RoleDisableRequest extends PostRequest<Role> {
        @PathParam
        private final String identity;

        private RoleDisableRequest(HttpClient httpClient, String identity) {
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
            return "/roles/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "roles";
        }

        @Override
        protected Class<Role> getResponseClass() {
            return Role.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
