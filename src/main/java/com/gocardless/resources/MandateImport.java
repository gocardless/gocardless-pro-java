package com.gocardless.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a mandate import resource returned from the API.
 *
 * Mandate Imports allow you to migrate existing mandates from other providers into the GoCardless
 * platform.
 * 
 * The process is as follows:
 * 
 * <ol>
 * <li><a href=
 * "https://developer.gocardless.com/api-reference/#mandate-imports-create-a-new-mandate-import">Create
 * a mandate import</a></li>
 * <li><a href=
 * "https://developer.gocardless.com/api-reference/#mandate-import-entries-add-a-mandate-import-entry">Add
 * entries</a> to the import</li>
 * <li><a href=
 * "https://developer.gocardless.com/api-reference/#mandate-imports-submit-a-mandate-import">Submit</a>
 * the import</li>
 * <li>Wait until a member of the GoCardless team approves the import, at which point the mandates
 * will be created</li>
 * <li><a href=
 * "https://developer.gocardless.com/api-reference/#mandate-import-entries-list-all-mandate-import-entries">Link
 * up the mandates</a> in your system</li>
 * </ol>
 * When you add entries to your mandate import, they are not turned into actual mandates until the
 * mandate import is submitted by you via the API, and then processed by a member of the GoCardless
 * team. When that happens, a mandate will be created for each entry in the import.
 * 
 * We will issue a <code>mandate_created</code> webhook for each entry, which will be the same as
 * the webhooks triggered when
 * <a href="https://developer.gocardless.com/api-reference/#mandates-create-a-mandate"> creating a
 * mandate </a> using the mandates API. Once these webhooks start arriving, any reconciliation can
 * now be accomplished by <a href=
 * "https://developer.gocardless.com/api-reference/#mandate-imports-get-a-mandate-import">checking
 * the current status</a> of the mandate import and <a href=
 * "https://developer.gocardless.com/api-reference/#mandate-import-entries-list-all-mandate-import-entries">linking
 * up the mandates to your system</a>.
 * 
 * <p class="notice">
 * Note that all Mandate Imports have an upper limit of 30,000 entries, so we recommend you split
 * your import into several smaller imports if you're planning to exceed this threshold.
 * </p>
 * <p class="restricted-notice">
 * <strong>Restricted</strong>: This API is currently only available for approved integrators -
 * please <a href="mailto:help@gocardless.com">get in touch</a> if you would like to use this API.
 * </p>
 */
public class MandateImport {
    private MandateImport() {
        // blank to prevent instantiation
    }

    private String createdAt;
    private String id;
    private Links links;
    private Scheme scheme;
    private Status status;

    /**
     * Fixed <a href=
     * "https://developer.gocardless.com/api-reference/#api-usage-dates-and-times">timestamp</a>,
     * recording when this resource was created.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Unique identifier, beginning with "IM".
     */
    public String getId() {
        return id;
    }

    /**
     * Related resources
     */
    public Links getLinks() {
        return links;
    }

    /**
     * The scheme of the mandates to be imported.<br>
     * </br>
     * All mandates in a single mandate import must be for the same scheme.
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * The status of the mandate import.
     * 
     * <ul>
     * <li><code>created</code>: A new mandate import.</li>
     * <li><code>submitted</code>: After the integrator has finished adding mandates and <a href=
     * "https://developer.gocardless.com/api-reference/#mandate-imports-submit-a-mandate-import">submitted</a>
     * the import.</li>
     * <li><code>cancelled</code>: If the integrator <a href=
     * "https://developer.gocardless.com/api-reference/#mandate-imports-cancel-a-mandate-import">cancelled</a>
     * the mandate import.</li>
     * <li><code>processing</code>: Once a mandate import has been approved by a GoCardless team
     * member it will be in this state while mandates are imported.</li>
     * <li><code>processed</code>: When all mandates have been imported successfully.</li>
     * </ul>
     */
    public Status getStatus() {
        return status;
    }

    public enum Scheme {
        @SerializedName("ach")
        ACH, @SerializedName("autogiro")
        AUTOGIRO, @SerializedName("bacs")
        BACS, @SerializedName("becs")
        BECS, @SerializedName("becs_nz")
        BECS_NZ, @SerializedName("betalingsservice")
        BETALINGSSERVICE, @SerializedName("faster_payments")
        FASTER_PAYMENTS, @SerializedName("pad")
        PAD, @SerializedName("pay_to")
        PAY_TO, @SerializedName("sepa_core")
        SEPA_CORE, @SerializedName("unknown")
        UNKNOWN
    }

    public enum Status {
        @SerializedName("created")
        CREATED, @SerializedName("submitted")
        SUBMITTED, @SerializedName("cancelled")
        CANCELLED, @SerializedName("processing")
        PROCESSING, @SerializedName("processed")
        PROCESSED, @SerializedName("unknown")
        UNKNOWN
    }

    /**
     * Represents a link resource returned from the API.
     *
     * Related resources
     */
    public static class Links {
        private Links() {
            // blank to prevent instantiation
        }

        private String creditor;

        /**
         * ID of the associated creditor.
         */
        public String getCreditor() {
            return creditor;
        }
    }
}
