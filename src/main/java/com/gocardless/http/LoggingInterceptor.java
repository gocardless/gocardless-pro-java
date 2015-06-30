package com.gocardless.http;

import java.io.IOException;

import com.google.common.base.Stopwatch;

import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggingInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Response response = chain.proceed(request);
        stopwatch.stop();
        LOGGER.info("API request [{}] [{}] returned [{}] (took [{}])", request.method(),
                request.url(), response.code(), stopwatch);
        return response;
    }
}
