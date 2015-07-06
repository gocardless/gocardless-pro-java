package com.gocardless.services;

import com.gocardless.http.*;

/**
 * Service class for working with helper resources.
 *
 * 
 */
public class HelperService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#helpers() }.
     */
    public HelperService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
