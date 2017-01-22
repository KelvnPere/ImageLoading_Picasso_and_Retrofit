package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.adapter;

import android.view.View;

/**
 * Interface to handle RecyclerView item Clicks
 */
public interface ClickListener {

    /**
     * view to be clicked is passed with its position
     * @param view
     * @param position
     */
    void itemClicked(View view, int position);
}