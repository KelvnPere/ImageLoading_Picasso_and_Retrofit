package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Animation Handling class for Recycler View
 */
public class RecyclerViewAnimation {

    private final static int FADING_DURATION = 1000;

    public static void setFadingAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADING_DURATION);
        view.startAnimation(anim);
    }
}