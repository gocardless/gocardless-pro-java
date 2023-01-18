package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.SchemeIdentifier;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with schemeentifier resources.
 *
 * 
 */
public class SchemeIdentifierService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#schemeIdentifiers() }.
     */
    public SchemeIdentifierService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your scheme identifiers.
     */
    public SchemeIdentifierListRequest<ListResponse<SchemeIdentifier>> list() {
        return new SchemeIdentifierListRequest<>(httpClient,
                ListRequest.<SchemeIdentifier>pagingExecutor());
    }

    public SchemeIdentifierListRequest<Iterable<SchemeIdentifier>> all() {
        return new SchemeIdentifierListRequest<>(httpClient,
                ListRequest.<SchemeIdentifier>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing scheme identifier.
     */
    public SchemeIdentifierGetRequest get(String identity) {
        return new SchemeIdentifierGetRequest(httpClient, identity);
    }

    /**
     * Request class for {@link SchemeIdentifierService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your scheme identifiers.
     */
    public static final class SchemeIdentifierListRequest<S>
            extends ListRequest<S, SchemeIdentifier> {
        /**
         * Cursor pointing to the start of the desired set.
         */
        public SchemeIdentifierListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public SchemeIdentifierListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public SchemeIdentifierListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private SchemeIdentifierListRequest(HttpClient httpClient,
                ListRequestExecutor<S, SchemeIdentifier> executor) {
            super(httpClient, executor);
        }

        public SchemeIdentifierListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "scheme_identifiers";
        }

        @Override
        protected String getEnvelope() {
            return "scheme_identifiers";
        }

        @Override
        protected TypeToken<List<SchemeIdentifier>> getTypeToken() {
            return new TypeToken<List<SchemeIdentifier>>() {};
        }
    }

    /**
     * Request class for {@link SchemeIdentifierService#get }.
     *
     * Retrieves the details of an existing scheme identifier.
     */
    public static final class SchemeIdentifierGetRequest extends GetRequest<SchemeIdentifier> {
        @PathParam
        private final String identity;

        private SchemeIdentifierGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public SchemeIdentifierGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "scheme_identifiers/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "scheme_identifiers";
        }

        @Override
        protected Class<SchemeIdentifier> getResponseClass() {
            return SchemeIdentifier.class;
        }
    }
}
