package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.SchemeIdentifier;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with schemeentifier resources.
 *
 * This represents a scheme identifier (e.g. a SUN in Bacs or a CID in SEPA). Scheme identifiers are
 * used to specify the beneficiary name that appears on customers' bank statements.
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
     * Creates a new scheme identifier. The scheme identifier must be [applied to a
     * creditor](#creditors-apply-a-scheme-identifier) before payments are taken using it. The
     * scheme identifier must also have the `status` of active before it can be used. On Bacs, this
     * will take 5 working days. On other schemes, this happens instantly.
     * 
     * #### Scheme identifier name validations
     * 
     * The `name` field of a scheme identifier can contain alphanumeric characters, spaces and
     * special characters.
     * 
     * Its maximum length and the special characters it supports depend on the scheme:
     * 
     * | __scheme__ | __maximum length__ | __special characters allowed__ | | :---------------- |
     * :----------------- | :-------------------------------------------------- | | `bacs` | 18
     * characters | `/` `.` `&` `-` | | `sepa_core` | 70 characters | `/` `?` `:` `(` `)` `.` `,`
     * `+` `&` `<` `>` `'` `"` | | `ach` | 16 characters | `/` `?` `:` `(` `)` `.` `,` `'` `+` `-` |
     * | `faster_payments` | 18 characters | `/` `?` `:` `(` `)` `.` `,` `'` `+` `-` |
     * 
     * The validation error that gets returned for an invalid name will contain a suggested name in
     * the metadata that is guaranteed to pass name validations.
     * 
     * You should ensure that the name you set matches the legal name or the trading name of the
     * creditor, otherwise, there is an increased risk of chargeback.
     * 
     */
    public SchemeIdentifierCreateRequest create() {
        return new SchemeIdentifierCreateRequest(httpClient);
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
     * Request class for {@link SchemeIdentifierService#create }.
     *
     * Creates a new scheme identifier. The scheme identifier must be [applied to a
     * creditor](#creditors-apply-a-scheme-identifier) before payments are taken using it. The
     * scheme identifier must also have the `status` of active before it can be used. On Bacs, this
     * will take 5 working days. On other schemes, this happens instantly.
     * 
     * #### Scheme identifier name validations
     * 
     * The `name` field of a scheme identifier can contain alphanumeric characters, spaces and
     * special characters.
     * 
     * Its maximum length and the special characters it supports depend on the scheme:
     * 
     * | __scheme__ | __maximum length__ | __special characters allowed__ | | :---------------- |
     * :----------------- | :-------------------------------------------------- | | `bacs` | 18
     * characters | `/` `.` `&` `-` | | `sepa_core` | 70 characters | `/` `?` `:` `(` `)` `.` `,`
     * `+` `&` `<` `>` `'` `"` | | `ach` | 16 characters | `/` `?` `:` `(` `)` `.` `,` `'` `+` `-` |
     * | `faster_payments` | 18 characters | `/` `?` `:` `(` `)` `.` `,` `'` `+` `-` |
     * 
     * The validation error that gets returned for an invalid name will contain a suggested name in
     * the metadata that is guaranteed to pass name validations.
     * 
     * You should ensure that the name you set matches the legal name or the trading name of the
     * creditor, otherwise, there is an increased risk of chargeback.
     * 
     */
    public static final class SchemeIdentifierCreateRequest
            extends IdempotentPostRequest<SchemeIdentifier> {
        private String name;
        private Scheme scheme;

        /**
         * The name which appears on customers' bank statements. This should usually be the
         * merchant's trading name.
         */
        public SchemeIdentifierCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The scheme which this scheme identifier applies to.
         */
        public SchemeIdentifierCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        public SchemeIdentifierCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<SchemeIdentifier> handleConflict(HttpClient httpClient, String id) {
            SchemeIdentifierGetRequest request = new SchemeIdentifierGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private SchemeIdentifierCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public SchemeIdentifierCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
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
        protected Class<SchemeIdentifier> getResponseClass() {
            return SchemeIdentifier.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Scheme {
            @SerializedName("ach")
            ACH, @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("becs")
            BECS, @SerializedName("becs_nz")
            BECS_NZ, @SerializedName("betalingsservice")
            BETALINGSSERVICE, @SerializedName("faster_payments")
            FASTER_PAYMENTS, @SerializedName("pad")
            PAD, @SerializedName("pay_to")
            PAY_TO, @SerializedName("sepa")
            SEPA, @SerializedName("sepa_credit_transfer")
            SEPA_CREDIT_TRANSFER, @SerializedName("sepa_instant_credit_transfer")
            SEPA_INSTANT_CREDIT_TRANSFER, @SerializedName("unknown")
            UNKNOWN;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
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
