package com.rxmuhammadyoussef.anabeesh.ui.host;

import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserViewModel;
import com.rxmuhammadyoussef.anabeesh.ui.home.HomeFragment;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class HostActivityPresenter extends BaseActivityPresenter {

    private final UserMapper userMapper;
    private final HostActivityScreen hostScreen;
    private final UserSessionManager userSessionManager;

    @Inject
    HostActivityPresenter(UserMapper userMapper, HostActivityScreen hostScreen, UserSessionManager userSessionManager) {
        super(hostScreen);
        this.userMapper = userMapper;
        this.hostScreen = hostScreen;
        this.userSessionManager = userSessionManager;
    }

    @Override
    protected void onCreate() {
        UserViewModel userViewModel = userMapper.toViewModel(userSessionManager.getCurrentUser());
        hostScreen.setLayoutDirection();
        hostScreen.setupToolbar();
        hostScreen.setupNavigationDrawer(userViewModel);
        hostScreen.setFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
    }
}
