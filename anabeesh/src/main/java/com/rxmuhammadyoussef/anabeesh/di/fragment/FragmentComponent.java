package com.rxmuhammadyoussef.anabeesh.di.fragment;

import com.rxmuhammadyoussef.anabeesh.ui.category.CategoryFragment;
import com.rxmuhammadyoussef.anabeesh.ui.home.HomeFragment;
import com.rxmuhammadyoussef.anabeesh.ui.interests.InterestsFragment;
import com.rxmuhammadyoussef.anabeesh.ui.settings.SettingsFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import dagger.Subcomponent;

/**
 This interface is used by dagger to generate the code that defines the connection between the provider of objects
 (i.e. {@link FragmentModule}), and the object which expresses a dependency.
 */

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(HomeFragment homeFragment);

    void inject(InterestsFragment interestsFragment);

    void inject(CategoryFragment categoryFragment);

    void inject(SettingsFragment settingsFragment);
}