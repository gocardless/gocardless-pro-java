package com.gocardless.services;

import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.MandateImport;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

/**
 * Service class for working with mandate import resources.
 *
 * Mandate Imports allow you to migrate existing mandates from other providers into the
 * GoCardless platform.
 * 
 * The process is as follows:
 * 
 *   1. [Create a mandate import](#mandate-imports-create-a-new-mandate-import)
 *   2. [Add entries](#mandate-import-entries-add-a-mandate-import-entry) to the import
 *   3. [Submit](#mandate-imports-submit-a-mandate-import) the import
 *   4. Wait until a member of the GoCardless team approves the import, at which point the mandates
 * will be created
 *   5. [Link up the mandates](#mandate-import-entries-list-all-mandate-import-entries) in your
 * system
 * 
 * When you add entries to your mandate import, they are not turned into actual mandates
 * until the mandate import is submitted by you via the API, and then processed by a member
 * of the GoCardless team. When that happens, a mandate will be created for each entry in the import.
 * 
 * We will issue a `mandate_created` webhook for each entry, which will be the same as the webhooks
 * triggered when [ creating a mandate ](#mandates-create-a-mandate) using the mandates API. Once
 * these
 * webhooks start arriving, any reconciliation can now be accomplished by
 * [checking the current status](#mandate-imports-get-a-mandate-import) of the mandate import and
 * [linking up the mandates to your system](#mandate-import-entries-list-all-mandate-import-entries).
 * 
 * <p class="notice">Note that all Mandate Imports have an upper limit of 30,000 entries, so
 * we recommend you split your import into several smaller imports if you're planning to
 * exceed this threshold.</p>
 * 
 * <p class="restricted-notice"><strong>Restricted</strong>: This API is currently
 * only available for approved integrators - please <a href="mailto:help@gocardless.com">get
 * in touch</a> if you would like to use this API.</p>
 */
public class MandateImportService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#mandateImports() }.
     */
    public MandateImportService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Mandate imports are first created, before mandates are added one-at-a-time, so
     * this endpoint merely signals the start of the import process. Once you've finished
     * adding entries to an import, you should
     * [submit](#mandate-imports-submit-a-mandate-import) it.
     */
    public MandateImportCreateRequest create() {
        return new MandateImportCreateRequest(httpClient);
    }

    /**
     * Returns a single mandate import.
     */
    public MandateImportGetRequest get(String identity) {
        return new MandateImportGetRequest(httpClient, identity);
    }

    /**
     * Submits the mandate import, which allows it to be processed by a member of the
     * GoCardless team. Once the import has been submitted, it can no longer have entries
     * added to it.
     */
    public MandateImportSubmitRequest submit(String identity) {
        return new MandateImportSubmitRequest(httpClient, identity);
    }

    /**
     * Cancels the mandate import, which aborts the import process and stops the mandates
     * being set up in GoCardless. Once the import has been cancelled, it can no longer have
     * entries added to it. Mandate imports which have already been submitted cannot be
     * cancelled.
     */
    public MandateImportCancelRequest cancel(String identity) {
        return new MandateImportCancelRequest(httpClient, identity);
    }

    /**
     * Request class for {@link MandateImportService#create }.
     *
     * Mandate imports are first created, before mandates are added one-at-a-time, so
     * this endpoint merely signals the start of the import process. Once you've finished
     * adding entries to an import, you should
     * [submit](#mandate-imports-submit-a-mandate-import) it.
     */
    public static final class MandateImportCreateRequest extends
            IdempotentPostRequest<MandateImport> {
        private Scheme scheme;

        /**
         * A Direct Debit scheme. Currently "autogiro", "bacs", "becs", betalingsservice", and "sepa_core"
         * are supported.
         */
        public MandateImportCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        public MandateImportCreateRequest withIdempotencyKey(String idempotencyKey) {
            super.setIdempotencyKey(idempotencyKey);
            return this;
        }

        @Override
        protected GetRequest<MandateImport> handleConflict(HttpClient httpClient, String id) {
            MandateImportGetRequest request = new MandateImportGetRequest(httpClient, id);
            for (Map.Entry<String, String> header : this.getCustomHeaders().entrySet()) {
                request = request.withHeader(header.getKey(), header.getValue());
            }
            return request;
        }

        private MandateImportCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public MandateImportCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_imports";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_imports";
        }

        @Override
        protected Class<MandateImport> getResponseClass() {
            return MandateImport.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public enum Scheme {
            @SerializedName("autogiro")
            AUTOGIRO, @SerializedName("bacs")
            BACS, @SerializedName("becs")
            BECS, @SerializedName("betalingsservice")
            BETALINGSSERVICE, @SerializedName("sepa_core")
            SEPA_CORE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link MandateImportService#get }.
     *
     * Returns a single mandate import.
     */
    public static final class MandateImportGetRequest extends GetRequest<MandateImport> {
        @PathParam
        private final String identity;

        private MandateImportGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateImportGetRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_imports/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_imports";
        }

        @Override
        protected Class<MandateImport> getResponseClass() {
            return MandateImport.class;
        }
    }

    /**
     * Request class for {@link MandateImportService#submit }.
     *
     * Submits the mandate import, which allows it to be processed by a member of the
     * GoCardless team. Once the import has been submitted, it can no longer have entries
     * added to it.
     */
    public static final class MandateImportSubmitRequest extends PostRequest<MandateImport> {
        @PathParam
        private final String identity;

        private MandateImportSubmitRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateImportSubmitRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_imports/:identity/actions/submit";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_imports";
        }

        @Override
        protected Class<MandateImport> getResponseClass() {
            return MandateImport.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }

    /**
     * Request class for {@link MandateImportService#cancel }.
     *
     * Cancels the mandate import, which aborts the import process and stops the mandates
     * being set up in GoCardless. Once the import has been cancelled, it can no longer have
     * entries added to it. Mandate imports which have already been submitted cannot be
     * cancelled.
     */
    public static final class MandateImportCancelRequest extends PostRequest<MandateImport> {
        @PathParam
        private final String identity;

        private MandateImportCancelRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public MandateImportCancelRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "mandate_imports/:identity/actions/cancel";
        }

        @Override
        protected String getEnvelope() {
            return "mandate_imports";
        }

        @Override
        protected Class<MandateImport> getResponseClass() {
            return MandateImport.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }
    }
}
