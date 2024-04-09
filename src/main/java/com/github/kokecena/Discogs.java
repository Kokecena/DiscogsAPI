package com.github.kokecena;

import com.github.kokecena.service.DatabaseService;
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * A class for interacting with the Discogs API using Retrofit.
 * <p>
 * This class provides methods to build a Retrofit client that communicates with the Discogs API.
 * Different constructors can be used depending on the parameters provided for authentication.
 * <p>
 * Example Usage:
 * <p>
 * <code>Discogs discogsClient = new Discogs("MyApp/1.0");</code>
 * <p>
 * <code>DatabaseService databaseService = discogsClient.databaseService();</code>
 * <p>
 * // Make API calls to Discogs using the database service
 */
public class Discogs {
    private static final Logger log = LoggerFactory.getLogger(Discogs.class);
    private static final String BASE_URL = "https://api.discogs.com/";
    private final Retrofit retrofit;

    /**
     * Constructor for Discogs class.
     *
     * @param userAgent      User agent string identifying the client.
     * @param consumerKey    Consumer key for authentication.
     * @param consumerSecret Consumer secret for authentication.
     * @param accessToken    Access token for authentication.
     * @throws IllegalArgumentException If required parameters are missing.
     */
    private Discogs(String userAgent, String consumerKey, String consumerSecret, String accessToken) {
        if (userAgent == null || userAgent.isBlank()) {
            throw new IllegalArgumentException("User agent must have a value.");
        }
        if (accessToken == null || accessToken.isBlank()) {
            if (consumerKey == null || consumerKey.isBlank()) {
                throw new IllegalArgumentException("Consumer key must have a value.");
            }
            if (consumerSecret == null || consumerSecret.isBlank()) {
                throw new IllegalArgumentException("Consumer secret must have a value.");
            }
        }
        if (consumerKey != null && !consumerKey.isBlank() && consumerSecret != null && !consumerSecret.isBlank() || accessToken != null && !accessToken.isBlank()) {
            retrofit = buildRetrofitService(userAgent, consumerKey, consumerSecret, accessToken);
        } else {
            throw new IllegalArgumentException("Access token must have a value.");
        }

    }

    /**
     * Constructor for Discogs class.
     *
     * @param userAgent      User agent string identifying the client.
     * @param consumerKey    Consumer key for authentication.
     * @param consumerSecret Consumer secret for authentication.
     * @throws IllegalArgumentException If required parameters are missing.
     */
    public Discogs(String userAgent, String consumerKey, String consumerSecret) {
        this(userAgent, consumerKey, consumerSecret, null);
    }

    /**
     * Constructor for Discogs class.
     *
     * @param userAgent   User agent string identifying the client.
     * @param accessToken Access token for authentication.
     * @throws IllegalArgumentException If required parameters are missing.
     */
    public Discogs(String userAgent, String accessToken) {
        this(userAgent, null, null, accessToken);
    }

    /**
     * Constructor for Discogs class.
     *
     * @param userAgent User agent string identifying the client.
     * @throws IllegalArgumentException If required parameters are missing.
     */
    public Discogs(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            throw new IllegalArgumentException("User agent must have a value.");
        }
        this.retrofit = buildRetrofitService(userAgent, null, null, null);
    }

    /**
     * Build the Retrofit service with the specified parameters.
     */
    private Retrofit buildRetrofitService(String userAgent, String consumerKey, String consumerSecret, String accessToken) {
        boolean notHasKeys = (consumerKey == null || consumerKey.isBlank() || consumerSecret == null || consumerSecret.isBlank()) && (accessToken == null || accessToken.isBlank());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::info);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptorDelay = chain -> {
            try {
                TimeUnit.MILLISECONDS.sleep(notHasKeys ? 2400L : 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return chain.proceed(chain.request());
        };
        Interceptor interceptorHeaders;
        if (notHasKeys) {
            interceptorHeaders = chain -> {
                Request request = chain.request();
                Request requestWithUserAgent = request.newBuilder()
                        .addHeader("User-Agent", userAgent)
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(requestWithUserAgent);
            };
        } else {
            loggingInterceptor.redactHeader("Authorization");
            String authorization = accessToken != null ? String.format("Discogs token=%s", accessToken) :
                    String.format("Discogs key=%s, secret=%s", consumerKey, consumerSecret);
            interceptorHeaders = chain -> {
                Request request = chain.request();
                Request requestWithUserAgent = request.newBuilder()
                        .addHeader("User-Agent", userAgent)
                        .addHeader("Authorization", authorization)
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(requestWithUserAgent);
            };
        }
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptorDelay)
                .addInterceptor(interceptorHeaders);
        try {
            TrustManager[] trustAllCerts = {new X509Manager()};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            okHttpClient.sslSocketFactory(sslContext.getSocketFactory(), new X509Manager())
                    .hostnameVerifier((hostName, sslSession) -> true);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("{}", e.getMessage(), e);
        }
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    /**
     * Get the DatabaseService instance for making API calls related to the Discogs database.
     *
     * @return DatabaseService instance.
     */
    public DatabaseService databaseService() {
        return retrofit.create(DatabaseService.class);
    }


    private class X509Manager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}
