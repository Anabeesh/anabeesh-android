package com.rxmuhammadyoussef.anabeesh.ui.addquestion;

import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface AddQuestionScreen extends BaseActivityScreen {

    void setupEditText();

    void setSendButtonEnabled(boolean enabled);

    void onQuestionPostedSuccessfully();
}
