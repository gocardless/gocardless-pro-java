package com.gocardless.resources;

/**
 * Represents a mandate pdf resource returned from the API.
 *
 * Construct a mandate PDF for a given set of bank details or an already-existing mandate.
 */
public class MandatePdf {
    private MandatePdf() {
        // blank to prevent instantiation
    }

    private String expiresAt;
    private String url;

    /**
     * The date and time at which `url` will cease to be accessible (30 minutes after the original
     * request).
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * The URL at which this mandate PDF can be viewed until it expires at the date and time specified by
     * `expires_at`. *You should not store this URL as it will only work for a short period of time. The
     * structure of these URLs may change at any time.*
     */
    public String getUrl() {
        return url;
    }
}
