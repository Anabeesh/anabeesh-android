package com.rxmuhammadyoussef.core.di;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

public final class CoreActivityInjector {

    private static CoreActivityComponent component;

    private CoreActivityInjector() {
    }

    public static CoreActivityComponent getComponent(BaseActivity baseActivity) {
        if (component != null) {
            initComponent(baseActivity);
        }
        return component;
    }

    private static void initComponent(BaseActivity baseActivity) {
        component = DaggerCoreActivityComponent
                .builder()
                .coreActivityModule(new CoreActivityModule(baseActivity))
                .build();
    }
}
