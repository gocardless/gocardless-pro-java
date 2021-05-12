package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.ScenarioSimulator;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Service class for working with scenario simulator resources.
 *
 * Scenario Simulators allow you to manually trigger and test certain paths that your integration
 * will encounter in the real world. These endpoints are only active in the sandbox environment.
 */
public class ScenarioSimulatorService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#scenarioSimulators() }.
     */
    public ScenarioSimulatorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Runs the specific scenario simulator against the specific resource
     */
    public ScenarioSimulatorRunRequest run(String identity) {
        return new ScenarioSimulatorRunRequest(httpClient, identity);
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
         * ID of the resource to run the simulation against. Must be same type of resource as the
         * simulator that is being run. eg. Payment ID for `payment_failed`, Mandate ID for
         * `mandate_activated` etc
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
             * ID of the resource to run the simulation against. Must be same type of resource as
             * the simulator that is being run. eg. Payment ID for `payment_failed`, Mandate ID for
             * `mandate_activated` etc
             */
            public Links withResource(String resource) {
                this.resource = resource;
                return this;
            }
        }
    }
}
