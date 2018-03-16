package com.rxmuhammadyoussef.anabeesh.di.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.rxmuhammadyoussef.anabeesh.ui.host.HostScreen;
import com.rxmuhammadyoussef.anabeesh.ui.login.LoginScreen;
import com.rxmuhammadyoussef.anabeesh.ui.registration.RegistrationScreen;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.util.UiUtil;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 This class is responsible for providing the requested objects to {@link ActivityScope} annotated classes
 */

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {this.activity = activity;}

    @ActivityScope
    @Provides
    BaseActivity provideActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @ActivityScope
    @Provides
    Lifecycle provideLifCycle() {
        return activity.getLifecycle();
    }

    @ActivityScope
    @Provides
    UiUtil providesUiUtil() {
        return new UiUtil(activity);
    }

    @ActivityScope
    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ActivityScope
    @Provides
    LoginScreen providesLoginScreen() {
        return (LoginScreen) activity;
    }

    @ActivityScope
    @Provides
    RegistrationScreen providesRegistrationScreen() {
        return (RegistrationScreen) activity;
    }

    @ActivityScope
    @Provides
    HostScreen providesHostScreen() {
        return (HostScreen) activity;
    }
}