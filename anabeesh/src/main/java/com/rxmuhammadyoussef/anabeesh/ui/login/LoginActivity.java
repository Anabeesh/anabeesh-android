package com.rxmuhammadyoussef.anabeesh.ui.login;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.widget.Button;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityScope;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.anabeesh.ui.registration.RegistrationActivity;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.widget.rxedittext.email.EmailEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.password.PasswordEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ActivityScope
public class LoginActivity extends BaseActivity implements LoginScreen {

    @BindView(R.id.et_email)
    EmailEditText emailEditText;
    @BindView(R.id.et_password)
    PasswordEditText etPassword;
    @BindView(R.id.container_snackbar)
    CoordinatorLayout snackBarContainer;
    @BindView(R.id.btn_login)
    Button loginButton;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreateActivity() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setupEditText() {
        emailEditText.setValidityListener(result -> loginPresenter.onAfterEmailChange(result));
        etPassword.setValidityListener(result -> loginPresenter.onAfterPasswordChange(TextUtils.isEmpty(etPassword.getText())));
    }

    @Override
    public void showErrorMessage(String message) {
        getUiUtil().getErrorSnackBar(snackBarContainer, message)
                .show();
    }

    @Override
    public void onUserReady(UserModel user) {
        //TODO navigate to home
       /* Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        loginButton.setEnabled(enabled);
    }

    @OnClick(R.id.btn_new_account)
    void onNewAccountClicked() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    @OnClick(R.id.btn_login)
    void onClicked() {
        loginPresenter.onLoginClicked(
                emailEditText.getText().toString(),
                etPassword.getText().toString());
    }

    @OnClick(R.id.tv_forget_password)
    void onForgotPasswordClick() {
        //TODO navigate to forgot password
    }
}