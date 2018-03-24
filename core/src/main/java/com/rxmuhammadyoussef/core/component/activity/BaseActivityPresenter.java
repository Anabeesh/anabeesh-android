package com.rxmuhammadyoussef.core.component.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.rxmuhammadyoussef.core.util.permission.PermissionUtil;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class BaseActivityPresenter implements LifecycleObserver {

    private final BaseActivityScreen baseActivityScreen;

    @Inject
    protected BaseActivityPresenter(BaseActivityScreen baseActivityScreen) {
        this.baseActivityScreen = baseActivityScreen;
        baseActivityScreen.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_CREATE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        Timber.tag("Muhammad").d("Lifecycle presenter: ON_DESTROY");
    }

    protected Intent getIntent() {
        return baseActivityScreen.getIntent();
    }

    protected Lifecycle getLifeCycle() {
        return baseActivityScreen.getLifecycle();
    }

    protected ResourcesUtil getResourcesUtil() {
        return baseActivityScreen.getResourcesUtil();
    }

    protected PermissionUtil getPermissionUtil() {
        return baseActivityScreen.getPermissionUtil();
    }
}
