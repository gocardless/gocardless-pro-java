package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.Logo;

/**
 * Service class for working with logo resources.
 *
 * Logos are image uploads that, when associated with a creditor, are shown on the [billing request
 * flow](#billing-requests-billing-request-flows) payment pages.
 */
public class LogoService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling {@link com.gocardless.GoCardlessClient#logos() }.
     */
    public LogoService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new logo associated with a creditor. If a creditor already has a logo, this will
     * update the existing logo linked to the creditor.
     */
    public LogoCreateForCreditorRequest createForCreditor() {
        return new LogoCreateForCreditorRequest(httpClient);
    }

    /**
     * Request class for {@link LogoService#createForCreditor }.
     *
     * Creates a new logo associated with a creditor. If a creditor already has a logo, this will
     * update the existing logo linked to the creditor.
     */
    public static final class LogoCreateForCreditorRequest extends PostRequest<Logo> {
        private String image;
        private Links links;

        /**
         * Base64 encoded string.
         */
        public LogoCreateForCreditorRequest withImage(String image) {
            this.image = image;
            return this;
        }

        public LogoCreateForCreditorRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the creditor the payer theme belongs to
         */
        public LogoCreateForCreditorRequest withLinksCreditor(String creditor) {
            if (links == null) {
                links = new Links();
            }
            links.withCreditor(creditor);
            return this;
        }

        private LogoCreateForCreditorRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public LogoCreateForCreditorRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "branding/logos";
        }

        @Override
        protected String getEnvelope() {
            return "logos";
        }

        @Override
        protected Class<Logo> getResponseClass() {
            return Logo.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String creditor;

            /**
             * ID of the creditor the payer theme belongs to
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }
}
