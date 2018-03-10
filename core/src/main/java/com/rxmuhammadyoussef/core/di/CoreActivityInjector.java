package com.rxmuhammadyoussef.core.di;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import java.lang.ref.WeakReference;

public final class CoreActivityInjector {

    private static WeakReference<CoreActivityComponent> component;

    private CoreActivityInjector() {
    }

    public static CoreActivityComponent getComponent(BaseActivity baseActivity) {
        if (component == null || component.get() == null) {
            initComponent(baseActivity);
        }
        return component.get();
    }

    private static void initComponent(BaseActivity baseActivity) {
        component = new WeakReference<>(
                DaggerCoreActivityComponent
                        .builder()
                        .coreActivityModule(new CoreActivityModule(baseActivity))
                        .build());
    }
}
