package com.rxmuhammadyoussef.anabeesh.store;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.LoginRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.RegisterRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.core.util.Preconditions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ActivityScope
public class AuthenticationRepo implements LifecycleObserver {

    private final UserMapper userMapper;
    private final CompositeDisposable disposable;
    private final WebServiceStore webServiceStore;
    private final PreferencesStore preferencesStore;
    private final ThreadSchedulers threadSchedulers;

    @Inject
    AuthenticationRepo(@IOThread ThreadSchedulers threadSchedulers,
                       @ForActivity CompositeDisposable disposable,
                       PreferencesStore preferencesStore,
                       WebServiceStore webServiceStore,
                       UserMapper userMapper,
                       @ForActivity Lifecycle lifecycle) {
        lifecycle.addObserver(this);
        this.threadSchedulers = threadSchedulers;
        this.preferencesStore = preferencesStore;
        this.webServiceStore = webServiceStore;
        this.userMapper = userMapper;
        this.disposable = disposable;
    }

    public void login(LoginRequestBody loginRequestBody, OperationListener<UserModel> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener cannot be null");
        disposable.add(
                webServiceStore.login(Preconditions.requireNonNull(loginRequestBody, "request body cannot be null"))
                        .map(userMapper::toEntity)
                        .flatMap(preferencesStore::copyOrUpdateUser)
                        .map(userMapper::toModel)
                        .subscribeOn(threadSchedulers.workerThread())
                        .observeOn(threadSchedulers.mainThread())
                        .subscribe(operationListener::onSuccess, operationListener::onError));
    }

    public void register(RegisterRequestBody registerRequestBody, OperationListener<UserModel> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener cannot be null");
        disposable.add(
                webServiceStore.register(Preconditions.requireNonNull(registerRequestBody, "request body can not be null"))
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
