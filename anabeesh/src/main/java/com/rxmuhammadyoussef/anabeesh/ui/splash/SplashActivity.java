package com.rxmuhammadyoussef.anabeesh.ui.splash;

import android.content.Intent;
import android.os.Handler;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.anabeesh.ui.login.LoginActivity;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    UserSessionManager userSessionManager;

    @Override
    protected void onCreateActivityComponents() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        new Handler().postDelayed(() -> {
            if (userSessionManager.isSessionActive()) {
                startActivity(new Intent(this, HostActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }
}