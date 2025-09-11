package com.gocardless.http;

import com.gocardless.errors.ApiError;
import com.gocardless.errors.InvalidStateException;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import java.util.Map;
import java.util.UUID;

public abstract class IdempotentPostRequest<T> extends PostRequest<T> {
    private static final Predicate<ApiError> CONFLICT_ERROR = new Predicate<ApiError>() {
        @Override
        public boolean apply(ApiError error) {
            return error.getReason().equals("idempotent_creation_conflict");
        }
    };

    private transient String idempotencyKey;

    protected IdempotentPostRequest(HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Executes this request.
     *
     * Returns the response entity.
     *
     * @throws com.gocardless.GoCardlessException
     */
    @Override
    public T execute() {
        try {
            return getHttpClient().executeWithRetries(this);
        } catch (InvalidStateException e) {
            Optional<ApiError> conflictError = Iterables.tryFind(e.getErrors(), CONFLICT_ERROR);
            if (conflictError.isPresent() && !getHttpClient().isErrorOnIdempotencyConflict()) {
                String id = conflictError.get().getLinks().get("conflicting_resource_id");
                return handleConflict(getHttpClient(), id).execute();
            } else {
                throw Throwables.propagate(e);
            }
        }
    }

    protected void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    @Override
    protected final ImmutableMap<String, String> getHeaders() {
        String requestIdempotencyKey;
        if (this.idempotencyKey == null) {
            requestIdempotencyKey = UUID.randomUUID().toString();
            this.setIdempotencyKey(requestIdempotencyKey);
        } else {
            requestIdempotencyKey = this.idempotencyKey;
        }

        return ImmutableMap.<String, String>builder()
            .put("Idempotency-Key", requestIdempotencyKey)
            .putAll(super.getHeaders())
            .build();
    }

    protected abstract GetRequest<T> handleConflict(HttpClient httpClient, String id);
}
