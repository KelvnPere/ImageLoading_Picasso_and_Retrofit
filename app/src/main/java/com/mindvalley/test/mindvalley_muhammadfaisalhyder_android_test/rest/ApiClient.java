package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.config.AppConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Retrofit Client Class
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    //providing setLenient property to gson converter of Retrofit to avoid Malformed JSON Exception.
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    //for XML based response
    /*
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConfig.baseURL)
            .client(new OkHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
     */

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
