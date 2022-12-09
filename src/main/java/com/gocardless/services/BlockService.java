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
 * Blocks are created to prevent certain customer details from being used when creating mandates.
 * 
 * The details used to create blocks can be exact matches, like a bank account or an email, or a
 * more generic match such as an email domain or bank name. Please be careful when creating blocks
 * for more generic matches as this may block legitimate payers from using your service.
 * 
 * New block types may be added over time.
 * 
 * A block is in essence a simple rule that is used to match against details in a newly created
 * mandate. If there is a successful match then the mandate is transitioned to a "blocked" state.
 * 
 * Please note:
 * 
 * - Payments and subscriptions cannot be created against a mandate in blocked state. - A mandate
 * can never be transitioned out of the blocked state.
 * 
 * The one exception to this is when blocking a 'bank_name'. This block will prevent bank accounts
 * from being created for banks that match the given name. To ensure we match bank names correctly
 * an existing bank account must be used when creating this block. Please be aware that we cannot
 * always match a bank account to a given bank name.
 * 
 * <p class="notice">
 * This API is currently only available for GoCardless Protect+ integrators - please
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
     * Creates new blocks for a given reference. By default blocks will be active. Returns 201 if at
     * least one block was created. Returns 200 if there were no new blocks created.
     */
    public BlockBlockByRefRequest<Iterable<Block>> blockByRef() {
        return new BlockBlockByRefRequest<>(httpClient, ListRequest.<Block>iteratingExecutor());
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
         * currently be one of 'email', 'email_domain', 'bank_account', or 'bank_name'.
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
         * accounts and bank names). This means in order to block a specific bank account (even if
         * you wish to block generically by name) it must already have been created as a resource.
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
         * currently be one of 'email', 'email_domain', 'bank_account', or 'bank_name'.
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

    /**
     * Request class for {@link BlockService#blockByRef }.
     *
     * Creates new blocks for a given reference. By default blocks will be active. Returns 201 if at
     * least one block was created. Returns 200 if there were no new blocks created.
     */
    public static final class BlockBlockByRefRequest<S> extends ListRequest<S, Block> {
        private Boolean active;
        private String reasonDescription;
        private String reasonType;
        private String referenceType;
        private String referenceValue;

        /**
         * Shows if the block is active or disabled. Only active blocks will be used when deciding
         * if a mandate should be blocked.
         */
        public BlockBlockByRefRequest<S> withActive(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * This field is required if the reason_type is other. It should be a description of the
         * reason for why you wish to block this payer and why it does not align with the given
         * reason_types. This is intended to help us improve our knowledge of types of fraud.
         */
        public BlockBlockByRefRequest<S> withReasonDescription(String reasonDescription) {
            this.reasonDescription = reasonDescription;
            return this;
        }

        /**
         * The reason you wish to block this payer, can currently be one of 'identity_fraud',
         * 'no_intent_to_pay', 'unfair_chargeback'. If the reason isn't captured by one of the above
         * then 'other' can be selected but you must provide a reason description.
         */
        public BlockBlockByRefRequest<S> withReasonType(String reasonType) {
            this.reasonType = reasonType;
            return this;
        }

        /**
         * Type of entity we will seek to get the associated emails and bank accounts to create
         * blocks from. This can currently be one of 'customer' or 'mandate'.
         */
        public BlockBlockByRefRequest<S> withReferenceType(String referenceType) {
            this.referenceType = referenceType;
            return this;
        }

        /**
         * This field is a reference to the entity you wish to block based on its emails and bank
         * accounts. This may be the ID of a customer or a mandate. This means in order to block by
         * reference the entity must have already been created as a resource.
         */
        public BlockBlockByRefRequest<S> withReferenceValue(String referenceValue) {
            this.referenceValue = referenceValue;
            return this;
        }

        private BlockBlockByRefRequest(HttpClient httpClient,
                ListRequestExecutor<S, Block> executor) {
            super(httpClient, executor, "POST");
        }

        public BlockBlockByRefRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "block_by_ref";
        }

        @Override
        protected String getEnvelope() {
            return "blocks";
        }

        @Override
        protected TypeToken<List<Block>> getTypeToken() {
            return new TypeToken<List<Block>>() {};
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
