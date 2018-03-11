package com.rxmuhammadyoussef.anabeesh.ui.registration;

import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityScope;
import com.rxmuhammadyoussef.anabeesh.events.error.NetworkConnectionError;
import com.rxmuhammadyoussef.anabeesh.events.error.WebServiceError;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.AuthenticationRepo;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BasePresenter;
import com.rxmuhammadyoussef.core.util.TextUtil;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@ActivityScope
class RegistrationPresenter extends BasePresenter {

    private final RegistrationScreen registrationScreen;
    private final AuthenticationRepo authenticationRepo;
    private final SparseArray<String> errorSparseArray;
    private final CompositeDisposable disposable;
    private final TextUtil textUtil;
    private File avatarImage;

    @Inject
    RegistrationPresenter(AuthenticationRepo authenticationRepo,
                          RegistrationScreen registrationScreen,
                          CompositeDisposable disposable,
                          TextUtil textUtil) {
        super(registrationScreen);
        this.authenticationRepo = authenticationRepo;
        this.errorSparseArray = new SparseArray<>();
        this.registrationScreen = registrationScreen;
        this.textUtil = textUtil;
        this.disposable = disposable;
    }

    @Override
    protected void onCreate() {
        registrationScreen.setupEditText();
        registrationScreen.updateAvatarImage(null);
    }

    void onAfterFirstNameChange(TextUtil.Result result) {
        if (result.isValid()) {
            errorSparseArray.remove(TextUtil.KEY_FIRST_NAME);
        } else {
            errorSparseArray.put(TextUtil.KEY_FIRST_NAME, result.getMessageAr());
        }
    }

    void onAfterLastNameChange(TextUtil.Result result) {
        if (result.isValid()) {
            errorSparseArray.remove(TextUtil.KEY_LAST_NAME);
        } else {
            errorSparseArray.put(TextUtil.KEY_LAST_NAME, result.getMessageAr());
        }
    }

    void onAfterEmailChange(TextUtil.Result result) {
        if (result.isValid()) {
            errorSparseArray.remove(TextUtil.KEY_EMAIL);
        } else {
            errorSparseArray.put(TextUtil.KEY_EMAIL, result.getMessageAr());
        }
    }

    void onAfterPasswordChange(TextUtil.Result result) {
        if (result.isValid()) {
            errorSparseArray.remove(TextUtil.KEY_PASSWORD);
        } else {
            errorSparseArray.put(TextUtil.KEY_PASSWORD, result.getMessageAr());
        }
    }

    void observeIfPasswordsMatch(TextUtil.Result result) {
        if (result.isValid()) {
            errorSparseArray.remove(TextUtil.KEY_PASSWORD_MATCH);
        } else {
            errorSparseArray.put(TextUtil.KEY_PASSWORD_MATCH, result.getMessageAr());
        }
    }

    void onRegisterClick(String fName, String lName, String email, String password) {
        if (errorSparseArray.size() == 0) {
            register(fName, lName, email, password);
        } else {
            showErrorMessage();
        }
    }

    private void register(String email, String password, String firstName, String lastName) {
        registrationScreen.showLoadingAnimation();
        authenticationRepo.register(email, password, firstName, lastName, new OperationListener<UserModel>() {
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

    private void showErrorMessage() {
        if (errorSparseArray.get(TextUtil.KEY_FIRST_NAME) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_FIRST_NAME));
        } else if (errorSparseArray.get(TextUtil.KEY_LAST_NAME) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_LAST_NAME));
        } else if (errorSparseArray.get(TextUtil.KEY_PHONE) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_PHONE));
        } else if (errorSparseArray.get(TextUtil.KEY_EMAIL) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_EMAIL));
        } else if (errorSparseArray.get(TextUtil.KEY_PASSWORD) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_PASSWORD));
        } else if (errorSparseArray.get(TextUtil.KEY_PASSWORD_MATCH) != null) {
            registrationScreen.showErrorMessage(errorSparseArray.get(TextUtil.KEY_PASSWORD_MATCH));
        }
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
