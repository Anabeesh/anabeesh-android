package com.rxmuhammadyoussef.anabeesh.di.activity;

import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentComponent;
import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentModule;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.anabeesh.ui.login.LoginActivity;
import com.rxmuhammadyoussef.anabeesh.ui.registration.RegistrationActivity;
import com.rxmuhammadyoussef.anabeesh.ui.splash.SplashActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import dagger.Subcomponent;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link ActivityModule}), and the object which expresses a dependency.
 */

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    FragmentComponent plus(FragmentModule fragmentModule);

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegistrationActivity registrationActivity);

    void inject(HostActivity hostActivity);
}