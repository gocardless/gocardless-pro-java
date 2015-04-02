package com.example;

import com.google.gson.*;

import java.io.Reader;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

final class ResponseParser {
    private final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();

    <T> T parseSingle(Reader stream, String envelope, Class<T> clazz) {
        JsonElement json = new JsonParser().parse(stream);
        JsonObject object = json.getAsJsonObject().getAsJsonObject(envelope);
        return gson.fromJson(object, clazz);
    }
}
