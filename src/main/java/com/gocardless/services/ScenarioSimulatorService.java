package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.ScenarioSimulator;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with scenario simulator resources.
 *
 * Scenario Simulators allow you to manually trigger and test certain paths that your
 * integration will encounter in the real world. These endpoints are only active in the
 * sandbox environment.
 */
public class ScenarioSimulatorService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#scenarioSimulators() }.
     */
    public ScenarioSimulatorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Returns a list of all the available scenario simulators
     */
    public ScenarioSimulatorListRequest<ListResponse<ScenarioSimulator>> list() {
        return new ScenarioSimulatorListRequest<>(httpClient,
                ListRequest.<ScenarioSimulator>pagingExecutor());
    }

    public ScenarioSimulatorListRequest<Iterable<ScenarioSimulator>> all() {
        return new ScenarioSimulatorListRequest<>(httpClient,
                ListRequest.<ScenarioSimulator>iteratingExecutor());
    }

    /**
     * Runs the specific scenario simulator against the specific resource
     */
    public ScenarioSimulatorRunRequest run(String identity) {
        return new ScenarioSimulatorRunRequest(httpClient, identity);
    }

    /**
     * Request class for {@link ScenarioSimulatorService#list }.
     *
     * Returns a list of all the available scenario simulators
     */
    public static final class ScenarioSimulatorListRequest<S> extends
            ListRequest<S, ScenarioSimulator> {
        private ScenarioSimulatorListRequest(HttpClient httpClient,
                ListRequestExecutor<S, ScenarioSimulator> executor) {
            super(httpClient, executor);
        }

        public ScenarioSimulatorListRequest<S> withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "scenario_simulators";
        }

        @Override
        protected String getEnvelope() {
            return "scenario_simulators";
        }

        @Override
        protected TypeToken<List<ScenarioSimulator>> getTypeToken() {
            return new TypeToken<List<ScenarioSimulator>>() {};
        }
    }

    /**
     * Request class for {@link ScenarioSimulatorService#run }.
     *
     * Runs the specific scenario simulator against the specific resource
     */
    public static final class ScenarioSimulatorRunRequest extends PostRequest<ScenarioSimulator> {
        @PathParam
        private final String identity;
        private Links links;

        public ScenarioSimulatorRunRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the resource to run the simulation against. This should be of the type returned for this
         * simulator in the `GET /scenario_simulators` API.
         */
        public ScenarioSimulatorRunRequest withLinksResource(String resource) {
            if (links == null) {
                links = new Links();
            }
            links.withResource(resource);
            return this;
        }

        private ScenarioSimulatorRunRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public ScenarioSimulatorRunRequest withHeader(String headerName, String headerValue) {
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
            return "scenario_simulators/:identity/actions/run";
        }

        @Override
        protected String getEnvelope() {
            return "scenario_simulators";
        }

        @Override
        protected Class<ScenarioSimulator> getResponseClass() {
            return ScenarioSimulator.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        @Override
        protected String getRequestEnvelope() {
            return "data";
        }

        public static class Links {
            private String resource;

            /**
             * ID of the resource to run the simulation against. This should be of the type returned for this
             * simulator in the `GET /scenario_simulators` API.
             */
            public Links withResource(String resource) {
                this.resource = resource;
                return this;
            }
        }
    }
}
