package com.rxmuhammadyoussef.core.component.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.rxmuhammadyoussef.core.R;
import com.rxmuhammadyoussef.core.di.CoreActivityModule;
import com.rxmuhammadyoussef.core.di.DaggerCoreActivityComponent;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;
import com.rxmuhammadyoussef.core.util.UiUtil;
import com.rxmuhammadyoussef.core.util.permission.PermissionUtil;

import javax.inject.Inject;

import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner, BaseScreen {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    UiUtil uiUtil;
    @Inject
    ResourcesUtil resourcesUtil;
    @Inject
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        DaggerCoreActivityComponent.builder()
                .coreActivityModule(new CoreActivityModule(this))
                .build();
        onCreateActivity();
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        Timber.tag("Muhammad").d("Lifecycle activity: CREATED");
    }

    protected abstract void onCreateActivity();

    @LayoutRes
    protected abstract int getLayout();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
    protected void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        Timber.tag("Muhammad").d("Lifecycle activity: DESTROYED");
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
        uiUtil.getProgressDialog(getString(R.string.please_wait))
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

    @Override
    public PermissionUtil getPermissionUtil() {
        return permissionUtil;
    }

    public UiUtil getUiUtil() {
        return uiUtil;
    }
}
