package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.util;

import android.app.Application;

import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.config.AppConfig;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Utility class to handle Picasso Singleton Object for efficient Cache handling mechanism
 */
public class PicassoHandler extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, AppConfig.maxCacheSize));
        Picasso built = builder.build();
        Picasso.setSingletonInstance(built);
    }
}

