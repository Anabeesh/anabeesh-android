package com.rxmuhammadyoussef.anabeesh.ui.host;

import com.rxmuhammadyoussef.anabeesh.ui.home.HomeFragment;
import com.rxmuhammadyoussef.core.component.activity.BasePresenter;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class HostPresenter extends BasePresenter {

    private final HostScreen hostScreen;

    @Inject
    HostPresenter(HostScreen hostScreen) {
        super(hostScreen);
        this.hostScreen = hostScreen;
    }

    @Override
    protected void onCreate() {
        hostScreen.setLayoutDirection();
        hostScreen.setupToolbar();
        hostScreen.setupNavigationDrawer();
        hostScreen.setFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
    }
}
