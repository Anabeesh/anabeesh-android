package com.rxmuhammadyoussef.core.di.activity;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import dagger.Component;

@Component(modules = {CoreActivityModule.class})
public interface CoreActivityComponent {

    void inject(BaseActivity baseActivity);
}
