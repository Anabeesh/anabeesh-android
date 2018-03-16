package com.rxmuhammadyoussef.anabeesh.di.application;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityComponent;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import dagger.Component;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link AppModule}), and the object which expresses a dependency.
 */

@ApplicationScope
@Component(modules = {AppModule.class, SchedulersModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(AnabeeshApplication anabeeshApplication);
}