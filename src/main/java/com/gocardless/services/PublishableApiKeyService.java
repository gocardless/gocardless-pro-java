package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.PublishableApiKey;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with publishable api key resources.
 *
 * Publishable API keys are designed to be used by the [js
 * flow](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account-tokens). You
 * should generate a key and then use it to make requests to the API. They do not expire, but can be
 * disabled.
 * 
 * Publishable API keys only have permissions to create [customer bank account
 * tokens](https://developer.gocardless.com/pro/#api-endpoints-customer-bank-account-tokens).
 */
public class PublishableApiKeyService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#publishableApiKeys() }.
     */
    public PublishableApiKeyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a publishable API key object.
     */
    public PublishableApiKeyCreateRequest create() {
        return new PublishableApiKeyCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your publishable API keys
     */
    public PublishableApiKeyListRequest<ListResponse<PublishableApiKey>> list() {
        return new PublishableApiKeyListRequest<>(httpClient,
                ListRequest.<PublishableApiKey>pagingExecutor());
    }

    public PublishableApiKeyListRequest<Iterable<PublishableApiKey>> all() {
        return new PublishableApiKeyListRequest<>(httpClient,
                ListRequest.<PublishableApiKey>iteratingExecutor());
    }

    /**
     * Returns all details about a single publishable API key
     */
    public PublishableApiKeyGetRequest get(String identity) {
        return new PublishableApiKeyGetRequest(httpClient, identity);
    }

    /**
     * Updates a publishable API key. Only the `name` fields are supported. Any other fields passed will
     * be ignored.
     */
    public PublishableApiKeyUpdateRequest update(String identity) {
        return new PublishableApiKeyUpdateRequest(httpClient, identity);
    }

    /**
     * Disables a publishable API key. Once disabled, the publishable API key will not be usable to
     * authenticate any requests.
     */
    public PublishableApiKeyDisableRequest disable(String identity) {
        return new PublishableApiKeyDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link PublishableApiKeyService#create }.
     *
     * Creates a publishable API key object.
     */
    public static final class PublishableApiKeyCreateRequest extends PostRequest<PublishableApiKey> {
        private String name;

        /**
         * Human readable name for the key. This should not exceed 75 characters.
         */
        public PublishableApiKeyCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private PublishableApiKeyCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/publishable_api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "publishable_api_keys";
        }

        @Override
        protected Class<PublishableApiKey> getResponseClass() {
            return PublishableApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link PublishableApiKeyService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your publishable API keys
     */
    public static final class PublishableApiKeyListRequest<S> extends
            ListRequest<S, PublishableApiKey> {
        private Enabled enabled;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public PublishableApiKeyListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public PublishableApiKeyListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Get enabled or disabled publishable API keys.
         */
        public PublishableApiKeyListRequest<S> withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public PublishableApiKeyListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private PublishableApiKeyListRequest(HttpClient httpClient,
                ListRequestExecutor<S, PublishableApiKey> executor) {
            super(httpClient, executor);
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
            return "/publishable_api_keys";
        }

        @Override
        protected String getEnvelope() {
            return "publishable_api_keys";
        }

        @Override
        protected TypeToken<List<PublishableApiKey>> getTypeToken() {
            return new TypeToken<List<PublishableApiKey>>() {};
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
     * Request class for {@link PublishableApiKeyService#get }.
     *
     * Returns all details about a single publishable API key
     */
    public static final class PublishableApiKeyGetRequest extends GetRequest<PublishableApiKey> {
        @PathParam
        private final String identity;

        private PublishableApiKeyGetRequest(HttpClient httpClient, String identity) {
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
            return "/publishable_api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "publishable_api_keys";
        }

        @Override
        protected Class<PublishableApiKey> getResponseClass() {
            return PublishableApiKey.class;
        }
    }

    /**
     * Request class for {@link PublishableApiKeyService#update }.
     *
     * Updates a publishable API key. Only the `name` fields are supported. Any other fields passed will
     * be ignored.
     */
    public static final class PublishableApiKeyUpdateRequest extends PutRequest<PublishableApiKey> {
        @PathParam
        private final String identity;
        private String name;

        /**
         * Human readable name for the key. This should not exceed 75 characters.
         */
        public PublishableApiKeyUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        private PublishableApiKeyUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/publishable_api_keys/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "publishable_api_keys";
        }

        @Override
        protected Class<PublishableApiKey> getResponseClass() {
            return PublishableApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link PublishableApiKeyService#disable }.
     *
     * Disables a publishable API key. Once disabled, the publishable API key will not be usable to
     * authenticate any requests.
     */
    public static final class PublishableApiKeyDisableRequest extends
            PostRequest<PublishableApiKey> {
        @PathParam
        private final String identity;

        private PublishableApiKeyDisableRequest(HttpClient httpClient, String identity) {
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
            return "/publishable_api_keys/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "publishable_api_keys";
        }

        @Override
        protected Class<PublishableApiKey> getResponseClass() {
            return PublishableApiKey.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
