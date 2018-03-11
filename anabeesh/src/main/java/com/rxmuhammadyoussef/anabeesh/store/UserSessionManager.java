package com.rxmuhammadyoussef.anabeesh.store;

import android.support.annotation.NonNull;

import com.rxmuhammadyoussef.anabeesh.di.application.ApplicationScope;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;

import javax.inject.Inject;

@ApplicationScope
public class UserSessionManager {

    private final PreferencesStore preferencesStore;
    private final UserMapper userMapper;

    @Inject
    UserSessionManager(PreferencesStore preferencesStore, UserMapper userMapper) {
        this.preferencesStore = preferencesStore;
        this.userMapper = userMapper;
    }

    public boolean isSessionActive() {
        return preferencesStore.getCurrentUser()
                .map(userEntity -> userEntity != null)
                .blockingGet();
    }

    @NonNull
    public UserModel getCurrentUser() {
        return preferencesStore.getCurrentUser()
                .map(userMapper::toModel)
                .blockingGet();
    }

    public void logout() {
        preferencesStore.deleteCurrentUser();
    }
}