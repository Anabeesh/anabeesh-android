package com.rxmuhammadyoussef.core.di;

import android.content.Context;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

@Module
class CoreActivityModule {

    private final BaseActivity baseActivity;

    CoreActivityModule(BaseActivity baseActivity) {
        this.baseActivity = new WeakReference<>(baseActivity).get();
    }

    @Provides
    @CoreComponentScope
    BaseActivity providesBaseActivity() {
        return baseActivity;
    }

    @Provides
    @CoreComponentScope
    Context provideBaseActivityContext() {
        return baseActivity;
    }
}
