package com.rxmuhammadyoussef.anabeesh.store;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserEntity;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.util.PreferencesUtil;

import javax.inject.Inject;

import static com.rxmuhammadyoussef.anabeesh.store.PreferencesStore.KEY_USER;

@ApplicationScope
public class UserSessionManager {

    private final PreferencesUtil preferencesUtil;
    private final UserMapper userMapper;

    @Inject
    UserSessionManager(PreferencesUtil preferencesUtil, UserMapper userMapper) {
        this.preferencesUtil = preferencesUtil;
        this.userMapper = userMapper;
    }

    public boolean isSessionActive() {
        Gson gson = new Gson();
        String userJson = preferencesUtil.getString(KEY_USER);
        return gson.fromJson(userJson, UserEntity.class) != null;
    }

    @Nullable
    public UserModel getCurrentUser() {
        Gson gson = new Gson();
        String userJson = preferencesUtil.getString(KEY_USER);
        UserEntity userEntity = gson.fromJson(userJson, UserEntity.class);
        UserModel userModel = null;
        if (userEntity != null) {
            userModel = userMapper.toModel(userEntity);
        }
        return userModel;
    }

    public void logout() {
        preferencesUtil.delete(KEY_USER);
    }
}