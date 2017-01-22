package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.adapter.ClickListener;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.adapter.ImageAdapter;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.model.ApiResponse;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest.ApiClient;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.rest.ApiMethods;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.util.EmailUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.SDK;

public class MainActivity extends AppCompatActivity implements ClickListener,View.OnClickListener,
       SwipeRefreshLayout.OnRefreshListener{

    private CoordinatorLayout mCoordinatorLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImageAdapter imageAdapter;
    private List<String> imagesUrlList;
    private List<String> usersNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        prepareResponseCall();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayoutContainer);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        }

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void prepareResponseCall() {

        imagesUrlList = new ArrayList<>();
        usersNameList = new ArrayList<>();

        Handler delayer = new Handler();
        delayer.postDelayed(new Runnable() {
            @Override
            public void run() {

                ApiMethods createApiClient = ApiClient.getApiClient().create(ApiMethods.class);

                Call<List<ApiResponse>> callApi = createApiClient.getJSONResponse();
                callApi.enqueue(new Callback<List<ApiResponse>>() {
                    @Override
                    public void onResponse(Call<List<ApiResponse>> successCall, Response<List<ApiResponse>> response) {
                        if (response != null) {
                            for (ApiResponse tempObj : response.body()) {
                                imagesUrlList.add(tempObj.getUser().getProfile_image().getLarge());
                                usersNameList.add(tempObj.getUser().getName());
                            }
                            imageAdapter = new ImageAdapter(getApplicationContext(), imagesUrlList);
                            imageAdapter.setListener(MainActivity.this);

                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                            mRecyclerView.setAdapter(imageAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiResponse>> failedCall, Throwable exception) {
                        Snackbar.make(mCoordinatorLayout, "Some error in fetching data", Snackbar.LENGTH_SHORT).show();
                        Log.d("errors : ", exception.toString());
                    }
                });
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                String [] recipient = {"mfaisalhyder@gmail.com"};
                String subject = "Feedback";
                String content = "Hello, this is Faisal, i am looking forward to your kind response.";
                EmailUtility.sendToGMail(recipient, subject, content, getApplicationContext());
        }
    }

    @Override
    public void itemClicked(View view, int position){
        Snackbar.make(mCoordinatorLayout, "Hello, I am "+ usersNameList.get(position), Snackbar.LENGTH_LONG).show();
    }

   @Override
    public void onRefresh() {

        if(imageAdapter != null) {
            imageAdapter = new ImageAdapter(getApplicationContext(), imagesUrlList);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mRecyclerView.setAdapter(imageAdapter);
        }
        else Snackbar.make(mCoordinatorLayout, "No data available at the moment", Snackbar.LENGTH_SHORT).show();

        mSwipeRefreshLayout.setRefreshing(false);
    }
}