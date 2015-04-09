package com.gocardless.pro.repositories;

import com.gocardless.pro.http.GetRequest;
import com.gocardless.pro.http.HttpClient;
import com.gocardless.pro.http.ListRequest;
import com.gocardless.pro.resources.Refund;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RefundRepository {
    private HttpClient httpClient;

    public RefundRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }



    public void create() throws IOException {
        throw new IllegalStateException("Not implemented!");

    }



    public RefundListRequest list() throws IOException {
        return new RefundListRequest(httpClient

        );

    }



    public RefundGetRequest get(String identity) throws IOException {
        return new RefundGetRequest(httpClient

        , identity

        );

    }



    public void update(String identity) throws IOException {
        throw new IllegalStateException("Not implemented!");

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



        private RefundListRequest(HttpClient httpClient

        ) {
            super(httpClient, "/refunds", "refunds",

            new TypeToken<List<Refund>>() {}

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

            if (limit != null) {
                params.put("limit", limit);
            }

            if (payment != null) {
                params.put("payment", payment);
            }


            return params.build();
        }

    }



    public static final class RefundGetRequest extends GetRequest<Refund> {

        private final String identity;



        private RefundGetRequest(HttpClient httpClient

        , String identity

        ) {
            super(httpClient, "/refunds/:identity", "refunds",

            Refund.class

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
