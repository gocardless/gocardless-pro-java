package com.gocardless.services;

import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.CustomerNotification;

import com.google.common.collect.ImmutableMap;

/**
 * Service class for working with customer notification resources.
 *
 * Customer Notifications represent the notification which is due to be sent to a customer
 * after an event has happened. The event, the resource and the customer to be notified
 * are all identified in the `links` property.
 * 
 * Note that these are ephemeral records - once the notification has been actioned in some
 * way, it is no longer visible using this API.
 * 
 */
public class CustomerNotificationService {
    private final HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#customerNotifications() }.
     */
    public CustomerNotificationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * "Handling" a notification means that you have sent the notification yourself (and
     * don't want GoCardless to send it).
     * If the notification has already been actioned, or the deadline to notify has passed,
     * this endpoint will return an `already_actioned` error and you should not take
     * further action.
     * 
     */
    public CustomerNotificationHandleRequest handle(String identity) {
        return new CustomerNotificationHandleRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CustomerNotificationService#handle }.
     *
     * "Handling" a notification means that you have sent the notification yourself (and
     * don't want GoCardless to send it).
     * If the notification has already been actioned, or the deadline to notify has passed,
     * this endpoint will return an `already_actioned` error and you should not take
     * further action.
     * 
     */
    public static final class CustomerNotificationHandleRequest extends
            PostRequest<CustomerNotification> {
        @PathParam
        private final String identity;

        private CustomerNotificationHandleRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        public CustomerNotificationHandleRequest withHeader(String headerName, String headerValue) {
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
            return "customer_notifications/:identity/actions/handle";
        }

        @Override
        protected String getEnvelope() {
            return "customer_notifications";
        }

        @Override
        protected Class<CustomerNotification> getResponseClass() {
            return CustomerNotification.class;
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
