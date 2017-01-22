package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.anim;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class FABScrollingBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public FABScrollingBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //For SnackBar to push up the Floating Button when it pops up
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

    //For FAB to hide or show on scroll
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target,
                                       int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child,
                directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

    //Animates Fab back to bottom when SnackBar is swiped
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        final float translationY = Math.min(0, parent.getBottom() - child.getBottom());
        child.animate().translationY(translationY).setInterpolator(new DecelerateInterpolator(1)).start();
    }
}
