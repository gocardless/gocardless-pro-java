package com.gocardless.pro.http;

import java.io.Reader;
import java.util.List;

import com.gocardless.pro.exceptions.*;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

final class ResponseParser {
    private final Gson gson;

    ResponseParser(Gson gson) {
        this.gson = gson;
    }

    <T> T parseSingle(Reader stream, String envelope, Class<T> clazz) {
        JsonElement json = new JsonParser().parse(stream);
        JsonObject object = json.getAsJsonObject().getAsJsonObject(envelope);
        return gson.fromJson(object, clazz);
    }

    <T> ListResponse<T> parsePage(Reader stream, String envelope, TypeToken<List<T>> clazz) {
        JsonObject json = new JsonParser().parse(stream).getAsJsonObject();
        JsonArray array = json.getAsJsonArray(envelope);
        List<T> items = gson.fromJson(array, clazz.getType());
        JsonObject metaJson = json.getAsJsonObject("meta");
        ListResponse.Meta meta = gson.fromJson(metaJson, ListResponse.Meta.class);
        return new ListResponse<>(ImmutableList.copyOf(items), meta);
    }

    GoCardlessApiException parseError(Reader stream) {
        ApiErrorResponse error = parseSingle(stream, "error", ApiErrorResponse.class);
        switch (error.getType()) {
            case GOCARDLESS:
                return new GoCardlessInternalException(error);
            case INVALID_API_USAGE:
                return new InvalidApiUsageException(error);
            case INVALID_STATE:
                return new InvalidStateException(error);
            case VALIDATION_FAILED:
                return new ValidationFailedException(error);
        }
        throw new IllegalStateException("Unknown error type: " + error.getType());
    }
}
