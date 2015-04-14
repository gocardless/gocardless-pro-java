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

        public UserCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        private String familyName;

        public UserCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        private String givenName;

        public UserCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        private Object links;

        public UserCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private String password;

        public UserCreateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        private String passwordConfirmation;

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
    }

    public static final class UserListRequest extends ListRequest<User> {
        private String after;

        public UserListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public UserListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public UserListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

        public UserListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String role;

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

        public UserUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        private String familyName;

        public UserUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        private String givenName;

        public UserUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        private Object links;

        public UserUpdateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private String password;

        public UserUpdateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        private String passwordConfirmation;

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
