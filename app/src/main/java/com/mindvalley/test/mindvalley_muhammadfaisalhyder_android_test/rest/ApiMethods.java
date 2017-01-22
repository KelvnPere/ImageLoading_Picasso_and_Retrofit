package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest;

import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface to expose REST End points for Retrofit api calls
 */
public interface ApiMethods {

    /**
     * Method to get JSON response from API
     * @return List of ApiResponse Objects
     */
    @GET("raw/wgkJgazE")
    Call<List<ApiResponse>> getJSONResponse();
}
