package com.gocardless.pro.services;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Helper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HelperService {
    private HttpClient httpClient;

    public HelperService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void mandate() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }

    public void modulusCheck() throws IOException {
        throw new IllegalStateException("Not implemented!");
    }
}
