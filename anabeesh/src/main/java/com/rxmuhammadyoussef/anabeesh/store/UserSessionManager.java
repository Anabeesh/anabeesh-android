package com.rxmuhammadyoussef.anabeesh.store;

import com.google.gson.Gson;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserEntity;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.util.PreferencesUtil;

import javax.inject.Inject;

import io.reactivex.Completable;

import static com.rxmuhammadyoussef.anabeesh.store.PreferencesStore.KEY_USER;

@ApplicationScope
public class UserSessionManager {

    private final PreferencesUtil preferencesUtil;
    private final RealmStore realmStore;
    private final UserMapper userMapper;

    @Inject
    UserSessionManager(PreferencesUtil preferencesUtil, RealmStore realmStore, UserMapper userMapper) {
        this.preferencesUtil = preferencesUtil;
        this.realmStore = realmStore;
        this.userMapper = userMapper;
    }

    public boolean isSessionActive() {
        Gson gson = new Gson();
        String userJson = preferencesUtil.getString(KEY_USER);
        return gson.fromJson(userJson, UserEntity.class) != null;
    }

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

    public Completable logout() {
        return realmStore.clearDatabase()
                .doOnComplete(preferencesUtil::deleteAll);
    }
}