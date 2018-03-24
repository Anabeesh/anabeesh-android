package com.rxmuhammadyoussef.anabeesh.ui.registration;

import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface RegistrationActivityScreen extends BaseActivityScreen {

    void setupEditText();

    void setRegistrationButtonEnabled(boolean enabled);

    void onUserReady(UserModel element);
}
