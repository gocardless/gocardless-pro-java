package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Block;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with block resources.
 *
 * A block object is a simple rule, when matched, pushes a newly created mandate to a blocked state.
 * These details can be an exact match, like a bank account or an email, or a more generic match
 * such as an email domain. New block types may be added over time. Payments and subscriptions can't
 * be created against mandates in blocked state.
 * 
 * <p class="notice">
 * Client libraries have not yet been updated for this API but will be released soon. This API is
 * currently only available for approved integrators - please
 * <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class BlockService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#blocks() }.
     */
    public BlockService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new Block of a given type. By default it will be active.
     */
    public BlockCreateRequest create() {
        return new BlockCreateRequest(httpClient);
    }

    /**
     * Retrieves the details of an existing block.
     */
    public BlockGetRequest get(String identity) {
        return new BlockGetRequest(httpClient, identity);
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your blocks.
     */
    public BlockListRequest<ListResponse<Block>> list() {
        return new BlockListRequest<>(httpClient, ListRequest.<Block>pagingExecutor());
    }

    public BlockListRequest<Iterable<Block>> all() {
        return new BlockListRequest<>(httpClient, ListRequest.<Block>iteratingExecutor());
    }

    /**
     * Disables a block so that it no longer will prevent mandate creation.
     */
    public BlockDisableRequest disable(String identity) {
        return new BlockDisableRequest(httpClient, identity);
    }

    /**
     * Enables a previously disabled block so that it will prevent mandate creation
     */
    public BlockEnableRequest enable(String identity) {
        return new BlockEnableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link BlockService#create }.
     *
     * Creates a new Block of a given type. By default it will be active.
     */
    public static final class BlockCreateRequest extends IdempotentPostRequest<Block> {
        private Boolean active;
        private String blockType;
        private String reasonDescription;
        private String reasonType;
        private String resourceReference;

        /**
         * Shows if the block is active or disabled. Only active blocks will be used when deciding
         * if a mandate should be blocked.
         */
        public BlockCreateRequest withActive(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Type of entity we will seek to match against when blocking the mandate. This can
         * currently be one of 'email', 'email_domain', or 'bank_account'.
         */
        public BlockCreateRequest withBlockType(String blockType) {
            this.blockType = blockType;
            return this;
        }

        /**
         * This field is required if the reason_type is other. It should be a description of the
         * reason for why you wish to block this payer and why it does not align with the given
         * reason_types. This is intended to help us improve our knowledge of types of fraud.
         */
        public BlockCreateRequest withReasonDescription(String reasonDescription) {
            this.reasonDescription = reasonDescription;
            return this;
        }

        /**
         * The reason you wish to block this payer, can currently be one of 'identity_fraud',
         * 'no_intent_to_pay', 'unfair_chargeback'. If the reason isn't captured by one of the above
         * then 'other' can be selected but you must provide a reason description.
         */
        public BlockCreateRequest withReasonType(String reasonType) {
            this.reasonType = reasonType;
            return this;
        }

        /**
         * This field is a reference to the value you wish to block. This may be the raw value (in
         * the case of emails or email domains) or the ID of the resource (in the case of bank
         * accounts). This means in order to block a specific bank account it must already have been
         * created as a resource.
         */
        public BlockCreateRequest withResourceReference(String resourceReference) {
            this.resourceReference = resourceReference;
            return this;
        }

        public BlockCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<Block> handleConflict(HttpClient httpClient, String id) {
            BlockGetRequest request = new BlockGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private BlockCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BlockCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "blocks";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected Class<Block> getResponseClass() {
            return Block.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link BlockService#get }.
     *
     * Retrieves the details of an existing block.
     */
    public static final class BlockGetRequest extends GetRequest<Block> {
        @PathParam
        private final String identity;

        private BlockGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BlockGetRequest withHeader(String headerName, String headerValue) {
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
            return "blocks/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected Class<Block> getResponseClass() {
            return Block.class;
        }
    }

    /**
     * Request class for {@link BlockService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of your blocks.
     */
    public static final class BlockListRequest<S> extends ListRequest<S, Block> {
        private String block;
        private String blockType;
        private String createdAt;
        private String reasonType;
        private String updatedAt;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public BlockListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public BlockListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * ID of a [Block](#core-endpoints-blocks).
         */
        public BlockListRequest<S> withBlock(String block) {
            this.block = block;
            return this;
        }

        /**
         * Type of entity we will seek to match against when blocking the mandate. This can
         * currently be one of 'email', 'email_domain', or 'bank_account'.
         */
        public BlockListRequest<S> withBlockType(String blockType) {
            this.blockType = blockType;
            return this;
        }

        /**
         * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
         * created.
         */
        public BlockListRequest<S> withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Number of records to return.
         */
        public BlockListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * The reason you wish to block this payer, can currently be one of 'identity_fraud',
         * 'no_intent_to_pay', 'unfair_chargeback'. If the reason isn't captured by one of the above
         * then 'other' can be selected but you must provide a reason description.
         */
        public BlockListRequest<S> withReasonType(String reasonType) {
            this.reasonType = reasonType;
            return this;
        }

        /**
         * Fixed [timestamp](#api-usage-time-zones--dates), recording when this resource was
         * updated.
         */
        public BlockListRequest<S> withUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        private BlockListRequest(HttpClient httpClient, ListRequestExecutor<S, Block> executor) {
            super(httpClient, executor);
        }

        public BlockListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (block != null) {
                params.put("block", block);
            }
            if (blockType != null) {
                params.put("block_type", blockType);
            }
            if (createdAt != null) {
                params.put("created_at", createdAt);
            }
            if (reasonType != null) {
                params.put("reason_type", reasonType);
            }
            if (updatedAt != null) {
                params.put("updated_at", updatedAt);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "blocks";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected TypeToken<List<Block>> getTypeToken() {
            return new TypeToken<List<Block>>() {};
        }
    }

    /**
     * Request class for {@link BlockService#disable }.
     *
     * Disables a block so that it no longer will prevent mandate creation.
     */
    public static final class BlockDisableRequest extends PostRequest<Block> {
        @PathParam
        private final String identity;

        private BlockDisableRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BlockDisableRequest withHeader(String headerName, String headerValue) {
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
            return "blocks/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected Class<Block> getResponseClass() {
            return Block.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }

    /**
     * Request class for {@link BlockService#enable }.
     *
     * Enables a previously disabled block so that it will prevent mandate creation
     */
    public static final class BlockEnableRequest extends PostRequest<Block> {
        @PathParam
        private final String identity;

        private BlockEnableRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public BlockEnableRequest withHeader(String headerName, String headerValue) {
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
            return "blocks/:identity/actions/enable";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected Class<Block> getResponseClass() {
            return Block.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
