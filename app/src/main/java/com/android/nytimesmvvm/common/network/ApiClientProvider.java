package com.android.nytimesmvvm.common.network;

import android.text.TextUtils;

import com.android.nytimesmvvm.BuildConfig;
import com.android.nytimesmvvm.common.error.BaseURLEmptyException;
import com.android.nytimesmvvm.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Generic provider to give retrofit instance with JSON or SOAP request handler.
 */

public class ApiClientProvider {

    private String baseUrl;
    private Retrofit retrofitClient = null;
    protected BaseRequestInterceptor mBaseRequestInterceptor;
    private Gson mGson;

    public ApiClientProvider(String baseUrl, BaseRequestInterceptor mBaseRequestInterceptor) {

        this.baseUrl = baseUrl;
        if(mGson == null)
            mGson = createGson();

        if (mBaseRequestInterceptor != null) {
            this.mBaseRequestInterceptor = mBaseRequestInterceptor;
        } else {
            this.mBaseRequestInterceptor = new BaseRequestInterceptor(null);
        }
    }

    public ApiClientProvider(String baseUrl, BaseRequestInterceptor mBaseRequestInterceptor, Headers headers) {
        this.baseUrl = baseUrl;
        if(mGson == null)
            mGson = createGson();

        if (mBaseRequestInterceptor != null) {
            this.mBaseRequestInterceptor = mBaseRequestInterceptor;
        } else {
            this.mBaseRequestInterceptor = new BaseRequestInterceptor(null);
        }
        int count = headers.size();
        if(count > 0) {
            final HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < count; i++) {
                map.put(headers.name(i), headers.value(i));
            }
            this.mBaseRequestInterceptor.setRequestHeaderValues(map);
        }

    }



    private Retrofit getSOAPClient(String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(mBaseRequestInterceptor);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);

        httpClient = getUnsafeOkHttpClient(httpClient);

        OkHttpClient okHttpClient = httpClient.build();

        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                    .client(okHttpClient)
                    .build();
        }
        return retrofitClient;
    }

    private Retrofit getRestClient(String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(mBaseRequestInterceptor);

        httpClient = getUnsafeOkHttpClient(httpClient);

        OkHttpClient okHttpClient = httpClient.build();

        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .build();
        }
        return retrofitClient;
    }

    public Retrofit provideApiClient(boolean isSoapRequest) throws BaseURLEmptyException {
        if (!TextUtils.isEmpty(baseUrl)) {
            if (isSoapRequest) {
                return getSOAPClient(baseUrl);
            } else {
                return getRestClient(baseUrl);
            }
        } else {
            throw new BaseURLEmptyException();
        }
    }


    /**
     * Temporory method to okHttpClient builde whcih will ignore SSl certificate. We are getting SSL error on UAT so ignore cert.
     * production works fine so we will not call this method.
     * @param build
     * @return
     */
    private static OkHttpClient.Builder getUnsafeOkHttpClient(OkHttpClient.Builder build) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = build;
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            if(BuildConfig.DEBUG){
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

//            OkHttpClient okHttpClient = builder.build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Gson createGson(){
       /* mGson = new GsonBuilder()
                .setLenient()
                .create();*/
        Gson mGson = new GsonBuilder()
                .registerTypeAdapter(Result.class, new CustomDesirializer())
                .create();
        return mGson;
    }


}
