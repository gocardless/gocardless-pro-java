package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.PublishableApiKey;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PublishableApiKeyService {
    private HttpClient httpClient;

    public PublishableApiKeyService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public PublishableApiKeyCreateRequest create() throws IOException {
        return new PublishableApiKeyCreateRequest(httpClient);
    }

    public PublishableApiKeyListRequest list() throws IOException {
        return new PublishableApiKeyListRequest(httpClient);
    }

    public PublishableApiKeyGetRequest get(String identity) throws IOException {
        return new PublishableApiKeyGetRequest(httpClient, identity);
    }

    public PublishableApiKeyUpdateRequest update(String identity) throws IOException {
        return new PublishableApiKeyUpdateRequest(httpClient, identity);
    }

    public PublishableApiKeyDisableRequest disable(String identity) throws IOException {
        return new PublishableApiKeyDisableRequest(httpClient, identity);
    }

    public static final class PublishableApiKeyCreateRequest extends PostRequest<PublishableApiKey> {
        private String name;

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

    public static final class PublishableApiKeyListRequest extends ListRequest<PublishableApiKey> {
        private String after;

        public PublishableApiKeyListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public PublishableApiKeyListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        public enum Enabled {
            TRUE, FALSE,
        }

        private Enabled enabled;

        public PublishableApiKeyListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        private Integer limit;

        public PublishableApiKeyListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private PublishableApiKeyListRequest(HttpClient httpClient) {
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
    }

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

    public static final class PublishableApiKeyUpdateRequest extends PutRequest<PublishableApiKey> {
        @PathParam
        private final String identity;
        private String name;

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
