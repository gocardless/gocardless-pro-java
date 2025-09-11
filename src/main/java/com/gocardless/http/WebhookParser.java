package com.gocardless.http;

import com.gocardless.resources.Event;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public final class WebhookParser {
    private WebhookParser() {
    }

    public static ImmutableList<Event> parse(String responseBody) {
        Gson gson = GsonFactory.build();
        ResponseParser responseParser = new ResponseParser(gson);

        return responseParser.parseMultiple(responseBody, "events", new TypeToken<List<Event>>() {});
    }
}
