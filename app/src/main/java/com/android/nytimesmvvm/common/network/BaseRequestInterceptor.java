package com.android.nytimesmvvm.common.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BaseRequestInterceptor implements Interceptor {

    private final static String TAG = "BaseRequestInterceptor";

    private HashMap<String, String> requestHeaderValues;

    public BaseRequestInterceptor(HashMap<String, String> headerValues) {
        requestHeaderValues = headerValues;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder newBuilder = original.newBuilder();

        if (requestHeaderValues != null && requestHeaderValues.size() > 0) {
            final Set<String> keys = requestHeaderValues.keySet();
            for (final String key : keys) {
                newBuilder.addHeader(key, requestHeaderValues.get(key));
            }
            requestHeaderValues.clear();
        }
        // Customize the request
        Request request = newBuilder
                //TODO check needed default headers
//                .header("Accept", "application/json")
//                .header("Authorization", "auth-token")
//                .header("ContentType", X_CONTENT_TYPE)
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);
        // Customize or return the response
        return response;
    }

    public void setRequestHeaderValues(HashMap<String, String> headers){
        this.requestHeaderValues = headers;
    }
}
