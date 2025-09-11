package com.gocardless.resources;

/**
 * Represents a payer theme resource returned from the API.
 *
 * Custom colour themes for payment pages and customer notifications.
 */
public class PayerTheme {
    private PayerTheme() {
        // blank to prevent instantiation
    }

    private String id;

    /**
     * Unique identifier, beginning with "PTH".
     */
    public String getId() {
        return id;
    }
}
