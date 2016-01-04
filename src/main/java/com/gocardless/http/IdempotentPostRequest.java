package com.gocardless.http;

import java.util.Map;
import java.util.UUID;

import com.gocardless.errors.ApiError;
import com.gocardless.errors.InvalidStateException;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

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
        String idempotencyKey = UUID.randomUUID().toString();
        Map<String, String> headers = ImmutableMap.of("Idempotency-Key", getIdempotencyKey());
        try {
            return getHttpClient().executeWithRetries(this, headers);
        } catch (InvalidStateException e) {
            Optional<ApiError> conflictError = Iterables.tryFind(e.getErrors(), CONFLICT_ERROR);
            if (conflictError.isPresent()) {
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

    private String getIdempotencyKey() {
        if (this.idempotencyKey == null) {
            return UUID.randomUUID().toString();
        } else {
            return idempotencyKey;
        }
    }

    protected abstract GetRequest<T> handleConflict(HttpClient httpClient, String id);
}
