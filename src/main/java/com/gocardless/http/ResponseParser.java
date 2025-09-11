package com.gocardless.http;

import com.gocardless.errors.ApiErrorResponse;
import com.gocardless.errors.GoCardlessApiException;
import com.gocardless.errors.GoCardlessErrorMapper;
import com.gocardless.errors.MalformedResponseException;
import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.List;

final class ResponseParser {
    private final Gson gson;

    ResponseParser(Gson gson) {
        this.gson = gson;
    }

    <T> T parseSingle(String responseBody, String envelope, Class<T> clazz) {
        JsonElement json;

        try {
            json = new JsonParser().parse(responseBody);
        } catch (JsonSyntaxException e) {
            throw new MalformedResponseException(responseBody);
        }

        JsonObject object = json.getAsJsonObject().getAsJsonObject(envelope);
        return gson.fromJson(object, clazz);
    }

    <T> ImmutableList<T> parseMultiple(String responseBody, String envelope, TypeToken<List<T>> clazz) {
        JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();

        JsonArray array = json.getAsJsonArray(envelope);
        List<T> items = gson.fromJson(array, clazz.getType());

        return ImmutableList.copyOf(items);
    }

    <T> ImmutableList<T> parseMultiple(JsonObject json, String envelope, TypeToken<List<T>> clazz) {
        JsonArray array = json.getAsJsonArray(envelope);
        List<T> items = gson.fromJson(array, clazz.getType());

        return ImmutableList.copyOf(items);
    }

    <T> ListResponse<T> parsePage(String responseBody, String envelope, TypeToken<List<T>> clazz) {
      JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();

      List<T> items = parseMultiple(json, envelope, clazz);

      JsonObject metaJson = json.getAsJsonObject("meta");
      ListResponse.Meta meta = gson.fromJson(metaJson, ListResponse.Meta.class);

      JsonObject linkedJson = json.getAsJsonObject("linked");
      ListResponse.Linked linked = gson.fromJson(linkedJson, ListResponse.Linked.class);
      return new ListResponse<>(ImmutableList.copyOf(items), meta, linked);
    }

    GoCardlessApiException parseError(String responseBody) {
        ApiErrorResponse error = parseSingle(responseBody, "error", ApiErrorResponse.class);
        return GoCardlessErrorMapper.toException(error);
    }
}
