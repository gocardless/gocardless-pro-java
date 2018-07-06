package com.gocardless.errors;

import com.gocardless.GoCardlessException;

/**
 * Exception thrown when the signature included in a webhook doesn't match one computed
 * using the webhook endpoint's secret and the request body, indicating that the webhook
 * is not genuinely from GoCardless
 */
public class InvalidSignatureException extends GoCardlessException {
}
