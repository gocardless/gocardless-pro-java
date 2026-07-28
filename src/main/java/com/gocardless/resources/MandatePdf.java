package com.gocardless.resources;

/**
 * Represents a mandate pdf resource returned from the API.
 *
 * Mandate PDFs allow you to easily display <a href=
 * "https://developer.gocardless.com/api-reference/#appendix-compliance-requirements">scheme-rules
 * compliant</a> Direct Debit mandates to your customers.
 */
public class MandatePdf {
    private MandatePdf() {
        // blank to prevent instantiation
    }

    private String expiresAt;
    private String url;

    /**
     * The date and time at which the <code>url</code> will expire (10 minutes after the original
     * request).
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * The URL at which this mandate PDF can be viewed until it expires at the date and time
     * specified by <code>expires_at</code>. You should not store this URL or rely on its structure
     * remaining the same.
     */
    public String getUrl() {
        return url;
    }
}
