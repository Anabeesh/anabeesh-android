package com.rxmuhammadyoussef.anabeesh.di.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

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
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}