package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.OutboundPaymentImportEntry;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with outbound payment import entry resources.
 *
 * Import Entries are the individual rows of an outbound payment import, representing each payment
 * to be created.
 */
public class OutboundPaymentImportEntryService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#outboundPaymentImportEntries() }.
     */
    public OutboundPaymentImportEntryService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of the entries for a given
     * outbound payment import.
     */
    public OutboundPaymentImportEntryListRequest<ListResponse<OutboundPaymentImportEntry>> list() {
        return new OutboundPaymentImportEntryListRequest<>(httpClient,
                ListRequest.<OutboundPaymentImportEntry>pagingExecutor());
    }

    public OutboundPaymentImportEntryListRequest<Iterable<OutboundPaymentImportEntry>> all() {
        return new OutboundPaymentImportEntryListRequest<>(httpClient,
                ListRequest.<OutboundPaymentImportEntry>iteratingExecutor());
    }

    /**
     * Request class for {@link OutboundPaymentImportEntryService#list }.
     *
     * Returns a [cursor-paginated](#api-usage-cursor-pagination) list of the entries for a given
     * outbound payment import.
     */
    public static final class OutboundPaymentImportEntryListRequest<S>
            extends ListRequest<S, OutboundPaymentImportEntry> {
        private String outboundPaymentImport;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public OutboundPaymentImportEntryListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public OutboundPaymentImportEntryListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public OutboundPaymentImportEntryListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        /**
         * Unique identifier, beginning with "IM".
         */
        public OutboundPaymentImportEntryListRequest<S> withOutboundPaymentImport(
                String outboundPaymentImport) {
            this.outboundPaymentImport = outboundPaymentImport;
            return this;
        }

        private OutboundPaymentImportEntryListRequest(HttpClient httpClient,
                ListRequestExecutor<S, OutboundPaymentImportEntry> executor) {
            super(httpClient, executor);
        }

        public OutboundPaymentImportEntryListRequest<S> withHeader(String headerName,
                String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (outboundPaymentImport != null) {
                params.put("outbound_payment_import", outboundPaymentImport);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "outbound_payment_import_entries";
        }

        @Override
        protected String getEnvelope() {
            return "outbound_payment_import_entries";
        }

        @Override
        protected TypeToken<List<OutboundPaymentImportEntry>> getTypeToken() {
            return new TypeToken<List<OutboundPaymentImportEntry>>() {};
        }
    }
}
