package com.rxmuhammadyoussef.anabeesh.ui.login;

import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface LoginActivityScreen extends BaseActivityScreen {

    void setupEditText();

    void onUserReady(UserModel user);

    void setLoginButtonEnabled(boolean enabled);
}
