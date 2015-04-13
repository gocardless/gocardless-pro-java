package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Refund;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RefundService {
    private HttpClient httpClient;

    public RefundService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RefundCreateRequest create() throws IOException {
        return new RefundCreateRequest(httpClient);
    }

    public RefundListRequest list() throws IOException {
        return new RefundListRequest(httpClient);
    }

    public RefundGetRequest get(String identity) throws IOException {
        return new RefundGetRequest(httpClient, identity);
    }

    public RefundUpdateRequest update(String identity) throws IOException {
        return new RefundUpdateRequest(httpClient, identity);
    }

    public static final class RefundCreateRequest extends PostRequest<Refund> {
        private Integer amount;

        public RefundCreateRequest withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        private Object links;

        public RefundCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private Object metadata;

        public RefundCreateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private Integer totalAmountConfirmation;

        public RefundCreateRequest withTotalAmountConfirmation(Integer totalAmountConfirmation) {
            this.totalAmountConfirmation = totalAmountConfirmation;
            return this;
        }

        private RefundCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class RefundListRequest extends ListRequest<Refund> {
        private String after;

        public RefundListRequest withAfter(String after) {
            this.after = after;
            return this;
        }

        private String before;

        public RefundListRequest withBefore(String before) {
            this.before = before;
            return this;
        }

        private Integer limit;

        public RefundListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        private String payment;

        public RefundListRequest withPayment(String payment) {
            this.payment = payment;
            return this;
        }

        private RefundListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            if (after != null) {
                params.put("after", after);
            }
            if (before != null) {
                params.put("before", before);
            }
            if (limit != null) {
                params.put("limit", limit);
            }
            if (payment != null) {
                params.put("payment", payment);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected TypeToken<List<Refund>> getTypeToken() {
            return new TypeToken<List<Refund>>() {};
        }
    }

    public static final class RefundGetRequest extends GetRequest<Refund> {
        @PathParam
        private final String identity;

        private RefundGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }
    }

    public static final class RefundUpdateRequest extends PutRequest<Refund> {
        @PathParam
        private final String identity;
        private Object metadata;

        public RefundUpdateRequest withMetadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        private RefundUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/refunds/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "refunds";
        }

        @Override
        protected Class<Refund> getResponseClass() {
            return Refund.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
