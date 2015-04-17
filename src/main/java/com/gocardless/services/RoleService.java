package com.gocardless.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Role;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with role resources.
 *
 * <a name="insufficient_permissions"></a>Roles represent a set of permissions that may be granted to
 * a user. The permissions are specified at the resource-type level, and can be `full_access` or
 * `read_only`. If a resource-type is not included that role's users will have no access to resources
 * of that type, and will receive an `insufficient_permissions` error when trying to use those
 * endpoints.
 * 
 * A role's `permissions` attribute is used to set/show the permissions for a role
 * and it's key/value pairs are restricted to the below:
 * 
 * <dl>
 *  
 * <dt><p><code>resource</code></p></dt>
 *   <dd><p>One of:</p>
 *     <ul>
 *      
 * <li><code>customers</code></li>
 *       <li><code>customer_bank_accounts</code></li>
 *      
 * <li><code>mandates</code></li>
 *       <li><code>payments</code></li>
 *      
 * <li><code>payouts</code></li>
 *       <li><code>creditors</code></li>
 *      
 * <li><code>creditor_bank_accounts</code></li>
 *       <li><code>roles</code></li>
 *      
 * <li><code>users</code></li>
 *       <li><code>events</code></li>
 *      
 * <li><code>api_keys</code></li>
 *       <li><code>subscriptions</code></li>
 *      
 * <li><code>redirect_flows</code></li>
 *     </ul>
 *   </dd>
 * </dl>
 * 
 * <dl>
 *  
 * <dt><p><code>access</code></p></dt>
 *   <dd><p>One of:</p>
 *     <ul>
 *      
 * <li><code>full_access</code>: read and write all records of this type</li>
 *      
 * <li><code>read_only</code>: list and show endpoints available, but not create, update, delete, or
 * actions</li>
 *     </ul>
 *   </dd>
 * </dl>
 * 
 */
public class RoleService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#roles() }.
     */
    public RoleService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Create a role with set access permissions
     */
    public RoleCreateRequest create() {
        return new RoleCreateRequest(httpClient);
    }

    /**
     * List all existing roles
     */
    public RoleListRequest list() {
        return new RoleListRequest(httpClient);
    }

    /**
     * Retrieve all details for a single role
     */
    public RoleGetRequest get(String identity) {
        return new RoleGetRequest(httpClient, identity);
    }

    /**
     * Updates a role object. Supports all of the fields supported when creating a role.
     */
    public RoleUpdateRequest update(String identity) {
        return new RoleUpdateRequest(httpClient, identity);
    }

    /**
     * Disables a role
     */
    public RoleDisableRequest disable(String identity) {
        return new RoleDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link RoleService#create }.
     *
     * Create a role with set access permissions
     */
    public static final class RoleCreateRequest extends PostRequest<Role> {
        private String name;
        private List<Permissions> permissions;

        /**
         * Human-readable name for the role.
         */
        public RoleCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * An array of key-value pairs that define the permissions the role has. Each element of the array
         * contains the following keys:
         * <ul>
         * <li>`resource`: the relevant, pluralised API resource, e.g.
         * `customers`</li>
         * <li>`access`: the access level. One of `full_access` or `read_only`</li>
         *
         * </ul>
         */
        public RoleCreateRequest withPermissions(List<Permissions> permissions) {
            this.permissions = permissions;
            return this;
        }

        /**
         * An array of key-value pairs that define the permissions the role has. Each element of the array
         * contains the following keys:
         * <ul>
         * <li>`resource`: the relevant, pluralised API resource, e.g.
         * `customers`</li>
         * <li>`access`: the access level. One of `full_access` or `read_only`</li>
         *
         * </ul>
         */
        public RoleCreateRequest withPermissions(Permissions permissions) {
            if (this.permissions == null) {
                this.permissions = new ArrayList<>();
            }
            this.permissions.add(permissions);
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
            private Access access;
            private String resource;

            public Permissions withAccess(Access access) {
                this.access = access;
                return this;
            }

            public Permissions withResource(String resource) {
                this.resource = resource;
                return this;
            }

            public enum Access {
                READ_ONLY, FULL_ACCESS;
                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }
    }

    /**
     * Request class for {@link RoleService#list }.
     *
     * List all existing roles
     */
    public static final class RoleListRequest extends ListRequest<Role> {
        private Enabled enabled;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public RoleListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public RoleListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Get enabled or disabled roles.
         */
        public RoleListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public RoleListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private RoleListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (enabled != null) {
                params.put("enabled", enabled);
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

    /**
     * Request class for {@link RoleService#get }.
     *
     * Retrieve all details for a single role
     */
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

    /**
     * Request class for {@link RoleService#update }.
     *
     * Updates a role object. Supports all of the fields supported when creating a role.
     */
    public static final class RoleUpdateRequest extends PutRequest<Role> {
        @PathParam
        private final String identity;
        private String name;
        private List<Permissions> permissions;

        /**
         * Human-readable name for the role.
         */
        public RoleUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * An array of key-value pairs that define the permissions the role has. Each element of the array
         * contains the following keys:
         * <ul>
         * <li>`resource`: the relevant, pluralised API resource, e.g.
         * `customers`</li>
         * <li>`access`: the access level. One of `full_access` or `read_only`</li>
         *
         * </ul>
         */
        public RoleUpdateRequest withPermissions(List<Permissions> permissions) {
            this.permissions = permissions;
            return this;
        }

        /**
         * An array of key-value pairs that define the permissions the role has. Each element of the array
         * contains the following keys:
         * <ul>
         * <li>`resource`: the relevant, pluralised API resource, e.g.
         * `customers`</li>
         * <li>`access`: the access level. One of `full_access` or `read_only`</li>
         *
         * </ul>
         */
        public RoleUpdateRequest withPermissions(Permissions permissions) {
            if (this.permissions == null) {
                this.permissions = new ArrayList<>();
            }
            this.permissions.add(permissions);
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
            private Access access;
            private String resource;

            public Permissions withAccess(Access access) {
                this.access = access;
                return this;
            }

            public Permissions withResource(String resource) {
                this.resource = resource;
                return this;
            }

            public enum Access {
                READ_ONLY, FULL_ACCESS;
                @Override
                public String toString() {
                    return name().toLowerCase();
                }
            }
        }
    }

    /**
     * Request class for {@link RoleService#disable }.
     *
     * Disables a role
     */
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
