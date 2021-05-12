package com.gocardless.http;

import static com.google.common.base.Charsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.resources.Event;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WebhookParserTest {
    @Test
    public void shouldParseWebhook() throws IOException {
        String requestBody =
                "{\"events\":[{\"id\":\"EV00BD05S5VM2T\",\"created_at\":\"2018-07-05T09:13:51.404Z\",\"resource_type\":\"subscriptions\",\"action\":\"created\",\"links\":{\"subscription\":\"SB0003JJQ2MR06\"},\"details\":{\"origin\":\"api\",\"cause\":\"subscription_created\",\"description\":\"Subscription created via the API.\"},\"metadata\":{}},{\"id\":\"EV00BD05TB8K63\",\"created_at\":\"2018-07-05T09:13:56.893Z\",\"resource_type\":\"mandates\",\"action\":\"created\",\"links\":{\"mandate\":\"MD000AMA19XGEC\"},\"details\":{\"origin\":\"api\",\"cause\":\"mandate_created\",\"description\":\"Mandate created via the API.\"},\"metadata\":{}}]}";
        ImmutableList<Event> events = WebhookParser.parse(requestBody);
        assertThat(events.size()).isEqualTo(2);
        assertThat(events.get(0).getId()).isEqualTo("EV00BD05S5VM2T");
        assertThat(events.get(1).getId()).isEqualTo("EV00BD05TB8K63");
    }
}
