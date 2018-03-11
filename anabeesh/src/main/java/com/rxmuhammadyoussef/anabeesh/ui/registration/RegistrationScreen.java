package com.rxmuhammadyoussef.anabeesh.ui.registration;

import com.rxmuhammadyoussef.anabeesh.store.model.user.UserModel;
import com.rxmuhammadyoussef.core.component.activity.BaseScreen;

public interface RegistrationScreen extends BaseScreen {

    void setupEditText();

    void updateAvatarImage(String path);

    void onUserReady(UserModel element);
}
