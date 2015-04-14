package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.User;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

public class UserService {
    private HttpClient httpClient;

    public UserService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public UserCreateRequest create() {
        return new UserCreateRequest(httpClient);
    }

    public UserListRequest list() {
        return new UserListRequest(httpClient);
    }

    public UserGetRequest get(String identity) {
        return new UserGetRequest(httpClient, identity);
    }

    public UserUpdateRequest update(String identity) {
        return new UserUpdateRequest(httpClient, identity);
    }

    public UserEnableRequest enable(String identity) {
        return new UserEnableRequest(httpClient, identity);
    }

    public UserDisableRequest disable(String identity) {
        return new UserDisableRequest(httpClient, identity);
    }

    public static final class UserCreateRequest extends PostRequest<User> {
        private String email;
        private String familyName;
        private String givenName;
        private Links links;
        private String password;
        private String passwordConfirmation;

        public UserCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public UserCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public UserCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public UserCreateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserCreateRequest withPasswordConfirmation(String passwordConfirmation) {
            this.passwordConfirmation = passwordConfirmation;
            return this;
        }

        private UserCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/users";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected Class<User> getResponseClass() {
            return User.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String role;

            public Links withRole(String role) {
                this.role = role;
                return this;
            }
        }
    }

    public static final class UserListRequest extends ListRequest<User> {
        private String after;
        private String before;
        private Enabled enabled;
        private Integer limit;
        private String role;

        public UserListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        public UserListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public UserListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public UserListRequest withRole(String role) {
            this.role = role;
            return this;
        }

        private UserListRequest(HttpClient httpClient) {
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
            if (role != null) {
                params.put("role", role);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/users";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected TypeToken<List<User>> getTypeToken() {
            return new TypeToken<List<User>>() {};
        }

        public enum Enabled {
            TRUE, FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    public static final class UserGetRequest extends GetRequest<User> {
        @PathParam
        private final String identity;

        private UserGetRequest(HttpClient httpClient, String identity) {
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
            return "/users/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected Class<User> getResponseClass() {
            return User.class;
        }
    }

    public static final class UserUpdateRequest extends PutRequest<User> {
        @PathParam
        private final String identity;
        private String email;
        private String familyName;
        private String givenName;
        private Links links;
        private String password;
        private String passwordConfirmation;

        public UserUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public UserUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public UserUpdateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        public UserUpdateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserUpdateRequest withPasswordConfirmation(String passwordConfirmation) {
            this.passwordConfirmation = passwordConfirmation;
            return this;
        }

        private UserUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/users/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected Class<User> getResponseClass() {
            return User.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String role;

            public Links withRole(String role) {
                this.role = role;
                return this;
            }
        }
    }

    public static final class UserEnableRequest extends PostRequest<User> {
        @PathParam
        private final String identity;

        private UserEnableRequest(HttpClient httpClient, String identity) {
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
            return "/users/:identity/actions/enable";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected Class<User> getResponseClass() {
            return User.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    public static final class UserDisableRequest extends PostRequest<User> {
        @PathParam
        private final String identity;

        private UserDisableRequest(HttpClient httpClient, String identity) {
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
            return "/users/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "users";
        }

        @Override
        protected Class<User> getResponseClass() {
            return User.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
