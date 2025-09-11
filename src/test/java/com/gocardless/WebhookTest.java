package com.gocardless;

import com.gocardless.resources.Event;
import com.gocardless.errors.InvalidSignatureException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WebhookTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldParseWebhookWithValidSignature() throws IOException {
        String requestBody = "{\"events\":[{\"id\":\"EV00BD05S5VM2T\",\"created_at\":\"2018-07-05T09:13:51.404Z\",\"resource_type\":\"subscriptions\",\"action\":\"created\",\"links\":{\"subscription\":\"SB0003JJQ2MR06\"},\"details\":{\"origin\":\"api\",\"cause\":\"subscription_created\",\"description\":\"Subscription created via the API.\"},\"metadata\":{}},{\"id\":\"EV00BD05TB8K63\",\"created_at\":\"2018-07-05T09:13:56.893Z\",\"resource_type\":\"mandates\",\"action\":\"created\",\"links\":{\"mandate\":\"MD000AMA19XGEC\"},\"details\":{\"origin\":\"api\",\"cause\":\"mandate_created\",\"description\":\"Mandate created via the API.\"},\"metadata\":{}}]}";
        String webhookEndpointSecret = "ED7D658C-D8EB-4941-948B-3973214F2D49";
        String signatureHeader = "2693754819d3e32d7e8fcb13c729631f316c6de8dc1cf634d6527f1c07276e7e";

        List<Event> events = Webhook.parse(requestBody, signatureHeader, webhookEndpointSecret);

        assertThat(events.size()).isEqualTo(2);
        assertThat(events.get(0).getId()).isEqualTo("EV00BD05S5VM2T");
        assertThat(events.get(1).getId()).isEqualTo("EV00BD05TB8K63");
    }

    @Test
    public void shouldThrowExceptionForWebhookWithInvalidSignature() throws IOException {
        String requestBody = "{\"events\":[{\"id\":\"EV00BD05S5VM2T\",\"created_at\":\"2018-07-05T09:13:51.404Z\",\"resource_type\":\"subscriptions\",\"action\":\"created\",\"links\":{\"subscription\":\"SB0003JJQ2MR06\"},\"details\":{\"origin\":\"api\",\"cause\":\"subscription_created\",\"description\":\"Subscription created via the API.\"},\"metadata\":{}},{\"id\":\"EV00BD05TB8K63\",\"created_at\":\"2018-07-05T09:13:56.893Z\",\"resource_type\":\"mandates\",\"action\":\"created\",\"links\":{\"mandate\":\"MD000AMA19XGEC\"},\"details\":{\"origin\":\"api\",\"cause\":\"mandate_created\",\"description\":\"Mandate created via the API.\"},\"metadata\":{}}]}";
        String webhookEndpointSecret = "ED7D658C-D8EB-4941-948B-3973214F2D49";
        String signatureHeader = "dummy";

        exception.expect(InvalidSignatureException.class);

        Webhook.parse(requestBody, signatureHeader, webhookEndpointSecret);
    }

    @Test
    public void shouldValidateWebhookWithValidSignature() throws IOException {
        String requestBody = "{\"events\":[{\"id\":\"EV00BD05S5VM2T\",\"created_at\":\"2018-07-05T09:13:51.404Z\",\"resource_type\":\"subscriptions\",\"action\":\"created\",\"links\":{\"subscription\":\"SB0003JJQ2MR06\"},\"details\":{\"origin\":\"api\",\"cause\":\"subscription_created\",\"description\":\"Subscription created via the API.\"},\"metadata\":{}},{\"id\":\"EV00BD05TB8K63\",\"created_at\":\"2018-07-05T09:13:56.893Z\",\"resource_type\":\"mandates\",\"action\":\"created\",\"links\":{\"mandate\":\"MD000AMA19XGEC\"},\"details\":{\"origin\":\"api\",\"cause\":\"mandate_created\",\"description\":\"Mandate created via the API.\"},\"metadata\":{}}]}";
        String webhookEndpointSecret = "ED7D658C-D8EB-4941-948B-3973214F2D49";
        String signatureHeader = "2693754819d3e32d7e8fcb13c729631f316c6de8dc1cf634d6527f1c07276e7e";

        assertThat(Webhook.isValidSignature(requestBody, signatureHeader, webhookEndpointSecret)).isEqualTo(true);
    }

    @Test
    public void shouldValidateWebhookWithInvalidSignature() throws IOException {
        String requestBody = "{\"events\":[{\"id\":\"EV00BD05S5VM2T\",\"created_at\":\"2018-07-05T09:13:51.404Z\",\"resource_type\":\"subscriptions\",\"action\":\"created\",\"links\":{\"subscription\":\"SB0003JJQ2MR06\"},\"details\":{\"origin\":\"api\",\"cause\":\"subscription_created\",\"description\":\"Subscription created via the API.\"},\"metadata\":{}},{\"id\":\"EV00BD05TB8K63\",\"created_at\":\"2018-07-05T09:13:56.893Z\",\"resource_type\":\"mandates\",\"action\":\"created\",\"links\":{\"mandate\":\"MD000AMA19XGEC\"},\"details\":{\"origin\":\"api\",\"cause\":\"mandate_created\",\"description\":\"Mandate created via the API.\"},\"metadata\":{}}]}";
        String webhookEndpointSecret = "ED7D658C-D8EB-4941-948B-3973214F2D49";
        String signatureHeader = "dummy";

        assertThat(Webhook.isValidSignature(requestBody, signatureHeader, webhookEndpointSecret)).isEqualTo(false);
    }
}
