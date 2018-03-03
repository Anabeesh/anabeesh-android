package com.rxmuhammadyoussef.core.di;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreActivityModule.class})
public interface CoreActivityComponent {

    void inject(BaseActivity baseActivity);
}
