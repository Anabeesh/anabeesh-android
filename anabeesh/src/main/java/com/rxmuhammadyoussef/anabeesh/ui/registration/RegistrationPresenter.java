package com.rxmuhammadyoussef.anabeesh.ui.registration;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityScope;
import com.rxmuhammadyoussef.anabeesh.events.error.NetworkConnectionError;
import com.rxmuhammadyoussef.anabeesh.events.error.WebServiceError;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.anabeesh.store.AuthenticationRepo;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BasePresenter;
import com.rxmuhammadyoussef.core.util.TextUtil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@ActivityScope
class RegistrationPresenter extends BasePresenter {

    private final BehaviorRelay<Boolean> firstNameValidityRelay;
    private final BehaviorRelay<Boolean> lastNameValidityRelay;
    private final BehaviorRelay<Boolean> passwordValidityRelay;
    private final BehaviorRelay<Boolean> emailValidityRelay;
    private final RegistrationScreen registrationScreen;
    private final AuthenticationRepo authenticationRepo;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;

    @Inject
    RegistrationPresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                          AuthenticationRepo authenticationRepo,
                          RegistrationScreen registrationScreen,
                          CompositeDisposable disposable) {
        super(registrationScreen);
        this.threadSchedulers = threadSchedulers;
        this.authenticationRepo = authenticationRepo;
        this.registrationScreen = registrationScreen;
        this.disposable = disposable;
        this.firstNameValidityRelay = BehaviorRelay.createDefault(false);
        this.lastNameValidityRelay = BehaviorRelay.createDefault(false);
        this.emailValidityRelay = BehaviorRelay.createDefault(false);
        this.passwordValidityRelay = BehaviorRelay.createDefault(false);
    }

    @Override
    protected void onCreate() {
        registrationScreen.setupEditText();
        disposable.add(Observable.combineLatest(
                firstNameValidityRelay.hide(),
                lastNameValidityRelay.hide(),
                emailValidityRelay.hide(),
                passwordValidityRelay.hide(),
                this::shouldEnableRegistrationButton)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(registrationScreen::setRegistrationButtonEnabled, Timber::e));
    }

    void onAfterFirstNameChange(TextUtil.Result result) {
        firstNameValidityRelay.accept(result.isValid());
    }

    void onAfterLastNameChange(TextUtil.Result result) {
        lastNameValidityRelay.accept(result.isValid());
    }

    void onAfterEmailChange(TextUtil.Result result) {
        emailValidityRelay.accept(result.isValid());
    }

    void onAfterPasswordChange(TextUtil.Result result) {
        passwordValidityRelay.accept(result.isValid());
    }

    private boolean shouldEnableRegistrationButton(boolean fName, boolean lName, boolean email, boolean password) {
        return fName && lName && email && password;
    }

    void onRegisterClick(String email, String password, String fName, String lName) {
        registrationScreen.showLoadingAnimation();
        authenticationRepo.register(email, password, fName, lName, new OperationListener<UserModel>() {
            @Override
            public void onSuccess(UserModel element) {
                registrationScreen.hideLoadingAnimation();
                registrationScreen.onUserReady(element);
            }

            @Override
            public void onError(Throwable t) {
                registrationScreen.hideLoadingAnimation();
                processErrorResult(t);
            }
        });
    }

    private void processErrorResult(Throwable t) {
        Timber.e(t);
        if (t instanceof WebServiceError) {
            registrationScreen.showErrorMessage(t.getMessage());
        } else if (t instanceof NetworkConnectionError) {
            registrationScreen.showErrorMessage(getResourcesUtil().getString(R.string.error_network));
        } else {
            registrationScreen.showErrorMessage(getResourcesUtil().getString(R.string.error_communicating_with_server));
        }
    }

    protected void onDestroy() {
        disposable.clear();
    }
}
