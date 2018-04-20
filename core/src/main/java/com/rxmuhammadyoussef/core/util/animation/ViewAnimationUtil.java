package com.rxmuhammadyoussef.core.util.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.rxmuhammadyoussef.core.util.Preconditions;

public final class ViewAnimationUtil {

    public static final int DEFAULT_RADIUS = 1000;

    private ViewAnimationUtil() {}

    public static void circularReveal(View view, float xCoordinate, float yCoordinate, int finalRadius) {
        Preconditions.checkNonNull(view);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) xCoordinate, (int) yCoordinate, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void circularHide(View view, float xCoordinate, float yCoordinate, int finalRadius) {
        Preconditions.checkNonNull(view);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) xCoordinate, (int) yCoordinate, finalRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        anim.start();
    }
}
