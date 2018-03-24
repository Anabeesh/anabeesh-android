package com.rxmuhammadyoussef.core.di.activity;

import android.content.Context;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreActivityModule {

    private final BaseActivity baseActivity;

    public CoreActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    BaseActivity providesBaseActivity() {
        return baseActivity;
    }

    @Provides
    Context provideBaseActivityContext() {
        return baseActivity;
    }
}
