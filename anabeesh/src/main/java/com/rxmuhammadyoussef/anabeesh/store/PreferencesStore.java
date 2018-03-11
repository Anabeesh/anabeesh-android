package com.rxmuhammadyoussef.anabeesh.store;

import com.google.gson.Gson;
import com.rxmuhammadyoussef.anabeesh.di.application.ApplicationScope;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserEntity;
import com.rxmuhammadyoussef.core.util.PreferencesUtil;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

@ApplicationScope
class PreferencesStore {

    static final String KEY_USER = "UserEntityPrefKey";
    private final PreferencesUtil preferencesUtil;

    @Inject
    PreferencesStore(PreferencesUtil preferencesUtil) {
        this.preferencesUtil = preferencesUtil;
    }

    Completable insertOrUpdateUser(UserEntity user) {
        return Completable.create(emitter -> {
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            preferencesUtil.saveOrUpdateString(KEY_USER, userJson);
            emitter.onComplete();
        });
    }

    Single<UserEntity> copyOrUpdateUser(UserEntity user) {
        return Single.create(emitter -> {
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            preferencesUtil.saveOrUpdateString(KEY_USER, userJson);
            emitter.onSuccess(user);
        });
    }

    Single<UserEntity> getCurrentUser() {
        return Single.create(emitter -> {
            Gson gson = new Gson();
            String userJson = preferencesUtil.getString(KEY_USER);
            UserEntity user = gson.fromJson(userJson, UserEntity.class);
            emitter.onSuccess(user);
        });
    }

    Completable deleteCurrentUser() {
        return Completable.create(emitter -> {
            preferencesUtil.delete(KEY_USER);
            emitter.onComplete();
        });
    }
}
