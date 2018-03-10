package com.rxmuhammadyoussef.core.di;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import dagger.Component;

@CoreComponentScope
@Component(modules = {CoreActivityModule.class})
public interface CoreActivityComponent {

    void inject(BaseActivity baseActivity);
}
