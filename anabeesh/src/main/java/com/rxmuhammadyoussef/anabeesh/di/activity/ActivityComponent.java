package com.rxmuhammadyoussef.anabeesh.di.activity;

import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentComponent;
import com.rxmuhammadyoussef.anabeesh.di.fragment.FragmentModule;

import dagger.Subcomponent;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link ActivityModule}), and the object which expresses a dependency.
 */

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    FragmentComponent plus(FragmentModule fragmentModule);
}