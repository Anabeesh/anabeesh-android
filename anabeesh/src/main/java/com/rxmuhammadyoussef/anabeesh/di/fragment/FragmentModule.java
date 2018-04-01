package com.rxmuhammadyoussef.anabeesh.di.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rxmuhammadyoussef.anabeesh.ui.category.CategoryScreen;
import com.rxmuhammadyoussef.anabeesh.ui.home.HomeScreen;
import com.rxmuhammadyoussef.anabeesh.ui.settings.SettingsScreen;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

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
    @Provides
    FragmentManager provideFragmentManager() {
        return fragment.getChildFragmentManager();
    }

    @FragmentScope
    @ForFragment
    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @FragmentScope
    @ForFragment
    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    @FragmentScope
    @Provides
    HomeScreen ProvideHomeScreen() {
        return (HomeScreen) fragment;
    }

    @FragmentScope
    @Provides
    CategoryScreen ProvideCategoryScreen() {
        return (CategoryScreen) fragment;
    }

    @FragmentScope
    @Provides
    SettingsScreen ProvideSettingsScreen() {
        return (SettingsScreen) fragment;
    }
}