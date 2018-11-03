package com.ramapps.ramplaces.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import com.ramapps.ramplaces.BuildConfig;
import com.ramapps.ramplaces.app.data.PlacesApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Ramsheed.A on 11/03/2018.
 * Rest Service implementation class
 */

public class RestService {

    private static final int MINUTES = 5;
    private static final int CONNECTION_TIMEOUT = 60000 * MINUTES;
    private static final int READ_TIMEOUT = 60000 * MINUTES;

    /* Module APIs Settings
     * Main service APIs settings for common moudles
     */
    public static PlacesApi getPlacesAPI() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        JacksonConverterFactory.create(objectMapper))
                .baseUrl(BuildConfig.API_URL)
                .client(getClient())
                .build();
        return retrofit.create(PlacesApi.class);
    }

    /*
    HTTP Client Configuration Block
    Timeout and Interceptor module integration
     */
    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

    }
}