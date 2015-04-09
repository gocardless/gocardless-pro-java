package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.PublishableApiKey;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PublishableApiKeyRepository {
    private HttpClient httpClient;

    public PublishableApiKeyRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }



    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");

    }



    public PublishableApiKeyListRequest list() throws IOException {
        return new PublishableApiKeyListRequest(httpClient

        );

    }



    public PublishableApiKeyGetRequest get(String identity) throws IOException {
        return new PublishableApiKeyGetRequest(httpClient

        , identity

        );

    }



    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");

    }



    public void disable(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");

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

            TRUE,

            FALSE,

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



        private PublishableApiKeyListRequest(HttpClient httpClient

        ) {
            super(httpClient, "/publishable_api_keys", "publishable_api_keys",

            new TypeToken<List<PublishableApiKey>>() {}

            );


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



    public static final class PublishableApiKeyGetRequest extends GetRequest<PublishableApiKey> {

        private final String identity;



        private PublishableApiKeyGetRequest(HttpClient httpClient

        , String identity

        ) {
            super(httpClient, "/publishable_api_keys/:identity", "publishable_api_keys",

            PublishableApiKey.class

            );



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
