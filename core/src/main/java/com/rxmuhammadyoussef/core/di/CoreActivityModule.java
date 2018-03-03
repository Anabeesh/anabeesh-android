package com.rxmuhammadyoussef.core.di;

import android.content.Context;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class CoreActivityModule {

    private final BaseActivity baseActivity;

    CoreActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Singleton
    @Provides
    BaseActivity providesBaseActivity() {
        return baseActivity;
    }

    @Singleton
    @Provides
    Context provideBaseActivityContext() {
        return baseActivity;
    }
}
