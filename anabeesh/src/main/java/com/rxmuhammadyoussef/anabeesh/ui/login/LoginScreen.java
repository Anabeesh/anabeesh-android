package com.rxmuhammadyoussef.anabeesh.ui.login;

import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BaseScreen;

public interface LoginScreen extends BaseScreen {

    void setupEditText();

    void onUserReady(UserModel user);

    void setLoginButtonEnabled(boolean enabled);
}
