package com.rxmuhammadyoussef.core.component.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rxmuhammadyoussef.core.di.CoreActivityInjector;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;
import com.rxmuhammadyoussef.core.util.UiUtil;

import javax.inject.Inject;

import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner, BaseScreen {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    UiUtil uiUtil;
    @Inject
    ResourcesUtil resourcesUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        CoreActivityInjector.getComponent(this)
                .inject(this);
        onCreateActivity();
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        Timber.tag("Muhammad").d("Lifecycle activity: CREATED");
    }

    protected abstract void onCreateActivity();

    @LayoutRes
    protected abstract int getLayout();

    @Override
    public void onStart() {
        super.onStart();
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        Timber.tag("Muhammad").d("Lifecycle activity: STARTED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
        Timber.tag("Muhammad").d("Lifecycle activity: RESUMED");
    }

    @Override
    public void showDefaultMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        uiUtil.getSuccessToast(message)
                .show();
    }

    @Override
    public void showAnnouncementMessage(String message) {
        uiUtil.getAnnouncementToast(message)
                .show();
    }

    @Override
    public void showWarningMessage(String message) {
        uiUtil.getWarningToast(message)
                .show();
    }

    @Override
    public void showErrorMessage(String message) {
        uiUtil.getErrorToast(message)
                .show();
    }

    @Override
    public void showLoadingAnimation() {
        uiUtil.getProgressDialog()
                .show();
    }

    @Override
    public void hideLoadingAnimation() {
        uiUtil.getProgressDialog()
                .dismiss();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public ResourcesUtil getResourcesUtil() {
        return resourcesUtil;
    }

    public UiUtil getUiUtil() {
        return uiUtil;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        Timber.tag("Muhammad").d("Lifecycle activity: DESTROYED");
    }
}
