package com.gocardless.resources;

/**
 * Represents a logo resource returned from the API.
 *
 * Logos are image uploads that, when associated with a creditor, are shown on the <a href=
 * "https://developer.gocardless.com/api-reference/#billing-requests-billing-request-flows">billing
 * request flow</a> payment pages.
 */
public class Logo {
    private Logo() {
        // blank to prevent instantiation
    }

    private String id;

    /**
     * Unique identifier, beginning with "LO".
     */
    public String getId() {
        return id;
    }
}
