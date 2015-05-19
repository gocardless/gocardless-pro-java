package com.gocardless.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.gocardless.errors.ApiErrorResponse;
import com.gocardless.errors.GoCardlessApiException;
import com.gocardless.errors.GoCardlessErrorMapper;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

final class ResponseParser {
    private final Gson gson;

    ResponseParser(Gson gson) {
        this.gson = gson;
    }

    <T> T parseSingle(InputStream stream, String envelope, Class<T> clazz) {
        Reader reader = new InputStreamReader(stream);
        JsonElement json = new JsonParser().parse(reader);
        JsonObject object = json.getAsJsonObject().getAsJsonObject(envelope);
        return gson.fromJson(object, clazz);
    }

    <T> ListResponse<T> parsePage(InputStream stream, String envelope, TypeToken<List<T>> clazz) {
        Reader reader = new InputStreamReader(stream);
        JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonArray array = json.getAsJsonArray(envelope);
        List<T> items = gson.fromJson(array, clazz.getType());
        JsonObject metaJson = json.getAsJsonObject("meta");
        ListResponse.Meta meta = gson.fromJson(metaJson, ListResponse.Meta.class);
        return new ListResponse<>(ImmutableList.copyOf(items), meta);
    }

    GoCardlessApiException parseError(InputStream stream) {
        ApiErrorResponse error = parseSingle(stream, "error", ApiErrorResponse.class);
        return GoCardlessErrorMapper.toException(error);
    }
}
