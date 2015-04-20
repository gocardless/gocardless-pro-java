package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.User;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with user resources.
 *
 * 
 */
public class UserService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#users() }.
     */
    public UserService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * <a name="user_exists"></a>Creates a new user object. Email addresses must be unique.
     */
    public UserCreateRequest create() {
        return new UserCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your users.
     */
    public UserListRequest list() {
        return new UserListRequest(httpClient);
    }

    /**
     * Retrieves the details of an existing user. In addition to the usual permissions based access
     * rules, any user can access their own record.
     */
    public UserGetRequest get(String identity) {
        return new UserGetRequest(httpClient, identity);
    }

    /**
     * Updates a user object. Supports all of the fields supported when creating a user.
     */
    public UserUpdateRequest update(String identity) {
        return new UserUpdateRequest(httpClient, identity);
    }

    /**
     * Enables a user
     */
    public UserEnableRequest enable(String identity) {
        return new UserEnableRequest(httpClient, identity);
    }

    /**
     * Disables a user
     */
    public UserDisableRequest disable(String identity) {
        return new UserDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link UserService#create }.
     *
     * <a name="user_exists"></a>Creates a new user object. Email addresses must be unique.
     */
    public static final class UserCreateRequest extends PostRequest<User> {
        private String email;
        private String familyName;
        private String givenName;
        private Links links;
        private String password;
        private String passwordConfirmation;

        /**
         * Unique email address, used as a username.
         */
        public UserCreateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * User's surname. This field may not exceed 100 characters.
         */
        public UserCreateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * User's given name. This field may not exceed 100 characters.
         */
        public UserCreateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public UserCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the user's [role](https://developer.gocardless.com/pro/#api-endpoints-roles), which dictates
         * what resources the user can see.
         */
        public UserCreateRequest withLinksRole(String role) {
            if (links == null) {
                links = new Links();
            }
            links.withRole(role);
            return this;
        }

        /**
         * This field must be at least 12 characters.
         */
        public UserCreateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * If supplied, this must match `password`.
         */
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

            /**
             * ID of the user's [role](https://developer.gocardless.com/pro/#api-endpoints-roles), which dictates
             * what resources the user can see.
             */
            public Links withRole(String role) {
                this.role = role;
                return this;
            }
        }
    }

    /**
     * Request class for {@link UserService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your users.
     */
    public static final class UserListRequest extends ListRequest<User> {
        private Enabled enabled;
        private String role;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public UserListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public UserListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Get enabled or disabled users.
         */
        public UserListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public UserListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "RO"
         */
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
            params.putAll(super.getQueryParams());
            if (enabled != null) {
                params.put("enabled", enabled);
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
            @SerializedName("true")
            TRUE, @SerializedName("false")
            FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link UserService#get }.
     *
     * Retrieves the details of an existing user. In addition to the usual permissions based access
     * rules, any user can access their own record.
     */
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

    /**
     * Request class for {@link UserService#update }.
     *
     * Updates a user object. Supports all of the fields supported when creating a user.
     */
    public static final class UserUpdateRequest extends PutRequest<User> {
        @PathParam
        private final String identity;
        private String email;
        private String familyName;
        private String givenName;
        private Links links;
        private String password;
        private String passwordConfirmation;

        /**
         * Unique email address, used as a username.
         */
        public UserUpdateRequest withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * User's surname. This field may not exceed 100 characters.
         */
        public UserUpdateRequest withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        /**
         * User's given name. This field may not exceed 100 characters.
         */
        public UserUpdateRequest withGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public UserUpdateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the user's [role](https://developer.gocardless.com/pro/#api-endpoints-roles), which dictates
         * what resources the user can see.
         */
        public UserUpdateRequest withLinksRole(String role) {
            if (links == null) {
                links = new Links();
            }
            links.withRole(role);
            return this;
        }

        /**
         * This field must be at least 12 characters.
         */
        public UserUpdateRequest withPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * If supplied, this must match `password`.
         */
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

            /**
             * ID of the user's [role](https://developer.gocardless.com/pro/#api-endpoints-roles), which dictates
             * what resources the user can see.
             */
            public Links withRole(String role) {
                this.role = role;
                return this;
            }
        }
    }

    /**
     * Request class for {@link UserService#enable }.
     *
     * Enables a user
     */
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

    /**
     * Request class for {@link UserService#disable }.
     *
     * Disables a user
     */
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
