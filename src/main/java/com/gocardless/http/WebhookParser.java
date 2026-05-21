package com.gocardless.http;

import com.gocardless.resources.Event;
import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public final class WebhookParser {
    private WebhookParser() {}

    public static ImmutableList<Event> parse(String responseBody) {
        Gson gson = GsonFactory.build();
        ResponseParser responseParser = new ResponseParser(gson);
        return responseParser.parseMultiple(responseBody, "events",
                new TypeToken<List<Event>>() {});
    }

    public static WebhookParseResult parseWithMeta(String responseBody) {
        Gson gson = GsonFactory.build();
        ResponseParser responseParser = new ResponseParser(gson);
        ImmutableList<Event> events = responseParser.parseMultiple(responseBody, "events",
                new TypeToken<List<Event>>() {});
        String webhookId = null;
        try {
            JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();
            JsonObject meta = json.getAsJsonObject("meta");
            if (meta != null && meta.has("webhook_id")) {
                webhookId = meta.get("webhook_id").getAsString();
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            // If we can't parse meta, just leave webhookId as null
        }
        return new WebhookParseResult(events, webhookId);
    }
}
