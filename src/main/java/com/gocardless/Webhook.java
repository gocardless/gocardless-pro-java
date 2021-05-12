package com.gocardless;

import com.gocardless.errors.InvalidSignatureException;
import com.gocardless.http.WebhookParser;
import com.gocardless.resources.Event;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.util.List;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * Class containing a collection of functions for validating and parsing GoCardless webhooks
 */
public class Webhook {
    private Webhook() {}

    /**
     * Validates that a webhook was genuinely sent by GoCardless using `isValidSignature`, and then
     * parses it into an array of `com.gocardless.resources.Event` objects representing each event
     * included in the webhook.
     *
     * @param requestBody the request body
     * @param signatureHeader the signature included in the request, found in the
     *        `Webhook-Signature` header
     * @param webhookEndpointSecret the webhook endpoint secret for your webhook endpoint, as
     *        configured in your GoCardless Dashboard
     * @return the events included in the webhook
     * @throws com.gocardless.errors.InvalidSignatureException if the signature header specified
     *         does not match the signature computed using the request body and webhook endpoint
     *         secret
     */
    public static List<Event> parse(String requestBody, String signatureHeader,
            String webhookEndpointSecret) {
        if (isValidSignature(requestBody, signatureHeader, webhookEndpointSecret)) {
            return WebhookParser.parse(requestBody);
        } else {
            throw new InvalidSignatureException();
        }
    }

    /**
     * Validates that a webhook was genuinely sent by GoCardless by computing its signature using
     * the body and your webhook endpoint secret, and comparing that with the signature included in
     * the `Webhook-Signature` header.
     *
     * @param requestBody the request body
     * @param signatureHeader the signature included in the request, found in the
     *        `Webhook-Signature` header
     * @param webhookEndpointSecret the webhook endpoint secret for your webhook endpoint, as
     *        configured in your GoCardless Dashboard
     * @return whether the webhook's signature is valid
     */
    public static boolean isValidSignature(String requestBody, String signatureHeader,
            String webhookEndpointSecret) {
        String computedSignature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, webhookEndpointSecret)
                .hmacHex(requestBody);
        return MessageDigest.isEqual(signatureHeader.getBytes(), computedSignature.getBytes());
    }
}
