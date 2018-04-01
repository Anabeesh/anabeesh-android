package com.rxmuhammadyoussef.anabeesh.ui.settings;

import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@FragmentScope
class SettingsPresenter {

    private final ThreadSchedulers threadSchedulers;
    private final UserSessionManager userSessionManager;
    private final CompositeDisposable disposable;
    private final SettingsScreen settingsScreen;

    @Inject
    SettingsPresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                      @ForFragment CompositeDisposable disposable,
                      UserSessionManager userSessionManager,
                      SettingsScreen settingsScreen) {
        this.threadSchedulers = threadSchedulers;
        this.userSessionManager = userSessionManager;
        this.disposable = disposable;
        this.settingsScreen = settingsScreen;
    }

    void onLogoutClick() {
        disposable.add(userSessionManager
                .logout()
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(settingsScreen::onReadyToLogout, Timber::e));
    }
}
