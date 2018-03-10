package com.rxmuhammadyoussef.anabeesh.di.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects to {@link FragmentScope} annotated classes
 */

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @FragmentScope
    @ForFragment
    @Provides
    Context provideFragmentContext() {
        return fragment.getContext();
    }

    @FragmentScope
    @ForFragment
    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }
}