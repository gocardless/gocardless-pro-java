package com.gocardless.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

final class RequestWriter {
    private final Gson gson;

    RequestWriter(Gson gson) {
        this.gson = gson;
    }

    public <T> String write(T object, String envelope) {
        JsonObject result = new JsonObject();
        result.add(envelope, gson.toJsonTree(object));
        return gson.toJson(result);
    }
}
