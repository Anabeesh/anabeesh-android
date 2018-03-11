package com.rxmuhammadyoussef.anabeesh.store;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityScope;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.util.Preconditions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

@ActivityScope
public class AuthenticationRepo implements LifecycleObserver {

    private final UserMapper userMapper;
    private final CompositeDisposable disposable;
    private final WebServiceStore webServiceStore;
    private final PreferencesStore preferencesStore;
    private final ThreadSchedulers threadSchedulers;

    @Inject
    AuthenticationRepo(@IOThread ThreadSchedulers threadSchedulers,
                       PreferencesStore preferencesStore,
                       WebServiceStore webServiceStore,
                       CompositeDisposable disposable,
                       UserMapper userMapper,
                       Lifecycle lifecycle) {
        lifecycle.addObserver(this);
        this.threadSchedulers = threadSchedulers;
        this.preferencesStore = preferencesStore;
        this.webServiceStore = webServiceStore;
        this.userMapper = userMapper;
        this.disposable = disposable;
    }

    public void login(String email, String password, OperationListener<UserModel> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener cannot be null");
        disposable.add(
                webServiceStore.login(
                        Preconditions.requireStringNotEmpty(email, "email is required non empty or null"),
                        Preconditions.requireStringNotEmpty(password, "password is required non empty or null"))
                        .map(userMapper::toEntity)
                        .flatMap(preferencesStore::copyOrUpdateUser)
                        .map(userMapper::toModel)
                        .subscribeOn(threadSchedulers.workerThread())
                        .observeOn(threadSchedulers.mainThread())
                        .subscribe(operationListener::onSuccess, operationListener::onError));
    }

    public void register(String email, String password, String firstName, String lastName, OperationListener<UserModel> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener cannot be null");
        disposable.add(
                webServiceStore.register(
                        Preconditions.requireStringNotEmpty(email, "email is required non empty or null"),
                        Preconditions.requireStringNotEmpty(password, "password is required non empty or null"),
                        Preconditions.requireStringNotEmpty(firstName, "firstName is required non empty or null"),
                        Preconditions.requireStringNotEmpty(lastName, "lastName is required non empty or null"))
                        .map(userMapper::toEntity)
                        .flatMap(preferencesStore::copyOrUpdateUser)
                        .map(userMapper::toModel)
                        .subscribeOn(threadSchedulers.workerThread())
                        .observeOn(threadSchedulers.mainThread())
                        .subscribe(operationListener::onSuccess, operationListener::onError));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        disposable.clear();
    }
}
