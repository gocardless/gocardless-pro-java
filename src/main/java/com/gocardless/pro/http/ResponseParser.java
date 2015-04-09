package com.gocardless.pro.http;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.util.List;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

final class ResponseParser {
    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
            .create();

    <T> T parseSingle(Reader stream, String envelope, Class<T> clazz) {
        JsonElement json = new JsonParser().parse(stream);
        JsonObject object = json.getAsJsonObject().getAsJsonObject(envelope);
        return gson.fromJson(object, clazz);
    }

    <T> List<T> parseMultiple(Reader stream, String envelope, TypeToken<List<T>> clazz) {
        JsonElement json = new JsonParser().parse(stream);
        JsonArray array = json.getAsJsonObject().getAsJsonArray(envelope);

        List<T> items = gson.fromJson(array, clazz.getType());
        return ImmutableList.copyOf(items);
    }
}
