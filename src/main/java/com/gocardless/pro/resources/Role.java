package com.gocardless.pro.resources;

import java.util.List;

/**
 * Represents a Role resource returned from the API.
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
public class Role {
    private Role() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private Boolean enabled;
    private String id;
    private String name;
    private List<Permissions> permissions;

    /**
     * Fixed [timestamp](https://developer.gocardless.com/pro/#overview-time-zones-dates), recording when
     * this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Boolean value showing whether the role is enabled or disabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Unique identifier, beginning with "RO"
     */
    public String getId() {
        return id;
    }

    /**
     * Human-readable name for the role.
     */
    public String getName() {
        return name;
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
    public List<Permissions> getPermissions() {
        return permissions;
    }

    public static class Permissions {
        private Permissions() {
            // blank to prevent instantiation
        }

        private String access;
        private String resource;

        public String getAccess() {
            return access;
        }

        public String getResource() {
            return resource;
        }
    }
}
