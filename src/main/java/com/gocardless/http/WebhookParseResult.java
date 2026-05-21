package com.gocardless.http;

import com.gocardless.resources.Event;
import com.google.common.collect.ImmutableList;

/**
 * Represents the result of parsing a webhook, containing both the events and the webhook metadata.
 */
public final class WebhookParseResult {
    private final ImmutableList<Event> events;
    private final String webhookId;

    WebhookParseResult(ImmutableList<Event> events, String webhookId) {
        this.events = events;
        this.webhookId = webhookId;
    }

    /**
     * Returns the list of events included in the webhook.
     */
    public ImmutableList<Event> getEvents() {
        return events;
    }

    /**
     * Returns the webhook ID from the meta field, or null if not present.
     */
    public String getWebhookId() {
        return webhookId;
    }
}
