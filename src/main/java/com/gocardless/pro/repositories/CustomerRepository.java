package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Customer;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomerRepository {
    private HttpClient httpClient;

    public CustomerRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }



    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");

    }



    public CustomerListRequest list() throws IOException {
        return new CustomerListRequest(httpClient

        );

    }



    public CustomerGetRequest get(String identity) throws IOException {
        return new CustomerGetRequest(httpClient

        , identity

        );

    }



    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");

    }



    public static final class CustomerListRequest extends ListRequest<Customer> {



        private String after;

        public CustomerListRequest withAfter(String after) {
            this.after = after;
            return this;
        }



        private String before;

        public CustomerListRequest withBefore(String before) {
            this.before = before;
            return this;
        }



        private Object createdAt;

        public CustomerListRequest withCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
            return this;
        }



        private Integer limit;

        public CustomerListRequest withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }



        private CustomerListRequest(HttpClient httpClient

        ) {
            super(httpClient, "/customers", "customers",

            new TypeToken<List<Customer>>() {}

            );


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

            if (createdAt != null) {
                params.put("created_at", createdAt);
            }

            if (limit != null) {
                params.put("limit", limit);
            }


            return params.build();
        }

    }



    public static final class CustomerGetRequest extends GetRequest<Customer> {

        private final String identity;



        private CustomerGetRequest(HttpClient httpClient

        , String identity

        ) {
            super(httpClient, "/customers/:identity", "customers",

            Customer.class

            );



            this.identity = identity;

        }


        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();


            params.put("identity", identity);


            return params.build();
        }



    }



}
