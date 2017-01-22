package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test;

import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.model.ApiResponse;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest.ApiClient;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest.ApiMethods;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void test_RetrofitIsInitialized() throws Exception {
        ApiMethods createApiClient = ApiClient.getApiClient().create(ApiMethods.class);

        Call<List<ApiResponse>> callApi = createApiClient.getJSONResponse();
        assertNotNull(callApi);
    }
}