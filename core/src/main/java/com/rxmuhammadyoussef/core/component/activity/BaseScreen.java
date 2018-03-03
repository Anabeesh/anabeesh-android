package com.rxmuhammadyoussef.core.component.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;

import com.rxmuhammadyoussef.core.util.ResourcesUtil;

public interface BaseScreen extends UIHostComponentProvider {

    void showDefaultMessage(String message);

    void showSuccessMessage(String message);

    void showAnnouncementMessage(String message);

    void showWarningMessage(String message);

    void showErrorMessage(String message);

    void showLoadingAnimation();

    void hideLoadingAnimation();

    Intent getIntent();

    Lifecycle getLifecycle();

    ResourcesUtil getResourcesUtil();
}
