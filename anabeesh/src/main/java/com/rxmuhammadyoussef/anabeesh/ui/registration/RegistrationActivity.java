package com.rxmuhammadyoussef.anabeesh.ui.registration;

import android.support.design.widget.CoordinatorLayout;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityScope;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.widget.rxedittext.email.EmailEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.name.NameEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.password.PasswordEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityScope
public class RegistrationActivity extends BaseActivity implements RegistrationScreen {

    @BindView(R.id.et_first_name)
    NameEditText firstNameEditText;
    @BindView(R.id.et_last_name)
    NameEditText lastNameEditText;
    @BindView(R.id.et_email)
    EmailEditText emailEditText;
    @BindView(R.id.et_password)
    PasswordEditText passwordEditText;
    @BindView(R.id.et_confirm_password)
    PasswordEditText confirmPasswordEditText;
    @BindView(R.id.container_snackbar)
    CoordinatorLayout snackBarContainer;
    @BindView(R.id.iv_avatar)
    CircleImageView avatarImageView;

    @Inject
    RegistrationPresenter presenter;

    @Override
    protected void onCreateActivity() {
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
        passwordEditText.checIfPasswordsMatches(confirmPasswordEditText, result -> presenter.observeIfPasswordsMatch(result));
    }

    @Override
    public void updateAvatarImage(String path) {
        GlideApp.with(this)
                .load(path)
                .centerCrop()
                .placeholder(R.drawable.user_icon)
                .into(avatarImageView);
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

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        finish();
    }

    @OnClick(R.id.iv_avatar)
    void onAvatarClick() {
    }

    @OnClick(R.id.btn_register)
    void onRegisterClick() {
        presenter.onRegisterClick(
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                emailEditText.getText().toString(),
                passwordEditText.getText().toString());
    }
}
