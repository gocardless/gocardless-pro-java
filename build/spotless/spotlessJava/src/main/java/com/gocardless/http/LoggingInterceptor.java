package com.gocardless.http;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInterceptor implements Interceptor {
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
