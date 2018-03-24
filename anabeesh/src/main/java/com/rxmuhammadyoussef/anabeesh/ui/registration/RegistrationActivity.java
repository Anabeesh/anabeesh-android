package com.rxmuhammadyoussef.anabeesh.ui.registration;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.widget.Button;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivity;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.widget.rxedittext.email.EmailEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.name.NameEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.password.PasswordEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ActivityScope
public class RegistrationActivity extends BaseActivity implements RegistrationActivityScreen {

    @BindView(R.id.et_first_name)
    NameEditText firstNameEditText;
    @BindView(R.id.et_last_name)
    NameEditText lastNameEditText;
    @BindView(R.id.et_email)
    EmailEditText emailEditText;
    @BindView(R.id.et_password)
    PasswordEditText passwordEditText;
    @BindView(R.id.btn_register)
    Button registerButton;
    @BindView(R.id.container_snackbar)
    CoordinatorLayout snackBarContainer;

    @Inject
    RegistrationPresenter presenter;

    @Override
    protected void onCreateActivityComponents() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_registration;
    }

    @Override
    public void setupEditText() {
        firstNameEditText.setValidityListener(result -> presenter.onAfterFirstNameChange(result));
        lastNameEditText.setValidityListener(result -> presenter.onAfterLastNameChange(result));
        emailEditText.setValidityListener(result -> presenter.onAfterEmailChange(result));
        passwordEditText.setValidityListener(result -> presenter.onAfterPasswordChange(result));
    }

    @Override
    public void setRegistrationButtonEnabled(boolean enabled) {
        registerButton.setEnabled(enabled);
    }

    @Override
    public void showErrorMessage(String message) {
        getUiUtil().getErrorSnackBar(snackBarContainer, message)
                .show();
    }

    @Override
    public void onUserReady(UserModel user) {
        Intent intent = new Intent(this, HostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.tv_login)
    void onLoginClick() {
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.btn_register)
    void onRegisterClick() {
        presenter.onRegisterClick(
                emailEditText.getText().toString(),
                passwordEditText.getText().toString(),
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString());
    }
}
