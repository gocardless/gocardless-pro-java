package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.ApiKey;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with API Key resources.
 *
 * <a name="api_key_not_active"></a>API keys are designed to be used by any integrations you build.
 * You should generate a key and then use it to make requests to the API and set the webhook URL for
 * that integration. They do not expire, but can be disabled.
 */
public class ApiKeyService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.pro.GoCardlessClient#apiKeys() }.
     */
    public ApiKeyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new API key.
     */
    public ApiKeyCreateRequest create() {
        return new ApiKeyCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your API keys.
     */
    public ApiKeyListRequest list() {
        return new ApiKeyListRequest(httpClient);
    }

    /**
     * Retrieves the details of an existing API key.
     */
    public ApiKeyGetRequest get(String identity) {
        return new ApiKeyGetRequest(httpClient, identity);
    }

    /**
     * Updates an API key. Only the `name` and `webhook_url` fields are supported.
     */
    public ApiKeyUpdateRequest update(String identity) {
        return new ApiKeyUpdateRequest(httpClient, identity);
    }

    /**
     * Disables an API key. Once disabled, the API key will not be usable to authenticate any requests,
     * and its `webhook_url` will not receive any more events.
     */
    public ApiKeyDisableRequest disable(String identity) {
        return new ApiKeyDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link ApiKeyService#create }.
     *
     * Creates a new API key.
     */
    public static final class ApiKeyCreateRequest extends PostRequest<ApiKey> {
        private Links links;
        private String name;
        private String webhookUrl;

        public ApiKeyCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * Human readable name for the key. This field cannot exceed 75 characters.
         */
        public ApiKeyCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Optional https url, to which all webhooks will be sent. Note that if this is set on multiple API
         * keys, each event will be sent to each `webhook_url`; for example, if you have two keys with
         * `webhook_url` set to `https://example.com/webhooks`, then we will send two requests to that url
         * for each event that occurs.
         */
        public ApiKeyCreateRequest withWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
            return this;
        }

        private ApiKeyCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String role;

            /**
             * Unique identifier, beginning with "RO"
             */
            public Links withRole(String role) {
                this.role = role;
                return this;
            }
        }
    }

    /**
     * Request class for {@link ApiKeyService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your API keys.
     */
    public static final class ApiKeyListRequest extends ListRequest<ApiKey> {
        private String after;
        private String before;
        private Enabled enabled;
        private Integer limit;
        private String role;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public ApiKeyListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public ApiKeyListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        /**
         * Get enabled or disabled API keys.
         */
        public ApiKeyListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public ApiKeyListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Unique identifier, beginning with "RO"
         */
        public ApiKeyListRequest withRole(String role) {
            this.role = role;
            return this;
        }

        private ApiKeyListRequest(HttpClient httpClient) {
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
            return "/api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected TypeToken<List<ApiKey>> getTypeToken() {
            return new TypeToken<List<ApiKey>>() {};
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
     * Request class for {@link ApiKeyService#get }.
     *
     * Retrieves the details of an existing API key.
     */
    public static final class ApiKeyGetRequest extends GetRequest<ApiKey> {
        @PathParam
        private final String identity;

        private ApiKeyGetRequest(HttpClient httpClient, String identity) {
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
            return "/api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }
    }

    /**
     * Request class for {@link ApiKeyService#update }.
     *
     * Updates an API key. Only the `name` and `webhook_url` fields are supported.
     */
    public static final class ApiKeyUpdateRequest extends PutRequest<ApiKey> {
        @PathParam
        private final String identity;
        private String name;
        private String webhookUrl;

        /**
         * Human readable name for the key. This field cannot exceed 75 characters.
         */
        public ApiKeyUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Optional https url, to which all webhooks will be sent. Note that if this is set on multiple API
         * keys, each event will be sent to each `webhook_url`; for example, if you have two keys with
         * `webhook_url` set to `https://example.com/webhooks`, then we will send two requests to that url
         * for each event that occurs.
         */
        public ApiKeyUpdateRequest withWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
            return this;
        }

        private ApiKeyUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link ApiKeyService#disable }.
     *
     * Disables an API key. Once disabled, the API key will not be usable to authenticate any requests,
     * and its `webhook_url` will not receive any more events.
     */
    public static final class ApiKeyDisableRequest extends PostRequest<ApiKey> {
        @PathParam
        private final String identity;

        private ApiKeyDisableRequest(HttpClient httpClient, String identity) {
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
            return "/api_keys/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "api_keys";
        }

        @Override
        protected Class<ApiKey> getResponseClass() {
            return ApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
