package com.gocardless.pro.services;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.RedirectFlow;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RedirectFlowService {
    private HttpClient httpClient;

    public RedirectFlowService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RedirectFlowCreateRequest create() throws IOException {
        return new RedirectFlowCreateRequest(httpClient);
    }

    public RedirectFlowGetRequest get(String identity) throws IOException {
        return new RedirectFlowGetRequest(httpClient, identity);
    }

    public RedirectFlowCompleteRequest complete(String identity) throws IOException {
        return new RedirectFlowCompleteRequest(httpClient, identity);
    }

    public static final class RedirectFlowCreateRequest extends PostRequest<RedirectFlow> {
        private String description;

        public RedirectFlowCreateRequest withDescription(String description) {
            this.description = description;
            return this;
        }

        private Object links;

        public RedirectFlowCreateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        public enum Scheme {
            BACS, SEPA_CORE,
        }

        private Scheme scheme;

        public RedirectFlowCreateRequest withScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        private String sessionToken;

        public RedirectFlowCreateRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private String successRedirectUrl;

        public RedirectFlowCreateRequest withSuccessRedirectUrl(String successRedirectUrl) {
            this.successRedirectUrl = successRedirectUrl;
            return this;
        }

        private RedirectFlowCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/redirect_flows";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class RedirectFlowGetRequest extends GetRequest<RedirectFlow> {
        @PathParam
        private final String identity;

        private RedirectFlowGetRequest(HttpClient httpClient, String identity) {
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
            return "/redirect_flows/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }
    }

    public static final class RedirectFlowCompleteRequest extends PostRequest<RedirectFlow> {
        @PathParam
        private final String identity;
        private String sessionToken;

        public RedirectFlowCompleteRequest withSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        private RedirectFlowCompleteRequest(HttpClient httpClient, String identity) {
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
            return "/redirect_flows/:identity/actions/complete";
        }

        @Override
        protected String getEnvelope() {
            return "redirect_flows";
        }

        @Override
        protected Class<RedirectFlow> getResponseClass() {
            return RedirectFlow.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
